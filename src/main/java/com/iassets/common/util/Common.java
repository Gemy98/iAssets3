/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringEscapeUtils
 *  org.apache.commons.lang.StringUtils
 */
package com.iassets.common.util;

import com.iassets.common.logging.BasicLogger;
import com.iassets.common.logging.LOGGER_TYPE;
import com.iassets.common.logging.MyLogger;
import com.iassets.common.logging.NothingLogger;
import com.iassets.common.util.Default;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class Common {
    private static final LOGGER_TYPE loggingCapabilities = Default.lOG_LEVEL;
    private static final BasicLogger logger = loggingCapabilities == LOGGER_TYPE.FULLY_FEATURED ? new MyLogger() : new NothingLogger();

    public static void log(Exception ex) {
        ex.printStackTrace();
        logger.log(ex);
    }

    public static void log(String str) {
        logger.log(str);
    }

    public static String concatenateValues(Object[] values) {
        String result = "";
        if (values != null) {
            for (Object v : values) {
                result = result + "$";
                result = result + v.toString();
            }
            result = result + "$";
        }
        return "".equals(result) ? null : result;
    }

    public static String[] getConcatenatedValues(String concatenatedValuesStr, boolean sort) {
        if (concatenatedValuesStr == null) {
            return null;
        }
        if (StringUtils.countMatches((String)concatenatedValuesStr, (String)"$") < 2) {
            return new String[]{concatenatedValuesStr};
        }
        Object[] valuesAry = concatenatedValuesStr.split("\\$");
        valuesAry = Arrays.copyOfRange(valuesAry, 1, valuesAry.length);
        if (sort) {
            Arrays.sort(valuesAry);
        }
        return (String[]) valuesAry;
    }

    public static String getDisplayString(Object inputStr, String defaultStr, boolean embeddedDirectlyInHtml) {
        if (inputStr != null && !inputStr.toString().trim().isEmpty()) {
            if (embeddedDirectlyInHtml) {
                return StringEscapeUtils.escapeHtml((String)inputStr.toString());
            }
            return StringEscapeUtils.escapeJava((String)inputStr.toString());
        }
        return defaultStr;
    }

    public static String getDisplayString(Object inputStr, String defaultStr) {
        return Common.getDisplayString(inputStr, defaultStr, false);
    }

    public static int getDisplayBooleanAsInt(Boolean inputBool) {
        return inputBool.compareTo(false);
    }

    public static String formatMoneyValue(Float value) {
        if (value == null || value.floatValue() == 0.0f) {
            return "-";
        }
        return new DecimalFormat("0.00").format(value);
    }
}
