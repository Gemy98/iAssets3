/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="bio_monthly_contractor_evaluation")
@NamedQueries(value={@NamedQuery(name="BioMonthlyContractorEvaluation.findAll", query="SELECT l FROM BioMonthlyContractorEvaluation l"), @NamedQuery(name="BioMonthlyContractorEvaluation.findBySiteAndYearAndMonth", query="SELECT l FROM BioMonthlyContractorEvaluation l where l.site.id = :siteId and l.month = :month and l.year = :year")})
public class BioMonthlyContractorEvaluation
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    protected Integer month;
    protected Integer year;
    @OneToOne
    @JoinColumn(name="site_id")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonSite site;
    @Column(name="month_days_no")
    protected Integer monthDaysNo;
    @Column(name="unpaid_emp_no")
    protected int unpaidEmpNo;
    @Column(name="class_A_B_not_happened_ppm_visits_no")
    protected int classABNotHappenedPPMVisitsNo;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;
    @ManyToOne
    @JoinColumn(name="last_modified_by")
    protected CommonUser lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    protected Date lastModifiedIn;
    @Column(name="directorate_id")
    private Integer directorateId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public Integer getMonthDaysNo() {
        return this.monthDaysNo;
    }

    public void setMonthDaysNo(Integer monthDaysNo) {
        this.monthDaysNo = monthDaysNo;
    }

    public int getUnpaidEmpNo() {
        return this.unpaidEmpNo;
    }

    public void setUnpaidEmpNo(int unpaidEmpNo) {
        this.unpaidEmpNo = unpaidEmpNo;
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

    public CommonUser getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(CommonUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedIn() {
        return this.lastModifiedIn;
    }

    public void setLastModifiedIn(Date lastModifiedIn) {
        this.lastModifiedIn = lastModifiedIn;
    }

    public Integer getDirectorateId() {
        return this.directorateId;
    }

    public void setDirectorateId(Integer directorateId) {
        this.directorateId = directorateId;
    }

    public int getClassABNotHappenedPPMVisitsNo() {
        return this.classABNotHappenedPPMVisitsNo;
    }

    public void setClassABNotHappenedPPMVisitsNo(int classABNotHappenedPPMVisitsNo) {
        this.classABNotHappenedPPMVisitsNo = classABNotHappenedPPMVisitsNo;
    }
}
