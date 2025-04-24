/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.AbstractMultiValueBarChart;
import com.iassets.common.bo.charts.MultiBarChartSegment;
import java.util.List;

public class MultiColumnBarChart
extends AbstractMultiValueBarChart {
    public MultiColumnBarChart(String[] segmentLiteralKeyArray) {
        super(segmentLiteralKeyArray);
    }

    public MultiColumnBarChart(String[] barSegmentLiteralKey, List<MultiBarChartSegment> segmentList) {
        super(barSegmentLiteralKey, segmentList);
    }
}
