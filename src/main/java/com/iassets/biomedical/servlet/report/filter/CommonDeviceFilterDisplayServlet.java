/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.iassets.biomedical.servlet.report.filter;

import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.biomedical.entity.BioLookupDeviceFunctionalClassification;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(value={"/bio/CommonDeviceFilterDisplay"})
public class CommonDeviceFilterDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean ajaxRequest;
        String langCode = this.getSessionLanguage(request);
        CommonHospital currentLocation = this.getSessionLocation(request);
        String reportType = WebUtil.getParamValue(request, "reportType", null);
        boolean bl = ajaxRequest = reportType != null && !reportType.isEmpty();
        if (ajaxRequest && (currentLocation == null || currentLocation.getHealthCenter().booleanValue())) {
            return;
        }
        if (currentLocation != null && !currentLocation.getHealthCenter().booleanValue()) {
            List<CommonHospitalDepartment> hospDepList = this.bioDBQueryManager.getLocationDepartments(currentLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, langCode);
            if (ajaxRequest) {
                PrintWriter out = response.getWriter();
                out.println(this.getHospitalDepListAsJSONArray(hospDepList, langCode));
                return;
            }
            if (!this.getSessionSite(request).getContainsSeveralLocations().booleanValue()) {
                List<CommonHospitalLocation> hospLocationList = this.commonDBQueryManager.getHospitalLocations(currentLocation.getId(), langCode);
                request.setAttribute("locationList", hospLocationList);
            }
            request.setAttribute("depList", hospDepList);
        }
        request.setAttribute("catList", this.bioDBQueryManager.findAll(BioLookupDeviceCategory.class, langCode));
        request.setAttribute("classificationList", this.bioDBQueryManager.findAll(BioLookupDeviceFunctionalClassification.class, langCode));
    }

    private JSONArray getHospitalDepListAsJSONArray(List<CommonHospitalDepartment> depList, String langCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (CommonHospitalDepartment dep : depList) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("id", (Object)dep.getId());
            jsonObj.put("name", (Object)dep.getLocalizedName(langCode));
            jsonArray.put((Object)jsonObj);
        }
        return jsonArray;
    }
}
