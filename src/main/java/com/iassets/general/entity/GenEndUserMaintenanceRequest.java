/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractEndUserMaintenanceRequest;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.general.entity.GenHospitalDevice;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gen_end_user_maintenance_request")
@NamedQueries(value={@NamedQuery(name="GenEndUserMaintenanceRequest.findAll", query="SELECT b FROM GenEndUserMaintenanceRequest b"), @NamedQuery(name="GenEndUserMaintenanceRequest.findOpenMaintenanceRequests", query="SELECT b FROM GenEndUserMaintenanceRequest b where b.site.id=:siteId and b.fake=false and b.handled=false"), @NamedQuery(name="GenEndUserMaintenanceRequest.findOpenMaintenanceRequestByDeviceId", query="SELECT b FROM GenEndUserMaintenanceRequest b where b.site.id=:siteId and b.hospitalDevice.id = :deviceId and b.fake=false and b.handled=false")})
public class GenEndUserMaintenanceRequest
extends AbstractEndUserMaintenanceRequest
implements Serializable {
    private static final long serialVersionUID = -3451580745359409283L;
    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private GenHospitalDevice hospitalDevice;
    @Column(name="uncoded_device")
    private boolean uncodedDevice;
    @Column(name="uncoded_device_name")
    private String uncodedDeviceName;
    @ManyToOne
    @JoinColumn(name="uncoded_device_location_id")
    private CommonHospitalLocation uncodedDeviceLocation;
    @ManyToOne
    @JoinColumn(name="uncoded_device_dep_id")
    private CommonHospitalDepartment uncodedDeviceDepartment;
    @Column(name="uncoded_device_room")
    private String uncodedDeviceRoom;
    @Column(name="uncoded_device_location_description")
    private String uncodedDeviceLocationDescription;

    public boolean isUncodedDevice() {
        return this.uncodedDevice;
    }

    public void setUncodedDevice(boolean uncodedDevice) {
        this.uncodedDevice = uncodedDevice;
    }

    public String getUncodedDeviceName() {
        return this.uncodedDeviceName;
    }

    public void setUncodedDeviceName(String uncodedDeviceName) {
        this.uncodedDeviceName = uncodedDeviceName;
    }

    public CommonHospitalLocation getUncodedDeviceLocation() {
        return this.uncodedDeviceLocation;
    }

    public void setUncodedDeviceLocation(CommonHospitalLocation uncodedDeviceLocation) {
        this.uncodedDeviceLocation = uncodedDeviceLocation;
    }

    public CommonHospitalDepartment getUncodedDeviceDepartment() {
        return this.uncodedDeviceDepartment;
    }

    public void setUncodedDeviceDepartment(CommonHospitalDepartment uncodedDeviceDepartment) {
        this.uncodedDeviceDepartment = uncodedDeviceDepartment;
    }

    public String getUncodedDeviceRoom() {
        return this.uncodedDeviceRoom;
    }

    public void setUncodedDeviceRoom(String uncodedDeviceRoom) {
        this.uncodedDeviceRoom = uncodedDeviceRoom;
    }

    public String getUncodedDeviceLocationDescription() {
        return this.uncodedDeviceLocationDescription;
    }

    public void setUncodedDeviceLocationDescription(String uncodedDeviceLocationDescription) {
        this.uncodedDeviceLocationDescription = uncodedDeviceLocationDescription;
    }

    public GenHospitalDevice getHospitalDevice() {
        if (this.hospitalDevice != null && this.hospitalDevice.getId() == 1) {
            this.hospitalDevice.setSite(this.getSite());
            this.hospitalDevice.setName(this.getUncodedDeviceName());
            this.hospitalDevice.setHospitalLocation(this.getUncodedDeviceLocation());
            this.hospitalDevice.setHospitalDepartment(this.getUncodedDeviceDepartment());
            this.hospitalDevice.setHospitalRoom(this.getUncodedDeviceRoom());
            this.hospitalDevice.setHospitalLocationDescription(this.getUncodedDeviceLocationDescription());
        }
        return this.hospitalDevice;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }
}
