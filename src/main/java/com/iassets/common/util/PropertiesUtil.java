/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.util.Common;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
    private static String propFileName = "system.properties";
    private static Properties sysProps = new Properties();

    public static String getSystemProperty(String propName) {
        return sysProps.getProperty(propName);
    }

    static {
        try {
            InputStreamReader inputStream = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(propFileName), "UTF8");
            if (inputStream != null) {
                sysProps.load(inputStream);
            }
        }
        catch (Exception e) {
            Common.log(e);
        }
    }
}
