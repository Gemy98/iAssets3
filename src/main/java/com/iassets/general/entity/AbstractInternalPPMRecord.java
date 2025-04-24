/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.FetchType
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.MappedSuperclass
 */
package com.iassets.general.entity;

import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmSchedule;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractInternalPPMRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;
    @Column(name="site_id")
    protected Integer siteId;
    @Column(name="device_id")
    protected Integer deviceId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="device_id", insertable=false, updatable=false)
    protected GenHospitalDevice hospitalDevice;
    @Column(name="scheduled_ppm_id")
    protected Integer scheduledPpmId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="scheduled_ppm_id", insertable=false, updatable=false)
    protected GenHospitalDeviceInternalPpmSchedule scheduledPPM;
    @Column(name="ppm_period_id")
    protected Integer ppmPeriodId;
    @Column(name="ppm_period_priority")
    protected Integer ppmPeriodPriority;
    @Column(name="week_no")
    protected Integer weekNo;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public GenHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public Integer getScheduledPpmId() {
        return this.scheduledPpmId;
    }

    public void setScheduledPpmId(Integer scheduledPpmId) {
        this.scheduledPpmId = scheduledPpmId;
    }

    public GenHospitalDeviceInternalPpmSchedule getScheduledPPM() {
        return this.scheduledPPM;
    }

    public void setScheduledPPM(GenHospitalDeviceInternalPpmSchedule scheduledPPM) {
        this.scheduledPPM = scheduledPPM;
    }

    public Integer getPpmPeriodId() {
        return this.ppmPeriodId;
    }

    public void setPpmPeriodId(Integer ppmPeriodId) {
        this.ppmPeriodId = ppmPeriodId;
    }

    public Integer getPpmPeriodPriority() {
        return this.ppmPeriodPriority;
    }

    public void setPpmPeriodPriority(Integer ppmPeriodPriority) {
        this.ppmPeriodPriority = ppmPeriodPriority;
    }

    public Integer getWeekNo() {
        return this.weekNo;
    }

    public void setWeekNo(Integer weekNo) {
        this.weekNo = weekNo;
    }
}
