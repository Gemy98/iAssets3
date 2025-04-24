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

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/EmployeeInfoProcess", "/gen/EmployeeInfoProcess"})
public class EmployeeInfoProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonEmployee emp = this.commonDBQueryManager.findSiteEmployeeById(this.getSessionSiteId(request), WebUtil.getParamValueAsInteger(request, "employeeId", 0));
        if (emp == null) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        emp.setNameAr(WebUtil.getParamValue(request, "nameAr", "").trim());
        emp.setNameEn(WebUtil.getParamValue(request, "nameEn", "").trim());
        emp.setMobile(WebUtil.getParamValue(request, "mobile", "").trim());
        emp = this.commonDBQueryManager.mergeEntity(emp);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.EmployeeInfoProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
    }
}
