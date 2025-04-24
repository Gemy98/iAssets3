/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report;

import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewStandardJobOrderReport"})
public class ViewStandardJobOrderReport
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BioJobOrder jo = (BioJobOrder)request.getAttribute("jobOrderInfoObj");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jobOrder", jo);
        params.put("device", jo.getHospitalDevice());
        params.put("currentLocIsHealthCenter", jo.getHospitalDevice().getHospital().getHealthCenter());
        params.put("responsibleEngineer", jo.getResponsibleEngineer());
        params.put("hospDepHead", jo.getRelatedHospitalDepartmentHead());
        params.put("siteManager", jo.getSiteManager());
        params.put("mmpDepHead", jo.getMmpDepartmentHead());
        params.put("mmpDepSupervisor", jo.getMmpDepartmentSupervisor());
        params.put("hospAssistDir", jo.getHospitalAssistantDirector());
        params.put("hospDirector", jo.getHospitalDirector());
        params.put("dirSupervisor", jo.getDirectorateSupervisor());
        params.put("dirAdmin", jo.getDirectorateAdmin());
        params.put("dirSuperAdmin", jo.getDirectorateSuperAdmin());
        this.viewPDFReport("JobOrderReport.jasper", null, null, params, request, response);
    }
}
