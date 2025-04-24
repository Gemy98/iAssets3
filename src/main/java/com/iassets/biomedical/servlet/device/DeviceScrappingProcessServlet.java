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
import com.iassets.biomedical.entity.BioHospitalDeviceScrappingInfo;
import com.iassets.biomedical.entity.BioLookupScrappingReason;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
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
@WebServlet(value={"/bio/DeviceScrappingProcess"})
public class DeviceScrappingProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        int deviceScrappingId = WebUtil.getParamValueAsInteger(request, "deviceScrappingId", 0);
        BioHospitalDeviceScrappingInfo scrapping = null;
        scrapping = deviceScrappingId != 0 ? this.bioDBQueryManager.findById(deviceScrappingId, BioHospitalDeviceScrappingInfo.class) : new BioHospitalDeviceScrappingInfo();
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        device.setStatus(Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        scrapping.setHospitalDevice(device);
        scrapping.setDeviceCondition(WebUtil.getParamValue(request, "deviceCondition", null));
        int scrappingReason = WebUtil.getParamValueAsInteger(request, "scrappingReason", 0);
        if (scrappingReason != 0) {
            scrapping.setScrappingReason(this.bioDBQueryManager.getReference(scrappingReason, BioLookupScrappingReason.class));
        }
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
        Object[] scrappingReasonReportUrl = FileUtil.uploadFiles(request, "scrappingReasonReportUrl", config);
        scrapping.setScrappingReasonReportUrl(Common.concatenateValues(scrappingReasonReportUrl));
        Object[] scrappingFinalReportUrl = FileUtil.uploadFiles(request, "scrappingFinalReportUrl", config);
        scrapping.setScrappingFinalReportUrl(Common.concatenateValues(scrappingFinalReportUrl));
        scrapping.setScrappingDate(WebUtil.getParamValueAsDate(request, "scrappingDate", null));
        scrapping.setCreatedBy(this.getSessionUser(request));
        scrapping.setCreatedIn(new Date());
        scrapping = this.bioDBQueryManager.mergeEntity(scrapping);
        if (deviceScrappingId == 0) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.DeviceScrappingProcess.addSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.DeviceScrappingProcess.updateSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        }
        this.sendRedirect(request, response, "/bio/ScrappDeviceSearch");
    }
}
