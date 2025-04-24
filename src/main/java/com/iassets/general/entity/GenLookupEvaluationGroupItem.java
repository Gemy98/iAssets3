/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.general.entity.GenLookupEvaluationGroup;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_lookup_evaluation_group_item")
@NamedQuery(name="GenLookupEvaluationGroupItem.findAll", query="SELECT g FROM GenLookupEvaluationGroupItem g")
public class GenLookupEvaluationGroupItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    private String name;
    @Column(name="max_degree")
    private Integer maxDegree;
    @Column(name="display_order")
    private Integer displayOrder;
    private String description;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name="group_id")
    private GenLookupEvaluationGroup evaluationGroup;

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

    public Integer getMaxDegree() {
        return this.maxDegree;
    }

    public void setMaxDegree(Integer maxDegree) {
        this.maxDegree = maxDegree;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public GenLookupEvaluationGroup getEvaluationGroup() {
        return this.evaluationGroup;
    }

    public void setEvaluationGroup(GenLookupEvaluationGroup evaluationGroup) {
        this.evaluationGroup = evaluationGroup;
    }
}
