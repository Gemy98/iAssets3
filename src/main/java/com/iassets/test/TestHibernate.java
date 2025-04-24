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

import com.iassets.common.servlet.CommonBasicServlet;
import com.iassets.general.entity.GenHospitalDevice;
import com.iassets.general.entity.GenHospitalDeviceAccessory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/TestHibernateAdd"})
public class TestHibernate
extends CommonBasicServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GenHospitalDevice ghd = this.commonDBQueryManager.findById(50510, GenHospitalDevice.class);
        int cho = 1;
        if (cho == 1) {
            ghd.setName("ssssssssss");
            this.commonDBQueryManager.mergeEntity(ghd);
        } else if (cho == 2) {
            GenHospitalDeviceAccessory g = new GenHospitalDeviceAccessory();
            g.setDescription("asASAXSCS");
            g.setQuantity(5);
            g.setHospitalDevice(ghd);
            ghd.getHospitalDeviceAccessories().add(g);
            this.commonDBQueryManager.mergeEntity(ghd);
        } else if (cho == 3) {
            this.commonDBQueryManager.deleteEntity(ghd);
        }
    }
}
