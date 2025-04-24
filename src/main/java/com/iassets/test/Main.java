/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.test;

import com.iassets.common.util.DateUtil;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println(DateUtil.getDiffMonthsBetweenDates(new Date(), new Date(119, 10, 1)));
    }
}
