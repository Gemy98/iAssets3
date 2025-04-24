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
package com.iassets.general.servlet.device;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Common;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Enums;
import com.iassets.common.util.FileUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceAccessory;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmSchedule;
import com.iassets.general.entity.GenLookupInternalPpmPeriod;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(value={"/gen/DeviceInfoProcess"})
public class DeviceInfoProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int dep;
        String langCode = this.getSessionLanguage(request);
        CommonUser sessionUser = this.getSessionUser(request);
        CommonHospital sessionLocation = this.getSessionLocation(request);
        Date now = new Date();
        int deviceId = WebUtil.getParamValueAsInteger(request, "deviceId", 0);
        GenHospitalDevice device = null;
        if (deviceId != 0) {
            device = this.genDBQueryManager.findById(deviceId, GenHospitalDevice.class, "GenHospitalDevice.graph.fetchAll");
            if (DataSecurityChecker.deviceUpdateBlocked(request, device, false)) {
                throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
            }
            device.setLastModifiedBy(sessionUser);
            device.setLastModifiedIn(now);
        } else {
            device = new GenHospitalDevice();
            device.setStatus(Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus());
            device.setSite(this.getSessionSite(request));
            device.setHospital(sessionLocation);
            if (sessionLocation.getHealthCenter().booleanValue()) {
                device.setHospitalLocation(this.genDBQueryManager.getHospitalLocations(sessionLocation.getId()).get(0));
                device.setHospitalDepartment(this.genDBQueryManager.getLocationDepartments(sessionLocation.getId(), Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP).get(0));
            }
            device.setCreatedBy(sessionUser);
            device.setCreatedIn(now);
        }
        device.setCode(WebUtil.getParamValue(request, "code", null));
        device.setSerialNo(WebUtil.getParamValue(request, "serial", null));
        device.setName(WebUtil.getParamValue(request, "name", null));
        device.setManufacturerName(WebUtil.getParamValue(request, "manufacturerName", null));
        device.setModel(WebUtil.getParamValue(request, "model", null));
        device.setSpecs(WebUtil.getParamValue(request, "specs", null));
        int gmpDepId = WebUtil.getParamValueAsInteger(request, "gmp_id", 0);
        device.setLookupCategoryId(gmpDepId);
        device.setSpecHP(WebUtil.getParamValueAsFloat(request, "spec_HP", null));
        device.setSpecRPM(WebUtil.getParamValueAsFloat(request, "spec_RPM", null));
        device.setSpecP(WebUtil.getParamValueAsFloat(request, "spec_P", null));
        device.setSpecT(WebUtil.getParamValueAsFloat(request, "spec_T", null));
        device.setSpecH(WebUtil.getParamValueAsFloat(request, "spec_H", null));
        device.setSpecKG(WebUtil.getParamValueAsFloat(request, "spec_KG", null));
        device.setSpecQ(WebUtil.getParamValueAsFloat(request, "spec_Q", null));
        device.setSpecPH(WebUtil.getParamValueAsFloat(request, "spec_PH", null));
        device.setSpecIP(WebUtil.getParamValueAsFloat(request, "spec_IP", null));
        device.setSpecA(WebUtil.getParamValueAsFloat(request, "spec_A", null));
        device.setSpecV(WebUtil.getParamValueAsFloat(request, "spec_V", null));
        device.setSpecKW(WebUtil.getParamValueAsFloat(request, "spec_KW", null));
        device.setAgentName(WebUtil.getParamValue(request, "agentName", null));
        device.setSubcontractor(WebUtil.getParamValue(request, "subcontractor", null));
        int hospLocation = WebUtil.getParamValueAsInteger(request, "hospLocation", 0);
        if (hospLocation != 0) {
            device.setHospitalLocation(this.genDBQueryManager.getReference(hospLocation, CommonHospitalLocation.class));
        }
        if ((dep = WebUtil.getParamValueAsInteger(request, "dep", 0).intValue()) != 0) {
            device.setHospitalDepartment(this.genDBQueryManager.getReference(dep, CommonHospitalDepartment.class));
        }
        device.setHospitalLocationDescription(WebUtil.getParamValue(request, "hospitalLocationDescription", null));
        device.setHospitalRoom(WebUtil.getParamValue(request, "hospitalRoom", null));
        device.setOperationDate(WebUtil.getParamValueAsDate(request, "operationDate", null));
        device.setTa3midNo(WebUtil.getParamValue(request, "ta3midNo", null));
        device.setPrice(WebUtil.getParamValueAsFloat(request, "price", null));
        FileUploadConfig config = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
        Object[] otherAttachments = FileUtil.uploadFiles(request, "otherAttachments", config);
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
        device.setPmAnnualVisitsNo(WebUtil.getParamValueAsInteger(request, "pmAnnualVisitsNo", null));
        Object[] pmVisitsMonths = request.getParameterValues("pmVisitsMonths");
        if (pmVisitsMonths != null && pmVisitsMonths.length != 0) {
            device.setPmVisitsMonths(Common.concatenateValues(pmVisitsMonths));
        }
        if (device.getHospitalDeviceAccessories() == null) {
            device.setHospitalDeviceAccessories(new ArrayList<GenHospitalDeviceAccessory>());
        } else {
            device.getHospitalDeviceAccessories().clear();
        }
        String deviceAccessories_rowOrder = request.getParameter("deviceAccessories_rowOrder");
        if (deviceAccessories_rowOrder != null && !deviceAccessories_rowOrder.isEmpty()) {
            String[] indexes = deviceAccessories_rowOrder.split(",");
            for (int i = 0; i < indexes.length; ++i) {
                GenHospitalDeviceAccessory acc = new GenHospitalDeviceAccessory();
                acc.setQuantity(WebUtil.getParamValueAsInteger(request, "deviceAccessories_accQuantity_" + indexes[i], null));
                acc.setDescription(WebUtil.getParamValue(request, "deviceAccessories_accDescription_" + indexes[i], null));
                device.addHospitalDeviceAccessory(acc);
            }
        }
        ArrayList<GenHospitalDeviceInternalPpmSchedule> devicePPMScheduleTypes = new ArrayList<GenHospitalDeviceInternalPpmSchedule>(device.getHospitalDeviceInternalPpmSchedules());
        String[] ppmSchedules = WebUtil.getParamValuesAsStringArray(request, "ppmPeriod", null);
        if (device.getHospitalDeviceInternalPpmSchedules() != null) {
            device.getHospitalDeviceInternalPpmSchedules().clear();
        }
        if (ppmSchedules != null) {
            GenHospitalDeviceInternalPpmSchedule ppmSchedule = null;
            FileUploadConfig config1 = new FileUploadConfig(FileUploadConfig.UPLOADS_TYPE.DEVICE_UPLOADS);
            for (String p : ppmSchedules) {
                ppmSchedule = this.getMatchedPPMSchedule(devicePPMScheduleTypes, Integer.parseInt(p));
                if (ppmSchedule == null) {
                    ppmSchedule = new GenHospitalDeviceInternalPpmSchedule();
                    ppmSchedule.setPpmPeriod(this.genDBQueryManager.getReference(Integer.parseInt(p), GenLookupInternalPpmPeriod.class));
                }
                Object[] ppmCheckList = FileUtil.uploadFiles(request, "checklist_file_" + p, config1);
                ppmSchedule.setChecklistFileUrl(Common.concatenateValues(ppmCheckList));
                device.addHospitalDeviceInternalPpmSchedules(ppmSchedule);
            }
        }
        device = this.genDBQueryManager.mergeEntity(device);
        this.createNotificationsSchedule(device);
        if (deviceId == 0) {
            this.setMessage(request, new Message("\u062a\u0645\u062a  \u0625\u0636\u0627\u0641\u0629 \u0627\u0644\u062c\u0647\u0627\u0632 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/gen/DeviceInfoDisplay");
        } else {
            this.setMessage(request, new Message("\u062a\u0645 \u062a\u0639\u062f\u064a\u0644 \u0628\u064a\u0627\u0646\u0627\u062a \u0627\u0644\u062c\u0647\u0627\u0632 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
            this.sendRedirect(request, response, "/gen/UpdateDeviceInfoSearch");
        }
    }

    private GenHospitalDeviceInternalPpmSchedule getMatchedPPMSchedule(List<GenHospitalDeviceInternalPpmSchedule> devicePPMScheduleTypes, int ppmScheduleTypeIdToBeChecked) {
        if (devicePPMScheduleTypes != null) {
            for (GenHospitalDeviceInternalPpmSchedule st : devicePPMScheduleTypes) {
                if (ppmScheduleTypeIdToBeChecked != st.getPpmPeriod().getId()) continue;
                return st;
            }
        }
        return null;
    }

    private void createNotificationsSchedule(GenHospitalDevice device) {
        List<GenHospitalDeviceInternalPpmSchedule> periodList = device.getHospitalDeviceInternalPpmSchedules();
        this.genDBTransManager.deletePpmNotificationsSchedule(device.getId());
        Date refStartDate = device.getSite().getGenPpmRefDate();
        Date refEndDate = device.getSite().getGenContractEndDate();
        HashMap m = new HashMap();
        GenLookupInternalPpmPeriod ppmPeriod = null;
        for (GenHospitalDeviceInternalPpmSchedule internalPpmSchedule : periodList) {
            ppmPeriod = internalPpmSchedule.getPpmPeriod();
            int sequenceDiffInMonth = Math.round(ppmPeriod.getSubsequentDiffInMonth().floatValue());
            Date dateStartedCalculationsFrom = DateUtil.addMonthsToDate(new Date(), -1);
            Date closestStartDate = DateUtil.getClosestStartDateToSpecificDate(dateStartedCalculationsFrom, refStartDate, sequenceDiffInMonth);
            if (ppmPeriod.getId().intValue() == Enums.GPPM_PERIOD.SEMI_MONTHLY.getId()) {
                this.createSemimonthlyNotificationsSchedule(device, refEndDate, m, internalPpmSchedule, sequenceDiffInMonth, closestStartDate);
                continue;
            }
            this.createPPMPeriodNotificationsSchedule(device, refEndDate, m, internalPpmSchedule, sequenceDiffInMonth, closestStartDate);
        }
    }

    private void createPPMPeriodNotificationsSchedule(GenHospitalDevice device, Date refEndDate, Map m, GenHospitalDeviceInternalPpmSchedule internalPpmSchedule, int sequenceDiffInMonth, Date startDate) {
        while (startDate.getTime() < refEndDate.getTime()) {
            GenHospitalDeviceInternalPpmNotificationSchedule notificationSchedule = this.prepareNextPPMNotificationScheduleRecord(device, internalPpmSchedule, startDate);
            String key = DateUtil.getDateString(startDate) + "_" + device.getId();
            if (m.get(key) == null) {
                m.put(key, notificationSchedule);
                this.genDBQueryManager.persistEntity(notificationSchedule);
            }
            startDate = DateUtil.addMonthsToDate(startDate, sequenceDiffInMonth);
        }
    }

    private void createSemimonthlyNotificationsSchedule(GenHospitalDevice device, Date refEndDate, Map m, GenHospitalDeviceInternalPpmSchedule internalPpmSchedule, int sequenceDiffInMonth, Date startDate) {
        int i = 1;
        --sequenceDiffInMonth;
        Date rdate = (Date)startDate.clone();
        while (startDate.getTime() < refEndDate.getTime()) {
            GenHospitalDeviceInternalPpmNotificationSchedule notificationSchedule = this.prepareNextPPMNotificationScheduleRecord(device, internalPpmSchedule, startDate);
            String key = DateUtil.getDateString(startDate) + "_" + device.getId();
            if (m.get(key) == null) {
                m.put(key, notificationSchedule);
                this.genDBQueryManager.persistEntity(notificationSchedule);
            }
            startDate = i % 2 == 0 ? DateUtil.addMonthsToDate(rdate, ++sequenceDiffInMonth) : DateUtil.addMonthsAndDaysToDate(rdate, sequenceDiffInMonth, 15);
            ++i;
        }
    }

    private GenHospitalDeviceInternalPpmNotificationSchedule prepareNextPPMNotificationScheduleRecord(GenHospitalDevice device, GenHospitalDeviceInternalPpmSchedule internalPpmSchedule, Date d) {
        GenLookupInternalPpmPeriod ppmPeriod = internalPpmSchedule.getPpmPeriod();
        GenHospitalDeviceInternalPpmNotificationSchedule notificationSchedule = new GenHospitalDeviceInternalPpmNotificationSchedule();
        notificationSchedule.setSiteId(device.getSite().getId());
        notificationSchedule.setDeviceId(device.getId());
        notificationSchedule.setScheduledPpmId(internalPpmSchedule.getId());
        notificationSchedule.setPpmPeriodId(ppmPeriod.getId());
        notificationSchedule.setPpmPeriodPriority(ppmPeriod.getPeriority());
        notificationSchedule.setPlannedDate(d);
        notificationSchedule.setPlannedMonth(DateUtil.getMonth(d));
        notificationSchedule.setPlannedYear(DateUtil.getYear(d));
        notificationSchedule.setLookupCategoryId(device.getLookupCategoryId());
        Date notificationValidTo = Enums.GPPM_PERIOD.SEMI_MONTHLY.getId() == ppmPeriod.getId().intValue() ? DateUtil.addDaysToDate(d, 14L) : DateUtil.addMonthsAndDaysToDate(d, Math.round(ppmPeriod.getSubsequentDiffInMonth().floatValue()), -1);
        notificationSchedule.setNotificationValidTo(notificationValidTo);
        return notificationSchedule;
    }
}
