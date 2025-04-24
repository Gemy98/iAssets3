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

import com.iassets.biomedical.entity.BioSparePartsProgressBill;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewSparePartsProgressBillHistory"})
public class ViewSparePartsProgressBillHistory
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        List<BioSparePartsProgressBill> pbs = this.bioDBQueryManager.getSparePartsProgressBillList(siteId);
        if (pbs == null || pbs.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewSparePartsProgressBillHistory.msg", langCode));
            return;
        }
        request.setAttribute("lastSpPbInfoObj", (Object)this.bioDBQueryManager.getLastSparePartsProgressBill(siteId, 0));
        request.setAttribute("sparePartsProgressBillList", pbs);
        this.viewHTMLReport(request, response, "/bio/report/SparePartsProgressBillHistory.jsp", Enums.REPORT_TITLE.PROGRESS_BILL_SPARE_PARTS.getReportTitle(langCode));
    }
}
