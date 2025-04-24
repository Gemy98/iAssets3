package com.iassets.biomedical.DB;

import com.iassets.biomedical.dto.report.DashboardItem;
import com.iassets.biomedical.entity.BioDashboardContractorEvalMeasures;
import com.iassets.biomedical.entity.BioDashboardMaintenancePerformanceMeasures;
import com.iassets.common.DB.CommonDBQueryManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="bioDashboardDBQueryManager")
@Transactional
public class BioDashboardDBQueryManager
extends CommonDBQueryManager {
    public Long getPPMMosta5lsMeassureValue(Integer siteId, Integer month, Integer year) {
        StringBuilder jpql = new StringBuilder("SELECT COUNT(p.id) FROM BioPPMProgressBill p");
        jpql.append(" WHERE p.progressBill.site.id = :siteId");
        jpql.append(" AND MONTH(p.from) <= :month");
        jpql.append(" AND YEAR(p.from) <= :year");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("month", month);
        params.put("year", year);
        return this.executeRetriveSingleResultJPQLWithNamedParameters(jpql.toString(), params, Long.class);
    }

    public Long[] getE3tmadEmployeeMessureValue(Integer siteId, Integer month, Integer year) {
        StringBuilder jpql = new StringBuilder("SELECT SUM(d.totalMo3tamadEmpNo), SUM(d.totalContractEmpNo) FROM BioDailyContractorEvaluation d");
        jpql.append(" WHERE d.site.id = :siteId");
        jpql.append(" AND d.evalMonth = :month");
        jpql.append(" AND d.evalYear = :year");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("month", month);
        params.put("year", year);
        return this.executeRetriveSingleResultJPQLWithNamedParameters(jpql.toString(), params, Long[].class);
    }

    public BioDashboardContractorEvalMeasures getBioDashboardContractorEvalMeasuresBySite(Integer siteId, Integer month, Integer year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("month", month);
        params.put("year", year);
        List<BioDashboardContractorEvalMeasures> list = this.executeNamedQuery("BioDashboardContractorEvalMeasures.getDailyContractorEvaluationViewBySiteAndMonthAndYear", params, BioDashboardContractorEvalMeasures.class);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public List<BioDashboardContractorEvalMeasures> getBioDashboardContractorEvalMeasuresByDirectorate(Integer directorateId, Integer month, Integer year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("directorateId", directorateId);
        params.put("month", month);
        params.put("year", year);
        List<BioDashboardContractorEvalMeasures> list = this.executeNamedQuery("BioDashboardContractorEvalMeasures.getDailyContractorEvaluationViewByDirectorateAndMonthAndYear", params, BioDashboardContractorEvalMeasures.class);
        return list;
    }

    public BioDashboardMaintenancePerformanceMeasures getBioDashboardMaintenancePerformanceMeasuresBySite(Integer siteId, Integer month, Integer year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("month", month);
        params.put("year", year);
        List<BioDashboardMaintenancePerformanceMeasures> list = this.executeNamedQuery("BioDashboardMaintenancePerformanceMeasures.getDashboardMaintenancePerformanceBySiteAndMonthAndYear", params, BioDashboardMaintenancePerformanceMeasures.class);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public List<BioDashboardMaintenancePerformanceMeasures> getBioDashboardMaintenancePerformanceListByDirectorate(Integer directorateId, Integer month, Integer year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("directorateId", directorateId);
        params.put("month", month);
        params.put("year", year);
        List<BioDashboardMaintenancePerformanceMeasures> list = this.executeNamedQuery("BioDashboardMaintenancePerformanceMeasures.getDashboardMaintenancePerformanceByDirectorateAndMonthAndYear", params, BioDashboardMaintenancePerformanceMeasures.class);
        return list;
    }

    public List<DashboardItem> getBioDashboardForGroupOneByDirectorate(Integer directorateId, Integer year, Integer month) {
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        ArrayList<DashboardItem> dashboardItemList = new ArrayList<DashboardItem>();
        params.put(1, year);
        params.put(2, month);
        params.put(3, directorateId);
        List searchResult = this.executeNativeQueryForSearch("SELECT name_literal_key ,get_JO_measure_value(id,1,?1,?2) as measureValue, get_JO_measure_value(id,0,?1,?2) as complementary FROM common_site s where directorate_id=?3 and status = 1", params);
        DashboardItem item = null;
        Object[] r = null;
        if (searchResult != null && !searchResult.isEmpty()) {
            for (int i = 0; i < searchResult.size(); ++i) {
                r = (Object[])searchResult.get(i);
                String siteName = r[0] == null ? "" : (String)r[0];
                int measureValue = r[1] == null ? 0 : (Integer)r[1];
                int complementaryValue = r[2] == null ? 0 : (Integer)r[2];
                item = new DashboardItem(complementaryValue, measureValue, siteName);
                dashboardItemList.add(item);
            }
        }
        return dashboardItemList;
    }

    public List<DashboardItem> getBioDashboardForGroup3AND4ByDirectorate(Integer directorateId, Integer catId) {
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        ArrayList<DashboardItem> dashboardItemList = new ArrayList<DashboardItem>();
        params.put(1, catId);
        params.put(2, directorateId);
        List searchResult = this.executeNativeQueryForSearch("SELECT name_literal_key ,get_class_measure_value(id,1,?1) as measureValue, get_class_measure_value(id,0,?1) as complementary FROM common_site s where directorate_id=?2 and status = 1", params);
        DashboardItem item = null;
        Object[] r = null;
        if (searchResult != null && !searchResult.isEmpty()) {
            for (int i = 0; i < searchResult.size(); ++i) {
                r = (Object[])searchResult.get(i);
                String siteName = r[0] == null ? "" : (String)r[0];
                int measureValue = r[1] == null ? 0 : (Integer)r[1];
                int complementaryValue = r[2] == null ? 0 : (Integer)r[2];
                item = new DashboardItem(complementaryValue, measureValue, siteName);
                dashboardItemList.add(item);
            }
        }
        return dashboardItemList;
    }

    public List<DashboardItem> getBioDashboardForGroupTwoByDirectorate(Integer directorateId, Integer year, Integer month) {
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        ArrayList<DashboardItem> dashboardItemList = new ArrayList<DashboardItem>();
        params.put(1, month);
        params.put(2, year);
        params.put(3, directorateId);
        List searchResult = this.executeNativeQueryForSearch("SELECT name_literal_key ,get_ppm_total(id,?1) as total, get_ppm_complementary(id,?2,?1) as complementary FROM common_site s where directorate_id=?3 and status = 1", params);
        DashboardItem item = null;
        Object[] r = null;
        if (searchResult != null && !searchResult.isEmpty()) {
            for (int i = 0; i < searchResult.size(); ++i) {
                r = (Object[])searchResult.get(i);
                String siteName = r[0] == null ? "" : (String)r[0];
                int totalValue = r[1] == null ? 0 : (Integer)r[1];
                int complementaryValue = r[2] == null ? 0 : (Integer)r[2];
                item = new DashboardItem(siteName, complementaryValue, totalValue);
                dashboardItemList.add(item);
            }
        }
        return dashboardItemList;
    }
}
