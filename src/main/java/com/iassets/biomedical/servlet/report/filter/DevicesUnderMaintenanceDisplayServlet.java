/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report.filter;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonSite;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/DevicesUnderMaintenanceSearch"})
public class DevicesUnderMaintenanceDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.include(request, response, "/bio/CommonDeviceFilterDisplay");
        CommonSite commonSite = this.getSessionSite(request);
        Date contractStartDate = commonSite.getBioContractStartDate();
        Date contractEndDate = commonSite.getBioContractEndDate();
        request.setAttribute("contractStartDate", (Object)contractStartDate);
        request.setAttribute("contractEndDate", (Object)contractEndDate);
        this.forward(request, response, "/bio/report/filter/DevicesUnderMaintenanceFilter.jsp");
    }
}
