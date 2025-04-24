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
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDeviceProfile"})
public class ViewDeviceProfile
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        BioHospitalDevice device = (BioHospitalDevice)request.getAttribute("deviceInfoObj");
        String deviceDetailsReportUrl = "http://" + WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) + "/bio/ViewDeviceDetailsReport?deviceCode=" + device.getCode();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("device", device);
        params.put("deviceStatus", AppUtil.getDeviceStatusName(device, langCode));
        params.put("deviceDetailsReportUrl", deviceDetailsReportUrl);
        this.viewPDFReport("rt5.jasper", Enums.REPORT_TITLE.DEVICE_ACCESSORIES.getReportTitle(langCode), null, params, request, response);
    }
}
