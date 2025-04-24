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
import com.iassets.common.servlet.admintools.AbstractSiteEmployeeDisplayServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/gen/ManageMaintenanceDepartmentSupervisorsDisplay", "/bio/ManageMaintenanceDepartmentSupervisorsDisplay"})
public class ManageMaintenanceDepartmentSupervisorsDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        Enums.USER_TYPE userTypeInQuestion;
        String displayServlet;
        CommonEmployee employee = null;
        String pageTitle = null;
        switch (displayServlet = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageMaintenanceDepartmentSupervisorsDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_SUPERVISOR;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageMaintenanceDepartmentSupervisors.msg1", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageMaintenanceDepartmentSupervisorsDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DEPARTMENT_SUPERVISOR;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageMaintenanceDepartmentSupervisors.msg2", this.getSessionLanguage(request));
                break;
            }
            default: {
                throw new ServletException("ManageMaintenanceDepartmentSupervisorsDisplayServlet:: Not handled URL Pattern");
            }
        }
        List<CommonEmployee> supervisorsList = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), userTypeInQuestion, false);
        int selectedSupervisorId = WebUtil.getParamValueAsInteger(request, "supervisorId", 0);
        boolean enableDelete = selectedSupervisorId != 0;
        boolean enableUpdate = enableDelete;
        boolean supervisorSelected = enableDelete;
        if (supervisorSelected) {
            request.setAttribute("selectedSupervisorId", (Object)selectedSupervisorId);
            employee = this.commonDBQueryManager.findSiteEmployeeById(this.getSessionSiteId(request), selectedSupervisorId);
            if (employee.getUserType().getId().intValue() != userTypeInQuestion.getId()) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", this.getSessionLanguage(request)));
            }
        }
        request.setAttribute("displayServlet", (Object)displayServlet);
        request.setAttribute("supervisorsList", supervisorsList);
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageMaintenanceDepartmentSupervisors.jsp", false, enableUpdate, enableDelete);
    }
}
