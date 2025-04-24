/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_lookup_job_order_priority")
@NamedQuery(name="GenLookupJobOrderPriority.findAll", query="SELECT l FROM GenLookupJobOrderPriority l WHERE l.status=true ORDER BY l.id")
public class GenLookupJobOrderPriority
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
}
