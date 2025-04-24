/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.test;

import com.iassets.biomedical.entity.BioHospitalDevice;
import com.iassets.common.servlet.CommonBasicServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/TestPPMVistis"})
public class TestFixDevicePPMVists
extends CommonBasicServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("start fixing");
        List ghd = this.commonDBQueryManager.findAll(BioHospitalDevice.class);
//        for (BioHospitalDevice bioHospitalDevice : ghd) {
//            int secondMonth;
//            String pmVists = bioHospitalDevice.getPmVisitsMonths();
//            if (pmVists == null || pmVists == "") continue;
//            int firstMonth = Integer.parseInt(pmVists.split("[$]")[1]);
//            if (firstMonth > 6) {
//                secondMonth = firstMonth;
//                firstMonth -= 6;
//            } else {
//                secondMonth = firstMonth + 6;
//            }
//            bioHospitalDevice.setPmVisitsMonths("$" + firstMonth + "$" + secondMonth + "$");
//            this.commonDBQueryManager.mergeEntity(bioHospitalDevice);
//        }
        System.out.println("end fixing");
    }
}
