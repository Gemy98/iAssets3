/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.MappedSuperclass
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLookupEntity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNonLocalizedEntity
extends AbstractLookupEntity {
    protected String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
