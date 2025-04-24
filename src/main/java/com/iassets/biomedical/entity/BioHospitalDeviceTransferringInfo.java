/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.entity.AbstractHospitalDeviceTransferringInfo;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bio_hospital_device_transferring_info")
public class BioHospitalDeviceTransferringInfo
extends AbstractHospitalDeviceTransferringInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private BioHospitalDevice hospitalDevice;

    public BioHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(BioHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }
}
