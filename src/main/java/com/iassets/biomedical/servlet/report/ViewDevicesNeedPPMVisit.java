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

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonHospitalDepartment;
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

@WebServlet(value={"/bio/ViewDevicesNeedPPMVisit"})
public class ViewDevicesNeedPPMVisit
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        Integer siteId = this.getSessionSiteId(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        int locationId = this.getSessionLocationId(request);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        Integer deviceStatus = WebUtil.getParamValueAsInteger(request, "deviceStatus", null);
        jpqlParams.put("month", month);
        jpqlParams.put("deviceStatus", deviceStatus);
        List<BioHospitalDevice> deviceList = this.bioDBQueryManager.searchDevicesForNeedPPMVisits(siteId, locationId, jpqlParams);
        if (deviceList == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDevicesNeedPPMVisit.msg", this.getSessionLanguage(request)) + Enums.YEAR_MONTHS.getNameById("" + month, langCode));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        params.put("month", month);
        this.viewPDFReport("rt8.jasper", Enums.REPORT_TITLE.DEVICES_NEED_PPM_VISIT.getReportTitle(langCode), null, params, request, response);
    }
}
