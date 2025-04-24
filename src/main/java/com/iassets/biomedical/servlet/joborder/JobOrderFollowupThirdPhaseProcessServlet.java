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
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.entity.BioJobOrderSparePart;
import com.iassets.biomedical.servlet.joborder.JobOrderTrackingProcessServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/JobOrderFollowupThirdPhaseProcess"})
public class JobOrderFollowupThirdPhaseProcessServlet
extends JobOrderTrackingProcessServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        if (!this.skipActionProcessing(request)) {
            int jobOrderId = WebUtil.getParamValueAsInteger(request, "jobOrderId", 0);
            BioJobOrder order = this.bioDBQueryManager.findById(jobOrderId, BioJobOrder.class);
            if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
            Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
            order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
            if (order.getSparePartsOutsideQuotation() == null) {
                order.setSparePartsOutsideQuotation(new ArrayList<BioJobOrderSparePart>());
            } else {
                order.getSparePartsOutsideQuotation().clear();
            }
            this.setSparePartsOutsideQuotation(request, order);
            this.setFinalActionDetails(order, request);
            order = this.bioDBQueryManager.mergeEntity(order);
            if (request.getParameter("showJobOrderReport") == null) {
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.JobOrderFollowupThirdPhaseProcess.addSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            }
        }
        this.updateNotificationsThenGoForward(request, response);
    }

    private void setSparePartsOutsideQuotation(HttpServletRequest request, BioJobOrder order) throws FileNotFoundException, IOException, ServletException {
        String jobOrderSpareParts_rowOrder = request.getParameter("extraSpareParts_rowOrder");
        if (jobOrderSpareParts_rowOrder != null && !jobOrderSpareParts_rowOrder.isEmpty()) {
            String[] indexes = jobOrderSpareParts_rowOrder.split(",");
            int quantity = 0;
            float price = 0.0f;
            for (int i = 0; i < indexes.length; ++i) {
                BioJobOrderSparePart sPart = new BioJobOrderSparePart();
                quantity = WebUtil.getParamValueAsInteger(request, "extraSpareParts_accQuantity_" + indexes[i], 0);
                price = 0.0f;
                sPart.setInQuotation(false);
                sPart.setQuantity(quantity);
                sPart.setPrice(Float.valueOf(price));
                sPart.setTotalPrice(Float.valueOf((float)quantity * price));
                sPart.setDescription(WebUtil.getParamValue(request, "extraSpareParts_accDescription_" + indexes[i], null));
                order.addSparePartsOutsideQuotation(sPart);
            }
        }
    }
}
