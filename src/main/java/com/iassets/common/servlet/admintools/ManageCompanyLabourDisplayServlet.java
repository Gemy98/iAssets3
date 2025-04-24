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
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.servlet.admintools.AbstractSiteEmployeeDisplayServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/bio/ManageCompanyLabourDisplay", "/gen/ManageCompanyLabourDisplay"})
public class ManageCompanyLabourDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        Enums.USER_TYPE[] excludedUserTypes;
        Enums.USER_ALLOWED_APP_TYPE targetAppType;
        String displayServlet;
        String langCode = this.getSessionLanguage(request);
        int sessionSiteId = this.getSessionSiteId(request);
        CommonEmployee employee = null;
        String pageTitle = null;
        switch (displayServlet = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageCompanyLabourDisplay": {
                targetAppType = Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
                excludedUserTypes = new Enums.USER_TYPE[]{Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER};
                pageTitle = LocalizationManager.getLiteral("servlet.ManageCompanyLabourDisplay.msg1", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageCompanyLabourDisplay": {
                targetAppType = Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
                excludedUserTypes = new Enums.USER_TYPE[]{Enums.USER_TYPE.GENERAL_SITE_MANGER, Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER};
                pageTitle = LocalizationManager.getLiteral("servlet.ManageCompanyLabourDisplay.msg1", this.getSessionLanguage(request));
                break;
            }
            default: {
                throw new ServletException("ManageCompanyLabourDisplayServlet:: Not handled URL Pattern");
            }
        }
        request.setAttribute("displayServlet", (Object)displayServlet);
        List<CommonLookupUserType> managedUserTypes = this.commonDBQueryManager.getOnsiteOperatingCompanyLaborUserTypes(targetAppType, excludedUserTypes);
        request.setAttribute("managedUserTypes", managedUserTypes);
        int selectedUserTypeId = WebUtil.getParamValueAsInteger(request, "userType", 0);
        int selectedEmployeeId = WebUtil.getParamValueAsInteger(request, "empId", 0);
        boolean userTypeSelected = selectedUserTypeId != 0;
        boolean enableDelete = selectedEmployeeId != 0;
        boolean enableUpdate = enableDelete;
        boolean employeeSelected = enableDelete;
        if (userTypeSelected) {
            request.setAttribute("selectedUserTypeId", (Object)selectedUserTypeId);
            Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.getUserTypeById(selectedUserTypeId);
            List<CommonEmployee> employeeList = this.commonDBQueryManager.getEmployees(sessionSiteId, userTypeInQuestion, false);
            request.setAttribute("employeeList", employeeList);
            if (employeeSelected) {
                employee = this.commonDBQueryManager.findSiteEmployeeById(sessionSiteId, selectedEmployeeId);
                if (employee.getUserType().getId() != selectedUserTypeId) {
                    throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
                }
                request.setAttribute("selectedEmployeeId", (Object)selectedEmployeeId);
            }
        }
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageCompanyLabour.jsp", false, enableUpdate, enableDelete);
    }
}
