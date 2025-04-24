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
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="bio_progress_bill")
@NamedQueries(value={@NamedQuery(name="BioProgressBill.findAll", query="SELECT p FROM BioProgressBill p Order By p.issueDate")})
public class BioProgressBill
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;
    @Column(name="pb_type_id")
    private Integer pbTypeId;
    @Column(name="payment_no")
    private Integer paymentNo;
    @Column(name="pb_value")
    private Float pbValue;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="pb_date")
    private Date pbDate;
    @Column(name="issue_no")
    private String issueNo;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="issue_date")
    private Date issueDate;
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

    public Integer getPbTypeId() {
        return this.pbTypeId;
    }

    public void setPbTypeId(Integer pbTypeId) {
        this.pbTypeId = pbTypeId;
    }

    public Integer getPaymentNo() {
        return this.paymentNo;
    }

    public void setPaymentNo(Integer paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Float getPbValue() {
        return this.pbValue;
    }

    public void setPbValue(Float pbValue) {
        this.pbValue = pbValue;
    }

    public Date getPbDate() {
        return this.pbDate;
    }

    public void setPbDate(Date pbDate) {
        this.pbDate = pbDate;
    }

    public String getIssueNo() {
        return this.issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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
}
