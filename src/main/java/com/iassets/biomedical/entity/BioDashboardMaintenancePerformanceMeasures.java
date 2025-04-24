/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  org.hibernate.annotations.Immutable
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.CommonSite;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name="bio_dashboard_maintenance_performance_measures")
@NamedQueries(value={@NamedQuery(name="BioDashboardMaintenancePerformanceMeasures.getDashboardMaintenancePerformanceBySiteAndMonthAndYear", query="SELECT b FROM BioDashboardMaintenancePerformanceMeasures b where b.site.id=:siteId and b.dateYear=:year and b.dateMonth=:month "), @NamedQuery(name="BioDashboardMaintenancePerformanceMeasures.getDashboardMaintenancePerformanceByDirectorateAndMonthAndYear", query="SELECT b FROM BioDashboardMaintenancePerformanceMeasures b where b.directorateId=:directorateId and b.dateYear=:year and b.dateMonth=:month ")})
@Immutable
public class BioDashboardMaintenancePerformanceMeasures
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @JoinColumn(name="site_id")
    @ManyToOne
    protected CommonSite site;
    @Column(name="date_month")
    private int dateMonth;
    @Column(name="date_year")
    private int dateYear;
    @Column(name="directorate_id")
    private int directorateId;
    @Column(name="period_length")
    private int periodLength;
    @Column(name="sum_cancelled_jo_no")
    private int sumCancelledJoNo;
    @Column(name="sum_classA_cancelled_jo_no")
    private int sumClassA_CancelledJoNo;
    @Column(name="sum_classA_closed_jo_no")
    private int sumClassA_ClosedJoNo;
    @Column(name="sum_classB_cancelled_jo_no")
    private int sumClassB_CancelledJoNo;
    @Column(name="sum_classB_closed_jo_no")
    private int sumClassB_ClosedJoNo;
    @Column(name="sum_closed_jo_no")
    private int sumClosedJoNo;
    @Column(name="sum_no_of_handeled_mrequests_outside_whrs")
    private int sumNoOfHandeledMrequestsOutsideWhrs;
    @Column(name="sum_no_of_urgent_mrequests_outside_whrs")
    private int sumNoOfUrgentMrequestsOutsideWhrs;
    @Column(name="sum_other_month_cancelled_jo_no")
    private int sumOtherMonthCancelledJoNo;
    @Column(name="sum_other_month_classA_cancelled_jo_no")
    private int sumOtherMonthClassA_cancelledJoNo;
    @Column(name="sum_other_month_classA_closed_jo_no")
    private int sumOtherMonthClassA_closedJoNo;
    @Column(name="sum_other_month_classB_cancelled_jo_no")
    private int sumOtherMonthClassB_cancelledJoNo;
    @Column(name="sum_other_month_classB_closed_jo_no")
    private int sumOtherMonthClassB_closedJoNo;
    @Column(name="sum_other_month_closed_jo_no")
    private int sumOtherMonthClosedJoNo;
    @Column(name="sum_ppm_not_happened_visits_no")
    private int sumPpmNotHappenedVisitsNo;
    @Column(name="sum_total_classA_opened_jo_no")
    private int sumTotalClassA_openedJoNo;
    @Column(name="sum_total_classB_opened_jo_no")
    private int sumTotalClassB_openedJoNo;
    @Column(name="sum_total_opened_jo_no")
    private int sumTotalOpenedJoNo;
    @Column(name="sum_undermaintenance_classA_device_no")
    private int sumUndermaintenanceClassA_deviceNo;
    @Column(name="sum_undermaintenance_classB_device_no")
    private int sumUndermaintenanceClassB_deviceNo;
    @Column(name="sum_working_classA_device_no")
    private int workingClassA_deviceNo;
    @Column(name="sum_working_classB_device_no")
    private int workingClassB_deviceNo;
    @Column(name="sum_class_A_B_scheduled_ppm_visits_no")
    private int sumClassABScheduledPPMVisitsNo;

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public int getDateMonth() {
        return this.dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateYear() {
        return this.dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public int getDirectorateId() {
        return this.directorateId;
    }

    public void setDirectorateId(int directorateId) {
        this.directorateId = directorateId;
    }

    public int getSumCancelledJoNo() {
        return this.sumCancelledJoNo;
    }

    public void setSumCancelledJoNo(int sumCancelledJoNo) {
        this.sumCancelledJoNo = sumCancelledJoNo;
    }

    public int getSumClassA_CancelledJoNo() {
        return this.sumClassA_CancelledJoNo;
    }

    public void setSumClassA_CancelledJoNo(int sumClassA_CancelledJoNo) {
        this.sumClassA_CancelledJoNo = sumClassA_CancelledJoNo;
    }

    public int getSumClassA_ClosedJoNo() {
        return this.sumClassA_ClosedJoNo;
    }

    public void setSumClassA_ClosedJoNo(int sumClassA_ClosedJoNo) {
        this.sumClassA_ClosedJoNo = sumClassA_ClosedJoNo;
    }

    public int getSumClassB_CancelledJoNo() {
        return this.sumClassB_CancelledJoNo;
    }

    public void setSumClassB_CancelledJoNo(int sumClassB_CancelledJoNo) {
        this.sumClassB_CancelledJoNo = sumClassB_CancelledJoNo;
    }

    public int getSumClassB_ClosedJoNo() {
        return this.sumClassB_ClosedJoNo;
    }

    public void setSumClassB_ClosedJoNo(int sumClassB_ClosedJoNo) {
        this.sumClassB_ClosedJoNo = sumClassB_ClosedJoNo;
    }

    public int getSumClosedJoNo() {
        return this.sumClosedJoNo;
    }

    public void setSumClosedJoNo(int sumClosedJoNo) {
        this.sumClosedJoNo = sumClosedJoNo;
    }

    public int getSumNoOfHandeledMrequestsOutsideWhrs() {
        return this.sumNoOfHandeledMrequestsOutsideWhrs;
    }

    public void setSumNoOfHandeledMrequestsOutsideWhrs(int sumNoOfHandeledMrequestsOutsideWhrs) {
        this.sumNoOfHandeledMrequestsOutsideWhrs = sumNoOfHandeledMrequestsOutsideWhrs;
    }

    public int getSumNoOfUrgentMrequestsOutsideWhrs() {
        return this.sumNoOfUrgentMrequestsOutsideWhrs;
    }

    public void setSumNoOfUrgentMrequestsOutsideWhrs(int sumNoOfUrgentMrequestsOutsideWhrs) {
        this.sumNoOfUrgentMrequestsOutsideWhrs = sumNoOfUrgentMrequestsOutsideWhrs;
    }

    public int getSumOtherMonthCancelledJoNo() {
        return this.sumOtherMonthCancelledJoNo;
    }

    public void setSumOtherMonthCancelledJoNo(int sumOtherMonthCancelledJoNo) {
        this.sumOtherMonthCancelledJoNo = sumOtherMonthCancelledJoNo;
    }

    public int getSumOtherMonthClassA_cancelledJoNo() {
        return this.sumOtherMonthClassA_cancelledJoNo;
    }

    public void setSumOtherMonthClassA_cancelledJoNo(int sumOtherMonthClassA_cancelledJoNo) {
        this.sumOtherMonthClassA_cancelledJoNo = sumOtherMonthClassA_cancelledJoNo;
    }

    public int getSumOtherMonthClassA_closedJoNo() {
        return this.sumOtherMonthClassA_closedJoNo;
    }

    public void setSumOtherMonthClassA_closedJoNo(int sumOtherMonthClassA_closedJoNo) {
        this.sumOtherMonthClassA_closedJoNo = sumOtherMonthClassA_closedJoNo;
    }

    public int getSumOtherMonthClassB_cancelledJoNo() {
        return this.sumOtherMonthClassB_cancelledJoNo;
    }

    public void setSumOtherMonthClassB_cancelledJoNo(int sumOtherMonthClassB_cancelledJoNo) {
        this.sumOtherMonthClassB_cancelledJoNo = sumOtherMonthClassB_cancelledJoNo;
    }

    public int getSumOtherMonthClassB_closedJoNo() {
        return this.sumOtherMonthClassB_closedJoNo;
    }

    public void setSumOtherMonthClassB_closedJoNo(int sumOtherMonthClassB_closedJoNo) {
        this.sumOtherMonthClassB_closedJoNo = sumOtherMonthClassB_closedJoNo;
    }

    public int getSumOtherMonthClosedJoNo() {
        return this.sumOtherMonthClosedJoNo;
    }

    public void setSumOtherMonthClosedJoNo(int sumOtherMonthClosedJoNo) {
        this.sumOtherMonthClosedJoNo = sumOtherMonthClosedJoNo;
    }

    public int getSumPpmNotHappenedVisitsNo() {
        return this.sumPpmNotHappenedVisitsNo;
    }

    public void setSumPpmNotHappenedVisitsNo(int sumPpmNotHappenedVisitsNo) {
        this.sumPpmNotHappenedVisitsNo = sumPpmNotHappenedVisitsNo;
    }

    public int getSumTotalClassA_openedJoNo() {
        return this.sumTotalClassA_openedJoNo;
    }

    public void setSumTotalClassA_openedJoNo(int sumTotalClassA_openedJoNo) {
        this.sumTotalClassA_openedJoNo = sumTotalClassA_openedJoNo;
    }

    public int getSumTotalClassB_openedJoNo() {
        return this.sumTotalClassB_openedJoNo;
    }

    public void setSumTotalClassB_openedJoNo(int sumTotalClassB_openedJoNo) {
        this.sumTotalClassB_openedJoNo = sumTotalClassB_openedJoNo;
    }

    public int getSumTotalOpenedJoNo() {
        return this.sumTotalOpenedJoNo;
    }

    public void setSumTotalOpenedJoNo(int sumTotalOpenedJoNo) {
        this.sumTotalOpenedJoNo = sumTotalOpenedJoNo;
    }

    public int getSumUndermaintenanceClassA_deviceNo() {
        return this.sumUndermaintenanceClassA_deviceNo;
    }

    public void setSumUndermaintenanceClassA_deviceNo(int sumUndermaintenanceClassA_deviceNo) {
        this.sumUndermaintenanceClassA_deviceNo = sumUndermaintenanceClassA_deviceNo;
    }

    public int getSumUndermaintenanceClassB_deviceNo() {
        return this.sumUndermaintenanceClassB_deviceNo;
    }

    public void setSumUndermaintenanceClassB_deviceNo(int sumUndermaintenanceClassB_deviceNo) {
        this.sumUndermaintenanceClassB_deviceNo = sumUndermaintenanceClassB_deviceNo;
    }

    public int getWorkingClassA_deviceNo() {
        return this.workingClassA_deviceNo;
    }

    public void setWorkingClassA_deviceNo(int workingClassA_deviceNo) {
        this.workingClassA_deviceNo = workingClassA_deviceNo;
    }

    public int getWorkingClassB_deviceNo() {
        return this.workingClassB_deviceNo;
    }

    public void setWorkingClassB_deviceNo(int sumEorkingClassB_deviceNo) {
        this.workingClassB_deviceNo = sumEorkingClassB_deviceNo;
    }

    public int getPeriodLength() {
        return this.periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }

    public int getSumClassABScheduledPPMVisitsNo() {
        return this.sumClassABScheduledPPMVisitsNo;
    }

    public void setSumClassABScheduledPPMVisitsNo(int sumClassABScheduledPPMVisitsNo) {
        this.sumClassABScheduledPPMVisitsNo = sumClassABScheduledPPMVisitsNo;
    }
}
