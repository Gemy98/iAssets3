/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/gen/ManageMaintenanceDepartmentSupervisorsProcess", "/bio/ManageMaintenanceDepartmentSupervisorsProcess"})
public class ManageMaintenanceDepartmentSupervisorsProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_TYPE userTypeInQuestion;
        String requestURILastSegment;
        switch (requestURILastSegment = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageMaintenanceDepartmentSupervisorsProcess": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_SUPERVISOR;
                break;
            }
            case "gen/ManageMaintenanceDepartmentSupervisorsProcess": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DEPARTMENT_SUPERVISOR;
                break;
            }
            default: {
                return null;
            }
        }
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
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
