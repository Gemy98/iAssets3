/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.util.LocalizationManager;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="common_directorate")
@NamedQuery(name="CommonDirectorate.findAll", query="SELECT d FROM CommonDirectorate d Where d.status=true")
public class CommonDirectorate
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="region_name_literal_key")
    private String regionNameLiteralKey;
    @Column(name="user_name_space")
    private String userNameSpace;

    protected String getRegionNameLiteralKey() {
        return this.regionNameLiteralKey;
    }

    public String getRegionName(String langCode) {
        return LocalizationManager.getLiteral(this.regionNameLiteralKey, langCode);
    }

    public String getUserNameSpace() {
        return this.userNameSpace;
    }
}
