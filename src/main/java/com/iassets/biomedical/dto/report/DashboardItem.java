/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.biomedical.dto.report;

public class DashboardItem {
    private int complementaryValue;
    private int measureValue;
    private int totalValue;
    private String siteName;
    private int indicatorRatio;
    private String indicatorPercentageStr;

    public DashboardItem() {
    }

    public DashboardItem(int complementaryValue, int measureValue, String siteName) {
        this.complementaryValue = complementaryValue;
        this.measureValue = measureValue;
        this.siteName = siteName;
        this.totalValue = this.measureValue + this.complementaryValue;
        this.indicatorRatio = this.totalValue == 0 ? 100 : Math.round(this.measureValue * 100 / this.totalValue);
        this.indicatorPercentageStr = this.indicatorRatio + " %";
    }

    public DashboardItem(String siteName, int complementaryValue, int totalValue) {
        this.complementaryValue = complementaryValue;
        this.totalValue = totalValue;
        this.siteName = siteName;
        this.measureValue = this.totalValue - this.complementaryValue;
        this.indicatorRatio = totalValue == 0 ? 100 : Math.round(this.measureValue * 100 / this.totalValue);
        this.indicatorPercentageStr = this.indicatorRatio + " %";
    }

    public int getComplementaryValue() {
        return this.complementaryValue;
    }

    public void setComplementaryValue(int complementaryValue) {
        this.complementaryValue = complementaryValue;
    }

    public int getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String hospitalName) {
        this.siteName = hospitalName;
    }

    public float getIndicatorRatio() {
        return this.indicatorRatio;
    }

    public void setIndicatorRatio(int indicatorRatio) {
        this.indicatorRatio = indicatorRatio;
    }

    public int getMeasureValue() {
        return this.measureValue;
    }

    public void setMeasureValue(int measureValue) {
        this.measureValue = measureValue;
    }

    public String getIndicatorPercentageStr() {
        return this.indicatorPercentageStr;
    }

    public void setIndicatorPercentageStr(String indicatorPercentageStr) {
        this.indicatorPercentageStr = indicatorPercentageStr;
    }
}
