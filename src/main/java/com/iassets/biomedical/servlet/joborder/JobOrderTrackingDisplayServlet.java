/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/JobOrderTrackingDisplay"})
public class JobOrderTrackingDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BioJobOrder jo = (BioJobOrder)request.getAttribute("jobOrderInfoObj");
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
            this.forward(request, response, "/bio/joborder/" + dest);
        } else {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.JobOrderTrackingDisplay.msg", this.getSessionLanguage(request)));
        }
    }
}
