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
import com.iassets.common.entity.AbstractSpInventoryTransaction;
import com.iassets.common.util.LocalizationManager;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="bio_sp_inventory_transaction")
@NamedQuery(name="BioSpInventoryTransaction.findAll", query="SELECT s FROM BioSpInventoryTransaction s")
public class BioSpInventoryTransaction
extends AbstractSpInventoryTransaction
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="job_order_id")
    @NotFound(action=NotFoundAction.IGNORE)
    private BioJobOrder jobOrder;

    public BioJobOrder getJobOrder() {
        return this.jobOrder;
    }

    public void setJobOrder(BioJobOrder jobOrder) {
        this.jobOrder = jobOrder;
    }

    public String getOperationDescription(String langCode) {
        if (this.input != null) {
            if (this.input.booleanValue()) {
                return LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioSpInventoryTransaction.operationDescription.status1", langCode);
            }
            if (this.jobOrder != null) {
                return LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioSpInventoryTransaction.operationDescription.status2", langCode) + this.jobOrder.getJobOrderNo();
            }
            return LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioSpInventoryTransaction.operationDescription.status3", langCode);
        }
        return LocalizationManager.getLiteral("com.iassets.biomedical.entity.BioSpInventoryTransaction.operationDescription.status4", langCode);
    }
}
