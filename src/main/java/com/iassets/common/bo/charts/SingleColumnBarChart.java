/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.BarChart;
import com.iassets.common.bo.charts.SingleBarChartSegment;
import java.util.List;

public class SingleColumnBarChart
extends BarChart {
    public SingleColumnBarChart(String barSegmentLiteralKey) {
        super(new String[]{barSegmentLiteralKey});
    }

    public SingleColumnBarChart(String barSegmentLiteralKey, List<SingleBarChartSegment> segmentList) {
        super(new String[]{barSegmentLiteralKey}, segmentList);
    }

    public void addBar(SingleBarChartSegment singleBarChartSegment) {
        if (singleBarChartSegment == null) {
            throw new NullPointerException("MultiBarChartSegment is null");
        }
        if (singleBarChartSegment.getValues().size() != this.getBarSegmentLiteralKeyArray().length) {
            throw new IllegalStateException("barChartSegment length differ than barSegmentLiteralKeyArray");
        }
        this.getSegmentList().add(singleBarChartSegment);
    }

    public void addBar(String barTitleLiteralKey, Integer value) {
        this.addBar(new SingleBarChartSegment(barTitleLiteralKey, value));
    }
}
