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
import java.util.HashSet;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/bio/ManageSiteManagerProcess", "/gen/ManageSiteManagerProcess", "/bio/ManageDepHeadProcess", "/gen/ManageDepHeadProcess", "/bio/ManageHospitalDiractorProcess", "/gen/ManageHospitalDiractorProcess"})
public class ManageUnrepeatedSiteEmployeeProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_TYPE userTypeInQuestion;
        int sessionSiteId = this.getSessionSiteId(request);
        String requestURILastSegment = WebUtil.getRequestUriLastTwoSegments(request);
        HashSet<Integer> associatedSitesIds = new HashSet<Integer>();
        switch (requestURILastSegment) {
            case "bio/ManageSiteManagerProcess": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER;
                associatedSitesIds.add(sessionSiteId);
                break;
            }
            case "gen/ManageSiteManagerProcess": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_SITE_MANGER;
                associatedSitesIds.add(sessionSiteId);
                break;
            }
            case "bio/ManageDepHeadProcess": {
                userTypeInQuestion = Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_HEAD;
                associatedSitesIds.add(sessionSiteId);
                break;
            }
            case "gen/ManageDepHeadProcess": {
                userTypeInQuestion = Enums.USER_TYPE.GENERAL_DEPARTMENT_HEAD;
                associatedSitesIds.add(sessionSiteId);
                break;
            }
            case "gen/ManageHospitalDiractorProcess": 
            case "bio/ManageHospitalDiractorProcess": {
                userTypeInQuestion = Enums.USER_TYPE.HOSPITAL_DIRECTOR;
                associatedSitesIds.add(sessionSiteId);
                break;
            }
            default: {
                return null;
            }
        }
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, null, null, associatedSitesIds);
    }

    @Override
    protected CommonEmployee getEmployeeToBeReplaced(HttpServletRequest request, EmployeeMetaData metaData) {
        List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), metaData.getUserTypeEnum(), false);
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
