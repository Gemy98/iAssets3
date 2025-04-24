/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Entity
 *  javax.persistence.FetchType
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedNativeQuery
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  org.hibernate.annotations.LazyCollection
 *  org.hibernate.annotations.LazyCollectionOption
 *  org.hibernate.annotations.Where
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioEndUserMaintenanceRequest;
import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.biomedical.entity.BioJobOrderSparePart;
import com.iassets.biomedical.entity.BioLookupJobOrderPriority;
import com.iassets.biomedical.entity.BioLookupJobOrderType;
import com.iassets.common.entity.AbstractJobOrder;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

@Entity
@Table(name="bio_job_order")
@NamedQuery(name="BioJobOrder.findAll", query="SELECT j FROM BioJobOrder j")
@NamedNativeQuery(name="BioJobOrder.getMeasurement", query="SELECT name_literal_key ,get_JO_measure_value(id,1,?1,?2) as measureValue, get_JO_measure_value(id,0,?3,?4) as complementary FROM iassets_current.common_site s where directorate_id=?5")
public class BioJobOrder
extends AbstractJobOrder
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="site_id")
    protected CommonSite site;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hosp_id")
    protected CommonHospital hospital;
    @ManyToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="device_id")
    private BioHospitalDevice hospitalDevice;
    @OneToOne
    @JoinColumn(name="maintenance_request_id")
    private BioEndUserMaintenanceRequest maintenanceRequest;
    @ManyToOne
    @JoinColumn(name="type_id")
    private BioLookupJobOrderType jobOrderType;
    @ManyToOne
    @JoinColumn(name="priority_id")
    private BioLookupJobOrderPriority jobOrderPriority;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation is null")
    private List<BioJobOrderSparePart> jobOrderSpareParts;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation = 1")
    private List<BioJobOrderSparePart> sparePartsInsideQuotation;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation = 0")
    private List<BioJobOrderSparePart> sparePartsOutsideQuotation;

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public void setHospital(CommonHospital hospital) {
        this.hospital = hospital;
    }

    public BioHospitalDevice getHospitalDevice() {
        return this.hospitalDevice;
    }

    public void setHospitalDevice(BioHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public BioEndUserMaintenanceRequest getMaintenanceRequest() {
        return this.maintenanceRequest;
    }

    public void setMaintenanceRequest(BioEndUserMaintenanceRequest maintenanceRequest) {
        this.maintenanceRequest = maintenanceRequest;
    }

    public BioLookupJobOrderType getJobOrderType() {
        return this.jobOrderType;
    }

    public void setJobOrderType(BioLookupJobOrderType jobOrderType) {
        this.jobOrderType = jobOrderType;
    }

    public BioLookupJobOrderPriority getJobOrderPriority() {
        return this.jobOrderPriority;
    }

    public void setJobOrderPriority(BioLookupJobOrderPriority jobOrderPriority) {
        this.jobOrderPriority = jobOrderPriority;
    }

    public List<BioJobOrderSparePart> getJobOrderSpareParts() {
        return this.jobOrderSpareParts;
    }

    public void setJobOrderSpareParts(List<BioJobOrderSparePart> jobOrderSpareParts) {
        this.jobOrderSpareParts = jobOrderSpareParts;
    }

    public BioJobOrderSparePart addJobOrderSparePart(BioJobOrderSparePart jobOrderSparePart) {
        this.getJobOrderSpareParts().add(jobOrderSparePart);
        jobOrderSparePart.setJobOrder(this);
        return jobOrderSparePart;
    }

    public BioJobOrderSparePart removeJobOrderSparePart(BioJobOrderSparePart jobOrderSparePart) {
        this.getJobOrderSpareParts().remove(jobOrderSparePart);
        jobOrderSparePart.setJobOrder(null);
        return jobOrderSparePart;
    }

    public List<BioJobOrderSparePart> getSparePartsInsideQuotation() {
        return this.sparePartsInsideQuotation;
    }

    public void setSparePartsInsideQuotation(List<BioJobOrderSparePart> sparePartsInsideQuotation) {
        this.sparePartsInsideQuotation = sparePartsInsideQuotation;
    }

    public BioJobOrderSparePart addSparePartsInsideQuotation(BioJobOrderSparePart sparePartsInsideQuotation) {
        this.getSparePartsInsideQuotation().add(sparePartsInsideQuotation);
        sparePartsInsideQuotation.setJobOrder(this);
        return sparePartsInsideQuotation;
    }

    public BioJobOrderSparePart removeSparePartsInsideQuotation(BioJobOrderSparePart sparePartsInsideQuotation) {
        this.getSparePartsInsideQuotation().remove(sparePartsInsideQuotation);
        sparePartsInsideQuotation.setJobOrder(null);
        return sparePartsInsideQuotation;
    }

    public List<BioJobOrderSparePart> getSparePartsOutsideQuotation() {
        return this.sparePartsOutsideQuotation;
    }

    public void setSparePartsOutsideQuotation(List<BioJobOrderSparePart> sparePartsOutsideQuotation) {
        this.sparePartsOutsideQuotation = sparePartsOutsideQuotation;
    }

    public BioJobOrderSparePart addSparePartsOutsideQuotation(BioJobOrderSparePart sparePartsOutsideQuotation) {
        this.getSparePartsOutsideQuotation().add(sparePartsOutsideQuotation);
        sparePartsOutsideQuotation.setJobOrder(this);
        return sparePartsOutsideQuotation;
    }

    public BioJobOrderSparePart removeSparePartsOutsideQuotation(BioJobOrderSparePart sparePartsOutsideQuotation) {
        this.getSparePartsOutsideQuotation().remove(sparePartsOutsideQuotation);
        sparePartsOutsideQuotation.setJobOrder(null);
        return sparePartsOutsideQuotation;
    }

    public List<BioJobOrderSparePart> getSpareParts() {
        if (this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId() || this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId()) {
            if (this.finalAction != null && this.finalActionDate != null) {
                return this.jobOrderSpareParts;
            }
            return this.getDummySparePartsList();
        }
        if (this.fixIncludingSpareParts != null && !this.fixIncludingSpareParts.booleanValue()) {
            return null;
        }
        List<BioJobOrderSparePart> list = null;
        if (this.spSource.intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
            list = this.jobOrderSpareParts;
        } else if (this.sparePartsInsideQuotation != null && !this.sparePartsInsideQuotation.isEmpty()) {
            list = new ArrayList<BioJobOrderSparePart>(this.sparePartsInsideQuotation.size());
            list.addAll(this.sparePartsInsideQuotation);
            if (this.sparePartsOutsideQuotation != null && !this.sparePartsOutsideQuotation.isEmpty()) {
                list.addAll(this.sparePartsOutsideQuotation);
            }
        }
        return list == null || list.isEmpty() ? this.getDummySparePartsList() : list;
    }

    private List<BioJobOrderSparePart> getDummySparePartsList() {
        ArrayList<BioJobOrderSparePart> list = new ArrayList<BioJobOrderSparePart>(8);
        for (int i = 0; i < 7; ++i) {
            list.add(new BioJobOrderSparePart());
        }
        return list;
    }

    public String getCurrentPhaseName(String langCode) {
        String name = null;
        if (this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId()) {
            name = LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status1", langCode);
        } else if (this.fixIncludingSpareParts == null) {
            name = !this.agentMustAttend.booleanValue() ? LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status2", langCode) : LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status3", langCode);
        } else if (!this.fixIncludingSpareParts.booleanValue()) {
            name = LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status4", langCode);
        } else if (this.fixIncludingSpareParts.booleanValue()) {
            name = this.spSource.intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId() ? LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status5", langCode) : (this.quotationRecieved == null ? LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status6", langCode) : (this.quotationAccepted == null ? LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status7", langCode) : LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioJobOrder.currentPhaseName.status8", langCode)));
        }
        return name;
    }

    public float getSparePartsTotalCost() {
        List<BioJobOrderSparePart> list = null;
        if (this.fixIncludingSpareParts != null && this.fixIncludingSpareParts.booleanValue()) {
            if (this.spSource.intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
                list = this.jobOrderSpareParts;
            } else if (this.sparePartsInsideQuotation != null && !this.sparePartsInsideQuotation.isEmpty()) {
                list = this.sparePartsInsideQuotation;
                if (this.sparePartsOutsideQuotation != null && !this.sparePartsOutsideQuotation.isEmpty()) {
                    list.addAll(this.sparePartsOutsideQuotation);
                }
            }
        }
        float sum = 0.0f;
        if (list != null && !list.isEmpty()) {
            for (BioJobOrderSparePart p : list) {
                sum += p.getTotalPrice() != null ? p.getTotalPrice().floatValue() : 0.0f;
            }
        }
        return sum;
    }
}
