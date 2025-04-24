/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedAttributeNode
 *  javax.persistence.NamedEntityGraph
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.general.entity;

import com.iassets.general.entity.AbstractInternalPPMRecord;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="gen_hospital_device_internal_ppm_notification_schedule")
@NamedEntityGraph(name="GenHospitalDeviceInternalPpmNotificationSchedule.graph.fetchAll", attributeNodes={@NamedAttributeNode(value="hospitalDevice"), @NamedAttributeNode(value="scheduledPPM")})
@NamedQueries(value={@NamedQuery(name="GenHospitalDeviceInternalPpmNotificationSchedule.findAll", query="SELECT g FROM GenHospitalDeviceInternalPpmNotificationSchedule g"), @NamedQuery(name="GenHospitalDeviceInternalPpmNotificationSchedule.deleteDeviceInternalPPMSchedule", query="DELETE FROM GenHospitalDeviceInternalPpmNotificationSchedule g WHERE g.deviceId = :deviceId AND  g.recorded IS NULL"), @NamedQuery(name="GenHospitalDeviceInternalPpmNotificationSchedule.getSiteNotifications", query="SELECT g FROM GenHospitalDeviceInternalPpmNotificationSchedule g WHERE g.siteId =:site_id AND g.plannedDate <:planned_date AND g.notificationValidTo >= :planned_date AND g.recorded IS NULL"), @NamedQuery(name="GenHospitalDeviceInternalPpmNotificationSchedule.getSiteNotificationsPerDepartment", query="SELECT g FROM GenHospitalDeviceInternalPpmNotificationSchedule g WHERE g.siteId =:site_id AND g.plannedDate <:planned_date AND g.notificationValidTo >= :planned_date AND g.recorded IS NULL and g.lookupCategoryId=:catId"), @NamedQuery(name="GenHospitalDeviceInternalPpmNotificationSchedule.getDeviceInternalPPMSchedule", query="SELECT g FROM GenHospitalDeviceInternalPpmNotificationSchedule g WHERE g.siteId =:site_id AND g.hospitalDevice.id=:device_id AND g.plannedDate <:planned_date AND g.notificationValidTo >= :planned_date AND g.recorded IS NULL")})
public class GenHospitalDeviceInternalPpmNotificationSchedule
extends AbstractInternalPPMRecord
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="planned_date")
    private Date plannedDate;
    @Column(name="planned_month")
    private Integer plannedMonth;
    @Column(name="planned_year")
    private Integer plannedYear;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="notification_valid_to")
    private Date notificationValidTo;
    private Boolean recorded;
    @Column(name="current_notification_flag")
    private Integer currentNotificationFlag;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lookup_cat_id", insertable=false, updatable=false)
    protected GenLookupJobOrderCategory lookupCategory;
    @Column(name="lookup_cat_id")
    protected Integer lookupCategoryId;

    public Date getPlannedDate() {
        return this.plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Integer getPlannedMonth() {
        return this.plannedMonth;
    }

    public void setPlannedMonth(Integer plannedMonth) {
        this.plannedMonth = plannedMonth;
    }

    public Integer getPlannedYear() {
        return this.plannedYear;
    }

    public void setPlannedYear(Integer plannedYear) {
        this.plannedYear = plannedYear;
    }

    public Date getNotificationValidTo() {
        return this.notificationValidTo;
    }

    public void setNotificationValidTo(Date notificationValidTo) {
        this.notificationValidTo = notificationValidTo;
    }

    public Boolean getRecorded() {
        return this.recorded;
    }

    public void setRecorded(Boolean recorded) {
        this.recorded = recorded;
    }

    public Integer getCurrentNotificationFlag() {
        return this.currentNotificationFlag;
    }

    public void setCurrentNotificationFlag(Integer currentNotificationFlag) {
        this.currentNotificationFlag = currentNotificationFlag;
    }

    public String toString() {
        return "GenHospitalDeviceInternalPpmNotificationSchedule [id=" + this.id + ", currentNotificationFlag=" + this.currentNotificationFlag + ", hospitalDevice=" + this.hospitalDevice + ", notificationValidTo=" + this.notificationValidTo + ", plannedDate=" + this.plannedDate + ", plannedMonth=" + this.plannedMonth + ", plannedYear=" + this.plannedYear + ", ppmPeriodId=" + this.ppmPeriodId + ", recorded=" + this.recorded + ", siteId=" + this.siteId + ", weekNo=" + this.weekNo + "]";
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
