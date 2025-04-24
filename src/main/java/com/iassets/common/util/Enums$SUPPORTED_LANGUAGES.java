///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public static enum Enums.SUPPORTED_LANGUAGES {
//    ARABIC("ar", "\u0639\u0631\u0628\u064a"),
//    ENGLISH("en", "english");
//
//    String code;
//    String displayName;
//
//    private Enums.SUPPORTED_LANGUAGES(String code, String displayName) {
//        this.code = code;
//        this.displayName = displayName;
//    }
//
//    public String getLanguageCode() {
//        return this.code;
//    }
//
//    public String getDisplayName() {
//        return this.displayName;
//    }
//
//    public static List<String> getSupportedLanguagesCodes() {
//        ArrayList<String> list = new ArrayList<String>();
//        for (Enums.SUPPORTED_LANGUAGES l : Enums.SUPPORTED_LANGUAGES.values()) {
//            list.add(l.getLanguageCode());
//        }
//        return list;
//    }
//
//    public static Enums.SUPPORTED_LANGUAGES getLanguageEnumByCode(String code) {
//        for (Enums.SUPPORTED_LANGUAGES l : Enums.SUPPORTED_LANGUAGES.values()) {
//            if (!code.equals(l.getLanguageCode())) continue;
//            return l;
//        }
//        return null;
//    }
//
//    public static String getDefaultLanguageCode() {
//        return ARABIC.getLanguageCode();
//    }
//}
