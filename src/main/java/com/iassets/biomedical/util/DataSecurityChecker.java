/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package com.iassets.biomedical.util;

import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.entity.BioProgressBill;
import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.WebUtil;
import javax.servlet.http.HttpServletRequest;

public class DataSecurityChecker {
    public static boolean deviceUpdateBlocked(HttpServletRequest request, BioHospitalDevice device, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        boolean bl = blocked = device == null || !device.getSite().getId().equals(sessionSite.getId());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !device.getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }

    public static boolean jobOrderUpdateBlocked(HttpServletRequest request, BioJobOrder jobOrder, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        boolean bl = blocked = jobOrder == null || !jobOrder.getHospitalDevice().getSite().getId().equals(sessionSite.getId()) || Boolean.TRUE.equals(jobOrder.getCancelled()) || Boolean.TRUE.equals(jobOrder.getClosed());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !jobOrder.getHospitalDevice().getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }

    public static boolean spInventoryContentUpdateBlocked(HttpServletRequest request, BioSpInventoryContent sp) {
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        boolean blocked = sp == null || !sp.getSite().getId().equals(sessionSite.getId());
        return blocked;
    }

    public static boolean maintenanceRequestUpdateBlocked(HttpServletRequest request, BioEndUserMaintenanceRequest mReq, boolean includeDepartmentCheck) {
        boolean blocked;
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        CommonHospitalDepartment sessionUserDepartment = WebUtil.getSessionUser(request).getDepartment();
        boolean bl = blocked = mReq == null || !mReq.getSite().getId().equals(sessionSite.getId()) || Boolean.TRUE.equals(mReq.isFake()) || Boolean.TRUE.equals(mReq.isHandled());
        if (includeDepartmentCheck && sessionUserDepartment != null) {
            blocked |= !mReq.getHospitalDepartment().getId().equals(sessionUserDepartment.getId());
        }
        return blocked;
    }

    public static boolean progressBillUpdateBlocked(HttpServletRequest request, BioProgressBill pb) {
        CommonSite sessionSite = WebUtil.getSessionSite(request);
        boolean blocked = pb == null || !pb.getSite().getId().equals(sessionSite.getId());
        return blocked;
    }
}
