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

import com.iassets.common.bo.Message;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.endpoints.JobOrderNotificationsEndPoint;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/gen/JobOrderDeleteProcess"})
public class JobOrderDeleteProcessServlet
extends GenBasicServlet {
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
        GenJobOrder order = this.genDBQueryManager.findById(jobOrderId, GenJobOrder.class);
        if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        if (!order.isUncodedDevice()) {
            GenHospitalDevice device = order.getHospitalDevice();
            device.setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
            device = this.genDBQueryManager.mergeEntity(device);
        }
        order.setActualCancelDate(WebUtil.getParamValueAsDate(request, "actualCancelDate", null));
        order.setCancelReason(WebUtil.getParamValue(request, "cancelReason", null));
        order.setCancelled(true);
        order.setCancelledBy(this.getSessionUser(request));
        order.setCancelledIn(new Date());
        order = this.genDBQueryManager.mergeEntity(order);
        this.setMessage(request, new Message("\u062a\u0645 \u062d\u0630\u0641 \u0623\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.joNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request), this.getSessionUser(request).getGmpCategory());
        this.sendRedirect(request, response, "/gen/CancelJobOrderSearch");
    }
}
