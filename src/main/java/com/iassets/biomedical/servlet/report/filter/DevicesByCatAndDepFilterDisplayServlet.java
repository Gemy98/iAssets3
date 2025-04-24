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
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDevicesByCatnDepSearch", "/bio/ViewMaintenanceRequestsSearch", "/bio/ViewDevicesNotInContractSearch"})
public class DevicesByCatAndDepFilterDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.include(request, response, "/bio/CommonDeviceFilterDisplay");
        String filterPage = "/bio/report/filter/DevicesByDepAndCatFilter.jsp";
        String uri = request.getRequestURI();
        Boolean showAgentAndSubcontractor = Boolean.TRUE;
        Boolean showCategoryAndClassification = Boolean.TRUE;
        Boolean showDepartmentAndLocation = Boolean.TRUE;
        Boolean showDeviceName = Boolean.TRUE;
        Boolean showManufacturerNameAndModel = Boolean.TRUE;
        if (!uri.contains("ViewDevicesByCatnDepSearch") && !uri.contains("ViewMaintenanceRequestsSearch") && uri.contains("ViewDevicesNotInContractSearch")) {
            showDepartmentAndLocation = Boolean.FALSE;
        }
        request.setAttribute("showAgentAndSubcontractor", (Object)showAgentAndSubcontractor);
        request.setAttribute("showCategoryAndClassification", (Object)showCategoryAndClassification);
        request.setAttribute("showDepartmentAndLocation", (Object)showDepartmentAndLocation);
        request.setAttribute("showDeviceName", (Object)showDeviceName);
        request.setAttribute("showManufacturerNameAndModel", (Object)showManufacturerNameAndModel);
        this.forward(request, response, filterPage);
    }
}
