/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report;

import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.entity.GenSpInventoryTransaction;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="GenViewSpecificSPInventoryTransactions", urlPatterns={"/gen/ViewSpecificSPInventoryTransactions"})
public class ViewSpecificSPInventoryTransactions
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        GenSpInventoryContent spInventoryContent = (GenSpInventoryContent)request.getAttribute("spCategoryObj");
        String jpql = "SELECT b FROM GenSpInventoryTransaction b WHERE b.siteId=? AND b.spInventoryContentId=? ORDER BY b.id DESC";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, spInventoryContent.getId());
        List<GenSpInventoryTransaction> transList = this.genDBQueryManager.queryJPQL(jpql, map, GenSpInventoryTransaction.class);
        if (transList == null || transList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u062d\u0631\u0643\u0627\u062a \u0645\u062e\u0632\u0646\u064a\u0629 \u0644\u0644\u0635\u0646\u0641 \u0627\u0644\u0645\u0637\u0644\u0648\u0628");
            return;
        }
        request.setAttribute("transList", transList);
        this.viewHTMLReport(request, response, "/gen/report/ViewSpecificSPInventoryTransactions.jsp", "\u0628\u064a\u0627\u0646 \u0628\u0627\u0644\u062d\u0631\u0643\u0627\u062a \u0627\u0644\u0645\u062e\u0632\u0646\u064a\u0629 \u0644\u0642\u0637\u0639\u0629 \u063a\u064a\u0627\u0631 \u0628\u0627\u0644\u0645\u0633\u062a\u0648\u062f\u0639");
    }
}
