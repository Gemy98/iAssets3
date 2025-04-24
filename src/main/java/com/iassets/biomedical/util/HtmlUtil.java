/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.biomedical.util;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.CommonHtmlUtil;
import com.iassets.common.util.Enums;

public class HtmlUtil
extends CommonHtmlUtil {
    public static String getDevicePPMVisitMonthesAsHtmlSelect(String name, BioHospitalDevice device, String langCode) {
        String html = "<select id='" + name + "' name='" + name + "'>";
        String[] ppmMonths = AppUtil.getPPMVisitMonths(device);
        if (ppmMonths != null) {
            for (String m : ppmMonths) {
                html = html + "<option value='" + m + "'>" + Enums.YEAR_MONTHS.getNameById(m, langCode) + "</option>";
            }
        }
        html = html + "</select>";
        return html;
    }
}
