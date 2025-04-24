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

import com.iassets.biomedical.entity.BioSiteContract;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Common;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/ManageContractInfoProcess"})
public class ManageContractInfoProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommonSite commonSite = this.getSessionSite(request);
        BioSiteContract bioSiteContract = commonSite.getBioSiteContract();
        int numOfEngineers = WebUtil.getParamValueAsInteger(request, "numOfEngineers", 0);
        bioSiteContract.setNumOfEngineers(numOfEngineers);
        int numOfTechnicians = WebUtil.getParamValueAsInteger(request, "numOfTechnicians", 0);
        bioSiteContract.setNumOfTechnicians(numOfTechnicians);
        int numOfChemists = WebUtil.getParamValueAsInteger(request, "numOfChemists", 0);
        bioSiteContract.setNumOfChemists(numOfChemists);
        int numOfItEmployees = WebUtil.getParamValueAsInteger(request, "numOfItEmployees", 0);
        bioSiteContract.setNumOfItEmployees(numOfItEmployees);
        int numOfDrivers = WebUtil.getParamValueAsInteger(request, "numOfDrivers", 0);
        bioSiteContract.setNumOfDrivers(numOfDrivers);
        bioSiteContract.setTotalNumOfEmployees(numOfEngineers + numOfTechnicians + numOfChemists + numOfItEmployees + numOfDrivers);
        int numOfAgents = WebUtil.getParamValueAsInteger(request, "numOfAgents", 0);
        bioSiteContract.setNumOfAgents(numOfAgents);
        int numOfSubContractors = WebUtil.getParamValueAsInteger(request, "numOfSubContractors", 0);
        bioSiteContract.setNumOfSubContractors(numOfSubContractors);
        int numOfOtherSuppliers = WebUtil.getParamValueAsInteger(request, "numOfOtherSuppliers", 0);
        bioSiteContract.setNumOfOtherSuppliers(numOfOtherSuppliers);
        bioSiteContract.setLastModifiedBy(this.getSessionUser(request));
        bioSiteContract.setLastModifiedIn(new Date());
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.SITE_ADMINISTRATION_UPLOADS);
        Object[] takeoverReportScans = FileUtil.uploadFiles(request, "bioTakeoverReportScanUrl", config);
        bioSiteContract.setTakeoverReportScan(Common.concatenateValues(takeoverReportScans));
        this.bioDBQueryManager.mergeEntity(bioSiteContract);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.UploadTakeOverReportProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/ManageContractInfoDisplay");
    }
}
