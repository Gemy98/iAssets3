/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.common.entity.AbstractSpInventoryContent;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="bio_sp_inventory_content")
@NamedQuery(name="BioSpInventoryContent.findAll", query="SELECT s FROM BioSpInventoryContent s ORDER BY s.name")
public class BioSpInventoryContent
extends AbstractSpInventoryContent
implements Serializable {
    private static final long serialVersionUID = 1L;
}
