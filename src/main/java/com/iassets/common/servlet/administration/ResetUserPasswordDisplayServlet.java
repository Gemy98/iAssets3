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
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ResetUserPasswordDisplay", "/gen/ResetUserPasswordDisplay"})
public class ResetUserPasswordDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", this.commonDBQueryManager.getSiteActiveUsers(this.getSessionSiteId(request), WebUtil.getCurrentlyActiveApp(request)));
        this.forward(request, response, "/administration/ResetUserPassword.jsp");
    }
}
