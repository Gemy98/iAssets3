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
import com.iassets.general.entity.GenHospitalDeviceTransferringInfo;
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
@WebServlet(value={"/gen/DeviceTransferringProcess"})
public class DeviceTransferringProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        GenHospitalDeviceTransferringInfo transferInfo = new GenHospitalDeviceTransferringInfo();
        GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        device.setStatus(Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        transferInfo.setHospitalDevice(device);
        transferInfo.setTransferDate(WebUtil.getParamValueAsDate(request, "transferDate", null));
        transferInfo.setTransferReason(WebUtil.getParamValue(request, "transferReason", null));
        transferInfo.setTransferDestination(WebUtil.getParamValue(request, "transferDestination", null));
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
        Object[] transferReportUrl = FileUtil.uploadFiles(request, "transferReportUrl", config);
        transferInfo.setTransferReportUrl(Common.concatenateValues(transferReportUrl));
        transferInfo.setCreatedBy(this.getSessionUser(request));
        transferInfo.setCreatedIn(new Date());
        transferInfo = this.genDBQueryManager.mergeEntity(transferInfo);
        this.setMessage(request, new Message("\u062a\u0645 \u062a\u0633\u062c\u064a\u0644 \u0628\u064a\u0627\u0646\u0627\u062a \u0627\u0644\u0646\u0642\u0644  \u0623\u0648 \u0627\u0644\u0633\u062d\u0628 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/gen/TransferDeviceSearch");
    }
}
