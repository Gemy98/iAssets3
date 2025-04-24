/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.endpoints.JobOrderNotificationsEndPoint;
import com.iassets.biomedical.endpoints.SPInventoryNotificationsEndPoint;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.WebUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@MultipartConfig
public class JobOrderTrackingProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    JobOrderNotificationsEndPoint joNotifier;
    @Autowired
    SPInventoryNotificationsEndPoint spCategoryNotifier;

    protected void updateNotifications(HttpServletRequest request) {
        int siteId = this.getSessionSiteId(request);
        String langCode = this.getSessionLanguage(request);
        this.joNotifier.broadcastNotificationUpdates(siteId, langCode);
        this.spCategoryNotifier.broadcastNotificationUpdates(siteId, langCode);
    }

    protected void updateNotificationsThenGoForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("showJobOrderReportForPrint") != null) {
            this.sendRedirect(request, response, "/bio/ViewJobOrderReport?jobOrderNo=" + request.getParameter("jobOrderNo"));
            return;
        }
        this.updateNotifications(request);
        Common.log("submit btn name : " + request.getParameter("saveThenReturn"));
        Common.log("back btn name : " + request.getParameter("saveThenGoBack"));
        if (WebUtil.getParamValue(request, "saveThenReturn", null) != null) {
            if (request.getParameter("showJobOrderReport") != null) {
                this.sendRedirect(request, response, "/bio/ViewJobOrderReport?jobOrderNo=" + request.getParameter("jobOrderNo"));
            } else {
                this.sendRedirect(request, response, "/bio/FollowupJobOrderSearch");
            }
        } else if (WebUtil.getParamValue(request, "saveThenContinue", null) != null) {
            this.basicForward(request, response, "/bio/FollowupJobOrder");
        } else if (WebUtil.getParamValue(request, "saveThenGoBack", null) != null) {
            String uri = WebUtil.getRequestUriLastSegment(request);
            String reqPhaseId = null;
            if (uri.contains("JobOrderFollowupSecondPhaseProcess")) {
                reqPhaseId = "" + Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId();
            }
            request.setAttribute("phaseId", reqPhaseId);
            this.basicForward(request, response, "/bio/FollowupJobOrder");
        } else {
            this.sendRedirect(request, response, "/bio/FollowupJobOrderSearch");
        }
    }

    protected void setFinalActionDetails(BioJobOrder jobOrder, HttpServletRequest request) throws FileNotFoundException, IOException, ServletException {
        BioHospitalDevice device = jobOrder.getHospitalDevice();
        jobOrder.setFinalAction(null);
        jobOrder.setFinalActionDate(null);
        jobOrder.setFinalReportUrl(null);
        jobOrder.setFinalAgentReportUrl(null);
        jobOrder.setFinalAction(WebUtil.getParamValue(request, "finalAction", null));
        jobOrder.setFinalActionDate(WebUtil.getParamValueAsDate(request, "finalActionDate", null));
        if (!jobOrder.getFixIncludingSpareParts().booleanValue() || jobOrder.getFixIncludingSpareParts().booleanValue() && jobOrder.getSpSource().intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
            jobOrder.setSecondAction(jobOrder.getFinalAction());
            jobOrder.setSecondActionDate(jobOrder.getFinalActionDate());
        }
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
        Object[] finalReportUrl = FileUtil.uploadFiles(request, "finalReportUrl", config);
        jobOrder.setFinalReportUrl(Common.concatenateValues(finalReportUrl));
        if (jobOrder.getAgentMustAttend().booleanValue()) {
            Object[] finalAgentReportUrl = FileUtil.uploadFiles(request, "finalAgentReportUrl", config);
            jobOrder.setFinalAgentReportUrl(Common.concatenateValues(finalAgentReportUrl));
        }
        if (WebUtil.userHasRightToCloseJobOrder(request) && request.getParameter("closed") != null) {
            jobOrder.setClosed(true);
            jobOrder.setClosedBy(this.getSessionUser(request));
            jobOrder.setClosedIn(new Date());
            jobOrder.setActualCloseDate(WebUtil.getParamValueAsDate(request, "actualCloseDate", null));
            jobOrder.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.FINAL_ACTION_TAKEN.getId());
            device.setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
        }
    }

    public boolean skipActionProcessing(HttpServletRequest request) {
        return WebUtil.getParamValue(request, "saveThenGoBack", null) != null;
    }
}
