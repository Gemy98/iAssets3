///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.LocalizationManager;
//
//public static enum Enums.YEAR_MONTHS {
//    JAN("1", "common.util.Enums.YEAR_MONTHS.m1"),
//    FEB("2", "common.util.Enums.YEAR_MONTHS.m2"),
//    MAR("3", "common.util.Enums.YEAR_MONTHS.m3"),
//    APR("4", "common.util.Enums.YEAR_MONTHS.m4"),
//    MAY("5", "common.util.Enums.YEAR_MONTHS.m5"),
//    JUN("6", "common.util.Enums.YEAR_MONTHS.m6"),
//    JUL("7", "common.util.Enums.YEAR_MONTHS.m7"),
//    AUG("8", "common.util.Enums.YEAR_MONTHS.m8"),
//    SEP("9", "common.util.Enums.YEAR_MONTHS.m9"),
//    OCT("10", "common.util.Enums.YEAR_MONTHS.m10"),
//    NOV("11", "common.util.Enums.YEAR_MONTHS.m11"),
//    DEC("12", "common.util.Enums.YEAR_MONTHS.m12");
//
//    String id;
//    String nameLiteral;
//
//    private Enums.YEAR_MONTHS(String id, String nameLiteral) {
//        this.id = id;
//        this.nameLiteral = nameLiteral;
//    }
//
//    public String getId() {
//        return this.id;
//    }
//
//    public String getName(String langCode) {
//        return LocalizationManager.getLiteral(this.nameLiteral, langCode);
//    }
//
//    public static String getNameById(String id, String langCode) {
//        for (Enums.YEAR_MONTHS i : Enums.YEAR_MONTHS.values()) {
//            if (!i.getId().equals(id)) continue;
//            return i.getName(langCode);
//        }
//        return "no match found";
//    }
//}
