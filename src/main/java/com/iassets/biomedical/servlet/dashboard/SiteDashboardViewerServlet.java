/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 */
package com.iassets.biomedical.servlet.dashboard;

import com.iassets.biomedical.dashboard.SiteDashboardManager;
import com.iassets.common.bo.charts.Gauge;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@WebServlet(value={"/bio/SiteDashboardViewer"})
public class SiteDashboardViewerServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        int directorateId = this.getSessionDirectorate(request).getId();
        CommonSite site = this.getSessionSite(request);
        Date today = new Date();
        int month = today.getMonth() + 1;
        int year = today.getYear() + 1900;
        SiteDashboardManager siteDashboardManager = new SiteDashboardManager(this.applicationContext, directorateId, site, month, year);
        Vector<Gauge> list = new Vector<Gauge>();
        list.add(siteDashboardManager.getWorkerOrderEfficiencyGauge());
        list.add(siteDashboardManager.getClassADevicesEfficiencyGauge());
        list.add(siteDashboardManager.getClassBDevicesEfficiencyGauge());
        list.add(siteDashboardManager.getPCEfficiencyGauge());
        list.add(siteDashboardManager.getPPMVisitsEfficiencyGauge());
        request.setAttribute("listOfGauges", list);
        Vector list2 = new Vector();
        request.setAttribute("listOfGauges2", list2);
        list.add(siteDashboardManager.getPPMMosta5lsGauge());
        list.add(siteDashboardManager.getWorkerE3tmadGauge());
        list.add(siteDashboardManager.getUniformComitmitGauge());
        list.add(siteDashboardManager.getCompalainGauge());
        this.viewHTMLReport(request, response, "/bio/dashboard/TestDashboardPOC.jsp", "");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
