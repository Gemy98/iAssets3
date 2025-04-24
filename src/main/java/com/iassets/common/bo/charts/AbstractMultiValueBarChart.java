/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.BarChart;
import com.iassets.common.bo.charts.MultiBarChartSegment;
import java.util.List;

public abstract class AbstractMultiValueBarChart
extends BarChart {
    public AbstractMultiValueBarChart(String[] barSegmentLiteralKeyArray) {
        super(barSegmentLiteralKeyArray);
    }

    public AbstractMultiValueBarChart(String[] barSegmentLiteralKeyArrary, List<MultiBarChartSegment> segmentList) {
        super(barSegmentLiteralKeyArrary, segmentList);
    }

    public void addBar(MultiBarChartSegment multiBarChartSegment) {
        if (multiBarChartSegment == null) {
            throw new NullPointerException("MultiBarChartSegment is null");
        }
        if (multiBarChartSegment.getValues().size() != this.getBarSegmentLiteralKeyArray().length) {
            throw new IllegalStateException("segmentList length differ than barSegmentLiteralKeyArray");
        }
        this.getSegmentList().add(multiBarChartSegment);
    }

    public void addBar(String barTitleLiteralKey, List<Integer> values) {
        this.addBar(new MultiBarChartSegment(barTitleLiteralKey, values));
    }
}
