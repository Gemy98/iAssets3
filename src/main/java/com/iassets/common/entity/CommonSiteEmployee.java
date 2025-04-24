/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonSite;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="common_site_employee")
@NamedQueries(value={@NamedQuery(name="CommonSiteEmployee.findActiveEmployeesOfSite", query="SELECT s.employee FROM CommonSiteEmployee s WHERE s.status=true AND s.employee.status=true AND s.site.id=:siteId AND s.employee.userType.allowedApps IN (:appTypeId,3) ORDER BY s.employee.userType.displayOrder, s.employee.nameAr"), @NamedQuery(name="CommonSiteEmployee.getOnsiteOperatingCompanyLaborers", query="SELECT cse.employee FROM CommonSiteEmployee cse WHERE cse.site.id=:siteId AND cse.status = true AND cse.employee.status = true AND cse.employee.userType.operatingCompanyLaborer = true AND cse.employee.userType.allowedApps=:appTypeId AND cse.employee.userType.id NOT IN (:excludedUserTypeIds) ORDER BY cse.employee.userType.displayOrder, cse.employee.nameAr"), @NamedQuery(name="CommonSiteEmployee.findSupervisorsOfDirectorateSites", query="SELECT s FROM CommonSiteEmployee s WHERE s.site.directorate.id=:directorateId AND s.employee.userType.id=:userTypeId ORDER BY s.employee.nameAr"), @NamedQuery(name="CommonSiteEmployee.findByEmployeeId", query="SELECT s FROM CommonSiteEmployee s WHERE s.employee.id=:employeeId"), @NamedQuery(name="CommonSiteEmployee.findEmployeeById", query="SELECT s.employee FROM CommonSiteEmployee s WHERE s.employee.id=:employeeId AND s.site.id=:siteId AND s.status=true")})
public class CommonSiteEmployee
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="site_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private CommonSite site;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private CommonEmployee employee;
    private Boolean status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public CommonEmployee getEmployee() {
        return this.employee;
    }

    public void setEmployee(CommonEmployee employee) {
        this.employee = employee;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
