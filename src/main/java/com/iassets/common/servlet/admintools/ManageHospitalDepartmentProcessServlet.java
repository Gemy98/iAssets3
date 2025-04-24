/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.configuration.ConfigurationException
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.LocalizationUtil;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.ConfigurationException;

@WebServlet(value={"/bio/ManageHospitalDepartmentProcess", "/gen/ManageHospitalDepartmentProcess"})
public class ManageHospitalDepartmentProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] departmentLocations;
        boolean isBioMode = request.getRequestURI().contains("/bio/ManageHospitalDepartmentProcess");
        List<CommonHospitalDepartment> deparments = null;
        deparments = isBioMode ? this.commonDBQueryManager.getLocationDepartments(this.getSessionLocationId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) : this.commonDBQueryManager.getLocationDepartments(this.getSessionLocationId(request), Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP);
        if (deparments != null && !deparments.isEmpty()) {
            for (CommonHospitalDepartment department : deparments) {
                boolean deletedBuilding;
                boolean bl = deletedBuilding = request.getParameter("delete_department_" + department.getId()) != null;
                if (deletedBuilding && !department.getAllowedApps().equals(Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP.getId())) {
                    department.setStatus(false);
                } else if (deletedBuilding && department.getAllowedApps().equals(Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP.getId())) {
                    if (isBioMode) {
                        department.setAllowedApps(Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP.getId());
                    } else {
                        department.setAllowedApps(Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP.getId());
                    }
                } else {
                    for (String langCode : LocalizationManager.SUPPORTED_LANGUAGES) {
                        String departmentName = WebUtil.getParamValue(request, "department_name_" + langCode + "_" + department.getId(), "\u062f\u0648\u0646 \u0627\u0633\u0645");
                        try {
                            LocalizationManager.addOrUpdateLiteral(department.getNameLiteralKey(), departmentName, langCode);
                        }
                        catch (ConfigurationException e) {
                            Common.log((Exception)((Object)e));
                        }
                    }
                    department.setLocation(new CommonHospitalLocation(WebUtil.getParamValueAsInteger(request, "department_building_" + department.getId(), 0)));
                }
                this.commonDBQueryManager.mergeEntity(department);
            }
        }
        if ((departmentLocations = WebUtil.getParamValuesAsStringArray(request, "department_building", null)) != null) {
            for (int i = 0; i < departmentLocations.length; ++i) {
                CommonHospitalDepartment commonHospitalDepartment = new CommonHospitalDepartment();
                commonHospitalDepartment.setLocation(new CommonHospitalLocation(Integer.parseInt(departmentLocations[i])));
                if (isBioMode) {
                    commonHospitalDepartment.setAllowedApps(Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP.getId());
                } else {
                    commonHospitalDepartment.setAllowedApps(Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP.getId());
                }
                commonHospitalDepartment.setHospital(this.getSessionLocation(request));
                commonHospitalDepartment.setStatus(true);
                commonHospitalDepartment = this.commonDBQueryManager.mergeEntity(commonHospitalDepartment);
                String nameLiteralKey = LocalizationUtil.generateDepartmentNameLiteralKey(this.getSessionDirectorate(request).getId(), this.getSessionSiteId(request), commonHospitalDepartment.getId());
                commonHospitalDepartment.setNameLiteralKey(nameLiteralKey);
                this.commonDBQueryManager.mergeEntity(commonHospitalDepartment);
                for (String langCode : LocalizationManager.SUPPORTED_LANGUAGES) {
                    String localizedDepartmentName = WebUtil.getParamValuesAsStringArray(request, "department_name_" + langCode, null)[i];
                    try {
                        LocalizationManager.addOrUpdateLiteral(nameLiteralKey, localizedDepartmentName, langCode);
                    }
                    catch (ConfigurationException e) {
                        Common.log((Exception)((Object)e));
                    }
                }
            }
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ManageHospitalDepartmentProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        if (isBioMode) {
            this.sendRedirect(request, response, "/bio/ManageHospitalDepartmentDisplay");
        } else {
            this.sendRedirect(request, response, "/gen/ManageHospitalDepartmentDisplay");
        }
    }
}
