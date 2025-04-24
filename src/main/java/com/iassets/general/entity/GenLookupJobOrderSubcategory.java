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
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_lookup_job_order_subcategory")
@NamedQuery(name="GenLookupJobOrderSubcategory.findAll", query="SELECT g FROM GenLookupJobOrderSubcategory g WHERE g.status = true")
public class GenLookupJobOrderSubcategory
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="category_id")
    private GenLookupJobOrderCategory parentCategory;

    public GenLookupJobOrderCategory getParentCategory() {
        return this.parentCategory;
    }

    public void setParentCategory(GenLookupJobOrderCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
}
