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
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevicePpmDetail;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewPPMVisitsDetails"})
public class ViewPPMVisitsDetails
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDevicePpmDetail> ppmList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 0;
        String jpql = "SELECT h FROM GenHospitalDevicePpmDetail h WHERE h.hospitalDevice.site.id = " + siteId + " AND h.hospitalDevice.status NOT IN(" + Enums.DEVICE_STATUS.SCRAPPED.getStatus() + "," + Enums.DEVICE_STATUS.TRANSFERRED.getStatus() + ") AND h.visitStatus != " + Enums.PPM_VISIT_STATUS.NOT_HAPPENED.getId();
        if (locationId != 0) {
            jpql = jpql + " AND h.hospitalDevice.hospital.id  = ?";
            map.put(++i, locationId);
        }
        if (month != 0) {
            jpql = jpql + " AND MONTH(h.visitDate) = ?";
            map.put(++i, month);
        }
        if (year != 0) {
            jpql = jpql + " AND YEAR(h.visitDate) = ?";
            map.put(++i, year);
        }
        if ((ppmList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.visitDate, h.subcontractor, h.hospitalDevice.name", map, GenHospitalDevicePpmDetail.class)) == null || ppmList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0645 \u064a\u062a\u0645 \u0625\u062f\u062e\u0627\u0644 \u0645\u0639\u0644\u0648\u0645\u0627\u062a \u0632\u064a\u0627\u0631\u0627\u062a \u0627\u0644\u0648\u0643\u064a\u0644 \u0644\u0644\u0623\u062c\u0647\u0632\u0629 \u0627\u0644\u0645\u0637\u0644\u0648\u0628\u0629 \u0641\u064a \u0627\u0644\u0632\u0645\u0646 \u0627\u0644\u0630\u064a \u062a\u0645 \u062a\u062d\u062f\u064a\u062f\u0647");
            return;
        }
        request.setAttribute("ppmList", ppmList);
        this.viewHTMLReport(request, response, "/gen/report/ViewPPMVisitsDetails.jsp", Enums.REPORT_TITLE.PPM_ATTACHED_FILES.getReportTitle(langCode));
    }
}
