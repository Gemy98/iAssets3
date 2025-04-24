/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  org.hibernate.annotations.Where
 */
package com.iassets.common.entity;

import com.iassets.common.entity.AbstractLocalizedEntity;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalLocation;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Cacheable
@Entity
@Table(name="common_hospital_department")
@NamedQuery(name="CommonHospitalDepartment.findAll", query="SELECT h FROM CommonHospitalDepartment h WHERE h.status=true")
public class CommonHospitalDepartment
extends AbstractLocalizedEntity
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="hosp_id")
    private CommonHospital hospital;
    @ManyToOne
    @JoinColumn(name="location_id")
    private CommonHospitalLocation location;
    @OneToOne(mappedBy="department")
    @Where(clause="user_type=5 and status=1")
    private CommonEmployee departmentHead;
    @Column(name="allowed_apps")
    private Integer allowedApps;

    public CommonHospital getHospital() {
        return this.hospital;
    }

    public void setHospital(CommonHospital hospital) {
        this.hospital = hospital;
    }

    public CommonHospitalLocation getLocation() {
        return this.location;
    }

    public void setLocation(CommonHospitalLocation location) {
        this.location = location;
    }

    public CommonEmployee getDepartmentHead() {
        return this.departmentHead;
    }

    public void setDepartmentHead(CommonEmployee departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Integer getAllowedApps() {
        return this.allowedApps;
    }

    public void setAllowedApps(Integer allowedApps) {
        this.allowedApps = allowedApps;
    }
}
