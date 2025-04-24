/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.biomedical.dto.report;

import com.iassets.biomedical.dto.report.DashboardItem;
import java.util.ArrayList;
import java.util.List;

public class DashboardPerformance {
    private List<DashboardItem> groupOne;
    private List<DashboardItem> groupTwo;
    private List<DashboardItem> groupThree;
    private List<DashboardItem> groupFour;
    private List<DashboardItem> groupFive;

    public List<DashboardItem> getGroupOne() {
        return this.groupOne;
    }

    public void setGroupOne(List<DashboardItem> GroupOne) {
        this.groupOne = GroupOne;
    }

    public List<DashboardItem> getGroupTwo() {
        return this.groupTwo;
    }

    public void setGroupTwo(List<DashboardItem> GroupTwo) {
        this.groupTwo = GroupTwo;
    }

    public List<DashboardItem> getGroupThree() {
        return this.groupThree;
    }

    public void setGroupThree(List<DashboardItem> GroupThree) {
        this.groupThree = GroupThree;
    }

    public List<DashboardItem> getGroupFour() {
        return this.groupFour;
    }

    public void setGroupFour(List<DashboardItem> GroupFour) {
        this.groupFour = GroupFour;
    }

    public List<DashboardItem> getGroupFive() {
        return this.groupFive;
    }

    public void setGroupFive(List<DashboardItem> Groupive) {
        this.groupFive = Groupive;
    }

    public void addGroupOne(DashboardItem item) {
        if (this.groupOne == null) {
            this.groupOne = new ArrayList<DashboardItem>();
        }
        this.groupOne.add(item);
    }

    public void addGroupTwo(DashboardItem item) {
        if (this.groupTwo == null) {
            this.groupTwo = new ArrayList<DashboardItem>();
        }
        this.groupTwo.add(item);
    }

    public void addGroupThree(DashboardItem item) {
        if (this.groupThree == null) {
            this.groupThree = new ArrayList<DashboardItem>();
        }
        this.groupThree.add(item);
    }

    public void addGroupFour(DashboardItem item) {
        if (this.groupFour == null) {
            this.groupFour = new ArrayList<DashboardItem>();
        }
        this.groupFour.add(item);
    }

    public void addGroupFive(DashboardItem item) {
        if (this.groupFive == null) {
            this.groupFive = new ArrayList<DashboardItem>();
        }
        this.groupFive.add(item);
    }
}
