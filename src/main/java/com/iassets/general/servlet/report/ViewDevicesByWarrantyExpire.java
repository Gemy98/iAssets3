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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDevicesByWarrantyExpire"})
public class ViewDevicesByWarrantyExpire
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GenHospitalDevice> deviceList;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        Date from = WebUtil.getParamValueAsDate(request, "pFrom", null);
        Date to = WebUtil.getParamValueAsDate(request, "pTo", null);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        String jpql = "SELECT h FROM GenHospitalDevice h WHERE h.site.id = ? AND h.status NOT IN (?,?)  AND h.warrantyExpireDate >= ?";
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        map.put(4, from);
        int i = 5;
        if (locationId != 0) {
            jpql = jpql + " AND h.hospital.id = ?";
            map.put(i++, locationId);
        }
        if (to != null) {
            jpql = jpql + " AND h.warrantyExpireDate <=?";
            map.put(i++, to);
        }
        if ((deviceList = this.genDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospital.id, h.hospitalDepartment.name, h.warrantyExpireDate", map, GenHospitalDevice.class)) == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u062c\u0647\u0632\u0629 \u0633\u064a\u0646\u062a\u0647\u064a \u0636\u0645\u0627\u0646\u0647\u0627 \u062e\u0644\u0627\u0644 \u0627\u0644\u0641\u062a\u0631\u0629 \u0627\u0644\u062a\u064a \u062a\u0645 \u062a\u062d\u062f\u064a\u062f\u0647\u0627");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        this.viewPDFReport("rt6.jasper", Enums.REPORT_TITLE.DEVICE_BY_WARRANTY_EXPIRE.getReportTitle(langCode), null, params, request, response);
    }
}
