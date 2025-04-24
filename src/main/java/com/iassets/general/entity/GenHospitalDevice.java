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
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractHospitalDevice;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.general.entity.GenHospitalDeviceAccessory;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmSchedule;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name="gen_hospital_device")
@NamedEntityGraph(name="GenHospitalDevice.graph.fetchAll", attributeNodes={@NamedAttributeNode(value="hospitalDeviceAccessories")})
@NamedQuery(name="GenHospitalDevice.findAll", query="SELECT h FROM GenHospitalDevice h ORDER BY h.name")
public class GenHospitalDevice
extends AbstractHospitalDevice
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="specs")
    private String specs;
    @OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="hospitalDevice", orphanRemoval=true)
    private List<GenHospitalDeviceAccessory> hospitalDeviceAccessories;
    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="hospitalDevice", orphanRemoval=true)
    private List<GenHospitalDeviceInternalPpmSchedule> hospitalDeviceInternalPpmSchedules;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lookup_cat_id", insertable=false, updatable=false)
    protected GenLookupJobOrderCategory lookupCategory;
    @Column(name="lookup_cat_id")
    protected Integer lookupCategoryId;
    @Column(name="spec_HP")
    protected Float specHP;
    @Column(name="spec_RPM")
    protected Float specRPM;
    @Column(name="spec_P")
    protected Float specP;
    @Column(name="spec_T")
    protected Float specT;
    @Column(name="spec_H")
    protected Float specH;
    @Column(name="spec_KG")
    protected Float specKG;
    @Column(name="spec_Q")
    protected Float specQ;
    @Column(name="spec_PH")
    protected Float specPH;
    @Column(name="spec_IP")
    protected Float specIP;
    @Column(name="spec_A")
    protected Float specA;
    @Column(name="spec_V")
    protected Float specV;
    @Column(name="spec_KW")
    protected Float specKW;

    public String getSpecs() {
        return this.specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public List<GenHospitalDeviceInternalPpmSchedule> getHospitalDeviceInternalPpmSchedules() {
        return this.hospitalDeviceInternalPpmSchedules;
    }

    public void setHospitalDeviceInternalPpmSchedules(List<GenHospitalDeviceInternalPpmSchedule> hospitalDeviceInternalPpmSchedules) {
        this.hospitalDeviceInternalPpmSchedules = hospitalDeviceInternalPpmSchedules;
    }

    public List<GenHospitalDeviceAccessory> getHospitalDeviceAccessories() {
        return this.hospitalDeviceAccessories;
    }

    public void setHospitalDeviceAccessories(List<GenHospitalDeviceAccessory> hospitalDeviceAccessories) {
        this.hospitalDeviceAccessories = hospitalDeviceAccessories;
    }

    public GenHospitalDeviceAccessory addHospitalDeviceAccessory(GenHospitalDeviceAccessory hospitalDeviceAccessory) {
        this.getHospitalDeviceAccessories().add(hospitalDeviceAccessory);
        hospitalDeviceAccessory.setHospitalDevice(this);
        return hospitalDeviceAccessory;
    }

    public GenHospitalDeviceAccessory removeHospitalDeviceAccessory(GenHospitalDeviceAccessory hospitalDeviceAccessory) {
        this.getHospitalDeviceAccessories().remove(hospitalDeviceAccessory);
        hospitalDeviceAccessory.setHospitalDevice(null);
        return hospitalDeviceAccessory;
    }

    public void addHospitalDeviceInternalPpmSchedules(GenHospitalDeviceInternalPpmSchedule hospitalDeviceInternalPpmSchedules) {
        if (this.getHospitalDeviceInternalPpmSchedules() == null) {
            this.setHospitalDeviceInternalPpmSchedules(new ArrayList<GenHospitalDeviceInternalPpmSchedule>());
        }
        this.getHospitalDeviceInternalPpmSchedules().add(hospitalDeviceInternalPpmSchedules);
        hospitalDeviceInternalPpmSchedules.setHospitalDevice(this);
    }

    @Override
    public CommonHospitalLocation getHospitalLocation() {
        return this.hospitalLocation;
    }

    @Override
    public void setHospitalLocation(CommonHospitalLocation hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public GenLookupJobOrderCategory getLookupCategory() {
        return this.lookupCategory;
    }

    public void setLookupCategory(GenLookupJobOrderCategory lookupCategory) {
        this.lookupCategory = lookupCategory;
    }

    public Integer getLookupCategoryId() {
        return this.lookupCategoryId;
    }

    public void setLookupCategoryId(Integer lookupCategoryId) {
        this.lookupCategoryId = lookupCategoryId;
    }

    public Float getSpecHP() {
        return this.specHP;
    }

    public void setSpecHP(Float specsHP) {
        this.specHP = specsHP;
    }

    public Float getSpecRPM() {
        return this.specRPM;
    }

    public void setSpecRPM(Float specsRPM) {
        this.specRPM = specsRPM;
    }

    public Float getSpecP() {
        return this.specP;
    }

    public void setSpecP(Float specsP) {
        this.specP = specsP;
    }

    public Float getSpecT() {
        return this.specT;
    }

    public void setSpecT(Float specsT) {
        this.specT = specsT;
    }

    public Float getSpecH() {
        return this.specH;
    }

    public void setSpecH(Float specsH) {
        this.specH = specsH;
    }

    public Float getSpecKG() {
        return this.specKG;
    }

    public void setSpecKG(Float specsKG) {
        this.specKG = specsKG;
    }

    public Float getSpecQ() {
        return this.specQ;
    }

    public void setSpecQ(Float specsQ) {
        this.specQ = specsQ;
    }

    public Float getSpecPH() {
        return this.specPH;
    }

    public void setSpecPH(Float specsPH) {
        this.specPH = specsPH;
    }

    public Float getSpecIP() {
        return this.specIP;
    }

    public void setSpecIP(Float specsIP) {
        this.specIP = specsIP;
    }

    public Float getSpecA() {
        return this.specA;
    }

    public void setSpecA(Float specsA) {
        this.specA = specsA;
    }

    public Float getSpecV() {
        return this.specV;
    }

    public void setSpecV(Float specsV) {
        this.specV = specsV;
    }

    public Float getSpecKW() {
        return this.specKW;
    }

    public void setSpecKW(Float specsKW) {
        this.specKW = specsKW;
    }
}
