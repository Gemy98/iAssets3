/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.report;

import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.PPMTableRecord;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/bio/ViewPPMTable"})
public class ViewPPMTable
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        int siteId = sessionSite.getId();
        Integer[] warranty = WebUtil.getParamValuesAsIntegerArray(request, "warranty", null);
        Integer[] category = WebUtil.getParamValuesAsIntegerArray(request, "category", null);
        int classification = WebUtil.getParamValueAsInteger(request, "classification", 0);
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        String query = "SELECT name, (SELECT name_literal_key FROM bio_lookup_device_category WHERE id = category_id) AS category, category_id, subcontractor, model, pm_visits_months, (pm_annual_visits_no * ?) AS contract_visits_no, count(id) AS device_no_in_site FROM bio_hospital_device WHERE site_id = ? AND status NOT IN (?,?)";
        map.put(1, sessionSite.getBioContractLengthInYears());
        map.put(2, siteId);
        map.put(3, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(4, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        int count = 5;
        Boolean withinWarranty = null;
        if (warranty != null && warranty.length == 1) {
            withinWarranty = warranty[0] == 1;
            query = query + " AND ( warranty_expire_date " + (withinWarranty != false ? "> ?" : " < ? OR warranty_expire_date IS NULL") + " ) ";
            map.put(count++, new Date());
        }
        if (category != null && category.length != 3) {
            if (category.length == 1) {
                query = query + " AND category_id = ?";
                map.put(count++, category[0]);
            } else if (category.length == 2) {
                query = query + " AND category_id IN (?,?)";
                map.put(count++, category[0]);
                map.put(count++, category[1]);
            }
        }
        if (classification != 0) {
            query = query + " AND functional_classification_id = ?";
            map.put(count++, classification);
        }
        query = query + " GROUP BY category_id, subcontractor, model ORDER BY category_id, subcontractor, name";
        List list = this.bioDBQueryManager.executeNativeQueryForSearch(query, map);
        ArrayList<PPMTableRecord> records = null;
        if (list != null && !list.isEmpty()) {
            records = new ArrayList<PPMTableRecord>();
            for (int i = 0; i < list.size(); ++i) {
                Object[] r = (Object[])list.get(i);
                PPMTableRecord tr = new PPMTableRecord();
                tr.setDeviceName((String)r[0]);
                tr.setCategory(LocalizationManager.getLiteral((String)r[1], langCode));
                tr.setSubcontractor((String)r[3]);
                tr.setPmVisitsMonths((String)r[5]);
                tr.setContractVisitsNo(r[6] == null ? 0 : ((BigInteger)r[6]).intValue());
                tr.setDeviceNoInSite(r[7] == null ? 0 : ((BigInteger)r[7]).intValue());
                records.add(tr);
            }
        }
        if (records == null || records.isEmpty()) {
            this.announceErorrMessage(request, response, LocalizationManager.getLiteral("servlet.ViewPPMTable.msg", langCode));
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceList", records);
        String reportTitle = withinWarranty == null ? Enums.REPORT_TITLE.PPM_TABLE_ALL.getReportTitle(langCode) : (withinWarranty != false ? Enums.REPORT_TITLE.PPM_TABLE_WITHIN_WARRANTY.getReportTitle(langCode) : Enums.REPORT_TITLE.PPM_TABLE_OUTSIDE_WARRANTY.getReportTitle(langCode));
        this.viewPDFReport("rt7.jasper", reportTitle, null, params, request, response);
    }
}
