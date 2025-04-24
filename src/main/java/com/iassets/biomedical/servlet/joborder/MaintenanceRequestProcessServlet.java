/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.endpoints.MaintenanceRequestNotificationsEndPoint;
import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/bio/MaintenanceRequestProcess"})
public class MaintenanceRequestProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    MaintenanceRequestNotificationsEndPoint mReqNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        CommonUser sUser = this.getSessionUser(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, true)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        device.setStatus(Enums.DEVICE_STATUS.IDLE.getStatus());
        BioEndUserMaintenanceRequest maintenanceRequest = new BioEndUserMaintenanceRequest();
        maintenanceRequest.setSite(sessionSite);
        maintenanceRequest.setHospitalDevice(device);
        maintenanceRequest.setHospitalDepartment(device.getHospitalDepartment());
        maintenanceRequest.setDamageDate(WebUtil.getParamValueAsDate(request, "damageDate", null));
        maintenanceRequest.setDamageDescription(WebUtil.getParamValue(request, "damageDescription", null));
        maintenanceRequest.setEndUserName(WebUtil.getParamValue(request, "endUserName", null));
        maintenanceRequest.setFake(false);
        maintenanceRequest.setHandled(false);
        maintenanceRequest.setRequestedBy(sUser);
        maintenanceRequest.setRequestedIn(new Date());
        maintenanceRequest = this.bioDBQueryManager.mergeEntity(maintenanceRequest);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.MaintenanceRequestProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.mReqNotifier.broadcastNotificationUpdates(sessionSite.getId(), langCode);
        this.sendRedirect(request, response, "/bio/OpenMaintenanceRequestSearch");
    }
}
