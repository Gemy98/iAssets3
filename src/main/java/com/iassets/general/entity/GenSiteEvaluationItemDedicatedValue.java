/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
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

import com.iassets.common.entity.CommonSite;
import com.iassets.general.entity.GenLookupEvaluationGroupItem;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="gen_site_evaluation_item_dedicated_value")
@NamedQuery(name="GenSiteEvaluationItemDedicatedValue.findAll", query="SELECT g FROM GenSiteEvaluationItemDedicatedValue g")
public class GenSiteEvaluationItemDedicatedValue
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="site_id")
    private CommonSite site;
    @ManyToOne
    @JoinColumn(name="item_id")
    private GenLookupEvaluationGroupItem evaluationGroupItem;
    @Column(name="dedicated_value")
    private Float dedicatedValue;

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

    public GenLookupEvaluationGroupItem getEvaluationGroupItem() {
        return this.evaluationGroupItem;
    }

    public void setEvaluationGroupItem(GenLookupEvaluationGroupItem evaluationGroupItem) {
        this.evaluationGroupItem = evaluationGroupItem;
    }

    public Float getDedicatedValue() {
        return this.dedicatedValue;
    }

    public void setDedicatedValue(Float dedicatedValue) {
        this.dedicatedValue = dedicatedValue;
    }
}
