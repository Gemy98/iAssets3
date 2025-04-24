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

import com.iassets.biomedical.entity.BioSpInventoryContent;
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

@WebServlet(value={"/bio/UpdateSPCategory", "/bio/ViewSpecificSPInventoryTransactionsReport"})
public class SearchForSPCategoryProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BioSpInventoryContent spInventoryContent = this.bioDBQueryManager.getSpInventoryContentWithStats(this.getSessionSiteId(request), WebUtil.getParamValue(request, "code", null));
        String msg = null;
        if (spInventoryContent == null) {
            msg = LocalizationManager.getLiteral("servlet.SearchForSPCategoryProcess.msg", this.getSessionLanguage(request));
        }
        if (msg == null) {
            request.setAttribute("spCategoryObj", (Object)spInventoryContent);
            this.basicForward(request, response, "/bio/" + this.getSearchDestination(destinations, request));
            return;
        }
        if (WebUtil.getParamValue(request, "report", null) != null) {
            this.announceErorrMessage(request, response, msg);
        } else {
            this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/bio/" + WebUtil.getSearchDisplayURL(request));
        }
    }

    static {
        destinations.put("UpdateSPCategory", "UpdateSPCategoryDisplay");
        destinations.put("ViewSpecificSPInventoryTransactionsReport", "ViewSpecificSPInventoryTransactions");
    }
}
