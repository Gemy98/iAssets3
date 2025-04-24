/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.systemsettings;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ChangePasswordProcess", "/gen/ChangePasswordProcess"})
public class ChangePasswordProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonUser sessionUser = this.getSessionUser(request);
        String inputCurrentPass = WebUtil.getParamValue(request, "currentPass", null);
        if (inputCurrentPass != null) {
            if (!inputCurrentPass.equals(sessionUser.getPassword())) {
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangePasswordProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
                this.sendRedirectRelativeToCurrentAppDir(request, response, "/ChangePasswordDisplay");
                return;
            }
            String newPass = WebUtil.getParamValue(request, "newPass", null);
            String confNewPass = WebUtil.getParamValue(request, "confNewPass", null);
            if (newPass != null && !newPass.isEmpty() && confNewPass != null && !confNewPass.isEmpty()) {
                if (newPass.equals(confNewPass) && !newPass.equals(sessionUser.getPassword())) {
                    if (!newPass.equals(sessionUser.getEmployee().getUserType().getDefaultPassword())) {
                        this.commonDBQueryManager.updateUserPassword(sessionUser.getId(), newPass);
                        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangePasswordProcess.msg2", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
                        this.sendRedirect(request, response, "/Logout");
                        return;
                    }
                    this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangePasswordProcess.msg3", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
                    this.sendRedirectRelativeToCurrentAppDir(request, response, "/ChangePasswordDisplay");
                    return;
                }
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangePasswordProcess.msg4", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
                this.sendRedirectRelativeToCurrentAppDir(request, response, "/ChangePasswordDisplay");
                return;
            }
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ChangePasswordProcess.msg5", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            this.sendRedirectRelativeToCurrentAppDir(request, response, "/ChangePasswordDisplay");
            return;
        }
    }
}
