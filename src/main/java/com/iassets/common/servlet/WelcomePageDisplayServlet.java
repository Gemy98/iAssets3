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

import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/WelcomePageDisplay", "/gen/WelcomePageDisplay"})
public class WelcomePageDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enums.USER_ALLOWED_APP_TYPE currentlyActiveApp = WebUtil.getCurrentlyActiveApp(request);
        this.setCurrentlyActiveAppInSession(request, currentlyActiveApp);
        this.setSessionLanguage(request);
        String welcomePageUrl = "/UserProfileDisplay";
        CommonUser sessionUser = this.getSessionUser(request);
        if (currentlyActiveApp == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            if (sessionUser.getBioAppWelcomePageUrl() != null) {
                welcomePageUrl = sessionUser.getBioAppWelcomePageUrl();
            }
        } else if (currentlyActiveApp == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && sessionUser.getGenAppWelcomePageUrl() != null) {
            welcomePageUrl = sessionUser.getGenAppWelcomePageUrl();
        }
        this.sendRedirectRelativeToCurrentAppDir(request, response, welcomePageUrl);
    }
}
