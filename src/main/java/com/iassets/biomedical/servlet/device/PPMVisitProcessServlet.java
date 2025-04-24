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
package com.iassets.biomedical.servlet.device;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioHospitalDevicePpmDetail;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/PPMVisitProcess"})
public class PPMVisitProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        int ppmId = WebUtil.getParamValueAsInteger(request, "ppmId", 0);
        BioHospitalDevicePpmDetail ppm = null;
        ppm = ppmId != 0 ? this.bioDBQueryManager.findById(ppmId, BioHospitalDevicePpmDetail.class) : new BioHospitalDevicePpmDetail();
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        ppm.setHospitalDevice(device);
        ppm.setVisitMonth(WebUtil.getParamValueAsInteger(request, "visitMonth", null));
        ppm.setVisitYear(WebUtil.getParamValueAsInteger(request, "visitYear", null));
        ppm.setSubcontractor(WebUtil.getParamValue(request, "subcontractor", null));
        int visitStatusInt = WebUtil.getParamValueAsInteger(request, "visitStatus", null);
        ppm.setVisitStatus(visitStatusInt);
        ppm.setVisitDate(null);
        ppm.setVisitCost(null);
        ppm.setVisitReportUrl(null);
        ppm.setDelayPenalty(null);
        ppm.setAbsenceReason(null);
        ppm.setAbsenceReportUrl(null);
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.PPM_UPLOADS);
        if (visitStatusInt == 1 || visitStatusInt == 3) {
            ppm.setVisitDate(WebUtil.getParamValueAsDate(request, "visitDate", null));
            ppm.setVisitCost(WebUtil.getParamValueAsFloat(request, "visitCost", null));
            Object[] visitReportURL = FileUtil.uploadFiles(request, "visitReportURL", config);
            ppm.setVisitReportUrl(Common.concatenateValues(visitReportURL));
            if (visitStatusInt == 3) {
                ppm.setDelayPenalty(WebUtil.getParamValueAsFloat(request, "delayPenalty", null));
            }
        } else if (visitStatusInt == 2) {
            ppm.setAbsenceReason(WebUtil.getParamValue(request, "absenceReason", null));
            Object[] absenceReportURL = FileUtil.uploadFiles(request, "absenceReportURL", config);
            ppm.setAbsenceReportUrl(Common.concatenateValues(absenceReportURL));
        }
        ppm.setCreatedBy(this.getSessionUser(request));
        ppm.setCreatedIn(new Date());
        ppm = this.bioDBQueryManager.mergeEntity(ppm);
        if (ppmId == 0) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.PPMVisitProcess.addSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.PPMVisitProcess.updateSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        }
        this.sendRedirect(request, response, "/bio/PpmFollowupSearch");
    }
}
