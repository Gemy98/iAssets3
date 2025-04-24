/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.OneToOne
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonOperatingCompany;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@MappedSuperclass
public class AbstractSiteContract
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @OneToOne
    @JoinColumn(name="site_id")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonSite site;
    @Column(name="app_supported_lang")
    protected String appSupportedLang;
    @ManyToOne
    @JoinColumn(name="operating_company_id")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonOperatingCompany operatingCompany;
    @Column(name="contract_length_in_years")
    protected Integer contractLengthInYears;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="contract_start_date")
    protected Date contractStartDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="contract_end_date")
    protected Date contractEndDate;
    @Column(name="spare_parts_value")
    protected Float sparePartsValue;
    @Column(name="contract_total_value")
    protected Float contractTotalValue;
    @Column(name="ta3mid_no")
    protected String ta3midNo;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="ta3mid_date")
    protected Date ta3midDate;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;
    @ManyToOne
    @JoinColumn(name="last_modified_by")
    protected CommonUser lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    protected Date lastModifiedIn;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="actual_contract_end_date")
    protected Date actualContractEndDate;
    @Column(name="license_status")
    protected Integer licenseStatus;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public String getAppSupportedLang() {
        return this.appSupportedLang;
    }

    public void setAppSupportedLang(String appSupportedLang) {
        this.appSupportedLang = appSupportedLang;
    }

    public CommonOperatingCompany getOperatingCompany() {
        return this.operatingCompany;
    }

    public void setOperatingCompany(CommonOperatingCompany operatingCompany) {
        this.operatingCompany = operatingCompany;
    }

    public Integer getContractLengthInYears() {
        return this.contractLengthInYears;
    }

    public void setContractLengthInYears(Integer contractLengthInYears) {
        this.contractLengthInYears = contractLengthInYears;
    }

    public Date getContractStartDate() {
        return this.contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return this.contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Float getSparePartsValue() {
        return this.sparePartsValue;
    }

    public void setSparePartsValue(Float sparePartsValue) {
        this.sparePartsValue = sparePartsValue;
    }

    public Float getContractTotalValue() {
        return this.contractTotalValue;
    }

    public void setContractTotalValue(Float contractTotalValue) {
        this.contractTotalValue = contractTotalValue;
    }

    public String getTa3midNo() {
        return this.ta3midNo;
    }

    public void setTa3midNo(String ta3midNo) {
        this.ta3midNo = ta3midNo;
    }

    public Date getTa3midDate() {
        return this.ta3midDate;
    }

    public void setTa3midDate(Date ta3midDate) {
        this.ta3midDate = ta3midDate;
    }

    public Integer getLicenseStatus() {
        return this.licenseStatus;
    }

    public void setLicenseStatus(Integer licenseStatus) {
        this.licenseStatus = licenseStatus;
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

    public Date getActualContractEndDate() {
        return this.actualContractEndDate;
    }

    public void setActualContractEndDate(Date actualContractEndDate) {
        this.actualContractEndDate = actualContractEndDate;
    }
}
