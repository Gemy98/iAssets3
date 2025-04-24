/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.EmployeeMetaData;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.entity.CommonUserRole;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSiteEmployeeProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int sessionDirectorateId = WebUtil.getSessionDirectorate(request).getId();
        int sessionSiteId = this.getSessionSiteId(request);
        Integer employeeId = WebUtil.getParamValueAsInteger(request, "employeeId", null);
        EmployeeMetaData metaData = this.getEmployeeMetaData(request);
        metaData.setDirectorate(WebUtil.getSessionDirectorate(request));
        if (employeeId != null) {
            CommonEmployee employee = null;
            employee = AppUtil.userBelongsToDirectorate(metaData.getUserTypeEnum()) ? this.commonDBQueryManager.findDirectorateEmployeeById(sessionDirectorateId, employeeId) : this.commonDBQueryManager.findSiteEmployeeById(sessionSiteId, employeeId);
            if (employee == null) {
                throw new ServletException("No employee found");
            }
            if (employee.getUserType().getId().intValue() != metaData.getUserType().getId().intValue() && employee.getDirectorate().getId().intValue() != this.getSessionDirectorate(request).getId().intValue()) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            String processType = WebUtil.getParamValue(request, "processType", null);
            if ("delete".equals(processType)) {
                if (!this.employeeCanBeSafelyDeleted(employee)) {
                    throw new ServletException("Not allowed process");
                }
                this.deleteEmployee(request, employee);
            } else {
                if (!"update".equals(processType)) throw new ServletException("Unknown process");
                this.updateEmployee(request, employee);
            }
        } else {
            this.addNewEmployee(request, metaData);
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.AbstractSiteEmployeeProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/" + WebUtil.getCurrentlyActiveAppDirectory(request) + "/" + WebUtil.getLastSegmentOfDisplayServletUrl(request));
    }

    protected void updateEmployee(HttpServletRequest request, CommonEmployee employee) {
        employee.setNameAr(WebUtil.getParamValue(request, "nameAr", null));
        employee.setNameEn(WebUtil.getParamValue(request, "nameEn", null));
        employee.setMobile(WebUtil.getParamValue(request, "mobile", null));
        employee.setLastModifiedBy(this.getSessionUser(request));
        employee.setLastModifiedIn(new Date());
        this.commonDBQueryManager.mergeEntity(employee);
    }

    protected List<String> deleteEmployee(HttpServletRequest request, CommonEmployee toBeDeleted) {
        Date now = new Date();
        CommonEmployee currentEmployee = toBeDeleted;
        CommonUser currentUser = null;
        Integer currentEmployeeId = null;
        String currentUserName = null;
        ArrayList<String> currentUserRoleList = new ArrayList<String>();
        if (currentEmployee != null) {
            currentEmployeeId = currentEmployee.getId();
            currentEmployee.setDeletedBy(this.getSessionUser(request));
            currentEmployee.setDeletedIn(now);
            currentEmployee.setStatus(false);
            currentEmployee = this.commonDBQueryManager.mergeEntity(currentEmployee);
            List<CommonSiteEmployee> cses = this.commonDBQueryManager.getSitesOfSpecificEmployee(currentEmployeeId);
            if (cses != null && !cses.isEmpty()) {
                for (CommonSiteEmployee se : cses) {
                    se.setStatus(false);
                    this.commonDBQueryManager.mergeEntity(se);
                }
            }
            if ((currentUser = this.commonDBQueryManager.getUserOfSpecificEmployee(currentEmployeeId)) != null) {
                currentUserName = currentUser.getUserName();
                currentUser.setUserName("deleted_" + currentUserName);
                currentUser.setDeletedBy(this.getSessionUser(request));
                currentUser.setDeletedIn(now);
                currentUser.setStatus(false);
                currentUser = this.commonDBQueryManager.mergeEntity(currentUser);
                List<CommonUserRole> curs = this.commonDBQueryManager.getUserRoles(currentUserName);
                if (curs != null && !curs.isEmpty()) {
                    for (CommonUserRole ur : curs) {
                        currentUserRoleList.add(ur.getRoleName());
                        this.commonDBQueryManager.deleteEntity(ur);
                    }
                }
            }
        }
        return currentUserRoleList;
    }

    protected void addNewEmployee(HttpServletRequest request, EmployeeMetaData metaData) throws ServletException {
        Integer gmpCatId;
        if (metaData == null || metaData.getUserType() == null) {
            throw new ServletException("employee meta data not initialized");
        }
        Date now = new Date();
        CommonEmployee currentEmployee = this.getEmployeeToBeReplaced(request, metaData);
        List<String> newUserRoleList = null;
        if (currentEmployee != null) {
            newUserRoleList = this.deleteEmployee(request, currentEmployee);
        }
        CommonEmployee newEmployee = new CommonEmployee();
        newEmployee.setDirectorate(this.getSessionDirectorate(request));
        Integer hospDepId = metaData.getHospDepartmentId();
        if (hospDepId != null && hospDepId > 0) {
            newEmployee.setDepartment(this.commonDBQueryManager.getReference(hospDepId, CommonHospitalDepartment.class));
        }
        if ((gmpCatId = metaData.getGmpCategoryId()) != null && gmpCatId > 0) {
            newEmployee.setGmpCategory(this.commonDBQueryManager.getReference(gmpCatId, GenLookupJobOrderCategory.class));
        }
        newEmployee.setNameAr(WebUtil.getParamValue(request, "nameAr", null));
        newEmployee.setNameEn(WebUtil.getParamValue(request, "nameEn", null));
        newEmployee.setMobile(WebUtil.getParamValue(request, "mobile", null));
        newEmployee.setUserType(metaData.getUserType());
        newEmployee.setMovingTeam(this.getSessionSite(request).getContainsSeveralLocations());
        newEmployee.setCreatedBy(this.getSessionUser(request));
        newEmployee.setCreatedIn(now);
        newEmployee.setStatus(true);
        newEmployee = this.commonDBQueryManager.mergeEntity(newEmployee);
        Set<Integer> associatedSitesIds = metaData.getAssociatedSitesIds();
        if (associatedSitesIds != null && !associatedSitesIds.isEmpty()) {
            for (Integer siteId : associatedSitesIds) {
                CommonSiteEmployee cse = new CommonSiteEmployee();
                cse.setSite(this.commonDBQueryManager.getReference(siteId, CommonSite.class));
                cse.setEmployee(newEmployee);
                cse.setStatus(true);
                cse = this.commonDBQueryManager.mergeEntity(cse);
            }
        }
        CommonUser newUser = new CommonUser();
        newUser.setEmployee(newEmployee);
        newUser.setUserName(this.getNewUserName(metaData));
        newUser.setPassword(metaData.getDefaultPassword());
        newUser.setInitialPassword(metaData.getDefaultPassword());
        newUser.setCreatedBy(this.getSessionUser(request));
        newUser.setCreatedIn(now);
        newUser.setStatus(true);
        newUser = this.commonDBQueryManager.mergeEntity(newUser);
        if (newUserRoleList == null || newUserRoleList.isEmpty()) {
            newUserRoleList = Arrays.asList(metaData.getDefaultRoles());
        }
        if (newUserRoleList != null && !newUserRoleList.isEmpty()) {
            for (String r : newUserRoleList) {
                CommonUserRole userRole = new CommonUserRole();
                userRole.setUserName(newUser.getUserName());
                userRole.setRoleName(r);
                this.commonDBQueryManager.mergeEntity(userRole);
            }
        }
    }

    public String getNewUserName(EmployeeMetaData metaData) throws ServletException {
        if (AppUtil.userBelongsToDirectorate(metaData.getUserTypeEnum())) {
            CommonDirectorate directorate = metaData.getDirectorate();
            return this.generateNewUserName(directorate, metaData.getUserTypeEnum(), metaData.getUserType().getSinglePerDirectorate(), metaData.getDefaultUserName(), directorate.getUserNameSpace());
        }
        CommonSite site = this.commonDBQueryManager.findById(metaData.getAssociatedSitesIds().iterator().next(), CommonSite.class);
        return this.generateNewUserName(site, metaData.getUserTypeEnum(), metaData.getUserType().getSinglePerSite(), metaData.getDefaultUserName(), site.getUserNameSpace());
    }

    private <T> String generateNewUserName(T place, Enums.USER_TYPE userType, Boolean singlePerPlace, String defaultUserName, String nameSpace) throws ServletException {
        int discriminator;
        if (singlePerPlace == null) {
            throw new ServletException("User Types Metadata is Misconfigured");
        }
        if (singlePerPlace.booleanValue()) {
            discriminator = 0;
        } else if (place instanceof CommonSite) {
            discriminator = this.commonDBQueryManager.getEmployees((int)((CommonSite)place).getId(), userType, true).size() + 1;
        } else if (place instanceof CommonDirectorate) {
            discriminator = this.commonDBQueryManager.getDirectorateEmployess(((CommonDirectorate)place).getId(), userType, true).size() + 1;
        } else {
            throw new ServletException(place.getClass() + " is not supported type");
        }
        return this.getUnrepeatedUserName(defaultUserName, discriminator, nameSpace);
    }

    private String getUnrepeatedUserName(String defaultUserName, int discriminator, String userNameSpace) {
        String newUsername;
        do {
            newUsername = defaultUserName + (discriminator != 0 ? "." + discriminator : "") + "@" + userNameSpace;
            ++discriminator;
        } while (this.commonDBQueryManager.userNameDuplicated(newUsername));
        return newUsername;
    }

    protected abstract EmployeeMetaData getEmployeeMetaData(HttpServletRequest var1) throws ServletException;

    protected abstract CommonEmployee getEmployeeToBeReplaced(HttpServletRequest var1, EmployeeMetaData var2);

    protected abstract boolean employeeCanBeSafelyDeleted(CommonEmployee var1);
}
