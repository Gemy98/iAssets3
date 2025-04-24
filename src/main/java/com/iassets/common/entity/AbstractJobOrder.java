/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Column
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

import com.iassets.common.entity.CommonEmployee;
import com.iassets.common.entity.CommonUser;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
public abstract class AbstractJobOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    protected Integer id;
    @Column(name="job_order_no")
    protected String jobOrderNo;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="job_order_date")
    protected Date jobOrderDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="damage_date")
    protected Date damageDate;
    @Column(name="damage_description")
    protected String damageDescription;
    @ManyToOne
    @JoinColumn(name="opened_by")
    protected CommonUser openedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="opened_in")
    protected Date openedIn;
    @Column(name="request_form_url")
    protected String requestFormUrl;
    @ManyToOne
    @JoinColumn(name="responsible_engineer")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee responsibleEngineer;
    @ManyToOne
    @JoinColumn(name="related_hospital_department_head")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee relatedHospitalDepartmentHead;
    @ManyToOne
    @JoinColumn(name="site_manager")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee siteManager;
    @ManyToOne
    @JoinColumn(name="mmp_department_head")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee mmpDepartmentHead;
    @ManyToOne
    @JoinColumn(name="mmp_department_supervisor")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee mmpDepartmentSupervisor;
    @ManyToOne
    @JoinColumn(name="hospital_assistant_director")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee hospitalAssistantDirector;
    @ManyToOne
    @JoinColumn(name="hospital_director")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee hospitalDirector;
    @ManyToOne
    @JoinColumn(name="dir_supervisor")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee directorateSupervisor;
    @ManyToOne
    @JoinColumn(name="dir_admin")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee directorateAdmin;
    @ManyToOne
    @JoinColumn(name="dir_super_admin")
    @NotFound(action=NotFoundAction.IGNORE)
    protected CommonEmployee directorateSuperAdmin;
    protected Boolean cancelled;
    @ManyToOne
    @JoinColumn(name="cancelled_by")
    protected CommonUser cancelledBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="cancelled_in")
    protected Date cancelledIn;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="actual_cancel_date")
    protected Date actualCancelDate;
    @Column(name="cancel_reason")
    protected String cancelReason;
    protected Boolean closed;
    @ManyToOne
    @JoinColumn(name="closed_by")
    protected CommonUser closedBy;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="closed_in")
    protected Date closedIn;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="actual_close_date")
    protected Date actualCloseDate;
    @Column(name="fix_including_spare_parts")
    protected Boolean fixIncludingSpareParts;
    @Column(name="sp_source")
    protected Integer spSource;
    @Column(name="sp_po_scan_url")
    protected String spPoScanUrl;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="inventory_transaction_date")
    protected Date inventoryTransactionDate;
    @Column(name="agent_report_url")
    protected String agentReportUrl;
    @Column(name="quotation_recieved")
    protected Boolean quotationRecieved;
    @Column(name="quotation_scan_url")
    protected String quotationScanUrl;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="quotation_date")
    protected Date quotationDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="quotation_expire_date")
    protected Date quotationExpireDate;
    @Column(name="quotation_accepted")
    protected Boolean quotationAccepted;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="quotation_acceptance_date")
    protected Date quotationAcceptanceDate;
    @Column(name="quotation_acceptance_no")
    protected String quotationAcceptanceNo;
    @Column(name="first_action")
    protected String firstAction;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="first_action_date")
    protected Date firstActionDate;
    @Column(name="agent_must_attend")
    protected Boolean agentMustAttend;
    @Column(name="second_action")
    protected String secondAction;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="second_action_date")
    protected Date secondActionDate;
    @Column(name="final_action")
    protected String finalAction;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="final_action_date")
    protected Date finalActionDate;
    @Column(name="final_agent_report_url")
    protected String finalAgentReportUrl;
    @Column(name="final_report_url")
    protected String finalReportUrl;
    @Column(name="last_completed_phase_id")
    protected Integer lastCompletedPhaseId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobOrderNo() {
        return this.jobOrderNo;
    }

    public void setJobOrderNo(String jobOrderNo) {
        this.jobOrderNo = jobOrderNo;
    }

    public Date getJobOrderDate() {
        return this.jobOrderDate;
    }

    public void setJobOrderDate(Date jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public Date getDamageDate() {
        return this.damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
    }

    public String getDamageDescription() {
        return this.damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public CommonUser getOpenedBy() {
        return this.openedBy;
    }

    public void setOpenedBy(CommonUser openedBy) {
        this.openedBy = openedBy;
    }

    public Date getOpenedIn() {
        return this.openedIn;
    }

    public void setOpenedIn(Date openedIn) {
        this.openedIn = openedIn;
    }

    public String getRequestFormUrl() {
        return this.requestFormUrl;
    }

    public void setRequestFormUrl(String requestFormUrl) {
        this.requestFormUrl = requestFormUrl;
    }

    public CommonEmployee getResponsibleEngineer() {
        return this.responsibleEngineer;
    }

    public void setResponsibleEngineer(CommonEmployee responsibleEngineer) {
        this.responsibleEngineer = responsibleEngineer;
    }

    public CommonEmployee getRelatedHospitalDepartmentHead() {
        return this.relatedHospitalDepartmentHead;
    }

    public void setRelatedHospitalDepartmentHead(CommonEmployee relatedHospitalDepartmentHead) {
        this.relatedHospitalDepartmentHead = relatedHospitalDepartmentHead;
    }

    public CommonEmployee getSiteManager() {
        return this.siteManager;
    }

    public void setSiteManager(CommonEmployee siteManager) {
        this.siteManager = siteManager;
    }

    public CommonEmployee getMmpDepartmentHead() {
        return this.mmpDepartmentHead;
    }

    public void setMmpDepartmentHead(CommonEmployee mmpDepartmentHead) {
        this.mmpDepartmentHead = mmpDepartmentHead;
    }

    public CommonEmployee getMmpDepartmentSupervisor() {
        return this.mmpDepartmentSupervisor;
    }

    public void setMmpDepartmentSupervisor(CommonEmployee mmpDepartmentSupervisor) {
        this.mmpDepartmentSupervisor = mmpDepartmentSupervisor;
    }

    public CommonEmployee getHospitalAssistantDirector() {
        return this.hospitalAssistantDirector;
    }

    public void setHospitalAssistantDirector(CommonEmployee hospitalAssistantDirector) {
        this.hospitalAssistantDirector = hospitalAssistantDirector;
    }

    public CommonEmployee getHospitalDirector() {
        return this.hospitalDirector;
    }

    public void setHospitalDirector(CommonEmployee hospitalDirector) {
        this.hospitalDirector = hospitalDirector;
    }

    public CommonEmployee getDirectorateSupervisor() {
        return this.directorateSupervisor;
    }

    public void setDirectorateSupervisor(CommonEmployee directorateSupervisor) {
        this.directorateSupervisor = directorateSupervisor;
    }

    public CommonEmployee getDirectorateAdmin() {
        return this.directorateAdmin;
    }

    public void setDirectorateAdmin(CommonEmployee directorateAdmin) {
        this.directorateAdmin = directorateAdmin;
    }

    public CommonEmployee getDirectorateSuperAdmin() {
        return this.directorateSuperAdmin;
    }

    public void setDirectorateSuperAdmin(CommonEmployee directorateSuperAdmin) {
        this.directorateSuperAdmin = directorateSuperAdmin;
    }

    public Boolean getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public CommonUser getCancelledBy() {
        return this.cancelledBy;
    }

    public void setCancelledBy(CommonUser cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public Date getCancelledIn() {
        return this.cancelledIn;
    }

    public void setCancelledIn(Date cancelledIn) {
        this.cancelledIn = cancelledIn;
    }

    public Date getActualCancelDate() {
        return this.actualCancelDate;
    }

    public void setActualCancelDate(Date actualCancelDate) {
        this.actualCancelDate = actualCancelDate;
    }

    public String getCancelReason() {
        return this.cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Boolean getClosed() {
        return this.closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public CommonUser getClosedBy() {
        return this.closedBy;
    }

    public void setClosedBy(CommonUser closedBy) {
        this.closedBy = closedBy;
    }

    public Date getClosedIn() {
        return this.closedIn;
    }

    public void setClosedIn(Date closedIn) {
        this.closedIn = closedIn;
    }

    public Date getActualCloseDate() {
        return this.actualCloseDate;
    }

    public void setActualCloseDate(Date actualCloseDate) {
        this.actualCloseDate = actualCloseDate;
    }

    public Boolean getFixIncludingSpareParts() {
        return this.fixIncludingSpareParts;
    }

    public void setFixIncludingSpareParts(Boolean fixIncludingSpareParts) {
        this.fixIncludingSpareParts = fixIncludingSpareParts;
    }

    public Integer getSpSource() {
        return this.spSource;
    }

    public void setSpSource(Integer spSource) {
        this.spSource = spSource;
    }

    public String getSpPoScanUrl() {
        return this.spPoScanUrl;
    }

    public void setSpPoScanUrl(String spPoScanUrl) {
        this.spPoScanUrl = spPoScanUrl;
    }

    public Date getInventoryTransactionDate() {
        return this.inventoryTransactionDate;
    }

    public void setInventoryTransactionDate(Date inventoryTransactionDate) {
        this.inventoryTransactionDate = inventoryTransactionDate;
    }

    public String getAgentReportUrl() {
        return this.agentReportUrl;
    }

    public void setAgentReportUrl(String agentReportUrl) {
        this.agentReportUrl = agentReportUrl;
    }

    public Boolean getQuotationRecieved() {
        return this.quotationRecieved;
    }

    public void setQuotationRecieved(Boolean quotationRecieved) {
        this.quotationRecieved = quotationRecieved;
    }

    public String getQuotationScanUrl() {
        return this.quotationScanUrl;
    }

    public void setQuotationScanUrl(String quotationScanUrl) {
        this.quotationScanUrl = quotationScanUrl;
    }

    public Date getQuotationDate() {
        return this.quotationDate;
    }

    public void setQuotationDate(Date quotationDate) {
        this.quotationDate = quotationDate;
    }

    public Date getQuotationExpireDate() {
        return this.quotationExpireDate;
    }

    public void setQuotationExpireDate(Date quotationExpireDate) {
        this.quotationExpireDate = quotationExpireDate;
    }

    public Boolean getQuotationAccepted() {
        return this.quotationAccepted;
    }

    public void setQuotationAccepted(Boolean quotationAccepted) {
        this.quotationAccepted = quotationAccepted;
    }

    public Date getQuotationAcceptanceDate() {
        return this.quotationAcceptanceDate;
    }

    public void setQuotationAcceptanceDate(Date quotationAcceptanceDate) {
        this.quotationAcceptanceDate = quotationAcceptanceDate;
    }

    public String getQuotationAcceptanceNo() {
        return this.quotationAcceptanceNo;
    }

    public void setQuotationAcceptanceNo(String quotationAcceptanceNo) {
        this.quotationAcceptanceNo = quotationAcceptanceNo;
    }

    public String getFirstAction() {
        return this.firstAction;
    }

    public void setFirstAction(String firstAction) {
        this.firstAction = firstAction;
    }

    public Date getFirstActionDate() {
        return this.firstActionDate;
    }

    public void setFirstActionDate(Date firstActionDate) {
        this.firstActionDate = firstActionDate;
    }

    public Boolean getAgentMustAttend() {
        return this.agentMustAttend;
    }

    public void setAgentMustAttend(Boolean agentMustAttend) {
        this.agentMustAttend = agentMustAttend;
    }

    public String getSecondAction() {
        return this.secondAction;
    }

    public void setSecondAction(String secondAction) {
        this.secondAction = secondAction;
    }

    public Date getSecondActionDate() {
        return this.secondActionDate;
    }

    public void setSecondActionDate(Date secondActionDate) {
        this.secondActionDate = secondActionDate;
    }

    public String getFinalAction() {
        return this.finalAction;
    }

    public void setFinalAction(String finalAction) {
        this.finalAction = finalAction;
    }

    public Date getFinalActionDate() {
        return this.finalActionDate;
    }

    public void setFinalActionDate(Date finalActionDate) {
        this.finalActionDate = finalActionDate;
    }

    public String getFinalAgentReportUrl() {
        return this.finalAgentReportUrl;
    }

    public void setFinalAgentReportUrl(String finalAgentReportUrl) {
        this.finalAgentReportUrl = finalAgentReportUrl;
    }

    public String getFinalReportUrl() {
        return this.finalReportUrl;
    }

    public void setFinalReportUrl(String finalReportUrl) {
        this.finalReportUrl = finalReportUrl;
    }

    public Integer getLastCompletedPhaseId() {
        return this.lastCompletedPhaseId;
    }

    public void setLastCompletedPhaseId(Integer lastCompletedPhaseId) {
        this.lastCompletedPhaseId = lastCompletedPhaseId;
    }
}
