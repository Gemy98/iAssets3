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

import com.iassets.common.entity.CommonHospital;
import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenLookupInternalPpmPeriod;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/DeviceInfoDisplay"})
public class DeviceInfoDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonHospital currentLocation = null;
        GenHospitalDevice deviceToBeUpdated = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        currentLocation = deviceToBeUpdated == null ? this.getSessionLocation(request) : deviceToBeUpdated.getHospital();
        if (currentLocation != null && !currentLocation.getHealthCenter().booleanValue()) {
            request.setAttribute("hospLocationList", this.commonDBQueryManager.getHospitalLocations(currentLocation.getId(), langCode));
            request.setAttribute("depList", this.genDBQueryManager.getLocationDepartments(currentLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP, langCode));
        }
        request.setAttribute("currentLocation", (Object)currentLocation);
        List ppmPeriods = this.genDBQueryManager.findAll(GenLookupInternalPpmPeriod.class);
        request.setAttribute("ppmPeriods", (Object)ppmPeriods);
        request.setAttribute("genLookupJobOrderCategoryList", this.genDBQueryManager.findAll(GenLookupJobOrderCategory.class, langCode));
        this.forward(request, response, "/gen/device/DeviceInfo.jsp");
    }
}
