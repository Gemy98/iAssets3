/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.admintools;

import com.iassets.common.bo.EmployeeManagementUIMetaData;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractSiteEmployeeDisplayServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeManagementUIMetaData uiMetaData = this.prepareEmployeeManagementUIMetaData(request);
        request.setAttribute("currentEmployee", (Object)uiMetaData.getEmployee());
        request.setAttribute("pageTitle", (Object)uiMetaData.getPageTitle());
        request.setAttribute("processServlet", (Object)(WebUtil.getCurrentlyActiveAppDirectory(request) + "/" + WebUtil.getLastSegmentOfProcessServletUrl(request)));
        if (uiMetaData.isEnableAddFunction()) {
            request.setAttribute("enableAddButton", (Object)true);
        }
        if (uiMetaData.isEnableUpdateFunction()) {
            request.setAttribute("enableUpdateButton", (Object)true);
        }
        if (uiMetaData.deleteFunctionEnabled()) {
            request.setAttribute("enableDeleteButton", (Object)true);
        }
        this.forward(request, response, uiMetaData.getViewRelativeURL());
    }

    protected abstract EmployeeManagementUIMetaData prepareEmployeeManagementUIMetaData(HttpServletRequest var1) throws ServletException;
}
