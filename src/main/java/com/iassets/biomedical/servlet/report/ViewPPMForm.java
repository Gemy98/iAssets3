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
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewPPMForm"})
public class ViewPPMForm
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        BioJobOrder jo = new BioJobOrder();
        jo.setJobOrderNo("");
        jo.setHospitalDevice(device);
        jo.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId());
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jobOrder", jo);
        params.put("device", device);
        params.put("currentLocIsHealthCenter", jo.getHospitalDevice().getHospital().getHealthCenter());
        params.put("hospDepHead", device.getHospitalDepartment().getDepartmentHead());
        params.put("siteManager", sessionSite.getBioSiteManager());
        params.put("mmpDepHead", sessionSite.getBioDepartmentHead());
        params.put("mmpDepSupervisor", sessionSite.getBioDepartmentSupervisors().get(0));
        params.put("hospAssistDir", sessionSite.getHospitalAssistantDirector());
        params.put("hospDirector", sessionSite.getHospitalDirector());
        this.viewPDFReport("PPMFormReport.jasper", null, null, params, request, response);
    }
}
