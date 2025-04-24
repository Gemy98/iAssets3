///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//public static enum Enums.USER_ALLOWED_APP_TYPE {
//    BIOMEDICAL_MAINTENANCE_APP(1, "bio"),
//    GENERAL_MAINTENANCE_APP(2, "gen"),
//    BOTH_MAINTENANCE_APP(3, "undetermined");
//
//    int id;
//    String appDir;
//
//    private Enums.USER_ALLOWED_APP_TYPE(int id, String appDir) {
//        this.id = id;
//        this.appDir = appDir;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public String getAppDirectory() {
//        return this.appDir;
//    }
//
//    public static Enums.USER_ALLOWED_APP_TYPE getAppTypeEnum(int appTypeId) {
//        for (Enums.USER_ALLOWED_APP_TYPE t : Enums.USER_ALLOWED_APP_TYPE.values()) {
//            if (appTypeId != t.getId()) continue;
//            return t;
//        }
//        return null;
//    }
//}
