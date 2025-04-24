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
package com.iassets.general.servlet.joborder;

import com.iassets.biomedical.endpoints.MaintenanceRequestNotificationsEndPoint;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/gen/MaintenanceRequestProcess"})
public class MaintenanceRequestProcessServlet
extends GenBasicServlet {
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
        GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        boolean uncodedDevice = device.getId() == 1;
        GenEndUserMaintenanceRequest maintenanceRequest = new GenEndUserMaintenanceRequest();
        maintenanceRequest.setUncodedDevice(uncodedDevice);
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
        if (uncodedDevice) {
            int depId;
            maintenanceRequest.setUncodedDeviceName(WebUtil.getParamValue(request, "uncodedDeviceName", null));
            int hospLocationId = WebUtil.getParamValueAsInteger(request, "hospLocation", 0);
            if (hospLocationId != 0) {
                maintenanceRequest.setUncodedDeviceLocation(this.genDBQueryManager.getReference(hospLocationId, CommonHospitalLocation.class));
            }
            if ((depId = WebUtil.getParamValueAsInteger(request, "dep", 0).intValue()) != 0) {
                maintenanceRequest.setUncodedDeviceDepartment(this.genDBQueryManager.getReference(depId, CommonHospitalDepartment.class));
            }
            maintenanceRequest.setUncodedDeviceRoom(WebUtil.getParamValue(request, "hospitalRoom", null));
            maintenanceRequest.setUncodedDeviceLocationDescription(WebUtil.getParamValue(request, "hospitalLocationDescription", null));
        } else {
            device.setStatus(Enums.DEVICE_STATUS.IDLE.getStatus());
        }
        maintenanceRequest = this.genDBQueryManager.mergeEntity(maintenanceRequest);
        this.setMessage(request, new Message("\u062a\u0645 \u0625\u0631\u0633\u0627\u0644 \u0627\u0644\u0628\u0644\u0627\u063a \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.mReqNotifier.broadcastNotificationUpdates(sessionSite.getId(), langCode);
        this.sendRedirect(request, response, "/gen/OpenMaintenanceRequestSearch");
    }
}
