/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet.administration;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/AssignDirectorateSupervisorToSitesProcess", "/gen/AssignDirectorateSupervisorToSitesProcess"})
public class AssignDirectorateSupervisorToSitesProcessServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int directorateId = this.getSessionDirectorate(request).getId();
        Enums.USER_ALLOWED_APP_TYPE currentlyActiveAppType = WebUtil.getCurrentlyActiveApp(request);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("directorateId", directorateId);
        if (currentlyActiveAppType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            map.put("userTypeId", Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN.getId());
        } else if (currentlyActiveAppType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            map.put("userTypeId", Enums.USER_TYPE.GENERAL_DIRECTORATE_ASSISTANT_ADMIN.getId());
        }
        List<CommonSiteEmployee> list = this.commonDBQueryManager.executeNamedQuery("CommonSiteEmployee.findSupervisorsOfDirectorateSites", map, CommonSiteEmployee.class);
        if (list != null && !list.isEmpty()) {
            for (CommonSiteEmployee se : list) {
                this.commonDBQueryManager.deleteEntity(se);
            }
        }
        List<CommonSite> activeDirectorateSites = this.commonDBQueryManager.getActiveSitesOfDirectorate(directorateId, currentlyActiveAppType);
        for (CommonSite s : activeDirectorateSites) {
            String[] dirSupsIds = WebUtil.getParamValuesAsStringArray(request, "empId_" + s.getId(), null);
            if (dirSupsIds == null) continue;
            for (String id : dirSupsIds) {
                CommonSiteEmployee se = new CommonSiteEmployee();
                se.setSite(s);
                se.setEmployee(this.commonDBQueryManager.getReference(Integer.parseInt(id), CommonEmployee.class));
                se.setStatus(true);
                se = this.commonDBQueryManager.mergeEntity(se);
            }
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.AssignDirectorateSupervisorToSitesProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirectRelativeToCurrentAppDir(request, response, "/AssignDirectorateSupervisorToSitesDisplay");
    }
}
