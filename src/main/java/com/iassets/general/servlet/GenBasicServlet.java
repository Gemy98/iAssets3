/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 */
package com.iassets.general.servlet;

import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.general.DB.DBQueryManager;
import com.iassets.general.DB.DBTransactionManager;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class GenBasicServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    @Qualifier(value="genDBQueryManager")
    protected DBQueryManager genDBQueryManager;
    @Autowired
    @Qualifier(value="genDBTransactionManager")
    protected DBTransactionManager genDBTransManager;

    @Override
    protected void viewPDFReport(String jasperFileName, String reportTitle, Collection data, HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String repoHeadLangCode = "ar";
        String repoHead = this.getSessionDirectorate(request).getLocalizedName(repoHeadLangCode) + "\n";
        CommonHospital sessionLocation = this.getSessionLocation(request);
        CommonSite sessionSite = this.getSessionSite(request);
        if (sessionLocation != null) {
            repoHead = repoHead + sessionLocation.getLocalizedName(repoHeadLangCode);
        } else if (sessionSite != null) {
            repoHead = repoHead + sessionSite.getLocalizedName(repoHeadLangCode);
        }
        params.put("repoHead", repoHead);
        super.viewPDFReport(jasperFileName, reportTitle, data, params, request, response);
    }
}
