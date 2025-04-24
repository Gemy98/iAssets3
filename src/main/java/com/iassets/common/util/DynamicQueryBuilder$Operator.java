///*
// * Decompiled with CFR 0.152.
// */
//package com.iassets.common.util;
//
//public static enum DynamicQueryBuilder.Operator {
//    LIKE("LIKE "),
//    IN("IN "),
//    BETWEEN("BETWEEN "),
//    NE("!= "),
//    GT("> "),
//    LT("< "),
//    GE(">= "),
//    LE("=> ");
//
//    private String op;
//
//    private DynamicQueryBuilder.Operator(String op) {
//        this.op = op;
//    }
//
//    public String getOp() {
//        return this.op;
//    }
//
//    public static String adjustLikeValue(String value, LikePattern pattern) {
//        switch (pattern) {
//            case PERCENT_AFTER: {
//                return value + "%";
//            }
//            case PERCENT_BEFORE: {
//                return "%" + value;
//            }
//            case PERCENT_AROUND: {
//                return "%" + value + "%";
//            }
//        }
//        return "";
//    }
//
//    public static enum OperType {
//        AND(" AND "),
//        OR(" OR ");
//
//        private String op;
//
//        private OperType(String op) {
//            this.op = op;
//        }
//
//        public String getOp() {
//            return this.op;
//        }
//    }
//
//    public static enum LikePattern {
//        PERCENT_BEFORE,
//        PERCENT_AFTER,
//        PERCENT_AROUND;
//
//    }
//}
