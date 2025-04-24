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

@WebServlet(value={"/bio/ViewJobOrderReport"})
public class InquireJobOrderProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderNo = WebUtil.getParamValue(request, "jobOrderNo", null);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        BioJobOrder order = this.bioDBQueryManager.inquireJobOrder(siteId, locationId, orderNo);
        String msg = null;
        if (order == null) {
            msg = LocalizationManager.getLiteral("servlet.InquireJobOrderProcess.msg1", this.getSessionLanguage(request));
        } else if (order.getCancelled().booleanValue()) {
            msg = LocalizationManager.getLiteral("servlet.InquireJobOrderProcess.msg2", this.getSessionLanguage(request));
        }
        if (msg != null) {
            if (WebUtil.getParamValue(request, "report", null) != null) {
                this.announceErorrMessage(request, response, msg);
            } else {
                this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
                this.basicForward(request, response, "/bio/" + WebUtil.getSearchDisplayURL(request));
            }
            return;
        }
        request.setAttribute("jobOrderInfoObj", (Object)order);
        request.setAttribute("deviceInfoObj", (Object)order.getHospitalDevice());
        this.basicForward(request, response, "/bio/" + this.getSearchDestination(destinations, request));
    }

    static {
        destinations.put("ViewJobOrderReport", "ViewStandardJobOrderReport");
    }
}
