/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ManageHospitalBuildingDisplay", "/gen/ManageHospitalBuildingDisplay"})
public class ManageHospitalBuildingDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CommonHospitalLocation> locations = this.commonDBQueryManager.getHospitalLocations(this.getSessionLocationId(request), this.getSessionLanguage(request));
        request.setAttribute("processServlet", (Object)(WebUtil.getCurrentlyActiveAppDirectory(request) + "/ManageHospitalBuildingProcess"));
        request.setAttribute("locations", locations);
        request.setAttribute("supportedLanguages", LocalizationManager.SUPPORTED_LANGUAGES);
        this.forward(request, response, "/admintools/ManageHospitalBuilding.jsp");
    }
}
