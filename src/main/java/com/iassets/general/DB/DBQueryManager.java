/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.general.DB;

import com.iassets.common.DB.CommonDBQueryManager;
import com.iassets.common.util.Enums;
import com.iassets.general.bo.EvaluationTemplateItem;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import com.iassets.general.entity.GenHospitalDevicePpmDetail;
import com.iassets.general.entity.GenHospitalDeviceScrappingInfo;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenLookupEvaluationGroup;
import com.iassets.general.entity.GenLookupEvaluationGroupItem;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.entity.GenPeriodicSiteEvaluation;
import com.iassets.general.entity.GenSpInventoryContent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="genDBQueryManager")
@Transactional
public class DBQueryManager
extends CommonDBQueryManager {
    public GenHospitalDevice getDevice(int siteId, int locationId, String code, String sn, Enums.DEVICE_STATUS status, boolean excludeScrapped, boolean excludeTransferred) {
        List<GenHospitalDevice> list;
        boolean snExists;
        boolean codeExists = code != null && !code.trim().isEmpty();
        boolean bl = snExists = sn != null && !sn.trim().isEmpty();
        if (siteId == 0 || !codeExists && !snExists) {
            return null;
        }
        String jpql = "SELECT DISTINCT h FROM GenHospitalDevice h WHERE h.site.id = " + siteId;
        if (status != null) {
            jpql = jpql + " AND h.status = " + status.getStatus();
        } else {
            if (excludeScrapped) {
                jpql = jpql + " AND h.status != " + Enums.DEVICE_STATUS.SCRAPPED.getStatus();
            }
            if (excludeTransferred) {
                jpql = jpql + " AND h.status != " + Enums.DEVICE_STATUS.TRANSFERRED.getStatus();
            }
        }
        int i = 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (codeExists) {
            jpql = jpql + " AND h.code = ? ";
            map.put(++i, code);
        }
        if (snExists) {
            jpql = jpql + " AND h.serialNo = ? ";
            map.put(++i, sn);
        }
        if ((list = this.executeRetriveJPQLWithPositionalParameters(jpql, map, GenHospitalDevice.class, "GenHospitalDevice.graph.fetchAll")) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public GenHospitalDeviceScrappingInfo getDeviceScrappingInfo(int deviceId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        List<GenHospitalDeviceScrappingInfo> resultList = this.executeNamedQuery("GenHospitalDeviceScrappingInfo.findByDevice", params, GenHospitalDeviceScrappingInfo.class);
        if (resultList != null && resultList.size() == 1) {
            return resultList.get(0);
        }
        return null;
    }

    public GenHospitalDevicePpmDetail getSpecificPPMDetails(int deviceId, int month, int year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("month", month);
        params.put("year", year);
        List<GenHospitalDevicePpmDetail> resultList = this.executeNamedQuery("GenHospitalDevicePpmDetail.getPPMDetails", params, GenHospitalDevicePpmDetail.class);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public GenHospitalDevice checkDeviceCodeOrSerialDuplication(int siteId, String code, String sn, int excludedDeviceId) {
        if ((code == null || code.isEmpty()) && (sn == null || sn.isEmpty())) {
            return null;
        }
        String jpql = "select h from GenHospitalDevice h where h.site.id = ? and (h.code = ? or h.serialNo = ?) and h.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, code);
        map.put(3, sn);
        map.put(4, excludedDeviceId);
        List<GenHospitalDevice> list = this.queryJPQL(jpql, map, GenHospitalDevice.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public GenJobOrder checkJobOrderNoDuplication(int siteId, String jobOrderNo) {
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.site.id = " + siteId + " AND j.jobOrderNo = ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, jobOrderNo);
        List<GenJobOrder> list = this.queryJPQL(jpql, map, GenJobOrder.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    private List<GenSpInventoryContent> getSpInventoryContent(int siteId, String code, boolean underThresholdOnly) {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        String sqlString = "SELECT sp_inventory_content_id, name, MIN(NULLIf(unit_price,0)), MAX(unit_price) FROM gen_sp_inventory_content sp_inventory_content INNER JOIN gen_sp_inventory_transaction sp_inventory_transaction ON sp_inventory_content.id = sp_inventory_transaction.sp_inventory_content_id WHERE sp_inventory_content.site_id = ? AND sp_inventory_transaction.input = true";
        map.put(1, siteId);
        if (code != null) {
            sqlString = sqlString + " AND sp_inventory_content.code = ? ";
            map.put(2, code);
        } else if (underThresholdOnly) {
            sqlString = sqlString + " AND sp_inventory_content.available_quantity < sp_inventory_content.threshold";
        }
        sqlString = sqlString + " GROUP BY sp_inventory_content_id ORDER BY name";
        ArrayList<GenSpInventoryContent> inventoryContents = null;
        GenSpInventoryContent spInventoryContent = null;
        List list = this.executeNativeQueryForSearch(sqlString, map);
        if (list != null && !list.isEmpty()) {
            inventoryContents = new ArrayList<GenSpInventoryContent>();
            for (int i = 0; i < list.size(); ++i) {
                Object[] r = (Object[])list.get(i);
                spInventoryContent = this.findById((Integer)r[0], GenSpInventoryContent.class);
                if (r[2] == null) {
                    spInventoryContent.setMinPrice(Float.valueOf(0.0f));
                } else {
                    spInventoryContent.setMinPrice(Float.valueOf(((Double)r[2]).floatValue()));
                }
                spInventoryContent.setMaxPrice((Float)r[3]);
                inventoryContents.add(spInventoryContent);
            }
        }
        return inventoryContents;
    }

    public GenSpInventoryContent getSpInventoryContentWithStats(int siteId, String code) {
        List<GenSpInventoryContent> list = this.getSpInventoryContent(siteId, code, false);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<GenSpInventoryContent> getSpInventoryContentsWithStats(int siteId, boolean underThresholdOnly) {
        return this.getSpInventoryContent(siteId, null, underThresholdOnly);
    }

    public GenSpInventoryContent checkSPCategoryDuplication(int siteId, String code, int excludedSPCatId) {
        if (siteId == 0 || code == null || code.isEmpty()) {
            return null;
        }
        String jpql = "select s from GenSpInventoryContent s where s.site.id = ? and s.code = ? and s.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, code);
        map.put(3, excludedSPCatId);
        List<GenSpInventoryContent> list = this.queryJPQL(jpql, map, GenSpInventoryContent.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public GenJobOrder inquireJobOrder(int siteId, int locationId, String jobOrderNo) {
        List<GenJobOrder> list;
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.site.id = " + siteId;
        int i = 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (jobOrderNo != null && !jobOrderNo.trim().isEmpty()) {
            jpql = jpql + " AND j.jobOrderNo = ?";
            map.put(++i, jobOrderNo);
        }
        if ((list = this.queryJPQL(jpql, map, GenJobOrder.class)) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<GenJobOrder> getJobOrders(int siteId, int locationId, GenLookupJobOrderCategory gmpDep, int month, int year, Enums.JOB_ORDER_STATUS status) {
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.site.id = " + siteId + " AND j.hospitalDevice.status NOT IN (" + Enums.DEVICE_STATUS.SCRAPPED.getStatus() + "," + Enums.DEVICE_STATUS.TRANSFERRED.getStatus() + ")";
        int i = 1;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (locationId != 0) {
            jpql = jpql + " AND (j.hospital.id = ? OR j.uncodedDevice = true)";
            map.put(i++, locationId);
        }
        if (gmpDep != null) {
            jpql = jpql + " AND j.category.id = ?";
            map.put(i++, gmpDep.getId());
        }
        if (status != null) {
            if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql = jpql + " AND j.closed = true";
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql = jpql + " AND j.cancelled = true";
            } else {
                jpql = jpql + " AND j.cancelled = false AND j.closed = false";
                if (status == Enums.JOB_ORDER_STATUS.LATE) {
                    jpql = jpql + " AND j.jobOrderPriority.id != " + Enums.JOB_ORDER_PRIORITY.NORMAL.getDBId() + " AND DATEDIFF(now(),j.jobOrderDate) >= 4 AND j.secondAction IS NULL AND j.secondActionDate IS NULL";
                }
            }
        }
        if (month != 0 && status != Enums.JOB_ORDER_STATUS.LATE) {
            if (status == null || status == Enums.JOB_ORDER_STATUS.OPENED) {
                jpql = jpql + " AND MONTH(j.jobOrderDate) = ?";
            } else if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql = jpql + " AND MONTH(j.actualCloseDate) = ?";
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql = jpql + " AND MONTH(j.actualCancelDate) = ?";
            }
            map.put(i++, month);
        }
        if (year != 0 && status != Enums.JOB_ORDER_STATUS.LATE) {
            if (status == null || status == Enums.JOB_ORDER_STATUS.OPENED) {
                jpql = jpql + " AND YEAR(j.jobOrderDate) = ?";
            } else if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql = jpql + " AND YEAR(j.actualCloseDate) = ?";
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql = jpql + " AND YEAR(j.actualCancelDate) = ?";
            }
            map.put(i++, year);
        }
        jpql = jpql + " ORDER BY j.jobOrderDate,  j.hospitalDevice.name";
        return this.queryJPQL(jpql, map, GenJobOrder.class);
    }

    public GenJobOrder searchForActiveJobOrder(int siteId, int locationId, String jobOrderNo, String deviceCode, String deviceSN) {
        List<GenJobOrder> list;
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.site.id = " + siteId + " AND j.closed = false AND j.cancelled = false AND (j.hospitalDevice.status = " + Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus() + " OR j.hospitalDevice.id = " + 1 + ")";
        int i = 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (jobOrderNo != null && !jobOrderNo.trim().isEmpty()) {
            jpql = jpql + " AND j.jobOrderNo = ?";
            map.put(++i, jobOrderNo);
        }
        if (deviceCode != null && !deviceCode.trim().isEmpty()) {
            jpql = jpql + " AND j.hospitalDevice.code = ?";
            map.put(++i, deviceCode);
        }
        if (deviceSN != null && !deviceSN.trim().isEmpty()) {
            jpql = jpql + " AND j.hospitalDevice.serialNo = ?";
            map.put(++i, deviceSN);
        }
        if ((list = this.queryJPQL(jpql, map, GenJobOrder.class)) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<GenJobOrder> getDeviceJobOrders(int siteId, int locationId, String deviceCode, String deviceSN) {
        String jpql = "SELECT j FROM GenJobOrder j WHERE j.hospitalDevice.site.id = ? AND j.cancelled = false";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        int i = 1;
        map.put(1, siteId);
        if (locationId != 0) {
            jpql = jpql + " AND j.hospitalDevice.hospital.id = ?";
            map.put(++i, locationId);
        }
        if (deviceCode != null && !deviceCode.trim().isEmpty()) {
            jpql = jpql + " AND j.hospitalDevice.code = ?";
            map.put(++i, deviceCode);
        }
        if (deviceSN != null && !deviceSN.trim().isEmpty()) {
            jpql = jpql + " AND j.hospitalDevice.serialNo = ?";
            map.put(++i, deviceSN);
        }
        jpql = jpql + " ORDER BY j.jobOrderDate";
        return this.queryJPQL(jpql, map, GenJobOrder.class);
    }

    public synchronized long getJobOrderCount(int siteId, int month, int year) {
        String jpql = "SELECT COUNT(j.id) FROM GenJobOrder j WHERE j.site.id = " + siteId + " AND MONTH(j.openedIn) = " + month + " AND YEAR(j.openedIn) = " + year;
        return this.getJPQLSingleResult(jpql, null, Long.class);
    }

    public List<GenLookupEvaluationGroup> getActiveEvaluationGroups() {
        return this.executeNamedQuery("GenLookupEvaluationGroup.findAllActive", GenLookupEvaluationGroup.class);
    }

    public List<EvaluationTemplateItem> getEvaluationTemplateItems(int groupId, int siteId, Integer month, Integer year) {
        ArrayList<EvaluationTemplateItem> items = new ArrayList<EvaluationTemplateItem>();
        String namedQueryName = "select gl.item_id ,gp.max_degree,gp.eval_degree, gp.eval_percentage, gp.penalty_percentage , gp.penalty_value ,  gp.dedicated_value as d1, gl.dedicated_value  as d2 , gp.start_date , gp.end_date  from gen_site_evaluation_item_dedicated_value as gl   left join gen_periodic_site_evaluation as gp   on gl.item_id = gp.item_id where gl.item_id in (select id from gen_lookup_evaluation_group_item where group_id =?) and gl.site_id =?  and gp.month =?  and year =? ";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, groupId);
        params.put(2, siteId);
        params.put(3, month);
        params.put(4, year);
        List searchResult = this.executeNativeQueryForSearch(namedQueryName, params);
        if (searchResult != null && !searchResult.isEmpty()) {
            for (int i = 0; i < searchResult.size(); ++i) {
                Object[] r = (Object[])searchResult.get(i);
                EvaluationTemplateItem eti = new EvaluationTemplateItem();
                eti.setItem(this.findById((Integer)r[0], GenLookupEvaluationGroupItem.class));
                eti.setMaxDegree(r[1] == null ? 0 : (Integer)r[1]);
                eti.setEvalDegree(r[2] == null ? 0.0f : ((Float)r[2]).floatValue());
                eti.setEvalPercentage(r[3] == null ? 0.0f : ((Float)r[3]).floatValue());
                eti.setPenaltyPercentage(r[4] == null ? 0.0f : ((Float)r[4]).floatValue());
                eti.setPenaltyValue(r[5] == null ? 0.0f : ((Float)r[5]).floatValue());
                eti.setEvaluationDedicatedValue(r[6] == null ? 0.0f : ((Float)r[6]).floatValue());
                eti.setItemDedicatedValue(r[7] == null ? 0.0f : ((Float)r[7]).floatValue());
                eti.setStartDate((Date)(r[8] == null ? Integer.valueOf(0) : (Date)r[8]));
                eti.setEndDate((Date)(r[9] == null ? Integer.valueOf(0) : (Date)r[9]));
                items.add(eti);
            }
        }
        return items;
    }

    public List<EvaluationTemplateItem> getEvaluationTemplateItems(int groupId, int siteId) {
        ArrayList<EvaluationTemplateItem> items = new ArrayList<EvaluationTemplateItem>();
        String namedQueryName = "select gl.id, gs.dedicated_value , gl.max_degree from gen_lookup_evaluation_group_item as gl inner join gen_site_evaluation_item_dedicated_value as gs on gl.id = gs.item_id and gl.id in (select id from gen_lookup_evaluation_group_item where group_id = ? ) and gs.site_id = ? ";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, groupId);
        params.put(2, siteId);
        List searchResult = this.executeNativeQueryForSearch(namedQueryName, params);
        if (searchResult != null && !searchResult.isEmpty()) {
            for (int i = 0; i < searchResult.size(); ++i) {
                Object[] r = (Object[])searchResult.get(i);
                EvaluationTemplateItem eti = new EvaluationTemplateItem();
                eti.setItem(this.findById((Integer)r[0], GenLookupEvaluationGroupItem.class));
                eti.setItemDedicatedValue(r[1] == null ? 0.0f : ((Float)r[1]).floatValue());
                eti.setMaxDegree(r[2] == null ? 0 : (Integer)r[2]);
                items.add(eti);
            }
        }
        return items;
    }

    public Date getEvaluationStartDate(int siteId, int month, int year) {
        String jpql = "SELECT gp from GenPeriodicSiteEvaluation gp WHERE gp.site.id = ? and  gp.month = ? and gp.year = ? ";
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, siteId);
        params.put(2, month);
        params.put(3, year);
        List<GenPeriodicSiteEvaluation> list = this.queryJPQL(jpql, params, GenPeriodicSiteEvaluation.class);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getStartDate();
        }
        return null;
    }

    public List<GenEndUserMaintenanceRequest> getOpenMaintenanceRequests(int siteId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        return this.executeNamedQuery("GenEndUserMaintenanceRequest.findOpenMaintenanceRequests", params, GenEndUserMaintenanceRequest.class);
    }

    public List<GenHospitalDeviceInternalPpmNotificationSchedule> getCurrentlyScheduledInternalPPMForSite(int siteId, int pPMcatId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("site_id", siteId);
        params.put("planned_date", new Date());
        if (pPMcatId <= 0) {
            return this.executeNamedQuery("GenHospitalDeviceInternalPpmNotificationSchedule.getSiteNotifications", params, GenHospitalDeviceInternalPpmNotificationSchedule.class);
        }
        params.put("catId", pPMcatId);
        return this.executeNamedQuery("GenHospitalDeviceInternalPpmNotificationSchedule.getSiteNotificationsPerDepartment", params, GenHospitalDeviceInternalPpmNotificationSchedule.class);
    }

    public List<GenHospitalDeviceInternalPpmNotificationSchedule> getCurrentlyScheduledInternalPPMForDevice(int siteId, int deviceId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("site_id", siteId);
        params.put("planned_date", new Date());
        params.put("device_id", deviceId);
        List<GenHospitalDeviceInternalPpmNotificationSchedule> list = this.executeNamedQuery("GenHospitalDeviceInternalPpmNotificationSchedule.getDeviceInternalPPMSchedule", params, GenHospitalDeviceInternalPpmNotificationSchedule.class, "GenHospitalDeviceInternalPpmNotificationSchedule.graph.fetchAll");
        return list;
    }

    public GenHospitalDeviceInternalPpmNotificationSchedule getPPpmNotificationSchedule(int ppmNotificationScheduleId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id", ppmNotificationScheduleId);
        List<GenHospitalDeviceInternalPpmNotificationSchedule> schedules = this.executeNamedQuery("GenHospitalDeviceInternalPpmNotificationSchedule.findById", params, GenHospitalDeviceInternalPpmNotificationSchedule.class, "GenHospitalDeviceInternalPpmNotificationSchedule.graph.fetchAll");
        return schedules.size() == 1 ? schedules.get(0) : null;
    }
}
