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

import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewScheduledInternalPPMByMonthNYear"})
public class ViewScheduledInternalPPMByMonthNYear
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDeviceInternalPpmNotificationSchedule> nsList;
        int siteId = this.getSessionSiteId(request);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        int buildingId = WebUtil.getParamValueAsInteger(request, "location", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 1;
        String jpql = "SELECT h FROM GenHospitalDeviceInternalPpmNotificationSchedule h WHERE h.recorded IS NULL AND h.siteId = " + siteId;
        if (month != 0) {
            jpql = jpql + " AND h.plannedMonth = ?";
            map.put(i++, month);
        }
        if (year != 0) {
            jpql = jpql + " AND h.plannedYear = ?";
            map.put(i++, year);
        }
        if (buildingId != 0) {
            jpql = jpql + " AND h.hospitalDevice.hospitalLocation.id = ?";
            map.put(i++, buildingId);
        }
        if ((nsList = this.genDBQueryManager.executeRetriveJPQLWithPositionalParameters(jpql = jpql + " ORDER BY h.plannedDate", map, GenHospitalDeviceInternalPpmNotificationSchedule.class, "GenHospitalDeviceInternalPpmNotificationSchedule.graph.fetchAll")) == null || nsList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u062a\u0648\u062c\u062f \u0635\u064a\u0627\u0646\u0629 \u0648\u0642\u0627\u0626\u064a\u0629 \u0645\u062c\u062f\u0648\u0644\u0629 \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643");
            return;
        }
        request.setAttribute("nsList", nsList);
        this.viewHTMLReport(request, response, "/gen/report/ViewInternalPPMSchedules.jsp", "\u0628\u064a\u0627\u0646 \u0628\u0627\u0644\u0635\u064a\u0627\u0646\u0629 \u0627\u0644\u0648\u0642\u0627\u0626\u064a\u0629 \u0627\u0644\u0645\u062c\u062f\u0648\u0644\u0629 \u062e\u0644\u0627\u0644 \u0634\u0647\u0631 \u0648\u0633\u0646\u0629");
    }
}
