/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.entity.BioSpInventoryTransaction;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="BioViewSpecificSPInventoryTransactions", urlPatterns={"/bio/ViewSpecificSPInventoryTransactions"})
public class ViewSpecificSPInventoryTransactions
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        BioSpInventoryContent spInventoryContent = (BioSpInventoryContent)request.getAttribute("spCategoryObj");
        String jpql = "SELECT b FROM BioSpInventoryTransaction b WHERE b.siteId=? AND b.spInventoryContentId=? ORDER BY b.id DESC";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, spInventoryContent.getId());
        List<BioSpInventoryTransaction> transList = this.bioDBQueryManager.queryJPQL(jpql, map, BioSpInventoryTransaction.class);
        if (transList == null || transList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewSpecificSPInventoryTransactions.msg1", this.getSessionLanguage(request)));
            return;
        }
        request.setAttribute("transList", transList);
        this.viewHTMLReport(request, response, "/bio/report/ViewSpecificSPInventoryTransactions.jsp", LocalizationManager.getLiteral("servlet.ViewSpecificSPInventoryTransactions.msg2", this.getSessionLanguage(request)));
    }
}
