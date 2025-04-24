/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.EmployeeManagementUIMetaData;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.servlet.admintools.AbstractSiteEmployeeDisplayServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/bio/ManageHospitalDepartmentHeadDisplay", "/gen/ManageHospitalDepartmentHeadDisplay"})
public class ManageHospitalDepartmentHeadDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        String langCode = this.getSessionLanguage(request);
        CommonEmployee employee = null;
        String pageTitle = LocalizationManager.getLiteral("servlet.ManageHospitalDepartmentHeadDisplay.msg1", this.getSessionLanguage(request));
        Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.HOSPITAL_DEPARTMENT_SUPERVISOR;
        int selectedHospDepId = WebUtil.getParamValueAsInteger(request, "departmentId", 0);
        if (selectedHospDepId != 0) {
            request.setAttribute("selectedHospDepId", (Object)selectedHospDepId);
            List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), selectedHospDepId, userTypeInQuestion, false);
            if (list != null && list.size() == 1) {
                employee = list.get(0);
            }
        }
        request.setAttribute("displayServlet", (Object)WebUtil.getRequestUriLastTwoSegments(request));
        List<CommonHospitalDepartment> departmentList = this.commonDBQueryManager.getLocationDepartments(this.getSessionLocationId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, langCode);
        request.setAttribute("departmentList", departmentList);
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageHospitalDeparmentHead.jsp", true, true, false);
    }
}
