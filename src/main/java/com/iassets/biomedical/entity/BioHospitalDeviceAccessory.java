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
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.entity.AbstractHospitalDeviceAccessory;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="bio_hospital_device_accessory")
@NamedQuery(name="BioHospitalDeviceAccessory.findAll", query="SELECT h FROM BioHospitalDeviceAccessory h")
public class BioHospitalDeviceAccessory
extends AbstractHospitalDeviceAccessory
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="device_id")
    private BioHospitalDevice hospitalDevice;

    public BioHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(BioHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }
}
