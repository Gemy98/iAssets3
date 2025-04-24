/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
 *  javax.persistence.FetchType
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Temporal
 *  javax.persistence.TemporalType
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.common.entity;

import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonHospitalLocation;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.LocalizationManager;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@MappedSuperclass
public abstract class AbstractHospitalDevice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="site_id")
    protected CommonSite site;
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="hosp_id")
    protected CommonHospital hospital;
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="hospital_location_id")
    protected CommonHospitalLocation hospitalLocation;
    @ManyToOne
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name="department_id")
    protected CommonHospitalDepartment hospitalDepartment;
    @Column(name="hospital_room")
    protected String hospitalRoom;
    @Column(name="hospital_location_description")
    protected String hospitalLocationDescription;
    protected String code;
    @Column(name="serial_no")
    protected String serialNo;
    protected String name;
    @Column(name="manufacturer_name")
    protected String manufacturerName;
    protected String model;
    @Column(name="agent_name")
    protected String agentName;
    protected String subcontractor;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="operation_date")
    protected Date operationDate;
    @Column(name="ta3mid_no")
    protected String ta3midNo;
    protected Float price;
    @Column(name="within_warranty")
    protected Boolean withinWarranty;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="warranty_expire_date")
    protected Date warrantyExpireDate;
    @Column(name="within_contract")
    protected Boolean withinContract;
    @Column(name="added_from_another_location")
    protected Boolean addedFromAnotherLocation;
    @Column(name="location_name")
    protected String locationName;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="add_date")
    protected Date addDate;
    @Column(name="pm_annual_visits_no")
    protected Integer pmAnnualVisitsNo;
    @Column(name="pm_visits_months")
    protected String pmVisitsMonths;
    @Column(name="previous_expenses")
    protected Float previousExpenses;
    @Column(name="other_attachments")
    protected String otherAttachments;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="created_by")
    protected CommonUser createdBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="created_in")
    protected Date createdIn;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="last_modified_by")
    protected CommonUser lastModifiedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="last_modified_in")
    protected Date lastModifiedIn;
    protected Integer status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonSite getSite() {
        return this.site;
    }

    public void setSite(CommonSite site) {
        this.site = site;
    }

    public CommonHospital getHospital() {
        return this.hospital;
    }

    public void setHospital(CommonHospital hospital) {
        this.hospital = hospital;
    }

    public CommonHospitalDepartment getHospitalDepartment() {
        return this.hospitalDepartment;
    }

    public void setHospitalDepartment(CommonHospitalDepartment hospitalDepartment) {
        this.hospitalDepartment = hospitalDepartment;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturerName() {
        return this.manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSubcontractor() {
        return this.subcontractor;
    }

    public void setSubcontractor(String subcontractor) {
        this.subcontractor = subcontractor;
    }

    public Date getOperationDate() {
        return this.operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getTa3midNo() {
        return this.ta3midNo;
    }

    public void setTa3midNo(String ta3midNo) {
        this.ta3midNo = ta3midNo;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getWithinWarranty() {
        return this.withinWarranty;
    }

    public void setWithinWarranty(Boolean withinWarranty) {
        this.withinWarranty = withinWarranty;
    }

    public Date getWarrantyExpireDate() {
        return this.warrantyExpireDate;
    }

    public void setWarrantyExpireDate(Date warrantyExpireDate) {
        this.warrantyExpireDate = warrantyExpireDate;
    }

    public Boolean getWithinContract() {
        return this.withinContract;
    }

    public void setWithinContract(Boolean withinContract) {
        this.withinContract = withinContract;
    }

    public Boolean getAddedFromAnotherLocation() {
        return this.addedFromAnotherLocation;
    }

    public void setAddedFromAnotherLocation(Boolean addedFromAnotherLocation) {
        this.addedFromAnotherLocation = addedFromAnotherLocation;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getPmAnnualVisitsNo() {
        return this.pmAnnualVisitsNo;
    }

    public void setPmAnnualVisitsNo(Integer pmAnnualVisitsNo) {
        this.pmAnnualVisitsNo = pmAnnualVisitsNo;
    }

    public String getPmVisitsMonths() {
        return this.pmVisitsMonths;
    }

    public void setPmVisitsMonths(String pmVisitsMonths) {
        this.pmVisitsMonths = pmVisitsMonths;
    }

    public Float getPreviousExpenses() {
        return this.previousExpenses;
    }

    public void setPreviousExpenses(Float previousExpenses) {
        this.previousExpenses = previousExpenses;
    }

    public String getOtherAttachments() {
        return this.otherAttachments;
    }

    public void setOtherAttachments(String otherAttachments) {
        this.otherAttachments = otherAttachments;
    }

    public CommonUser getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CommonUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedIn() {
        return this.createdIn;
    }

    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public CommonUser getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(CommonUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedIn() {
        return this.lastModifiedIn;
    }

    public void setLastModifiedIn(Date lastModifiedIn) {
        this.lastModifiedIn = lastModifiedIn;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation(String langCode) {
        String location = "";
        if (this.hospitalLocation != null) {
            location = location + this.hospitalLocation.getLocalizedName(langCode);
        }
        if (this.hospitalDepartment != null) {
            if (!location.isEmpty()) {
                location = location + " - ";
            }
            location = location + this.hospitalDepartment.getLocalizedName(langCode);
        }
        if (this.hospitalRoom != null) {
            if (!location.isEmpty()) {
                location = location + " - ";
            }
            location = location + this.hospitalRoom;
        }
        if (this.hospitalLocationDescription != null) {
            if (!location.isEmpty()) {
                location = location + " - ";
            }
            location = location + this.hospitalLocationDescription;
        }
        return location.isEmpty() ? LocalizationManager.getLiteral("com.iassets.common.entity.AbstractHospitalDevice.location.status", langCode) : location;
    }

    public CommonHospitalLocation getHospitalLocation() {
        return this.hospitalLocation;
    }

    public void setHospitalLocation(CommonHospitalLocation hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public String getHospitalRoom() {
        return this.hospitalRoom;
    }

    public void setHospitalRoom(String hospitalRoom) {
        this.hospitalRoom = hospitalRoom;
    }

    public String getHospitalLocationDescription() {
        return this.hospitalLocationDescription;
    }

    public void setHospitalLocationDescription(String hospitalLocationDescription) {
        this.hospitalLocationDescription = hospitalLocationDescription;
    }
}
