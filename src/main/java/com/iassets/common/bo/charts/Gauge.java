/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo.charts;

import com.iassets.common.bo.charts.Chart;
import com.iassets.common.bo.charts.GaugeValue;

public class Gauge
extends Chart {
    public String totalLiteralKey;
    public GaugeValue measureValue;
    public GaugeValue complementaryMeasureValue;

    public Gauge(Integer total, String valueLiteralKey, GaugeValue complementingMeasureValue) {
        this.complementaryMeasureValue = complementingMeasureValue;
        Integer value = total - complementingMeasureValue.getValue();
        this.measureValue = new GaugeValue(value, valueLiteralKey);
    }

    public Gauge(GaugeValue measureValue, Integer total, String complementaryLiteralKey) {
        this.measureValue = measureValue;
        Integer complementary = total - measureValue.getValue();
        this.complementaryMeasureValue = new GaugeValue(complementary, complementaryLiteralKey);
    }

    public Gauge(GaugeValue measureValue, GaugeValue complementingMeasureValue) {
        this.measureValue = measureValue;
        this.complementaryMeasureValue = complementingMeasureValue;
    }

    public GaugeValue getComplementaryMeasureValue() {
        return this.complementaryMeasureValue;
    }

    public GaugeValue getMeasureValue() {
        return this.measureValue;
    }

    public String getTotalLiteralKey() {
        return this.totalLiteralKey;
    }

    public void setComplementaryMeasureValue(GaugeValue complementingMeasureValue) {
        this.complementaryMeasureValue = complementingMeasureValue;
    }

    public void setMeasureValue(GaugeValue measureValue) {
        this.measureValue = measureValue;
    }

    public void setTotalLiteralKey(String totalLiteralKey) {
        this.totalLiteralKey = totalLiteralKey;
    }
}
