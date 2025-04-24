/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import java.util.ArrayList;
import java.util.List;

public abstract class BarChartSegment {
    private String barTitleLiteralKey;
    private List<Integer> values;

    public BarChartSegment() {
        this.values = new ArrayList<Integer>();
    }

    public BarChartSegment(String barTitleLiteralKey) {
        this.values = new ArrayList<Integer>();
        this.barTitleLiteralKey = barTitleLiteralKey;
    }

    public BarChartSegment(String barTitleLiteralKey, List<Integer> values) {
        this.values = values;
        this.barTitleLiteralKey = barTitleLiteralKey;
    }

    public String getBarTitleLiteralKey() {
        return this.barTitleLiteralKey;
    }

    public void setBarTitleLiteralKey(String barTitleLiteralKey) {
        this.barTitleLiteralKey = barTitleLiteralKey;
    }

    public List<Integer> getValues() {
        return this.values;
    }

    public int getValueSize() {
        return this.values.size();
    }
}
