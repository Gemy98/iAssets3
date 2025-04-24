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
package com.iassets.general.servlet.joborder;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.endpoints.JobOrderNotificationsEndPoint;
import com.iassets.general.endpoints.MaintenanceRequestNotificationsEndPoint;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.entity.GenLookupJobOrderPriority;
import com.iassets.general.entity.GenLookupJobOrderSubcategory;
import com.iassets.general.entity.GenLookupJobOrderType;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@MultipartConfig
@WebServlet(value={"/gen/JobOrderOpenProcess"})
public class JobOrderOpenProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    JobOrderNotificationsEndPoint joNotifier;
    @Autowired
    MaintenanceRequestNotificationsEndPoint mReqNotifier;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int gmpDepId;
        int priority;
        int type;
        Integer mReqId;
        GenEndUserMaintenanceRequest maintenanceRequest;
        boolean uncodedDevice;
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        if (deviceId == 0) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY", langCode));
            return;
        }
        GenHospitalDevice device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class);
        if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        boolean bl = uncodedDevice = device.getId() == 1;
        if (!uncodedDevice) {
            device.setStatus(Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus());
            device = this.genDBQueryManager.mergeEntity(device);
        }
        if ((maintenanceRequest = this.genDBQueryManager.findById(mReqId = WebUtil.getParamValueAsInteger(request, "mReqId", 0), GenEndUserMaintenanceRequest.class)) != null) {
            maintenanceRequest.setHandled(true);
            maintenanceRequest.setHandledBy(this.getSessionUser(request));
            maintenanceRequest.setHandledIn(new Date());
        }
        GenJobOrder order = new GenJobOrder();
        order.setSite(uncodedDevice ? this.getSessionSite(request) : device.getSite());
        order.setHospital(uncodedDevice ? this.getSessionLocation(request) : device.getHospital());
        order.setHospitalDevice(device);
        order.setJobOrderNo(WebUtil.getParamValue(request, "jobOrderNo", null));
        order.setUncodedDevice(uncodedDevice);
        order.setMaintenanceRequest(maintenanceRequest);
        if (uncodedDevice) {
            int depId;
            order.setUncodedDeviceName(WebUtil.getParamValue(request, "uncodedDeviceName", null));
            int hospLocationId = WebUtil.getParamValueAsInteger(request, "hospLocation", 0);
            if (hospLocationId != 0) {
                order.setUncodedDeviceLocation(this.genDBQueryManager.getReference(hospLocationId, CommonHospitalLocation.class));
            }
            if ((depId = WebUtil.getParamValueAsInteger(request, "dep", 0).intValue()) != 0) {
                order.setUncodedDeviceDepartment(this.genDBQueryManager.getReference(depId, CommonHospitalDepartment.class));
            }
            order.setUncodedDeviceRoom(WebUtil.getParamValue(request, "hospitalRoom", null));
            order.setUncodedDeviceLocationDescription(WebUtil.getParamValue(request, "hospitalLocationDescription", null));
        }
        if ((type = WebUtil.getParamValueAsInteger(request, "type_id", 0).intValue()) != 0) {
            order.setJobOrderType(this.genDBQueryManager.getReference(type, GenLookupJobOrderType.class));
        }
        if ((priority = WebUtil.getParamValueAsInteger(request, "priority_id", 0).intValue()) != 0) {
            order.setJobOrderPriority(this.genDBQueryManager.getReference(priority, GenLookupJobOrderPriority.class));
        }
        if ((gmpDepId = WebUtil.getParamValueAsInteger(request, "gmp_id", 0).intValue()) != 0) {
            GenLookupJobOrderCategory genLookupJobOrderCategory = this.genDBQueryManager.getReference(gmpDepId, GenLookupJobOrderCategory.class);
            order.setCategory(genLookupJobOrderCategory);
            int gmpSubId = WebUtil.getParamValueAsInteger(request, "gmp_sub_" + gmpDepId, 0);
            if (gmpSubId != 0) {
                GenLookupJobOrderSubcategory genLookupJobOrderSubcategory = this.genDBQueryManager.getReference(gmpSubId, GenLookupJobOrderSubcategory.class);
                order.setSubCategory(genLookupJobOrderSubcategory);
            }
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
        order.setSiteManager(sessionSite.getGenSiteManager());
        if (device.getHospitalDepartment() != null) {
            order.setRelatedHospitalDepartmentHead(device.getHospitalDepartment().getDepartmentHead());
        }
        order.setMmpDepartmentHead(sessionSite.getGenDepartmentHead());
        order.setMmpDepartmentSupervisor(sessionSite.getGenDepartmentSupervisors().get(0));
        order.setHospitalAssistantDirector(sessionSite.getHospitalAssistantDirector());
        order.setHospitalDirector(sessionSite.getHospitalDirector());
        order.setDirectorateAdmin(sessionSite.getGenDirectorateAdmin());
        order.setDirectorateSuperAdmin(sessionSite.getDirectorateSuperAdmin());
        order.setLastCompletedPhaseId(Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId());
        order = this.genDBQueryManager.mergeEntity(order);
        int siteId = this.getSessionSiteId(request);
        this.mReqNotifier.broadcastNotificationUpdates(siteId);
        this.joNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request), this.getSessionUser(request).getGmpCategory());
        this.sendRedirect(request, response, "/gen/ViewJobOrderReport?jobOrderNo=" + order.getJobOrderNo());
    }
}
