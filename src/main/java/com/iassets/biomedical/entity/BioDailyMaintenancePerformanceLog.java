/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.CommonSite;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bio_daily_maintenance_performance_log")
@NamedQueries(value={@NamedQuery(name="BioDailyMaintenancePerformanceLog.findAll", query="SELECT b FROM BioDailyMaintenancePerformanceLog b"), @NamedQuery(name="BioDailyMaintenancePerformanceLog.findMaintenancePerformanceLogRecordByDate", query="SELECT b FROM BioDailyMaintenancePerformanceLog b where b.site.id=:siteId and DATE(b.date)=DATE(:date) ")})
public class BioDailyMaintenancePerformanceLog
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="cancelled_jo_no")
    private Integer cancelledJoNo;
    @Column(name="classA_cancelled_jo_no")
    private Integer classA_CancelledJoNo;
    @Column(name="classA_closed_jo_no")
    private Integer classA_ClosedJoNo;
    @Column(name="classB_cancelled_jo_no")
    private Integer classB_CancelledJoNo;
    @Column(name="classB_closed_jo_no")
    private Integer classB_ClosedJoNo;
    @Column(name="closed_jo_no")
    private Integer closedJoNo;
    @Column(name="created_by")
    private Integer createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    private Date createdIn;
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date date;
    @Column(name="date_month")
    private Integer dateMonth;
    @Column(name="date_year")
    private Integer dateYear;
    @Column(name="last_modified_by")
    private Integer lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    private Date lastModifiedIn;
    @Column(name="no_of_handeled_mrequests_outside_whrs")
    private int noOfHandeledMrequestsOutsideWhrs;
    @Column(name="no_of_urgent_mrequests_outside_whrs")
    private int noOfUrgentMrequestsOutsideWhrs;
    @Column(name="other_month_cancelled_jo_no")
    private int otherMonthCancelledJoNo;
    @Column(name="other_month_classA_cancelled_jo_no")
    private int otherMonthClassA_cancelledJoNo;
    @Column(name="other_month_classA_closed_jo_no")
    private Integer otherMonthClassA_closedJoNo;
    @Column(name="other_month_classB_cancelled_jo_no")
    private Integer otherMonthClassB_cancelledJoNo;
    @Column(name="other_month_classB_closed_jo_no")
    private Integer otherMonthClassB_closedJoNo;
    @Column(name="other_month_closed_jo_no")
    private int otherMonthClosedJoNo;
    @Column(name="ppm_not_happened_visits_no")
    private int ppmNotHappenedVisitsNo;
    @Column(name="site_id")
    @JoinColumn(name="site_id")
    protected CommonSite site;
    @Column(name="total_classA_opened_jo_no")
    private Integer totalClassA_openedJoNo;
    @Column(name="total_classB_opened_jo_no")
    private Integer totalClassB_openedJoNo;
    @Column(name="total_opened_jo_no")
    private int totalOpenedJoNo;
    @Column(name="undermaintenance_classA_device_no")
    private Integer undermaintenanceClassA_deviceNo;
    @Column(name="undermaintenance_classB_device_no")
    private int undermaintenanceClassB_deviceNo;
    @Column(name="working_classA_device_no")
    private int workingClassA_deviceNo;
    @Column(name="working_classB_device_no")
    private Integer workingClassB_deviceNo;
    @Column(name="directorate_id")
    private Integer directorateId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCancelledJoNo() {
        return this.cancelledJoNo;
    }

    public void setCancelledJoNo(Integer cancelledJoNo) {
        this.cancelledJoNo = cancelledJoNo;
    }

    public Integer getClassA_CancelledJoNo() {
        return this.classA_CancelledJoNo;
    }

    public void setClassA_CancelledJoNo(Integer classA_CancelledJoNo) {
        this.classA_CancelledJoNo = classA_CancelledJoNo;
    }

    public Integer getClassA_ClosedJoNo() {
        return this.classA_ClosedJoNo;
    }

    public void setClassA_ClosedJoNo(Integer classA_ClosedJoNo) {
        this.classA_ClosedJoNo = classA_ClosedJoNo;
    }

    public Integer getClassB_CancelledJoNo() {
        return this.classB_CancelledJoNo;
    }

    public void setClassB_CancelledJoNo(Integer classB_CancelledJoNo) {
        this.classB_CancelledJoNo = classB_CancelledJoNo;
    }

    public Integer getClassB_ClosedJoNo() {
        return this.classB_ClosedJoNo;
    }

    public void setClassB_ClosedJoNo(Integer classB_ClosedJoNo) {
        this.classB_ClosedJoNo = classB_ClosedJoNo;
    }

    public Integer getClosedJoNo() {
        return this.closedJoNo;
    }

    public void setClosedJoNo(Integer closedJoNo) {
        this.closedJoNo = closedJoNo;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedIn() {
        return this.createdIn;
    }

    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDateMonth() {
        return this.dateMonth;
    }

    public void setDateMonth(Integer dateMonth) {
        this.dateMonth = dateMonth;
    }

    public Integer getDateYear() {
        return this.dateYear;
    }

    public void setDateYear(Integer dateYear) {
        this.dateYear = dateYear;
    }

    public Integer getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedIn() {
        return this.lastModifiedIn;
    }

    public void setLastModifiedIn(Date lastModifiedIn) {
        this.lastModifiedIn = lastModifiedIn;
    }

    public int getNoOfHandeledMrequestsOutsideWhrs() {
        return this.noOfHandeledMrequestsOutsideWhrs;
    }

    public void setNoOfHandeledMrequestsOutsideWhrs(int noOfHandeledMrequestsOutsideWhrs) {
        this.noOfHandeledMrequestsOutsideWhrs = noOfHandeledMrequestsOutsideWhrs;
    }

    public int getNoOfUrgentMrequestsOutsideWhrs() {
        return this.noOfUrgentMrequestsOutsideWhrs;
    }

    public void setNoOfUrgentMrequestsOutsideWhrs(int noOfUrgentMrequestsOutsideWhrs) {
        this.noOfUrgentMrequestsOutsideWhrs = noOfUrgentMrequestsOutsideWhrs;
    }

    public int getOtherMonthCancelledJoNo() {
        return this.otherMonthCancelledJoNo;
    }

    public void setOtherMonthCancelledJoNo(int otherMonthCancelledJoNo) {
        this.otherMonthCancelledJoNo = otherMonthCancelledJoNo;
    }

    public int getOtherMonthClassA_cancelledJoNo() {
        return this.otherMonthClassA_cancelledJoNo;
    }

    public void setOtherMonthClassA_cancelledJoNo(int otherMonthClassA_cancelledJoNo) {
        this.otherMonthClassA_cancelledJoNo = otherMonthClassA_cancelledJoNo;
    }

    public Integer getOtherMonthClassA_closedJoNo() {
        return this.otherMonthClassA_closedJoNo;
    }

    public void setOtherMonthClassA_closedJoNo(Integer otherMonthClassA_closedJoNo) {
        this.otherMonthClassA_closedJoNo = otherMonthClassA_closedJoNo;
    }

    public Integer getOtherMonthClassB_cancelledJoNo() {
        return this.otherMonthClassB_cancelledJoNo;
    }

    public void setOtherMonthClassB_cancelledJoNo(Integer otherMonthClassB_cancelledJoNo) {
        this.otherMonthClassB_cancelledJoNo = otherMonthClassB_cancelledJoNo;
    }

    public Integer getOtherMonthClassB_closedJoNo() {
        return this.otherMonthClassB_closedJoNo;
    }

    public void setOtherMonthClassB_closedJoNo(Integer otherMonthClassB_closedJoNo) {
        this.otherMonthClassB_closedJoNo = otherMonthClassB_closedJoNo;
    }

    public int getOtherMonthClosedJoNo() {
        return this.otherMonthClosedJoNo;
    }

    public void setOtherMonthClosedJoNo(int otherMonthClosedJoNo) {
        this.otherMonthClosedJoNo = otherMonthClosedJoNo;
    }

    public int getPpmNotHappenedVisitsNo() {
        return this.ppmNotHappenedVisitsNo;
    }

    public void setPpmNotHappenedVisitsNo(int ppmNotHappenedVisitsNo) {
        this.ppmNotHappenedVisitsNo = ppmNotHappenedVisitsNo;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public Integer getTotalClassA_openedJoNo() {
        return this.totalClassA_openedJoNo;
    }

    public void setTotalClassA_openedJoNo(Integer totalClassA_openedJoNo) {
        this.totalClassA_openedJoNo = totalClassA_openedJoNo;
    }

    public Integer getTotalClassB_openedJoNo() {
        return this.totalClassB_openedJoNo;
    }

    public void setTotalClassB_openedJoNo(Integer totalClassB_openedJoNo) {
        this.totalClassB_openedJoNo = totalClassB_openedJoNo;
    }

    public int getTotalOpenedJoNo() {
        return this.totalOpenedJoNo;
    }

    public void setTotalOpenedJoNo(int totalOpenedJoNo) {
        this.totalOpenedJoNo = totalOpenedJoNo;
    }

    public Integer getUndermaintenanceClassA_deviceNo() {
        return this.undermaintenanceClassA_deviceNo;
    }

    public void setUndermaintenanceClassA_deviceNo(Integer undermaintenanceClassA_deviceNo) {
        this.undermaintenanceClassA_deviceNo = undermaintenanceClassA_deviceNo;
    }

    public int getUndermaintenanceClassB_deviceNo() {
        return this.undermaintenanceClassB_deviceNo;
    }

    public void setUndermaintenanceClassB_deviceNo(int undermaintenanceClassB_deviceNo) {
        this.undermaintenanceClassB_deviceNo = undermaintenanceClassB_deviceNo;
    }

    public int getWorkingClassA_deviceNo() {
        return this.workingClassA_deviceNo;
    }

    public void setWorkingClassA_deviceNo(int workingClassA_deviceNo) {
        this.workingClassA_deviceNo = workingClassA_deviceNo;
    }

    public Integer getWorkingClassB_deviceNo() {
        return this.workingClassB_deviceNo;
    }

    public void setWorkingClassB_deviceNo(Integer workingClassB_deviceNo) {
        this.workingClassB_deviceNo = workingClassB_deviceNo;
    }

    public Integer getDirectorateId() {
        return this.directorateId;
    }

    public void setDirectorateId(Integer directorateId) {
        this.directorateId = directorateId;
    }
}
