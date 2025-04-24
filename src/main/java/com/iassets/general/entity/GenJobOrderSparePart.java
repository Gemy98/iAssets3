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
package com.iassets.general.entity;

import com.iassets.common.entity.AbstractJobOrderSparePart;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenSpInventoryContent;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="gen_job_order_spare_part")
@NamedQuery(name="GenJobOrderSparePart.findAll", query="SELECT j FROM GenJobOrderSparePart j")
public class GenJobOrderSparePart
extends AbstractJobOrderSparePart
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="job_order_id")
    private GenJobOrder jobOrder;
    @ManyToOne
    @JoinColumn(name="sp_inventory_category_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private GenSpInventoryContent spInventoryCategoryId;

    public GenJobOrder getJobOrder() {
        return this.jobOrder;
    }

    public void setJobOrder(GenJobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public GenSpInventoryContent getSpInventoryCategoryId() {
        return this.spInventoryCategoryId;
    }

    public void setSpInventoryCategoryId(GenSpInventoryContent spInventoryCategoryId) {
        this.spInventoryCategoryId = spInventoryCategoryId;
    }
}
