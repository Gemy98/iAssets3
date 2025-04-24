/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.biomedical.servlet.dashboard;

import com.iassets.common.bo.charts.PieChart;
import com.iassets.common.bo.charts.PieChartSegment;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/TestPieDashboard"})
public class TestPieDashboardServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PieChart pieChart = new PieChart("\u0639\u062f\u062f \u0627\u0644\u062d\u0627\u062c\u0627\u062a \u0627\u0644\u0644\u0649 \u0641\u0649 \u0627\u0644\u0645\u0633\u062a\u0634\u0641\u064a\u0627\u062a");
        pieChart.setTitleLiteralKey("Pie Title");
        PieChartSegment PieChartSegment2 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u062e\u0627\u0644\u062f", 20);
        PieChartSegment PieChartSegment1 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0639\u0628\u062f \u0627\u0644\u0644\u0647", 50);
        PieChartSegment PieChartSegment22 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u062b\u0627\u0631 \u0627\u0644\u0639\u0627\u0645", 30);
        PieChartSegment PieChartSegment3 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0648\u0644\u0627\u062f\u0629 \u0648\u0627\u0644\u0623\u0637\u0641\u0627\u0644", 25);
        PieChartSegment PieChartSegment4 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u062d\u0628\u0648\u0646\u0627 \u0627\u0644\u0639\u0627\u0645", 35);
        PieChartSegment PieChartSegment5 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0635\u062d\u0629 \u0627\u0644\u0646\u0641\u0633\u064a\u0629", 20);
        PieChartSegment PieChartSegment6 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0627\u0633\u0646\u0627\u0646", 70);
        PieChartSegment PieChartSegment7 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u062c\u0631\u0627\u062d\u0629", 63);
        PieChartSegment PieChartSegment8 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0641\u064a\u0635\u0644", 21);
        PieChartSegment PieChartSegment9 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0641\u0647\u062f", 19);
        PieChartSegment PieChartSegment10 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0637\u0644\u0627\u0644", 61);
        PieChartSegment PieChartSegment11 = new PieChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0633\u0644\u0645\u0627\u0646", 33);
        pieChart.addSegment(PieChartSegment2);
        pieChart.addSegment(PieChartSegment1);
        pieChart.addSegment(PieChartSegment22);
        pieChart.addSegment(PieChartSegment3);
        pieChart.addSegment(PieChartSegment4);
        pieChart.addSegment(PieChartSegment5);
        pieChart.addSegment(PieChartSegment6);
        pieChart.addSegment(PieChartSegment7);
        pieChart.addSegment(PieChartSegment8);
        pieChart.addSegment(PieChartSegment9);
        pieChart.addSegment(PieChartSegment10);
        pieChart.addSegment(PieChartSegment11);
        request.setAttribute("pieChart", (Object)pieChart);
        this.viewHTMLReport(request, response, "/tests/TestPieDashboardPOC.jsp", "");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
