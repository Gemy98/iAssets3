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
import com.iassets.common.util.Enums;
import com.iassets.common.util.WebUtil;
import com.iassets.general.DB.DBQueryManager;
import com.iassets.general.entity.GenJobOrder;
import com.iassets.general.entity.GenLookupJobOrderCategory;
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

@Component(value="genJobOrderNotificationsEndPoint")
@ServerEndpoint(value="/gen/JobOrdersNotifier", configurator=SpringConfigurator.class)
public class JobOrderNotificationsEndPoint {
    @Autowired
    @Qualifier(value="genDBQueryManager")
    DBQueryManager dbQueryManager;
    private static final Logger LOGGER = Logger.getLogger(JobOrderNotificationsEndPoint.class.getName());
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

    @OnOpen
    public void onOpen(Session session) {
        queue.add(session);
        JobOrderNotificationsEndPoint.sendMessage(session, this.getNotification(WebUtil.getSiteIdFromEndPointSession(session), WebUtil.getGmpDepartmentIdFromEndPointSession(session)));
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

    public void broadcastNotificationUpdates(Integer siteId, GenLookupJobOrderCategory gmpDep) {
        Integer gmpDepId = gmpDep != null ? gmpDep.getId() : null;
        String message = this.getNotification(siteId, gmpDepId);
        try {
            for (Session s : queue) {
                if (siteId != WebUtil.getSiteIdFromEndPointSession(s) || gmpDepId != null && gmpDepId != WebUtil.getGmpDepartmentIdFromEndPointSession(s)) continue;
                JobOrderNotificationsEndPoint.sendMessage(s, message);
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

    private String getNotification(Integer siteId, Integer gmpDepId) {
        List<GenJobOrder> list;
        if (siteId == null || siteId == 0) {
            return "";
        }
        GenLookupJobOrderCategory gmpDep = null;
        if (gmpDepId != null && gmpDepId != 0) {
            gmpDep = this.dbQueryManager.findById(gmpDepId, GenLookupJobOrderCategory.class);
        }
        if ((list = this.dbQueryManager.getJobOrders(siteId, 0, gmpDep, 0, 0, Enums.JOB_ORDER_STATUS.LATE)) == null || list.isEmpty()) {
            return "";
        }
        String notfHtml = "<div id='jo_notifications_ph'><img src='image/warning.png' />\u064a\u0648\u062c\u062f \u0623\u0648\u0627\u0645\u0631 \u0639\u0645\u0644 \u062a\u062d\u062a\u0627\u062c \u0645\u062a\u0627\u0628\u0639\u0629 (" + list.size() + ") .. <a href='gen/ViewLateJobOrders?" + "ihf" + "' target='_blank' rel='opener'>\u0627\u0644\u062a\u0641\u0627\u0635\u064a\u0644</a><a title='\u062a\u062c\u0627\u0647\u0644 \u0647\u0630\u0627 \u0627\u0644\u062a\u0646\u0628\u064a\u0647' class='close_btn' href='javascript:void(0)' onclick=\"activateNotificationStopperCookie(this,'gen_stop_jo_notify')\">X</a>";
        return notfHtml + "</div>";
    }
}
