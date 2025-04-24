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

import com.iassets.biomedical.entity.BioPPMProgressBill;
import com.iassets.biomedical.entity.BioSparePartsProgressBill;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.util.LocalizationManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/bio/ProgressBillInfoDisplay", "/bio/UpdateLastPPMProgressBill", "/bio/UpdateLastSparePartsProgressBill"})
public class ProgressBillInfoDisplayServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int siteId = this.getSessionSiteId(request);
        String uri = request.getRequestURI();
        if (uri.contains("UpdateLastPPMProgressBill")) {
            BioPPMProgressBill ppmPB = this.bioDBQueryManager.getLastPPMProgressBill(siteId, 0);
            if (ppmPB == null) {
                this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ProgressBillInfoDisplay.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
                this.sendRedirect(request, response, "/bio/ProgressBillInfoDisplay");
                return;
            }
            request.setAttribute("lastPPMProgressBill", (Object)ppmPB);
        } else {
            BioSparePartsProgressBill spPB = this.bioDBQueryManager.getLastSparePartsProgressBill(siteId, 0);
            if (uri.contains("UpdateLastSparePartsProgressBill")) {
                if (spPB == null) {
                    this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ProgressBillInfoDisplay.msg2", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
                    this.sendRedirect(request, response, "/bio/ProgressBillInfoDisplay");
                    return;
                }
                request.setAttribute("lastSparePartsProgressBill", (Object)spPB);
            } else if (uri.contains("ProgressBillInfoDisplay")) {
                BioPPMProgressBill ppmPB = this.bioDBQueryManager.getLastPPMProgressBill(siteId, 0);
                request.setAttribute("newPPMPB_PaymentNo", (Object)(ppmPB != null ? ppmPB.getProgressBill().getPaymentNo() + 1 : 1));
                request.setAttribute("newSPPB_PaymentNo", (Object)(spPB != null ? spPB.getProgressBill().getPaymentNo() + 1 : 1));
                if (spPB != null) {
                    request.setAttribute("remainedValueAfterLastSPPB", (Object)spPB.getRemainedBudgetAfterPB());
                    request.setAttribute("payedValueAfterLastSPPB", (Object)spPB.getPayedBudgetAfterPB());
                }
            }
        }
        this.forward(request, response, "/bio/siteadministration/ProgressBillInfo.jsp");
    }
}
