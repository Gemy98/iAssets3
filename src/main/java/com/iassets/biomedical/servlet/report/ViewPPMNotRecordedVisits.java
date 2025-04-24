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
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewPPMNotRecordedVisits"})
public class ViewPPMNotRecordedVisits
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        Integer siteId = this.getSessionSiteId(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        String monthYear = WebUtil.getParamValue(request, "monthYear", null);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        String ppmNotificationsFlag = WebUtil.getParamValue(request, "ihf", null);
        if (ppmNotificationsFlag != null) {
            int year = DateUtil.getYear(new Date());
            int month = DateUtil.getMonth(new Date());
            jpqlParams.put("monthYear", month + "/" + year);
        } else {
            jpqlParams.put("monthYear", monthYear);
        }
        List<BioHospitalDevice> list = this.bioDBQueryManager.getDevicesWhosePPmVisitsNotRecorded(siteId, 0, jpqlParams);
        if (list == null || list.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewPPMNotRecordedVisits.msg", this.getSessionLanguage(request)));
            return;
        }
        request.setAttribute("deviceList", list);
        this.viewHTMLReport(request, response, "/bio/report/ViewPPMNotRecordedVisits.jsp", Enums.REPORT_TITLE.PPM_NOT_RECORDED_VISITS.getReportTitle(langCode));
    }
}
