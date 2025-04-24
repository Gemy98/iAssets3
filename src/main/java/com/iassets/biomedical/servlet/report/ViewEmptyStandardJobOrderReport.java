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

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewEmptyStandardJobOrderReport"})
public class ViewEmptyStandardJobOrderReport
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonSite sessionSite = this.getSessionSite(request);
        BioHospitalDevice device = new BioHospitalDevice();
        device.setSite(this.getSessionSite(request));
        device.setHospital(this.getSessionLocation(request));
        BioJobOrder jo = new BioJobOrder();
        jo.setJobOrderNo("");
        jo.setHospitalDevice(device);
        jo.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jobOrder", jo);
        params.put("device", device);
        params.put("currentLocIsHealthCenter", jo.getHospitalDevice().getHospital().getHealthCenter());
        params.put("siteManager", sessionSite.getBioSiteManager());
        params.put("mmpDepHead", sessionSite.getBioDepartmentHead());
        params.put("mmpDepSupervisor", sessionSite.getBioDepartmentSupervisors().get(0));
        params.put("hospAssistDir", sessionSite.getHospitalAssistantDirector());
        params.put("hospDirector", sessionSite.getHospitalDirector());
        this.viewPDFReport("JobOrderReport.jasper", null, null, params, request, response);
    }
}
