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

import com.iassets.common.entity.AbstractSpInventoryTransaction;
import com.iassets.general.entity.GenJobOrder;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="gen_sp_inventory_transaction")
@NamedQuery(name="GenSpInventoryTransaction.findAll", query="SELECT s FROM GenSpInventoryTransaction s")
public class GenSpInventoryTransaction
extends AbstractSpInventoryTransaction
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="job_order_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private GenJobOrder jobOrder;

    public GenJobOrder getJobOrder() {
        return this.jobOrder;
    }

    public void setJobOrder(GenJobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public String getOperationDescription() {
        if (this.input != null) {
            if (this.input.booleanValue()) {
                return "\u0625\u0636\u0627\u0641\u0629 / \u062a\u0633\u0648\u064a\u0629 \u0628\u0627\u0644\u0632\u064a\u0627\u062f\u0629";
            }
            if (this.jobOrder != null) {
                return "\u0633\u062d\u0628 \u0644\u0625\u0635\u0644\u0627\u062d - \u0623\u0645\u0631 \u0639\u0645\u0644 \u0631\u0642\u0645 " + this.jobOrder.getJobOrderNo();
            }
            return "\u0633\u062d\u0628 / \u062a\u0633\u0648\u064a\u0629 \u0628\u0627\u0644\u0646\u0642\u0635";
        }
        return "\u063a\u064a\u0631 \u0645\u062d\u062f\u062f";
    }
}
