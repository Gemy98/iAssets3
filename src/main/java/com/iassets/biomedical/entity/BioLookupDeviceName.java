/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.common.entity.AbstractNonLocalizedEntity;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="bio_lookup_device_name")
@NamedQuery(name="BioLookupDeviceName.findAll", query="SELECT l FROM BioLookupDeviceName l WHERE l.status=true ORDER BY l.name")
public class BioLookupDeviceName
extends AbstractNonLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="category_id")
    private BioLookupDeviceCategory category;

    public BioLookupDeviceCategory getCategory() {
        return this.category;
    }

    public void setCategory(BioLookupDeviceCategory category) {
        this.category = category;
    }
}
