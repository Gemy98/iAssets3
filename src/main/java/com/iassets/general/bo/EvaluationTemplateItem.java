/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.general.bo;

import com.iassets.general.entity.GenLookupEvaluationGroupItem;
import java.util.Date;

public class EvaluationTemplateItem {
    private GenLookupEvaluationGroupItem item;
    private float itemDedicatedValue;
    private float evaluationDedicatedValue;
    private int maxDegree;
    private float evalDegree;
    private float evalPercentage;
    private float penaltyValue;
    private float penaltyPercentage;
    private int itemId;
    private Date startDate;
    private Date endDate;

    public GenLookupEvaluationGroupItem getItem() {
        return this.item;
    }

    public void setItem(GenLookupEvaluationGroupItem item) {
        this.item = item;
    }

    public int getMaxDegree() {
        return this.maxDegree;
    }

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public float getEvalDegree() {
        return this.evalDegree;
    }

    public void setEvalDegree(float evalDegree) {
        this.evalDegree = evalDegree;
    }

    public float getEvalPercentage() {
        return this.evalPercentage;
    }

    public void setEvalPercentage(float evalPercentage) {
        this.evalPercentage = evalPercentage;
    }

    public float getPenaltyValue() {
        return this.penaltyValue;
    }

    public void setPenaltyValue(float penaltyValue) {
        this.penaltyValue = penaltyValue;
    }

    public float getPenaltyPercentage() {
        return this.penaltyPercentage;
    }

    public void setPenaltyPercentage(float penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public float getCurrentDedicatedValue() {
        if (this.itemDedicatedValue == 0.0f) {
            return this.evaluationDedicatedValue;
        }
        return this.itemDedicatedValue;
    }

    public void setItemDedicatedValue(float itemDedicatedValue) {
        this.itemDedicatedValue = itemDedicatedValue;
    }

    public void setEvaluationDedicatedValue(float evaluationDedicatedValue) {
        this.evaluationDedicatedValue = evaluationDedicatedValue;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
