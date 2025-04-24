/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet;

import com.iassets.common.bo.Message;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewJobOrderReport"})
public class InquireJobOrderProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderNo = WebUtil.getParamValue(request, "jobOrderNo", null);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        GenJobOrder order = this.genDBQueryManager.inquireJobOrder(siteId, locationId, orderNo);
        String msg = null;
        if (order == null) {
            msg = "\u0644\u0645 \u064a\u062a\u0645 \u0627\u0644\u0639\u062b\u0648\u0631 \u0639\u0644\u064a \u0623\u064a \u0646\u062a\u0627\u0626\u062c \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643";
        } else if (order.getCancelled().booleanValue()) {
            msg = "\u0623\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u0642\u062f \u062a\u0645 \u0625\u0644\u063a\u0627\u0621\u0647";
        }
        if (msg != null) {
            if (WebUtil.getParamValue(request, "report", null) != null) {
                this.announceErorrMessage(request, response, msg);
            } else {
                this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
                this.basicForward(request, response, "/gen/" + WebUtil.getSearchDisplayURL(request));
            }
            return;
        }
        request.setAttribute("jobOrderInfoObj", (Object)order);
        request.setAttribute("deviceInfoObj", (Object)order.getHospitalDevice());
        this.basicForward(request, response, "/gen/" + this.getSearchDestination(destinations, request));
    }

    static {
        destinations.put("ViewJobOrderReport", "ViewStandardJobOrderReport");
    }
}
