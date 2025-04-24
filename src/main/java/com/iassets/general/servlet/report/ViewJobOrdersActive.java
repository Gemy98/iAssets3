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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewOpenedJobOrders", "/gen/ViewLateJobOrders"})
public class ViewJobOrdersActive
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportTitle;
        String errorMsg;
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        int month = WebUtil.getParamValueAsInteger(request, "month", 0);
        int year = WebUtil.getParamValueAsInteger(request, "year", 0);
        String uri = request.getServletPath();
        boolean viewLate = uri.contains("ViewLateJobOrders");
        if (viewLate) {
            errorMsg = "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u0648\u0627\u0645\u0631 \u0639\u0645\u0644 \u0645\u062a\u0623\u062e\u0631\u0629";
            reportTitle = Enums.REPORT_TITLE.LATE_JOB_ORDERS.getReportTitle(langCode);
        } else {
            errorMsg = "\u0644\u0627 \u064a\u0648\u062c\u062f \u0623\u0648\u0627\u0645\u0631 \u0639\u0645\u0644 \u0645\u0641\u062a\u0648\u062d\u0629";
            reportTitle = Enums.REPORT_TITLE.OPENED_JOB_ORDERS.getReportTitle(langCode);
        }
        List<GenJobOrder> jOrders = this.genDBQueryManager.getJobOrders(siteId, locationId, this.getSessionUser(request).getGmpCategory(), month, year, viewLate ? Enums.JOB_ORDER_STATUS.LATE : Enums.JOB_ORDER_STATUS.OPENED);
        if (jOrders == null || jOrders.isEmpty()) {
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        request.setAttribute("viewLate", (Object)viewLate);
        request.setAttribute("jobOrders", jOrders);
        this.viewHTMLReport(request, response, "/gen/report/ViewJobOrdersList.jsp", reportTitle);
    }
}
