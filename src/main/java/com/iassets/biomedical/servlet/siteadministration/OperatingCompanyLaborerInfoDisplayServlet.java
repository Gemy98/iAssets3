/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.siteadministration;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonLookupNationality;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/OperatingCompanyLaborerInfoDisplay"})
public class OperatingCompanyLaborerInfoDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        Integer empId = WebUtil.getParamValueAsInteger(request, "empId", null);
        CommonEmployee emp = null;
        if (empId != null && (emp = this.commonDBQueryManager.findSiteEmployeeById(this.getSessionSiteId(request), empId)) != null) {
            request.setAttribute("employeeObj", (Object)emp);
        }
        if (emp == null || emp.getUserType().getId().intValue() != Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()) {
            request.setAttribute("jobTitleList", this.bioDBQueryManager.getOnsiteOperatingCompanyUserTypes(Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, true));
        }
        request.setAttribute("nationalityList", this.bioDBQueryManager.findAll(CommonLookupNationality.class, langCode));
        this.forward(request, response, "/bio/siteadministration/OperatingCompanyLaborerInfo.jsp");
    }
}
