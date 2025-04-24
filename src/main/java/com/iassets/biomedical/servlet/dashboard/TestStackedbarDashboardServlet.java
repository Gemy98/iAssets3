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

import com.iassets.common.bo.charts.SingleBarChartSegment;
import com.iassets.common.bo.charts.SingleColumnBarChart;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/TestStackedbarDashboard"})
public class TestStackedbarDashboardServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SingleColumnBarChart stackedBarChart = new SingleColumnBarChart("\u0639\u062f\u062f \u0623\u0648\u0627\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0627\u0644\u063a\u064a\u0631 \u0645\u0646\u062c\u0632\u0629");
        SingleBarChartSegment singleBarChartSegment = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u062e\u0627\u0644\u062f", 20);
        SingleBarChartSegment singleBarChartSegment1 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0639\u0628\u062f \u0627\u0644\u0644\u0647", 50);
        SingleBarChartSegment singleBarChartSegment2 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u062b\u0627\u0631 \u0627\u0644\u0639\u0627\u0645", 30);
        SingleBarChartSegment singleBarChartSegment3 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0648\u0644\u0627\u062f\u0629 \u0648\u0627\u0644\u0623\u0637\u0641\u0627\u0644", 25);
        SingleBarChartSegment singleBarChartSegment4 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u062d\u0628\u0648\u0646\u0627 \u0627\u0644\u0639\u0627\u0645", 35);
        SingleBarChartSegment singleBarChartSegment5 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0635\u062d\u0629 \u0627\u0644\u0646\u0641\u0633\u064a\u0629", 20);
        SingleBarChartSegment singleBarChartSegment6 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0627\u0633\u0646\u0627\u0646", 70);
        SingleBarChartSegment singleBarChartSegment7 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u062c\u0631\u0627\u062d\u0629", 63);
        SingleBarChartSegment singleBarChartSegment8 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0641\u064a\u0635\u0644", 21);
        SingleBarChartSegment singleBarChartSegment9 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0641\u0647\u062f", 19);
        SingleBarChartSegment singleBarChartSegment10 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0637\u0644\u0627\u0644", 61);
        SingleBarChartSegment singleBarChartSegment11 = new SingleBarChartSegment("\u0645\u0633\u062a\u0634\u0641\u064a \u0627\u0644\u0645\u0644\u0643 \u0633\u0644\u0645\u0627\u0646", 33);
        stackedBarChart.addBar(singleBarChartSegment);
        stackedBarChart.addBar(singleBarChartSegment1);
        stackedBarChart.addBar(singleBarChartSegment2);
        stackedBarChart.addBar(singleBarChartSegment3);
        stackedBarChart.addBar(singleBarChartSegment4);
        stackedBarChart.addBar(singleBarChartSegment5);
        stackedBarChart.addBar(singleBarChartSegment6);
        stackedBarChart.addBar(singleBarChartSegment7);
        stackedBarChart.addBar(singleBarChartSegment8);
        stackedBarChart.addBar(singleBarChartSegment9);
        stackedBarChart.addBar(singleBarChartSegment10);
        stackedBarChart.addBar(singleBarChartSegment11);
        request.setAttribute("stackedBarChart", (Object)stackedBarChart);
        this.viewHTMLReport(request, response, "/tests/GaugePOC.jsp", "");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
