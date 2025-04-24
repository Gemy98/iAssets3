/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.validation;

import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/CheckUserNameDuplication"})
public class CheckUserNameDuplication
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = WebUtil.getSessionUser(request);
        String userNameSuffix = "@" + (WebUtil.sessionUserIsDirectorateUser(request) ? this.getSessionDirectorate(request).getUserNameSpace() : this.getSessionSite(request).getUserNameSpace());
        String newUsernamePrefix = WebUtil.getParamValue(request, "newUsername", null);
        PrintWriter out = response.getWriter();
        if (newUsernamePrefix == null || newUsernamePrefix.trim().isEmpty()) {
            out.print("false");
            return;
        }
        String newUsername = newUsernamePrefix + userNameSuffix;
        if (newUsername.equals(sessionUser.getUserName())) {
            out.print("false");
        } else if (this.commonDBQueryManager.userNameDuplicated(newUsername)) {
            out.print("false");
        } else {
            out.print("true");
        }
    }
}
