/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedAttributeNode
 *  javax.persistence.NamedEntityGraph
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.Table
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioHospitalDeviceAccessory;
import com.iassets.biomedical.entity.BioLookupDeviceCategory;
import com.iassets.biomedical.entity.BioLookupDeviceFunctionalClassification;
import com.iassets.common.entity.AbstractHospitalDevice;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bio_hospital_device")
@NamedEntityGraph(name="BioHospitalDevice.graph.fetchAll", attributeNodes={@NamedAttributeNode(value="hospitalDeviceAccessories")})
@NamedQuery(name="BioHospitalDevice.findAll", query="SELECT h FROM BioHospitalDevice h ORDER BY h.name")
public class BioHospitalDevice
extends AbstractHospitalDevice
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="device_picture")
    private String devicePicture;
    @ManyToOne
    @JoinColumn(name="functional_classification_id")
    private BioLookupDeviceFunctionalClassification functionalClassification;
    @ManyToOne
    @JoinColumn(name="category_id")
    private BioLookupDeviceCategory category;
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="hospitalDevice", orphanRemoval=true)
    private List<BioHospitalDeviceAccessory> hospitalDeviceAccessories;
    @Column(name="Floor")
    private String floor;

    public BioLookupDeviceCategory getCategory() {
        return this.category;
    }

    public void setCategory(BioLookupDeviceCategory category) {
        this.category = category;
    }

    public List<BioHospitalDeviceAccessory> getHospitalDeviceAccessories() {
        return this.hospitalDeviceAccessories;
    }

    public void setHospitalDeviceAccessories(List<BioHospitalDeviceAccessory> hospitalDeviceAccessories) {
        this.hospitalDeviceAccessories = hospitalDeviceAccessories;
    }

    public BioHospitalDeviceAccessory addHospitalDeviceAccessory(BioHospitalDeviceAccessory hospitalDeviceAccessory) {
        this.getHospitalDeviceAccessories().add(hospitalDeviceAccessory);
        hospitalDeviceAccessory.setHospitalDevice(this);
        return hospitalDeviceAccessory;
    }

    public BioHospitalDeviceAccessory removeHospitalDeviceAccessory(BioHospitalDeviceAccessory hospitalDeviceAccessory) {
        this.getHospitalDeviceAccessories().remove(hospitalDeviceAccessory);
        hospitalDeviceAccessory.setHospitalDevice(null);
        return hospitalDeviceAccessory;
    }

    public String getFloor() {
        return this.floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public BioLookupDeviceFunctionalClassification getFunctionalClassification() {
        return this.functionalClassification;
    }

    public void setFunctionalClassification(BioLookupDeviceFunctionalClassification classification) {
        this.functionalClassification = classification;
    }

    public String getDevicePicture() {
        return this.devicePicture;
    }

    public void setDevicePicture(String devicePic) {
        this.devicePicture = devicePic;
    }
}
