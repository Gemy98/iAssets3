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

@WebServlet(value={"/bio/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay", "/gen/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay"})
public class ManageDirectorateMaintenanceDepartmentSupervisorsDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        Enums.USER_TYPE userTypeInQuestion;
        String displayServlet;
        CommonEmployee employee = null;
        String pageTitle = null;
        switch (displayServlet = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageDirectorateMaintenanceDepartmentSupervisorsDisplay.msg1", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DIRECTORATE_ASSISTANT_ADMIN;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageDirectorateMaintenanceDepartmentSupervisorsDisplay.msg2", this.getSessionLanguage(request));
                break;
            }
            default: {
                throw new ServletException("ManageDirectorateMaintenanceDepartmentSupervisorsDisplayServlet:: Not handled URL Pattern");
            }
        }
        List<CommonEmployee> supervisorsList = this.commonDBQueryManager.getDirectorateEmployess(this.getSessionDirectorate(request).getId(), userTypeInQuestion, false);
        int selectedSupervisorId = WebUtil.getParamValueAsInteger(request, "supervisorId", 0);
        boolean enableDelete = selectedSupervisorId != 0;
        boolean enableUpdate = enableDelete;
        boolean supervisorSelected = enableDelete;
        if (supervisorSelected) {
            request.setAttribute("selectedSupervisorId", (Object)selectedSupervisorId);
            employee = this.commonDBQueryManager.findById(selectedSupervisorId, CommonEmployee.class);
            if (employee.getUserType().getId().intValue() != userTypeInQuestion.getId() && employee.getDirectorate().getId().intValue() != this.getSessionDirectorate(request).getId().intValue()) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", this.getSessionLanguage(request)));
            }
        }
        request.setAttribute("displayServlet", (Object)displayServlet);
        request.setAttribute("supervisorsList", supervisorsList);
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageDirectorateMaintenanceDepartmentSupervisors.jsp", false, enableUpdate, enableDelete);
    }
}
