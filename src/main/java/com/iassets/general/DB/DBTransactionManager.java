/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.general.DB;

import com.iassets.common.DB.CommonDBTransactionManager;
import com.iassets.common.entity.CommonSite;
import com.iassets.general.bo.EvaluationTemplateItem;
import com.iassets.general.entity.GenLookupEvaluationGroupItem;
import com.iassets.general.entity.GenPeriodicSiteEvaluation;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.entity.GenSpInventoryTransaction;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="genDBTransactionManager")
@Transactional(rollbackFor={Exception.class})
public class DBTransactionManager
extends CommonDBTransactionManager {
    @Transactional(rollbackFor={Exception.class})
    public void save(GenSpInventoryContent c, GenSpInventoryTransaction t) throws Exception {
        this.persistEntity(c);
        this.persistEntity(t);
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean deleteEvaluationTemplateItems(int groupId, int siteId, int month, int year) {
        String query = "delete from gen_periodic_site_evaluation where site_id =? and month =? and year =?  and item_id in (select id from gen_lookup_evaluation_group_item where group_id =? )";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, siteId);
        params.put(2, month);
        params.put(3, year);
        params.put(4, groupId);
        int count = this.executeNativeQueryForUpdate(query, params);
        return count > 0;
    }

    @Transactional(rollbackFor={Exception.class})
    public void insert(List<EvaluationTemplateItem> items, int siteId, int month, int year, Date startDate, Date endDate) throws Exception {
        for (EvaluationTemplateItem item : items) {
            GenPeriodicSiteEvaluation evaluation = new GenPeriodicSiteEvaluation();
            evaluation.setSite(this.findById(siteId, CommonSite.class));
            evaluation.setEvaluationGroupItem(this.findById(item.getItemId(), GenLookupEvaluationGroupItem.class));
            evaluation.setMonth(month);
            evaluation.setYear(year);
            evaluation.setStartDate(startDate);
            evaluation.setEndDate(endDate);
            evaluation.setDedicatedValue(Float.valueOf(item.getCurrentDedicatedValue()));
            evaluation.setMaxDegree(item.getMaxDegree());
            evaluation.setEvalDegree(Float.valueOf(item.getEvalDegree()));
            evaluation.setEvalPercentage(Float.valueOf(item.getEvalPercentage()));
            evaluation.setPenaltyValue(Float.valueOf(item.getPenaltyValue()));
            evaluation.setPenaltyPercentage(Float.valueOf(item.getPenaltyPercentage()));
            this.persistEntity(evaluation);
        }
    }

    public boolean update(int siteId, int month, int year, Date startDate) {
        String query = "update gen_periodic_site_evaluation  set start_date = ? where site_id= ? and month =?  and year = ? ";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, startDate);
        params.put(2, siteId);
        params.put(3, month);
        params.put(4, year);
        int count = this.executeNativeQueryForUpdate(query, params);
        return count > 0;
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean deletePpmNotificationsSchedule(int deviceId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        int count = this.executeUpdateNamedQuery("GenHospitalDeviceInternalPpmNotificationSchedule.deleteDeviceInternalPPMSchedule", params);
        return count > 0;
    }
}
