/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JasperExportManager
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 */
package com.iassets.common.util;

import com.iassets.common.bo.JRBean;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperUtil {
    public static void exportReportToResponseOutputStreamAsPDF(JRBean b, HttpServletResponse response) throws Exception {
        String fileName = new String(b.getDisplayName().getBytes("utf-8"), "iso-8859-1");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        long start = System.currentTimeMillis();
        ServletOutputStream os = response.getOutputStream();
        JasperPrint jPrint = JasperFillManager.fillReport((String)b.getJasperPrintPath(), b.getParams(), (JRDataSource)b.getDataSource());
        JasperExportManager.exportReportToPdfStream((JasperPrint)jPrint, (OutputStream)os);
        os.close();
        System.out.println("PDF streaming takes: " + (System.currentTimeMillis() - start) + " ms");
    }
}
