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
import com.iassets.biomedical.entity.BioHospitalDeviceTransferringInfo;
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
@WebServlet(value={"/bio/DeviceTransferringProcess"})
public class DeviceTransferringProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        BioHospitalDeviceTransferringInfo transferInfo = new BioHospitalDeviceTransferringInfo();
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
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
        transferInfo = this.bioDBQueryManager.mergeEntity(transferInfo);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.DeviceTransferringProcess.successProcess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/TransferDeviceSearch");
    }
}
