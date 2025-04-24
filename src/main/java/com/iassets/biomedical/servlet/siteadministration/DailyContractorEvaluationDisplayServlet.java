/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.siteadministration;

import com.iassets.biomedical.entity.BioDailyContractorEvaluation;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="DailyContractorEvaluationDisplay", urlPatterns={"/bio/DailyContractorEvaluationDisplay"})
public class DailyContractorEvaluationDisplayServlet
extends BioBasicServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BioDailyContractorEvaluation bioDailyContractorEvaluation;
        String langCode = this.getSessionLanguage(request);
        Date today = new Date();
        Date evalDate = WebUtil.getParamValueAsDate(request, "dayDate", null);
        if (evalDate != null) {
            if (evalDate.after(today)) {
                evalDate = today;
                this.setMessage(request, new Message(LocalizationManager.getLiteral("jsp.contractor.rejectFuturedate", langCode), Message.MESSAGE_TYPE.ERROR));
            }
        } else {
            evalDate = today;
        }
        if ((bioDailyContractorEvaluation = this.bioDBQueryManager.getContractorEvaluationRecordByDate(this.getSessionSiteId(request), evalDate)) == null) {
            bioDailyContractorEvaluation = new BioDailyContractorEvaluation();
            bioDailyContractorEvaluation.setEvalDate(evalDate);
        }
        request.setAttribute("bioDailyContractorEvaluation", (Object)bioDailyContractorEvaluation);
        this.forward(request, response, "/bio/siteadministration/DailyContractorEvaluation.jsp");
    }
}
