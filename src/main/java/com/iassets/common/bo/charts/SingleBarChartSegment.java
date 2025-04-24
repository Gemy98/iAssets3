/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.BarChartSegment;

public class SingleBarChartSegment
extends BarChartSegment {
    public SingleBarChartSegment() {
    }

    public SingleBarChartSegment(String barTitleLiteralKey) {
        super(barTitleLiteralKey);
    }

    public SingleBarChartSegment(String barTitleLiteralKey, Integer value) {
        super(barTitleLiteralKey);
        this.getValues().add(value);
    }

    public void addValue(Integer value) {
        this.getValues().add(0, value);
    }

    public void removeValue() {
        this.getValues().remove(0);
    }
}
