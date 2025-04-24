/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Transient
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLookupEntity;
import com.iassets.common.util.LocalizationManager;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractLocalizedEntity
extends AbstractLookupEntity
implements Comparable<AbstractLocalizedEntity> {
    @Column(name="name_literal_key")
    protected String nameLiteralKey;
    @Transient
    protected String localizedName;

    public String getNameLiteralKey() {
        return this.nameLiteralKey;
    }

    public void setNameLiteralKey(String nameLiteralKey) {
        this.nameLiteralKey = nameLiteralKey;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getLocalizedName() {
        return this.localizedName;
    }

    public String getLocalizedName(String langCode) {
        return LocalizationManager.getLiteral(this.nameLiteralKey, langCode);
    }

    @Override
    public int compareTo(AbstractLocalizedEntity other) {
        return this.localizedName == null ? 0 : this.localizedName.compareToIgnoreCase(other.localizedName);
    }
}
