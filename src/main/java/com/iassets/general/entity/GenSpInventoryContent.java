/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractSpInventoryContent;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gen_sp_inventory_content")
@NamedQuery(name="GenSpInventoryContent.findAll", query="SELECT s FROM GenSpInventoryContent s ORDER BY s.name")
public class GenSpInventoryContent
extends AbstractSpInventoryContent
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="specs")
    private String specs;

    public String getSpecs() {
        return this.specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }
}
