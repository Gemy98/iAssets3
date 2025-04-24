/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.biomedical.DB;

import com.iassets.biomedical.entity.BioDailyContractorEvaluation;
import com.iassets.biomedical.entity.BioDailyMaintenancePerformanceLog;
import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioHospitalDevicePpmDetail;
import com.iassets.biomedical.entity.BioHospitalDeviceScrappingInfo;
import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.entity.BioMonthlyContractorEvaluation;
import com.iassets.biomedical.entity.BioPPMProgressBill;
import com.iassets.biomedical.entity.BioProgressBill;
import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.entity.BioSparePartsProgressBill;
import com.iassets.common.DB.CommonDBQueryManager;
import com.iassets.common.util.Enums;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="bioDBQueryManager")
@Transactional
public class DBQueryManager
extends CommonDBQueryManager {
    public BioHospitalDevice getDevice(int siteId, int locationId, String code, String sn, Enums.DEVICE_STATUS status, boolean excludeScrapped, boolean excludeTransferred) {
        List<BioHospitalDevice> list;
        boolean snExists;
        boolean codeExists = code != null && !code.trim().isEmpty();
        boolean bl = snExists = sn != null && !sn.trim().isEmpty();
        if (siteId == 0 || !codeExists && !snExists) {
            return null;
        }
        String jpql = "SELECT DISTINCT h FROM BioHospitalDevice h WHERE h.site.id = " + siteId;
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
        if ((list = this.executeRetriveJPQLWithPositionalParameters(jpql, map, BioHospitalDevice.class, "BioHospitalDevice.graph.fetchAll")) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public BioHospitalDevice checkDeviceCodeOrSerialDuplication(int siteId, String code, String sn, int excludedDeviceId) {
        if ((code == null || code.isEmpty()) && (sn == null || sn.isEmpty())) {
            return null;
        }
        String jpql = "select h from BioHospitalDevice h where h.site.id = ? and (h.code = ? or h.serialNo = ?) and h.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, code);
        map.put(3, sn);
        map.put(4, excludedDeviceId);
        List<BioHospitalDevice> list = this.queryJPQL(jpql, map, BioHospitalDevice.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public BioJobOrder checkJobOrderNoDuplication(int siteId, String jobOrderNo) {
        String jpql = "SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = " + siteId + " AND j.jobOrderNo = ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, jobOrderNo);
        List<BioJobOrder> list = this.queryJPQL(jpql, map, BioJobOrder.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    private List<BioSpInventoryContent> getSpInventoryContent(int siteId, String code, boolean underThresholdOnly) {
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        String sqlString = "SELECT sp_inventory_content_id, name, MIN(NULLIf(unit_price,0)), MAX(unit_price) FROM bio_sp_inventory_content sp_inventory_content INNER JOIN bio_sp_inventory_transaction sp_inventory_transaction ON sp_inventory_content.id = sp_inventory_transaction.sp_inventory_content_id WHERE sp_inventory_content.site_id = ? AND sp_inventory_transaction.input = true";
        map.put(1, siteId);
        if (code != null) {
            sqlString = sqlString + " AND sp_inventory_content.code = ? ";
            map.put(2, code);
        } else if (underThresholdOnly) {
            sqlString = sqlString + " AND sp_inventory_content.available_quantity < sp_inventory_content.threshold";
        }
        sqlString = sqlString + " GROUP BY sp_inventory_content_id ORDER BY name";
        ArrayList<BioSpInventoryContent> inventoryContents = null;
        BioSpInventoryContent spInventoryContent = null;
        List list = this.executeNativeQueryForSearch(sqlString, map);
        if (list != null && !list.isEmpty()) {
            inventoryContents = new ArrayList<BioSpInventoryContent>();
            for (int i = 0; i < list.size(); ++i) {
                Object[] r = (Object[])list.get(i);
                spInventoryContent = this.findById((Integer)r[0], BioSpInventoryContent.class);
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

    public Long getSpInventoryNotificationsCount(int siteId) {
        String sqlString = "SELECT count(h) from BioSpInventoryContent h WHERE h.site.id = ? AND h.availableQuantity < h.threshold";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        return this.getJPQLSingleResult(sqlString, map, Long.class);
    }

    public BioSpInventoryContent getSpInventoryContentWithStats(int siteId, String code) {
        List<BioSpInventoryContent> list = this.getSpInventoryContent(siteId, code, false);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<BioSpInventoryContent> getSpInventoryContentsWithStats(int siteId, boolean underThresholdOnly) {
        return this.getSpInventoryContent(siteId, null, underThresholdOnly);
    }

    public BioSpInventoryContent checkSPCategoryDuplication(int siteId, String code, int excludedSPCatId) {
        if (siteId == 0 || code == null || code.isEmpty()) {
            return null;
        }
        String jpql = "select s from BioSpInventoryContent s where s.site.id = ? and s.code = ? and s.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, code);
        map.put(3, excludedSPCatId);
        List<BioSpInventoryContent> list = this.queryJPQL(jpql, map, BioSpInventoryContent.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public BioJobOrder inquireJobOrder(int siteId, int locationId, String jobOrderNo) {
        List<BioJobOrder> list;
        String jpql = "SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = " + siteId;
        int i = 0;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (jobOrderNo != null && !jobOrderNo.trim().isEmpty()) {
            jpql = jpql + " AND j.jobOrderNo = ?";
            map.put(++i, jobOrderNo);
        }
        if ((list = this.queryJPQL(jpql, map, BioJobOrder.class)) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<BioJobOrder> getJobOrdersColsedAndCanceled(int siteId, int locationId, Enums.JOB_ORDER_STATUS status, HashMap<String, Object> params) {
        StringBuilder jpql = new StringBuilder("SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = :siteId");
        jpql.append(" AND j.hospitalDevice.status NOT IN ( :excludedStatus )");
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        if (locationId != 0) {
            jpql.append(" AND j.hospitalDevice.hospital.id = :locationId");
            params.put("locationId", locationId);
        }
        if (status != null) {
            if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql.append(" AND j.closed = true");
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql.append(" AND j.cancelled = true");
            } else {
                jpql.append(" AND j.cancelled = false AND j.closed = false");
                if (status == Enums.JOB_ORDER_STATUS.LATE) {
                    jpql.append(" AND j.jobOrderPriority.id != " + Enums.JOB_ORDER_PRIORITY.NORMAL.getDBId() + " AND DATEDIFF(now(),j.jobOrderDate) >= 4 AND j.secondAction IS NULL AND j.secondActionDate IS NULL");
                }
            }
        }
        if (params.get("monthYear") != null) {
            String jobOrderOpenDate = (String)params.get("monthYear");
            String[] jobOrderOpenDateArr = jobOrderOpenDate.split("/");
            params.remove("monthYear");
            params.put("month", Integer.parseInt(jobOrderOpenDateArr[0]));
            if (status != Enums.JOB_ORDER_STATUS.LATE) {
                if (status == null || status == Enums.JOB_ORDER_STATUS.OPENED) {
                    jpql.append(" AND MONTH(j.jobOrderDate) = :month");
                } else if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                    jpql.append(" AND MONTH(j.actualCloseDate) = :month");
                } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                    jpql.append(" AND MONTH(j.actualCancelDate) = :month");
                }
            }
            params.put("year", Integer.parseInt(jobOrderOpenDateArr[1]));
            if (status != Enums.JOB_ORDER_STATUS.LATE) {
                if (status == null || status == Enums.JOB_ORDER_STATUS.OPENED) {
                    jpql.append(" AND YEAR(j.jobOrderDate) = :year");
                } else if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                    jpql.append(" AND YEAR(j.actualCloseDate) = :year");
                } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                    jpql.append(" AND YEAR(j.actualCancelDate) = :year");
                }
            }
        }
        if (params.get("employeeId") != null && status != null) {
            if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql.append(" AND j.closedBy.id = :employeeId");
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql.append(" AND j.cancelledBy.id = :employeeId");
            } else {
                params.remove("employeeId");
            }
        }
        jpql.append(" ORDER BY j.jobOrderDate, j.hospitalDevice.name");
        return this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioJobOrder.class);
    }

    public List<BioJobOrder> getJobOrdersForOpendAndLate(int siteId, int locationId, Enums.JOB_ORDER_STATUS status, HashMap<String, Object> params) {
        StringBuilder jpql = new StringBuilder("SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = :siteId");
        jpql.append(" AND j.hospitalDevice.status NOT IN ( :excludedStatus )");
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        if (locationId != 0) {
            jpql.append(" AND j.hospitalDevice.hospital.id = :locationId");
            params.put("locationId", locationId);
        }
        if (status != null) {
            if (status == Enums.JOB_ORDER_STATUS.CLOSED) {
                jpql.append(" AND j.closed = true");
            } else if (status == Enums.JOB_ORDER_STATUS.CANCELLED) {
                jpql.append(" AND j.cancelled = true");
            } else {
                jpql.append(" AND j.cancelled = false AND j.closed = false");
                if (status == Enums.JOB_ORDER_STATUS.LATE) {
                    jpql.append(" AND j.jobOrderPriority.id != " + Enums.JOB_ORDER_PRIORITY.NORMAL.getDBId() + " AND DATEDIFF(now(),j.jobOrderDate) >= 4 AND j.secondAction IS NULL AND j.secondActionDate IS NULL");
                }
            }
        }
        if (params.get("deviceWithinWarranty") != null) {
            Integer[] deviceWithinWarranty = (Integer[])params.get("deviceWithinWarranty");
            Boolean includeWarranty = deviceWithinWarranty.length == 1;
            params.remove("deviceWithinWarranty");
            if (includeWarranty.booleanValue()) {
                Boolean isDeviceWithinWarranty = deviceWithinWarranty[0] == 1;
                jpql.append(" AND ( j.hospitalDevice.warrantyExpireDate ");
                jpql.append(isDeviceWithinWarranty != false ? "> :crrentDate" : " < :crrentDate OR j.hospitalDevice.warrantyExpireDate IS NULL");
                jpql.append(" ) ");
                params.put("crrentDate", new Date());
            }
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND j.hospitalDevice.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND j.hospitalDevice.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND j.hospitalDevice.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND j.hospitalDevice.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND j.hospitalDevice.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND j.hospitalDevice.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND j.hospitalDevice.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND j.hospitalDevice.model LIKE :model");
        }
        if (params.get("deviceStatus") != null) {
            jpql.append(" AND j.hospitalDevice.status LIKE :deviceStatus");
        }
        jpql.append(" ORDER BY j.jobOrderDate, j.hospitalDevice.name");
        return this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioJobOrder.class);
    }

    public List<BioJobOrder> getJobOrders(int siteId, int locationId, int month, int year, Enums.JOB_ORDER_STATUS status, Boolean deviceWithinWarranty) {
        String jpql = "SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = " + siteId + " AND j.hospitalDevice.status NOT IN (" + Enums.DEVICE_STATUS.SCRAPPED.getStatus() + "," + Enums.DEVICE_STATUS.TRANSFERRED.getStatus() + ")";
        int i = 1;
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        if (locationId != 0) {
            jpql = jpql + " AND j.hospitalDevice.hospital.id = ?";
            map.put(i++, locationId);
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
        if (deviceWithinWarranty != null) {
            jpql = jpql + " AND ( j.hospitalDevice.warrantyExpireDate " + (deviceWithinWarranty != false ? "> ?" : " < ? OR j.hospitalDevice.warrantyExpireDate IS NULL") + " ) ";
            map.put(i++, new Date());
        }
        jpql = jpql + " ORDER BY j.jobOrderDate, j.hospitalDevice.name";
        return this.queryJPQL(jpql, map, BioJobOrder.class);
    }

    public Long getJobOrderNotifactionsCount(int siteId) {
        String jpql = "SELECT count(j) FROM BioJobOrder j WHERE j.hospitalDevice.site.id = ? AND j.hospitalDevice.status NOT IN (?,?) AND j.cancelled = false AND j.closed = false AND j.jobOrderPriority.id != ? AND DATEDIFF(now(),j.jobOrderDate) >= 4 AND j.secondAction IS NULL AND j.secondActionDate IS NULL";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        map.put(4, Enums.JOB_ORDER_PRIORITY.NORMAL.getDBId());
        return this.getJPQLSingleResult(jpql, map, Long.class);
    }

    public BioJobOrder searchForActiveJobOrder(int siteId, int locationId, String jobOrderNo, String deviceCode, String deviceSN) {
        List<BioJobOrder> list;
        String jpql = "SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = " + siteId + " AND j.closed = false AND j.cancelled = false AND j.hospitalDevice.status = " + Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus();
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
        if ((list = this.queryJPQL(jpql, map, BioJobOrder.class)) != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<BioJobOrder> getDeviceJobOrders(int siteId, int locationId, String deviceCode, String deviceSN) {
        String jpql = "SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = ? AND j.cancelled = false";
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
        return this.queryJPQL(jpql, map, BioJobOrder.class);
    }

    public synchronized long getJobOrderCount(int siteId, int month, int year) {
        String jpql = "SELECT COUNT(j.id) FROM BioJobOrder j WHERE j.hospitalDevice.site.id = " + siteId + " AND MONTH(j.openedIn) = " + month + " AND YEAR(j.openedIn) = " + year;
        return this.getJPQLSingleResult(jpql, null, Long.class);
    }

    public List<BioEndUserMaintenanceRequest> getOpenMaintenanceRequests(int siteId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        return this.executeNamedQuery("BioEndUserMaintenanceRequest.findOpenMaintenanceRequests", params, BioEndUserMaintenanceRequest.class);
    }

    public Long getOpenMaintenanceNotifactionsCount(int siteId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        return this.getJPQLSingleResultFromNamedQuery("BioEndUserMaintenanceRequest.findOpenMaintenanceRequestsCount", params, Long.class, null);
    }

    public BioEndUserMaintenanceRequest getOpenMaintenanceRequestForDevice(int siteId, int deviceId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("deviceId", deviceId);
        List<BioEndUserMaintenanceRequest> list = this.executeNamedQuery("BioEndUserMaintenanceRequest.findOpenMaintenanceRequestByDeviceId", params, BioEndUserMaintenanceRequest.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<BioPPMProgressBill> getPPMProgressBillList(int siteId) {
        String jpql = "select ppm from  BioPPMProgressBill ppm where ppm.progressBill.site.id = ? order by ppm.progressBill.issueDate";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        List<BioPPMProgressBill> list = this.queryJPQL(jpql, map, BioPPMProgressBill.class);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    public List<BioSparePartsProgressBill> getSparePartsProgressBillList(int siteId) {
        String jpql = "select sp from  BioSparePartsProgressBill sp where sp.progressBill.site.id = ? order by sp.progressBill.issueDate";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        List<BioSparePartsProgressBill> list = this.queryJPQL(jpql, map, BioSparePartsProgressBill.class);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    public BioPPMProgressBill getLastPPMProgressBill(int siteId, int excludedId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("pbId", excludedId);
        List<BioPPMProgressBill> resultList = this.executeNamedQuery("BioPPMProgressBill.getLastProgressBill", params, BioPPMProgressBill.class);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public BioSparePartsProgressBill getLastSparePartsProgressBill(int siteId, int excludedId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("pbId", excludedId);
        List<BioSparePartsProgressBill> resultList = this.executeNamedQuery("BioSparePartsProgressBill.getLastProgressBill", params, BioSparePartsProgressBill.class);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public BioProgressBill checkPBPaymentNoDuplication(int siteId, int paymentNo, int pbTypeId, int excludedPBId) {
        String jpql = "select b from BioProgressBill b where b.site.id = ? and b.paymentNo = ? and b.pbTypeId = ? and b.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, paymentNo);
        map.put(3, pbTypeId);
        map.put(4, excludedPBId);
        List<BioProgressBill> list = this.queryJPQL(jpql, map, BioProgressBill.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public BioProgressBill checkIssueNoAndIssueDateDuplication(int siteId, String issueNo, Date issueDate, int excludedPBId) {
        if (issueNo == null || issueNo.isEmpty() || issueDate == null) {
            return null;
        }
        String jpql = "select b from BioProgressBill b where b.site.id = ? and b.issueNo = ? and b.issueDate = ? and b.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, issueNo);
        map.put(3, issueDate);
        map.put(4, excludedPBId);
        List<BioProgressBill> list = this.queryJPQL(jpql, map, BioProgressBill.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public BioHospitalDevicePpmDetail getSpecificPPMDetails(int deviceId, int month, int year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        params.put("month", month);
        params.put("year", year);
        List<BioHospitalDevicePpmDetail> resultList = this.executeNamedQuery("BioHospitalDevicePpmDetail.getPPMDetails", params, BioHospitalDevicePpmDetail.class);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public BioHospitalDeviceScrappingInfo getDeviceScrappingInfo(int deviceId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("deviceId", deviceId);
        List<BioHospitalDeviceScrappingInfo> resultList = this.executeNamedQuery("BioHospitalDeviceScrappingInfo.findByDevice", params, BioHospitalDeviceScrappingInfo.class);
        if (resultList != null && resultList.size() == 1) {
            return resultList.get(0);
        }
        return null;
    }

    public BioMonthlyContractorEvaluation getBioMonthlyContractorEvaluation(int siteId, int month, int year) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("month", month);
        params.put("year", year);
        List<BioMonthlyContractorEvaluation> resultList = this.executeNamedQuery("BioMonthlyContractorEvaluation.findBySiteAndYearAndMonth", params, BioMonthlyContractorEvaluation.class);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public List<BioHospitalDevice> getDevicesWhosePPmVisitsNotRecorded(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT j FROM BioHospitalDevice j where j.site.id = :siteId ");
        jpql.append(" AND j.status NOT IN (:excludedStatus)");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND j.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND j.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND j.category.id IN (:categoryArr)");
        }
        if (params.get("monthYear") != null) {
            String jobOrderOpenDate = (String)params.get("monthYear");
            String[] jobOrderOpenDateArr = jobOrderOpenDate.split("/");
            params.remove("monthYear");
            params.put("monthWithSeparator", "%$" + Integer.parseInt(jobOrderOpenDateArr[0]) + "$" + "%");
            params.put("month", Integer.parseInt(jobOrderOpenDateArr[0]));
            params.put("year", Integer.parseInt(jobOrderOpenDateArr[1]));
            jpql.append(" AND j.pmVisitsMonths LIKE :monthWithSeparator ");
            jpql.append(" AND j.id not in (select p.hospitalDevice.id from BioHospitalDevicePpmDetail p where p.visitYear =:year and p.visitMonth =:month) ");
        }
        jpql.append(" ORDER BY j.hospital.id, j.hospitalDepartment.id, j.category.id, j.name");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public Long getPPMNotificationsCount(int siteId, int year, int month) {
        String jpql = "SELECT count(h) FROM BioHospitalDevice h where h.site.id = ? AND h.status NOT IN( ?,?) AND h.pmVisitsMonths LIKE ? AND h.id NOT IN (SELECT p.hospitalDevice.id FROM BioHospitalDevicePpmDetail p WHERE p.visitYear = ? AND p.visitMonth = ?)";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, Enums.DEVICE_STATUS.SCRAPPED.getStatus());
        map.put(3, Enums.DEVICE_STATUS.TRANSFERRED.getStatus());
        map.put(4, "%$" + month + "$" + "%");
        map.put(5, year);
        map.put(6, month);
        return this.getJPQLSingleResult(jpql, map, Long.class);
    }

    public List<BioHospitalDevice> searchDevices(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN ( :excludedStatus )");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        jpql.append(" ORDER BY h.hospital.id, h.category.id, h.name, h.status");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public List<BioJobOrder> searchBioJobOrders(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT j FROM BioJobOrder j WHERE j.hospitalDevice.site.id = :siteId ");
        jpql.append(" AND j.cancelled = false AND j.closed = false ");
        jpql.append(" AND j.hospitalDevice.status NOT IN (:excludedStatus)");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND j.hospitalDevice.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND j.hospitalDevice.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND j.hospitalDevice.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND j.hospitalDevice.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND j.hospitalDevice.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND j.hospitalDevice.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND j.hospitalDevice.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND j.hospitalDevice.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND j.hospitalDevice.model LIKE :model");
        }
        if (params.get("jobOrderOpenDate") != null) {
            String jobOrderOpenDate = (String)params.get("jobOrderOpenDate");
            String[] jobOrderOpenDateArr = jobOrderOpenDate.split("/");
            params.remove("jobOrderOpenDate");
            params.put("jobOrderOpenDateMonth", Integer.parseInt(jobOrderOpenDateArr[0]));
            jpql.append(" AND Month(j.openedIn) = :jobOrderOpenDateMonth");
            params.put("jobOrderOpenDateYear", Integer.parseInt(jobOrderOpenDateArr[1]));
            jpql.append(" AND Year(j.openedIn) = :jobOrderOpenDateYear");
        }
        jpql.append(" ORDER BY j.hospitalDevice.hospital.id, j.hospitalDevice.hospitalDepartment.id, j.jobOrderDate");
        List<BioJobOrder> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioJobOrder.class);
        return deviceList;
    }

    public List<BioHospitalDevice> searchBioHospitalDevicesForWarrenty(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN (:excludedStatus) ");
        if (params.get("from") != null) {
            jpql.append(" AND h.warrantyExpireDate >= :from ");
        }
        if (params.get("to") != null) {
            jpql.append(" AND h.warrantyExpireDate <= :to");
        }
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND  h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        jpql.append(" ORDER BY h.hospital.id, h.hospitalDepartment.id, h.warrantyExpireDate");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public List<BioHospitalDevice> searchBioHospitalDevicesForDevicesNotInContract(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN (:excludedStatus) ");
        jpql.append(" AND h.withinContract != true ");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND  h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        jpql.append(" ORDER BY  h.hospital.id, h.category.id, h.subcontractor, h.model, h.name");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public List<BioHospitalDevice> searchBioHospitalDevicesForFromOtherSites(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN (:excludedStatus) ");
        jpql.append(" AND h.addedFromAnotherLocation = true ");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND  h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        if (params.get("previousSiteName") != null) {
            jpql.append(" AND h.locationName LIKE :previousSiteName");
        }
        jpql.append(" ORDER BY h.hospital.id, h.hospitalDepartment.id, h.category.id, h.name");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public List<BioHospitalDevice> searchDevicesForStatus(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN ( :excludedStatus )");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        if (params.get("deviceStatus") != null) {
            jpql.append(" AND h.status LIKE :deviceStatus");
        }
        jpql.append(" ORDER BY h.hospital.id, h.category.id, h.name, h.status");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public List<BioHospitalDevice> searchDevicesForNeedPPMVisits(Integer siteId, int locationId, HashMap<String, Object> params) {
        params.put("siteId", siteId);
        params.put("excludedStatus", Arrays.asList(Enums.DEVICE_STATUS.SCRAPPED.getStatus(), Enums.DEVICE_STATUS.TRANSFERRED.getStatus()));
        StringBuilder jpql = new StringBuilder("SELECT h FROM BioHospitalDevice h WHERE h.site.id = :siteId ");
        jpql.append(" AND h.status NOT IN ( :excludedStatus )");
        if (locationId != 0) {
            params.put("locationId", locationId);
            jpql.append(" AND h.hospital.id = :locationId");
        }
        if (params.get("classification") != null) {
            jpql.append(" AND h.functionalClassification.id = :classification");
        }
        if (params.get("categoryArr") != null) {
            jpql.append(" AND h.category.id IN (:categoryArr)");
        }
        if (params.get("hospLocation") != null) {
            jpql.append(" AND h.hospitalLocation.id = :hospLocation");
        }
        if (params.get("department") != null) {
            jpql.append(" AND h.hospitalDepartment.id = :department");
        }
        if (params.get("name") != null) {
            jpql.append(" AND h.name LIKE :name");
        }
        if (params.get("agentName") != null) {
            jpql.append(" AND h.agentName LIKE :agentName");
        }
        if (params.get("subcontractor") != null) {
            jpql.append(" AND h.subcontractor LIKE :subcontractor");
        }
        if (params.get("manufacturerName") != null) {
            jpql.append(" AND h.manufacturerName LIKE :manufacturerName");
        }
        if (params.get("model") != null) {
            jpql.append(" AND h.model LIKE :model");
        }
        if (params.get("deviceStatus") != null) {
            jpql.append(" AND h.status LIKE :deviceStatus");
        }
        if (params.get("month") != null) {
            Integer month = (Integer)params.get("month");
            params.remove("month");
            params.put("monthLike", "%$" + month + "$" + "%");
            jpql.append(" AND h.pmVisitsMonths LIKE :monthLike");
        }
        jpql.append(" ORDER BY h.hospital.id, h.category.id, h.name, h.status");
        List<BioHospitalDevice> deviceList = this.executeRetriveJPQLWithNamedParameters(jpql.toString(), params, BioHospitalDevice.class);
        return deviceList;
    }

    public BioDailyContractorEvaluation getContractorEvaluationRecordByDate(int siteId, Date date) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("date", date);
        return this.getJPQLSingleResultFromNamedQuery("BioDailyContractorEvaluation.findContractorEvaluationRecordByDate", params, BioDailyContractorEvaluation.class, null);
    }

    public BioDailyMaintenancePerformanceLog findMaintenancePerformanceLogRecordByDate(int siteId, Date date) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("siteId", siteId);
        params.put("date", date);
        return this.getJPQLSingleResultFromNamedQuery("BioDailyMaintenancePerformanceLog.findMaintenancePerformanceLogRecordByDate", params, BioDailyMaintenancePerformanceLog.class, null);
    }
}
