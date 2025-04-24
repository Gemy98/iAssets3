/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.configuration.ConfigurationException
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Common;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.LocalizationUtil;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.ConfigurationException;

@WebServlet(value={"/bio/ManageHospitalBuildingProcess", "/gen/ManageHospitalBuildingProcess"})
public class ManageHospitalBuildingProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] buildingsName;
        String sessionLangCode = WebUtil.getSessionLanguage(request);
        List<CommonHospitalLocation> locations = this.commonDBQueryManager.getHospitalLocations(this.getSessionLocationId(request));
        if (locations != null && !locations.isEmpty()) {
            for (CommonHospitalLocation location : locations) {
                boolean deletedBuilding;
                boolean bl = deletedBuilding = request.getParameter("delete_building_" + location.getId()) != null;
                if (deletedBuilding) {
                    location.setStatus(false);
                } else {
                    for (String langCode : LocalizationManager.SUPPORTED_LANGUAGES) {
                        String buildingName = WebUtil.getParamValue(request, "building_name_" + langCode + "_" + location.getId(), "\u062f\u0648\u0646 \u0627\u0633\u0645");
                        try {
                            LocalizationManager.addOrUpdateLiteral(location.getNameLiteralKey(), buildingName, langCode);
                        }
                        catch (ConfigurationException e) {
                            Common.log((Exception)((Object)e));
                        }
                    }
                }
                this.commonDBQueryManager.mergeEntity(location);
            }
        }
        if ((buildingsName = WebUtil.getParamValuesAsStringArray(request, "building_name_" + sessionLangCode, null)) != null) {
            for (int i = 0; i < buildingsName.length; ++i) {
                CommonHospitalLocation commonHospitalLocation = new CommonHospitalLocation();
                commonHospitalLocation.setSite(this.getSessionSite(request));
                commonHospitalLocation.setHospital(this.getSessionLocation(request));
                commonHospitalLocation.setStatus(true);
                commonHospitalLocation = this.commonDBQueryManager.mergeEntity(commonHospitalLocation);
                String nameLiteralKey = LocalizationUtil.generateLocationNameLiteralKey(this.getSessionDirectorate(request).getId(), this.getSessionSiteId(request), commonHospitalLocation.getId());
                commonHospitalLocation.setNameLiteralKey(nameLiteralKey);
                this.commonDBQueryManager.mergeEntity(commonHospitalLocation);
                for (String langCode : LocalizationManager.SUPPORTED_LANGUAGES) {
                    String localizedBuildingName = WebUtil.getParamValuesAsStringArray(request, "building_name_" + langCode, null)[i];
                    try {
                        LocalizationManager.addOrUpdateLiteral(nameLiteralKey, localizedBuildingName, langCode);
                    }
                    catch (ConfigurationException e) {
                        Common.log((Exception)((Object)e));
                    }
                }
            }
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ManageHospitalBuildingProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/" + WebUtil.getCurrentlyActiveAppDirectory(request) + "/ManageHospitalBuildingDisplay");
    }
}
