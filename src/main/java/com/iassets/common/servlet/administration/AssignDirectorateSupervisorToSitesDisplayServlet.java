/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.administration;

import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/AssignDirectorateSupervisorToSitesDisplay", "/gen/AssignDirectorateSupervisorToSitesDisplay"})
public class AssignDirectorateSupervisorToSitesDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int directoratId = this.getSessionDirectorate(request).getId();
        Enums.USER_ALLOWED_APP_TYPE currentlyActiveAppType = WebUtil.getCurrentlyActiveApp(request);
        request.setAttribute("sites", this.commonDBQueryManager.getActiveSitesOfDirectorate(directoratId, currentlyActiveAppType, this.getSessionLanguage(request)));
        request.setAttribute("dirSupervisors", this.commonDBQueryManager.getActiveDirectorateSupervisors(directoratId, currentlyActiveAppType));
        this.forwardToBasicTemplate(request, response, "/administration/AssignDirectorateSupervisorToSites.jsp");
    }
}
