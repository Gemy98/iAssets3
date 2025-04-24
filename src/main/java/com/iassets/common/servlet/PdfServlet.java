/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.text.Document
 *  com.itextpdf.text.DocumentException
 *  com.itextpdf.text.FontFactory
 *  com.itextpdf.text.pdf.PdfWriter
 *  com.itextpdf.tool.xml.XMLWorkerHelper
 *  javax.servlet.ServletException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PdfServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FontFactory.registerDirectories();
        Document document = new Document();
        ServletOutputStream out = response.getOutputStream();
        try {
            FontFactory.register((String)"C:\\Windows\\Fonts\\ARIALUNI.TTF", (String)"Arial Unicode MS");
            FontFactory.register((String)"C:\\Windows\\Fonts\\andlso.ttf");
            response.setContentType("application/pdf");
            PdfWriter writer = PdfWriter.getInstance((Document)document, (OutputStream)response.getOutputStream());
            writer.setRunDirection(3);
            document.open();
            String style = "<link type=\"nameLiteral/css\" rel=\"stylesheet\" href=\"http://localhost:8080/MMP/js/jquery.tablesorter/themes/blue/style_pdf.css\"></link>";
            String html = "<meta http-equiv=\"content-type\" content=\"nameLiteral/html\";charset=\"utf-8\" /><table id=\"sp_list\" class=\"tablesorter\" style=\"dir:rtl\">\t\t<thead>\t\t\t<tr>\t\t\t\t<th>\u0645</th>\t\t\t\t<th class=\"header\">\u0627\u0639\u0648\u0636</th>\t\t\t\t<th class=\"header\" style=\"font-family:arial\">\u0627\u0644\u0627\u0633\u0645</th>\t\t\t\t<th class=\"header\">\u0627\u0644\u0643\u0645\u064a\u0629 \u0627\u0644\u0645\u062a\u0627\u062d\u0629</th>\t\t\t\t<th class=\"header\">\u0627\u0644\u062d\u062f \u0627\u0644\u0623\u062f\u0646\u0649</th>\t\t\t\t<th class=\"header\">\u0645\u062a\u0648\u0633\u0637 \u0633\u0639\u0631 \u0627\u0644\u0642\u0637\u0639\u0629 \u0628\u0627\u0644\u0631\u064a\u0627\u0644 \u0627\u0644\u0633\u0639\u0648\u062f\u064a</th>\t\t\t</tr>\t\t</thead>\t\t<tbody>\t\t\t\t\t\t<tr class=\"odd\">\t\t\t\t<td align=\"center\">1</td>\t\t\t\t<td><a href=\"/MMP/SearchForSPCategoryProcess?dest=update_spcategory&amp;code=abc\" target=\"_blank\"> abc</a></td>\t                            \t\t\t\t<td>\u0641\u064a\u0634 \u0643\u0647\u0631\u0628\u0627\u0621</td>\t            <td>10</td>\t            <td>10</td>\t            <td>5.50</td>     \t\t\t</tr>\t\t\t\t\t\t<tr class=\"even\">\t\t\t\t<td align=\"center\">2</td>\t\t\t\t<td><a href=\"/MMP/SearchForSPCategoryProcess?dest=update_spcategory&amp;code=def\" target=\"_blank\"> def</a></td>\t                            \t\t\t\t<td>\u0645\u0643\u062b\u0641\u0627\u062a</td>\t            <td>80</td>\t            <td>50</td>\t            <td>0.20</td>     \t\t\t</tr>\t\t\t\t\t</tbody>\t</table>\t      ";
            html = style + html;
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, (Reader)new StringReader(html));
            document.addAuthor("Awad Khalil Auther");
            document.addCreator("awad Creator");
            document.addSubject("Awad Subject");
            document.addTitle("Awad Title");
            document.close();
            PdfWriter.getInstance((Document)document, (OutputStream)out);
            System.out.println("PDF Created!-----------");
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
