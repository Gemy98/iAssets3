/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractHospitalDeviceAccessory;
import com.iassets.general.entity.GenHospitalDevice;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gen_hospital_device_accessory")
@NamedQuery(name="GenHospitalDeviceAccessory.findAll", query="SELECT h FROM GenHospitalDeviceAccessory h")
public class GenHospitalDeviceAccessory
extends AbstractHospitalDeviceAccessory
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="device_id")
    private GenHospitalDevice hospitalDevice;

    public GenHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }
}
