/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report;

import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDeviceDetails"})
public class ViewDeviceDetails
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        GenHospitalDevice device = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        request.setAttribute("deviceStatus", (Object)AppUtil.getDeviceStatusName(device, langCode));
        this.viewHTMLReport(request, response, "/gen/report/ViewDeviceDetails.jsp", Enums.REPORT_TITLE.DEVICE_DETAILS.getReportTitle(langCode));
    }
}
