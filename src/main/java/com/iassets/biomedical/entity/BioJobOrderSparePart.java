/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Entity
 *  javax.persistence.JoinColumn
 *  javax.persistence.ManyToOne
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 *  org.hibernate.annotations.NotFound
 *  org.hibernate.annotations.NotFoundAction
 */
package com.iassets.biomedical.entity;

import com.iassets.biomedical.entity.BioJobOrder;
import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.common.entity.AbstractJobOrderSparePart;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="bio_job_order_spare_part")
@NamedQuery(name="BioJobOrderSparePart.findAll", query="SELECT j FROM BioJobOrderSparePart j")
public class BioJobOrderSparePart
extends AbstractJobOrderSparePart
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="job_order_id")
    private BioJobOrder jobOrder;
    @ManyToOne
    @JoinColumn(name="sp_inventory_category_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private BioSpInventoryContent spInventoryCategoryId;

    public BioJobOrder getJobOrder() {
        return this.jobOrder;
    }

    public void setJobOrder(BioJobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public BioSpInventoryContent getSpInventoryCategoryId() {
        return this.spInventoryCategoryId;
    }

    public void setSpInventoryCategoryId(BioSpInventoryContent spInventoryCategoryId) {
        this.spInventoryCategoryId = spInventoryCategoryId;
    }
}
