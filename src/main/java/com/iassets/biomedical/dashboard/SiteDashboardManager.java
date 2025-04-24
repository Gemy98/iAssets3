/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 */
package com.iassets.biomedical.dashboard;

import com.iassets.biomedical.DB.BioDashboardDBQueryManager;
import com.iassets.biomedical.DB.DBQueryManager;
import com.iassets.biomedical.entity.BioDashboardContractorEvalMeasures;
import com.iassets.biomedical.entity.BioDashboardMaintenancePerformanceMeasures;
import com.iassets.biomedical.entity.BioMonthlyContractorEvaluation;
import com.iassets.common.bo.charts.Gauge;
import com.iassets.common.bo.charts.GaugeValue;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.DateUtil;
import java.util.Date;
import org.springframework.context.ApplicationContext;

public class SiteDashboardManager {
    private ApplicationContext applicationContext;
    private Integer directorateId;
    private CommonSite site;
    private Integer month;
    private Integer year;
    private DBQueryManager dbQueryManager;
    private BioDashboardDBQueryManager bioDashboardDBQueryManager;
    private BioDashboardContractorEvalMeasures bioDashboardContractorEvalMeasures;
    private BioDashboardMaintenancePerformanceMeasures bioDashboardMaintenancePerformanceMeasures;
    private BioMonthlyContractorEvaluation bioMonthlyContractorEvaluation;

    public SiteDashboardManager(ApplicationContext applicationContext, Integer directorateId, CommonSite site, Integer month, Integer year) {
        this.applicationContext = applicationContext;
        this.directorateId = directorateId;
        this.site = site;
        this.month = month;
        this.year = year;
        this.bioDashboardDBQueryManager = (BioDashboardDBQueryManager)this.applicationContext.getBean(BioDashboardDBQueryManager.class);
        this.bioDashboardContractorEvalMeasures = this.bioDashboardDBQueryManager.getBioDashboardContractorEvalMeasuresBySite(site.getId(), month, year);
        this.bioDashboardMaintenancePerformanceMeasures = this.bioDashboardDBQueryManager.getBioDashboardMaintenancePerformanceMeasuresBySite(site.getId(), month, year);
        this.dbQueryManager = (DBQueryManager)this.applicationContext.getBean("bioDBQueryManager", DBQueryManager.class);
        this.bioMonthlyContractorEvaluation = this.dbQueryManager.getBioMonthlyContractorEvaluation(site.getId(), month, year);
    }

    public Gauge getPPMMosta5lsGauge() {
        Long value = this.bioDashboardDBQueryManager.getPPMMosta5lsMeassureValue(this.site.getId(), this.month, this.year);
        Date date = new Date(this.year - 1900, this.month - 1, 1);
        Date today = new Date();
        Integer contractNumOfMonths = DateUtil.getDiffMonthsBetweenDates(this.site.getBioContractStartDate(), date);
        Integer complementary = (int)((long)contractNumOfMonths.intValue() - value);
        if (date.getMonth() == today.getMonth() && date.getYear() == today.getYear()) {
            Integer n = complementary;
            Integer n2 = complementary = Integer.valueOf(complementary - 1);
        }
        GaugeValue gaugeValue = new GaugeValue(value.intValue(), "servlet.charts.group2.chart1.MeasureValueTitle");
        GaugeValue gaugecomplementary = new GaugeValue(complementary, "servlet.charts.group2.chart1.ComplementingMeasureValueTitle");
        Gauge gauge = new Gauge(gaugeValue, gaugecomplementary);
        gauge.setTitleLiteralKey("servlet.charts.group2.chart1.title");
        gauge.setTotalLiteralKey("servlet.charts.group2.chart1.totalTitle");
        return gauge;
    }

    public Gauge getWorkerE3tmadGauge() {
        Integer value = this.bioDashboardContractorEvalMeasures.getSumTotalMo3tamadEmpNo();
        Integer total = this.bioDashboardContractorEvalMeasures.getSumTotalWorkingEmpNo();
        GaugeValue gaugeValue = new GaugeValue(value, "servlet.charts.group2.chart2.MeasureValueTitle");
        Gauge gauge = new Gauge(gaugeValue, total, "servlet.charts.group2.chart2.ComplementingMeasureValueTitle");
        gauge.setTitleLiteralKey("servlet.charts.group2.chart2.title");
        gauge.setTotalLiteralKey("servlet.charts.group2.chart2.totalTitle");
        return gauge;
    }

    public Gauge getUniformComitmitGauge() {
        Integer sumUniformViolationEmpNo = this.bioDashboardContractorEvalMeasures.getSumUniformViolentEmpNo();
        Integer total = this.bioDashboardContractorEvalMeasures.getSumTotalWorkingEmpNo();
        Integer complementary = sumUniformViolationEmpNo;
        GaugeValue gaugecomplementary = new GaugeValue(complementary, "servlet.charts.group2.chart3.ComplementingMeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group2.chart3.MeasureValueTitle", gaugecomplementary);
        gauge.setTitleLiteralKey("servlet.charts.group2.chart3.title");
        gauge.setTotalLiteralKey("servlet.charts.group2.chart3.totalTitle");
        return gauge;
    }

