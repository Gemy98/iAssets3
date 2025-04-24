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
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDevicesNeedPPMVisit"})
public class ViewDevicesNeedPPMVisit
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDevice> deviceList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 1;
        String jpql = "SELECT h FROM GenHospitalDevice h WHERE h.status NOT IN(" + Enums.DEVICE_STATUS.SCRAPPED.getStatus() + "," + Enums.DEVICE_STATUS.TRANSFERRED.getStatus() + ") AND h.site.id = " + siteId;
        if (locationId != 0) {
            jpql = jpql + " AND h.hospital.id = ?";
            map.put(i++, locationId);
        }
        if (month != 0) {
            jpql = jpql + " AND h.pmVisitsMonths LIKE ?";
            map.put(i++, "%$" + month + "$" + "%");
        }
        if ((deviceList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospital.id, h.hospitalDepartment.name, h.name", map, GenHospitalDevice.class)) == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u062a\u0648\u062c\u062f \u0623\u062c\u0647\u0632\u0629  \u0645\u0646 \u0627\u0644\u0641\u0626\u0629 \u0627\u0644\u0645\u062f\u062e\u0644\u0629 \u062a\u062d\u062a\u0627\u062c \u0632\u064a\u0627\u0631\u0629 \u0648\u0643\u064a\u0644 \u062e\u0644\u0627\u0644 \u0634\u0647\u0631 " + Enums.YEAR_MONTHS.getNameById("" + month, langCode));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        params.put("month", month);
        this.viewPDFReport("rt8.jasper", Enums.REPORT_TITLE.DEVICES_NEED_PPM_VISIT.getReportTitle(langCode), null, params, request, response);
    }
}
