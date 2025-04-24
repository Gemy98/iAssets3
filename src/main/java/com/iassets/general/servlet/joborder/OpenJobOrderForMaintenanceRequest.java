/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.joborder;

import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/OpenJobOrderForMaintenanceRequest"})
public class OpenJobOrderForMaintenanceRequest
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int mReqId = WebUtil.getParamValueAsInteger(request, "mReqId", 0);
        GenEndUserMaintenanceRequest maintenanceRequest = this.genDBQueryManager.findById(mReqId, GenEndUserMaintenanceRequest.class);
        if (DataSecurityChecker.maintenanceRequestUpdateBlocked(request, maintenanceRequest, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        request.setAttribute("deviceInfoObj", (Object)maintenanceRequest.getHospitalDevice());
        request.setAttribute("mReqInfoObj", (Object)maintenanceRequest);
        this.basicForward(request, response, "/gen/JobOrderOpenDisplay");
    }
}
