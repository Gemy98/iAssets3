/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.systemsettings;

import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/HospitalContractProfileDisplay", "/gen/HospitalContractProfileDisplay"})
public class HospitalContractProfileDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("site", (Object)this.getSessionSite(request));
        this.forward(request, response, "/systemsettings/" + this.getCurrentlyActiveAppDir(request) + "/ContractProfile.jsp");
    }
}
