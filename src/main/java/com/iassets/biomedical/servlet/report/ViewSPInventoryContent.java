/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report;

import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewAllSPInventoryCategories", "/bio/ViewUnderThresholdSPInventoryCategories"})
public class ViewSPInventoryContent
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportTitle;
        String errorMsg;
        String langCode = this.getSessionLanguage(request);
        String uri = WebUtil.getRequestUriLastSegment(request);
        boolean underthresholdFlag = uri.contains("ViewUnderThresholdSPInventoryCategories");
        if (underthresholdFlag) {
            errorMsg = LocalizationManager.getLiteral("servlet.ViewSPInventoryContent.msg1", langCode);
            reportTitle = Enums.REPORT_TITLE.SP_INVENTORY_CONTENT_UNDER_THRESHOLD.getReportTitle(langCode);
        } else {
            errorMsg = LocalizationManager.getLiteral("servlet.ViewSPInventoryContent.msg2", langCode);
            reportTitle = Enums.REPORT_TITLE.SP_INVENTORY_CONTENT.getReportTitle(langCode);
        }
        List<BioSpInventoryContent> spInventoryContentList = this.bioDBQueryManager.getSpInventoryContentsWithStats(this.getSessionSiteId(request), underthresholdFlag);
        if (spInventoryContentList == null || spInventoryContentList.isEmpty()) {
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        request.setAttribute("spInventoryContentList", spInventoryContentList);
        this.viewHTMLReport(request, response, "/bio/report/ViewSPInventoryContent.jsp", reportTitle);
    }
}
