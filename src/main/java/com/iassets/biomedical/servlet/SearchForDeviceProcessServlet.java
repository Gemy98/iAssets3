/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/UpdateDeviceInfo", "/bio/TransferDevice", "/bio/ScrappDevice", "/bio/PpmFollowup", "/bio/OpenJobOrder", "/bio/ViewDeviceCard", "/bio/ViewDeviceDetailsReport", "/bio/OpenMaintenanceRequest"})
public class SearchForDeviceProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dest = this.getSearchDestination(destinations, request);
        String code = WebUtil.getParamValue(request, "deviceCode", null);
        String sn = WebUtil.getParamValue(request, "deviceSerial", null);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        CommonUser sUser = this.getSessionUser(request);
        BioHospitalDevice searchResult = this.bioDBQueryManager.getDevice(siteId, locationId, code, sn, null, false, false);
        String msg = null;
        if (searchResult == null) {
            msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg1", this.getSessionLanguage(request));
        } else {
            String msgPrefix = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg2", this.getSessionLanguage(request));
            if (sUser.getDepartment() != null && !searchResult.getHospitalDepartment().getId().equals(sUser.getDepartment().getId())) {
                msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg3", this.getSessionLanguage(request));
            } else if ("JobOrderOpenDisplay".equals(dest) || "MaintenanceRequestDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg4", this.getSessionLanguage(request));
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg5", this.getSessionLanguage(request));
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg6", this.getSessionLanguage(request));
                } else if ("MaintenanceRequestDisplay".equals(dest) && searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.IDLE.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg7", this.getSessionLanguage(request));
                } else if ("JobOrderOpenDisplay".equals(dest) && searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.IDLE.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg8", this.getSessionLanguage(request));
                }
            } else if ("DeviceInfoDisplay".equals(dest) || "PPMVisitDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg9", this.getSessionLanguage(request));
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg10", this.getSessionLanguage(request));
                }
            } else if ("DeviceScrappingDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg11", this.getSessionLanguage(request));
                }
            } else if ("DeviceTransferringDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg12", this.getSessionLanguage(request));
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = LocalizationManager.getLiteral("servlet.SearchForDeviceProcess.msg13", this.getSessionLanguage(request));
                }
            }
            if (msg != null) {
                msg = msgPrefix + msg;
            }
        }
        if (msg == null) {
            request.setAttribute("deviceInfoObj", (Object)searchResult);
            this.basicForward(request, response, "/bio/" + dest);
            return;
        }
        if (WebUtil.getParamValue(request, "report", null) != null) {
            this.announceErorrMessage(request, response, msg);
        } else {
            this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/bio/" + WebUtil.getSearchDisplayURL(request));
        }
    }

    static {
        destinations.put("UpdateDeviceInfo", "DeviceInfoDisplay");
        destinations.put("TransferDevice", "DeviceTransferringDisplay");
        destinations.put("ScrappDevice", "DeviceScrappingDisplay");
        destinations.put("PpmFollowup", "PPMVisitDisplay");
        destinations.put("OpenJobOrder", "JobOrderOpenDisplay");
        destinations.put("ViewDeviceCard", "ViewDeviceProfile");
        destinations.put("ViewDeviceDetailsReport", "ViewDeviceDetails");
        destinations.put("OpenMaintenanceRequest", "MaintenanceRequestDisplay");
    }
}
