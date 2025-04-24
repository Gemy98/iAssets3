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

import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.AppUtil;
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

@WebServlet(value={"/ChooseSessionHospitalDisplay"})
public class ChooseSessionHospitalDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!WebUtil.userHasRightToChangeSessionHospital(request)) {
            response.sendError(403);
            return;
        }
        String langCode = this.getSessionLanguage(request);
        CommonUser sessionUser = this.getSessionUser(request);
        int siteId = WebUtil.getParamValueAsInteger(request, "site", 0);
        if (siteId != 0) {
            List<Integer> sitesIds = sessionUser.getActiveSitesIds();
            if (!sitesIds.contains(siteId)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            CommonSite site = this.commonDBQueryManager.findById(siteId, CommonSite.class);
            request.setAttribute("locationList", this.commonDBQueryManager.getSiteActiveLocations(siteId, langCode));
            Enums.USER_ALLOWED_APP_TYPE sUserAllowedApps = WebUtil.getAllowedAppsOfSessionUser(request);
            Enums.USER_ALLOWED_APP_TYPE activeApps = Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT;
            if (!(activeApps != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && activeApps != Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP || sUserAllowedApps != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && sUserAllowedApps != Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP || !site.isBioAppActivated())) {
                request.setAttribute("showBioSubmitBtn", (Object)true);
            }
            if (!(activeApps != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && activeApps != Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP || sUserAllowedApps != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && sUserAllowedApps != Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP || !site.isGenAppActivated())) {
                request.setAttribute("showGenSubmitBtn", (Object)true);
            }
        }
        this.setSessionLocation(request, null);
        request.setAttribute("siteList", AppUtil.sortLocalizedEntityList(sessionUser.getActiveSites(), langCode));
        this.forwardToBasicTemplate(request, response, "/ChooseHospital.jsp");
    }
}
