/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.general.servlet.report;

import com.iassets.common.bo.PPMTableRecord;
import com.iassets.common.util.Enums;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/gen/ViewPPMTable"})
public class ViewPPMTable
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int siteId = this.getSessionSiteId(request);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        String query = "SELECT name, subcontractor, model, pm_visits_months, (pm_annual_visits_no * 3) AS contract_visits_no, count(id) AS device_no_in_site FROM gen_hospital_device WHERE site_id = ? AND status NOT IN (?,?) AND within_warranty = ? AND within_contract = ?";
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        map.put(4, false);
        map.put(5, true);
        query = query + " GROUP BY subcontractor, model ORDER BY subcontractor, name";
        List list = this.genDBQueryManager.executeNativeQueryForSearch(query, map);
        ArrayList<PPMTableRecord> records = null;
        if (list != null && !list.isEmpty()) {
            records = new ArrayList<PPMTableRecord>();
            for (int i = 0; i < list.size(); ++i) {
                Object[] r = (Object[])list.get(i);
                PPMTableRecord tr = new PPMTableRecord();
                tr.setDeviceName((String)r[0]);
                tr.setSubcontractor((String)r[1]);
                tr.setPmVisitsMonths((String)r[3]);
                tr.setContractVisitsNo(((BigInteger)r[4]).intValue());
                tr.setDeviceNoInSite(((BigInteger)r[5]).intValue());
                records.add(tr);
            }
        }
        if (records == null || records.isEmpty()) {
            this.announceErorrMessage(request, response, "\u0639\u0641\u0648\u0627 .. \u0644\u0627 \u062a\u0648\u062c\u062f  \u0623\u062c\u0647\u0632\u0629 \u062e\u0627\u0631\u062c \u0627\u0644\u0636\u0645\u0627\u0646  \u0645\u0646 \u0627\u0644\u0641\u0626\u0629 \u0627\u0644\u0645\u0637\u0644\u0648\u0628\u0629 \u0648\u064a\u062a\u0636\u0645\u0646\u0647\u0627 \u0627\u0644\u0639\u0642\u062f");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", records);
        this.viewPDFReport("rt7.jasper", Enums.REPORT_TITLE.PPM_TABLE.getReportTitle(langCode), null, params, request, response);
    }
}
