/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.iassets.common.util;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.entity.AbstractHospitalDevice;
import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.AbstractNonLocalizedEntity;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Common;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Default;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;

public class AppUtil {
    public static boolean ppmNotificationsPublishingAllowed() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(5);
        return day >= Default.PPM_NOTIFICATIONS_START_DAY_OF_MONTH && day <= cal.getActualMaximum(5);
    }

    public static Enums.USER_ALLOWED_APP_TYPE getAllowedAppsOfUser(CommonUser user) {
        if (user != null) {
            if (user.getAllowedApps() != null && user.getAllowedApps() != 0) {
                return Enums.USER_ALLOWED_APP_TYPE.getAppTypeEnum(user.getAllowedApps());
            }
            return Enums.USER_ALLOWED_APP_TYPE.getAppTypeEnum(user.getEmployee().getUserType().getAllowedApps());
        }
        return null;
    }

    public static boolean userHasPrivilegeToHandleGivenGmpDepartment(CommonUser user, int deparmentId) {
        if (user != null) {
            return user.getGmpCategory() == null || user.getGmpCategory().getId() == deparmentId;
        }
        return false;
    }

    public static String getDeviceStatusName(AbstractHospitalDevice device, String langCode) {
        int status = device.getStatus();
        if (status == Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus()) {
            return LocalizationManager.getLiteral("com.iassets.common.util.AppUtil.DeviceStatusName.WORK_PROPERLY", langCode);
        }
        if (status == Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus()) {
            return LocalizationManager.getLiteral("com.iassets.common.util.AppUtil.DeviceStatusName.UNDER_MAINTENANCE", langCode);
        }
        if (status == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
            return LocalizationManager.getLiteral("com.iassets.common.util.AppUtil.DeviceStatusName.SCRAPPED", langCode);
        }
        if (status == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
            return LocalizationManager.getLiteral("com.iassets.common.util.AppUtil.DeviceStatusName.TRANSFERRED", langCode);
        }
        return LocalizationManager.getLiteral("com.iassets.common.util.AppUtil.DeviceStatusName.ELSE", langCode);
    }

    public static boolean jobOrderPhaseIsPartOfMiddlePhases(int phaseId) {
        return phaseId == Enums.JOBORDER_FOLLOWUP_PHASES.AGENT_REPORT_UPLOADED.getId() || phaseId == Enums.JOBORDER_FOLLOWUP_PHASES.QUOTATION_RECIEVED.getId();
    }

    public static Enums.LICENSE_STATUS getBioLicenseStatus(CommonSite site) {
        Date actualEndDate = site.getBioContractEndDate();
        Date endDateWithGracePeriod = DateUtil.addDaysToDate(actualEndDate, 300L);
        if (endDateWithGracePeriod.compareTo(DateUtil.getCurrentDate()) < 0) {
            return Enums.LICENSE_STATUS.EXPIRED;
        }
        if (endDateWithGracePeriod.compareTo(DateUtil.getCurrentDate()) > 0 && actualEndDate.compareTo(DateUtil.getCurrentDate()) < 0) {
            return Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD;
        }
        return Enums.LICENSE_STATUS.getLicenseStatusEnum(site.getBioLicenseStatus());
    }

    public static Enums.LICENSE_STATUS getGenLicenseStatus(CommonSite site) {
        Date actualEndDate = site.getGenContractEndDate();
        Date endDateWithGracePeriod = DateUtil.addDaysToDate(actualEndDate, 300L);
        if (endDateWithGracePeriod.compareTo(DateUtil.getCurrentDate()) < 0) {
            return Enums.LICENSE_STATUS.EXPIRED;
        }
        if (endDateWithGracePeriod.compareTo(DateUtil.getCurrentDate()) > 0 && actualEndDate.compareTo(DateUtil.getCurrentDate()) < 0) {
            return Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD;
        }
        return Enums.LICENSE_STATUS.getLicenseStatusEnum(site.getGenLicenseStatus());
    }

    public static boolean userBelongsToDirectorate(Enums.USER_TYPE userType) {
        switch (userType) {
            case DIRECTORATE_SUPER_ADMIN: 
            case DIRECTORATE_VICE_SUPER_ADMIN: 
            case BIOMEDICAL_DIRECTORATE_ADMIN: 
            case BIOMEDICAL_DIRECTORATE_VICE_ADMIN: 
            case BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN: 
            case GENERAL_DIRECTORATE_ADMIN: 
            case GENERAL_DIRECTORATE_VICE_ADMIN: 
            case GENERAL_DIRECTORATE_ASSISTANT_ADMIN: {
                return true;
            }
        }
        return false;
    }

    public static boolean changingUserNameAllowed(CommonUser user) {
        return user != null && user.getUserNameChangeCount() < 2;
    }

    public static boolean userTypeAllowedToOverrideDisplayLanguageRestrictions(Integer userTypeId) {
        Enums.USER_TYPE userType = Enums.USER_TYPE.getUserTypeById(userTypeId);
        switch (userType) {
            case HOSPITAL_DEPARTMENT_SUPERVISOR: {
                return true;
            }
        }
        return false;
    }

    private static <T extends AbstractLocalizedEntity> void localizeEntity(T entity, String langCode) {
        String nameLiteralKey = entity.getNameLiteralKey();
        if (nameLiteralKey != null) {
            entity.setLocalizedName(LocalizationManager.getLiteral(nameLiteralKey, langCode));
        }
    }

    public static <T extends AbstractLocalizedEntity> List<T> sortLocalizedEntityList(List<T> list, String langCode) {
        for (AbstractLocalizedEntity localizedEntity : list) {
            AppUtil.localizeEntity(localizedEntity, langCode);
        }
        Collections.sort(list);
        return list;
    }

    public static String getNamesJsonArrayFromJavaList(List<AbstractNonLocalizedEntity> list) {
        ArrayList<String> nameList = new ArrayList<String>();
        for (AbstractNonLocalizedEntity e : list) {
            nameList.add(e.getName());
        }
        return new JSONArray(nameList).toString();
    }

    public static String[] getPPMVisitMonths(BioHospitalDevice hd) {
        String pmVisitsMonths;
        if (hd != null && (pmVisitsMonths = hd.getPmVisitsMonths()) != null) {
            return Common.getConcatenatedValues(pmVisitsMonths, true);
        }
        return new String[0];
    }

    public static String getPPMVisitMonthsNamesAsString(BioHospitalDevice hd, String langCode) {
        return AppUtil.getPPMVisitMonthsNamesAsString(AppUtil.getPPMVisitMonths(hd), langCode);
    }

    public static String getPPMVisitMonthsNamesAsString(String[] months, String langCode) {
        if (months != null && months.length > 1) {
            StringBuffer sb = new StringBuffer();
            int month = 0;
            for (String monthStr : months) {
                month = Integer.parseInt(monthStr);
                sb.append(DateUtil.getMonthName(month, langCode) + " - ");
            }
            String str = sb.toString();
            str = str.substring(0, str.lastIndexOf("-") - 1);
            return str;
        }
        return "";
    }
}
