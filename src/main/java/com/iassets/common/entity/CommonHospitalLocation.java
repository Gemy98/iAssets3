/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonSite;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="common_hospital_location")
@NamedQueries(value={@NamedQuery(name="CommonHospitalLocation.findAll", query="SELECT c FROM CommonHospitalLocation c WHERE c.status=true"), @NamedQuery(name="CommonHospitalLocation.findHospitalLocations", query="SELECT c FROM CommonHospitalLocation c WHERE c.status=true AND c.hospital.id=:hospId"), @NamedQuery(name="CommonHospitalLocation.findSiteLcations", query="SELECT c FROM CommonHospitalLocation c WHERE c.status=true AND c.site.id =:siteId")})
public class CommonHospitalLocation
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;
    @ManyToOne
    @JoinColumn(name="hosp_id")
    private CommonHospital hospital;

    public CommonHospitalLocation() {
    }

    public CommonHospitalLocation(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite commonSite) {
        this.site = commonSite;
    }

    public CommonHospital getHospital() {
        return this.hospital;
    }

    public void setHospital(CommonHospital commonHospital) {
        this.hospital = commonHospital;
    }
}
