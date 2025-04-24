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

import com.iassets.common.util.DateUtil;
import com.iassets.common.util.WebUtil;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/MasterEvaluationDisplay"})
public class MasterEvaluationDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean firstVisit;
        Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", null);
        boolean bl = firstVisit = month == null || year == null;
        if (!firstVisit) {
            boolean updateMode = false;
            Date startDate = this.genDBQueryManager.getEvaluationStartDate(this.getSessionSiteId(request), month, year);
            if (startDate != null) {
                updateMode = true;
            } else {
                startDate = DateUtil.getFirstDateInMonth(month, year);
            }
            request.setAttribute("startDate", (Object)startDate);
            Date endDate = DateUtil.getLastDateInMonth(month, year);
            request.setAttribute("endDate", (Object)endDate);
            request.setAttribute("updateMode", (Object)updateMode);
            request.setAttribute("evaluationGroupList", this.genDBQueryManager.getActiveEvaluationGroups());
        }
        request.setAttribute("firstVisit", (Object)firstVisit);
        this.clearSessionMessage(request);
        this.forwardToBasicTemplate(request, response, "/gen/evaluation/TabbedEvaluation.jsp");
    }
}
