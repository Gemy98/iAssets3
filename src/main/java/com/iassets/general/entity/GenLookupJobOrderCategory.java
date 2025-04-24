/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.Table
 *  org.hibernate.annotations.Where
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.general.entity.GenLookupJobOrderSubcategory;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Cacheable
@Entity
@Table(name="gen_lookup_job_order_category")
@NamedQuery(name="GenLookupJobOrderCategory.findAll", query="SELECT g FROM GenLookupJobOrderCategory g WHERE g.status = true")
public class GenLookupJobOrderCategory
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy="parentCategory", fetch=FetchType.EAGER)
    @Where(clause="status = 1")
    private List<GenLookupJobOrderSubcategory> subcategories;

    public List<GenLookupJobOrderSubcategory> getSubcategories() {
        return this.subcategories;
    }

    public void setSubcategories(List<GenLookupJobOrderSubcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
