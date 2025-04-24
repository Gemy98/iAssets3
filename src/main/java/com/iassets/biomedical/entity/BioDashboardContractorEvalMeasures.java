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
@Table(name="bio_dashboard_contractor_eval_measures")
@NamedQueries(value={@NamedQuery(name="BioDashboardContractorEvalMeasures.getDailyContractorEvaluationViewBySiteAndMonthAndYear", query="SELECT b FROM BioDashboardContractorEvalMeasures b where b.site.id=:siteId and b.evalYear=:year and b.evalMonth=:month "), @NamedQuery(name="BioDashboardContractorEvalMeasures.getDailyContractorEvaluationViewByDirectorateAndMonthAndYear", query="SELECT b FROM BioDashboardContractorEvalMeasures b where b.directorateId=:directorateId and b.evalYear=:year and b.evalMonth=:month ")})
@Immutable
public class BioDashboardContractorEvalMeasures
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @JoinColumn(name="site_id")
    @ManyToOne
    protected CommonSite site;
    @Column(name="directorate_id")
    private int directorateId;
    @Column(name="eval_month")
    private int evalMonth;
    @Column(name="eval_year")
    private int evalYear;
    @Column(name="period_length")
    private int periodLength;
    @Column(name="sum_total_contract_emp_no")
    private int sumTotalContractEmpNo;
    @Column(name="sum_total_mo3tamad_emp_no")
    private int sumTotalMo3tamadEmpNo;
    @Column(name="sum_total_working_emp_no")
    private int sumTotalWorkingEmpNo;
    @Column(name="sum_abs_bioeng_no")
    private int sumAbsBioengNo;
    @Column(name="sum_abs_biotech_no")
    private int sumAbsBiotechNo;
    @Column(name="sum_abs_biochem_no")
    private int sumAbsBiochemNo;
    @Column(name="sum_abs_itemp_no")
    private int sumAbsItempNo;
    @Column(name="sum_abs_driver_no")
    private int sumAbsDriverNo;
    @Column(name="sum_total_absent_emp_no")
    private int sumTotalAbsentEmpNo;
    @Column(name="sum_uniform_violent_emp_no")
    private int sumUniformViolentEmpNo;
    @Column(name="sum_total_agent_no")
    private int sumTotalAgentNo;
    @Column(name="sum_total_subcontractor_no")
    private int sumTotalSubcontractorNo;
    @Column(name="sum_other_supplier_no")
    private int sumOtherSupplierNo;
    @Column(name="sum_suppliers_complains_no")
    private int sumSuppliersComplainsNo;
    @Column(name="sum_pc_suspended")
    private int sumPcSuspended;

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public int getDirectorateId() {
        return this.directorateId;
    }

    public void setDirectorateId(int directorateId) {
        this.directorateId = directorateId;
    }

    public int getEvalMonth() {
        return this.evalMonth;
    }

    public void setEvalMonth(int evalMonth) {
        this.evalMonth = evalMonth;
    }

    public int getEvalYear() {
        return this.evalYear;
    }

    public void setEvalYear(int evalYear) {
        this.evalYear = evalYear;
    }

    public int getPeriodLength() {
        return this.periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }

    public int getSumTotalContractEmpNo() {
        return this.sumTotalContractEmpNo;
    }

    public void setSumTotalContractEmpNo(int sumTotalContractEmpNo) {
        this.sumTotalContractEmpNo = sumTotalContractEmpNo;
    }

    public int getSumTotalMo3tamadEmpNo() {
        return this.sumTotalMo3tamadEmpNo;
    }

    public void setSumTotalMo3tamadEmpNo(int sumTotalMo3tamadEmpNo) {
        this.sumTotalMo3tamadEmpNo = sumTotalMo3tamadEmpNo;
    }

    public int getSumTotalWorkingEmpNo() {
        return this.sumTotalWorkingEmpNo;
    }

    public void setSumTotalWorkingEmpNo(int sumTotalWorkingEmpNo) {
        this.sumTotalWorkingEmpNo = sumTotalWorkingEmpNo;
    }

    public int getSumAbsBioengNo() {
        return this.sumAbsBioengNo;
    }

    public void setSumAbsBioengNo(int sumAbsBioengNo) {
        this.sumAbsBioengNo = sumAbsBioengNo;
    }

    public int getSumAbsBiotechNo() {
        return this.sumAbsBiotechNo;
    }

    public void setSumAbsBiotechNo(int sumAbsBiotechNo) {
        this.sumAbsBiotechNo = sumAbsBiotechNo;
    }

    public int getSumAbsBiochemNo() {
        return this.sumAbsBiochemNo;
    }

    public void setSumAbsBiochemNo(int sumAbsBiochemNo) {
        this.sumAbsBiochemNo = sumAbsBiochemNo;
    }

    public int getSumAbsItempNo() {
        return this.sumAbsItempNo;
    }

    public void setSumAbsItempNo(int sumAbsItempNo) {
        this.sumAbsItempNo = sumAbsItempNo;
    }

    public int getSumAbsDriverNo() {
        return this.sumAbsDriverNo;
    }

    public void setSumAbsDriverNo(int sumAbsDriverNo) {
        this.sumAbsDriverNo = sumAbsDriverNo;
    }

    public int getSumTotalAbsentEmpNo() {
        return this.sumTotalAbsentEmpNo;
    }

    public void setSumTotalAbsentEmpNo(int sumTotalAbsentEmpNo) {
        this.sumTotalAbsentEmpNo = sumTotalAbsentEmpNo;
    }

    public int getSumUniformViolentEmpNo() {
        return this.sumUniformViolentEmpNo;
    }

    public void setSumUniformViolentEmpNo(int sumUniformViolentEmpNo) {
        this.sumUniformViolentEmpNo = sumUniformViolentEmpNo;
    }

    public int getSumTotalAgentNo() {
        return this.sumTotalAgentNo;
    }

    public void setSumTotalAgentNo(int sumTotalAgentNo) {
        this.sumTotalAgentNo = sumTotalAgentNo;
    }

    public int getSumTotalSubcontractorNo() {
        return this.sumTotalSubcontractorNo;
    }

    public void setSumTotalSubcontractorNo(int sumTotalSubcontractorNo) {
        this.sumTotalSubcontractorNo = sumTotalSubcontractorNo;
    }

    public int getSumOtherSupplierNo() {
        return this.sumOtherSupplierNo;
    }

    public void setSumOtherSupplierNo(int sumOtherSupplierNo) {
        this.sumOtherSupplierNo = sumOtherSupplierNo;
    }

    public int getSumSuppliersComplainsNo() {
        return this.sumSuppliersComplainsNo;
    }

    public void setSumSuppliersComplainsNo(int sumSuppliersComplainsNo) {
        this.sumSuppliersComplainsNo = sumSuppliersComplainsNo;
    }

    public int getSumPcSuspended() {
        return this.sumPcSuspended;
    }

    public void setSumPcSuspended(int sumPcSuspended) {
        this.sumPcSuspended = sumPcSuspended;
    }
}
