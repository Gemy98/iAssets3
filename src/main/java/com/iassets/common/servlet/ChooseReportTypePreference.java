/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet;

import com.iassets.common.entity.CommonSite;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDevicesUnderMaintenanceAlias", "/bio/ViewDevicesNotInContractAlias", "/bio/ViewDevicesFromOtherSitesAlias", "/bio/ViewDevicesTransferredAlias", "/bio/ViewDevicesScrappedAlias", "/bio/ViewPPMNotHappenedVisitsAlias", "/bio/ViewOpenedJobOrdersAlias", "/bio/ViewLateJobOrdersAlias", "/bio/ViewOpenedJobOrdersOutsideWarrantyAlias", "/bio/ViewLateJobOrdersOutsideWarrantyAlias", "/bio/ViewOpenedJobOrdersWithinWarrantyAlias", "/bio/ViewLateJobOrdersWithinWarrantyAlias", "/gen/ViewDevicesUnderMaintenanceAlias", "/gen/ViewDevicesNotInContractAlias", "/gen/ViewDevicesFromOtherSitesAlias", "/gen/ViewDevicesTransferredAlias", "/gen/ViewDevicesScrappedAlias", "/gen/ViewPPMNotHappenedVisitsAlias", "/gen/ViewOpenedJobOrdersAlias", "/gen/ViewLateJobOrdersAlias"})
public class ChooseReportTypePreference
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dest = destinations.get(WebUtil.getRequestUriLastSegment(request));
        CommonSite sessionSite = this.getSessionSite(request);
        if (sessionSite.getContainsSeveralLocations().booleanValue()) {
            request.setAttribute("formAction", (Object)(this.getCurrentlyActiveAppDir(request) + "/" + dest));
            this.forward(request, response, "/ChooseReportTypePreferenceForm.jsp");
        } else {
            this.basicForward(request, response, "/" + this.getCurrentlyActiveAppDir(request) + "/" + dest);
        }
    }

    static {
        destinations.put("ViewDevicesUnderMaintenanceAlias", "ViewDevicesUnderMaintenance");
        destinations.put("ViewDevicesNotInContractAlias", "ViewDevicesNotInContract");
        destinations.put("ViewDevicesFromOtherSitesAlias", "ViewDevicesFromOtherSites");
        destinations.put("ViewDevicesTransferredAlias", "ViewDevicesTransferred");
        destinations.put("ViewDevicesScrappedAlias", "ViewDevicesScrapped");
        destinations.put("ViewPPMNotHappenedVisitsAlias", "ViewPPMNotHappenedVisits");
        destinations.put("ViewLateJobOrdersAlias", "ViewLateJobOrders");
        destinations.put("ViewOpenedJobOrdersAlias", "ViewOpenedJobOrders");
        destinations.put("ViewLateJobOrdersWithinWarrantyAlias", "ViewLateJobOrdersWithinWarranty");
        destinations.put("ViewLateJobOrdersOutsideWarrantyAlias", "ViewLateJobOrdersOutsideWarranty");
        destinations.put("ViewOpenedJobOrdersWithinWarrantyAlias", "ViewOpenedJobOrdersWithinWarranty");
        destinations.put("ViewOpenedJobOrdersOutsideWarrantyAlias", "ViewOpenedJobOrdersOutsideWarranty");
    }
}