    public Gauge getCompalainGauge() {
        Integer complains = this.bioDashboardContractorEvalMeasures.getSumSuppliersComplainsNo();
        Integer total = this.bioDashboardContractorEvalMeasures.getSumTotalSubcontractorNo();
        total = total + this.bioDashboardContractorEvalMeasures.getSumTotalAgentNo();
        total = total + this.bioDashboardContractorEvalMeasures.getSumTotalSubcontractorNo();
        Integer complementary = complains;
        GaugeValue gaugecomplementary = new GaugeValue(complementary, "servlet.charts.group2.chart4.ComplementingMeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group2.chart4.MeasureValueTitle", gaugecomplementary);
        gauge.setTitleLiteralKey("servlet.charts.group2.chart4.title");
        gauge.setTotalLiteralKey("servlet.charts.group2.chart4.totalTitle");
        return gauge;
    }

    public Gauge getWorkerOrderEfficiencyGauge() {
        Integer total = this.bioDashboardMaintenancePerformanceMeasures.getSumTotalOpenedJoNo() - this.bioDashboardMaintenancePerformanceMeasures.getSumCancelledJoNo();
        Integer value = this.bioDashboardMaintenancePerformanceMeasures.getSumClosedJoNo();
        GaugeValue measureValue = new GaugeValue(value, "servlet.charts.group1.chart1.MeasureValueTitle");
        Gauge gauge = new Gauge(measureValue, total, "servlet.charts.group1.chart1.ComplementingMeasureValueTitle");
        gauge.setTitleLiteralKey("servlet.charts.group1.chart1.title");
        gauge.setTotalLiteralKey("servlet.charts.group1.chart1.totalTitle");
        return gauge;
    }

    public Gauge getClassADevicesEfficiencyGauge() {
        Integer total = this.bioDashboardMaintenancePerformanceMeasures.getWorkingClassA_deviceNo();
        Integer complementary = this.bioDashboardMaintenancePerformanceMeasures.getSumUndermaintenanceClassA_deviceNo();
        GaugeValue gaugeComplementary = new GaugeValue(complementary, "servlet.charts.group1.chart3.ComplementingMeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group1.chart3.MeasureValueTitle", gaugeComplementary);
        gauge.setTitleLiteralKey("servlet.charts.group1.chart3.title");
        gauge.setTotalLiteralKey("servlet.charts.group1.chart3.totalTitle");
        return gauge;
    }

    public Gauge getClassBDevicesEfficiencyGauge() {
        Integer total = this.bioDashboardMaintenancePerformanceMeasures.getWorkingClassB_deviceNo();
        Integer complementary = this.bioDashboardMaintenancePerformanceMeasures.getSumUndermaintenanceClassB_deviceNo();
        GaugeValue gaugeComplementary = new GaugeValue(complementary, "servlet.charts.group1.chart4.ComplementingMeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group1.chart4.MeasureValueTitle", gaugeComplementary);
        gauge.setTitleLiteralKey("servlet.charts.group1.chart4.title");
        gauge.setTotalLiteralKey("servlet.charts.group1.chart4.totalTitle");
        return gauge;
    }

    public Gauge getPCEfficiencyGauge() {
        int total = this.bioDashboardContractorEvalMeasures.getPeriodLength();
        int complementary = this.bioDashboardContractorEvalMeasures.getSumPcSuspended();
        GaugeValue gaugeComplementary = new GaugeValue(complementary, "servlet.charts.group1.chart5.MeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group1.chart5.ComplementingMeasureValueTitle", gaugeComplementary);
        gauge.setTitleLiteralKey("servlet.charts.group1.chart5.title");
        gauge.setTotalLiteralKey("servlet.charts.group1.chart5.totalTitle");
        return gauge;
    }

    public Gauge getPPMVisitsEfficiencyGauge() {
        int total = this.bioDashboardMaintenancePerformanceMeasures.getSumClassABScheduledPPMVisitsNo() / this.bioDashboardMaintenancePerformanceMeasures.getPeriodLength();
        int complementary = this.bioMonthlyContractorEvaluation.getClassABNotHappenedPPMVisitsNo();
        GaugeValue gaugeComplementary = new GaugeValue(complementary, "servlet.charts.group1.chart2.MeasureValueTitle");
        Gauge gauge = new Gauge(total, "servlet.charts.group1.chart2.ComplementingMeasureValueTitle", gaugeComplementary);
        gauge.setTitleLiteralKey("servlet.charts.group1.chart2.title");
        gauge.setTotalLiteralKey("servlet.charts.group1.chart2.totalTitle");
        return gauge;
    }
}
