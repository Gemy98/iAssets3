/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report.filter;

import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.biomedical.servlet.BioBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/PPMVisitsDetailsFilterDisplay"})
public class PPMVisitsDetailsFilterDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("catList", this.bioDBQueryManager.findAll(BioLookupDeviceCategory.class, this.getSessionLanguage(request)));
        this.forward(request, response, "/bio/report/filter/PPMVisitsDetailsFilter.jsp");
    }
}
