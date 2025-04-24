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
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewClosedJobOrders", "/gen/ViewCancelledJobOrders"})
public class ViewJobOrdersNotActive
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        String uri = WebUtil.getRequestUriLastSegment(request);
        boolean viewClosed = uri.contains("ViewClosedJobOrders");
        String errorMsg = viewClosed ? "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u0648\u0627\u0645\u0631 \u0639\u0645\u0644 \u0645\u063a\u0644\u0642\u0629 \u0644\u0644\u0634\u0647\u0631 \u0648\u0627\u0644\u0633\u0646\u0629 \u0627\u0644\u062a\u064a \u062a\u0645 \u0627\u062e\u062a\u064a\u0627\u0631\u0647\u0627" : "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u0648\u0627\u0645\u0631 \u0639\u0645\u0644 \u0645\u0644\u063a\u064a\u0629 \u0644\u0644\u0634\u0647\u0631 \u0648\u0627\u0644\u0633\u0646\u0629 \u0627\u0644\u062a\u064a \u062a\u0645 \u0627\u062e\u062a\u064a\u0627\u0631\u0647\u0627";
        String reportTitle = null;
        List<GenJobOrder> jOrders = this.genDBQueryManager.getJobOrders(siteId, locationId, this.getSessionUser(request).getGmpCategory(), month, year, viewClosed ? Enums.JOB_ORDER_STATUS.CLOSED : Enums.JOB_ORDER_STATUS.CANCELLED);
        if (jOrders == null || jOrders.isEmpty()) {
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        if (viewClosed) {
            reportTitle = Enums.REPORT_TITLE.JOB_ORDER_CLOSED.getReportTitle(langCode);
            request.setAttribute("jobOrders", jOrders);
            request.setAttribute("showClosedJobOrders", (Object)viewClosed);
            this.viewHTMLReport(request, response, "/gen/report/ViewJobOrdersList.jsp", reportTitle);
        } else {
            reportTitle = Enums.REPORT_TITLE.JOB_ORDER_CANCELLED.getReportTitle(langCode);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("jobOrderList", jOrders);
            this.viewPDFReport("rt13.jasper", reportTitle, null, params, request, response);
        }
    }
}
