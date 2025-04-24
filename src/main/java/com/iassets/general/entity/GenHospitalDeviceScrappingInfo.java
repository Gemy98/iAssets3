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
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractHospitalDeviceScrappingInfo;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenLookupScrappingReason;
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
@Table(name="gen_hospital_device_scrapping_info")
@NamedQueries(value={@NamedQuery(name="GenHospitalDeviceScrappingInfo.findAll", query="SELECT h FROM GenHospitalDeviceScrappingInfo h"), @NamedQuery(name="GenHospitalDeviceScrappingInfo.findByDevice", query="SELECT h FROM GenHospitalDeviceScrappingInfo h WHERE h.hospitalDevice.id=:deviceId")})
public class GenHospitalDeviceScrappingInfo
extends AbstractHospitalDeviceScrappingInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private GenHospitalDevice hospitalDevice;
    @ManyToOne
    @JoinColumn(name="scrapping_reason_id")
    private GenLookupScrappingReason scrappingReason;

    public GenHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public GenLookupScrappingReason getScrappingReason() {
        return this.scrappingReason;
    }

    public void setScrappingReason(GenLookupScrappingReason scrappingReason) {
        this.scrappingReason = scrappingReason;
    }
}
