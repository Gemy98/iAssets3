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

import com.iassets.common.bo.Message;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ResetUserPasswordProcess", "/gen/ResetUserPasswordProcess"})
public class ResetUserPasswordProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = WebUtil.getParamValueAsInteger(request, "userId", null);
        String newPass = WebUtil.getParamValue(request, "newPass", null);
        String confNewPass = WebUtil.getParamValue(request, "confNewPass", null);
        if (userId != null && newPass != null && !newPass.isEmpty() && confNewPass != null && !confNewPass.isEmpty() && newPass.equals(confNewPass)) {
            this.commonDBQueryManager.updateSiteUserPassword(this.getSessionSiteId(request), userId, newPass);
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ResetUserPasswordProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirectRelativeToCurrentAppDir(request, response, "/ResetUserPasswordDisplay");
        }
    }
}
