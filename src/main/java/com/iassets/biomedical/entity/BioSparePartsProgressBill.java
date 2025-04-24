/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.CascadeType
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.NamedNativeQuery
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioProgressBill;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bio_progress_bill_spareparts")
@NamedNativeQuery(name="BioSparePartsProgressBill.getLastProgressBill", query="SELECT a.id, a.pb_id, a.serial_scan, a.remained_budget_after_pb, a.payed_budget_after_pb FROM (SELECT a1.*, b1.site_id, b1.issue_date FROM bio_progress_bill_spareparts a1 INNER JOIN bio_progress_bill b1 ON a1.pb_id = b1.id WHERE b1.site_id = :siteId AND b1.id != :pbId) a LEFT JOIN (SELECT a2.*, b2.site_id, b2.issue_date FROM bio_progress_bill_spareparts a2 INNER JOIN bio_progress_bill b2 ON a2.pb_id = b2.id WHERE b2.site_id = :siteId AND b2.id != :pbId) b ON a.issue_date < b.issue_date WHERE b.id IS NULL ORDER BY a.id DESC", resultClass=BioSparePartsProgressBill.class)
public class BioSparePartsProgressBill
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String FIND_LAST_SPAREPARTS_PB_SQL = "SELECT a.id, a.pb_id, a.serial_scan, a.remained_budget_after_pb, a.payed_budget_after_pb FROM (SELECT a1.*, b1.site_id, b1.issue_date FROM bio_progress_bill_spareparts a1 INNER JOIN bio_progress_bill b1 ON a1.pb_id = b1.id WHERE b1.site_id = :siteId AND b1.id != :pbId) a LEFT JOIN (SELECT a2.*, b2.site_id, b2.issue_date FROM bio_progress_bill_spareparts a2 INNER JOIN bio_progress_bill b2 ON a2.pb_id = b2.id WHERE b2.site_id = :siteId AND b2.id != :pbId) b ON a.issue_date < b.issue_date WHERE b.id IS NULL ORDER BY a.id DESC";
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @OneToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="pb_id")
    private BioProgressBill progressBill;
    @Column(name="serial_scan")
    protected String serialScan;
    @Column(name="remained_budget_after_pb")
    protected Float remainedBudgetAfterPB;
    @Column(name="payed_budget_after_pb")
    protected Float payedBudgetAfterPB;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BioProgressBill getProgressBill() {
        return this.progressBill;
    }

    public void setProgressBill(BioProgressBill progressBill) {
        this.progressBill = progressBill;
    }

    public String getSerialScan() {
        return this.serialScan;
    }

    public void setSerialScan(String serialScan) {
        this.serialScan = serialScan;
    }

    public Float getRemainedBudgetAfterPB() {
        return this.remainedBudgetAfterPB;
    }

    public void setRemainedBudgetAfterPB(Float remainedBudgetAfterPB) {
        this.remainedBudgetAfterPB = remainedBudgetAfterPB;
    }

    public Float getPayedBudgetAfterPB() {
        return this.payedBudgetAfterPB;
    }

    public void setPayedBudgetAfterPB(Float payedBudgetAfterPB) {
        this.payedBudgetAfterPB = payedBudgetAfterPB;
    }
}
