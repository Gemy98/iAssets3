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

import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.endpoints.MaintenanceRequestNotificationsEndPoint;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/gen/MarkMaintenanceRequestAsFake"})
public class MarkMaintenanceRequestAsFake
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    MaintenanceRequestNotificationsEndPoint mReqNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        CommonUser sUser = this.getSessionUser(request);
        int mReqId = WebUtil.getParamValueAsInteger(request, "mReqId", 0);
        GenEndUserMaintenanceRequest maintenanceRequest = this.genDBQueryManager.findById(mReqId, GenEndUserMaintenanceRequest.class);
        if (DataSecurityChecker.maintenanceRequestUpdateBlocked(request, maintenanceRequest, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        if (!maintenanceRequest.isUncodedDevice()) {
            maintenanceRequest.getHospitalDevice().setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
        }
        maintenanceRequest.setFake(true);
        maintenanceRequest.setHandledBy(sUser);
        maintenanceRequest.setHandledIn(new Date());
        maintenanceRequest = this.genDBQueryManager.mergeEntity(maintenanceRequest);
        this.mReqNotifier.broadcastNotificationUpdates(sessionSite.getId());
        this.sendRedirect(request, response, "/gen/ViewMaintenanceRequests");
    }
}
