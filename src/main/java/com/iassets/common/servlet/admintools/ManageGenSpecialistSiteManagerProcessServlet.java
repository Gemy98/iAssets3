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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(value={"/gen/ManageSpecialistSiteManagerProcess"})
public class ManageGenSpecialistSiteManagerProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER;
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
        int gmpId = WebUtil.getParamValueAsInteger(request, "gmpId", 0);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, null, (Integer)gmpId, this.getSessionSiteId(request));
    }

    @Override
    protected CommonEmployee getEmployeeToBeReplaced(HttpServletRequest request, EmployeeMetaData metaData) {
        List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), metaData.getUserTypeEnum(), (int)metaData.getGmpCategoryId(), false);
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
