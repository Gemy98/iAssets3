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
package com.iassets.general.servlet.report.filter;

import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.servlet.GenBasicServlet;
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

@WebServlet(value={"/gen/ViewDevicesByCatnDepSearch", "/gen/ViewDevicesStatusSearch", "/gen/ViewMaintenanceRequestsSearch"})
public class DevicesByCatAndDepFilterDisplayServlet
extends GenBasicServlet {
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
            List<CommonHospitalDepartment> hospDepList = this.genDBQueryManager.getLocationDepartments(currentLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP, langCode);
            if (ajaxRequest) {
                PrintWriter out = response.getWriter();
                out.println(this.getHospitalDepListAsJSONArray(hospDepList, langCode));
                return;
            }
            request.setAttribute("depList", hospDepList);
        }
        this.forward(request, response, "/gen/report/filter/DevicesByDepAndCatFilter.jsp");
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
