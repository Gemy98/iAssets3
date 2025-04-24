/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package com.iassets.general.util;

import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenSpInventoryContent;
import javax.servlet.http.HttpServletRequest;

public class DataSecurityChecker {
    public static boolean deviceUpdateBlocked(HttpServletRequest request, GenHospitalDevice device, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        if (device != null && device.getId() == 1) {
            return false;
        }
        boolean bl = blocked = device == null || !device.getSite().getId().equals(sessionSite.getId());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !device.getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }

    public static boolean jobOrderUpdateBlocked(HttpServletRequest request, GenJobOrder jobOrder, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        boolean bl = blocked = jobOrder == null || !jobOrder.getHospitalDevice().getSite().getId().equals(sessionSite.getId()) || Boolean.TRUE.equals(jobOrder.getCancelled()) || Boolean.TRUE.equals(jobOrder.getClosed());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !jobOrder.getHospitalDevice().getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }

    public static boolean spInventoryContentUpdateBlocked(HttpServletRequest request, GenSpInventoryContent sp) {
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        boolean blocked = sp == null || !sp.getSite().getId().equals(sessionSite.getId());
        return blocked;
    }

    public static boolean maintenanceRequestUpdateBlocked(HttpServletRequest request, GenEndUserMaintenanceRequest gReq, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        boolean bl = blocked = gReq == null || !gReq.getSite().getId().equals(sessionSite.getId()) || Boolean.TRUE.equals(gReq.isFake()) || Boolean.TRUE.equals(gReq.isHandled());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !gReq.getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }
}
