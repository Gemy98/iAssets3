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
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewDeviceHistory"})
public class ViewDeviceHistory
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        String code = WebUtil.getParamValue(request, "code", null);
        String sn = WebUtil.getParamValue(request, "sn", null);
        float prevCost = WebUtil.getParamValueAsFloat(request, "prevCost", Float.valueOf(0.0f)).floatValue();
        BioHospitalDevice device = this.bioDBQueryManager.getDevice(siteId, locationId, code, sn, null, false, false);
        if (device == null) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewDeviceHistory.msg", this.getSessionLanguage(request)));
            return;
        }
        List<BioJobOrder> list = this.bioDBQueryManager.getDeviceJobOrders(siteId, locationId, code, sn);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("jOrderList", list);
        params.put("device", device);
        params.put("prevCost", Float.valueOf(prevCost));
        params.put("deviceStatus", AppUtil.getDeviceStatusName(device, langCode));
        this.viewPDFReport("rt4.jasper", Enums.REPORT_TITLE.DEVICE_HISTORY.getReportTitle(langCode), null, params, request, response);
    }
}
