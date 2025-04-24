/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractSiteContract;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_site_contract")
@NamedQueries(value={@NamedQuery(name="GenSiteContract.findAll", query="SELECT s FROM GenSiteContract s")})
public class GenSiteContract
extends AbstractSiteContract
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name="subcontractor_expenses")
    private Float subcontractorExpenses;
    @Column(name="electrical_mechanical_consumables")
    private Float electricalMechanicalConsumables;
    @Column(name="monthly_consumables")
    private Float monthlyConsumables;
    @Column(name="labor_expenses")
    private Float laborExpenses;
    @Column(name="water_supply_expenses")
    private Float waterSupplyExpenses;
    @Column(name="washing_supply_expenses")
    private Float washingSupplyExpenses;
    @Column(name="sweeping_sewage_expenses")
    private Float sweepingSewageExpenses;
    @Column(name="developement_expenses")
    private Float developementExpenses;
    @Column(name="ppm_ref_date")
    private Date ppmRefDate;

    public Float getSubcontractorExpenses() {
        return this.subcontractorExpenses;
    }

    public void setSubcontractorExpenses(Float subcontractorExpenses) {
        this.subcontractorExpenses = subcontractorExpenses;
    }

    public Float getElectricalMechanicalConsumables() {
        return this.electricalMechanicalConsumables;
    }

    public void setElectricalMechanicalConsumables(Float electricalMechanicalConsumables) {
        this.electricalMechanicalConsumables = electricalMechanicalConsumables;
    }

    public Float getMonthlyConsumables() {
        return this.monthlyConsumables;
    }

    public void setMonthlyConsumables(Float monthlyConsumables) {
        this.monthlyConsumables = monthlyConsumables;
    }

    public Float getLaborExpenses() {
        return this.laborExpenses;
    }

    public void setLaborExpenses(Float laborExpenses) {
        this.laborExpenses = laborExpenses;
    }

    public Float getWaterSupplyExpenses() {
        return this.waterSupplyExpenses;
    }

    public void setWaterSupplyExpenses(Float waterSupplyExpenses) {
        this.waterSupplyExpenses = waterSupplyExpenses;
    }

    public Float getWashingSupplyExpenses() {
        return this.washingSupplyExpenses;
    }

    public void setWashingSupplyExpenses(Float washingSupplyExpenses) {
        this.washingSupplyExpenses = washingSupplyExpenses;
    }

    public Float getSweepingSewageExpenses() {
        return this.sweepingSewageExpenses;
    }

    public void setSweepingSewageExpenses(Float sweepingSewageExpenses) {
        this.sweepingSewageExpenses = sweepingSewageExpenses;
    }

    public Float getDevelopementExpenses() {
        return this.developementExpenses;
    }

    public void setDevelopementExpenses(Float developementExpenses) {
        this.developementExpenses = developementExpenses;
    }

    public Date getPpmRefDate() {
        return this.ppmRefDate;
    }

    public void setPpmRefDate(Date ppmRefDate) {
        this.ppmRefDate = ppmRefDate;
    }
}
