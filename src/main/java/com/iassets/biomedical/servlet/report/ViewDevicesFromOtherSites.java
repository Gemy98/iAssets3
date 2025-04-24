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

@WebServlet(value={"/bio/ViewDevicesFromOtherSites"})
public class ViewDevicesFromOtherSites
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        Integer siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        String previousSiteName = WebUtil.getParamValue(request, "previousSiteName", null);
        jpqlParams.put("previousSiteName", previousSiteName == null ? null : "%" + previousSiteName + "%");
        List<BioHospitalDevice> deviceList = this.bioDBQueryManager.searchBioHospitalDevicesForFromOtherSites(siteId, locationId, jpqlParams);
        if (deviceList == null || deviceList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDevicesFromOtherSites.msg", this.getSessionLanguage(request)));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", deviceList);
        this.viewPDFReport("rt11.jasper", Enums.REPORT_TITLE.DEVICES_FROM_OTHER_SITE.getReportTitle(langCode), null, params, request, response);
    }
}
