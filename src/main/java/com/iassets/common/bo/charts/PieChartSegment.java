/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

public class PieChartSegment {
    private Integer value;
    private String segmentTitleLiteralKey;

    public PieChartSegment() {
    }

    public PieChartSegment(String segmentTitleLiteralKey) {
        this.segmentTitleLiteralKey = segmentTitleLiteralKey;
    }

    public PieChartSegment(String segmentTitleLiteralKey, Integer value) {
        this.value = value;
        this.segmentTitleLiteralKey = segmentTitleLiteralKey;
    }

    public String getSegmentTitleLiteralKey() {
        return this.segmentTitleLiteralKey;
    }

    public void setSegmentTitleLiteralKey(String segmentTitleLiteralKey) {
        this.segmentTitleLiteralKey = segmentTitleLiteralKey;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
