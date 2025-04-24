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

@WebServlet(value={"/gen/ManageHospitalDepartmentHeadProcess", "/bio/ManageHospitalDepartmentHeadProcess"})
public class ManageHospitalDepartmentHeadProcessServlet
extends AbstractSiteEmployeeProcessServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected EmployeeMetaData getEmployeeMetaData(HttpServletRequest request) {
        Enums.USER_TYPE userTypeInQuestion = Enums.USER_TYPE.HOSPITAL_DEPARTMENT_SUPERVISOR;
        CommonLookupUserType userTypeObj = this.commonDBQueryManager.findById(userTypeInQuestion.getId(), CommonLookupUserType.class);
        int selectedHospDepId = WebUtil.getParamValueAsInteger(request, "departmentId", 0);
        return new EmployeeMetaData(userTypeObj, userTypeInQuestion, (Integer)selectedHospDepId, null, this.getSessionSiteId(request));
    }

    @Override
    protected CommonEmployee getEmployeeToBeReplaced(HttpServletRequest request, EmployeeMetaData metaData) {
        List<CommonEmployee> list = this.commonDBQueryManager.getEmployees(this.getSessionSiteId(request), (int)metaData.getHospDepartmentId(), metaData.getUserTypeEnum(), false);
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
