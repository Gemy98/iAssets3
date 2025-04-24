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

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ChangeUserNameProcess", "/bio/ChangeUserNameProcess"})
public class ChangeUserNameProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = WebUtil.getSessionUser(request);
        if (!AppUtil.changingUserNameAllowed(sessionUser)) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangeUserNameProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            this.sendRedirect(request, response, "/" + WebUtil.getCurrentlyActiveAppDirectory(request) + "/" + WebUtil.getLastSegmentOfDisplayServletUrl(request));
            return;
        }
        String newUsernamePrefix = WebUtil.getParamValue(request, "newUsername", null);
        if (newUsernamePrefix == null) {
            throw new ServletException("not allowed null username");
        }
        String userNameSuffix = "@" + (WebUtil.sessionUserIsDirectorateUser(request) ? this.getSessionDirectorate(request).getUserNameSpace() : this.getSessionSite(request).getUserNameSpace());
        String newUsername = newUsernamePrefix + userNameSuffix;
        this.commonDBQueryManager.updateUsername(sessionUser, newUsername);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangeUserNameProcess.msg2", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/Logout");
    }
}
