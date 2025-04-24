/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.iassets.common.entity;

import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Cacheable
@Entity
@Table(name="common_lookup_user_type")
@NamedQueries(value={@NamedQuery(name="CommonLookupUserType.findAll", query="SELECT l FROM CommonLookupUserType l WHERE l.status=true ORDER BY l.id "), @NamedQuery(name="CommonLookupUserType.getOnsiteOperatingCompanyUserTypes", query="SELECT u FROM CommonLookupUserType u WHERE u.status=true AND u.operatingCompanyLaborer = true AND u.allowedApps = :appTypeId AND u.id NOT IN (:excludedUserTypeIds) ORDER BY u.displayOrder")})
public class CommonLookupUserType
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    private String name;
    @Column(name="job_title_literal_key")
    private String jobTitleLiteralKey;
    @Transient
    private String jobTitle;
    @Column(name="operating_company_laborer")
    private Boolean operatingCompanyLaborer;
    @Column(name="default_username")
    private String defaultUserName;
    @Column(name="default_password")
    private String defaultPassword;
    @Column(name="default_roles")
    private String defaultRolesString;
    @Column(name="single_per_directorate")
    private Boolean singlePerDirectorate;
    @Column(name="single_per_site")
    private Boolean singlePerSite;
    @Column(name="single_per_department")
    private Boolean singlePerDepartment;
    @Column(name="single_per_gmp")
    private Boolean singlePerGmp;
    @Column(name="allowed_apps")
    private Integer allowedApps;
    @Column(name="display_order")
    private Integer displayOrder;
    private Boolean status;

    public CommonLookupUserType() {
    }

    public CommonLookupUserType(Enums.USER_TYPE uType) {
        this.id = uType.getId();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getJobTitleLiteralKey() {
        return this.jobTitleLiteralKey;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle(String langCode) {
        if (this.jobTitle == null) {
            this.jobTitle = LocalizationManager.getLiteral(this.jobTitleLiteralKey, langCode);
        }
        return this.jobTitle;
    }

    public Boolean getOperatingCompanyLaborer() {
        return this.operatingCompanyLaborer;
    }

    public String getDefaultUserName() {
        return this.defaultUserName;
    }

    public String getDefaultPassword() {
        return this.defaultPassword;
    }

    public String getDefaultRolesString() {
        return this.defaultRolesString;
    }

    public String[] getDefaultRoles() {
        return Common.getConcatenatedValues(this.getDefaultRolesString(), false);
    }

    public Boolean getSinglePerDirectorate() {
        return this.singlePerDirectorate;
    }

    public Boolean getSinglePerSite() {
        return this.singlePerSite;
    }

    public Boolean getSinglePerDepartment() {
        return this.singlePerDepartment;
    }

    public Boolean getSinglePerGmp() {
        return this.singlePerGmp;
    }

    public Integer getAllowedApps() {
        return this.allowedApps;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public Boolean getStatus() {
        return this.status;
    }
}
