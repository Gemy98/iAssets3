/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet;

import com.iassets.common.bo.Message;
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/UpdateDeviceInfo", "/gen/TransferDevice", "/gen/ScrappDevice", "/gen/PpmFollowup", "/gen/OpenJobOrder", "/gen/ViewDeviceCard", "/gen/ViewDeviceDetailsReport", "/gen/OpenMaintenanceRequest", "/gen/InternalPpmFollowup"})
public class SearchForDeviceProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> destinations = new HashMap();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dest = this.getSearchDestination(destinations, request);
        String code = WebUtil.getParamValue(request, "deviceCode", null);
        String sn = WebUtil.getParamValue(request, "deviceSerial", null);
        int siteId = this.getSessionSiteId(request);
        int locationId = this.getSessionLocationId(request);
        GenHospitalDevice searchResult = null;
        searchResult = !(!"JobOrderOpenDisplay".equals(dest) && !"MaintenanceRequestDisplay".equals(dest) || !"000".equals(code) && !"000".equals(sn)) ? this.genDBQueryManager.findById(1, GenHospitalDevice.class) : this.genDBQueryManager.getDevice(siteId, locationId, code, sn, null, false, false);
        String msg = null;
        if (searchResult == null) {
            msg = "\u0644\u0645 \u064a\u062a\u0645 \u0627\u0644\u0639\u062b\u0648\u0631 \u0639\u0644\u064a \u0623\u064a \u0646\u062a\u0627\u0626\u062c \u0645\u0648\u0627\u0641\u0642\u0629 \u0644\u0628\u062d\u062b\u0643";
        } else {
            String msgPrefix = "\u0639\u0641\u0648\u0627 .. \u0644\u0627 \u064a\u0645\u0643\u0646 \u0625\u062a\u0645\u0627\u0645 \u0639\u0645\u0644\u064a\u0629 \u0627\u0644\u0628\u062d\u062b .. ";
            if ("JobOrderOpenDisplay".equals(dest) || "MaintenanceRequestDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus()) {
                    msg = "\u064a\u0648\u062c\u062f \u0623\u0645\u0631 \u0639\u0645\u0644 \u0645\u0641\u062a\u0648\u062d \u0644\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628";
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u062a\u0643\u0647\u064a\u0646\u0647";
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u0646\u0642\u0644\u0647 \u0623\u0648 \u0633\u062d\u0628\u0647";
                } else if ("MaintenanceRequestDisplay".equals(dest) && searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.IDLE.getStatus()) {
                    msg = "\u062a\u0645 \u0627\u0644\u0625\u0628\u0644\u0627\u063a \u0639\u0646 \u0627\u0644\u0639\u0637\u0644 \u0628\u0627\u0644\u0641\u0639\u0644";
                } else if ("JobOrderOpenDisplay".equals(dest) && searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.IDLE.getStatus()) {
                    msg = "\u064a\u062c\u0628 \u0641\u062a\u062d \u0623\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0645\u0646 \u0634\u0627\u0634\u0629 \u0628\u0644\u0627\u063a\u0627\u062a \u0627\u0644\u0623\u0639\u0637\u0627\u0644";
                }
            } else if ("DeviceInfoDisplay".equals(dest) || "PPMVisitDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u062a\u0643\u0647\u064a\u0646\u0647";
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u0646\u0642\u0644\u0647 \u0623\u0648 \u0633\u062d\u0628\u0647";
                }
            } else if ("DeviceScrappingDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u0646\u0642\u0644\u0647 \u0623\u0648 \u0633\u062d\u0628\u0647";
                }
            } else if ("DeviceTransferringDisplay".equals(dest)) {
                if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.SCRAPPED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u062a\u0643\u0647\u064a\u0646\u0647";
                } else if (searchResult.getStatus().intValue() == Enums.DEVICE_STATUS.TRANSFERRED.getStatus()) {
                    msg = "\u0627\u0644\u062c\u0647\u0627\u0632 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u062a\u0645 \u0646\u0642\u0644\u0647 \u0623\u0648 \u0633\u062d\u0628\u0647";
                }
            }
            if (msg != null) {
                msg = msgPrefix + msg;
            }
        }
        if (msg == null) {
            request.setAttribute("deviceInfoObj", (Object)searchResult);
            this.basicForward(request, response, "/gen/" + dest);
            return;
        }
        if (WebUtil.getParamValue(request, "report", null) != null) {
            this.announceErorrMessage(request, response, msg);
        } else {
            this.setMessage(request, new Message(msg, Message.MESSAGE_TYPE.ERROR));
            this.basicForward(request, response, "/gen/" + WebUtil.getSearchDisplayURL(request));
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
        destinations.put("InternalPpmFollowup", "InternalPPMVisitDisplay");
    }
}
