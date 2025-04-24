/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.AbstractNonLocalizedEntity;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="bio_lookup_subcontractor")
@NamedQuery(name="BioLookupSubcontractor.findAll", query="SELECT l FROM BioLookupSubcontractor l WHERE l.status=true ORDER BY l.name")
public class BioLookupSubcontractor
extends AbstractNonLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String telephone;

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
