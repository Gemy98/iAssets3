/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo;

public class PPMTableRecord {
    private String deviceName;
    private String category;
    private String subcontractor;
    private String pmVisitsMonths;
    private int contractVisitsNo;
    private int deviceNoInSite;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcontractor() {
        return this.subcontractor;
    }

    public void setSubcontractor(String subcontractor) {
        this.subcontractor = subcontractor;
    }

    public String getPmVisitsMonths() {
        return this.pmVisitsMonths;
    }

    public void setPmVisitsMonths(String pmVisitsMonths) {
        this.pmVisitsMonths = pmVisitsMonths;
    }

    public int getContractVisitsNo() {
        return this.contractVisitsNo;
    }

    public void setContractVisitsNo(int contractVisitsNo) {
        this.contractVisitsNo = contractVisitsNo;
    }

    public int getDeviceNoInSite() {
        return this.deviceNoInSite;
    }

    public void setDeviceNoInSite(int deviceNoInSite) {
        this.deviceNoInSite = deviceNoInSite;
    }
}
