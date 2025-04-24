/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  javax.persistence.Transient
 *  org.hibernate.annotations.LazyCollection
 *  org.hibernate.annotations.LazyCollectionOption
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 *  org.hibernate.annotations.Where
 */
package com.iassets.common.entity;

import com.iassets.biomedical.entity.BioSiteContract;
import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonOperatingCompany;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenSiteContract;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Cacheable
@Entity
@Table(name="common_site")
@NamedQueries(value={@NamedQuery(name="CommonSite.findAll", query="SELECT s FROM CommonSite s Where s.status=true"), @NamedQuery(name="CommonSite.findActiveBioSitesOfDirectorate", query="SELECT s FROM CommonSite s WHERE s.status=true AND s.directorate.id=:directorateId AND s.bioSiteContract.operatingCompany.id IS NOT NULL AND s.bioSiteContract.operatingCompany.id <> 0"), @NamedQuery(name="CommonSite.findActiveGenSitesOfDirectorate", query="SELECT s FROM CommonSite s WHERE s.status=true AND s.directorate.id=:directorateId AND s.genSiteContract.operatingCompany.id IS NOT NULL AND s.genSiteContract.operatingCompany.id <> 0")})
public class CommonSite
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="directorate_id")
    private CommonDirectorate directorate;
    @Column(name="user_name_space")
    private String userNameSpace;
    @Column(name="bed_no")
    private Integer bedNo;
    @Column(name="contains_several_locations")
    private Boolean containsSeveralLocations;
    @Column(name="affiliated_health_centers_no")
    private Integer affiliatedHealthCentersNo;
    @Column(name="affiliated_hospitals_no")
    private Integer affiliatedHospitalsNo;
    @OneToOne(mappedBy="site")
    @Where(clause="license_status <> 0")
    @NotFound(action=NotFoundAction.IGNORE)
    private BioSiteContract bioSiteContract;
    @OneToOne(mappedBy="site")
    @Where(clause="license_status <> 0")
    @NotFound(action=NotFoundAction.IGNORE)
    private GenSiteContract genSiteContract;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="site")
    @Where(clause="status = 1")
    private List<CommonSiteEmployee> siteEmployees;
    @Transient
    private CommonEmployee directorateSuperAdmin;
    @Transient
    private CommonEmployee hospitalDirector;
    @Transient
    private CommonEmployee hospitalAssistantDirector;
    @Transient
    private CommonEmployee bioDirectorateAdmin;
    @Transient
    private List<CommonEmployee> bioDirectorateSupervisors;
    @Transient
    private CommonEmployee bioDepartmentHead;
    @Transient
    private List<CommonEmployee> bioDepartmentSupervisors;
    @Transient
    private CommonEmployee bioSiteManager;
    @Transient
    private CommonEmployee genDirectorateAdmin;
    @Transient
    private List<CommonEmployee> genDirectorateSupervisors;
    @Transient
    private CommonEmployee genDepartmentHead;
    @Transient
    private List<CommonEmployee> genDepartmentSupervisors;
    @Transient
    private CommonEmployee genSiteManager;

    public CommonDirectorate getDirectorate() {
        return this.directorate;
    }

    public void setDirectorate(CommonDirectorate directorate) {
        this.directorate = directorate;
    }

    public String getUserNameSpace() {
        return this.userNameSpace;
    }

    public Integer getBedNo() {
        return this.bedNo;
    }

    public void setBedNo(Integer bedNo) {
        this.bedNo = bedNo;
    }

    public Boolean getContainsSeveralLocations() {
        return this.containsSeveralLocations;
    }

    public void setContainsSeveralLocations(Boolean containsSeveralLocations) {
        this.containsSeveralLocations = containsSeveralLocations;
    }

    public Integer getAffiliatedHealthCentersNo() {
        return this.affiliatedHealthCentersNo;
    }

    public void setAffiliatedHealthCentersNo(Integer affiliatedHealthCentersNo) {
        this.affiliatedHealthCentersNo = affiliatedHealthCentersNo;
    }

    public Integer getAffiliatedHospitalsNo() {
        return this.affiliatedHospitalsNo;
    }

    public void setAffiliatedHospitalsNo(Integer affiliatedHospitalsNo) {
        this.affiliatedHospitalsNo = affiliatedHospitalsNo;
    }

    public String getBioAppSupportedLang() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getAppSupportedLang();
    }

    public CommonOperatingCompany getBioOperatingCompany() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getOperatingCompany();
    }

    public void setBioOperatingCompany(CommonOperatingCompany bioOperatingCompany) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setOperatingCompany(bioOperatingCompany);
        }
    }

    public Date getBioContractStartDate() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getContractStartDate();
    }

    public void setBioContractStartDate(Date bioContractStartDate) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setContractStartDate(bioContractStartDate);
        }
    }

    public Date getBioContractEndDate() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getContractEndDate();
    }

    public void setBioContractEndDate(Date bioContractEndDate) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setContractEndDate(bioContractEndDate);
        }
    }

    public Float getBioSparePartsValue() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getSparePartsValue();
    }

    public void setBioSparePartsValue(Float bioSparePartsValue) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setSparePartsValue(bioSparePartsValue);
        }
    }

    public Float getBioContractTotalValue() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getContractTotalValue();
    }

    public void setBioContractTotalValue(Float bioContractTotalValue) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setContractTotalValue(bioContractTotalValue);
        }
    }

    public String getBioTa3midNo() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getTa3midNo();
    }

    public void setBioTa3midNo(String bioTa3midNo) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setTa3midNo(bioTa3midNo);
        }
    }

    public Date getBioTa3midDate() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getTa3midDate();
    }

    public void setBioTa3midDate(Date bioTa3midDate) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setTa3midDate(bioTa3midDate);
        }
    }

    public String getBioTakeoverReportScan() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getTakeoverReportScan();
    }

    public void setBioTakeoverReportScan(String bioTakeoverReportScan) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setTakeoverReportScan(bioTakeoverReportScan);
        }
    }

    public Integer getBioLicenseStatus() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getLicenseStatus();
    }

    public void setBioLicenseStatus(Integer bioLicenseStatus) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setLicenseStatus(bioLicenseStatus);
        }
    }

    public String getGenAppSupportedLang() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getAppSupportedLang();
    }

    public CommonOperatingCompany getGenOperatingCompany() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getOperatingCompany();
    }

    public void setGenOperatingCompany(CommonOperatingCompany genOperatingCompany) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setOperatingCompany(genOperatingCompany);
        }
    }

    public Date getGenContractStartDate() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getContractStartDate();
    }

    public void setGenContractStartDate(Date genContractStartDate) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setContractStartDate(genContractStartDate);
        }
    }

    public Date getGenContractEndDate() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getContractEndDate();
    }

    public void setGenContractEndDate(Date genContractEndDate) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setContractEndDate(genContractEndDate);
        }
    }

    public Float getGenSparePartsValue() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getSparePartsValue();
    }

    public void setGenSparePartsValue(Float genSparePartsValue) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setSparePartsValue(genSparePartsValue);
        }
    }

    public Float getGenSubcontractorExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getSubcontractorExpenses();
    }

    public void setGenSubcontractorExpenses(Float genSubcontractorExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setSubcontractorExpenses(genSubcontractorExpenses);
        }
    }

    public Float getGenElectricalMechanicalConsumables() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getElectricalMechanicalConsumables();
    }

    public void setGenElectricalMechanicalConsumables(Float genElectricalMechanicalConsumables) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setElectricalMechanicalConsumables(genElectricalMechanicalConsumables);
        }
    }

    public Float getGenMonthlyConsumables() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getMonthlyConsumables();
    }

    public void setGenMonthlyConsumables(Float genMonthlyConsumables) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setMonthlyConsumables(genMonthlyConsumables);
        }
    }

    public Float getGenLaborExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getLaborExpenses();
    }

    public void setGenLaborExpenses(Float genLaborExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setLaborExpenses(genLaborExpenses);
        }
    }

    public Float getGenWaterSupplyExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getWaterSupplyExpenses();
    }

    public void setGenWaterSupplyExpenses(Float genWaterSupplyExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setWaterSupplyExpenses(genWaterSupplyExpenses);
        }
    }

    public Float getGenWashingSupplyExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getWashingSupplyExpenses();
    }

    public void setGenWashingSupplyExpenses(Float genWashingSupplyExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setWashingSupplyExpenses(genWashingSupplyExpenses);
        }
    }

    public Float getGenSweepingSewageExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getSweepingSewageExpenses();
    }

    public void setGenSweepingSewageExpenses(Float genSweepingSewageExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setSweepingSewageExpenses(genSweepingSewageExpenses);
        }
    }

    public Float getGenDevelopementExpenses() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getDevelopementExpenses();
    }

    public void setGenDevelopementExpenses(Float genDevelopementExpenses) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setDevelopementExpenses(genDevelopementExpenses);
        }
    }

    public Float getGenContractTotalValue() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getContractTotalValue();
    }

    public void setGenContractTotalValue(Float genContractTotalValue) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setContractTotalValue(genContractTotalValue);
        }
    }

    public String getGenTa3midNo() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getTa3midNo();
    }

    public void setGenTa3midNo(String genTa3midNo) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setTa3midNo(genTa3midNo);
        }
    }

    public Date getGenTa3midDate() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getTa3midDate();
    }

    public void setGenTa3midDate(Date genTa3midDate) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setTa3midDate(genTa3midDate);
        }
    }

    public Integer getGenLicenseStatus() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getLicenseStatus();
    }

    public void setGenLicenseStatus(Integer genLicenseStatus) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setLicenseStatus(genLicenseStatus);
        }
    }

    public int hashCode() {
        return 31 + (this.getId() == null ? 0 : this.getId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CommonSite)) {
            return false;
        }
        CommonSite other = (CommonSite)obj;
        if (this.getId() != null && other.getId() != null) {
            return this.getId().equals(other.getId());
        }
        return false;
    }

    public String getDirectorateName(String langCode) {
        return this.directorate.getLocalizedName(langCode);
    }

    public String getDirectorateRegionName(String langCode) {
        return this.directorate.getRegionName(langCode);
    }

    public boolean isBioAppActivated() {
        return this.bioSiteContract != null;
    }

    public boolean isGenAppActivated() {
        return this.genSiteContract != null;
    }

    @Override
    public Boolean getStatus() {
        return this.status != false && this.getDirectorate().getStatus() != false;
    }

    private List<CommonEmployee> getActiveEmployeesOfSpecificType(Enums.USER_TYPE desiredUserType) {
        ArrayList<CommonEmployee> employees = null;
        if (this.siteEmployees != null && !this.siteEmployees.isEmpty()) {
            employees = new ArrayList<CommonEmployee>();
            for (CommonSiteEmployee siteEmp : this.siteEmployees) {
                CommonEmployee emp = siteEmp.getEmployee();
                if (!siteEmp.getStatus().booleanValue() || !emp.getStatus().booleanValue() || emp.getUserType().getId().intValue() != desiredUserType.getId()) continue;
                employees.add(emp);
            }
        }
        if (employees != null && !employees.isEmpty()) {
            Collections.sort(employees);
        }
        return employees;
    }

    public CommonEmployee getDirectorateSuperAdmin() {
        List<CommonEmployee> list;
        if (this.directorateSuperAdmin == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.DIRECTORATE_SUPER_ADMIN)) != null && !list.isEmpty()) {
            this.directorateSuperAdmin = list.get(0);
        }
        return this.directorateSuperAdmin;
    }

    public String getDirectorateSuperAdminName(String langCode) {
        CommonEmployee directorateSuperAdmin = this.getDirectorateSuperAdmin();
        if (directorateSuperAdmin != null) {
            return directorateSuperAdmin.getName(langCode);
        }
        return null;
    }

    public CommonEmployee getHospitalDirector() {
        List<CommonEmployee> list;
        if (this.hospitalDirector == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.HOSPITAL_DIRECTOR)) != null && !list.isEmpty()) {
            this.hospitalDirector = list.get(0);
        }
        return this.hospitalDirector;
    }

    public String getHospitalDirectorName(String langCode) {
        CommonEmployee hospitalDirector = this.getHospitalDirector();
        if (hospitalDirector != null) {
            return hospitalDirector.getName(langCode);
        }
        return null;
    }

    public CommonEmployee getHospitalAssistantDirector() {
        if (this.hospitalAssistantDirector == null) {
            List<CommonEmployee> list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.HOSPITAL_ASSISTANT_DIRECTOR);
            this.hospitalAssistantDirector = list != null && !list.isEmpty() ? list.get(0) : this.getHospitalDirector();
        }
        return this.hospitalAssistantDirector;
    }

    public String getHospitalAssistantDirectorName(String langCode) {
        CommonEmployee hospitalAssistantDirector = this.getHospitalAssistantDirector();
        if (hospitalAssistantDirector != null) {
            return hospitalAssistantDirector.getName(langCode);
        }
        return null;
    }

    public CommonEmployee getBioDirectorateAdmin() {
        List<CommonEmployee> list;
        if (this.bioDirectorateAdmin == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ADMIN)) != null && !list.isEmpty()) {
            this.bioDirectorateAdmin = list.get(0);
        }
        return this.bioDirectorateAdmin;
    }

    public String getBioDirectorateAdminName(String langCode) {
        CommonEmployee bioDirectorateAdmin = this.getBioDirectorateAdmin();
        if (bioDirectorateAdmin != null) {
            return bioDirectorateAdmin.getName(langCode);
        }
        return null;
    }

    public List<CommonEmployee> getBioDirectorateSupervisors() {
        if (this.bioDirectorateSupervisors == null) {
            this.bioDirectorateSupervisors = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN);
        }
        return this.bioDirectorateSupervisors;
    }

    public CommonEmployee getBioDepartmentHead() {
        List<CommonEmployee> list;
        if (this.bioDepartmentHead == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_HEAD)) != null && !list.isEmpty()) {
            this.bioDepartmentHead = list.get(0);
        }
        return this.bioDepartmentHead;
    }

    public String getBioDepartmentHeadName(String langCode) {
        CommonEmployee bioDepartmentHead = this.getBioDepartmentHead();
        if (bioDepartmentHead != null) {
            return bioDepartmentHead.getName(langCode);
        }
        return null;
    }

    public List<CommonEmployee> getBioDepartmentSupervisors() {
        if (this.bioDepartmentSupervisors == null) {
            this.bioDepartmentSupervisors = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.BIOMEDICAL_DEPARTMENT_SUPERVISOR);
            if (this.bioDepartmentSupervisors == null || this.bioDepartmentSupervisors.isEmpty()) {
                CommonEmployee e = this.getBioDepartmentHead();
                if (e != null) {
                    this.bioDepartmentSupervisors = new ArrayList<CommonEmployee>();
                    this.bioDepartmentSupervisors.add(e);
                } else {
                    this.bioDepartmentSupervisors = this.getBioDirectorateSupervisors();
                }
            }
        }
        return this.bioDepartmentSupervisors;
    }

    public CommonEmployee getBioSiteManager() {
        List<CommonEmployee> list;
        if (this.bioSiteManager == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER)) != null && !list.isEmpty()) {
            this.bioSiteManager = list.get(0);
        }
        return this.bioSiteManager;
    }

    public String getBioSiteManagerName(String langCode) {
        CommonEmployee bioSiteManager = this.getBioSiteManager();
        if (bioSiteManager != null) {
            return bioSiteManager.getName(langCode);
        }
        return null;
    }

    public CommonEmployee getGenDirectorateAdmin() {
        List<CommonEmployee> list;
        if (this.genDirectorateAdmin == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.GENERAL_DIRECTORATE_ADMIN)) != null && !list.isEmpty()) {
            this.genDirectorateAdmin = list.get(0);
        }
        return this.genDirectorateAdmin;
    }

    public String getGenDirectorateAdminName(String langCode) {
        CommonEmployee genDirectorateAdmin = this.getGenDirectorateAdmin();
        if (genDirectorateAdmin != null) {
            return genDirectorateAdmin.getName(langCode);
        }
        return null;
    }

    public List<CommonEmployee> getGenDirectorateSupervisors() {
        if (this.genDirectorateSupervisors == null) {
            this.genDirectorateSupervisors = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.GENERAL_DIRECTORATE_ASSISTANT_ADMIN);
        }
        return this.genDirectorateSupervisors;
    }

    public CommonEmployee getGenDepartmentHead() {
        List<CommonEmployee> list;
        if (this.genDepartmentHead == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.GENERAL_DEPARTMENT_HEAD)) != null && !list.isEmpty()) {
            this.genDepartmentHead = list.get(0);
        }
        return this.genDepartmentHead;
    }

    public String getGenDepartmentHeadName(String langCode) {
        CommonEmployee genDepartmentHead = this.getGenDepartmentHead();
        if (genDepartmentHead != null) {
            return genDepartmentHead.getName(langCode);
        }
        return null;
    }

    public List<CommonEmployee> getGenDepartmentSupervisors() {
        if (this.genDepartmentSupervisors == null) {
            CommonEmployee e;
            this.genDepartmentSupervisors = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.GENERAL_DEPARTMENT_SUPERVISOR);
            if ((this.genDepartmentSupervisors == null || this.genDepartmentSupervisors.isEmpty()) && (e = this.getGenDepartmentHead()) != null) {
                this.genDepartmentSupervisors = new ArrayList<CommonEmployee>();
                this.genDepartmentSupervisors.add(e);
            }
        }
        return this.genDepartmentSupervisors;
    }

    public CommonEmployee getGenSiteManager() {
        List<CommonEmployee> list;
        if (this.genSiteManager == null && (list = this.getActiveEmployeesOfSpecificType(Enums.USER_TYPE.GENERAL_SITE_MANGER)) != null && !list.isEmpty()) {
            this.genSiteManager = list.get(0);
        }
        return this.genSiteManager;
    }

    public String getGenSiteManagerName(String langCode) {
        CommonEmployee genSiteManager = this.getGenSiteManager();
        if (genSiteManager != null) {
            return genSiteManager.getName(langCode);
        }
        return null;
    }

    public Date getGenPpmRefDate() {
        if (this.getGenPpmRefDate() == null) {
            this.setGenPpmRefDate(this.getGenContractStartDate());
        }
        return this.getGenPpmRefDate();
    }

    public void setGenPpmRefDate(Date gePpmRefData) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setPpmRefDate(gePpmRefData);
        }
    }

    public Integer getBioContractLengthInYears() {
        if (this.bioSiteContract == null) {
            return null;
        }
        return this.bioSiteContract.getContractLengthInYears();
    }

    public void setBioContractLengthInYears(Integer bioContractLengthInYears) {
        if (this.bioSiteContract != null) {
            this.bioSiteContract.setContractLengthInYears(bioContractLengthInYears);
        }
    }

    public Integer getGenContractLengthInYears() {
        if (this.genSiteContract == null) {
            return null;
        }
        return this.genSiteContract.getContractLengthInYears();
    }

    public void setGenContractLengthInYears(Integer genContractLengthInYears) {
        if (this.genSiteContract != null) {
            this.genSiteContract.setContractLengthInYears(genContractLengthInYears);
        }
    }

    public GenSiteContract getGenSiteContract() {
        return this.genSiteContract;
    }

    public void setGenSiteContract(GenSiteContract genSiteContract) {
        this.genSiteContract = genSiteContract;
    }

    public BioSiteContract getBioSiteContract() {
        return this.bioSiteContract;
    }

    public void setBioSiteContract(BioSiteContract bioSiteContract) {
        this.bioSiteContract = bioSiteContract;
    }
}
