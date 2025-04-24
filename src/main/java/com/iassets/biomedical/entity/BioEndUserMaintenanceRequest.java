/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.entity.AbstractEndUserMaintenanceRequest;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="bio_end_user_maintenance_request")
@NamedQueries(value={@NamedQuery(name="BioEndUserMaintenanceRequest.findAll", query="SELECT b FROM BioEndUserMaintenanceRequest b"), @NamedQuery(name="BioEndUserMaintenanceRequest.findOpenMaintenanceRequests", query="SELECT b FROM BioEndUserMaintenanceRequest b where b.site.id=:siteId and b.fake=false and b.handled=false"), @NamedQuery(name="BioEndUserMaintenanceRequest.findOpenMaintenanceRequestsCount", query="SELECT count(b) FROM BioEndUserMaintenanceRequest b where b.site.id=:siteId and b.fake=false and b.handled=false"), @NamedQuery(name="BioEndUserMaintenanceRequest.findOpenMaintenanceRequestByDeviceId", query="SELECT b FROM BioEndUserMaintenanceRequest b where b.site.id=:siteId and b.hospitalDevice.id = :deviceId and b.fake=false and b.handled=false")})
public class BioEndUserMaintenanceRequest
extends AbstractEndUserMaintenanceRequest
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private BioHospitalDevice hospitalDevice;

    public BioHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(BioHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }
}
