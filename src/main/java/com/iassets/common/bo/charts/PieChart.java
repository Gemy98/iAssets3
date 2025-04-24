/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.Chart;
import com.iassets.common.bo.charts.PieChartSegment;
import java.util.ArrayList;
import java.util.List;

public class PieChart
extends Chart {
    private String seriesLiteralKey;
    private List<PieChartSegment> segmentList;

    public PieChart(String seriesLiteralKey) {
        this.seriesLiteralKey = seriesLiteralKey;
        this.segmentList = new ArrayList<PieChartSegment>();
    }

    public void addSegment(String segmentTitleLiteralKey, Integer value) {
        this.segmentList.add(new PieChartSegment(segmentTitleLiteralKey, value));
    }

    public void addSegment(PieChartSegment pieChartSegment) {
        this.segmentList.add(pieChartSegment);
    }

    public String getSeriesLiteralKey() {
        return this.seriesLiteralKey;
    }

    public void setSeriesLiteralKey(String seriesLiteralKey) {
        this.seriesLiteralKey = seriesLiteralKey;
    }

    public List<PieChartSegment> getSegmentList() {
        return this.segmentList;
    }

    public void setSegmentList(List<PieChartSegment> segmentList) {
        this.segmentList = segmentList;
    }
}
