/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ManageHospitalDepartmentDisplay", "/gen/ManageHospitalDepartmentDisplay"})
public class ManageHospitalDepartmentDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = WebUtil.getSessionLanguage(request);
        boolean isBioMode = request.getRequestURI().contains("/bio/ManageHospitalDepartmentDisplay");
        String processServlet = null;
        List<CommonHospitalDepartment> departments = null;
        if (isBioMode) {
            departments = this.commonDBQueryManager.getLocationDepartments(this.getSessionLocationId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, langCode);
            processServlet = "bio/ManageHospitalDepartmentProcess";
        } else {
            departments = this.commonDBQueryManager.getLocationDepartments(this.getSessionLocationId(request), Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP, langCode);
            processServlet = "gen/ManageHospitalDepartmentProcess";
        }
        List<CommonHospitalLocation> locations = this.commonDBQueryManager.getHospitalLocations(this.getSessionLocationId(request), langCode);
        request.setAttribute("locations", locations);
        request.setAttribute("departments", departments);
        request.setAttribute("supportedLanguages", LocalizationManager.SUPPORTED_LANGUAGES);
        request.setAttribute("processServlet", (Object)processServlet);
        this.forward(request, response, "/admintools/ManageHospitalDepartment.jsp");
    }
}
