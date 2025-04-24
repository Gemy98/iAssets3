/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Cacheable
@Entity
@NamedQueries(value={@NamedQuery(name="CommonUser.findAll", query="SELECT u FROM CommonUser u"), @NamedQuery(name="CommonUser.findById", query="SELECT u FROM CommonUser u INNER JOIN u.employee e INNER JOIN e.siteEmployees s Where u.id=:userId and s.site.id=:siteId"), @NamedQuery(name="CommonUser.findAllActiveBySite", query="SELECT u FROM CommonUser u INNER JOIN u.employee e INNER JOIN e.siteEmployees s WHERE s.site.id=:siteId AND u.status=true AND e.status=true AND e.userType.allowedApps IN (:appTypeId,3) ORDER BY e.userType.displayOrder"), @NamedQuery(name="CommonUser.findByEmployeeId", query="SELECT s FROM CommonUser s WHERE s.employee.id=:employeeId")})
@Table(name="common_user")
public class CommonUser
implements Serializable {
    public static final String jpql = "SELECT u FROM CommonUser u INNER JOIN u.employee e INNER JOIN e.siteEmployees s ";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @Column(name="user_name")
    private String userName;
    private String password;
    @Column(name="initial_password")
    private String initialPassword;
    @OneToOne
    @JoinColumn(name="employee_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private CommonEmployee employee;
    @Column(name="allowed_apps")
    private Integer allowedApps;
    @Column(name="username_change_no")
    private Integer userNameChangeCount;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_username_change_date")
    private Date lastUsernameChangeDate;
    @Column(name="password_change_no")
    private Integer passwordChangeCount;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_password_change_date")
    private Date lastPasswordChangeDate;
    @Column(name="login_no")
    private Integer loginCount;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_login_date")
    private Date lastLoginDate;
    @Column(name="bio_app_welcome_page_url")
    private String bioAppWelcomePageUrl;
    @Column(name="gen_app_welcome_page_url")
    private String genAppWelcomePageUrl;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="last_modified_by")
    protected CommonUser lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    protected Date lastModifiedIn;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="deleted_by")
    protected CommonUser deletedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="deleted_in")
    protected Date deletedIn;
    private Boolean status;

    public List<CommonSite> getActiveSites() {
        return this.employee.getActiveSites();
    }

    public List<Integer> getActiveSitesIds() {
        return this.employee.getActiveSitesIds();
    }

    public CommonSite getCurrentSite() {
        return this.employee.getCurrentSite();
    }

    public CommonHospitalDepartment getDepartment() {
        if (this.employee != null) {
            return this.employee.getDepartment();
        }
        return null;
    }

    public GenLookupJobOrderCategory getGmpCategory() {
        if (this.employee != null) {
            return this.employee.getGmpCategory();
        }
        return null;
    }

    public String getJobTitle(String langCode) {
        return this.employee.getJobTitle(langCode);
    }

    public String getMobile() {
        return this.employee.getMobile();
    }

    public Boolean getMovingTeam() {
        return this.employee.getMovingTeam();
    }

    public String getName(String langCode) {
        return this.employee.getName(langCode);
    }

    public List<Integer> getSitesIdsWithBioAppActivated() {
        return this.employee.getEmployeeSitesIdsWithBioAppActivated();
    }

    public List<Integer> getSitesIdsWithGenAppActivated() {
        return this.employee.getEmployeeSitesIdsWithGenAppActivated();
    }

    public List<CommonSite> getSitesWithBioAppActivated() {
        return this.employee.getEmployeeSitesWithBioAppActivated();
    }

    public List<CommonSite> getSitesWithGenAppActivated() {
        return this.employee.getEmployeeSitesWithGenAppActivated();
    }

    public CommonDirectorate getUserDirectorate() {
        return this.employee.getDirectorate();
    }

    public String getUserDirectorateName(String langCode) {
        CommonDirectorate d = this.getUserDirectorate();
        if (d != null) {
            return d.getLocalizedName(langCode);
        }
        return null;
    }

    public Integer getUserType() {
        return this.employee.getUserType().getId();
    }

    public boolean hasActiveSites() {
        return this.employee.hasActiveSites();
    }

    public boolean hasSitesWithBioAppActivated() {
        List<CommonSite> list = this.getSitesWithBioAppActivated();
        return list != null && !list.isEmpty();
    }

    public boolean hasSitesWithGenAppActivated() {
        List<CommonSite> list = this.getSitesWithGenAppActivated();
        return list != null && !list.isEmpty();
    }

    public void setCurrentSite(CommonSite currentSite) {
        this.employee.setCurrentSite(currentSite);
    }

    public Integer getId() {
        return this.id;
    }

    public void setAllowedApps(Integer allowedApps) {
        this.allowedApps = allowedApps;
    }

    public Integer getAllowedApps() {
        if (this.allowedApps == null || this.allowedApps == 0) {
            return this.employee.getUserType().getAllowedApps();
        }
        return this.allowedApps;
    }

    public CommonEmployee getEmployee() {
        return this.employee;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean getStatus() {
        return this.status != false && this.employee.getStatus() != false;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInitialPassword() {
        return this.initialPassword;
    }

    public void setInitialPassword(String initialPassword) {
        this.initialPassword = initialPassword;
    }

    public Integer getPasswordChangeCount() {
        return this.passwordChangeCount;
    }

    public void setPasswordChangeCount(Integer passwordChangeCount) {
        this.passwordChangeCount = passwordChangeCount;
    }

    public Date getLastPasswordChangeDate() {
        return this.lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public Integer getLoginCount() {
        return this.loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getBioAppWelcomePageUrl() {
        return this.bioAppWelcomePageUrl;
    }

    public String getGenAppWelcomePageUrl() {
        return this.genAppWelcomePageUrl;
    }

    public CommonUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CommonUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedIn() {
        return this.createdIn;
    }

    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public CommonUser getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(CommonUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedIn() {
        return this.lastModifiedIn;
    }

    public void setLastModifiedIn(Date lastModifiedIn) {
        this.lastModifiedIn = lastModifiedIn;
    }

    public CommonUser getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(CommonUser deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedIn() {
        return this.deletedIn;
    }

    public void setDeletedIn(Date deletedIn) {
        this.deletedIn = deletedIn;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployee(CommonEmployee employee) {
        this.employee = employee;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUserNameChangeCount() {
        return this.userNameChangeCount;
    }

    public void setUsernameChangeCount(Integer usernameChangeCount) {
        this.userNameChangeCount = usernameChangeCount;
    }

    public Date getLastUsernameChangeDate() {
        return this.lastUsernameChangeDate;
    }

    public void setLastUsernameChangeDate(Date lastUsernameChangeDate) {
        this.lastUsernameChangeDate = lastUsernameChangeDate;
    }
}
