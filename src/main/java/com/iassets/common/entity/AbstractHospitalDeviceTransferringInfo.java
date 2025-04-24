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
public abstract class AbstractHospitalDeviceTransferringInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="transfer_date")
    protected Date transferDate;
    @Column(name="transfer_reason")
    protected String transferReason;
    @Column(name="transfer_report_url")
    protected String transferReportUrl;
    @Column(name="transfer_destination")
    protected String transferDestination;
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

    public Date getTransferDate() {
        return this.transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferReason() {
        return this.transferReason;
    }

    public void setTransferReason(String transferReason) {
        this.transferReason = transferReason;
    }

    public String getTransferReportUrl() {
        return this.transferReportUrl;
    }

    public void setTransferReportUrl(String transferReportUrl) {
        this.transferReportUrl = transferReportUrl;
    }

    public String getTransferDestination() {
        return this.transferDestination;
    }

    public void setTransferDestination(String transferDestination) {
        this.transferDestination = transferDestination;
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
