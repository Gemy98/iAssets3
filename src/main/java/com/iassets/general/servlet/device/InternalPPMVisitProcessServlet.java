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
package com.iassets.general.servlet.device;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.util.Common;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmRecord;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/gen/InternalPPMVisitProcess"})
public class InternalPPMVisitProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int sessionSiteId = this.getSessionSiteId(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        int ppmNotifyId = WebUtil.getParamValueAsInteger(request, "ppm_notify_id", 0);
        int ppmStatus = WebUtil.getParamValueAsInteger(request, "ppm_Status", 0);
        if (deviceId == 0 || ppmNotifyId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        GenHospitalDeviceInternalPpmNotificationSchedule ppmNotificationSchedule = this.genDBQueryManager.findById(ppmNotifyId, GenHospitalDeviceInternalPpmNotificationSchedule.class, "GenHospitalDeviceInternalPpmNotificationSchedule.graph.fetchAll");
        if (ppmNotificationSchedule.getSiteId() != sessionSiteId || ppmNotificationSchedule.getDeviceId() != deviceId) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        GenHospitalDeviceInternalPpmRecord deviceInternalPpmRecord = new GenHospitalDeviceInternalPpmRecord();
        deviceInternalPpmRecord.setSiteId(this.getSessionSiteId(request));
        deviceInternalPpmRecord.setDeviceId(deviceId);
        deviceInternalPpmRecord.setScheduledPpmId(ppmNotificationSchedule.getScheduledPpmId());
        deviceInternalPpmRecord.setPpmPeriodId(ppmNotificationSchedule.getPpmPeriodId());
        deviceInternalPpmRecord.setPpmPeriodPriority(ppmNotificationSchedule.getPpmPeriodPriority());
        deviceInternalPpmRecord.setPpmStatus(ppmStatus);
        deviceInternalPpmRecord.setPlannedPpmDate(ppmNotificationSchedule.getPlannedDate());
        deviceInternalPpmRecord.setCreatedBy(this.getSessionUser(request));
        deviceInternalPpmRecord.setCreatedIn(new Date());
        if (ppmStatus == 1) {
            deviceInternalPpmRecord.setActualPpmDate(WebUtil.getParamValueAsDate(request, "visitDate", null));
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.INTERNAL_PPM_UPLOADS);
            Object[] ppmCheckListReport = FileUtil.uploadFiles(request, "internal_ppm_file", config);
            deviceInternalPpmRecord.setChecklistReportUrl(Common.concatenateValues(ppmCheckListReport));
            deviceInternalPpmRecord.setResponsibleEmployee(this.genDBQueryManager.getReference(WebUtil.getParamValueAsInteger(request, "responsibleEng", 0), CommonEmployee.class));
        } else if (ppmStatus == 2) {
            deviceInternalPpmRecord.setIncompletionReason(WebUtil.getParamValue(request, "absenceReason", null));
        }
        deviceInternalPpmRecord.setLookupCategoryId(device.getLookupCategoryId());
        deviceInternalPpmRecord = this.genDBQueryManager.mergeEntity(deviceInternalPpmRecord);
        ppmNotificationSchedule.setRecorded(true);
        ppmNotificationSchedule = this.genDBQueryManager.mergeEntity(ppmNotificationSchedule);
        this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/gen/InternalPpmFollowupSearch");
    }
}
