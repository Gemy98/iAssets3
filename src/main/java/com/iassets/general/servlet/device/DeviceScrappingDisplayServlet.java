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

import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenLookupScrappingReason;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/DeviceScrappingDisplay"})
public class DeviceScrappingDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GenHospitalDevice device = (GenHospitalDevice)request.getAttribute("deviceInfoObj");
        request.setAttribute("deviceScrappingInfo", (Object)this.genDBQueryManager.getDeviceScrappingInfo(device.getId()));
        request.setAttribute("reasons", this.genDBQueryManager.findAll(GenLookupScrappingReason.class, this.getSessionLanguage(request)));
        this.forward(request, response, "/gen/device/DeviceScrapping.jsp");
    }
}
