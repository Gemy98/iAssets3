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

@WebServlet(value={"/bio/ManageDirectorateMaintenanceHeadDisplay", "/gen/ManageDirectorateMaintenanceHeadDisplay", "/bio/ManageDirectorateBioMaintenanceHeadDisplay", "/gen/ManageDirectorateGenMaintenanceHeadDisplay"})
public class ManageUnrepeatedDirectorateEmployeeDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        Enums.USER_TYPE userTypeInQuestion;
        String requestURILastSegment;
        CommonEmployee employee = null;
        String pageTitle = null;
        switch (requestURILastSegment = WebUtil.getRequestUriLastSegment(request)) {
            case "ManageDirectorateMaintenanceHeadDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.DIRECTORATE_SUPER_ADMIN;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedDirectorateEmployeeDisplay.msg1", this.getSessionLanguage(request));
                break;
            }
            case "ManageDirectorateBioMaintenanceHeadDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ADMIN;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedDirectorateEmployeeDisplay.msg2", this.getSessionLanguage(request));
                break;
            }
            case "ManageDirectorateGenMaintenanceHeadDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DIRECTORATE_ADMIN;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedDirectorateEmployeeDisplay.msg3", this.getSessionLanguage(request));
                break;
            }
            default: {
                throw new ServletException("ManageUnrepeatedDirectorateEmployeeDisplayServlet:: Not handled URL Pattern");
            }
        }
        List<CommonEmployee> list = this.commonDBQueryManager.getDirectorateEmployess(this.getSessionDirectorate(request).getId(), userTypeInQuestion, false);
        if (list != null && !list.isEmpty()) {
            employee = list.get(0);
        }
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageUnrepeatedDirectorateEmployee.jsp", true, true, false);
    }
}
