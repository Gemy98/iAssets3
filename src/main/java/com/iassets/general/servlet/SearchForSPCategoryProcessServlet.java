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
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/UpdateSPCategory", "/gen/ViewSpecificSPInventoryTransactionsReport"})
public class SearchForSPCategoryProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GenSpInventoryContent spInventoryContent = this.genDBQueryManager.getSpInventoryContentWithStats(this.getSessionSiteId(request), WebUtil.getParamValue(request, "code", null));
        String msg = null;
        if (spInventoryContent == null) {
            msg = "\u0644\u0645 \u064a\u062a\u0645 \u0627\u0644\u0639\u062b\u0648\u0631 \u0639\u0644\u064a \u0623\u064a \u0646\u062a\u0627\u0626\u062c \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643";
        }
        if (msg == null) {
            request.setAttribute("spCategoryObj", (Object)spInventoryContent);
            this.basicForward(request, response, "/gen/" + this.getSearchDestination(destinations, request));
            return;
        }
        if (WebUtil.getParamValue(request, "report", null) != null) {
            this.announceErorrMessage(request, response, msg);
        } else {
            this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/gen/" + WebUtil.getSearchDisplayURL(request));
        }
    }

    static {
        destinations.put("UpdateSPCategory", "UpdateSPCategoryDisplay");
        destinations.put("ViewSpecificSPInventoryTransactionsReport", "ViewSpecificSPInventoryTransactions");
    }
}
