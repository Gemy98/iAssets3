/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.general.bo;

import com.iassets.common.util.Common;
import com.iassets.general.bo.EvaluationTemplateItem;
import com.iassets.general.entity.GenLookupEvaluationGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluationTemplate {
    private GenLookupEvaluationGroup group;
    private List<EvaluationTemplateItem> itemsList;
    private Date startDate;
    private Date endDate;
    private int month;
    private int year;
    private float totalDedicatedValue;
    private String totalDedicatedValueStr;

    public EvaluationTemplate() {
    }

    public EvaluationTemplate(GenLookupEvaluationGroup group, Date startDate, Date endDate, int month, int year) {
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
        this.month = month;
        this.year = year;
    }

    public GenLookupEvaluationGroup getGroup() {
        return this.group;
    }

    public void setGroup(GenLookupEvaluationGroup group) {
        this.group = group;
    }

    public List<EvaluationTemplateItem> getItemsList() {
        return this.itemsList;
    }

    public void setItemsList(List<EvaluationTemplateItem> itemsList) {
        this.itemsList = itemsList;
        if (itemsList != null && !itemsList.isEmpty()) {
            for (EvaluationTemplateItem item : itemsList) {
                this.totalDedicatedValue += item.getCurrentDedicatedValue();
            }
            this.totalDedicatedValueStr = Common.formatMoneyValue(Float.valueOf(this.totalDedicatedValue));
        }
    }

    public void addItemsList(EvaluationTemplateItem item) {
        if (this.itemsList == null) {
            this.itemsList = new ArrayList<EvaluationTemplateItem>();
        }
        this.itemsList.add(item);
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

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getTotalDedicatedValue() {
        return this.totalDedicatedValue;
    }

    public void setTotalDedicatedValue(float totalDedicatedValue) {
        this.totalDedicatedValue = totalDedicatedValue;
    }

    public String getTotalDedicatedValueStr() {
        return this.totalDedicatedValueStr;
    }

    public void setTotalDedicatedValueStr(String totalDedicatedValueStr) {
        this.totalDedicatedValueStr = totalDedicatedValueStr;
    }
}
