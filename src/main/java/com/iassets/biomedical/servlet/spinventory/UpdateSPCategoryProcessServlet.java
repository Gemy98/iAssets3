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
import com.iassets.biomedical.util.DataSecurityChecker;
import com.iassets.common.bo.Message;
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

@WebServlet(value={"/bio/UpdateSPCategoryProcess"})
public class UpdateSPCategoryProcessServlet
extends BioBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    SPInventoryNotificationsEndPoint spCategoryNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        boolean inputOperType = WebUtil.getParamValue(request, "input", "1").equals("1");
        Integer quantity = WebUtil.getParamValueAsInteger(request, "quantity", 0);
        Float price = WebUtil.getParamValueAsFloat(request, "price", Float.valueOf(0.0f));
        CommonUser sessionUser = this.getSessionUser(request);
        Date now = new Date();
        BioSpInventoryContent spInventoryContent = this.bioDBQueryManager.findById(WebUtil.getParamValueAsInteger(request, "spCatId", 0), BioSpInventoryContent.class);
        if (DataSecurityChecker.spInventoryContentUpdateBlocked(request, spInventoryContent)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        if (!inputOperType && quantity > spInventoryContent.getAvailableQuantity()) {
            this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.UpdateSPCategoryProcess.msg1", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.ERROR));
            this.sendRedirect(request, response, "/bio/UpdateSPCategory?code=" + spInventoryContent.getCode());
            return;
        }
        spInventoryContent.setCode(WebUtil.getParamValue(request, "code", null));
        spInventoryContent.setName(WebUtil.getParamValue(request, "name", null));
        spInventoryContent.setThreshold(WebUtil.getParamValueAsInteger(request, "threshold", 0));
        if (quantity > 0) {
            int newQuantity = spInventoryContent.getAvailableQuantity() + (inputOperType ? quantity : -quantity.intValue());
            float newPrice = 0.0f;
            if (newQuantity != 0) {
                newPrice = (spInventoryContent.getAvgUnitPrice().floatValue() * (float)spInventoryContent.getAvailableQuantity().intValue() + (float)(inputOperType ? 1 : -1) * (price.floatValue() * (float)quantity.intValue())) / (float)newQuantity;
            }
            spInventoryContent.setAvailableQuantity(newQuantity);
            spInventoryContent.setAvgUnitPrice(Float.valueOf(newPrice));
        }
        spInventoryContent.setLastModefiedBy(sessionUser);
        spInventoryContent.setLastModefiedIn(now);
        spInventoryContent = this.bioDBQueryManager.mergeEntity(spInventoryContent);
        if (quantity > 0) {
            BioSpInventoryTransaction spInventoryTransaction = new BioSpInventoryTransaction();
            spInventoryTransaction.setSiteId(this.getSessionSiteId(request));
            spInventoryTransaction.setSpInventoryContentId(spInventoryContent.getId());
            spInventoryTransaction.setInput(inputOperType);
            spInventoryTransaction.setQuantity(quantity);
            spInventoryTransaction.setUnitPrice(price);
            spInventoryTransaction.setNotes(WebUtil.getParamValue(request, "notes", null));
            spInventoryTransaction.setPerformedBy(sessionUser);
            spInventoryTransaction.setPerformedIn(now);
            spInventoryTransaction = this.bioDBQueryManager.persistEntity(spInventoryTransaction);
        }
        this.spCategoryNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request), langCode);
        this.setMessage(request, new Message(LocalizationManager.getLiteral("servlet.UpdateSPCategoryProcess.msg2", this.getSessionLanguage(request)), Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/bio/UpdateSPCategorySearch");
    }
}
