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
import com.iassets.biomedical.entity.BioLookupScrappingReason;
import com.iassets.biomedical.servlet.BioBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/DeviceScrappingDisplay"})
public class DeviceScrappingDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BioHospitalDevice device = (BioHospitalDevice)request.getAttribute("deviceInfoObj");
        request.setAttribute("deviceScrappingInfo", (Object)this.bioDBQueryManager.getDeviceScrappingInfo(device.getId()));
        request.setAttribute("reasons", this.bioDBQueryManager.findAll(BioLookupScrappingReason.class, this.getSessionLanguage(request)));
        this.forward(request, response, "/bio/device/DeviceScrapping.jsp");
    }
}
