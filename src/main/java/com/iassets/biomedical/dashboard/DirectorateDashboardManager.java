/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 */
package com.iassets.biomedical.dashboard;

import com.iassets.biomedical.DB.BioDashboardDBQueryManager;
import com.iassets.biomedical.entity.BioDashboardContractorEvalMeasures;
import com.iassets.biomedical.entity.BioDashboardMaintenancePerformanceMeasures;
import java.util.List;
import org.springframework.context.ApplicationContext;

public class DirectorateDashboardManager {
    private ApplicationContext applicationContext;
    private Integer directorateId;
    private Integer month;
    private Integer year;
    private List<BioDashboardContractorEvalMeasures> bioDashboardContractorEvalMeasuresList;
    private List<BioDashboardMaintenancePerformanceMeasures> bioDashboardMaintenancePerformanceMeasuresList;

    public DirectorateDashboardManager(ApplicationContext applicationContext, Integer directorateId, Integer month, Integer year) {
        this.applicationContext = applicationContext;
        this.directorateId = directorateId;
        this.month = month;
        this.year = year;
        BioDashboardDBQueryManager bioDashboardDBQueryManager = (BioDashboardDBQueryManager)this.applicationContext.getBean(BioDashboardDBQueryManager.class);
        this.bioDashboardContractorEvalMeasuresList = bioDashboardDBQueryManager.getBioDashboardContractorEvalMeasuresByDirectorate(directorateId, month, year);
    }
}
