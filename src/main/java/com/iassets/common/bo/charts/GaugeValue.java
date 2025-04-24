/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

public class GaugeValue {
    public int value;
    public String titleLiteralKey;

    public GaugeValue() {
    }

    public GaugeValue(int value, String titleLiteralKey) {
        this.value = value;
        this.titleLiteralKey = titleLiteralKey;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitleLiteralKey() {
        return this.titleLiteralKey;
    }

    public void setTitleLiteralKey(String titleLiteralKey) {
        this.titleLiteralKey = titleLiteralKey;
    }
}
