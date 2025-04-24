/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.FetchType
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractHospitalDeviceScrappingInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Column(name="device_condition")
    protected String deviceCondition;
    @Column(name="scrapping_reason_report_url")
    protected String scrappingReasonReportUrl;
    @Column(name="scrapping_final_report_url")
    protected String scrappingFinalReportUrl;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="scrapping_date")
    protected Date scrappingDate;
    @ManyToOne(fetch=FetchType.LAZY)
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

    public String getDeviceCondition() {
        return this.deviceCondition;
    }

    public void setDeviceCondition(String deviceCondition) {
        this.deviceCondition = deviceCondition;
    }

    public String getScrappingReasonReportUrl() {
        return this.scrappingReasonReportUrl;
    }

    public void setScrappingReasonReportUrl(String scrappingReasonReportUrl) {
        this.scrappingReasonReportUrl = scrappingReasonReportUrl;
    }

    public String getScrappingFinalReportUrl() {
        return this.scrappingFinalReportUrl;
    }

    public void setScrappingFinalReportUrl(String scrappingFinalReportUrl) {
        this.scrappingFinalReportUrl = scrappingFinalReportUrl;
    }

    public Date getScrappingDate() {
        return this.scrappingDate;
    }

    public void setScrappingDate(Date scrappingDate) {
        this.scrappingDate = scrappingDate;
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
