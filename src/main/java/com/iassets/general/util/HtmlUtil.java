/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.general.util;

import com.iassets.common.util.Common;
import com.iassets.common.util.CommonHtmlUtil;
import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenHospitalDevice;

public class HtmlUtil
extends CommonHtmlUtil {
    public static String[] getPPMVisitMonths(GenHospitalDevice hd) {
        String pmVisitsMonths;
        if (hd != null && (pmVisitsMonths = hd.getPmVisitsMonths()) != null) {
            return Common.getConcatenatedValues(pmVisitsMonths, true);
        }
        return new String[0];
    }

    public static String getDevicePPMVisitMonthesAsHtmlSelect(String name, GenHospitalDevice device, String langCode) {
        String html = "<select id='" + name + "' name='" + name + "'>";
        String[] ppmMonths = HtmlUtil.getPPMVisitMonths(device);
        if (ppmMonths != null) {
            for (String m : ppmMonths) {
                html = html + "<option value='" + m + "'>" + Enums.YEAR_MONTHS.getNameById(m, langCode) + "</option>";
            }
        }
        html = html + "</select>";
        return html;
    }
}
