/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 *  org.springframework.transaction.annotation.Transactional
 */
package com.iassets.common.DB;

import com.iassets.common.DB.DBManager;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonLookupUserType;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonSiteEmployee;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.entity.CommonUserRole;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Enums;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="commonDBQueryManager")
@Transactional
public class CommonDBQueryManager
extends DBManager {
    public CommonUser getUser(String userName, String password) {
        CommonUser user = null;
        String jpql = "select u from CommonUser u where u.userName = ? and u.password = ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, userName);
        map.put(2, password);
        List<CommonUser> resultList = this.queryJPQL(jpql, map, CommonUser.class);
        if (resultList != null && resultList.size() == 1) {
            user = resultList.get(0);
            jpql = "select se from CommonSiteEmployee se where se.employee.id = ? and se.status = true";
            map.clear();
            map.put(1, user.getEmployee().getId());
            user.getEmployee().setSiteEmployees(this.queryJPQL(jpql, map, CommonSiteEmployee.class));
        }
        return user;
    }

    public CommonUser getUserOfSpecificEmployee(int empId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", empId);
        List<CommonUser> resultList = this.executeNamedQuery("CommonUser.findByEmployeeId", params, CommonUser.class);
        if (resultList != null && resultList.size() == 1) {
            return resultList.get(0);
        }
        return null;
    }

    public List<CommonUserRole> getUserRoles(String userName) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        return this.executeNamedQuery("CommonUserRole.findByUserName", params, CommonUserRole.class);
    }

    public List<CommonSiteEmployee> getSitesOfSpecificEmployee(int empId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", empId);
        return this.executeNamedQuery("CommonSiteEmployee.findByEmployeeId", params, CommonSiteEmployee.class);
    }

    public List<CommonHospital> getSiteActiveLocations(int siteId, String langCode) {
        String jpql = "select h from CommonHospital h where h.status = true and h.site.id = ? order by h.healthCenter";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        List<CommonHospital> list = this.queryJPQL(jpql, map, CommonHospital.class);
        if (langCode == null) {
            return list;
        }
        return AppUtil.sortLocalizedEntityList(list, langCode);
    }

    public List<CommonHospital> getSiteActiveLocations(int siteId) {
        return this.getSiteActiveLocations(siteId, null);
    }

    public void updateUserPassword(int userId, String newPassword) {
        CommonUser user = this.findById(userId, CommonUser.class);
        user.setPassword(newPassword);
        user.setPasswordChangeCount(user.getPasswordChangeCount() + 1);
        user.setLastPasswordChangeDate(new Date());
    }

    public void updateSiteUserPassword(int siteId, int userId, String newPassword) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("siteId", siteId);
        List<CommonUser> users = this.executeNamedQuery("CommonUser.findById", map, CommonUser.class);
        if (users != null && users.size() == 1) {
            CommonUser user = users.get(0);
            user.setPassword(newPassword);
            user = this.mergeEntity(user);
        }
    }

    public CommonEmployee findSiteEmployeeById(int siteId, int empId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("employeeId", empId);
        List<CommonEmployee> emps = this.executeNamedQuery("CommonSiteEmployee.findEmployeeById", map, CommonEmployee.class);
        if (emps != null && emps.size() == 1) {
            return emps.get(0);
        }
        return null;
    }

    public CommonEmployee findDirectorateEmployeeById(int directorateId, int empId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("directorateId", directorateId);
        map.put("employeeId", empId);
        List<CommonEmployee> emps = this.executeNamedQuery("CommonEmployee.findEmployeeByIdAndDirectorateId", map, CommonEmployee.class);
        if (emps != null && emps.size() == 1) {
            return emps.get(0);
        }
        return null;
    }

    public List<CommonHospitalDepartment> getLocationDepartments(int locationId, Enums.USER_ALLOWED_APP_TYPE appType, String langCode) {
        String jpql = "SELECT h FROM CommonHospitalDepartment h WHERE h.status = true AND h.hospital.id =  " + locationId;
        if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            jpql = jpql + " AND h.allowedApps IN (1,3)";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            jpql = jpql + " AND h.allowedApps IN (2,3)";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) {
            jpql = jpql + " AND h.allowedApps = 3";
        }
        List<CommonHospitalDepartment> list = this.queryJPQL(jpql, null, CommonHospitalDepartment.class);
        if (langCode == null) {
            return list;
        }
        return AppUtil.sortLocalizedEntityList(list, langCode);
    }

    public List<CommonHospitalDepartment> getLocationDepartments(int locationId, Enums.USER_ALLOWED_APP_TYPE appType) {
        return this.getLocationDepartments(locationId, appType, null);
    }

    public List<CommonEmployee> getEmployees(int siteId, Enums.USER_TYPE[] desiredTypes, int hospDepId, int gmpCategoryId, boolean includeNotActive) {
        String jpql = "SELECT e FROM CommonEmployee e INNER JOIN e.siteEmployees s WHERE s.site.id = " + siteId;
        if (!includeNotActive) {
            jpql = jpql + " AND e.status = true";
        }
        if (hospDepId != 0) {
            jpql = jpql + " AND e.department.id = " + hospDepId;
        }
        if (gmpCategoryId != 0) {
            jpql = jpql + " AND e.gmpCategory.id = " + gmpCategoryId;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (desiredTypes != null && desiredTypes.length > 0) {
            ArrayList<Integer> desiredTypesIds = new ArrayList<Integer>();
            for (Enums.USER_TYPE userType : desiredTypes) {
                desiredTypesIds.add(userType.getId());
            }
            params.put("desiredTypesIds", desiredTypesIds);
            jpql = jpql + " AND e.userType.id IN ( :desiredTypesIds )";
        }
        jpql = jpql + " ORDER BY e.nameAr";
        return this.executeRetriveJPQLWithNamedParameters(jpql, params, CommonEmployee.class);
    }

    public List<CommonEmployee> getEmployees(int siteId, Enums.USER_TYPE type, boolean includeNotActive) {
        return this.getEmployees(siteId, new Enums.USER_TYPE[]{type}, 0, 0, includeNotActive);
    }

    public List<CommonEmployee> getEmployees(int siteId, Enums.USER_TYPE[] desiredTypes, boolean includeNotActive) {
        return this.getEmployees(siteId, desiredTypes, 0, 0, includeNotActive);
    }

    public List<CommonEmployee> getEmployees(int siteId, Enums.USER_TYPE type, int gmpCategoryId, boolean includeNotActive) {
        return this.getEmployees(siteId, new Enums.USER_TYPE[]{type}, 0, gmpCategoryId, includeNotActive);
    }

    public List<CommonEmployee> getEmployees(int siteId, Enums.USER_TYPE[] desiredTypes, int gmpCategoryId, boolean includeNotActive) {
        return this.getEmployees(siteId, desiredTypes, 0, gmpCategoryId, includeNotActive);
    }

    public List<CommonEmployee> getEmployees(int siteId, int hospDepId, Enums.USER_TYPE type, boolean includeNotActive) {
        return this.getEmployees(siteId, new Enums.USER_TYPE[]{type}, hospDepId, 0, includeNotActive);
    }

    public List<CommonEmployee> getEmployees(int siteId, int hospDepId, Enums.USER_TYPE[] desiredTypes, boolean includeNotActive) {
        return this.getEmployees(siteId, desiredTypes, hospDepId, 0, includeNotActive);
    }

    public List<CommonEmployee> getSiteActiveEmployees(int sessionSiteId, Enums.USER_ALLOWED_APP_TYPE appType) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", sessionSiteId);
        map.put("appTypeId", appType.getId());
        return this.executeNamedQuery("CommonSiteEmployee.findActiveEmployeesOfSite", map, CommonEmployee.class);
    }

    public List<CommonSite> getActiveSitesOfDirectorate(int directorateId, Enums.USER_ALLOWED_APP_TYPE appType, String langCode) {
        List<CommonSite> list = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("directorateId", directorateId);
        if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            list = this.executeNamedQuery("CommonSite.findActiveBioSitesOfDirectorate", map, CommonSite.class);
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            list = this.executeNamedQuery("CommonSite.findActiveGenSitesOfDirectorate", map, CommonSite.class);
        }
        if (langCode == null) {
            return list;
        }
        return AppUtil.sortLocalizedEntityList(list, langCode);
    }

    public List<CommonSite> getActiveSitesOfDirectorate(int directorateId, Enums.USER_ALLOWED_APP_TYPE appType) {
        return this.getActiveSitesOfDirectorate(directorateId, appType, null);
    }

    public List<CommonEmployee> getActiveDirectorateSupervisors(int directorateId, Enums.USER_ALLOWED_APP_TYPE appType) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("directorateId", directorateId);
        if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            map.put("userTypeId", Enums.USER_TYPE.BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN.getId());
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            map.put("userTypeId", Enums.USER_TYPE.GENERAL_DIRECTORATE_ASSISTANT_ADMIN.getId());
        }
        return this.executeNamedQuery("CommonEmployee.findActiveDirectorateSupervisors", map, CommonEmployee.class);
    }

    public List<CommonEmployee> getDirectorateEmployess(int directorateId, Enums.USER_TYPE userType, boolean includeNotActive) {
        String jpql = "SELECT s FROM CommonEmployee s WHERE s.directorate.id=" + directorateId;
        jpql = jpql + " AND s.userType.id= " + userType.getId();
        if (!includeNotActive) {
            jpql = jpql + " AND s.status = true";
        }
        jpql = jpql + " ORDER BY s.nameAr";
        return this.queryJPQL(jpql, null, CommonEmployee.class);
    }

    public Set<Integer> getDirectorateActiveSitesIds(int directorateId, Enums.USER_ALLOWED_APP_TYPE appType) {
        String jpql = "SELECT s.id FROM CommonSite s WHERE s.directorate.id=" + directorateId + " AND s.status=true";
        if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            jpql = jpql + " AND s.genOperatingCompany.id IS NOT NULL ";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            jpql = jpql + " AND s.bioOperatingCompany.id IS NOT NULL ";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) {
            jpql = jpql + " AND (s.bioOperatingCompany.id IS NOT NULL OR s.genOperatingCompany.id IS NOT NULL)";
        }
        jpql = jpql + " ORDER BY s.id";
        return new HashSet<Integer>(this.queryJPQL(jpql, null, Integer.class));
    }

    public Set<Integer> getDirectorateActiveSitesIds(int directorateId) {
        return this.getDirectorateActiveSitesIds(directorateId, null);
    }

    public List<CommonUser> getSiteActiveUsers(int sessionSiteId, Enums.USER_ALLOWED_APP_TYPE appType) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", sessionSiteId);
        map.put("appTypeId", appType.getId());
        return this.executeNamedQuery("CommonUser.findAllActiveBySite", map, CommonUser.class);
    }

    public Long getSiteActiveUsersCount(int sessionSiteId, Enums.USER_ALLOWED_APP_TYPE appType, Enums.OPERATING_COMPANY_LABOR_CLASSIFICATION operatingCompanyLaborClassification) {
        String jpql = "SELECT count(s.id) FROM CommonSiteEmployee s WHERE s.site.id=" + sessionSiteId + " AND s.status=true";
        if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            jpql = jpql + " AND s.site.genSiteContract.operatingCompany.id IS NOT NULL ";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            jpql = jpql + " AND s.site.bioSiteContract.operatingCompany.id IS NOT NULL ";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) {
            jpql = jpql + " AND (s.site.bioSiteContract.operatingCompany.id IS NOT NULL OR s.site.genSiteContract.operatingCompany.id IS NOT NULL)";
        }
        if (operatingCompanyLaborClassification != null) {
            if (operatingCompanyLaborClassification == Enums.OPERATING_COMPANY_LABOR_CLASSIFICATION.MO3TAMD) {
                jpql = jpql + " AND s.employee.approvalScan IS NOT NULL ";
            } else if (operatingCompanyLaborClassification == Enums.OPERATING_COMPANY_LABOR_CLASSIFICATION.NON_MO3TAMED) {
                jpql = jpql + " AND s.employee.approvalScan IS NULL ";
            }
        }
        return this.executeRetriveSingleResultJPQLWithNamedParameters(jpql, null, Long.class);
    }

    public void updateSupervisorsOfDirectorateSites(int directorateId, Integer[] supervisorsIds, Enums.USER_ALLOWED_APP_TYPE appType, String langCode) {
        List<CommonSite> dirSites = this.getActiveSitesOfDirectorate(directorateId, appType);
        if (dirSites.size() != supervisorsIds.length) {
            throw new IllegalArgumentException("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY");
        }
        for (int i = 0; i < dirSites.size(); ++i) {
            if (supervisorsIds[i] == null) continue;
            CommonSiteEmployee se = new CommonSiteEmployee();
            se.setSite(dirSites.get(i));
            se.setEmployee(this.getReference(supervisorsIds[i], CommonEmployee.class));
            se.setStatus(true);
            this.persistEntity(se);
        }
    }

    public List<CommonEmployee> getOnsiteOperatingCompanyLabors(int siteId, Enums.USER_ALLOWED_APP_TYPE appType, boolean excludeSiteManager) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        map.put("appTypeId", appType.getId());
        Integer[] excludedUserTypeIds = new Integer[]{0};
        if (excludeSiteManager) {
            if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                excludedUserTypeIds = new Integer[]{Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()};
            } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
                excludedUserTypeIds = new Integer[]{Enums.USER_TYPE.GENERAL_SITE_MANGER.getId(), Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER.getId()};
            }
        }
        map.put("excludedUserTypeIds", Arrays.asList(excludedUserTypeIds));
        return this.executeNamedQuery("CommonSiteEmployee.getOnsiteOperatingCompanyLaborers", map, CommonEmployee.class);
    }

    public List<CommonLookupUserType> getOnsiteOperatingCompanyUserTypes(Enums.USER_ALLOWED_APP_TYPE appType, boolean excludeSiteManager) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("appTypeId", appType.getId());
        Integer[] excludedUserTypeIds = new Integer[]{0};
        if (excludeSiteManager) {
            if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                excludedUserTypeIds = new Integer[]{Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()};
            } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
                excludedUserTypeIds = new Integer[]{Enums.USER_TYPE.GENERAL_SITE_MANGER.getId(), Enums.USER_TYPE.GENERAL_SPECIALIST_SITE_MANGER.getId()};
            }
        }
        map.put("excludedUserTypeIds", Arrays.asList(excludedUserTypeIds));
        return this.executeNamedQuery("CommonLookupUserType.getOnsiteOperatingCompanyUserTypes", map, CommonLookupUserType.class);
    }

    public CommonEmployee checkEmployeeNoDuplication(int siteId, String empNo, int excludedEmpId, Enums.USER_ALLOWED_APP_TYPE appType) {
        if (empNo == null || empNo.isEmpty()) {
            return null;
        }
        String jpql = "select cse.employee from CommonSiteEmployee cse where cse.site.id = ? and (cse.employee.employeeNo = ?) and (cse.employee.userType.allowedApps = ?) and cse.employee.id != ?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, siteId);
        map.put(2, empNo);
        map.put(3, appType.getId());
        map.put(4, excludedEmpId);
        List<CommonEmployee> list = this.queryJPQL(jpql, map, CommonEmployee.class);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<CommonHospitalLocation> getHospitalLocations(int hospId, String langCode) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("hospId", hospId);
        List<CommonHospitalLocation> list = this.executeNamedQuery("CommonHospitalLocation.findHospitalLocations", map, CommonHospitalLocation.class);
        if (langCode == null) {
            return list;
        }
        return AppUtil.sortLocalizedEntityList(list, langCode);
    }

    public List<CommonHospitalLocation> getHospitalLocations(int hospId) {
        return this.getHospitalLocations(hospId, null);
    }

    public List<CommonHospitalLocation> getSiteLocations(int siteId, String langCode) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("siteId", siteId);
        return AppUtil.sortLocalizedEntityList(this.executeNamedQuery("CommonHospitalLocation.findSiteLcations", map, CommonHospitalLocation.class), langCode);
    }

    public void deactivateEmployee(int siteId, int empId) {
        String sql = "UPDATE common_employee emp INNER JOIN common_site_employee cse ON emp.id = cse.employee_id SET emp.status = 0, cse.status=0 WHERE emp.id=? AND cse.site_id=?";
        HashMap<Integer, Object> map = new HashMap<Integer, Object>();
        map.put(1, empId);
        map.put(2, siteId);
        this.executeNativeQueryForUpdate(sql, map);
    }

    public List<CommonLookupUserType> getOnsiteOperatingCompanyLaborUserTypes(Enums.USER_ALLOWED_APP_TYPE appType, Enums.USER_TYPE[] excludedUserTypes) {
        String jpql = "SELECT u FROM CommonLookupUserType u WHERE u.status=true AND u.operatingCompanyLaborer = true";
        if (appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            jpql = jpql + " AND u.allowedApps IN (1,3)";
        } else if (appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            jpql = jpql + " AND u.allowedApps IN (2,3)";
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        if (excludedUserTypes != null && excludedUserTypes.length > 0) {
            ArrayList<Integer> excludedUserTypeIds = new ArrayList<Integer>();
            for (Enums.USER_TYPE userType : excludedUserTypes) {
                excludedUserTypeIds.add(userType.getId());
            }
            params.put("excludedUserTypeIds", excludedUserTypeIds);
            jpql = jpql + "  AND u.id NOT IN ( :excludedUserTypeIds )";
        }
        jpql = jpql + " ORDER BY u.displayOrder";
        return this.executeRetriveJPQLWithNamedParameters(jpql, params, CommonLookupUserType.class);
    }

    public List<CommonLookupUserType> getOnsiteOperatingCompanyLaborUserTypes(Enums.USER_ALLOWED_APP_TYPE appType, Enums.USER_TYPE excludedUserType) {
        return this.getOnsiteOperatingCompanyLaborUserTypes(appType, new Enums.USER_TYPE[]{excludedUserType});
    }

    public boolean userNameDuplicated(String userNameToBeChecked) {
        String jpql = "SELECT count(*) from CommonUser c where c.userName = '" + userNameToBeChecked + "'";
        return this.getJPQLSingleResult(jpql, null, Long.class) == 1L;
    }

    public void updateUsername(CommonUser currentUser, String newUserName) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("newusername", newUserName);
        map.put("oldusername", currentUser.getUserName());
        this.executeUpdateNamedQuery("CommonUserRole.updateUsername", map);
        currentUser.setUserName(newUserName);
        currentUser.setUsernameChangeCount(currentUser.getUserNameChangeCount() + 1);
        currentUser.setLastUsernameChangeDate(new Date());
        this.mergeEntity(currentUser);
    }
}
