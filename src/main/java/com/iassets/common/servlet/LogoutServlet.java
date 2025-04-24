/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 */
package com.iassets.common.servlet;

import com.iassets.common.bo.Message;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(value={"/Logout"})
public class LogoutServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();
        HttpSession session = request.getSession(false);
        Message prevSessionMsg = null;
        if (session == null || session.isNew()) {
            prevSessionMsg = new Message(LocalizationManager.getLiteral("com.iassets.common.util.Default.SESSION_EXPIRY_MESSAGE_LITERAL_KEY", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR);
        } else {
            prevSessionMsg = this.getMessage(request);
            session.invalidate();
        }
        this.setMessage(request, prevSessionMsg);
        this.sendRedirect(request, response, "/LoginDisplay");
    }
}
