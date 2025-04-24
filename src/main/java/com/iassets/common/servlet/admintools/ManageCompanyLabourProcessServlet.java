/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.EmployeeMetaData;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.servlet.admintools.AbstractSiteEmployeeProcessServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/bio/ManageCompanyLabourProcess", "/gen/ManageCompanyLabourProcess"})
public class ManageCompanyLabourProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) throws ServletException {
        int selectedUserId = WebUtil.getParamValueAsInteger(request, "userType", 0);
        String requestURILast2Segment = WebUtil.getRequestUriLastTwoSegments(request);
        Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.getUserTypeById(selectedUserId);
        if (!requestURILast2Segment.equals("bio/ManageCompanyLabourProcess") && !requestURILast2Segment.equals("gen/ManageCompanyLabourProcess")) {
            return null;
        }
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(selectedUserId, CommonLookupUserType.class);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, null, null, this.getSessionSiteId(request));
    }

    @Override
    protected CommonEmployee getEmployeeToBeReplaced(HttpServletRequest request, EmployeeMetaData metaData) {
        return null;
    }

    @Override
    protected boolean employeeCanBeSafelyDeleted(CommonEmployee employee) {
        return true;
    }
}
