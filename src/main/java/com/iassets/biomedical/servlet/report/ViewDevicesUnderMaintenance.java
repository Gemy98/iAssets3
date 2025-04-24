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

import com.iassets.biomedical.entity.BioJobOrder;
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

@WebServlet(value={"/bio/DevicesUnderMaintenance"})
public class ViewDevicesUnderMaintenance
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        Integer siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String jobOrderOpenDate = WebUtil.getParamValue(request, "jobOrderOpenDate", null);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        jpqlParams.put("jobOrderOpenDate", jobOrderOpenDate);
        List<BioJobOrder> jobOrderList = this.bioDBQueryManager.searchBioJobOrders(siteId, locationId, jpqlParams);
        if (jobOrderList == null || jobOrderList.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDevicesUnderMaintenance.msg", this.getSessionLanguage(request)));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jobOrderList", jobOrderList);
        this.viewPDFReport("rt2.jasper", Enums.REPORT_TITLE.DEVICES_UNDER_MAINTENANCE.getReportTitle(langCode), null, params, request, response);
    }
}
