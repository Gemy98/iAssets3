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

@WebServlet(value={"/bio/ViewClosedJobOrders", "/bio/ViewCancelledJobOrders"})
public class ViewJobOrdersNotActive
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospitalDepartment sessionUserDepartment = this.getSessionUser(request).getDepartment();
        Integer siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String monthYear = WebUtil.getParamValue(request, "monthYear", null);
        Integer employeeId = WebUtil.getParamValueAsInteger(request, "employeeId", null);
        HashMap<String, Object> jpqlParams = new HashMap<String, Object>();
        WebUtil.prepareCommonDeviceFilterParams(request, sessionUserDepartment, jpqlParams);
        String uri = WebUtil.getRequestUriLastSegment(request);
        boolean viewClosed = uri.contains("ViewClosedJobOrders");
        Enums.JOB_ORDER_STATUS status = viewClosed ? Enums.JOB_ORDER_STATUS.CLOSED : Enums.JOB_ORDER_STATUS.CANCELLED;
        jpqlParams.put("monthYear", monthYear);
        jpqlParams.put("employeeId", employeeId);
        List<BioJobOrder> jOrders = this.bioDBQueryManager.getJobOrdersColsedAndCanceled(siteId, locationId, status, jpqlParams);
        if (jOrders == null || jOrders.isEmpty()) {
            String errorMsg = viewClosed ? LocalizationManager.getLiteral("servlet.ViewJobOrdersNotActive.msg1", this.getSessionLanguage(request)) : LocalizationManager.getLiteral("servlet.ViewJobOrdersNotActive.msg2", this.getSessionLanguage(request));
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        String reportTitle = null;
        if (viewClosed) {
            reportTitle = Enums.REPORT_TITLE.JOB_ORDER_CLOSED.getReportTitle(langCode);
            request.setAttribute("jobOrders", jOrders);
            request.setAttribute("showClosedJobOrders", (Object)viewClosed);
            this.viewHTMLReport(request, response, "/bio/report/ViewJobOrdersList.jsp", reportTitle);
        } else {
            reportTitle = Enums.REPORT_TITLE.JOB_ORDER_CANCELLED.getReportTitle(langCode);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("jobOrderList", jOrders);
            this.viewPDFReport("rt13.jasper", reportTitle, null, params, request, response);
        }
    }
}
