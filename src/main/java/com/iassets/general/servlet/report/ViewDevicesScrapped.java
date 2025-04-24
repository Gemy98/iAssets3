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

import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDeviceScrappingInfo;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDevicesScrapped"})
public class ViewDevicesScrapped
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDeviceScrappingInfo> list;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String jpql = "SELECT h FROM GenHospitalDeviceScrappingInfo h WHERE h.hospitalDevice.site.id = ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        if (locationId != 0) {
            jpql = jpql + " AND h.hospitalDevice.hospital.id = ?";
            map.put(2, locationId);
        }
        if ((list = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospitalDevice.hospital.id, h.hospitalDevice.hospitalDepartment.name, h.hospitalDevice.name", map, GenHospitalDeviceScrappingInfo.class)) == null || list.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0645 \u064a\u062a\u0645  \u062a\u0633\u062c\u064a\u0644 \u0645\u0639\u0644\u0648\u0645\u0627\u062a \u062a\u0643\u0647\u064a\u0646 \u0623\u064a \u062c\u0647\u0627\u0632 \u0645\u0646 \u062e\u0644\u0627\u0644 \u0647\u0630\u0627 \u0627\u0644\u0646\u0638\u0627\u0645");
            return;
        }
        request.setAttribute("scrappingInfoList", list);
        this.viewHTMLReport(request, response, "/gen/report/ViewDevicesScrapped.jsp", Enums.REPORT_TITLE.SCRAPPED_DEVICES.getReportTitle(langCode));
    }
}
