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

@WebServlet(value={"/gen/ChangeUserNameDisplay", "/bio/ChangeUserNameDisplay"})
public class ChangeUserNameDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = WebUtil.getSessionUser(request);
        String nameSpace = null;
        if (AppUtil.changingUserNameAllowed(sessionUser)) {
            nameSpace = WebUtil.sessionUserIsDirectorateUser(request) ? this.getSessionDirectorate(request).getUserNameSpace() : this.getSessionSite(request).getUserNameSpace();
            request.setAttribute("nameSpace", (Object)nameSpace);
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangeUserNameDisplay.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
        }
        String processServlet = WebUtil.getCurrentlyActiveAppDirectory(request) + "/" + WebUtil.getLastSegmentOfProcessServletUrl(request);
        request.setAttribute("processServlet", (Object)processServlet);
        request.setAttribute("currentUsername", (Object)sessionUser.getUserName());
        request.setAttribute("previousChangeCount", (Object)sessionUser.getUserNameChangeCount());
        this.forward(request, response, "/admintools/ChangeUserName.jsp");
    }
}
