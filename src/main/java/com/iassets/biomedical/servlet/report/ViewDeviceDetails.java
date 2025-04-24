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
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDeviceDetails"})
public class ViewDeviceDetails
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        BioHospitalDevice device = (BioHospitalDevice)request.getAttribute("deviceInfoObj");
        request.setAttribute("deviceStatus", (Object)AppUtil.getDeviceStatusName(device, langCode));
        this.viewHTMLReport(request, response, "/bio/report/ViewDeviceDetails.jsp", Enums.REPORT_TITLE.DEVICE_DETAILS.getReportTitle(langCode));
    }
}
