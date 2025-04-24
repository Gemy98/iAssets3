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
import java.util.List;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/gen/ManageDirectorateMaintenanceHeadProcess", "/bio/ManageDirectorateMaintenanceHeadProcess", "/bio/ManageDirectorateBioMaintenanceHeadProcess", "/gen/ManageDirectorateGenMaintenanceHeadProcess"})
public class ManageUnrepeatedDirectorateEmployeeProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_ALLOWED_APP_TYPE allowedAppType;
        Enums.USER_TYPE userTypeInQuestion;
        String requestURILastSegment;
        switch (requestURILastSegment = WebUtil.getRequestUriLastSegment(request)) {
            case "ManageDirectorateMaintenanceHeadProcess": {
                userTypeInQuestion = Enums.USER_TYPE.DIRECTORATE_SUPER_ADMIN;
                allowedAppType = null;
                break;
            }
            case "ManageDirectorateBioMaintenanceHeadProcess": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ADMIN;
                allowedAppType = Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
                break;
            }
            case "ManageDirectorateGenMaintenanceHeadProcess": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DIRECTORATE_ADMIN;
                allowedAppType = Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
                break;
            }
            default: {
                return null;
            }
        }
        Set<Integer> associatedSitesIds = this.commonDBQueryManager.getDirectorateActiveSitesIds(this.getSessionDirectorate(request).getId(), allowedAppType);
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, null, null, associatedSitesIds);
    }

    @Override
    protected CommonEmployee getEmployeeToBeReplaced(HttpServletRequest request, EmployeeMetaData metaData) {
        List<CommonEmployee> list = this.commonDBQueryManager.getDirectorateEmployess(this.getSessionDirectorate(request).getId(), metaData.getUserTypeEnum(), false);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    protected boolean employeeCanBeSafelyDeleted(CommonEmployee employee) {
        return false;
    }
}
