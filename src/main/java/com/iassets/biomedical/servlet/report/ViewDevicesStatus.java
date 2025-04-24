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

@WebServlet(value={"/bio/ViewDevicesStatus"})
public class ViewDevicesStatus
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        Integer siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        Integer deviceStatus = WebUtil.getParamValueAsInteger(request, "deviceStatus", null);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        jpqlParams.put("deviceStatus", deviceStatus);
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        List<BioHospitalDevice> deviceList = this.bioDBQueryManager.searchDevicesForStatus(siteId, locationId, jpqlParams);
        if (deviceList == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDevicesByCatnDep.msg", this.getSessionLanguage(request)));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        this.viewPDFReport("rt1-2.jasper", Enums.REPORT_TITLE.HOSPITAL_DEVICES_STATUS.getReportTitle(langCode), null, params, request, response);
    }
}
