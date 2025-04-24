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
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonUser;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractHospitalDevicePpmDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Column(name="visit_month")
    protected Integer visitMonth;
    @Column(name="visit_year")
    protected Integer visitYear;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="visit_date")
    protected Date visitDate;
    @Column(name="visit_report_URL")
    protected String visitReportUrl;
    @Column(name="visit_cost")
    protected Float visitCost;
    protected String subcontractor;
    @Column(name="delay_penalty")
    protected Float delayPenalty;
    @Column(name="absence_reason")
    protected String absenceReason;
    @Column(name="absence_report_URL")
    protected String absenceReportUrl;
    @Column(name="visit_status")
    protected Integer visitStatus;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVisitMonth() {
        return this.visitMonth;
    }

    public void setVisitMonth(Integer visitMonth) {
        this.visitMonth = visitMonth;
    }

    public Integer getVisitYear() {
        return this.visitYear;
    }

    public void setVisitYear(Integer visitYear) {
        this.visitYear = visitYear;
    }

    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitReportUrl() {
        return this.visitReportUrl;
    }

    public void setVisitReportUrl(String visitReportUrl) {
        this.visitReportUrl = visitReportUrl;
    }

    public Float getVisitCost() {
        return this.visitCost;
    }

    public void setVisitCost(Float visitCost) {
        this.visitCost = visitCost;
    }

    public String getSubcontractor() {
        return this.subcontractor;
    }

    public void setSubcontractor(String subcontractor) {
        this.subcontractor = subcontractor;
    }

    public Float getDelayPenalty() {
        return this.delayPenalty;
    }

    public void setDelayPenalty(Float delayPenalty) {
        this.delayPenalty = delayPenalty;
    }

    public String getAbsenceReason() {
        return this.absenceReason;
    }

    public void setAbsenceReason(String absenceReason) {
        this.absenceReason = absenceReason;
    }

    public String getAbsenceReportUrl() {
        return this.absenceReportUrl;
    }

    public void setAbsenceReportUrl(String absenceReportUrl) {
        this.absenceReportUrl = absenceReportUrl;
    }

    public Integer getVisitStatus() {
        return this.visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
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
}
