/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 */
package com.iassets.common.servlet;

import com.iassets.common.DB.CommonDBQueryManager;
import com.iassets.common.DB.CommonDBTransactionManager;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.servlet.BasicServlet;
import com.iassets.common.util.WebUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommonBasicServlet
extends BasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    @Qualifier(value="commonDBQueryManager")
    protected CommonDBQueryManager commonDBQueryManager;
    @Autowired
    @Qualifier(value="commonDBTransactionManager")
    protected CommonDBTransactionManager CommonDBTransManager;

    @Override
    protected CommonHospital getSessionLocationById(HttpServletRequest request) {
        return this.CommonDBTransManager.findById(WebUtil.getParamValueAsInteger(request, "reportLocation", 0), CommonHospital.class);
    }
}
