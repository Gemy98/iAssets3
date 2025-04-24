/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.validation;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/CheckEmployeeInfoDuplication"})
public class CheckEmployeeInfoDuplication
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empNo = WebUtil.getParamValue(request, "employeeNo", null);
        int siteId = this.getSessionSiteId(request);
        int excludedEmpId = WebUtil.getParamValueAsInteger(request, "empId", 0);
        CommonEmployee employee = this.bioDBQueryManager.checkEmployeeNoDuplication(siteId, empNo, excludedEmpId, Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP);
        PrintWriter out = response.getWriter();
        if (employee == null) {
            out.print("true");
            return;
        }
        out.print("false");
    }
}
