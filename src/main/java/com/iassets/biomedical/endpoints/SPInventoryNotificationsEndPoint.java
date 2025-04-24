/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.websocket.OnClose
 *  javax.websocket.OnError
 *  javax.websocket.OnOpen
 *  javax.websocket.Session
 *  javax.websocket.server.ServerEndpoint
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.stereotype.Component
 *  org.springframework.web.socket.server.standard.SpringConfigurator
 */
package com.iassets.biomedical.endpoints;

import com.iassets.biomedical.DB.DBQueryManager;
import com.iassets.common.util.Common;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@Component(value="bioSPInventoryNotificationsEndPoint")
@ServerEndpoint(value="/bio/SPInventoryNotifier", configurator=SpringConfigurator.class)
public class SPInventoryNotificationsEndPoint {
    @Autowired
    @Qualifier(value="bioDBQueryManager")
    protected DBQueryManager dbQueryManager;
    private static final Logger LOGGER = Logger.getLogger(SPInventoryNotificationsEndPoint.class.getName());
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

    @OnOpen
    public void onOpen(Session session) {
        queue.add(session);
        SPInventoryNotificationsEndPoint.sendMessage(session, this.getNotification(WebUtil.getSiteIdFromEndPointSession(session), WebUtil.getStringRequestParameterValueFromEndPointSession(session, "langCode")));
        LOGGER.log(Level.INFO, "New connection with client: {0}", session.getId());
        Common.log("Number of opened connections is ************** " + queue.size());
    }

    @OnClose
    public void onClose(Session session) {
        queue.remove(session);
        LOGGER.log(Level.INFO, "Close connection for client: {0}", session.getId());
        Common.log("Number of opened connections is ************** " + queue.size());
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        queue.remove(session);
        exception.printStackTrace();
        LOGGER.log(Level.INFO, "Error for client: {0}", session.getId());
    }

    public void broadcastNotificationUpdates(Integer siteId, String langCode) {
        String message = this.getNotification(siteId, langCode);
        try {
            for (Session s : queue) {
                if (siteId != WebUtil.getSiteIdFromEndPointSession(s)) continue;
                SPInventoryNotificationsEndPoint.sendMessage(s, message);
            }
        }
        catch (Exception e) {
            Common.log(e);
        }
    }

    private static void sendMessage(Session session, String msg) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(msg);
            }
        }
        catch (IOException e) {
            Common.log(e);
        }
    }

    private String getNotification(Integer siteId, String langCode) {
        if (siteId == null || siteId == 0) {
            return " ";
        }
        Long notificationsCount = this.dbQueryManager.getSpInventoryNotificationsCount(siteId);
        if (notificationsCount == null || notificationsCount == 0L) {
            return " ";
        }
        String notfHtml = "<div id='sp_notifications_ph'><img src='image/warning.png' />" + LocalizationManager.getLiteral("com.iassets.biomedical.endpoints.SPInventoryNotificationsEndPoint.notification1", langCode) + " (" + notificationsCount + ") .. <a href='bio/ViewUnderThresholdSPInventoryCategories?" + "ihf" + "=1' target='_blank' rel='opener'>" + LocalizationManager.getLiteral("com.iassets.biomedical.endpoints.SPInventoryNotificationsEndPoint.notification2", langCode) + "</a><a title='" + LocalizationManager.getLiteral("com.iassets.biomedical.endpoints.SPInventoryNotificationsEndPoint.notification3", langCode) + "' class='close_btn' href='javascript:void(0)' onclick=\"activateNotificationStopperCookie(this,'bio_stop_spinv_notify')\">X</a>";
        return notfHtml + "</div>";
    }
}
