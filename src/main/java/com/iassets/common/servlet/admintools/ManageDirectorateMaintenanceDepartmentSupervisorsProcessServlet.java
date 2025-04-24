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

@WebServlet(value={"/bio/ManageDirectorateMaintenanceDepartmentSupervisorsProcess", "/gen/ManageDirectorateMaintenanceDepartmentSupervisorsProcess"})
public class ManageDirectorateMaintenanceDepartmentSupervisorsProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_TYPE userTypeInQuestion;
        String requestURILastSegment;
        switch (requestURILastSegment = WebUtil.getRequestUriLastTwoSegments(request)) {
            case "bio/ManageDirectorateMaintenanceDepartmentSupervisorsProcess": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN;
                break;
            }
            case "gen/ManageDirectorateMaintenanceDepartmentSupervisorsProcess": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DIRECTORATE_ASSISTANT_ADMIN;
                break;
            }
            default: {
                return null;
            }
        }
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, null, null, null);
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
