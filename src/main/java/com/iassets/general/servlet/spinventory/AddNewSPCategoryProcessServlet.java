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
package com.iassets.general.servlet.spinventory;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.WebUtil;
import com.iassets.general.endpoints.SPInventoryNotificationsEndPoint;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.entity.GenSpInventoryTransaction;
import com.iassets.general.servlet.GenBasicServlet;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/gen/AddNewSPCategoryProcess"})
public class AddNewSPCategoryProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    SPInventoryNotificationsEndPoint spCategoryNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer quantity = WebUtil.getParamValueAsInteger(request, "quantity", 0);
        Float unitPrice = WebUtil.getParamValueAsFloat(request, "price", Float.valueOf(0.0f));
        CommonUser sessionUser = this.getSessionUser(request);
        CommonSite sessionSite = this.getSessionSite(request);
        Date now = new Date();
        GenSpInventoryContent spInventoryContent = new GenSpInventoryContent();
        spInventoryContent.setSite(sessionSite);
        spInventoryContent.setCode(WebUtil.getParamValue(request, "code", null));
        spInventoryContent.setName(WebUtil.getParamValue(request, "name", null));
        spInventoryContent.setSpecs(WebUtil.getParamValue(request, "specs", null));
        spInventoryContent.setThreshold(WebUtil.getParamValueAsInteger(request, "threshold", 0));
        spInventoryContent.setAvailableQuantity(quantity);
        spInventoryContent.setAvgUnitPrice(unitPrice);
        spInventoryContent.setCreatedBy(sessionUser);
        spInventoryContent.setCreatedIn(now);
        spInventoryContent = this.genDBQueryManager.persistEntity(spInventoryContent);
        GenSpInventoryTransaction spInventoryTransaction = new GenSpInventoryTransaction();
        spInventoryTransaction.setSiteId(sessionSite.getId());
        spInventoryTransaction.setSpInventoryContentId(spInventoryContent.getId());
        spInventoryTransaction.setInput(true);
        spInventoryTransaction.setQuantity(quantity);
        spInventoryTransaction.setUnitPrice(unitPrice);
        spInventoryTransaction.setPerformedBy(sessionUser);
        spInventoryTransaction.setPerformedIn(now);
        spInventoryTransaction = this.genDBQueryManager.persistEntity(spInventoryTransaction);
        this.spCategoryNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request));
        this.setMessage(request, new Message("\u062a\u0645\u062a  \u0625\u0636\u0627\u0641\u0629 \u0627\u0644\u0635\u0646\u0641 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/gen/AddNewSPCategoryDisplay");
    }
}
