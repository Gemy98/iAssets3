/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Default;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/ChooseSessionHospitalProcess"})
public class ChooseSessionHospitalProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> sitesIds;
        String langCode = this.getSessionLanguage(request);
        if (!WebUtil.userHasRightToChangeSessionHospital(request)) {
            response.sendError(403);
            return;
        }
        int siteId = WebUtil.getParamValueAsInteger(request, "site", 0);
        CommonSite selectedSite = this.commonDBQueryManager.findById(siteId, CommonSite.class);
        CommonUser sessionUser = this.getSessionUser(request);
        int locationId = WebUtil.getParamValueAsInteger(request, "hospital", 0);
        CommonHospital selectedLocation = null;
        if (locationId != 0) {
            selectedLocation = this.commonDBQueryManager.findById(locationId, CommonHospital.class);
        }
        if (!(sitesIds = sessionUser.getActiveSitesIds()).contains(siteId) || selectedLocation != null && !selectedLocation.getStatus().booleanValue()) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        sessionUser.setCurrentSite(selectedSite);
        this.setSessionLocation(request, selectedLocation);
        this.removeSessionAttribute(request, "purpose");
        boolean bioRequest = WebUtil.getParamValue(request, "bioSubmitBtn", null) != null;
        boolean genRequest = WebUtil.getParamValue(request, "genSubmitBtn", null) != null;
        Enums.USER_ALLOWED_APP_TYPE sUserAllowedApps = WebUtil.getAllowedAppsOfSessionUser(request);
        Enums.USER_ALLOWED_APP_TYPE activeApps = Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT;
        if (bioRequest && (activeApps == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP || activeApps == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) && (sUserAllowedApps == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP || sUserAllowedApps == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) && selectedSite.isBioAppActivated()) {
            if (selectedSite.getBioSiteContract() == null) {
                this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ChooseSessionHospitalProcessServlet.SiteBasicDataNotProvided", langCode));
                return;
            }
            this.sendRedirect(request, response, "/bio/WelcomePageDisplay");
        } else if (genRequest && (activeApps == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP || activeApps == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) && (sUserAllowedApps == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP || sUserAllowedApps == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) && selectedSite.isGenAppActivated()) {
            if (selectedSite.getGenSiteContract() == null) {
                this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ChooseSessionHospitalProcessServlet.SiteBasicDataNotProvided", langCode));
                return;
            }
            this.sendRedirect(request, response, "/gen/WelcomePageDisplay");
        } else {
            this.setMessage(request, new Message("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/Logout");
        }
    }
}
