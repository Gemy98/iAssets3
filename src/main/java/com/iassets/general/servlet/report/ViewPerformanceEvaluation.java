/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report;

import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.WebUtil;
import com.iassets.general.bo.EvaluationTemplate;
import com.iassets.general.entity.GenLookupEvaluationGroup;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewPerformanceEvaluation"})
public class ViewPerformanceEvaluation
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", null);
        if (month == null && year == null) {
            this.forward(request, response, "/gen/evaluation/PerformanceEvaluationReport.jsp");
        } else {
            String reportTitle = "\u0627\u0644\u0635\u064a\u0627\u0646\u0629 \u0648\u0627\u0644\u0646\u0638\u0627\u0641\u0629 \u0644\u0645\u0633\u062a\u0634\u0641\u0649  " + this.genDBQueryManager.findById(siteId, CommonSite.class).getLocalizedName(langCode);
            ArrayList<EvaluationTemplate> evaluationTemplatesList = new ArrayList<EvaluationTemplate>();
            List<GenLookupEvaluationGroup> groups = this.genDBQueryManager.getActiveEvaluationGroups();
            for (GenLookupEvaluationGroup group : groups) {
                EvaluationTemplate template = new EvaluationTemplate();
                template.setGroup(group);
                template.setMonth(month);
                template.setYear(year);
                template.setStartDate(this.genDBQueryManager.getEvaluationStartDate(siteId, month, year));
                template.setEndDate(DateUtil.getLastDateInMonth(month, year));
                template.setItemsList(this.genDBQueryManager.getEvaluationTemplateItems(group.getId(), siteId, month, year));
                evaluationTemplatesList.add(template);
            }
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("reportTitleLiteralKey", reportTitle);
            this.viewPDFReport("MasterPerformanceEvaluationReport.jasper", reportTitle, evaluationTemplatesList, params, request, response);
        }
    }
}
