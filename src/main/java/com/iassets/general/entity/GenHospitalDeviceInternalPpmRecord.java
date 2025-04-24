/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.general.entity;

import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonUser;
import com.iassets.general.entity.AbstractInternalPPMRecord;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="gen_hospital_device_internal_ppm_record")
@NamedQuery(name="GenHospitalDeviceInternalPpmRecord.findAll", query="SELECT g FROM GenHospitalDeviceInternalPpmRecord g")
public class GenHospitalDeviceInternalPpmRecord
extends AbstractInternalPPMRecord
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="ppm_status")
    private Integer ppmStatus;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="planned_ppm_date")
    private Date plannedPpmDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="actual_ppm_date")
    private Date actualPpmDate;
    @ManyToOne
    @JoinColumn(name="responsible_employee_id")
    private CommonEmployee responsibleEmployee;
    @Column(name="checklist_report_url")
    private String checklistReportUrl;
    @Column(name="incompletion_reason")
    private String incompletionReason;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    private Date createdIn;
    @Column(name="checklist_id")
    private Integer checklistId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lookup_cat_id", insertable=false, updatable=false)
    protected GenLookupJobOrderCategory lookupCategory;
    @Column(name="lookup_cat_id")
    protected Integer lookupCategoryId;

    public Integer getPpmStatus() {
        return this.ppmStatus;
    }

    public void setPpmStatus(Integer ppmStatus) {
        this.ppmStatus = ppmStatus;
    }

    public Date getPlannedPpmDate() {
        return this.plannedPpmDate;
    }

    public void setPlannedPpmDate(Date plannedPpmDate) {
        this.plannedPpmDate = plannedPpmDate;
    }

    public Date getActualPpmDate() {
        return this.actualPpmDate;
    }

    public void setActualPpmDate(Date actualPpmDate) {
        this.actualPpmDate = actualPpmDate;
    }

    public CommonEmployee getResponsibleEmployee() {
        return this.responsibleEmployee;
    }

    public void setResponsibleEmployee(CommonEmployee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public String getChecklistReportUrl() {
        return this.checklistReportUrl;
    }

    public void setChecklistReportUrl(String checklistReportUrl) {
        this.checklistReportUrl = checklistReportUrl;
    }

    public String getIncompletionReason() {
        return this.incompletionReason;
    }

    public void setIncompletionReason(String incompletionReason) {
        this.incompletionReason = incompletionReason;
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

    public Integer getChecklistId() {
        return this.checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public GenLookupJobOrderCategory getLookupCategory() {
        return this.lookupCategory;
    }

    public void setLookupCategory(GenLookupJobOrderCategory lookupCategory) {
        this.lookupCategory = lookupCategory;
    }

    public Integer getLookupCategoryId() {
        return this.lookupCategoryId;
    }

    public void setLookupCategoryId(Integer lookupCategoryId) {
        this.lookupCategoryId = lookupCategoryId;
    }
}
