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
import com.iassets.biomedical.entity.BioSiteContract;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="DailyContractorEvaluationProcess", urlPatterns={"/bio/DailyContractorEvaluationProcess"})
public class DailyContractorEvaluationProcessServlet
extends BioBasicServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdateMode;
        String langCode = this.getSessionLanguage(request);
        CommonUser sessionUser = this.getSessionUser(request);
        CommonSite sessionSite = this.getSessionSite(request);
        CommonDirectorate commonDirectorate = this.getSessionDirectorate(request);
        Date now = new Date();
        Date dayDate = WebUtil.getParamValueAsDate(request, "dayDate", null);
        String dayDateStr = WebUtil.getParamValue(request, "dayDate", null);
        if (dayDate != null && dayDate.after(now)) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("jsp.contractor.rejectFuturedate", langCode), Message.MESSAGE_TYPE.ERROR));
            this.sendRedirect(request, response, "/bio/DailyContractorEvaluationDisplay?dayDate=" + dayDateStr);
            return;
        }
        BioDailyContractorEvaluation bioDailyContractorEvaluation = this.bioDBQueryManager.getContractorEvaluationRecordByDate(sessionSite.getId(), dayDate);
        boolean bl = isUpdateMode = bioDailyContractorEvaluation != null;
        if (isUpdateMode) {
            bioDailyContractorEvaluation.setLastModifiedBy(sessionUser.getId());
            bioDailyContractorEvaluation.setLastModifiedIn(now);
        } else {
            bioDailyContractorEvaluation = new BioDailyContractorEvaluation();
            bioDailyContractorEvaluation.setSite(sessionSite);
            int month = DateUtil.getMonth(dayDate);
            int year = DateUtil.getYear(dayDate);
            bioDailyContractorEvaluation.setEvalMonth(month);
            bioDailyContractorEvaluation.setEvalYear(year);
            bioDailyContractorEvaluation.setEvalDate(dayDate);
            bioDailyContractorEvaluation.setCreatedIn(now);
            bioDailyContractorEvaluation.setCreatedBy(sessionUser.getId());
            bioDailyContractorEvaluation.setDirectorateId(commonDirectorate.getId());
            Long totalMo3tamadEmpNo = this.commonDBQueryManager.getSiteActiveUsersCount(this.getSessionSiteId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, Enums.OPERATING_COMPANY_LABOR_CLASSIFICATION.MO3TAMD);
            bioDailyContractorEvaluation.setTotalMo3tamadEmpNo(totalMo3tamadEmpNo.intValue());
            Long totalWorkingEmpNo = this.commonDBQueryManager.getSiteActiveUsersCount(this.getSessionSiteId(request), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP, null);
            bioDailyContractorEvaluation.setTotalWorkingEmpNo(totalWorkingEmpNo.intValue());
            BioSiteContract bioSiteContract = sessionSite.getBioSiteContract();
            bioDailyContractorEvaluation.setTotalContractEmpNo(bioSiteContract.getTotalNumOfEmployees());
            bioDailyContractorEvaluation.setTotalAgentNo(bioSiteContract.getNumOfAgents());
            bioDailyContractorEvaluation.setTotalSubcontractorNo(bioSiteContract.getNumOfSubContractors());
            bioDailyContractorEvaluation.setOtherSupplierNo(bioSiteContract.getNumOfOtherSuppliers());
        }
        int absenceEmployee = WebUtil.getParamValueAsInteger(request, "absenceEmployee", 0);
        int engNo = 0;
        int teckNo = 0;
        int chemNo = 0;
        int compNo = 0;
        int driverNo = 0;
        if (absenceEmployee != 0) {
            engNo = WebUtil.getParamValueAsInteger(request, "engNo", 0);
            teckNo = WebUtil.getParamValueAsInteger(request, "teckNo", 0);
            chemNo = WebUtil.getParamValueAsInteger(request, "chemNo", 0);
            compNo = WebUtil.getParamValueAsInteger(request, "compNo", 0);
            driverNo = WebUtil.getParamValueAsInteger(request, "driverNo", 0);
        }
        bioDailyContractorEvaluation.setAbsBioengNo(engNo);
        bioDailyContractorEvaluation.setAbsBiotechNo(teckNo);
        bioDailyContractorEvaluation.setAbsBiochemNo(chemNo);
        bioDailyContractorEvaluation.setAbsItempNo(compNo);
        bioDailyContractorEvaluation.setAbsDriverNo(driverNo);
        bioDailyContractorEvaluation.setTotalAbsentEmpNo(engNo + teckNo + chemNo + compNo + driverNo);
        int complainSupplier = WebUtil.getParamValueAsInteger(request, "complainSupplier", 0);
        int deviceSuspend = WebUtil.getParamValueAsInteger(request, "deviceSuspend", 0);
        int commitmitEmployee = WebUtil.getParamValueAsInteger(request, "commitmitEmployee", 0);
        int nonComplainceNo = 0;
        int complainNo = 0;
        if (commitmitEmployee != 0) {
            nonComplainceNo = WebUtil.getParamValueAsInteger(request, "nonComplainceNo", 0);
        }
        if (complainSupplier != 0) {
            complainNo = WebUtil.getParamValueAsInteger(request, "complainNo", 0);
        }
        bioDailyContractorEvaluation.setSuppliersComplainsNo(complainNo);
        bioDailyContractorEvaluation.setUniformViolentEmpNo(nonComplainceNo);
        bioDailyContractorEvaluation.setPcSuspended((byte)deviceSuspend);
        bioDailyContractorEvaluation = this.bioDBQueryManager.mergeEntity(bioDailyContractorEvaluation);
        if (isUpdateMode) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.MonthlyContractorEvaluationProcess.EditEvaluation", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/bio/DailyContractorEvaluationDisplay?dayDate=" + dayDateStr);
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.MonthlyContractorEvaluationProcess.AddEvaluation", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/bio/DailyContractorEvaluationDisplay?dayDate=" + dayDateStr);
        }
    }
}
