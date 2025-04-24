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
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/gen/ManageSpecialistSiteManagerDisplay"})
public class ManageGenSpecialistSiteManagerDisplayServlet
extends AbstractSiteEmployeeDisplayServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest request) throws ServletException {
        String langCode = this.getSessionLanguage(request);
        CommonEmployee employee = null;
        String pageTitle = LocalizationManager.getLiteral("servlet.ManageGenSpecialistSiteManagerDisplay.msg1", this.getSessionLanguage(request));
        Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER;
        List<GenLookupJobOrderCategory> categoryList = this.commonDBQueryManager.findAll(GenLookupJobOrderCategory.class, langCode);
        request.setAttribute("categoryList", categoryList);
        int selectedGmpId = WebUtil.getParamValueAsInteger(request, "gmpId", 0);
        if (selectedGmpId != 0) {
            request.setAttribute("selectedGmpId", (Object)selectedGmpId);
            List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), userTypeInQuestion, selectedGmpId, false);
            if (list != null && list.size() == 1) {
                employee = list.get(0);
            }
        }
        return new EmployeeManagementUIMetaData(pageTitle, employee, "/admintools/ManageGenSpecialistSiteManager.jsp", true, true, false);
    }
}
