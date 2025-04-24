/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.iassets.biomedical.servlet.spinventory;

import com.iassets.biomedical.endpoints.SPInventoryNotificationsEndPoint;
import com.iassets.biomedical.entity.BioSpInventoryContent;
import com.iassets.biomedical.entity.BioSpInventoryTransaction;
import com.iassets.biomedical.servlet.BioBasicServlet;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/bio/AddNewSPCategoryProcess"})
public class AddNewSPCategoryProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    SPInventoryNotificationsEndPoint spCategoryNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        Integer quantity = WebUtil.getParamValueAsInteger(request, "quantity", 0);
        Float unitPrice = WebUtil.getParamValueAsFloat(request, "price", Float.valueOf(0.0f));
        CommonUser sessionUser = this.getSessionUser(request);
        CommonSite sessionSite = this.getSessionSite(request);
        Date now = new Date();
        BioSpInventoryContent spInventoryContent = new BioSpInventoryContent();
        spInventoryContent.setSite(sessionSite);
        spInventoryContent.setCode(WebUtil.getParamValue(request, "code", null));
        spInventoryContent.setName(WebUtil.getParamValue(request, "name", null));
        spInventoryContent.setThreshold(WebUtil.getParamValueAsInteger(request, "threshold", 0));
        spInventoryContent.setAvailableQuantity(quantity);
        spInventoryContent.setAvgUnitPrice(unitPrice);
        spInventoryContent.setCreatedBy(sessionUser);
        spInventoryContent.setCreatedIn(now);
        spInventoryContent = this.bioDBQueryManager.persistEntity(spInventoryContent);
        BioSpInventoryTransaction spInventoryTransaction = new BioSpInventoryTransaction();
        spInventoryTransaction.setSiteId(sessionSite.getId());
        spInventoryTransaction.setSpInventoryContentId(spInventoryContent.getId());
        spInventoryTransaction.setInput(true);
        spInventoryTransaction.setQuantity(quantity);
        spInventoryTransaction.setUnitPrice(unitPrice);
        spInventoryTransaction.setPerformedBy(sessionUser);
        spInventoryTransaction.setPerformedIn(now);
        spInventoryTransaction = this.bioDBQueryManager.persistEntity(spInventoryTransaction);
        this.spCategoryNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request), langCode);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.AddNewSPCategoryProcess.msg1", langCode), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/AddNewSPCategoryDisplay");
    }
}
