/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenInternalPpmChecklist;
import com.iassets.general.entity.GenLookupInternalPpmPeriod;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gen_hospital_device_internal_ppm_schedule")
@NamedQuery(name="GenHospitalDeviceInternalPpmSchedule.findAll", query="SELECT g FROM GenHospitalDeviceInternalPpmSchedule g")
public class GenHospitalDeviceInternalPpmSchedule
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="device_id")
    private GenHospitalDevice hospitalDevice;
    @ManyToOne
    @JoinColumn(name="ppm_period_id")
    private GenLookupInternalPpmPeriod ppmPeriod;
    @Column(name="week_no")
    private Integer weekNo;
    @Column(name="checklist_file_url")
    private String checklistFileUrl;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="checklist_id")
    private GenInternalPpmChecklist checkList;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GenHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public GenLookupInternalPpmPeriod getPpmPeriod() {
        return this.ppmPeriod;
    }

    public void setPpmPeriod(GenLookupInternalPpmPeriod ppmPeriod) {
        this.ppmPeriod = ppmPeriod;
    }

    public Integer getWeekNo() {
        return this.weekNo;
    }

    public void setWeekNo(Integer weekNo) {
        this.weekNo = weekNo;
    }

    public String getChecklistFileUrl() {
        return this.checklistFileUrl;
    }

    public void setChecklistFileUrl(String checklistFileUrl) {
        this.checklistFileUrl = checklistFileUrl;
    }

    public GenInternalPpmChecklist getCheckList() {
        return this.checkList;
    }

    public void setCheckList(GenInternalPpmChecklist checkList) {
        this.checkList = checkList;
    }
}
