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

import com.iassets.biomedical.endpoints.JobOrderNotificationsEndPoint;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.Message;
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

@WebServlet(value={"/bio/JobOrderDeleteProcess"})
public class JobOrderDeleteProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    JobOrderNotificationsEndPoint joNotifier;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int jobOrderId = WebUtil.getParamValueAsInteger(request, "jobOrderId", 0);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0 || jobOrderId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        BioJobOrder order = this.bioDBQueryManager.findById(jobOrderId, BioJobOrder.class);
        if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        BioHospitalDevice device = order.getHospitalDevice();
        device.setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
        order.setActualCancelDate(WebUtil.getParamValueAsDate(request, "actualCancelDate", null));
        order.setCancelReason(WebUtil.getParamValue(request, "cancelReason", null));
        order.setCancelled(true);
        order.setCancelledBy(this.getSessionUser(request));
        order.setCancelledIn(new Date());
        order = this.bioDBQueryManager.mergeEntity(order);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.JobOrderDeleteProcess.deleteSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.joNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request), langCode);
        this.sendRedirect(request, response, "/bio/CancelJobOrderSearch");
    }
}
