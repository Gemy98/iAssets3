/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

public abstract class Chart {
    private String titleLiteralKey;

    public Chart() {
    }

    public Chart(String titleLiteralKey) {
        this.titleLiteralKey = titleLiteralKey;
    }

    public String getTitleLiteralKey() {
        return this.titleLiteralKey;
    }

    public void setTitleLiteralKey(String titleLiteralKey) {
        this.titleLiteralKey = titleLiteralKey;
    }
}
