/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractEndUserMaintenanceRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;
    @ManyToOne
    @JoinColumn(name="hosp_dep_id")
    private CommonHospitalDepartment hospitalDepartment;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="damage_date")
    private Date damageDate;
    @Column(name="damage_description")
    private String damageDescription;
    @Column(name="end_user_name")
    private String endUserName;
    private boolean fake;
    private boolean handled;
    @ManyToOne
    @JoinColumn(name="requested_by")
    private CommonUser requestedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="requested_in")
    private Date requestedIn;
    @ManyToOne
    @JoinColumn(name="handled_by")
    private CommonUser handledBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="handled_in")
    private Date handledIn;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public CommonHospitalDepartment getHospitalDepartment() {
        return this.hospitalDepartment;
    }

    public void setHospitalDepartment(CommonHospitalDepartment hospitalDepartment) {
        this.hospitalDepartment = hospitalDepartment;
    }

    public Date getDamageDate() {
        return this.damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
    }

    public String getDamageDescription() {
        return this.damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public String getEndUserName() {
        return this.endUserName;
    }

    public void setEndUserName(String endUserName) {
        this.endUserName = endUserName;
    }

    public boolean isFake() {
        return this.fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public boolean isHandled() {
        return this.handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public CommonUser getRequestedBy() {
        return this.requestedBy;
    }

    public void setRequestedBy(CommonUser requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getRequestedIn() {
        return this.requestedIn;
    }

    public void setRequestedIn(Date requestedIn) {
        this.requestedIn = requestedIn;
    }

    public CommonUser getHandledBy() {
        return this.handledBy;
    }

    public void setHandledBy(CommonUser handledBy) {
        this.handledBy = handledBy;
    }

    public Date getHandledIn() {
        return this.handledIn;
    }

    public void setHandledIn(Date handledIn) {
        this.handledIn = handledIn;
    }
}
