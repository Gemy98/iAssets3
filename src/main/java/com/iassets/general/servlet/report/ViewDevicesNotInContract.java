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
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDevicesNotInContract"})
public class ViewDevicesNotInContract
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDevice> deviceList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String jpql = "SELECT h FROM GenHospitalDevice h WHERE h.site.id = ? AND h.status NOT IN (?,?) AND h.withinContract != true";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        if (locationId != 0) {
            jpql = jpql + " AND h.hospital.id = ?";
            map.put(4, locationId);
        }
        if ((deviceList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospital.id, h.subcontractor, h.model, h.name", map, GenHospitalDevice.class)) == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u062c\u0647\u0632\u0629 \u062e\u0627\u0631\u062c \u0627\u0644\u0639\u0642\u062f");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        this.viewPDFReport("rt10.jasper", Enums.REPORT_TITLE.DEVICES_NOT_IN_CONTRACT.getReportTitle(langCode), null, params, request, response);
    }
}
