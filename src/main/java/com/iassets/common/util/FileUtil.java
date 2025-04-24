/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.Part
 *  org.apache.commons.lang.ArrayUtils
 */
package com.iassets.common.util;

import com.iassets.common.bo.FileUploadConfig;
import com.iassets.common.util.Common;
import com.iassets.common.util.Default;
import com.iassets.common.util.WebUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.lang.ArrayUtils;

public class FileUtil {
    public static String[] uploadFiles(HttpServletRequest request, String fieldName, FileUploadConfig config) throws IOException, FileNotFoundException, ServletException {
        String directorateId = "d" + WebUtil.getSessionDirectorate(request).getId();
        String siteId = "s" + WebUtil.getSessionSite(request).getId();
        String currentAppDir = WebUtil.getCurrentlyActiveAppDirectory(request);
        String hospId = "h" + WebUtil.getSessionLocation(request).getId();
        config.setUploadSubDirectory(directorateId + "/" + siteId + "/" + currentAppDir + "/" + hospId);
        Object[] ary1 = FileUtil.uploadFiles(request.getParts(), fieldName, config);
        Object[] ary2 = request.getParameterValues(fieldName + "_PrevUploadedFiles");
        return (String[])ArrayUtils.addAll((Object[])ary1, (Object[])ary2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String[] uploadFiles(Collection<Part> fileTypeParts, String fieldName, FileUploadConfig config) throws IOException, FileNotFoundException {
        ArrayList<String> uploadedFilesNames = new ArrayList<String>();
        if (fileTypeParts != null && fileTypeParts.size() > 0 && fieldName != null) {
            Object[] objects = fileTypeParts.toArray();
            OutputStream out = null;
            InputStream filecontent = null;
            try {
                String fileName = null;
                String subDir = null;
                for (int i = 0; i < objects.length; ++i) {
                    Part p = (Part)objects[i];
                    if (!FileUtil.uploadedFile(p) || !fieldName.equals(p.getName())) continue;
                    subDir = FileUtil.getUploadSubDirectory(config);
                    fileName = fieldName + "_sfn_" + FileUtil.getTimeStampString() + "_sfn_" + FileUtil.getFileName(p);
                    FileUtil.createFileDirectory(Default.FILES_UPLOAD_DIR + subDir);
                    File file = new File(Default.FILES_UPLOAD_DIR + subDir + fileName);
                    out = new FileOutputStream(file);
                    filecontent = p.getInputStream();
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    uploadedFilesNames.add(subDir + fileName);
                }
            }
            finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        }
        return uploadedFilesNames.size() == 0 ? null : uploadedFilesNames.toArray(new String[uploadedFilesNames.size()]);
    }

    private static boolean uploadedFile(Part part) {
        String sentFileName = FileUtil.getFileName(part);
        return sentFileName != null && !sentFileName.isEmpty();
    }

    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (!content.trim().startsWith("filename")) continue;
            return content.substring(content.indexOf(61) + 1).trim().replace("\"", "");
        }
        return null;
    }

    private static String getUploadSubDirectory(FileUploadConfig config) throws IOException {
        if (config == null || config.getType() == null || config.getUploadSubDirectory() == null) {
            throw new IOException("FileUtil:: file upload configuration error");
        }
        return config.getUploadSubDirectory();
    }

    private static void createFileDirectory(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    protected static synchronized String getTimeStampString() {
        return new Timestamp(new Date().getTime()).toString().replaceAll(" ", "_").replaceAll(":", "");
    }

    public static void main(String[] args) {
        String s = "f1_2014-06-05_150535.305_box.gif";
        Common.log(s.substring(s.lastIndexOf("_") + 1));
    }
}
