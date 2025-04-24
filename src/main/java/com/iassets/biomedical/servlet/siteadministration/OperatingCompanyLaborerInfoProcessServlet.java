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

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonLookupNationality;
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/OperatingCompanyLaborerInfoProcess"})
public class OperatingCompanyLaborerInfoProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int empId = WebUtil.getParamValueAsInteger(request, "empId", 0);
        String operation = WebUtil.getParamValue(request, "operation", null);
        CommonEmployee employee = null;
        if (empId != 0) {
            employee = this.commonDBQueryManager.findSiteEmployeeById(this.getSessionSiteId(request), empId);
            if (employee == null) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
        } else {
            employee = new CommonEmployee();
        }
        if (operation != null) {
            if (employee.getUserType().getId().intValue() != Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()) {
                this.bioDBQueryManager.deactivateEmployee(this.getSessionSiteId(request), empId);
            }
        } else {
            int nationality;
            int userType;
            employee.setDirectorate(this.getSessionDirectorate(request));
            employee.setMovingTeam(this.getSessionUser(request).getMovingTeam());
            employee.setStatus(true);
            employee.setEmployeeNo(WebUtil.getParamValue(request, "employeeNo", null));
            employee.setNameAr(WebUtil.getParamValue(request, "nameAr", null));
            employee.setNameEn(WebUtil.getParamValue(request, "nameEn", null));
            if ((employee.getUserType() == null || employee.getUserType().getId().intValue() != Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()) && (userType = WebUtil.getParamValueAsInteger(request, "jobTitle", 0).intValue()) != 0) {
                employee.setUserType(this.bioDBQueryManager.getReference(userType, CommonLookupUserType.class));
            }
            if ((nationality = WebUtil.getParamValueAsInteger(request, "nationality", 0).intValue()) != 0) {
                employee.setNationality(this.bioDBQueryManager.getReference(nationality, CommonLookupNationality.class));
            }
            employee.setMobile(WebUtil.getParamValue(request, "mobile", null));
            employee.setContractSalary(WebUtil.getParamValueAsFloat(request, "salary", null));
            employee.setEmploymentDate(WebUtil.getParamValueAsDate(request, "employmentDate", null));
            FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.SITE_ADMINISTRATION_UPLOADS);
            Object[] approvalScans = FileUtil.uploadFiles(request, "approvalScanUrl", config);
            employee.setApprovalScan(Common.concatenateValues(approvalScans));
            employee = this.bioDBQueryManager.mergeEntity(employee);
            if (empId == 0) {
                CommonSiteEmployee cse = new CommonSiteEmployee();
                cse.setSite(this.getSessionSite(request));
                cse.setEmployee(employee);
                cse.setStatus(true);
                cse = this.bioDBQueryManager.mergeEntity(cse);
            }
        }
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.OperatingCompanyLaborerInfoProcess.msg", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/OperatingCompanyLaborerInfoDisplay");
    }
}
