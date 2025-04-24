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

import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewMaintenanceRequests"})
public class ViewMaintenanceRequests
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenEndUserMaintenanceRequest> maintenanceRequestList;
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        CommonUser sessionUser = this.getSessionUser(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int department = WebUtil.getParamValueAsInteger(request, "dep", 0);
        int requestApplicant = sessionUserDepartment != null ? sessionUser.getId() : 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 1;
        String jpql = "SELECT b FROM GenEndUserMaintenanceRequest b WHERE b.site.id=? AND  b.fake=false AND b.handled=false";
        map.put(1, siteId);
        if (locationId != 0) {
            jpql = jpql + " AND (b.hospitalDevice.hospital.id = ? OR b.uncodedDevice = true) ";
            map.put(++i, locationId);
        }
        if (department != 0) {
            jpql = jpql + " AND (b.hospitalDepartment.id = ? OR b.uncodedDeviceDepartment.id = ?)";
            map.put(++i, department);
        }
        if (requestApplicant != 0) {
            jpql = jpql + " AND b.requestedBy.id = ?";
            map.put(++i, requestApplicant);
        }
        if ((maintenanceRequestList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY b.damageDate", map, GenEndUserMaintenanceRequest.class)) == null || maintenanceRequestList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u0637\u0644\u0628\u0627\u062a \u0635\u064a\u0627\u0646\u0629  \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643");
            return;
        }
        request.setAttribute("maintenanceRequestList", maintenanceRequestList);
        this.viewHTMLReport(request, response, "/gen/report/ViewMaintenanceRequestsList.jsp", Enums.REPORT_TITLE.GEN_MAINTENANCE_REQUEST_LIST.getReportTitle(langCode));
    }
}
