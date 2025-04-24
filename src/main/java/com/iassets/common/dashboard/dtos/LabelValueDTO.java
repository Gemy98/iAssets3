/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.dashboard.dtos;

public class LabelValueDTO {
    private String label;
    private String value;

    public LabelValueDTO() {
    }

    public LabelValueDTO(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
