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

import com.iassets.biomedical.entity.BioHospitalDevicePpmDetail;
import com.iassets.biomedical.servlet.BioBasicServlet;
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

@WebServlet(value={"/bio/ViewPPMNotHappenedVisits"})
public class ViewPPMNotHappenedVisits
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BioHospitalDevicePpmDetail> ppmList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int category = WebUtil.getParamValueAsInteger(request, "category", 0);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 0;
        String jpql = "SELECT h FROM BioHospitalDevicePpmDetail h WHERE h.hospitalDevice.site.id = " + siteId + " AND h.hospitalDevice.status NOT IN (" + Enums.DEVICE_STATUS.SCRAPPED.getStatus() + "," + Enums.DEVICE_STATUS.TRANSFERRED.getStatus() + ") AND h.visitStatus = " + Enums.PPM_VISIT_STATUS.NOT_HAPPENED.getId();
        if (locationId != 0) {
            jpql = jpql + " AND h.hospitalDevice.hospital.id  = ?";
            map.put(++i, locationId);
        }
        if (category != 0) {
            jpql = jpql + " AND h.hospitalDevice.category.id = ?";
            map.put(++i, category);
        }
        if (month != 0) {
            jpql = jpql + " AND MONTH(h.visitDate) = ?";
            map.put(++i, month);
        }
        if (year != 0) {
            jpql = jpql + " AND YEAR(h.visitDate) = ?";
            map.put(++i, year);
        }
        if ((ppmList = this.bioDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.visitMonth, h.subcontractor, h.hospitalDevice.category, h.hospitalDevice.name", map, BioHospitalDevicePpmDetail.class)) == null || ppmList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewPPMNotHappenedVisits.msg", this.getSessionLanguage(request)));
            return;
        }
        request.setAttribute("ppmList", ppmList);
        this.viewHTMLReport(request, response, "/bio/report/ViewPPMNotHappenedVisits.jsp", Enums.REPORT_TITLE.NOT_HAPPENED_PPM_VISITS.getReportTitle(langCode));
    }
}
