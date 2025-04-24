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

import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/InternalPPMVisitDisplay"})
public class InternalPPMVisitDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        GenHospitalDevice device = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        List<GenHospitalDeviceInternalPpmNotificationSchedule> ppmNotificationSchedules = this.genDBQueryManager.getCurrentlyScheduledInternalPPMForDevice(siteId, device.getId());
        request.setAttribute("ppmNotificationSchedules", ppmNotificationSchedules);
        request.setAttribute("responsibleEngs", this.genDBQueryManager.getEmployees(siteId, new Enums.USER_TYPE[]{Enums.USER_TYPE.GENERAL_SITE_MANGER, Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER, Enums.USER_TYPE.GENERAL_ENGINEER, Enums.USER_TYPE.GENERAL_TECHNICIAN}, (int)device.getLookupCategoryId(), false));
        this.forward(request, response, "/gen/device/InternalPPMVisit.jsp");
    }
}
