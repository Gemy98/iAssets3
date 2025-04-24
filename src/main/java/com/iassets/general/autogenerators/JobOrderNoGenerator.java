/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 */
package com.iassets.general.autogenerators;

import com.iassets.general.DB.DBQueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class JobOrderNoGenerator {
    @Autowired
    @Qualifier(value="genDBQueryManager")
    protected DBQueryManager dbQueryManager;
}
