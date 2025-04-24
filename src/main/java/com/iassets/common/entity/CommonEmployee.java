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
 *  javax.persistence.OneToMany
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  javax.persistence.Transient
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonLookupNationality;
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Cacheable
@Entity
@NamedQueries(value={@NamedQuery(name="CommonEmployee.findAll", query="SELECT e FROM CommonEmployee e WHERE e.status = true  ORDER BY e.nameAr"), @NamedQuery(name="CommonEmployee.findEmployeeByIdAndDirectorateId", query="SELECT e FROM CommonEmployee e WHERE e.id = :employeeId AND e.directorate.id = :directorateId AND e.status = true"), @NamedQuery(name="CommonEmployee.findActiveDirectorateSupervisors", query="SELECT e FROM CommonEmployee e WHERE e.status=true AND e.userType.id = :userTypeId AND e.directorate.id=:directorateId  ORDER BY e.nameAr")})
@Table(name="common_employee")
public class CommonEmployee
implements Serializable,
Comparable<CommonEmployee> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="directorate_id")
    private CommonDirectorate directorate;
    @OneToOne
    @JoinColumn(name="department_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private CommonHospitalDepartment department;
    @OneToOne
    @JoinColumn(name="gmp_category_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private GenLookupJobOrderCategory gmpCategory;
    @Column(name="name_ar")
    private String nameAr;
    @Column(name="name_en")
    private String nameEn;
    private String mobile;
    @ManyToOne
    @JoinColumn(name="user_type")
    private CommonLookupUserType userType;
    @Column(name="job_title_name_literal_key")
    private String jobTitleNameLiteralKey;
    @Column(name="moving_team")
    private Boolean movingTeam;
    @Column(name="employee_no")
    private String employeeNo;
    @ManyToOne
    @JoinColumn(name="nationality_id")
    private CommonLookupNationality nationality;
    @Column(name="contract_salary")
    private Float contractSalary;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="employment_date")
    private Date employmentDate;
    @Column(name="approval_scan")
    private String approvalScan;
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
    @Transient
    private CommonSite currentSite;
    @OneToMany(mappedBy="employee")
    private List<CommonSiteEmployee> siteEmployees;
    @Transient
    private List<CommonSite> activeSites;
    @Transient
    private List<Integer> activeSitesIds;
    @Transient
    private Boolean hasActiveSites;
    @Transient
    private List<CommonSite> employeeSitesWithBioAppActivated;
    @Transient
    private List<Integer> employeeSitesIdsWithBioAppActivated;
    @Transient
    private List<CommonSite> employeeSitesWithGenAppActivated;
    @Transient
    private List<Integer> employeeSitesIdsWithGenAppActivated;

    public String getNameAr() {
        return this.nameAr;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setSiteEmployees(List<CommonSiteEmployee> siteEmployees) {
        this.siteEmployees = siteEmployees;
        this.init();
    }

    public List<CommonSiteEmployee> getSiteEmployees() {
        return this.siteEmployees;
    }

    private void init() {
        if (this.siteEmployees != null && !this.siteEmployees.isEmpty()) {
            this.activeSites = new ArrayList<CommonSite>();
            for (CommonSiteEmployee siteEmp : this.siteEmployees) {
                if (!siteEmp.getStatus().booleanValue() || !siteEmp.getSite().getStatus().booleanValue()) continue;
                this.activeSites.add(siteEmp.getSite());
            }
            if (this.activeSites != null && !this.activeSites.isEmpty()) {
                AppUtil.sortLocalizedEntityList(this.activeSites, LocalizationManager.DEFAULT_LANGUAGE_CODE);
            }
            this.activeSitesIds = new ArrayList<Integer>();
            for (CommonSite s : this.getActiveSites()) {
                this.activeSitesIds.add(s.getId());
            }
            this.hasActiveSites = this.activeSites != null && !this.activeSites.isEmpty();
            this.employeeSitesWithBioAppActivated = new ArrayList<CommonSite>();
            for (CommonSite site : this.activeSites) {
                if (!site.isBioAppActivated()) continue;
                this.employeeSitesWithBioAppActivated.add(site);
            }
            this.employeeSitesIdsWithBioAppActivated = new ArrayList<Integer>();
            for (CommonSite s : this.getEmployeeSitesWithBioAppActivated()) {
                this.employeeSitesIdsWithBioAppActivated.add(s.getId());
            }
            this.employeeSitesWithGenAppActivated = new ArrayList<CommonSite>();
            for (CommonSite site : this.activeSites) {
                if (!site.isGenAppActivated()) continue;
                this.employeeSitesWithGenAppActivated.add(site);
            }
            this.employeeSitesIdsWithGenAppActivated = new ArrayList<Integer>();
            for (CommonSite s : this.getEmployeeSitesWithGenAppActivated()) {
                this.employeeSitesIdsWithGenAppActivated.add(s.getId());
            }
        }
    }

    public List<CommonSite> getActiveSites() {
        return this.activeSites;
    }

    public List<Integer> getActiveSitesIds() {
        return this.activeSitesIds;
    }

    public boolean hasActiveSites() {
        return this.hasActiveSites;
    }

    public List<CommonSite> getEmployeeSitesWithBioAppActivated() {
        return this.employeeSitesWithBioAppActivated;
    }

    public List<Integer> getEmployeeSitesIdsWithBioAppActivated() {
        return this.employeeSitesIdsWithBioAppActivated;
    }

    public List<CommonSite> getEmployeeSitesWithGenAppActivated() {
        return this.employeeSitesWithGenAppActivated;
    }

    public List<Integer> getEmployeeSitesIdsWithGenAppActivated() {
        return this.employeeSitesIdsWithGenAppActivated;
    }

    public String getJobTitle(String langCode) {
        if (this.jobTitleNameLiteralKey != null) {
            return LocalizationManager.getLiteral(this.jobTitleNameLiteralKey, langCode);
        }
        return this.userType.getJobTitle(langCode);
    }

    public void setCurrentSite(CommonSite currentSite) {
        this.currentSite = currentSite;
    }

    public CommonSite getCurrentSite() {
        return this.currentSite;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonDirectorate getDirectorate() {
        return this.directorate;
    }

    public void setDirectorate(CommonDirectorate directorate) {
        this.directorate = directorate;
    }

    public CommonHospitalDepartment getDepartment() {
        return this.department;
    }

    public void setDepartment(CommonHospitalDepartment department) {
        this.department = department;
    }

    public GenLookupJobOrderCategory getGmpCategory() {
        return this.gmpCategory;
    }

    public void setGmpCategory(GenLookupJobOrderCategory gmpCategory) {
        this.gmpCategory = gmpCategory;
    }

    public String getName(String langCode) {
        if (langCode.equals(Enums.SUPPORTED_LANGUAGES.ARABIC.getLanguageCode())) {
            return this.nameAr;
        }
        if (langCode.equals(Enums.SUPPORTED_LANGUAGES.ENGLISH.getLanguageCode())) {
            return this.nameEn;
        }
        return null;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public CommonLookupUserType getUserType() {
        return this.userType;
    }

    public void setUserType(CommonLookupUserType userType) {
        this.userType = userType;
    }

    public Boolean getMovingTeam() {
        return this.movingTeam;
    }

    public void setMovingTeam(Boolean movingTeam) {
        this.movingTeam = movingTeam;
    }

    public String getEmployeeNo() {
        return this.employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public CommonLookupNationality getNationality() {
        return this.nationality;
    }

    public void setNationality(CommonLookupNationality nationality) {
        this.nationality = nationality;
    }

    public Float getContractSalary() {
        return this.contractSalary;
    }

    public void setContractSalary(Float contractSalary) {
        this.contractSalary = contractSalary;
    }

    public Date getEmploymentDate() {
        return this.employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getApprovalScan() {
        return this.approvalScan;
    }

    public void setApprovalScan(String approvalScan) {
        this.approvalScan = approvalScan;
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

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
    public int compareTo(CommonEmployee other) {
        return this.nameAr.compareToIgnoreCase(other.nameAr);
    }
}
