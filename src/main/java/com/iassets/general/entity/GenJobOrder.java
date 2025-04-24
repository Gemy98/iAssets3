/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.CascadeType
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.OneToOne
 *  javax.persistence.Table
 *  org.hibernate.annotations.LazyCollection
 *  org.hibernate.annotations.LazyCollectionOption
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 *  org.hibernate.annotations.Where
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractJobOrder;
import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.util.Enums;
import com.iassets.general.entity.GenEndUserMaintenanceRequest;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenJobOrderSparePart;
import com.iassets.general.entity.GenLookupJobOrderCategory;
import com.iassets.general.entity.GenLookupJobOrderPriority;
import com.iassets.general.entity.GenLookupJobOrderSubcategory;
import com.iassets.general.entity.GenLookupJobOrderType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

@Entity
@Table(name="gen_job_order")
@NamedQuery(name="GenJobOrder.findAll", query="SELECT j FROM GenJobOrder j")
public class GenJobOrder
extends AbstractJobOrder
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="site_id")
    protected CommonSite site;
    @ManyToOne
    @JoinColumn(name="hosp_id")
    protected CommonHospital hospital;
    @ManyToOne
    @JoinColumn(name="device_id")
    private GenHospitalDevice hospitalDevice;
    @OneToOne(cascade={CascadeType.MERGE})
    @JoinColumn(name="maintenance_request_id")
    private GenEndUserMaintenanceRequest maintenanceRequest;
    @ManyToOne
    @JoinColumn(name="type_id")
    private GenLookupJobOrderType jobOrderType;
    @ManyToOne
    @JoinColumn(name="priority_id")
    private GenLookupJobOrderPriority jobOrderPriority;
    @ManyToOne
    @JoinColumn(name="gmp_dep_supervisor")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee gmpDepartmentSupervisor;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation is null")
    private List<GenJobOrderSparePart> jobOrderSpareParts;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation = 1")
    private List<GenJobOrderSparePart> sparePartsInsideQuotation;
    @LazyCollection(value=LazyCollectionOption.FALSE)
    @OneToMany(cascade={CascadeType.ALL}, mappedBy="jobOrder", orphanRemoval=true)
    @Where(clause="in_quotation = 0")
    private List<GenJobOrderSparePart> sparePartsOutsideQuotation;
    @ManyToOne
    @JoinColumn(name="category_id")
    private GenLookupJobOrderCategory category;
    @ManyToOne
    @JoinColumn(name="subcategory_id")
    private GenLookupJobOrderSubcategory subCategory;
    @Column(name="directed")
    private Boolean directed;
    @ManyToOne
    @JoinColumn(name="directed_from_category_id")
    private GenLookupJobOrderCategory directedFromCategory;
    @ManyToOne
    @JoinColumn(name="directed_from_subcategory_id")
    private GenLookupJobOrderSubcategory directedFromSubcategory;
    @Column(name="directing_count")
    private Integer directingCount;
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

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public void setHospital(CommonHospital hospital) {
        this.hospital = hospital;
    }

    public GenEndUserMaintenanceRequest getMaintenanceRequest() {
        return this.maintenanceRequest;
    }

    public void setMaintenanceRequest(GenEndUserMaintenanceRequest maintenanceRequest) {
        this.maintenanceRequest = maintenanceRequest;
    }

    public GenHospitalDevice getHospitalDevice() {
        if (this.hospitalDevice != null && this.hospitalDevice.getId() == 1) {
            this.hospitalDevice.setSite(this.site);
            this.hospitalDevice.setHospital(this.hospital);
            this.hospitalDevice.setName(this.getUncodedDeviceName());
            this.hospitalDevice.setHospitalLocation(this.getUncodedDeviceLocation());
            this.hospitalDevice.setHospitalDepartment(this.getUncodedDeviceDepartment());
            this.hospitalDevice.setHospitalRoom(this.getUncodedDeviceRoom());
            this.hospitalDevice.setHospitalLocationDescription(this.getUncodedDeviceLocationDescription());
        }
        return this.hospitalDevice;
    }

    public String getUncodedDeviceName() {
        return this.uncodedDeviceName;
    }

    public void setUncodedDeviceName(String deviceName) {
        this.uncodedDeviceName = deviceName;
    }

    public void setHospitalDevice(GenHospitalDevice hospitalDevice) {
        this.hospitalDevice = hospitalDevice;
    }

    public GenLookupJobOrderType getJobOrderType() {
        return this.jobOrderType;
    }

    public void setJobOrderType(GenLookupJobOrderType jobOrderType) {
        this.jobOrderType = jobOrderType;
    }

    public GenLookupJobOrderPriority getJobOrderPriority() {
        return this.jobOrderPriority;
    }

    public void setJobOrderPriority(GenLookupJobOrderPriority jobOrderPriority) {
        this.jobOrderPriority = jobOrderPriority;
    }

    public CommonEmployee getGmpDepartmentSupervisor() {
        return this.gmpDepartmentSupervisor;
    }

    public void setGmpDepartmentSupervisor(CommonEmployee gmpDepartmentSupervisor) {
        this.gmpDepartmentSupervisor = gmpDepartmentSupervisor;
    }

    public List<GenJobOrderSparePart> getJobOrderSpareParts() {
        return this.jobOrderSpareParts;
    }

    public void setJobOrderSpareParts(List<GenJobOrderSparePart> jobOrderSpareParts) {
        this.jobOrderSpareParts = jobOrderSpareParts;
    }

    public GenJobOrderSparePart addJobOrderSparePart(GenJobOrderSparePart jobOrderSparePart) {
        this.getJobOrderSpareParts().add(jobOrderSparePart);
        jobOrderSparePart.setJobOrder(this);
        return jobOrderSparePart;
    }

    public GenJobOrderSparePart removeJobOrderSparePart(GenJobOrderSparePart jobOrderSparePart) {
        this.getJobOrderSpareParts().remove(jobOrderSparePart);
        jobOrderSparePart.setJobOrder(null);
        return jobOrderSparePart;
    }

    public List<GenJobOrderSparePart> getSparePartsInsideQuotation() {
        return this.sparePartsInsideQuotation;
    }

    public void setSparePartsInsideQuotation(List<GenJobOrderSparePart> sparePartsInsideQuotation) {
        this.sparePartsInsideQuotation = sparePartsInsideQuotation;
    }

    public GenJobOrderSparePart addSparePartsInsideQuotation(GenJobOrderSparePart sparePartsInsideQuotation) {
        this.getSparePartsInsideQuotation().add(sparePartsInsideQuotation);
        sparePartsInsideQuotation.setJobOrder(this);
        return sparePartsInsideQuotation;
    }

    public GenJobOrderSparePart removeSparePartsInsideQuotation(GenJobOrderSparePart sparePartsInsideQuotation) {
        this.getSparePartsInsideQuotation().remove(sparePartsInsideQuotation);
        sparePartsInsideQuotation.setJobOrder(null);
        return sparePartsInsideQuotation;
    }

    public List<GenJobOrderSparePart> getSparePartsOutsideQuotation() {
        return this.sparePartsOutsideQuotation;
    }

    public void setSparePartsOutsideQuotation(List<GenJobOrderSparePart> sparePartsOutsideQuotation) {
        this.sparePartsOutsideQuotation = sparePartsOutsideQuotation;
    }

    public GenJobOrderSparePart addSparePartsOutsideQuotation(GenJobOrderSparePart sparePartsOutsideQuotation) {
        this.getSparePartsOutsideQuotation().add(sparePartsOutsideQuotation);
        sparePartsOutsideQuotation.setJobOrder(this);
        return sparePartsOutsideQuotation;
    }

    public GenJobOrderSparePart removeSparePartsOutsideQuotation(GenJobOrderSparePart sparePartsOutsideQuotation) {
        this.getSparePartsOutsideQuotation().remove(sparePartsOutsideQuotation);
        sparePartsOutsideQuotation.setJobOrder(null);
        return sparePartsOutsideQuotation;
    }

    public GenLookupJobOrderCategory getCategory() {
        return this.category;
    }

    public void setCategory(GenLookupJobOrderCategory category) {
        this.category = category;
    }

    public GenLookupJobOrderSubcategory getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(GenLookupJobOrderSubcategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getResponsibleDepartmentName(String langCode) {
        if (this.subCategory != null) {
            return this.subCategory.getParentCategory().getLocalizedName(langCode) + " - " + this.subCategory.getLocalizedName(langCode);
        }
        if (this.category != null) {
            return this.category.getLocalizedName(langCode);
        }
        return null;
    }

    public Boolean getDirected() {
        return this.directed;
    }

    public void setDirected(Boolean directed) {
        this.directed = directed;
    }

    public GenLookupJobOrderCategory getDirectedFromCategory() {
        return this.directedFromCategory;
    }

    public void setDirectedFromCategory(GenLookupJobOrderCategory directedFromCategory) {
        this.directedFromCategory = directedFromCategory;
    }

    public GenLookupJobOrderSubcategory getDirectedFromSubcategory() {
        return this.directedFromSubcategory;
    }

    public void setDirectedFromSubcategory(GenLookupJobOrderSubcategory directedFromSubcategory) {
        this.directedFromSubcategory = directedFromSubcategory;
    }

    public Integer getDirectingCount() {
        if (this.directingCount == null) {
            this.directingCount = 0;
        }
        return this.directingCount;
    }

    public void setDirectingCount(Integer directingCount) {
        this.directingCount = directingCount;
    }

    public List<GenJobOrderSparePart> getSpareParts() {
        if (this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId() || this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.FIRST_ACTION_TAKEN.getId()) {
            if (this.finalAction != null && this.finalActionDate != null) {
                return this.jobOrderSpareParts;
            }
            return this.getDummySparePartsList();
        }
        if (this.fixIncludingSpareParts != null && !this.fixIncludingSpareParts.booleanValue()) {
            return null;
        }
        List<GenJobOrderSparePart> list = null;
        if (this.spSource.intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId()) {
            list = this.jobOrderSpareParts;
        } else if (this.sparePartsInsideQuotation != null && !this.sparePartsInsideQuotation.isEmpty()) {
            list = new ArrayList<GenJobOrderSparePart>(this.sparePartsInsideQuotation.size());
            list.addAll(this.sparePartsInsideQuotation);
            if (this.sparePartsOutsideQuotation != null && !this.sparePartsOutsideQuotation.isEmpty()) {
                list.addAll(this.sparePartsOutsideQuotation);
            }
        }
        return list == null || list.isEmpty() ? this.getDummySparePartsList() : list;
    }

    private List<GenJobOrderSparePart> getDummySparePartsList() {
        ArrayList<GenJobOrderSparePart> list = new ArrayList<GenJobOrderSparePart>(8);
        for (int i = 0; i < 6; ++i) {
            list.add(new GenJobOrderSparePart());
        }
        return list;
    }

    public String getCurrentPhaseName() {
        String name = null;
        if (this.lastCompletedPhaseId.intValue() == Enums.JOBORDER_FOLLOWUP_PHASES.NOT_HANDLED_YET.getId()) {
            name = "\u062a\u0645  \u0627\u0644\u0625\u0628\u0644\u0627\u063a \u0639\u0646 \u0627\u0644\u0639\u0637\u0644";
        } else if (this.fixIncludingSpareParts == null) {
            name = !this.agentMustAttend.booleanValue() ? "\u062c\u0627\u0631\u064a \u0641\u062d\u0635 \u0648\u062a\u062d\u062f\u064a\u062f \u0627\u0644\u0639\u0637\u0644" : "\u0641\u064a \u0627\u0646\u062a\u0638\u0627\u0631 \u062d\u0636\u0648\u0631 \u0627\u0644\u0648\u0643\u064a\u0644";
        } else if (!this.fixIncludingSpareParts.booleanValue()) {
            name = "\u062c\u0627\u0631\u064a \u0627\u0644\u0625\u0635\u0644\u0627\u062d";
        } else if (this.fixIncludingSpareParts.booleanValue()) {
            name = this.spSource.intValue() != Enums.JOBORDER_SP_SOURCES.AGENT.getId() ? "\u062c\u0627\u0631\u064a \u0627\u0644\u0625\u0635\u0644\u0627\u062d" : (this.quotationRecieved == null ? "\u0641\u064a \u0627\u0646\u062a\u0638\u0627\u0631 \u0639\u0631\u0636 \u0627\u0644\u0633\u0639\u0631" : (this.quotationAccepted == null ? "\u062c\u0627\u0631\u064a \u0627\u0644\u062a\u0639\u0645\u064a\u062f" : "\u0641\u064a \u0627\u0646\u062a\u0638\u0627\u0631 \u0642\u0637\u0639 \u0627\u0644\u063a\u064a\u0627\u0631"));
        }
        return name;
    }

    public float getSparePartsTotalCost() {
        List<GenJobOrderSparePart> list = null;
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
            for (GenJobOrderSparePart p : list) {
                sum += p.getTotalPrice() != null ? p.getTotalPrice().floatValue() : 0.0f;
            }
        }
        return sum;
    }

    public CommonHospitalLocation getUncodedDeviceLocation() {
        return this.uncodedDeviceLocation;
    }

    public void setUncodedDeviceLocation(CommonHospitalLocation hospitalLocation) {
        this.uncodedDeviceLocation = hospitalLocation;
    }

    public CommonHospitalDepartment getUncodedDeviceDepartment() {
        return this.uncodedDeviceDepartment;
    }

    public void setUncodedDeviceDepartment(CommonHospitalDepartment hospitalDepartment) {
        this.uncodedDeviceDepartment = hospitalDepartment;
    }

    public String getUncodedDeviceRoom() {
        return this.uncodedDeviceRoom;
    }

    public void setUncodedDeviceRoom(String deviceRoom) {
        this.uncodedDeviceRoom = deviceRoom;
    }

    public String getUncodedDeviceLocationDescription() {
        return this.uncodedDeviceLocationDescription;
    }

    public void setUncodedDeviceLocationDescription(String deviceLocationDescription) {
        this.uncodedDeviceLocationDescription = deviceLocationDescription;
    }

    public boolean isUncodedDevice() {
        return this.uncodedDevice;
    }

    public void setUncodedDevice(boolean uncodedDevice) {
        this.uncodedDevice = uncodedDevice;
    }
}
