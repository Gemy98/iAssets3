/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet;

import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/CancelJobOrder", "/bio/FollowupJobOrder"})
public class SearchForJobOrderProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int locationId;
        String orderNo = WebUtil.getParamValue(request, "jobOrderNo", null);
        String sn = WebUtil.getParamValue(request, "deviceSerial", null);
        String code = WebUtil.getParamValue(request, "deviceCode", null);
        int siteId = this.getSessionSiteId(request);
        BioJobOrder order = this.bioDBQueryManager.searchForActiveJobOrder(siteId, locationId = this.getSessionLocationId(request), orderNo, code, sn);
        if (order != null) {
            request.setAttribute("jobOrderInfoObj", (Object)order);
            request.setAttribute("deviceInfoObj", (Object)order.getHospitalDevice());
            request.setAttribute("mReqInfoObj", (Object)order.getMaintenanceRequest());
            this.basicForward(request, response, "/bio/" + this.getSearchDestination(destinations, request));
            return;
        }
        String msg = LocalizationManager.getLiteral("servlet.SearchForJobOrderProcess.msg", this.getSessionLanguage(request));
        if (WebUtil.getParamValue(request, "report", null) != null) {
            this.announceErorrMessage(request, response, msg);
        } else {
            this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/bio/" + WebUtil.getSearchDisplayURL(request));
        }
    }

    static {
        destinations.put("CancelJobOrder", "JobOrderDeleteDisplay");
        destinations.put("FollowupJobOrder", "JobOrderTrackingDisplay");
        destinations.put("JobOrderFollowupFirstPhaseProcess", "JobOrderTrackingDisplay");
        destinations.put("JobOrderFollowupSecondPhaseProcess", "JobOrderTrackingDisplay");
        destinations.put("JobOrderFollowupThirdPhaseProcess", "JobOrderTrackingDisplay");
    }
}
