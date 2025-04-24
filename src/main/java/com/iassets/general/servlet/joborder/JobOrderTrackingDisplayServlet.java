/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.joborder;

import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/JobOrderTrackingDisplay"})
public class JobOrderTrackingDisplayServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        GenJobOrder jo = (GenJobOrder)request.getAttribute("jobOrderInfoObj");
        String dest = null;
        if (!jo.getClosed().booleanValue() && !jo.getCancelled().booleanValue()) {
            String reqPhaseId = (String)request.getAttribute("phaseId");
            if (reqPhaseId != null) {
                int requiredPhaseId;
                if (WebUtil.userHasRightToUpdateCompletedPhasesOfJobOrderFollowup(request) && (requiredPhaseId = Integer.parseInt(reqPhaseId)) == Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId()) {
                    dest = "JobOrderFollowupFirstPhase.jsp";
                }
            } else {
                int joCurrentPhase = jo.getLastCompletedPhaseId();
                if (joCurrentPhase == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId()) {
                    dest = "JobOrderFollowupFirstPhase.jsp";
                } else if (joCurrentPhase == Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId() || AppUtil.jobOrderPhaseIsPartOfMiddlePhases(joCurrentPhase)) {
                    dest = "JobOrderFollowupSecondPhase.jsp";
                } else if (joCurrentPhase == Enums.JOBORDER_FOLLOWUP_PHASES.SECOND_ACTION_TAKEN.getId() && jo.getSpSource().intValue() == Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
                    dest = "JobOrderFollowupThirdPhase.jsp";
                }
            }
        }
        if (dest != null) {
            if ("JobOrderFollowupFirstPhase.jsp".equals(dest)) {
                int siteId = this.getSessionSiteId(request);
                int gmpCatId = jo.getCategory().getId();
                request.setAttribute("responsibleEngs", this.genDBQueryManager.getEmployees(siteId, new Enums.USER_TYPE[]{Enums.USER_TYPE.GENERAL_SITE_MANGER, Enums.USER_TYPE.GENERAL_ENGINEER, Enums.USER_TYPE.GENERAL_TECHNICIAN}, gmpCatId, false));
                request.setAttribute("gmpDepSupervisor", (Object)this.genDBQueryManager.getEmployees(siteId, Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER, gmpCatId, false).get(0));
                request.setAttribute("genLookupJobOrderCategoryList", this.filterGenJobOrderCategoryByRole(this.genDBQueryManager.findAll(GenLookupJobOrderCategory.class, langCode), jo.getCategory(), request));
            }
            this.forward(request, response, "/gen/joborder/" + dest);
        } else {
            this.announceErorrMessage(request, response, "\u0639\u0641\u0648\u0627 .. \u0644\u0627 \u064a\u0645\u0643\u0646\u0643 \u0637\u0644\u0628 \u0647\u0630\u0627 \u0627\u0644\u0645\u062d\u062a\u0648\u0649");
        }
    }

    private List<GenLookupJobOrderCategory> filterGenJobOrderCategoryByRole(List<GenLookupJobOrderCategory> list, GenLookupJobOrderCategory genLookupJobOrderCategory2, HttpServletRequest request) {
        ArrayList<GenLookupJobOrderCategory> categories = new ArrayList<GenLookupJobOrderCategory>();
        for (GenLookupJobOrderCategory genLookupJobOrderCategory : list) {
            if (genLookupJobOrderCategory.getId() == genLookupJobOrderCategory2.getId()) continue;
            categories.add(genLookupJobOrderCategory);
        }
        return categories;
    }
}
