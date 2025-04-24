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
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import com.iassets.general.endpoints.SPInventoryNotificationsEndPoint;
import com.iassets.general.entity.GenSpInventoryContent;
import com.iassets.general.entity.GenSpInventoryTransaction;
import com.iassets.general.servlet.GenBasicServlet;
import com.iassets.general.util.DataSecurityChecker;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet(value={"/gen/UpdateSPCategoryProcess"})
public class UpdateSPCategoryProcessServlet
extends GenBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    SPInventoryNotificationsEndPoint spCategoryNotifier;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        boolean inputOperType = WebUtil.getParamValue(request, "input", "1").equals("1");
        Integer quantity = WebUtil.getParamValueAsInteger(request, "quantity", 0);
        Float price = WebUtil.getParamValueAsFloat(request, "price", Float.valueOf(0.0f));
        CommonUser currentUser = this.getSessionUser(request);
        Date now = new Date();
        GenSpInventoryContent spInventoryContent = this.genDBQueryManager.findById(WebUtil.getParamValueAsInteger(request, "spCatId", 0), GenSpInventoryContent.class);
        if (DataSecurityChecker.spInventoryContentUpdateBlocked(request, spInventoryContent)) {
            throw new ServletException(LocalizationManager.getLiteral("com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY", langCode));
        }
        if (!inputOperType && quantity > spInventoryContent.getAvailableQuantity()) {
            this.setMessage(request, new Message("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0625\u062a\u0645\u0627\u0645 \u0627\u0644\u0639\u0645\u0644\u064a\u0629 .. \u0627\u0644\u0643\u0645\u064a\u0629 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u0633\u062d\u0628\u0647\u0627 \u0623\u0643\u0628\u0631 \u0645\u0646 \u0627\u0644\u0643\u0645\u064a\u0629 \u0627\u0644\u0645\u062a\u0627\u062d\u0629 \u0628\u0627\u0644\u0645\u0633\u062a\u0648\u062f\u0639", Message.MESSAGE_TYPE.ERROR));
            this.sendRedirect(request, response, "/gen/UpdateSPCategory?code=" + spInventoryContent.getCode());
            return;
        }
        spInventoryContent.setCode(WebUtil.getParamValue(request, "code", null));
        spInventoryContent.setName(WebUtil.getParamValue(request, "name", null));
        spInventoryContent.setSpecs(WebUtil.getParamValue(request, "specs", null));
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
        spInventoryContent.setLastModefiedBy(currentUser);
        spInventoryContent.setLastModefiedIn(now);
        spInventoryContent = this.genDBQueryManager.mergeEntity(spInventoryContent);
        if (quantity > 0) {
            GenSpInventoryTransaction spInventoryTransaction = new GenSpInventoryTransaction();
            spInventoryTransaction.setSiteId(this.getSessionSiteId(request));
            spInventoryTransaction.setSpInventoryContentId(spInventoryContent.getId());
            spInventoryTransaction.setInput(inputOperType);
            spInventoryTransaction.setQuantity(quantity);
            spInventoryTransaction.setUnitPrice(price);
            spInventoryTransaction.setNotes(WebUtil.getParamValue(request, "notes", null));
            spInventoryTransaction.setPerformedBy(currentUser);
            spInventoryTransaction.setPerformedIn(now);
            spInventoryTransaction = this.genDBQueryManager.persistEntity(spInventoryTransaction);
        }
        this.spCategoryNotifier.broadcastNotificationUpdates(this.getSessionSiteId(request));
        this.setMessage(request, new Message("\u062a\u0645  \u062a\u0639\u062f\u064a\u0644  \u0627\u0644\u0635\u0646\u0641 \u0628\u0646\u062c\u0627\u062d", Message.MESSAGE_TYPE.SUCCESS));
        this.sendRedirect(request, response, "/gen/UpdateSPCategorySearch");
    }
}
