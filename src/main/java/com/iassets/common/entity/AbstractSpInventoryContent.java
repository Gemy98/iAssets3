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
 *  javax.persistence.Transient
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonSite;
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
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractSpInventoryContent {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @ManyToOne
    @JoinColumn(name="site_id")
    protected CommonSite site;
    protected String code;
    protected String name;
    @Column(name="available_quantity")
    protected Integer availableQuantity;
    protected Integer threshold;
    @Column(name="avg_unit_price")
    protected Float avgUnitPrice;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modefied_in")
    protected Date lastModefiedIn;
    @ManyToOne
    @JoinColumn(name="last_modefied_by")
    protected CommonUser lastModefiedBy;
    @Transient
    protected Float minPrice;
    @Transient
    protected Float maxPrice;

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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getThreshold() {
        return this.threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Float getAvgUnitPrice() {
        return this.avgUnitPrice;
    }

    public void setAvgUnitPrice(Float avgUnitPrice) {
        this.avgUnitPrice = avgUnitPrice;
    }

    public Date getCreatedIn() {
        return this.createdIn;
    }

    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public CommonUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CommonUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModefiedIn() {
        return this.lastModefiedIn;
    }

    public void setLastModefiedIn(Date lastModefiedIn) {
        this.lastModefiedIn = lastModefiedIn;
    }

    public CommonUser getLastModefiedBy() {
        return this.lastModefiedBy;
    }

    public void setLastModefiedBy(CommonUser lastModefiedBy) {
        this.lastModefiedBy = lastModefiedBy;
    }

    public Float getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }
}
