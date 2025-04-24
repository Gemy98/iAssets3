/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 */
package com.iassets.test;

import com.iassets.biomedical.DB.BioDashboardDBQueryManager;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@WebServlet(value={"/TestServlet"})
public class TestServlet
extends CommonBasicServlet {
    @Autowired
    @Qualifier(value="bioDashboardDBQueryManager")
    protected BioDashboardDBQueryManager bioDashboardDBQueryManager;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("start request");
        System.out.println("end r");
    }
}
