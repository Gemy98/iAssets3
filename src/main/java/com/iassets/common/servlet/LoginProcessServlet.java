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
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Common;
import com.iassets.common.util.Default;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/LoginProcess"})
public class LoginProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = this.commonDBQueryManager.getUser(WebUtil.getParamValue(request, "userName", null), WebUtil.getParamValue(request, "password", null));
        if (sessionUser == null || Boolean.TRUE != sessionUser.getStatus()) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.LoginProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/Logout");
            return;
        }
        if (!sessionUser.hasActiveSites()) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.LoginProcess.msg2", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/Logout");
            return;
        }
        Enums.USER_ALLOWED_APP_TYPE userAllowedAppsType = AppUtil.getAllowedAppsOfUser(sessionUser);
        if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && userAllowedAppsType != Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT != userAllowedAppsType) {
            if (userAllowedAppsType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.LoginProcess.msg3", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            } else if (userAllowedAppsType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.LoginProcess.msg4", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            }
            this.basicForward(request, response, "/Logout");
            return;
        }
        try {
            request.login(sessionUser.getUserName(), sessionUser.getPassword());
            sessionUser.setLoginCount(sessionUser.getLoginCount() + 1);
            sessionUser.setLastLoginDate(new Date());
            this.commonDBQueryManager.mergeEntity(sessionUser);
        }
        catch (Exception e) {
            Common.log(e);
            this.basicForward(request, response, "/Logout");
            return;
        }
        this.setSessionUser(request, sessionUser);
        if (WebUtil.userHasRightToChangeSessionHospital(request)) {
            this.sendRedirect(request, response, "/ChooseSessionHospitalDisplay");
            return;
        }
        CommonSite currentSite = sessionUser.getActiveSites().get(0);
        sessionUser.setCurrentSite(currentSite);
        if (!sessionUser.getMovingTeam().booleanValue()) {
            this.setSessionLocation(request, this.commonDBQueryManager.getSiteActiveLocations(currentSite.getId()).get(0));
        } else if (sessionUser.getDepartment() != null) {
            CommonHospital healthcenter = this.commonDBQueryManager.findById(sessionUser.getDepartment().getHospital().getId(), CommonHospital.class);
            this.setSessionLocation(request, healthcenter);
        }
        if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && WebUtil.getAllowedAppsOfSessionUser(request) == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && currentSite.isBioAppActivated() && currentSite.isGenAppActivated()) {
            this.forwardToBasicTemplate(request, response, "/ChooseApplication.jsp");
        } else {
            this.sendRedirectRelativeToCurrentAppDir(request, response, "/WelcomePageDisplay");
        }
    }
}
