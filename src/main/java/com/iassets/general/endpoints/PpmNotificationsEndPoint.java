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
package com.iassets.general.endpoints;

import com.iassets.common.util.Common;
import com.iassets.common.util.WebUtil;
import com.iassets.general.DB.DBQueryManager;
import com.iassets.general.entity.GenHospitalDeviceInternalPpmNotificationSchedule;
import java.io.IOException;
import java.util.List;
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

@Component(value="genPpmNotificationsEndPoint")
@ServerEndpoint(value="/gen/PpmNotifier", configurator=SpringConfigurator.class)
public class PpmNotificationsEndPoint {
    @Autowired
    @Qualifier(value="genDBQueryManager")
    DBQueryManager dbQueryManager;
    private static final Logger LOGGER = Logger.getLogger(PpmNotificationsEndPoint.class.getName());
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

    @OnOpen
    public void onOpen(Session session) {
        queue.add(session);
        PpmNotificationsEndPoint.sendMessage(session, this.getNotification(WebUtil.getSiteIdFromEndPointSession(session), WebUtil.getPPMCatIdFromEndPointSession(session)));
        LOGGER.log(Level.INFO, "ppm New connection with client: {0}", session.getId());
        Common.log("ppm Number of opened connections is ************** " + queue.size());
    }

    @OnClose
    public void onClose(Session session) {
        queue.remove(session);
        LOGGER.log(Level.INFO, "ppm Close connection for client: {0}", session.getId());
        Common.log("ppm Number of opened connections is ************** " + queue.size());
    }

    @OnError
    public void onError(Throwable exception, Session session) {
        queue.remove(session);
        exception.printStackTrace();
        LOGGER.log(Level.INFO, "Error for client: {0}", session.getId());
    }

    public void broadcastNotificationUpdates(Integer siteId, Integer pPMcatId) {
        String message = this.getNotification(siteId, pPMcatId);
        try {
            for (Session s : queue) {
                if (siteId != WebUtil.getSiteIdFromEndPointSession(s)) continue;
                PpmNotificationsEndPoint.sendMessage(s, message);
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

    private String getNotification(Integer siteId, Integer pPMcatId) {
        if (siteId == null || siteId == 0) {
            return "";
        }
        List<GenHospitalDeviceInternalPpmNotificationSchedule> list = this.dbQueryManager.getCurrentlyScheduledInternalPPMForSite(siteId, pPMcatId);
        if (list == null || list.isEmpty()) {
            return "";
        }
        String notfHtml = "<div id='ppm_notifications_ph'><img src='image/warning.png' />\u064a\u0648\u062c\u062f \u0635\u064a\u0627\u0646\u0629 \u0648\u0642\u0627\u0626\u064a\u0629 \u0645\u062c\u062f\u0648\u0644\u0629 (" + list.size() + ") .. <a href='gen/ViewCurrentlyScheduledInternalPPM?" + "ihf" + "' target='_blank' rel='opener'>\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644</a><a title='\u062a\u062c\u0627\u0647\u0644 \u0647\u0630\u0627 \u0627\u0644\u062a\u0646\u0628\u064a\u0647' class='close_btn' href='javascript:void(0)' onclick=\"activateNotificationStopperCookie(this,'gen_stop_ppm_notify')\">X</a>";
        return notfHtml + "</div>";
    }
}
