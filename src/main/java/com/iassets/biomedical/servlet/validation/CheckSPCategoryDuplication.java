/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.validation;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/CheckSPCategoryDuplication"})
public class CheckSPCategoryDuplication
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = WebUtil.getParamValue(request, "code", null);
        int excludedSPCatId = WebUtil.getParamValueAsInteger(request, "spCatId", 0);
        int siteId = this.getSessionSiteId(request);
        BioSpInventoryContent sp = this.bioDBQueryManager.checkSPCategoryDuplication(siteId, code, excludedSPCatId);
        PrintWriter out = response.getWriter();
        if (sp == null) {
            out.print("true");
            return;
        }
        out.print("false");
    }
}
