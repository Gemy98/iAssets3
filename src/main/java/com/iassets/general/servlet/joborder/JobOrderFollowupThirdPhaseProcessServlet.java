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
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenJobOrderSparePart;
import com.iassets.general.servlet.joborder.JobOrderTrackingProcessServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/gen/JobOrderFollowupThirdPhaseProcess"})
public class JobOrderFollowupThirdPhaseProcessServlet
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
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
            Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
            order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
            if (order.getSparePartsOutsideQuotation() == null) {
                order.setSparePartsOutsideQuotation(new ArrayList<GenJobOrderSparePart>());
            } else {
                order.getSparePartsOutsideQuotation().clear();
            }
            this.setSparePartsOutsideQuotation(request, order);
            this.setFinalActionDetails(order, request);
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

    private void setSparePartsOutsideQuotation(HttpServletRequest request, GenJobOrder order) throws FileNotFoundException, IOException, ServletException {
        String jobOrderSpareParts_rowOrder = request.getParameter("extraSpareParts_rowOrder");
        if (jobOrderSpareParts_rowOrder != null && !jobOrderSpareParts_rowOrder.isEmpty()) {
            String[] indexes = jobOrderSpareParts_rowOrder.split(",");
            int quantity = 0;
            float price = 0.0f;
            for (int i = 0; i < indexes.length; ++i) {
                GenJobOrderSparePart sPart = new GenJobOrderSparePart();
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
