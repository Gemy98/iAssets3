/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.device;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.biomedical.entity.BioLookupDeviceFunctionalClassification;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.util.Enums;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/DeviceInfoDisplay"})
public class DeviceInfoDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospital currentLocation = null;
        BioHospitalDevice deviceToBeUpdated = (BioHospitalDevice)request.getAttribute("deviceInfoObj");
        currentLocation = deviceToBeUpdated == null ? this.getSessionLocation(request) : deviceToBeUpdated.getHospital();
        if (currentLocation != null && !currentLocation.getHealthCenter().booleanValue()) {
            request.setAttribute("hospLocationList", this.commonDBQueryManager.getHospitalLocations(currentLocation.getId(), langCode));
            request.setAttribute("depList", this.bioDBQueryManager.getLocationDepartments(currentLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, langCode));
        }
        request.setAttribute("currentLocation", (Object)currentLocation);
        request.setAttribute("catList", this.bioDBQueryManager.findAll(BioLookupDeviceCategory.class, langCode));
        request.setAttribute("classificationList", this.bioDBQueryManager.findAll(BioLookupDeviceFunctionalClassification.class, langCode));
        this.forward(request, response, "/bio/device/DeviceInfo.jsp");
    }
}
