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
import com.iassets.biomedical.servlet.joborder.JobOrderTrackingProcessServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/JobOrderFollowupFirstPhaseProcess"})
public class JobOrderFollowupFirstPhaseProcessServlet
extends JobOrderTrackingProcessServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int jobOrderId = WebUtil.getParamValueAsInteger(request, "jobOrderId", 0);
        BioJobOrder order = this.bioDBQueryManager.findById(jobOrderId, BioJobOrder.class);
        if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
        Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
        order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
        order.setFirstAction(WebUtil.getParamValue(request, "firstAction", null));
        order.setFirstActionDate(WebUtil.getParamValueAsDate(request, "firstActionDate", null));
        if (order.getLastCompletedPhaseId().intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId()) {
            order.setAgentMustAttend("1".equals(request.getParameter("agentMustAttend")));
            order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId());
        }
        order = this.bioDBQueryManager.mergeEntity(order);
        if (request.getParameter("showJobOrderReport") == null) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.JobOrderFollowupFirstPhaseProcess.addSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        }
        this.updateNotificationsThenGoForward(request, response);
    }
}
