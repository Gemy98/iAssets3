///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import com.iassets.common.util.LocalizationManager;
//
//public static enum Enums.LICENSE_STATUS {
//    INACTIVE(0, "common.util.Enums.LICENSE_STATUS.INACTIVE"),
//    ACTIVE(1, "common.util.Enums.LICENSE_STATUS.ACTIVE"),
//    NOT_PAID(2, "common.util.Enums.LICENSE_STATUS.NOT_PAID"),
//    EXPIRED_WITHIN_GRACE_PERIOD(3, "common.util.Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD"),
//    EXPIRED(4, "common.util.Enums.LICENSE_STATUS.EXPIRED");
//
//    protected int id;
//    protected String messageLiteralKey;
//
//    private Enums.LICENSE_STATUS(int id, String messageLiteralKey) {
//        this.id = id;
//        this.messageLiteralKey = messageLiteralKey;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public String getMessage(String langCode) {
//        return LocalizationManager.getLiteral(this.messageLiteralKey, langCode);
//    }
//
//    public static Enums.LICENSE_STATUS getLicenseStatusEnum(int id) {
//        for (Enums.LICENSE_STATUS l : Enums.LICENSE_STATUS.values()) {
//            if (id != l.getId()) continue;
//            return l;
//        }
//        return null;
//    }
//}
