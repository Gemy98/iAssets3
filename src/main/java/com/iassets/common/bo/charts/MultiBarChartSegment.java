/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.BarChartSegment;
import java.util.List;

public class MultiBarChartSegment
extends BarChartSegment {
    public MultiBarChartSegment() {
    }

    public MultiBarChartSegment(String barTitleLiteralKey) {
        super(barTitleLiteralKey);
    }

    public MultiBarChartSegment(String barTitleLiteralKey, List<Integer> values) {
        super(barTitleLiteralKey, values);
    }

    public void addValue(Integer value) {
        this.getValues().add(value);
    }

    public void removeValue(Integer value) {
        this.getValues().remove(value);
    }
}
