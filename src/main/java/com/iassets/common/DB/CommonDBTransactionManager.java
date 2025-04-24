/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.common.DB;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.entity.BioSpInventoryTransaction;
import com.iassets.common.DB.DBManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor={Exception.class})
public class CommonDBTransactionManager
extends DBManager {
    @Transactional(rollbackFor={Exception.class})
    public void save(BioSpInventoryContent c, BioSpInventoryTransaction t) throws Exception {
        this.persistEntity(c);
        this.persistEntity(t);
    }
}
