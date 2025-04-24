/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_lookup_internal_ppm_period")
@NamedQuery(name="GenLookupInternalPpmPeriod.findAll", query="SELECT g FROM GenLookupInternalPpmPeriod g WHERE g.status = true")
public class GenLookupInternalPpmPeriod
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer periority;
    @Column(name="subsequent_diff_in_month")
    private Float subsequentDiffInMonth;
    @Column(name="occurance_no_within_contract")
    private Integer occuranceNoWithinContract;
    private Boolean status;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriority() {
        return this.periority;
    }

    public void setPeriority(Integer periority) {
        this.periority = periority;
    }

    public Float getSubsequentDiffInMonth() {
        return this.subsequentDiffInMonth;
    }

    public void setSubsequentDiffInMonth(Float subsequentDiffInMonth) {
        this.subsequentDiffInMonth = subsequentDiffInMonth;
    }

    public Integer getOccuranceNoWithinContract() {
        return this.occuranceNoWithinContract;
    }

    public void setOccuranceNoWithinContract(Integer occuranceNoWithinContract) {
        this.occuranceNoWithinContract = occuranceNoWithinContract;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
