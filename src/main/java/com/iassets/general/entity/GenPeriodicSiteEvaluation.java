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
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.general.entity;

import com.iassets.common.entity.CommonSite;
import com.iassets.general.entity.GenLookupEvaluationGroupItem;
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="gen_periodic_site_evaluation")
@NamedQuery(name="GenPeriodicSiteEvaluation.findAll", query="SELECT g FROM GenPeriodicSiteEvaluation g")
public class GenPeriodicSiteEvaluation
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;
    @ManyToOne
    @JoinColumn(name="item_id")
    private GenLookupEvaluationGroupItem evaluationGroupItem;
    private Integer month;
    private Integer year;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="start_date")
    private Date startDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="end_date")
    private Date endDate;
    @Column(name="dedicated_value")
    private Float dedicatedValue;
    @Column(name="eval_degree")
    private Float evalDegree;
    @Column(name="eval_percentage")
    private Float evalPercentage;
    @Column(name="max_degree")
    private Integer maxDegree;
    @Column(name="penalty_percentage")
    private Float penaltyPercentage;
    @Column(name="penalty_value")
    private Float penaltyValue;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public GenLookupEvaluationGroupItem getEvaluationGroupItem() {
        return this.evaluationGroupItem;
    }

    public void setEvaluationGroupItem(GenLookupEvaluationGroupItem evaluationGroupItem) {
        this.evaluationGroupItem = evaluationGroupItem;
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

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getDedicatedValue() {
        return this.dedicatedValue;
    }

    public void setDedicatedValue(Float dedicatedValue) {
        this.dedicatedValue = dedicatedValue;
    }

    public Float getEvalDegree() {
        return this.evalDegree;
    }

    public void setEvalDegree(Float evalDegree) {
        this.evalDegree = evalDegree;
    }

    public Float getEvalPercentage() {
        return this.evalPercentage;
    }

    public void setEvalPercentage(Float evalPercentage) {
        this.evalPercentage = evalPercentage;
    }

    public Integer getMaxDegree() {
        return this.maxDegree;
    }

    public void setMaxDegree(Integer maxDegree) {
        this.maxDegree = maxDegree;
    }

    public Float getPenaltyPercentage() {
        return this.penaltyPercentage;
    }

    public void setPenaltyPercentage(Float penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public Float getPenaltyValue() {
        return this.penaltyValue;
    }

    public void setPenaltyValue(Float penaltyValue) {
        this.penaltyValue = penaltyValue;
    }
}
