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
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDeviceProfile"})
public class ViewDeviceProfile
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        GenHospitalDevice device = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        String deviceDetailsReportUrl = "http://" + WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) + "/gen/ViewDeviceDetailsReport?deviceCode=" + device.getCode();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("device", device);
        params.put("deviceStatus", AppUtil.getDeviceStatusName(device, langCode));
        params.put("deviceDetailsReportUrl", deviceDetailsReportUrl);
        this.viewPDFReport("rt5.jasper", Enums.REPORT_TITLE.DEVICE_ACCESSORIES.getReportTitle(langCode), null, params, request, response);
    }
}
