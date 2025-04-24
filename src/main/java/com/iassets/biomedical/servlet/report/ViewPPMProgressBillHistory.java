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

import com.iassets.biomedical.entity.BioPPMProgressBill;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewPPMProgressBillHistory"})
public class ViewPPMProgressBillHistory
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        List<BioPPMProgressBill> pbs = this.bioDBQueryManager.getPPMProgressBillList(siteId);
        if (pbs == null || pbs.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewPPMProgressBillHistory.msg", langCode));
            return;
        }
        request.setAttribute("ppmProgressBillList", pbs);
        this.viewHTMLReport(request, response, "/bio/report/PPMProgressBillHistory.jsp", Enums.REPORT_TITLE.PROGRESS_BILL_PPM.getReportTitle(langCode));
    }
}
