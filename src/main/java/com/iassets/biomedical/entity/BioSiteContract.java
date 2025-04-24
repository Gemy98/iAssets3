/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.AbstractSiteContract;
import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="bio_site_contract")
@NamedQueries(value={@NamedQuery(name="BioSiteContract.findAll", query="SELECT s FROM BioSiteContract s")})
public class BioSiteContract
extends AbstractSiteContract
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="takeover_report_scan")
    private String takeoverReportScan;
    @Column(name="bioeng_no")
    private Integer numOfEngineers;
    @Column(name="biotech_no")
    private Integer numOfTechnicians;
    @Column(name="chem_no")
    private Integer numOfChemists;
    @Column(name="itemp_no")
    private Integer numOfItEmployees;
    @Column(name="driver_no")
    private Integer numOfDrivers;
    @Column(name="total_emp_no")
    private Integer totalNumOfEmployees;
    @Column(name="agent_no")
    private Integer numOfAgents;
    @Column(name="subcontractors_no")
    private Integer numOfSubContractors;
    @Column(name="other_supplier_no")
    private Integer numOfOtherSuppliers;
    @Column(name="work_time_type")
    private Boolean workTimeType;
    @Column(name="work_time_from")
    private LocalTime workTimeFrom;
    @Column(name="work_time_to")
    private LocalTime workTimeTo;

    public String getTakeoverReportScan() {
        return this.takeoverReportScan;
    }

    public void setTakeoverReportScan(String takeoverReportScan) {
        this.takeoverReportScan = takeoverReportScan;
    }

    public Integer getNumOfEngineers() {
        return this.numOfEngineers;
    }

    public void setNumOfEngineers(Integer numOfEngineers) {
        this.numOfEngineers = numOfEngineers;
    }

    public Integer getNumOfTechnicians() {
        return this.numOfTechnicians;
    }

    public void setNumOfTechnicians(Integer numOfTechnicians) {
        this.numOfTechnicians = numOfTechnicians;
    }

    public Integer getNumOfChemists() {
        return this.numOfChemists;
    }

    public void setNumOfChemists(Integer numOfChemists) {
        this.numOfChemists = numOfChemists;
    }

    public Integer getNumOfItEmployees() {
        return this.numOfItEmployees;
    }

    public void setNumOfItEmployees(Integer numOfItEmployees) {
        this.numOfItEmployees = numOfItEmployees;
    }

    public Integer getNumOfDrivers() {
        return this.numOfDrivers;
    }

    public void setNumOfDrivers(Integer numOfDrivers) {
        this.numOfDrivers = numOfDrivers;
    }

    public Integer getTotalNumOfEmployees() {
        return this.totalNumOfEmployees;
    }

    public void setTotalNumOfEmployees(Integer totalNumOfEmployees) {
        this.totalNumOfEmployees = totalNumOfEmployees;
    }

    public Integer getNumOfAgents() {
        return this.numOfAgents;
    }

    public void setNumOfAgents(Integer numOfAgents) {
        this.numOfAgents = numOfAgents;
    }

    public Integer getNumOfSubContractors() {
        return this.numOfSubContractors;
    }

    public void setNumOfSubContractors(Integer numOfSubContractors) {
        this.numOfSubContractors = numOfSubContractors;
    }

    public Integer getNumOfOtherSuppliers() {
        return this.numOfOtherSuppliers;
    }

    public void setNumOfOtherSuppliers(Integer numOfOtherSuppliers) {
        this.numOfOtherSuppliers = numOfOtherSuppliers;
    }

    public Boolean getWorkTimeType() {
        return this.workTimeType;
    }

    public void setWorkTimeType(Boolean workTimeType) {
        this.workTimeType = workTimeType;
    }

    public LocalTime getWorkTimeFrom() {
        return this.workTimeFrom;
    }

    public void setWorkTimeFrom(LocalTime workTimeFrom) {
        this.workTimeFrom = workTimeFrom;
    }

    public LocalTime getWorkTimeTo() {
        return this.workTimeTo;
    }

    public void setWorkTimeTo(LocalTime workTimeTo) {
        this.workTimeTo = workTimeTo;
    }

    public int hashCode() {
        return 31 + (this.getId() == null ? 0 : this.getId().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BioSiteContract)) {
            return false;
        }
        BioSiteContract other = (BioSiteContract)obj;
        if (this.getId() != null && other.getId() != null) {
            return this.getId().equals(other.getId());
        }
        return false;
    }
}
