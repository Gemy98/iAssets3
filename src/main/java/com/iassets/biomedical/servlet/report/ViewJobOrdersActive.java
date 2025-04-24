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

@WebServlet(value={"/bio/ViewOpenedJobOrders", "/bio/ViewLateJobOrders"})
public class ViewJobOrdersActive
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        Integer siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        String uri = request.getServletPath();
        boolean viewLate = uri.contains("ViewLateJobOrders");
        Enums.JOB_ORDER_STATUS status = viewLate ? Enums.JOB_ORDER_STATUS.LATE : Enums.JOB_ORDER_STATUS.OPENED;
        Integer[] deviceWithinWarranty = WebUtil.getParamValuesAsIntegerArray(request, "warranty", null);
        jpqlParams.put("deviceWithinWarranty", deviceWithinWarranty);
        List<BioJobOrder> jOrders = this.bioDBQueryManager.getJobOrdersForOpendAndLate(siteId, locationId, status, jpqlParams);
        String errorMsg = null;
        String reportTitle = null;
        if (viewLate) {
            if (deviceWithinWarranty == null || deviceWithinWarranty.length == 2) {
                errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg5", this.getSessionLanguage(request));
                reportTitle = Enums.REPORT_TITLE.LATE_JOB_ORDERS.getReportTitle(langCode);
            } else if (deviceWithinWarranty[0] == 1) {
                errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg1", this.getSessionLanguage(request));
                reportTitle = Enums.REPORT_TITLE.LATE_JOB_ORDERS_WITHIN_WARRANTY.getReportTitle(langCode);
            } else if (deviceWithinWarranty[0] == 0) {
                errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg2", this.getSessionLanguage(request));
                reportTitle = Enums.REPORT_TITLE.LATE_JOB_ORDERS_OUTSIDE_WARRANTY.getReportTitle(langCode);
            }
        } else if (deviceWithinWarranty == null || deviceWithinWarranty.length == 2) {
            errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg6", this.getSessionLanguage(request));
            reportTitle = Enums.REPORT_TITLE.OPENED_JOB_ORDERS.getReportTitle(langCode);
        } else if (deviceWithinWarranty[0] == 1) {
            errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg3", this.getSessionLanguage(request));
            reportTitle = Enums.REPORT_TITLE.OPENED_JOB_ORDERS_WITHIN_WARRANTY.getReportTitle(langCode);
        } else if (deviceWithinWarranty[0] == 0) {
            errorMsg = LocalizationManager.getLiteral("servlet.ViewJobOrdersActive.msg4", this.getSessionLanguage(request));
            reportTitle = Enums.REPORT_TITLE.OPENED_JOB_ORDERS_OUTSIDE_WARRANTY.getReportTitle(langCode);
        }
        if (jOrders == null || jOrders.isEmpty()) {
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        request.setAttribute("viewLate", (Object)viewLate);
        request.setAttribute("jobOrders", jOrders);
        this.viewHTMLReport(request, response, "/bio/report/ViewJobOrdersList.jsp", reportTitle);
    }
}
