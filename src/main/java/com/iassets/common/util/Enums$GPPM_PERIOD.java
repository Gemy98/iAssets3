///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.LocalizationManager;
//
//public static enum Enums.GPPM_PERIOD {
//    ANNUAL(1, "common.util.Enums.GPPM_PERIOD.ANNUAL"),
//    SEMI_ANNUAL(2, "common.util.Enums.GPPM_PERIOD.SEMI_ANNUAL"),
//    QUARTER(3, "common.util.Enums.GPPM_PERIOD.QUARTER"),
//    MONTHLY(4, "common.util.Enums.GPPM_PERIOD.MONTHLY"),
//    SEMI_MONTHLY(5, "common.util.Enums.GPPM_PERIOD.SEMI_MONTHLY");
//
//    protected int id;
//    protected String nameLiteralKey;
//
//    private Enums.GPPM_PERIOD(int id, String nameLiteralKey) {
//        this.id = id;
//        this.nameLiteralKey = nameLiteralKey;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public String getName(String langCode) {
//        return LocalizationManager.getLiteral(this.nameLiteralKey, langCode);
//    }
//
//    public static Enums.GPPM_PERIOD getGPPMPeriod(int id) {
//        for (Enums.GPPM_PERIOD l : Enums.GPPM_PERIOD.values()) {
//            if (id != l.getId()) continue;
//            return l;
//        }
//        return null;
//    }
//}
