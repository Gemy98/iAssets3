/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.siteadministration;

import com.iassets.biomedical.DB.BioDashboardDBQueryManager;
import com.iassets.biomedical.entity.BioDashboardContractorEvalMeasures;
import com.iassets.biomedical.entity.BioMonthlyContractorEvaluation;
import com.iassets.biomedical.entity.BioSiteContract;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/bio/MonthlyContractorEvaluationDisplay"})
public class MonthlyContractorEvaluationDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private BioDashboardDBQueryManager bioDashboardDBQueryManager;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdateMode;
        CommonSite commonSite = this.getSessionSite(request);
        BioSiteContract bioSiteContract = commonSite.getBioSiteContract();
        String langCode = this.getSessionLanguage(request);
        Date today = new Date();
        int currentYear = today.getYear() + 1900;
        int currentMonth = today.getMonth() + 1;
        Integer month = WebUtil.getParamValueAsInteger(request, "month", currentMonth);
        Integer year = WebUtil.getParamValueAsInteger(request, "year", currentYear);
        if (year > currentYear || month > currentMonth && currentYear == year) {
            year = currentYear;
            month = currentYear;
            this.setMessage(request, new Message(LocalizationManager.getLiteral("jsp.contractor.rejectFuturedate", langCode), Message.MESSAGE_TYPE.ERROR));
        }
        Integer siteId = this.getSessionSiteId(request);
        BioDashboardContractorEvalMeasures bioDashboardContractorEvalMeasures = this.bioDashboardDBQueryManager.getBioDashboardContractorEvalMeasuresBySite(siteId, month, year);
        BioMonthlyContractorEvaluation bioMonthlyContractorEvaluation = this.bioDBQueryManager.getBioMonthlyContractorEvaluation(siteId, month, year);
        boolean bl = isUpdateMode = bioMonthlyContractorEvaluation != null;
        if (bioMonthlyContractorEvaluation == null) {
            bioMonthlyContractorEvaluation = new BioMonthlyContractorEvaluation();
        }
        if (bioDashboardContractorEvalMeasures == null) {
            bioDashboardContractorEvalMeasures = new BioDashboardContractorEvalMeasures();
        }
        Integer startYear = bioSiteContract.getContractStartDate().getYear() + 1900;
        Integer endYear = Math.min(bioSiteContract.getContractEndDate().getYear() + 1900, currentYear);
        request.setAttribute("isUpdateMode", (Object)isUpdateMode);
        request.setAttribute("startYear", (Object)startYear);
        request.setAttribute("endYear", (Object)endYear);
        request.setAttribute("month", (Object)month);
        request.setAttribute("year", (Object)year);
        request.setAttribute("bioDashboardContractorEvalMeasures", (Object)bioDashboardContractorEvalMeasures);
        request.setAttribute("bioMonthlyContractorEvaluation", (Object)bioMonthlyContractorEvaluation);
        this.forward(request, response, "/bio/siteadministration/MonthlyContractorEvaluation.jsp");
    }
}
