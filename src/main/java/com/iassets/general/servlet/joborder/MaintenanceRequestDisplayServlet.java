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

import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/MaintenanceRequestDisplay"})
public class MaintenanceRequestDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int locationId = this.getSessionLocationId(request);
        GenHospitalDevice searchDevice = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        boolean uncodedDevice = searchDevice.getId() == 1;
        request.setAttribute("uncodedDevice", (Object)uncodedDevice);
        if (uncodedDevice) {
            request.setAttribute("hospLocationList", this.genDBQueryManager.getHospitalLocations(locationId, langCode));
            request.setAttribute("depList", this.genDBQueryManager.getLocationDepartments(locationId, Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP, langCode));
        }
        this.forward(request, response, "/gen/joborder/MaintenanceRequest.jsp");
    }
}
