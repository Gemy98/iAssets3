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
package com.iassets.biomedical.servlet;

import com.iassets.biomedical.DB.DBQueryManager;
import com.iassets.biomedical.DB.DBTransactionManager;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BioBasicServlet
extends CommonBasicServlet {
    @Autowired
    @Qualifier(value="bioDBQueryManager")
    protected DBQueryManager bioDBQueryManager;
    @Autowired
    @Qualifier(value="bioDBTransactionManager")
    protected DBTransactionManager bioDBTransManager;

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
