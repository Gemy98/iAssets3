/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.iassets.common.servlet;

import com.iassets.common.util.Common;
import com.iassets.common.util.Default;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.WebUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value={"/FileViewer"})
public class FileViewerServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = WebUtil.getSessionLanguage(request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String fileName = WebUtil.decodeQueryStringParameter(request, "file_name");
        if (fileName != null) {
            byte[] buffer = new byte[8192];
            FileInputStream fis = null;
            OutputStream os = null;
            try {
                File f = new File(Default.FILES_UPLOAD_DIR + fileName);
                fis = new FileInputStream(f);
                response.setContentType("*/*");
                response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
                response.setContentLength((int)f.length());
                os = response.getOutputStream();
                int byteRead = 0;
                while ((byteRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, byteRead);
                    os.flush();
                }
            }
            catch (FileNotFoundException ex) {
                Common.log(ex);
                throw new FileNotFoundException(LocalizationManager.getLiteral("servlet.FileViewer.msg1", langCode));
            }
            catch (Exception ex) {
                Common.log(ex);
                throw new ServletException(LocalizationManager.getLiteral("servlet.FileViewer.msg2", langCode));
            }
            finally {
                if (fis != null) {
                    fis.close();
                }
                if (os != null) {
                    os.close();
                }
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
