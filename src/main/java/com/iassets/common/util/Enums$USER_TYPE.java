///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//public static enum Enums.USER_TYPE {
//    HOSPITAL_DIRECTOR(1),
//    HOSPITAL_ASSISTANT_DIRECTOR(2),
//    HOSPITAL_DEPARTMENT_SUPERVISOR(5),
//    BIOMEDICAL_DEPARTMENT_HEAD(3),
//    BIOMEDICAL_DEPARTMENT_SUPERVISOR(4),
//    BIOMEDICAL_SITE_MANGER(6),
//    BIOMEDICAL_ENGINEER(7),
//    BIOMEDICAL_TECHNICIAN(8),
//    BIOMEDICAL_CHEMIST(16),
//    BIOMEDICAL_DIRECTORATE_ADMIN(9),
//    BIOMEDICAL_DIRECTORATE_VICE_ADMIN(10),
//    BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN(11),
//    BIOMEDICAL_COMPUTER_OPERATOR(13),
//    BIOMEDICAL_INVENTORY_KEEPER(14),
//    BIOMEDICAL_DRIVER(17),
//    GENERAL_DEPARTMENT_HEAD(103),
//    GENERAL_DEPARTMENT_SUPERVISOR(104),
//    GENERAL_SITE_MANGER(106),
//    GENERAL_SPECIALIST_SITE_MANGER(105),
//    GENERAL_ENGINEER(107),
//    GENERAL_TECHNICIAN(108),
//    GENERAL_DIRECTORATE_ADMIN(109),
//    GENERAL_DIRECTORATE_VICE_ADMIN(110),
//    GENERAL_DIRECTORATE_ASSISTANT_ADMIN(111),
//    GENERAL_INVENTORY_KEEPER(114),
//    DIRECTORATE_SUPER_ADMIN(1000),
//    DIRECTORATE_VICE_SUPER_ADMIN(1001),
//    BIO_REGIONAL_DIRECTOR(2000),
//    GEN_REGIONAL_DIRECTOR(2001);
//
//    protected int id;
//
//    private Enums.USER_TYPE(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
//    public static Enums.USER_TYPE getUserTypeById(int id) {
//        for (Enums.USER_TYPE ut : Enums.USER_TYPE.values()) {
//            if (id != ut.getId()) continue;
//            return ut;
//        }
//        return null;
//    }
//}
