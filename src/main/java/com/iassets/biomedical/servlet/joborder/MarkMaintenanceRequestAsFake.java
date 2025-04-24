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
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
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

@WebServlet(value={"/bio/MarkMaintenanceRequestAsFake"})
public class MarkMaintenanceRequestAsFake
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    MaintenanceRequestNotificationsEndPoint mReqNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        CommonUser sUser = this.getSessionUser(request);
        int mReqId = WebUtil.getParamValueAsInteger(request, "mReqId", 0);
        BioEndUserMaintenanceRequest maintenanceRequest = this.bioDBQueryManager.findById(mReqId, BioEndUserMaintenanceRequest.class);
        if (DataSecurityChecker.maintenanceRequestUpdateBlocked(request, maintenanceRequest, true)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        maintenanceRequest.getHospitalDevice().setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
        maintenanceRequest.setFake(true);
        maintenanceRequest.setHandledBy(sUser);
        maintenanceRequest.setHandledIn(new Date());
        maintenanceRequest = this.bioDBQueryManager.mergeEntity(maintenanceRequest);
        this.mReqNotifier.broadcastNotificationUpdates(sessionSite.getId(), langCode);
        this.sendRedirect(request, response, "/bio/ViewMaintenanceRequests");
    }
}
