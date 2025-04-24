/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.administration;

import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/EmployeeInfoDisplay", "/gen/EmployeeInfoDisplay"})
public class EmployeeInfoDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer empId = WebUtil.getParamValueAsInteger(request, "empId", null);
        if (empId == null) {
            empId = (Integer)request.getAttribute("empId");
        }
        if (empId != null) {
            request.setAttribute("employee", (Object)this.commonDBQueryManager.findSiteEmployeeById(this.getSessionSiteId(request), empId));
        }
        this.include(request, response, "/administration/EmployeeInfoForm.jsp");
    }
}
