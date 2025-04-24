/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report.filter;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewClosedJobOrdersSearch", "/bio/ViewCancelledJobOrdersSearch"})
public class JobOrderClosedAndCanceledFilterDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonSite commonSite = this.getSessionSite(request);
        Date contractStartDate = commonSite.getBioContractStartDate();
        Date contractEndDate = commonSite.getBioContractEndDate();
        Enums.USER_TYPE[] desiredTypes = new Enums.USER_TYPE[]{Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER, Enums.USER_TYPE.BIOMEDICAL_ENGINEER, Enums.USER_TYPE.BIOMEDICAL_TECHNICIAN, Enums.USER_TYPE.BIOMEDICAL_CHEMIST};
        List<CommonEmployee> employeesList = this.commonDBQueryManager.getEmployees((int)commonSite.getId(), desiredTypes, false);
        String uri = request.getRequestURI();
        String sideLabelKey = null;
        if (uri.contains("ViewClosedJobOrdersSearch")) {
            sideLabelKey = "jsp.JobOrderByClosedAndCanceledFilter.closeEmployee";
        } else if (uri.contains("ViewCancelledJobOrdersSearch")) {
            sideLabelKey = "jsp.JobOrderByClosedAndCanceledFilter.cancelEmployee";
        }
        request.setAttribute("sideLabelKey", (Object)sideLabelKey);
        request.setAttribute("employeesList", employeesList);
        request.setAttribute("contractStartDate", (Object)contractStartDate);
        request.setAttribute("contractEndDate", (Object)contractEndDate);
        this.forward(request, response, "/bio/report/filter/JobOrderByClosedAndCanceledFilter.jsp");
    }
}
