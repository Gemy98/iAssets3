/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="common_lookup_nationality")
@NamedQueries(value={@NamedQuery(name="CommonLookupNationality.findAll", query="SELECT l FROM CommonLookupNationality l WHERE l.status=true ORDER BY l.displayOrder")})
public class CommonLookupNationality
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="display_order")
    private Integer displayOrder;

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
