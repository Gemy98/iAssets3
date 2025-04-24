/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;

public class CommonHtmlUtil {
    public static String getMonthesAsHtmlSelect(String name, String cssClassName, String langCode) {
        Enums.YEAR_MONTHS[] items;
        String html = "<select id='" + name + "' name='" + name + "'";
        if (cssClassName != null && !cssClassName.trim().isEmpty()) {
            html = html + "class='" + cssClassName + "'";
        }
        html = html + ">";
        for (Enums.YEAR_MONTHS i : items = Enums.YEAR_MONTHS.values()) {
            html = html + "<option value='" + i.getId() + "'>" + i.getName(langCode) + "</option>";
        }
        html = html + "</select>";
        return html;
    }

    public static String arrayFromJavaToJavaScript(String[] javaAry) {
        String jsAry = "";
        if (javaAry != null && javaAry.length > 0) {
            jsAry = jsAry + "['" + javaAry[0] + "'";
            for (int i = 1; i < javaAry.length; ++i) {
                jsAry = jsAry + ",'" + javaAry[i] + "'";
            }
            jsAry = jsAry + "]";
            return jsAry;
        }
        return "[]";
    }

    public static String arrayFromJavaToJavaScript(String concatenatedString) {
        return CommonHtmlUtil.arrayFromJavaToJavaScript(Common.getConcatenatedValues(concatenatedString, false));
    }

    public static String getResetButtonHTML(String langCode) {
        return "<input type='button' id='biomedapp_return_btn' value='" + LocalizationManager.getLiteral("common.util.CommonHtmlUtil.ResetButtonHTML", langCode) + "' onclick='returnToWelcomePage()' class='pure-skin-mine pure-button-primary' >";
    }

    public static String getSaveThenReturnButtonHTML(String langCode) {
        return "<input type='button' class='pure-skin-mine pure-button-primary'  onclick='_onClickJobOrderFollowupSaveBtn()' name='saveThenReturn' value='" + LocalizationManager.getLiteral("common.util.CommonHtmlUtil.SaveThenReturnButtonHTML", langCode) + "' style='width:80px'>";
    }

    public static String getSaveThenContinueButtonHTML(String langCode) {
        return "<input id='saveThenContinue' type='submit' name='saveThenContinue' value='" + LocalizationManager.getLiteral("common.util.CommonHtmlUtil.SaveThenContinueButtonHTML", langCode) + "' style='width:120px'>";
    }

    public static String getSaveThenGoBackButtonHTML(String langCode) {
        return "<input type='submit' name='saveThenGoBack' value='" + LocalizationManager.getLiteral("common.util.CommonHtmlUtil.SaveThenGoBackButtonHTML", langCode) + "' formnovalidate style='width:120px'>";
    }

    public static String showUploadedFilesInViewMode(String concatenatedStr, boolean sort, String langCode) {
        String[] fNames = Common.getConcatenatedValues(concatenatedStr, sort);
        if (fNames != null && fNames.length != 0) {
            StringBuffer html = new StringBuffer();
            for (String fName : fNames) {
                html.append("<a class='attach_link' href=\"javascript:viewFile('" + fName + "')\">" + fName.substring(fName.lastIndexOf("_") + 1) + "</a>");
            }
            return html.toString();
        }
        return LocalizationManager.getLiteral("common.util.CommonHtmlUtil.showUploadedFilesInViewMode", langCode);
    }

    public static String getEmployeeNameWithJobTitle(CommonEmployee emp, String langCode) {
        if (emp != null) {
            return emp.getName(langCode) + " - " + emp.getJobTitle(langCode) + (emp.getDepartment() != null ? " " + emp.getDepartment().getLocalizedName(langCode) : "");
        }
        return "";
    }

    public static String getBooleanAsString(Boolean b, String langCode) {
        if (b == null) {
            return LocalizationManager.getLiteral("common.util.CommonHtmlUtil.BooleanAsString", langCode);
        }
        return b != false ? LocalizationManager.getLiteral("common.util.CommonHtmlUtil.BooleanAsStringYes", langCode) : LocalizationManager.getLiteral("common.util.CommonHtmlUtil.BooleanAsStringNo", langCode);
    }
}
