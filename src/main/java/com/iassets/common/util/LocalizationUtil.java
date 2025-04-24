/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

public class LocalizationUtil {
    private static String generateDBVariablesLiteralKey(String prefix, Integer directorateId, Integer siteId, Integer id) {
        return "db_variable_" + prefix + "_" + directorateId + "_" + siteId + "_" + id;
    }

    public static String generateDepartmentNameLiteralKey(Integer directorateId, Integer siteId, Integer departmentId) {
        return LocalizationUtil.generateDBVariablesLiteralKey("hdep", directorateId, siteId, departmentId);
    }

    public static String generateLocationNameLiteralKey(Integer directorateId, Integer siteId, Integer locationId) {
        return LocalizationUtil.generateDBVariablesLiteralKey("hlocation", directorateId, siteId, locationId);
    }
}
