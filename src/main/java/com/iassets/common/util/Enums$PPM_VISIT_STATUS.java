///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.LocalizationManager;
//
//public static enum Enums.PPM_VISIT_STATUS {
//    IN_TIME(1, "common.util.Enums.PPM_VISIT_STATUS.IN_TIME"),
//    NOT_HAPPENED(2, "common.util.Enums.PPM_VISIT_STATUS.NOT_HAPPENED"),
//    LATE(3, "common.util.Enums.PPM_VISIT_STATUS.LATE");
//
//    protected int id;
//    protected String nameLiteralKey;
//
//    private Enums.PPM_VISIT_STATUS(int id, String nameLiteralKey) {
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
//}
