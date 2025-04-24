/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.biomedical.DB;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.entity.BioSpInventoryTransaction;
import com.iassets.common.DB.CommonDBTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="bioDBTransactionManager")
@Transactional(rollbackFor={Exception.class})
public class DBTransactionManager
extends CommonDBTransactionManager {
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(BioSpInventoryContent c, BioSpInventoryTransaction t) throws Exception {
        this.persistEntity(c);
        this.persistEntity(t);
    }
}
