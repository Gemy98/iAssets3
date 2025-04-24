/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.BarChartSegment;
import com.iassets.common.bo.charts.Chart;
import java.util.ArrayList;
import java.util.List;

public class BarChart
extends Chart {
    private String barSegmentTitleLiteralKey;
    private final String[] barSegmentLiteralKeyArray;
    private List<BarChartSegment> segmentsList;

    public BarChart(String[] barSegmentLiteralKeyArray) {
        this.barSegmentLiteralKeyArray = barSegmentLiteralKeyArray;
        this.segmentsList = new ArrayList<BarChartSegment>();
    }

    public BarChart(String[] barSegmentLiteralKeyArray, List<? extends BarChartSegment> segmentsList) {
        this.barSegmentLiteralKeyArray = barSegmentLiteralKeyArray;
        this.segmentsList = new ArrayList<BarChartSegment>();
        for (BarChartSegment barChartSegment : segmentsList) {
            if (barChartSegment.getValueSize() != barSegmentLiteralKeyArray.length) {
                throw new IllegalStateException("segmentsList length differ than barSegmentLiteralKeyArray");
            }
            this.segmentsList.add(barChartSegment);
        }
    }

    public String[] getBarSegmentLiteralKeyArray() {
        return this.barSegmentLiteralKeyArray;
    }

    public List<BarChartSegment> getSegmentList() {
        return this.segmentsList;
    }

    public void setSegmentList(List<BarChartSegment> segmentsList) {
        if (this.barSegmentLiteralKeyArray == null) {
            throw new NullPointerException("barSegmentLiteralKeyArray is null");
        }
        for (BarChartSegment barChartSegment : segmentsList) {
            if (barChartSegment.getValueSize() == this.barSegmentLiteralKeyArray.length) continue;
            throw new IllegalStateException("segmentsList length differ than barSegmentLiteralKeyArray");
        }
        this.segmentsList = segmentsList;
    }

    public String getBarSegmentTitleLiteralKey() {
        return this.barSegmentTitleLiteralKey;
    }

    public void setBarSegmentTitleLiteralKey(String barSegmentTitleLiteralKey) {
        this.barSegmentTitleLiteralKey = barSegmentTitleLiteralKey;
    }
}
