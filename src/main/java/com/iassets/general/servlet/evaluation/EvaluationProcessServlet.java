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

import com.iassets.common.bo.Message;
import com.iassets.common.util.Common;
import com.iassets.common.util.WebUtil;
import com.iassets.general.bo.EvaluationTemplateItem;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/EvaluationProcess"})
public class EvaluationProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", null);
        Date startDate = WebUtil.getParamValueAsDate(request, "datepickerfrom", null);
        Date endDate = WebUtil.getParamValueAsDate(request, "datepickerto", null);
        int totalItemsCount = WebUtil.getParamValueAsInteger(request, "totalItemsCount", 0);
        try {
            int siteId = this.getSessionSiteId(request);
            int groupId = WebUtil.getParamValueAsInteger(request, "groupId", 0);
            String paramNameSuffix = null;
            ArrayList<EvaluationTemplateItem> items = new ArrayList<EvaluationTemplateItem>();
            for (int counter = 1; counter <= totalItemsCount; ++counter) {
                paramNameSuffix = groupId + "_" + counter;
                EvaluationTemplateItem item = new EvaluationTemplateItem();
                item.setItemId(WebUtil.getParamValueAsInteger(request, "itemId_" + paramNameSuffix, 0));
                item.setMaxDegree(WebUtil.getParamValueAsInteger(request, "itemMaxDegree_" + paramNameSuffix, 0));
                item.setEvalDegree(WebUtil.getParamValueAsFloat(request, "itemEvalDegree_" + paramNameSuffix, Float.valueOf(0.0f)).floatValue());
                item.setEvalPercentage(WebUtil.getParamValueAsFloat(request, "itemEvalPercentage_" + paramNameSuffix, Float.valueOf(0.0f)).floatValue());
                item.setPenaltyPercentage(WebUtil.getParamValueAsFloat(request, "itemPenaltyPercentage_" + paramNameSuffix, Float.valueOf(0.0f)).floatValue());
                item.setItemDedicatedValue(WebUtil.getParamValueAsFloat(request, "itemDedicatedValue_" + paramNameSuffix, Float.valueOf(0.0f)).floatValue());
                item.setPenaltyValue(WebUtil.getParamValueAsFloat(request, "itemPenaltyValue_" + paramNameSuffix, Float.valueOf(0.0f)).floatValue());
                items.add(item);
            }
            this.genDBTransManager.deleteEvaluationTemplateItems(groupId, siteId, month, year);
            this.genDBTransManager.update(siteId, month, year, startDate);
            this.genDBTransManager.insert(items, siteId, month, year, startDate, endDate);
            this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        }
        catch (Exception e) {
            Common.log(e);
            this.setMessage(request, new Message("\u0644\u0645 \u062a\u062a\u0645 \u0627\u0644\u0639\u0645\u0644\u064a\u0629 \u0628\u0646\u062c\u0627\u062d .. \u0645\u0646 \u0641\u0636\u0644\u0643 \u062d\u0627\u0648\u0644 \u0645\u0631\u0629 \u0623\u062e\u0631\u0649", Message.MESSAGE_TYPE.ERROR));
        }
        this.basicForward(request, response, "/gen/EvaluationDisplay");
    }
}
