/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report.filter;

import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewScheduledInternalPPMByMonthNYearFilterDisplay"})
public class ViewScheduledInternalPPMByMonthNYearFilterDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CommonHospitalLocation> siteLocationList = this.genDBQueryManager.getSiteLocations(this.getSessionSiteId(request), this.getSessionLanguage(request));
        request.setAttribute("siteLocationList", siteLocationList);
        this.forward(request, response, "/gen/report/filter/ViewScheduledInternalPPMByMonthNYearFilter.jsp");
    }
}
