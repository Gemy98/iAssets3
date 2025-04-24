/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet;

import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Default;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChooseApplicationDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = this.getSessionUser(request);
        if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && WebUtil.getAllowedAppsOfSessionUser(request) == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP && sessionUser.getActiveSites().size() == 1 && sessionUser.hasSitesWithBioAppActivated() && sessionUser.hasSitesWithGenAppActivated()) {
            this.forwardToBasicTemplate(request, response, "/ChooseApplication.jsp");
        } else {
            this.sendRedirectRelativeToCurrentAppDir(request, response, "/ChooseSessionHospitalDisplay");
        }
    }
}
