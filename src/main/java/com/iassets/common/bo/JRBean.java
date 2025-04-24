/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRDataSource
 */
package com.iassets.common.bo;

import com.iassets.common.entity.CommonSite;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRDataSource;

public class JRBean {
    protected CommonSite currentSite;
    protected HashMap<String, Object> params;
    protected String jasperPrintPath;
    protected JRDataSource dataSource;
    protected Connection dbConnection;
    protected String displayName;

    public JRBean(CommonSite currentSite) {
        this.currentSite = currentSite;
    }

    public CommonSite getCurrentSite() {
        return this.currentSite;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDBConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getDBConnection() {
        return this.dbConnection;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }

    public HashMap<String, Object> getParams() {
        return this.params;
    }

    public void setJasperPrintPath(String jpFileName) {
        this.jasperPrintPath = jpFileName;
    }

    public String getJasperPrintPath() {
        return this.jasperPrintPath;
    }

    public void setDataSource(JRDataSource ds) {
        this.dataSource = ds;
    }

    public JRDataSource getDataSource() {
        return this.dataSource;
    }
}
