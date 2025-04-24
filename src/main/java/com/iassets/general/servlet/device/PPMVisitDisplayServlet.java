/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.device;

import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/PPMVisitDisplay"})
public class PPMVisitDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int visitMonth = WebUtil.getParamValueAsInteger(request, "visitMonth", 0);
        int visitYear = WebUtil.getParamValueAsInteger(request, "visitYear", 0);
        if (visitMonth != 0 && visitYear != 0) {
            int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
            GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
            if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            request.setAttribute("deviceInfoObj", (Object)device);
            request.setAttribute("ppmVisit", (Object)this.genDBQueryManager.getSpecificPPMDetails(deviceId, visitMonth, visitYear));
            this.basicForward(request, response, "/gen/device/PPMVisit.jsp");
            return;
        }
        this.forward(request, response, "/gen/device/PPMVisit.jsp");
    }
}
