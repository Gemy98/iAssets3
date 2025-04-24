/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.siteadministration;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewOperatingCompanyOnsiteLaborers_Segment"})
public class ViewOperatingCompanyOnsiteLaborers_Segment
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean excludeSiteManager = request.getParameter("excludeSiteManager") != null;
        request.setAttribute("operatingCompanyLaborers", this.bioDBQueryManager.getOnsiteOperatingCompanyLabors(this.getSessionSiteId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, excludeSiteManager));
        this.include(request, response, "/bio/siteadministration/ViewOperatingCompanyOnsiteLaborers_Segment.jsp");
    }
}
