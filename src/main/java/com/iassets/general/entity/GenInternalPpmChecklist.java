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
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.general.entity;

import com.iassets.common.entity.CommonUser;
import com.iassets.general.entity.GenLookupInternalPpmPeriod;
import java.io.Serializable;
import java.util.Date;
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
@Table(name="gen_internal_ppm_checklist")
@NamedQuery(name="GenInternalPpmChecklist.findAll", query="SELECT g FROM GenInternalPpmChecklist g")
public class GenInternalPpmChecklist
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="display_name")
    private String displayName;
    @Column(name="display_order")
    private Integer displayOrder;
    @ManyToOne
    @JoinColumn(name="ppm_period_id")
    private GenLookupInternalPpmPeriod ppmPeriod;
    @ManyToOne
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    private Date createdIn;
    @ManyToOne
    @JoinColumn(name="last_modified_by")
    protected CommonUser lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    private Date lastModifiedIn;
    private Boolean status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public GenLookupInternalPpmPeriod getPpmPeriod() {
        return this.ppmPeriod;
    }

    public void setPpmPeriod(GenLookupInternalPpmPeriod ppmPeriod) {
        this.ppmPeriod = ppmPeriod;
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

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
