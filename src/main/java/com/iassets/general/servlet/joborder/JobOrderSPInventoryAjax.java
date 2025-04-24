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
package com.iassets.general.servlet.joborder;

import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(value={"/gen/JobOrderSPInventoryAjax"})
public class JobOrderSPInventoryAjax
extends GenBasicServlet {
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
        GenSpInventoryContent spInventoryContent = this.genDBQueryManager.getSpInventoryContentWithStats(this.getSessionSiteId(request), code);
        if (spInventoryContent == null) {
            jsonObj.put("msg", (Object)"\u0627\u0644\u0643\u0648\u062f \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u063a\u064a\u0631 \u0645\u0648\u062c\u0648\u062f");
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
