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

import com.iassets.common.dashboard.dtos.LabelValueDTO;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/CommonDashboardServlet"})
public class CommonDashboardServlet
extends CommonBasicServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("Jan");
        categoryList.add("Feb");
        categoryList.add("Mar");
        categoryList.add("Apr");
        request.setAttribute("categories", categoryList);
        HashMap<String, List<Number>> nameDataMap = new HashMap<String, List<Number>>();
        nameDataMap.put("Tokyo", Arrays.asList(49.9, 71.5, 106.4, 5));
        nameDataMap.put("New York", Arrays.asList(83.6, 78.8, 98.5));
        nameDataMap.put("London", Arrays.asList(48.9, 38.8, 39.3));
        nameDataMap.put("Berlin", Arrays.asList(42.4, 33.2, 34.5));
        request.setAttribute("nameDataMap", nameDataMap);
        HashMap<String, List<Double>> nameDataMap2 = new HashMap<String, List<Double>>();
        nameDataMap2.put("Tokyo", Arrays.asList(49.9));
        nameDataMap2.put("New York", Arrays.asList(83.6));
        nameDataMap2.put("London", Arrays.asList(48.9));
        nameDataMap2.put("Berlin", Arrays.asList(42.4));
        request.setAttribute("nameDataMap2", nameDataMap2);
        HashMap<String, List<Double>> nameDataMap3 = new HashMap<String, List<Double>>();
        nameDataMap3.put("Tokyo", Arrays.asList(49.9, 71.5, 106.4));
        nameDataMap3.put("New York", Arrays.asList(83.6, 78.8, 98.5));
        nameDataMap3.put("London", Arrays.asList(48.9, 38.8, 39.3));
        nameDataMap3.put("Berlin", Arrays.asList(42.4, 33.2, 34.5));
        request.setAttribute("nameDataMap3", nameDataMap3);
        int data = (int)(Math.random() * 100.0 % 101.0);
        ArrayList<LabelValueDTO> entries = new ArrayList<LabelValueDTO>();
        entries.add(new LabelValueDTO("\u0625\u062c\u0645\u0627\u0644\u0649 \u0623\u0648\u0627\u0645\u0631 \u0627\u0644\u0639\u0645\u0644", "100"));
        entries.add(new LabelValueDTO("\u0623\u0648\u0627\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0627\u0644\u0645\u0641\u062a\u0648\u062d\u0629", 100 - data + ""));
        entries.add(new LabelValueDTO("\u0623\u0648\u0627\u0645\u0631 \u0627\u0644\u0639\u0645\u0644 \u0627\u0644\u0645\u063a\u0644\u0642\u0629", data + ""));
        request.setAttribute("entries", entries);
        this.basicForward(request, response, "/tests/GaugePOC.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
