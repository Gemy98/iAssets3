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

import com.iassets.biomedical.entity.BioHospitalDeviceTransferringInfo;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDevicesTransferred"})
public class ViewDevicesTransferred
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BioHospitalDeviceTransferringInfo> list;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String jpql = "SELECT h FROM BioHospitalDeviceTransferringInfo h WHERE h.hospitalDevice.site.id = ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        if (locationId != 0) {
            jpql = jpql + " AND h.hospitalDevice.hospital.id = ?";
            map.put(2, locationId);
        }
        if ((list = this.bioDBQueryManager.queryJPQL(jpql = jpql + " ORDER BY h.hospitalDevice.hospital.id, h.hospitalDevice.hospitalDepartment.id, h.hospitalDevice.category.id, h.hospitalDevice.name", map, BioHospitalDeviceTransferringInfo.class)) == null || list.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDevicesTransferred.msg", this.getSessionLanguage(request)));
            return;
        }
        request.setAttribute("transferringInfoList", list);
        this.viewHTMLReport(request, response, "/bio/report/ViewDevicesTransferred.jsp", Enums.REPORT_TITLE.TRANSFERRED_DEVICES.getReportTitle(langCode));
    }
}
