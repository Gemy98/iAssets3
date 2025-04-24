/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.joborder;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.entity.GenLookupJobOrderSubcategory;
import com.iassets.general.servlet.joborder.JobOrderTrackingProcessServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/gen/JobOrderFollowupFirstPhaseProcess"})
public class JobOrderFollowupFirstPhaseProcessServlet
extends JobOrderTrackingProcessServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int jobOrderId = WebUtil.getParamValueAsInteger(request, "jobOrderId", 0);
        GenJobOrder order = this.genDBQueryManager.findById(jobOrderId, GenJobOrder.class);
        if (DataSecurityChecker.jobOrderUpdateBlocked(request, order, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        if (WebUtil.getParamValueAsInteger(request, "other_depart", 0) == 1) {
            int gmpDepId = WebUtil.getParamValueAsInteger(request, "gmp_id", 0);
            if (gmpDepId != 0) {
                GenLookupJobOrderCategory category = order.getCategory();
                GenLookupJobOrderSubcategory subcatogory = order.getSubCategory();
                order.setDirected(true);
                order.setDirectedFromCategory(category);
                order.setDirectedFromSubcategory(subcatogory);
                order.setDirectingCount(order.getDirectingCount() + 1);
                GenLookupJobOrderCategory genLookupJobOrderCategory = this.genDBQueryManager.getReference(gmpDepId, GenLookupJobOrderCategory.class);
                order.setCategory(genLookupJobOrderCategory);
                int gmpSubId = WebUtil.getParamValueAsInteger(request, "gmp_sub_" + gmpDepId, 0);
                if (gmpSubId != 0) {
                    GenLookupJobOrderSubcategory genLookupJobOrderSubcategory = this.genDBQueryManager.getReference(gmpSubId, GenLookupJobOrderSubcategory.class);
                    order.setSubCategory(genLookupJobOrderSubcategory);
                }
            }
        } else {
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
            Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
            order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
            order.setFirstAction(WebUtil.getParamValue(request, "firstAction", null));
            order.setFirstActionDate(WebUtil.getParamValueAsDate(request, "firstActionDate", null));
            if (order.getLastCompletedPhaseId().intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId()) {
                order.setAgentMustAttend("1".equals(request.getParameter("agentMustAttend")));
                order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId());
            }
            order.setResponsibleEngineer(this.genDBQueryManager.findById(WebUtil.getParamValueAsInteger(request, "responsibleEng", 0), CommonEmployee.class));
            order.setGmpDepartmentSupervisor(this.genDBQueryManager.findById(WebUtil.getParamValueAsInteger(request, "gmpDepSupervisor", 0), CommonEmployee.class));
        }
        order = this.genDBQueryManager.mergeEntity(order);
        if (request.getParameter("showJobOrderReport") == null) {
            this.setMessage(request, new Message("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        }
        this.updateNotificationsThenGoForward(request, response);
    }
}
