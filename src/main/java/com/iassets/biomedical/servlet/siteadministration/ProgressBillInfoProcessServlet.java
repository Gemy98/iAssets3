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
package com.iassets.biomedical.servlet.siteadministration;

import com.iassets.biomedical.entity.BioPPMProgressBill;
import com.iassets.biomedical.entity.BioProgressBill;
import com.iassets.biomedical.entity.BioSparePartsProgressBill;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/ProgressBillInfoProcess"})
public class ProgressBillInfoProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int ppmId = WebUtil.getParamValueAsInteger(request, "ppmId", 0);
        int spId = WebUtil.getParamValueAsInteger(request, "spId", 0);
        int pbTypeId = WebUtil.getParamValueAsInteger(request, "pbType", 0);
        boolean inUpdateMode = ppmId != 0 || spId != 0;
        BioPPMProgressBill ppmProgressBill = null;
        BioSparePartsProgressBill spProgressBill = null;
        BioProgressBill pb = null;
        CommonUser sessionUser = this.getSessionUser(request);
        Date now = new Date();
        if (inUpdateMode) {
            if (ppmId != 0) {
                ppmProgressBill = this.bioDBQueryManager.findById(ppmId, BioPPMProgressBill.class);
                pb = ppmProgressBill.getProgressBill();
            } else if (spId != 0) {
                spProgressBill = this.bioDBQueryManager.findById(spId, BioSparePartsProgressBill.class);
                pb = spProgressBill.getProgressBill();
            }
            if (DataSecurityChecker.progressBillUpdateBlocked(request, pb)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            pb.setLastModifiedBy(sessionUser);
            pb.setLastModifiedIn(now);
        } else {
            pb = new BioProgressBill();
            pb.setSite(this.getSessionSite(request));
            pb.setPbTypeId(pbTypeId);
            pb.setCreatedBy(sessionUser);
            pb.setCreatedIn(now);
            if (pbTypeId == Enums.PROGRESS_BILL_TYPE.PPM.getId()) {
                ppmProgressBill = new BioPPMProgressBill();
                ppmProgressBill.setProgressBill(pb);
            } else if (pbTypeId == Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()) {
                spProgressBill = new BioSparePartsProgressBill();
                spProgressBill.setProgressBill(pb);
            }
        }
        Float pbValue = WebUtil.getParamValueAsFloat(request, "pbValue", Float.valueOf(0.0f));
        pb.setPaymentNo(WebUtil.getParamValueAsInteger(request, "paymentNo", null));
        pb.setPbValue(pbValue);
        pb.setIssueNo(WebUtil.getParamValue(request, "issueNo", null));
        pb.setIssueDate(WebUtil.getParamValueAsDate(request, "issueDate", null));
        if (ppmId != 0 || pbTypeId == Enums.PROGRESS_BILL_TYPE.PPM.getId()) {
            ppmProgressBill.setFrom(WebUtil.getParamValueAsDate(request, "from", null));
            ppmProgressBill.setTo(WebUtil.getParamValueAsDate(request, "to", null));
            ppmProgressBill = this.bioDBQueryManager.mergeEntity(ppmProgressBill);
        } else if (spId != 0 || pbTypeId == Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()) {
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.SITE_ADMINISTRATION_UPLOADS);
            Object[] serialScan = FileUtil.uploadFiles(request, "serialScan", config);
            spProgressBill.setSerialScan(Common.concatenateValues(serialScan));
            spProgressBill.setRemainedBudgetAfterPB(Float.valueOf(WebUtil.getParamValueAsFloat(request, "remainedBudgetBeforePb", Float.valueOf(0.0f)).floatValue() - pbValue.floatValue()));
            spProgressBill.setPayedBudgetAfterPB(Float.valueOf(WebUtil.getParamValueAsFloat(request, "payedBudgetBeforePb", Float.valueOf(0.0f)).floatValue() + pbValue.floatValue()));
            spProgressBill = this.bioDBQueryManager.mergeEntity(spProgressBill);
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.ProgressBillInfoProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/ProgressBillInfoDisplay?purpose=" + URLEncoder.encode(LocalizationManager.getLiteral("servlet.ProgressBillInfoProcess.msg2", this.getSessionLanguage(request)), "UTF-8"));
    }
}
