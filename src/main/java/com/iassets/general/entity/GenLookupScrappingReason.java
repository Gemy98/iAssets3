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
@Table(name="gen_lookup_scrapping_reason")
@NamedQuery(name="GenLookupScrappingReason.findAll", query="SELECT l FROM GenLookupScrappingReason l WHERE l.status=true")
public class GenLookupScrappingReason
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
}
