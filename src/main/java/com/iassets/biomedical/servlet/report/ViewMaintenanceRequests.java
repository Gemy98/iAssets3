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

import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewMaintenanceRequests"})
public class ViewMaintenanceRequests
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BioEndUserMaintenanceRequest> maintenanceRequestList;
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        CommonUser sessionUser = this.getSessionUser(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int department = WebUtil.getParamValueAsInteger(request, "dep", 0);
        int category = WebUtil.getParamValueAsInteger(request, "category", 0);
        int requestApplicant = sessionUserDepartment != null ? sessionUser.getId() : 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 1;
        String jpql = "SELECT b FROM BioEndUserMaintenanceRequest b WHERE b.site.id=? AND  b.fake=false AND b.handled=false";
        map.put(1, siteId);
        if (locationId != 0) {
            jpql = jpql + " AND b.hospitalDevice.hospital.id = ?";
            map.put(++i, locationId);
        }
        if (category != 0) {
            jpql = jpql + " AND b.hospitalDevice.category.id = ?";
            map.put(++i, category);
        }
        if (department != 0) {
            jpql = jpql + " AND b.hospitalDepartment.id = ?";
            map.put(++i, department);
        }
        if (requestApplicant != 0) {
            jpql = jpql + " AND b.requestedBy.id = ?";
            map.put(++i, requestApplicant);
        }
        if ((maintenanceRequestList = this.bioDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY b.damageDate", map, BioEndUserMaintenanceRequest.class)) == null || maintenanceRequestList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewMaintenanceRequests.msg", this.getSessionLanguage(request)));
            return;
        }
        request.setAttribute("maintenanceRequestList", maintenanceRequestList);
        this.viewHTMLReport(request, response, "/bio/report/ViewMaintenanceRequestsList.jsp", Enums.REPORT_TITLE.BIO_MAINTENANCE_REQUEST_LIST.getReportTitle(langCode));
    }
}
