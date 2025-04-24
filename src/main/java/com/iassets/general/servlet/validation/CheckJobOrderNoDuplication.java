/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.validation;

import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/CheckJobOrderNoDuplication"})
public class CheckJobOrderNoDuplication
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        String jobOrderNo = WebUtil.getParamValue(request, "jobOrderNo", null);
        GenJobOrder jobOrder = this.genDBQueryManager.checkJobOrderNoDuplication(siteId, jobOrderNo);
        PrintWriter out = response.getWriter();
        if (jobOrder == null) {
            out.print("true");
            return;
        }
        out.print("false");
    }
}
