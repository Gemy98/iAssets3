/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.biomedical.util;

import com.iassets.biomedical.dto.report.DashboardItem;
import com.iassets.biomedical.dto.report.DashboardPerformance;
import com.iassets.biomedical.entity.BioDashboardMaintenancePerformanceMeasures;
import java.util.List;

public class DashboardMapper {
    private static DashboardItem getGroupOne(BioDashboardMaintenancePerformanceMeasures performance, String siteLiteral) {
        DashboardItem item = new DashboardItem();
        item.setTotalValue(performance.getSumTotalOpenedJoNo() - performance.getSumCancelledJoNo());
        item.setComplementaryValue(performance.getSumClosedJoNo());
        int ratio = item.getComplementaryValue() / item.getTotalValue();
        item.setIndicatorRatio(ratio);
        item.setSiteName(siteLiteral);
        return item;
    }

    private static DashboardItem getGroupTwo(BioDashboardMaintenancePerformanceMeasures performance, String siteLiteral) {
        DashboardItem item = new DashboardItem();
        item.setTotalValue(performance.getPeriodLength());
        item.setComplementaryValue(performance.getSumClosedJoNo());
        int ratio = performance.getSumClosedJoNo() / (performance.getSumTotalOpenedJoNo() - performance.getSumCancelledJoNo());
        item.setIndicatorRatio(ratio);
        item.setSiteName(siteLiteral);
        return item;
    }

    private static DashboardItem getGroupThree(BioDashboardMaintenancePerformanceMeasures performance, String siteLiteral) {
        DashboardItem item = new DashboardItem();
        item.setTotalValue(performance.getPeriodLength());
        item.setComplementaryValue(performance.getSumClassA_ClosedJoNo());
        int ratio = (performance.getWorkingClassA_deviceNo() - performance.getWorkingClassA_deviceNo()) / (performance.getWorkingClassA_deviceNo() + performance.getSumUndermaintenanceClassA_deviceNo());
        item.setIndicatorRatio(ratio);
        item.setSiteName(siteLiteral);
        return item;
    }

    private static DashboardItem getGroupFour(BioDashboardMaintenancePerformanceMeasures performance, String siteLiteral) {
        DashboardItem item = new DashboardItem();
        item.setTotalValue(performance.getPeriodLength());
        item.setComplementaryValue(performance.getSumClassB_ClosedJoNo());
        int ratio = performance.getSumClosedJoNo() / (performance.getWorkingClassA_deviceNo() + performance.getSumUndermaintenanceClassA_deviceNo());
        item.setIndicatorRatio(ratio);
        item.setSiteName(siteLiteral);
        return item;
    }

    private static DashboardItem getGroupFive(BioDashboardMaintenancePerformanceMeasures performance, String siteLiteral) {
        DashboardItem item = new DashboardItem();
        item.setTotalValue(performance.getPeriodLength());
        item.setComplementaryValue(performance.getSumClosedJoNo());
        int ratio = performance.getSumClosedJoNo() / (performance.getSumTotalOpenedJoNo() - performance.getSumCancelledJoNo());
        item.setIndicatorRatio(ratio);
        item.setSiteName(siteLiteral);
        return item;
    }

    public static DashboardPerformance getDashboardPerformance(List<BioDashboardMaintenancePerformanceMeasures> performances) {
        DashboardPerformance dashboardPerformance = new DashboardPerformance();
        DashboardItem groupOne = null;
        DashboardItem groupTwo = null;
        DashboardItem groupThree = null;
        DashboardItem groupFour = null;
        DashboardItem groupFive = null;
        int i = 1;
        for (BioDashboardMaintenancePerformanceMeasures performance : performances) {
            groupOne = DashboardMapper.getGroupOne(performance, "one" + i);
            groupTwo = DashboardMapper.getGroupTwo(performance, "Two" + i);
            groupThree = DashboardMapper.getGroupThree(performance, "Three" + i);
            groupFour = DashboardMapper.getGroupFour(performance, "Four" + i);
            groupFive = DashboardMapper.getGroupFive(performance, "Five" + i);
            ++i;
            dashboardPerformance.addGroupOne(groupOne);
            dashboardPerformance.addGroupTwo(groupTwo);
            dashboardPerformance.addGroupThree(groupThree);
            dashboardPerformance.addGroupFour(groupFour);
            dashboardPerformance.addGroupFive(groupFive);
        }
        return dashboardPerformance;
    }
}
