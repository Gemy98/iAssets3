/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.evaluation;

import com.iassets.common.util.WebUtil;
import com.iassets.general.bo.EvaluationTemplate;
import com.iassets.general.bo.EvaluationTemplateItem;
import com.iassets.general.entity.GenLookupEvaluationGroup;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/EvaluationDisplay"})
public class EvaluationDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        Integer groupId = WebUtil.getParamValueAsInteger(request, "groupId", null);
        Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", null);
        EvaluationTemplate et = new EvaluationTemplate();
        et.setGroup(this.genDBQueryManager.findById(groupId, GenLookupEvaluationGroup.class));
        List<EvaluationTemplateItem> items = this.genDBQueryManager.getEvaluationTemplateItems(groupId, siteId, month, year);
        if (items == null || items.isEmpty()) {
            items = this.genDBQueryManager.getEvaluationTemplateItems(groupId, siteId);
        }
        et.setItemsList(items);
        request.setAttribute("evaluationTemplate", (Object)et);
        this.basicForward(request, response, "/gen/evaluation/Template.jsp");
    }
}
