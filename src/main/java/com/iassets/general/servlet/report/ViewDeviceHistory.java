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
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewDeviceHistory"})
public class ViewDeviceHistory
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String code = WebUtil.getParamValue(request, "code", null);
        String sn = WebUtil.getParamValue(request, "sn", null);
        float prevCost = WebUtil.getParamValueAsFloat(request, "prevCost", Float.valueOf(0.0f)).floatValue();
        GenHospitalDevice device = this.genDBQueryManager.getDevice(siteId, locationId, code, sn, null, false, false);
        if (device == null) {
            this.announceErorrMessage(request, response, "\u0644\u0645 \u064a\u062a\u0645 \u0627\u0644\u0639\u062b\u0648\u0631 \u0639\u0644\u064a \u0623\u064a \u0646\u062a\u0627\u0626\u062c \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643");
            return;
        }
        List<GenJobOrder> list = this.genDBQueryManager.getDeviceJobOrders(siteId, locationId, code, sn);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jOrderList", list);
        params.put("device", device);
        params.put("prevCost", Float.valueOf(prevCost));
        params.put("deviceStatus", AppUtil.getDeviceStatusName(device, langCode));
        this.viewPDFReport("rt4.jasper", Enums.REPORT_TITLE.DEVICE_HISTORY.getReportTitle(langCode), null, params, request, response);
    }
}
