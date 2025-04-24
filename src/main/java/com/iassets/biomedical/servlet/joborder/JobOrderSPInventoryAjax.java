/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONObject
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(value={"/bio/JobOrderSPInventoryAjax"})
public class JobOrderSPInventoryAjax
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = WebUtil.getParamValue(request, "code", null);
        Integer quantity = WebUtil.getParamValueAsInteger(request, "quantity", null);
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject();
        if (code == null || code.isEmpty()) {
            out.print(true);
            return;
        }
        BioSpInventoryContent spInventoryContent = this.bioDBQueryManager.getSpInventoryContentWithStats(this.getSessionSiteId(request), code);
        if (spInventoryContent == null) {
            jsonObj.put("msg", (Object)LocalizationManager.getLiteral("servlet.JobOrderSPInventoryAjax.msg1", this.getSessionLanguage(request)));
            out.print(jsonObj);
        } else if (quantity == null) {
            jsonObj.put("spDescription", (Object)spInventoryContent.getName());
            jsonObj.put("spQuantity", (Object)spInventoryContent.getAvailableQuantity());
            jsonObj.put("spPrice", (Object)spInventoryContent.getAvgUnitPrice());
            out.print(jsonObj);
        } else if (quantity > spInventoryContent.getAvailableQuantity()) {
            out.print(false);
        } else {
            out.print(true);
        }
    }
}
