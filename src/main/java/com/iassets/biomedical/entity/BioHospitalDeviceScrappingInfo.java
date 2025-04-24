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
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioLookupScrappingReason;
import com.iassets.common.entity.AbstractHospitalDeviceScrappingInfo;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="bio_hospital_device_scrapping_info")
@NamedQueries(value={@NamedQuery(name="BioHospitalDeviceScrappingInfo.findAll", query="SELECT h FROM BioHospitalDeviceScrappingInfo h"), @NamedQuery(name="BioHospitalDeviceScrappingInfo.findByDevice", query="SELECT h FROM BioHospitalDeviceScrappingInfo h WHERE h.hospitalDevice.id=:deviceId")})
public class BioHospitalDeviceScrappingInfo
extends AbstractHospitalDeviceScrappingInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private BioHospitalDevice hospitalDevice;
    @ManyToOne
    @JoinColumn(name="scrapping_reason_id")
    private BioLookupScrappingReason scrappingReason;

    public BioHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(BioHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public BioLookupScrappingReason getScrappingReason() {
        return this.scrappingReason;
    }

    public void setScrappingReason(BioLookupScrappingReason scrappingReason) {
        this.scrappingReason = scrappingReason;
    }
}
