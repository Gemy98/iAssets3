/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 */
package com.iassets.common.servlet.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;

class GZipServletOutputStream
extends ServletOutputStream {
    private GZIPOutputStream gzipOutputStream = null;

    public GZipServletOutputStream(OutputStream output) throws IOException {
        this.gzipOutputStream = new GZIPOutputStream(output);
    }

    public void close() throws IOException {
        this.gzipOutputStream.close();
    }

    public void flush() throws IOException {
        this.gzipOutputStream.flush();
    }

    public void write(byte[] b) throws IOException {
        this.gzipOutputStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.gzipOutputStream.write(b, off, len);
    }

    public void write(int b) throws IOException {
        this.gzipOutputStream.write(b);
    }
}
