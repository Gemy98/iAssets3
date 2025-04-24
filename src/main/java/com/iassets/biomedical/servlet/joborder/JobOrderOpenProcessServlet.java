/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.joborder;

import com.iassets.biomedical.endpoints.JobOrderNotificationsEndPoint;
import com.iassets.biomedical.endpoints.MaintenanceRequestNotificationsEndPoint;
import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.entity.BioLookupJobOrderPriority;
import com.iassets.biomedical.entity.BioLookupJobOrderType;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
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
import org.springframework.beans.factory.annotation.Autowired;

@MultipartConfig
@WebServlet(value={"/bio/JobOrderOpenProcess"})
public class JobOrderOpenProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    JobOrderNotificationsEndPoint joNotifier;
    @Autowired
    MaintenanceRequestNotificationsEndPoint mReqNotifier;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int priority;
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        BioHospitalDevice device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        device.setStatus(Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus());
        BioEndUserMaintenanceRequest maintenanceRequest = this.bioDBQueryManager.getOpenMaintenanceRequestForDevice(sessionSite.getId(), device.getId());
        if (maintenanceRequest != null) {
            maintenanceRequest.setHandled(true);
            maintenanceRequest.setHandledBy(this.getSessionUser(request));
            maintenanceRequest.setHandledIn(new Date());
            maintenanceRequest = this.bioDBQueryManager.mergeEntity(maintenanceRequest);
        }
        BioJobOrder order = new BioJobOrder();
        order.setSite(device.getSite());
        order.setHospital(device.getHospital());
        order.setHospitalDevice(device);
        order.setMaintenanceRequest(maintenanceRequest);
        order.setJobOrderNo(WebUtil.getParamValue(request, "jobOrderNo", null));
        int type = WebUtil.getParamValueAsInteger(request, "type_id", 0);
        if (type != 0) {
            order.setJobOrderType(this.bioDBQueryManager.getReference(type, BioLookupJobOrderType.class));
        }
        if ((priority = WebUtil.getParamValueAsInteger(request, "priority_id", 0).intValue()) != 0) {
            order.setJobOrderPriority(this.bioDBQueryManager.getReference(priority, BioLookupJobOrderPriority.class));
        }
        order.setJobOrderDate(WebUtil.getParamValueAsDate(request, "jobOrderDate", null));
        order.setDamageDescription(WebUtil.getParamValue(request, "damageDescription", null));
        order.setDamageDate(WebUtil.getParamValueAsDate(request, "damageDate", null));
        order.setCancelled(false);
        order.setClosed(false);
        order.setOpenedBy(this.getSessionUser(request));
        order.setOpenedIn(new Date());
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.JOBORDER_UPLOADS);
        Object[] joRequestFormUrl = FileUtil.uploadFiles(request, "joRequestFormUrl", config);
        order.setRequestFormUrl(Common.concatenateValues(joRequestFormUrl));
        order.setResponsibleEngineer(this.bioDBQueryManager.findById(WebUtil.getParamValueAsInteger(request, "responsibleEng", 0), CommonEmployee.class));
        order.setSiteManager(sessionSite.getBioSiteManager());
        order.setRelatedHospitalDepartmentHead(device.getHospitalDepartment().getDepartmentHead());
        order.setMmpDepartmentHead(sessionSite.getBioDepartmentHead());
        order.setMmpDepartmentSupervisor(sessionSite.getBioDepartmentSupervisors().get(0));
        order.setHospitalAssistantDirector(sessionSite.getHospitalAssistantDirector());
        order.setHospitalDirector(sessionSite.getHospitalDirector());
        order.setDirectorateAdmin(sessionSite.getBioDirectorateAdmin());
        order.setDirectorateSuperAdmin(sessionSite.getDirectorateSuperAdmin());
        order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId());
        order = this.bioDBQueryManager.mergeEntity(order);
        int siteId = this.getSessionSiteId(request);
        this.joNotifier.broadcastNotificationUpdates(siteId, langCode);
        this.mReqNotifier.broadcastNotificationUpdates(siteId, langCode);
        this.sendRedirect(request, response, "/bio/ViewJobOrderReport?jobOrderNo=" + order.getJobOrderNo());
    }
}
