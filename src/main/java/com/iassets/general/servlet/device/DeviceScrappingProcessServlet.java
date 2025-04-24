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
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceScrappingInfo;
import com.iassets.general.entity.GenLookupScrappingReason;
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
@WebServlet(value={"/gen/DeviceScrappingProcess"})
public class DeviceScrappingProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        int deviceScrappingId = WebUtil.getParamValueAsInteger(request, "deviceScrappingId", 0);
        GenHospitalDeviceScrappingInfo scrapping = null;
        scrapping = deviceScrappingId != 0 ? this.genDBQueryManager.findById(deviceScrappingId, GenHospitalDeviceScrappingInfo.class) : new GenHospitalDeviceScrappingInfo();
        GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        device.setStatus(Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        scrapping.setHospitalDevice(device);
        scrapping.setDeviceCondition(WebUtil.getParamValue(request, "deviceCondition", null));
        int scrappingReason = WebUtil.getParamValueAsInteger(request, "scrappingReason", 0);
        if (scrappingReason != 0) {
            scrapping.setScrappingReason(this.genDBQueryManager.getReference(scrappingReason, GenLookupScrappingReason.class));
        }
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
        Object[] scrappingReasonReportUrl = FileUtil.uploadFiles(request, "scrappingReasonReportUrl", config);
        scrapping.setScrappingReasonReportUrl(Common.concatenateValues(scrappingReasonReportUrl));
        Object[] scrappingFinalReportUrl = FileUtil.uploadFiles(request, "scrappingFinalReportUrl", config);
        scrapping.setScrappingFinalReportUrl(Common.concatenateValues(scrappingFinalReportUrl));
        scrapping.setScrappingDate(WebUtil.getParamValueAsDate(request, "scrappingDate", null));
        scrapping.setCreatedBy(this.getSessionUser(request));
        scrapping.setCreatedIn(new Date());
        scrapping = this.genDBQueryManager.mergeEntity(scrapping);
        if (deviceScrappingId == 0) {
            this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062a\u0643\u0647\u064a\u0646 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        } else {
            this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062a\u0639\u062f\u064a\u0644 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        }
        this.sendRedirect(request, response, "/gen/ScrappDeviceSearch");
    }
}
