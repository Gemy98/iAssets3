/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/OpenJobOrderForMaintenanceRequest"})
public class OpenJobOrderForMaintenanceRequest
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int mReqId = WebUtil.getParamValueAsInteger(request, "mReqId", 0);
        BioEndUserMaintenanceRequest maintenanceRequest = this.bioDBQueryManager.findById(mReqId, BioEndUserMaintenanceRequest.class);
        if (DataSecurityChecker.maintenanceRequestUpdateBlocked(request, maintenanceRequest, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        request.setAttribute("deviceInfoObj", (Object)maintenanceRequest.getHospitalDevice());
        request.setAttribute("mReqInfoObj", (Object)maintenanceRequest);
        this.basicForward(request, response, "/bio/JobOrderOpenDisplay");
    }
}
