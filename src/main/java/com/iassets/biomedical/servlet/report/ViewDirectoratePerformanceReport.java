/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.report;

import com.iassets.biomedical.DB.BioDashboardDBQueryManager;
import com.iassets.biomedical.dto.report.DashboardItem;
import com.iassets.biomedical.dto.report.DashboardPerformance;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/bio/ViewDirectoratePerformanceReport"})
public class ViewDirectoratePerformanceReport
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BioDashboardDBQueryManager bioDashboardDBQueryManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int month;
        String langCode = this.getSessionLanguage(request);
        int directorateId = this.getSessionDirectorate(request).getId();
        HashMap<String, Object> params = new HashMap<String, Object>();
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        if (year == 0) {
            year = DateUtil.getYear(new Date());
        }
        if ((month = WebUtil.getParamValueAsInteger(request, "month", 0).intValue()) == 0) {
            month = DateUtil.getMonth(new Date());
        }
        List<DashboardItem> listGroupOne = this.bioDashboardDBQueryManager.getBioDashboardForGroupOneByDirectorate(directorateId, year, month);
        List<DashboardItem> listGroupTwo = this.bioDashboardDBQueryManager.getBioDashboardForGroupTwoByDirectorate(directorateId, year, month);
        List<DashboardItem> listGroupThree = this.bioDashboardDBQueryManager.getBioDashboardForGroup3AND4ByDirectorate(directorateId, 1);
        List<DashboardItem> listGroupFour = this.bioDashboardDBQueryManager.getBioDashboardForGroup3AND4ByDirectorate(directorateId, 2);
        DashboardPerformance dashboardGroups = new DashboardPerformance();
        dashboardGroups.setGroupOne(listGroupOne);
        dashboardGroups.setGroupTwo(listGroupTwo);
        dashboardGroups.setGroupThree(listGroupThree);
        dashboardGroups.setGroupFour(listGroupFour);
        params.put("monthName", Enums.YEAR_MONTHS.getNameById("" + month, "en"));
        params.put("year", year);
        params.put("dashboardGroups", dashboardGroups);
        this.viewPDFReport("rt14.jasper", Enums.REPORT_TITLE.BIO_DIRECTORATE_MONTHLY_MAINTENANCE_PERFORMANCE.getReportTitle(langCode), null, params, request, response);
    }
}
