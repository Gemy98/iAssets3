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

@WebServlet(value={"/bio/ManageSiteManagerDisplay", "/gen/ManageSiteManagerDisplay", "/bio/ManageDepHeadDisplay", "/gen/ManageDepHeadDisplay", "/gen/ManageHospitalDiractorDisplay", "/bio/ManageHospitalDiractorDisplay"})
public class ManageUnrepeatedSiteEmployeeDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        Enums.USER_TYPE userTypeInQuestion;
        String requestURILastSegment;
        CommonEmployee employee = null;
        String pageTitle = null;
        switch (requestURILastSegment = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageSiteManagerDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedSiteEmployeeDisplay.msg1", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageSiteManagerDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_SITE_MANGER;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedSiteEmployeeDisplay.msg2", this.getSessionLanguage(request));
                break;
            }
            case "bio/ManageDepHeadDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_HEAD;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedSiteEmployeeDisplay.msg3", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageDepHeadDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DEPARTMENT_HEAD;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedSiteEmployeeDisplay.msg4", this.getSessionLanguage(request));
                break;
            }
            case "gen/ManageHospitalDiractorDisplay": 
            case "bio/ManageHospitalDiractorDisplay": {
                userTypeInQuestion = Enums.USER_TYPE.HOSPITAL_DIRECTOR;
                pageTitle = LocalizationManager.getLiteral("servlet.ManageUnrepeatedSiteEmployeeDisplay.msg5", this.getSessionLanguage(request));
                break;
            }
            default: {
                throw new ServletException("ManageUnrepeatedSiteEmployeeDisplayServlet:: Not handled URL Pattern");
            }
        }
        List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), userTypeInQuestion, false);
        if (list != null && !list.isEmpty()) {
            employee = list.get(0);
        }
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageUnrepeatedSiteEmployee.jsp", true, true, false);
    }
}
