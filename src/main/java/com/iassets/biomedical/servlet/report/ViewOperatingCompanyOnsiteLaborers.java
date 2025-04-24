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

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewOperatingCompanyOnsiteLaborers"})
public class ViewOperatingCompanyOnsiteLaborers
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        request.setAttribute("reportRequested", (Object)true);
        this.viewHTMLReport(request, response, "/bio/report/ViewOperatingCompanyOnsiteLaborers.jsp", Enums.REPORT_TITLE.ONSITE_OPERATING_COMPANY_LABORERS.getReportTitle(langCode));
    }
}
