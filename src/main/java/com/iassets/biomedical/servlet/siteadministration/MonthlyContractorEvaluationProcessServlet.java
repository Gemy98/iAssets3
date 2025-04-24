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

import com.iassets.biomedical.entity.BioMonthlyContractorEvaluation;
import com.iassets.biomedical.entity.BioSiteContract;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/MonthlyContractorEvaluationProcess"})
public class MonthlyContractorEvaluationProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messageKey;
        CommonSite commonSite = this.getSessionSite(request);
        BioSiteContract bioSiteContract = commonSite.getBioSiteContract();
        String langCode = this.getSessionLanguage(request);
        Date today = new Date();
        int currentYear = today.getYear() + 1900;
        int currentMonth = today.getMonth() + 1;
        Integer month = WebUtil.getParamValueAsInteger(request, "month", 0);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", 0);
        if (month == 0 || year == 0) {
            return;
        }
        if (year > currentYear || month > currentMonth && currentYear == year) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("jsp.contractor.rejectFuturedate", langCode), Message.MESSAGE_TYPE.ERROR));
            this.sendRedirect(request, response, "/bio/MonthlyContractorEvaluationDisplay");
            return;
        }
        Integer siteId = this.getSessionSiteId(request);
        BioMonthlyContractorEvaluation bioMonthlyContractorEvaluation = this.bioDBQueryManager.getBioMonthlyContractorEvaluation(siteId, month, year);
        Boolean isUpdateMode = bioMonthlyContractorEvaluation != null;
        Integer numOfUnpaidEmployess = WebUtil.getParamValueAsInteger(request, "numOfUnpaidEmployess", 0);
        if (isUpdateMode.booleanValue()) {
            bioMonthlyContractorEvaluation.setLastModifiedBy(this.getSessionUser(request));
            bioMonthlyContractorEvaluation.setLastModifiedIn(new Date());
            messageKey = "servlet.MonthlyContractorEvaluationProcess.EditEvaluation";
        } else {
            messageKey = "servlet.MonthlyContractorEvaluationProcess.AddEvaluation";
            bioMonthlyContractorEvaluation = new BioMonthlyContractorEvaluation();
            bioMonthlyContractorEvaluation.setMonth(month);
            bioMonthlyContractorEvaluation.setYear(year);
            bioMonthlyContractorEvaluation.setSite(commonSite);
            bioMonthlyContractorEvaluation.setDirectorateId(this.getSessionDirectorate(request).getId());
            bioMonthlyContractorEvaluation.setCreatedBy(this.getSessionUser(request));
            bioMonthlyContractorEvaluation.setCreatedIn(new Date());
            Integer startContractYear = bioSiteContract.getContractStartDate().getYear() + 1900;
            Integer startContractMonth = bioSiteContract.getContractStartDate().getMonth() + 1;
            Integer endContractYear = bioSiteContract.getContractEndDate().getYear() + 1900;
            Integer endContractMonth = bioSiteContract.getContractEndDate().getMonth() + 1;
            int daysInMonth = this.getNumOfMonthDays(year, month - 1);
            if (year == startContractYear && month == startContractMonth) {
                Integer startContractDay = bioSiteContract.getContractStartDate().getDate();
                daysInMonth -= startContractDay.intValue();
            } else if (year == endContractYear && month == endContractMonth) {
                Integer endContractDay = bioSiteContract.getContractEndDate().getDate();
                daysInMonth = endContractDay;
            }
            bioMonthlyContractorEvaluation.setMonthDaysNo(daysInMonth);
        }
        bioMonthlyContractorEvaluation.setUnpaidEmpNo(numOfUnpaidEmployess);
        bioMonthlyContractorEvaluation = this.bioDBQueryManager.mergeEntity(bioMonthlyContractorEvaluation);
        this.setMessage(request, new Message(LocalizationManager.getLiteral(messageKey, this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/MonthlyContractorEvaluationDisplay");
    }

    private int getNumOfMonthDays(Integer currentYear, Integer currentMonth) {
        GregorianCalendar mycal = new GregorianCalendar(currentYear, currentMonth, 1);
        return ((Calendar)mycal).getActualMaximum(5);
    }
}
