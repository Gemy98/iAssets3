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
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDevicesUnderMaintenance"})
public class ViewDevicesUnderMaintenance
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenJobOrder> jobOrderList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.hospitalDevice.site.id = ? AND j.cancelled = false AND j.closed = false AND j.hospitalDevice.status NOT IN (?,?)";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        if (locationId != 0) {
            jpql = jpql + " AND j.hospitalDevice.hospital.id = ?";
            map.put(4, locationId);
        }
        if ((jobOrderList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY j.hospitalDevice.hospital.id, j.hospitalDevice.hospitalDepartment.name, j.jobOrderDate", map, GenJobOrder.class)) == null || jobOrderList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u062c\u0647\u0632\u0629 \u062a\u062d\u062a \u0627\u0644\u0635\u064a\u0627\u0646\u0629");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jobOrderList", jobOrderList);
        this.viewPDFReport("rt2.jasper", Enums.REPORT_TITLE.DEVICES_UNDER_MAINTENANCE.getReportTitle(langCode), null, params, request, response);
    }
}
