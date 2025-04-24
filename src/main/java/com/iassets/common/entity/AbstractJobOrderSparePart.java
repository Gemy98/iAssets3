/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.MappedSuperclass
 */
package com.iassets.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractJobOrderSparePart {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Column(name="in_quotation")
    protected Boolean inQuotation;
    @Column(name="from_inventory")
    protected Boolean fromInventory;
    protected Integer quantity;
    protected String description;
    protected Float price;
    @Column(name="total_price")
    protected Float totalPrice;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getInQuotation() {
        return this.inQuotation;
    }

    public void setInQuotation(Boolean inQuotation) {
        this.inQuotation = inQuotation;
    }

    public Boolean getFromInventory() {
        return this.fromInventory;
    }

    public void setFromInventory(Boolean fromInventory) {
        this.fromInventory = fromInventory;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
