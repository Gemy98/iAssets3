/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo;

import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.util.Enums;
import java.util.HashSet;
import java.util.Set;

public class EmployeeMetaData {
    private CommonLookupUserType userType;
    private Enums.USER_TYPE userTypeEnum;
    private Integer hospDepartmentId;
    private Integer GmpCategoryId;
    private Set<Integer> associatedSitesIds;
    private CommonDirectorate directorate;

    public EmployeeMetaData() {
    }

    public EmployeeMetaData(CommonLookupUserType userType, Enums.USER_TYPE userTypeEnum, Integer hospDepartmentId, Integer gmpCategoryId, Set<Integer> associatedSitesIds) {
        this.userType = userType;
        this.userTypeEnum = userTypeEnum;
        this.hospDepartmentId = hospDepartmentId;
        this.GmpCategoryId = gmpCategoryId;
        this.associatedSitesIds = associatedSitesIds;
    }

    public EmployeeMetaData(CommonLookupUserType userType, Enums.USER_TYPE userTypeEnum, Integer hospDepartmentId, Integer gmpCategoryId, int associatedSiteId) {
        this.userType = userType;
        this.userTypeEnum = userTypeEnum;
        this.hospDepartmentId = hospDepartmentId;
        this.GmpCategoryId = gmpCategoryId;
        HashSet<Integer> associatedSitesIds = new HashSet<Integer>();
        associatedSitesIds.add(associatedSiteId);
        this.associatedSitesIds = associatedSitesIds;
    }

    public CommonLookupUserType getUserType() {
        return this.userType;
    }

    public void setUserType(CommonLookupUserType userType) {
        this.userType = userType;
    }

    public Enums.USER_TYPE getUserTypeEnum() {
        return this.userTypeEnum;
    }

    public void setUserTypeEnum(Enums.USER_TYPE userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }

    public Integer getHospDepartmentId() {
        return this.hospDepartmentId;
    }

    public void setHospDepartmentId(Integer hospDepartmentId) {
        this.hospDepartmentId = hospDepartmentId;
    }

    public Integer getGmpCategoryId() {
        return this.GmpCategoryId;
    }

    public void setGmpCategoryId(Integer gmpCategoryId) {
        this.GmpCategoryId = gmpCategoryId;
    }

    public Set<Integer> getAssociatedSitesIds() {
        return this.associatedSitesIds;
    }

    public void setAssociatedSitesIds(Set<Integer> associatedSitesIds) {
        this.associatedSitesIds = associatedSitesIds;
    }

    public String getDefaultUserName() {
        return this.userType.getDefaultUserName();
    }

    public String getDefaultPassword() {
        return this.userType.getDefaultPassword();
    }

    public String[] getDefaultRoles() {
        return this.userType.getDefaultRoles();
    }

    public boolean employeeAssociatedWithManySites() {
        return this.associatedSitesIds != null && this.associatedSitesIds.size() > 1;
    }

    public CommonDirectorate getDirectorate() {
        return this.directorate;
    }

    public void setDirectorate(CommonDirectorate directorate) {
        this.directorate = directorate;
    }
}
