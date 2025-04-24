/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Cacheable
@Entity
@Table(name="common_operating_company")
@NamedQuery(name="CommonOperatingCompany.findAll", query="SELECT o FROM CommonOperatingCompany o")
public class CommonOperatingCompany
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="short_name_literal_key")
    private String shortNameLiteralKey;
    @Transient
    private String shortName;
    @Column(name="owner_app_type_id")
    private Integer ownerAppTypeId;

    public Integer getOwnerAppTypeId() {
        return this.ownerAppTypeId;
    }

    public void setOwnerAppTypeId(Integer ownerAppTypeId) {
        this.ownerAppTypeId = ownerAppTypeId;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getShortNameLiteralKey() {
        return this.shortNameLiteralKey;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
