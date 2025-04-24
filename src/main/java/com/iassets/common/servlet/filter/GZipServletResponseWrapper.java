/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpServletResponseWrapper
 */
package com.iassets.common.servlet.filter;

import com.iassets.common.servlet.filter.GZipServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class GZipServletResponseWrapper
extends HttpServletResponseWrapper {
    private GZipServletOutputStream gzipOutputStream = null;
    private PrintWriter printWriter = null;

    public GZipServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
    }

    public void close() throws IOException {
        if (this.printWriter != null) {
            this.printWriter.close();
        }
        if (this.gzipOutputStream != null) {
            this.gzipOutputStream.close();
        }
    }

    public void flushBuffer() throws IOException {
        if (this.printWriter != null) {
            this.printWriter.flush();
        }
        IOException exception1 = null;
        try {
            if (this.gzipOutputStream != null) {
                this.gzipOutputStream.flush();
            }
        }
        catch (IOException e) {
            exception1 = e;
        }
        IOException exception2 = null;
        try {
            super.flushBuffer();
        }
        catch (IOException e) {
            exception2 = e;
        }
        if (exception1 != null) {
            throw exception1;
        }
        if (exception2 != null) {
            throw exception2;
        }
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this.printWriter != null) {
            throw new IllegalStateException("PrintWriter obtained already - cannot get OutputStream");
        }
        if (this.gzipOutputStream == null) {
            this.gzipOutputStream = new GZipServletOutputStream((OutputStream)this.getResponse().getOutputStream());
        }
        return this.gzipOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (this.printWriter == null && this.gzipOutputStream != null) {
            throw new IllegalStateException("OutputStream obtained already - cannot get PrintWriter");
        }
        if (this.printWriter == null) {
            this.gzipOutputStream = new GZipServletOutputStream((OutputStream)this.getResponse().getOutputStream());
            this.printWriter = new PrintWriter(new OutputStreamWriter((OutputStream)((Object)this.gzipOutputStream), this.getResponse().getCharacterEncoding()));
        }
        return this.printWriter;
    }

    public void setContentLength(int len) {
    }
}
