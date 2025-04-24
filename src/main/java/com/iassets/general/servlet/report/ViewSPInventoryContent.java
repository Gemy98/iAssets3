/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report;

import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewAllSPInventoryCategories", "/gen/ViewUnderThresholdSPInventoryCategories"})
public class ViewSPInventoryContent
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportTitle;
        String errorMsg;
        String langCode = this.getSessionLanguage(request);
        String uri = WebUtil.getRequestUriLastSegment(request);
        boolean underthresholdFlag = uri.contains("ViewUnderThresholdSPInventoryCategories");
        if (underthresholdFlag) {
            errorMsg = "\u0644\u0627 \u064a\u0648\u062c\u062f \u0642\u0637\u0639 \u063a\u064a\u0627\u0631 \u0628\u0627\u0644\u0645\u0633\u062a\u0648\u062f\u0639 \u062a\u062d\u062a \u0627\u0644\u062d\u062f \u0627\u0644\u0623\u062f\u0646\u0649";
            reportTitle = Enums.REPORT_TITLE.SP_INVENTORY_CONTENT_UNDER_THRESHOLD.getReportTitle(langCode);
        } else {
            errorMsg = "\u0639\u0641\u0648\u0627 .. \u0644\u0645  \u064a\u062a\u0645  \u0625\u0636\u0627\u0641\u0629 \u0623\u064a \u0642\u0637\u0639 \u063a\u064a\u0627\u0631 \u0644\u0644\u0645\u0633\u062a\u0648\u062f\u0639 \u0645\u0646 \u062e\u0644\u0627\u0644 \u0627\u0644\u0646\u0638\u0627\u0645";
            reportTitle = Enums.REPORT_TITLE.SP_INVENTORY_CONTENT.getReportTitle(langCode);
        }
        List<GenSpInventoryContent> spInventoryContentList = this.genDBQueryManager.getSpInventoryContentsWithStats(this.getSessionSiteId(request), underthresholdFlag);
        if (spInventoryContentList == null || spInventoryContentList.isEmpty()) {
            this.announceErorrMessage(request, response, errorMsg);
            return;
        }
        request.setAttribute("spInventoryContentList", spInventoryContentList);
        this.viewHTMLReport(request, response, "/gen/report/ViewSPInventoryContent.jsp", reportTitle);
    }
}
