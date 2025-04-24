/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.validation;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/CheckDeviceInfoDuplication"})
public class CheckDeviceInfoDuplication
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = WebUtil.getParamValue(request, "code", null);
        String sn = WebUtil.getParamValue(request, "serial", null);
        int siteId = this.getSessionSiteId(request);
        int excludedDeviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        BioHospitalDevice device = this.bioDBQueryManager.checkDeviceCodeOrSerialDuplication(siteId, code, sn, excludedDeviceId);
        PrintWriter out = response.getWriter();
        if (device == null) {
            out.print("true");
            return;
        }
        out.print("false");
    }
}
