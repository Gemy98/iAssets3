/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.joborder;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenJobOrderSparePart;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.entity.GenSpInventoryTransaction;
import com.iassets.general.servlet.joborder.JobOrderTrackingProcessServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/gen/JobOrderFollowupSecondPhaseProcess"})
public class JobOrderFollowupSecondPhaseProcessServlet
extends JobOrderTrackingProcessServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        if (!this.skipActionProcessing(request)) {
            int jobOrderId = WebUtil.getParamValueAsInteger(request, "jobOrderId", 0);
            GenJobOrder order = this.genDBQueryManager.findById(jobOrderId, GenJobOrder.class);
            if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            this.resetProperties(order);
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
            Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
            order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
            order.setFixIncludingSpareParts("1".equals(request.getParameter("fixIncludingSpareParts")));
            if (!order.getFixIncludingSpareParts().booleanValue()) {
                this.setFinalActionDetails(order, request);
            } else {
                Integer spSource = WebUtil.getParamValueAsInteger(request, "spSource", null);
                order.setSpSource(spSource);
                if (spSource.intValue() == Enums.JOBORDER_SP_SOURCES.INVENTORY.getId()) {
                    try {
                        order.setInventoryTransactionDate(WebUtil.getParamValueAsDate(request, "spTransDate", null));
                        this.setSparePartsFromSpInventory(request, order);
                        this.setFinalActionDetails(order, request);
                    }
                    catch (Exception e) {
                        this.announceErorrMessage(request, response, "\u0623\u062d\u062f \u0643\u0645\u064a\u0627\u062a \u0642\u0637\u0639 \u0627\u0644\u063a\u064a\u0627\u0631 \u0627\u0644\u0645\u0637\u0644\u0648\u0628\u0629 \u063a\u064a\u0631 \u0645\u062a\u0627\u062d\u0629");
                        return;
                    }
                } else if (spSource.intValue() == Enums.JOBORDER_SP_SOURCES.PURCHASE_ORDER.getId()) {
                    Object[] spPoScanUrl = FileUtil.uploadFiles(request, "spPoScanUrl", config);
                    order.setSpPoScanUrl(Common.concatenateValues(spPoScanUrl));
                    this.setSparePartsOfPurchaseOrder(request, order);
                    this.setFinalActionDetails(order, request);
                } else if (spSource.intValue() == Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
                    Object[] agentReportUrl = FileUtil.uploadFiles(request, "agentReportUrl", config);
                    if (agentReportUrl != null && agentReportUrl.length != 0) {
                        order.setAgentReportUrl(Common.concatenateValues(agentReportUrl));
                        order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.AGENT_REPORT_UPLOADED.getId());
                    }
                    if (request.getParameter("quotationRecieved") != null) {
                        order.setQuotationRecieved(true);
                        Object[] quotationScanUrl = FileUtil.uploadFiles(request, "quotationScanUrl", config);
                        order.setQuotationScanUrl(Common.concatenateValues(quotationScanUrl));
                        order.setQuotationDate(WebUtil.getParamValueAsDate(request, "quotationDate", null));
                        order.setQuotationExpireDate(WebUtil.getParamValueAsDate(request, "quotationExpireDate", null));
                        this.setSparePartsInsideQuotation(request, order);
                        order.setSecondAction(WebUtil.getParamValue(request, "secondAction", null));
                        order.setSecondActionDate(WebUtil.getParamValueAsDate(request, "secondActionDate", null));
                        order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.QUOTATION_RECIEVED.getId());
                    }
                    if (WebUtil.userHasRightToAcceptSparePartsQuotation(request) && request.getParameter("quotationAccepted") != null) {
                        order.setQuotationAccepted(true);
                        order.setQuotationAcceptanceNo(WebUtil.getParamValue(request, "quotationAcceptanceNo", null));
                        order.setQuotationAcceptanceDate(WebUtil.getParamValueAsDate(request, "quotationAcceptanceDate", null));
                        order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.SECOND_ACTION_TAKEN.getId());
                    }
                }
            }
            if (!order.isUncodedDevice()) {
                GenHospitalDevice device = order.getHospitalDevice();
                device = this.genDBQueryManager.mergeEntity(device);
            }
            order = this.genDBQueryManager.mergeEntity(order);
            if (request.getParameter("showJobOrderReport") == null) {
                this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
            }
        }
        this.updateNotificationsThenGoForward(request, response);
    }

    private void resetProperties(GenJobOrder order) {
        if (order.getJobOrderSpareParts() == null) {
            order.setJobOrderSpareParts(new ArrayList<GenJobOrderSparePart>());
        } else {
            order.getJobOrderSpareParts().clear();
        }
        if (order.getSparePartsInsideQuotation() == null) {
            order.setSparePartsInsideQuotation(new ArrayList<GenJobOrderSparePart>());
        } else {
            order.getSparePartsInsideQuotation().clear();
        }
        order.setSpSource(null);
        order.setInventoryTransactionDate(null);
        order.setSpPoScanUrl(null);
        order.setAgentReportUrl(null);
        order.setQuotationRecieved(null);
        order.setQuotationScanUrl(null);
        order.setQuotationDate(null);
        order.setQuotationExpireDate(null);
        order.setQuotationAccepted(null);
        order.setQuotationAcceptanceDate(null);
        order.setSecondAction(null);
        order.setSecondActionDate(null);
    }

    private void setSparePartsInsideQuotation(HttpServletRequest request, GenJobOrder order) throws FileNotFoundException, IOException, ServletException {
        String jobOrderSpareParts_rowOrder = request.getParameter("agentSpareParts_rowOrder");
        if (jobOrderSpareParts_rowOrder != null && !jobOrderSpareParts_rowOrder.isEmpty()) {
            String[] indexes = jobOrderSpareParts_rowOrder.split(",");
            int quantity = 0;
            float price = 0.0f;
            for (int i = 0; i < indexes.length; ++i) {
                GenJobOrderSparePart sPart = new GenJobOrderSparePart();
                quantity = WebUtil.getParamValueAsInteger(request, "agentSpareParts_accQuantity_" + indexes[i], 0);
                price = WebUtil.getParamValueAsFloat(request, "agentSpareParts_accPrice_" + indexes[i], Float.valueOf(0.0f)).floatValue();
                sPart.setInQuotation(true);
                sPart.setQuantity(quantity);
                sPart.setPrice(Float.valueOf(price));
                sPart.setTotalPrice(Float.valueOf((float)quantity * price));
                sPart.setDescription(WebUtil.getParamValue(request, "agentSpareParts_accDescription_" + indexes[i], null));
                order.addSparePartsInsideQuotation(sPart);
            }
        }
    }

    private void setSparePartsOfPurchaseOrder(HttpServletRequest request, GenJobOrder order) throws FileNotFoundException, IOException, ServletException {
        String jobOrderSpareParts_rowOrder = request.getParameter("poSpareParts_rowOrder");
        if (jobOrderSpareParts_rowOrder != null && !jobOrderSpareParts_rowOrder.isEmpty()) {
            String[] indexes = jobOrderSpareParts_rowOrder.split(",");
            int quantity = 0;
            float price = 0.0f;
            for (int i = 0; i < indexes.length; ++i) {
                GenJobOrderSparePart sPart = new GenJobOrderSparePart();
                quantity = WebUtil.getParamValueAsInteger(request, "poSpareParts_accQuantity_" + indexes[i], 0);
                price = WebUtil.getParamValueAsFloat(request, "poSpareParts_accPrice_" + indexes[i], Float.valueOf(0.0f)).floatValue();
                sPart.setQuantity(quantity);
                sPart.setPrice(Float.valueOf(price));
                sPart.setTotalPrice(Float.valueOf((float)quantity * price));
                sPart.setDescription(WebUtil.getParamValue(request, "poSpareParts_accDescription_" + indexes[i], null));
                order.addJobOrderSparePart(sPart);
            }
        }
    }

    private void setSparePartsFromSpInventory(HttpServletRequest request, GenJobOrder order) throws Exception {
        String jobOrderSpareParts_rowOrder = request.getParameter("inventorySpareParts_rowOrder");
        if (jobOrderSpareParts_rowOrder != null && !jobOrderSpareParts_rowOrder.isEmpty()) {
            String[] indexes = jobOrderSpareParts_rowOrder.split(",");
            int quantity = 0;
            for (int i = 0; i < indexes.length; ++i) {
                GenSpInventoryContent spInventoryContent = this.genDBQueryManager.getSpInventoryContentWithStats(this.getSessionSiteId(request), WebUtil.getParamValue(request, "inventorySpareParts_spCode_" + indexes[i], null));
                quantity = WebUtil.getParamValueAsInteger(request, "inventorySpareParts_spQuantity_" + indexes[i], 0);
                if (quantity > spInventoryContent.getAvailableQuantity()) {
                    throw new Exception("\u0627\u0644\u0643\u0645\u064a\u0629 \u0627\u0644\u0645\u0637\u0644\u0648\u0628\u0629 \u063a\u064a\u0631 \u0645\u062a\u0627\u062d\u0629 \u0628\u0627\u0644\u0645\u0633\u062a\u0648\u062f\u0639");
                }
                GenJobOrderSparePart sPart = new GenJobOrderSparePart();
                sPart.setQuantity(quantity);
                sPart.setPrice(spInventoryContent.getAvgUnitPrice());
                sPart.setTotalPrice(Float.valueOf((float)quantity * spInventoryContent.getAvgUnitPrice().floatValue()));
                sPart.setDescription(spInventoryContent.getName());
                sPart.setFromInventory(true);
                sPart.setSpInventoryCategoryId(spInventoryContent);
                order.addJobOrderSparePart(sPart);
                if (!WebUtil.userHasRightToCloseJobOrder(request) || request.getParameter("closed") == null) continue;
                GenSpInventoryTransaction transaction = new GenSpInventoryTransaction();
                transaction.setSiteId(spInventoryContent.getSite().getId());
                transaction.setSpInventoryContentId(spInventoryContent.getId());
                transaction.setJobOrder(order);
                transaction.setInput(false);
                transaction.setQuantity(quantity);
                transaction.setUnitPrice(spInventoryContent.getAvgUnitPrice());
                transaction.setPerformedBy(this.getSessionUser(request));
                transaction.setPerformedIn(new Date());
                transaction = this.genDBQueryManager.mergeEntity(transaction);
                spInventoryContent.setAvailableQuantity(spInventoryContent.getAvailableQuantity() - quantity);
                spInventoryContent.setLastModefiedBy(this.getSessionUser(request));
                spInventoryContent.setLastModefiedIn(new Date());
                spInventoryContent = this.genDBQueryManager.mergeEntity(spInventoryContent);
            }
        }
    }
}
