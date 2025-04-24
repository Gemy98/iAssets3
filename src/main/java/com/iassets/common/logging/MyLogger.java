/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.logging;

import com.iassets.common.logging.BasicLogger;

public class MyLogger
extends BasicLogger {
    @Override
    public void log(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
