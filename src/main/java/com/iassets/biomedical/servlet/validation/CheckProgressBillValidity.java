/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.validation;

import com.iassets.biomedical.entity.BioPPMProgressBill;
import com.iassets.biomedical.entity.BioProgressBill;
import com.iassets.biomedical.entity.BioSparePartsProgressBill;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/CheckProgressBillValidity"})
public class CheckProgressBillValidity
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int siteId = this.getSessionSiteId(request);
        Integer pbType = WebUtil.getParamValueAsInteger(request, "pbType", null);
        Integer paymentNo = WebUtil.getParamValueAsInteger(request, "paymentNo", null);
        String issueNo = WebUtil.getParamValue(request, "issueNo", null);
        Date issueDate = WebUtil.getParamValueAsDate(request, "issueDate", null);
        int excludedPBId = WebUtil.getParamValueAsInteger(request, "pbId", 0);
        BioProgressBill pb = null;
        String validityMsg = "true";
        if (pbType != null) {
            BioPPMProgressBill lastPPMPb = null;
            BioSparePartsProgressBill lastSparePartsPb = null;
            Integer lastPBPaymentNo = null;
            Date lastPBIsuueDate = null;
            if (pbType.intValue() == Enums.PROGRESS_BILL_TYPE.PPM.getId()) {
                lastPPMPb = this.bioDBQueryManager.getLastPPMProgressBill(siteId, excludedPBId);
                if (lastPPMPb != null) {
                    pb = lastPPMPb.getProgressBill();
                    lastPBPaymentNo = pb.getPaymentNo();
                    lastPBIsuueDate = pb.getIssueDate();
                }
            } else if (pbType.intValue() == Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId() && (lastSparePartsPb = this.bioDBQueryManager.getLastSparePartsProgressBill(siteId, excludedPBId)) != null) {
                pb = lastSparePartsPb.getProgressBill();
                lastPBPaymentNo = pb.getPaymentNo();
                lastPBIsuueDate = pb.getIssueDate();
            }
            if (paymentNo != null && lastPBPaymentNo != null) {
                pb = this.bioDBQueryManager.checkPBPaymentNoDuplication(siteId, paymentNo, pbType, excludedPBId);
                if (pb != null) {
                    validityMsg = LocalizationManager.getLiteral("servlet.CheckProgressBillValidity.msg1", this.getSessionLanguage(request));
                } else if (!paymentNo.equals(lastPBPaymentNo + 1)) {
                    validityMsg = LocalizationManager.getLiteral("servlet.CheckProgressBillValidity.msg2", this.getSessionLanguage(request));
                }
            } else if (issueDate != null && lastPBIsuueDate != null) {
                pb = this.bioDBQueryManager.checkIssueNoAndIssueDateDuplication(siteId, issueNo, issueDate, excludedPBId);
                if (pb != null) {
                    validityMsg = LocalizationManager.getLiteral("servlet.CheckProgressBillValidity.msg3", this.getSessionLanguage(request));
                } else if (issueDate.compareTo(lastPBIsuueDate) < 0) {
                    validityMsg = LocalizationManager.getLiteral("servlet.CheckProgressBillValidity.msg4", this.getSessionLanguage(request));
                }
            }
        } else if (issueNo != null && !issueNo.isEmpty() && issueDate != null && (pb = this.bioDBQueryManager.checkIssueNoAndIssueDateDuplication(siteId, issueNo, issueDate, excludedPBId)) != null) {
            validityMsg = LocalizationManager.getLiteral("servlet.CheckProgressBillValidity.msg5", this.getSessionLanguage(request));
        }
        out.print("\"" + Common.getDisplayString(validityMsg, "", true) + "\"");
    }
}
