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

@WebServlet(value={"/gen/ViewDevicesByCatnDep", "/gen/ViewDevicesStatus"})
public class ViewDevicesByCatnDep
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDevice> deviceList;
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int department = sessionUserDepartment != null ? sessionUserDepartment.getId() : WebUtil.getParamValueAsInteger(request, "dep", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 3;
        String jpql = "SELECT h FROM GenHospitalDevice h WHERE h.site.id = ? AND h.status NOT IN (?,?)";
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        if (locationId != 0) {
            jpql = jpql + " AND h.hospital.id = ?";
            map.put(++i, locationId);
        }
        if (department != 0) {
            jpql = jpql + " AND h.hospitalDepartment.id = ?";
            map.put(++i, department);
        }
        if ((deviceList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospital.id, h.hospitalDepartment.name, h.name, h.status", map, GenHospitalDevice.class)) == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u062c\u0647\u0632\u0629 \u0628\u064a\u0627\u0646\u0627\u062a\u0647\u0627 \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0644\u0642\u0633\u0645 \u0648\u0627\u0644\u0641\u0626\u0629 \u0627\u0644\u062a\u064a \u062a\u0645 \u0627\u062e\u062a\u064a\u0627\u0631\u0647\u0645\u0627");
            return;
        }
        String uri = WebUtil.getRequestUriLastSegment(request);
        boolean viewDevicesStatus = uri.contains("ViewDevicesStatus");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        if (viewDevicesStatus) {
            this.viewPDFReport("rt1-2.jasper", Enums.REPORT_TITLE.HOSPITAL_DEVICES_STATUS.getReportTitle(langCode), null, params, request, response);
        } else {
            this.viewPDFReport("rt1.jasper", Enums.REPORT_TITLE.HOSPITAL_DEVICES.getReportTitle(langCode), null, params, request, response);
        }
    }
}
