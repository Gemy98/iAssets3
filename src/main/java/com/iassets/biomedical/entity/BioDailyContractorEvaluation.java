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
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  javax.persistence.Transient
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="bio_daily_contractor_evaluation")
@NamedQueries(value={@NamedQuery(name="BioDailyContractorEvaluation.findAll", query="SELECT b FROM BioDailyContractorEvaluation b"), @NamedQuery(name="BioDailyContractorEvaluation.findContractorEvaluationRecordByDate", query="SELECT b FROM BioDailyContractorEvaluation b where b.site.id=:siteId and DATE(b.evalDate)=DATE(:date) ")})
public class BioDailyContractorEvaluation
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private int employeeAbsent;
    @Transient
    private int employeeCommitmit;
    @Transient
    private int supplierComplain;
    @Column(name="abs_biochem_no")
    private int absBiochemNo;
    @Column(name="abs_bioeng_no")
    private int absBioengNo;
    @Column(name="abs_biotech_no")
    private int absBiotechNo;
    @Column(name="abs_driver_no")
    private int absDriverNo;
    @Column(name="abs_itemp_no")
    private int absItempNo;
    @Column(name="other_supplier_no")
    private Integer otherSupplierNo;
    @Column(name="pc_suspended")
    private byte pcSuspended;
    @JoinColumn(name="site_id")
    @ManyToOne
    protected CommonSite site;
    @Column(name="suppliers_complains_no")
    private int suppliersComplainsNo;
    @Column(name="total_absent_emp_no")
    private Integer totalAbsentEmpNo;
    @Column(name="total_agent_no")
    private Integer totalAgentNo;
    @Column(name="total_contract_emp_no")
    private Integer totalContractEmpNo;
    @Column(name="total_mo3tamad_emp_no")
    private Integer totalMo3tamadEmpNo;
    @Column(name="total_subcontractor_no")
    private Integer totalSubcontractorNo;
    @Column(name="total_working_emp_no")
    private Integer totalWorkingEmpNo;
    @Column(name="uniform_violent_emp_no")
    private int uniformViolentEmpNo;
    @Column(name="default_record")
    private boolean defaultRecord;
    @Column(name="directorate_id")
    private Integer directorateId;
    @Column(name="created_by")
    private Integer createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    private Date createdIn;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="eval_date")
    private Date evalDate;
    @Column(name="eval_month")
    private Integer evalMonth;
    @Column(name="eval_year")
    private Integer evalYear;
    @Column(name="last_modified_by")
    private Integer lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    private Date lastModifiedIn;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAbsBiochemNo() {
        return this.absBiochemNo;
    }

    public void setAbsBiochemNo(int absBiochemNo) {
        this.absBiochemNo = absBiochemNo;
    }

    public int getAbsBioengNo() {
        return this.absBioengNo;
    }

    public void setAbsBioengNo(int absBioengNo) {
        this.absBioengNo = absBioengNo;
    }

    public int getAbsBiotechNo() {
        return this.absBiotechNo;
    }

    public void setAbsBiotechNo(int absBiotechNo) {
        this.absBiotechNo = absBiotechNo;
    }

    public int getAbsDriverNo() {
        return this.absDriverNo;
    }

    public void setAbsDriverNo(int absDriverNo) {
        this.absDriverNo = absDriverNo;
    }

    public int getAbsItempNo() {
        return this.absItempNo;
    }

    public void setAbsItempNo(int absItempNo) {
        this.absItempNo = absItempNo;
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

    public Date getEvalDate() {
        return this.evalDate;
    }

    public void setEvalDate(Date evalDate) {
        this.evalDate = evalDate;
    }

    public Integer getEvalMonth() {
        return this.evalMonth;
    }

    public void setEvalMonth(Integer evalMonth) {
        this.evalMonth = evalMonth;
    }

    public Integer getEvalYear() {
        return this.evalYear;
    }

    public void setEvalYear(Integer evalYear) {
        this.evalYear = evalYear;
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

    public Integer getOtherSupplierNo() {
        return this.otherSupplierNo;
    }

    public void setOtherSupplierNo(Integer otherSupplierNo) {
        this.otherSupplierNo = otherSupplierNo;
    }

    public byte getPcSuspended() {
        return this.pcSuspended;
    }

    public void setPcSuspended(byte pcSuspended) {
        this.pcSuspended = pcSuspended;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public int getSuppliersComplainsNo() {
        return this.suppliersComplainsNo;
    }

    public void setSuppliersComplainsNo(int suppliersComplainsNo) {
        this.suppliersComplainsNo = suppliersComplainsNo;
    }

    public Integer getTotalAbsentEmpNo() {
        return this.totalAbsentEmpNo;
    }

    public void setTotalAbsentEmpNo(Integer totalAbsentEmpNo) {
        this.totalAbsentEmpNo = totalAbsentEmpNo;
    }

    public Integer getTotalAgentNo() {
        return this.totalAgentNo;
    }

    public void setTotalAgentNo(Integer totalAgentNo) {
        this.totalAgentNo = totalAgentNo;
    }

    public Integer getTotalContractEmpNo() {
        return this.totalContractEmpNo;
    }

    public void setTotalContractEmpNo(Integer totalContractEmpNo) {
        this.totalContractEmpNo = totalContractEmpNo;
    }

    public Integer getTotalMo3tamadEmpNo() {
        return this.totalMo3tamadEmpNo;
    }

    public void setTotalMo3tamadEmpNo(Integer totalMo3tamadEmpNo) {
        this.totalMo3tamadEmpNo = totalMo3tamadEmpNo;
    }

    public Integer getTotalSubcontractorNo() {
        return this.totalSubcontractorNo;
    }

    public void setTotalSubcontractorNo(Integer totalSubcontractorNo) {
        this.totalSubcontractorNo = totalSubcontractorNo;
    }

    public Integer getTotalWorkingEmpNo() {
        return this.totalWorkingEmpNo;
    }

    public void setTotalWorkingEmpNo(Integer totalWorkingEmpNo) {
        this.totalWorkingEmpNo = totalWorkingEmpNo;
    }

    public int getUniformViolentEmpNo() {
        return this.uniformViolentEmpNo;
    }

    public void setUniformViolentEmpNo(int uniformViolentEmpNo) {
        this.uniformViolentEmpNo = uniformViolentEmpNo;
    }

    public boolean isDefaultRecord() {
        return this.defaultRecord;
    }

    public void setDefaultRecord(boolean defaultRecord) {
        this.defaultRecord = defaultRecord;
    }

    public int getEmployeeAbsent() {
        this.employeeAbsent = this.absBiochemNo + this.absBioengNo + this.absBiotechNo + this.absDriverNo + this.absItempNo == 0 ? 0 : 1;
        return this.employeeAbsent;
    }

    public int getEmployeeCommitmit() {
        this.employeeCommitmit = this.uniformViolentEmpNo > 0 ? 1 : 0;
        return this.employeeCommitmit;
    }

    public int getSupplierComplain() {
        this.supplierComplain = this.suppliersComplainsNo > 0 ? 1 : 0;
        return this.supplierComplain;
    }

    public Integer getDirectorateId() {
        return this.directorateId;
    }

    public void setDirectorateId(Integer directorateId) {
        this.directorateId = directorateId;
    }
}
