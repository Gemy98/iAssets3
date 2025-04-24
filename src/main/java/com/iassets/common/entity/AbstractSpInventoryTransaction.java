/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonUser;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractSpInventoryTransaction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Column(name="site_id")
    protected Integer siteId;
    @Column(name="sp_inventory_content_id")
    private Integer spInventoryContentId;
    protected Boolean input;
    @Column(name="quantity")
    protected Integer quantity;
    @Column(name="unit_price")
    protected Float unitPrice;
    @Column(name="notes")
    protected String notes;
    @ManyToOne
    @JoinColumn(name="performed_by")
    protected CommonUser performedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="performed_in")
    protected Date performedIn;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSpInventoryContentId() {
        return this.spInventoryContentId;
    }

    public void setSpInventoryContentId(Integer spInventoryContentId) {
        this.spInventoryContentId = spInventoryContentId;
    }

    public Boolean getInput() {
        return this.input;
    }

    public void setInput(Boolean input) {
        this.input = input;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CommonUser getPerformedBy() {
        return this.performedBy;
    }

    public void setPerformedBy(CommonUser performedBy) {
        this.performedBy = performedBy;
    }

    public Date getPerformedIn() {
        return this.performedIn;
    }

    public void setPerformedIn(Date performedIn) {
        this.performedIn = performedIn;
    }
}
