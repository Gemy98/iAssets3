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
package com.iassets.biomedical.servlet.device;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioHospitalDeviceAccessory;
import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.biomedical.entity.BioLookupDeviceFunctionalClassification;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/bio/DeviceInfoProcess"})
public class DeviceInfoProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int i;
        int dep;
        int category;
        String langCode = this.getSessionLanguage(request);
        CommonUser sessionUser = this.getSessionUser(request);
        CommonHospital sessionLocation = this.getSessionLocation(request);
        Date now = new Date();
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        BioHospitalDevice device = null;
        if (deviceId != 0) {
            device = this.bioDBQueryManager.findById(deviceId, BioHospitalDevice.class, "BioHospitalDevice.graph.fetchAll");
            if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            device.setLastModifiedBy(sessionUser);
            device.setLastModifiedIn(now);
        } else {
            device = new BioHospitalDevice();
            device.setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
            device.setSite(this.getSessionSite(request));
            device.setHospital(sessionLocation);
            if (sessionLocation.getHealthCenter().booleanValue()) {
                device.setHospitalLocation(this.bioDBQueryManager.getHospitalLocations(sessionLocation.getId()).get(0));
                device.setHospitalDepartment(this.bioDBQueryManager.getLocationDepartments(sessionLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP).get(0));
            }
            device.setCreatedBy(sessionUser);
            device.setCreatedIn(now);
        }
        device.setCode(WebUtil.getParamValue(request, "code", null));
        device.setSerialNo(WebUtil.getParamValue(request, "serial", null));
        device.setName(WebUtil.getParamValue(request, "name", null));
        device.setManufacturerName(WebUtil.getParamValue(request, "manufacturerName", null));
        device.setModel(WebUtil.getParamValue(request, "model", null));
        int functionalClassification = WebUtil.getParamValueAsInteger(request, "classification", 0);
        if (functionalClassification != 0) {
            device.setFunctionalClassification(this.bioDBQueryManager.getReference(functionalClassification, BioLookupDeviceFunctionalClassification.class));
        }
        if ((category = WebUtil.getParamValueAsInteger(request, "category", 0).intValue()) != 0) {
            device.setCategory(this.bioDBQueryManager.getReference(category, BioLookupDeviceCategory.class));
        }
        device.setAgentName(WebUtil.getParamValue(request, "agentName", null));
        device.setSubcontractor(WebUtil.getParamValue(request, "subcontractor", null));
        int hospLocation = WebUtil.getParamValueAsInteger(request, "hospLocation", 0);
        if (hospLocation != 0) {
            device.setHospitalLocation(this.bioDBQueryManager.getReference(hospLocation, CommonHospitalLocation.class));
        }
        if ((dep = WebUtil.getParamValueAsInteger(request, "dep", 0).intValue()) != 0) {
            device.setHospitalDepartment(this.bioDBQueryManager.getReference(dep, CommonHospitalDepartment.class));
        }
        device.setFloor(WebUtil.getParamValue(request, "hospitalRoomFloor", null));
        device.setHospitalRoom(WebUtil.getParamValue(request, "hospitalRoom", null));
        device.setHospitalLocationDescription(WebUtil.getParamValue(request, "hospitalLocationDescription", null));
        device.setOperationDate(WebUtil.getParamValueAsDate(request, "operationDate", null));
        device.setTa3midNo(WebUtil.getParamValue(request, "ta3midNo", null));
        device.setPrice(WebUtil.getParamValueAsFloat(request, "price", null));
        FileUploadConfig uploadsConfig = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
        Object[] devicePic = FileUtil.uploadFiles(request, "devicePic", uploadsConfig);
        device.setDevicePicture(Common.concatenateValues(devicePic));
        Object[] otherAttachments = FileUtil.uploadFiles(request, "otherAttachments", uploadsConfig);
        device.setOtherAttachments(Common.concatenateValues(otherAttachments));
        device.setWithinWarranty(false);
        device.setWarrantyExpireDate(null);
        device.setWithinContract(false);
        device.setAddedFromAnotherLocation(false);
        device.setLocationName(null);
        device.setAddDate(null);
        String withinWarranty = WebUtil.getParamValue(request, "withinWarranty", null);
        if (withinWarranty != null) {
            device.setWithinWarranty("1".equals(withinWarranty));
        }
        if (device.getWithinWarranty() != null && device.getWithinWarranty().booleanValue()) {
            device.setWarrantyExpireDate(WebUtil.getParamValueAsDate(request, "warrantyExpireDate", null));
            if (category == Enums.DEVICE_CATEGORY.C.getDBId()) {
                device.setWithinContract(true);
            }
        } else {
            String withinContract = WebUtil.getParamValue(request, "withinContract", null);
            if (withinContract != null) {
                device.setWithinContract("1".equals(withinContract));
            }
            if (device.getWithinContract() != null && !device.getWithinContract().booleanValue()) {
                String addedFromAnotherLocation = WebUtil.getParamValue(request, "addedFromAnotherLocation", null);
                if (addedFromAnotherLocation != null) {
                    device.setAddedFromAnotherLocation("1".equals(addedFromAnotherLocation));
                }
                if (device.getAddedFromAnotherLocation() != null && device.getAddedFromAnotherLocation().booleanValue()) {
                    device.setLocationName(WebUtil.getParamValue(request, "locationName", null));
                    device.setAddDate(WebUtil.getParamValueAsDate(request, "addDate", null));
                }
            }
        }
        int annualVisitsNo = WebUtil.getParamValueAsInteger(request, "pmAnnualVisitsNo", null);
        device.setPmAnnualVisitsNo(annualVisitsNo);
        int pmFirstVisitsMonth = WebUtil.getParamValueAsInteger(request, "pmVisitsMonths", 0);
        if (pmFirstVisitsMonth != 0) {
            int step = 12 / annualVisitsNo;
            Object[] months = new Integer[annualVisitsNo];
            months[0] = pmFirstVisitsMonth;
            for (i = 1; i < annualVisitsNo; ++i) {
                months[i] = pmFirstVisitsMonth + step * i > 12 ? pmFirstVisitsMonth + step * i - 12 : pmFirstVisitsMonth + step * i;
            }
            device.setPmVisitsMonths(Common.concatenateValues(months));
        }
        if (device.getHospitalDeviceAccessories() == null) {
            device.setHospitalDeviceAccessories(new ArrayList<BioHospitalDeviceAccessory>());
        } else {
            device.getHospitalDeviceAccessories().clear();
        }
        String deviceAccessories_rowOrder = request.getParameter("deviceAccessories_rowOrder");
        if (deviceAccessories_rowOrder != null && !deviceAccessories_rowOrder.isEmpty()) {
            String[] indexes = deviceAccessories_rowOrder.split(",");
            for (i = 0; i < indexes.length; ++i) {
                BioHospitalDeviceAccessory acc = new BioHospitalDeviceAccessory();
                acc.setQuantity(WebUtil.getParamValueAsInteger(request, "deviceAccessories_accQuantity_" + indexes[i], null));
                acc.setDescription(WebUtil.getParamValue(request, "deviceAccessories_accDescription_" + indexes[i], null));
                device.addHospitalDeviceAccessory(acc);
            }
        }
        device = this.bioDBQueryManager.mergeEntity(device);
        if (deviceId == 0) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.DeviceInfoProcess.addSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/bio/DeviceInfoDisplay");
        } else {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.DeviceInfoProcess.updateSuccess", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/bio/UpdateDeviceInfoSearch");
        }
    }
}
