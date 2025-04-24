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
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.OneToMany
 *  javax.persistence.Table
 */
package com.iassets.general.entity;

import com.iassets.general.entity.GenLookupEvaluationGroupItem;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Cacheable
@Entity
@Table(name="gen_lookup_evaluation_group")
@NamedQueries(value={@NamedQuery(name="GenLookupEvaluationGroup.findAll", query="SELECT g FROM GenLookupEvaluationGroup g "), @NamedQuery(name="GenLookupEvaluationGroup.findAllActive", query="SELECT g FROM GenLookupEvaluationGroup g WHERE g.active= true order by g.displayOrder , g.name ")})
public class GenLookupEvaluationGroup
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="display_order")
    private Integer displayOrder;
    @Column(name="active")
    private Boolean active;
    @OneToMany(mappedBy="evaluationGroup")
    private List<GenLookupEvaluationGroupItem> evaluationGroupItems;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return this.displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<GenLookupEvaluationGroupItem> getEvaluationGroupItems() {
        return this.evaluationGroupItems;
    }

    public void setEvaluationGroupItems(List<GenLookupEvaluationGroupItem> evaluationGroupItems) {
        this.evaluationGroupItems = evaluationGroupItems;
    }

    public GenLookupEvaluationGroupItem addGenLookupEvaluationGroupItem(GenLookupEvaluationGroupItem evaluationGroupItem) {
        this.getEvaluationGroupItems().add(evaluationGroupItem);
        evaluationGroupItem.setEvaluationGroup(this);
        return evaluationGroupItem;
    }

    public GenLookupEvaluationGroupItem removeGenLookupEvaluationGroupItem(GenLookupEvaluationGroupItem evaluationGroupItem) {
        this.getEvaluationGroupItems().remove(evaluationGroupItem);
        evaluationGroupItem.setEvaluationGroup(null);
        return evaluationGroupItem;
    }
}
