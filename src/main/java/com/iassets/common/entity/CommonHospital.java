/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.CommonSite;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="common_hospital")
@NamedQuery(name="CommonHospital.findAll", query="SELECT h FROM CommonHospital h WHERE h.status = true")
public class CommonHospital
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="health_center")
    private Boolean healthCenter;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;

    public Boolean getHealthCenter() {
        return this.healthCenter;
    }

    public void setHealthCenter(Boolean healthCenter) {
        this.healthCenter = healthCenter;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    @Override
    public Boolean getStatus() {
        return this.status != false && this.site.getStatus() != false;
    }
}
