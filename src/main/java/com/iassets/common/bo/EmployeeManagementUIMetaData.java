/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo;

import com.iassets.common.entity.CommonEmployee;

public class EmployeeManagementUIMetaData {
    private String pageTitle;
    private CommonEmployee employee;
    private String viewRelativeURL;
    private boolean enableAddFunction;
    private boolean enableUpdateFunction;
    private boolean enableDeleteFunction;

    public EmployeeManagementUIMetaData() {
    }

    public EmployeeManagementUIMetaData(String pageTitle, CommonEmployee employee, String viewRelativeURL, boolean enableAddFunction, boolean enableUpdateFunction, boolean enableDeleteFunction) {
        this.pageTitle = pageTitle;
        this.employee = employee;
        this.viewRelativeURL = viewRelativeURL;
        this.enableAddFunction = enableAddFunction;
        this.enableUpdateFunction = enableUpdateFunction;
        this.enableDeleteFunction = enableDeleteFunction;
    }

    public EmployeeManagementUIMetaData(String pageTitle, CommonEmployee employee, String viewRelativeURL) {
        this(pageTitle, employee, viewRelativeURL, true, true, false);
    }

    public String getPageTitle() {
        return this.pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public CommonEmployee getEmployee() {
        return this.employee;
    }

    public void setEmployee(CommonEmployee employee) {
        this.employee = employee;
    }

    public String getViewRelativeURL() {
        return this.viewRelativeURL;
    }

    public void setViewRelativeURL(String viewRelativeURL) {
        this.viewRelativeURL = viewRelativeURL;
    }

    public boolean deleteFunctionEnabled() {
        return this.enableDeleteFunction;
    }

    public void setEnableDeleteFunction(boolean enableDeleteFunction) {
        this.enableDeleteFunction = enableDeleteFunction;
    }

    public boolean isEnableAddFunction() {
        return this.enableAddFunction;
    }

    public void setEnableAddFunction(boolean enableAddFunction) {
        this.enableAddFunction = enableAddFunction;
    }

    public boolean isEnableUpdateFunction() {
        return this.enableUpdateFunction;
    }

    public void setEnableUpdateFunction(boolean enableUpdateFunction) {
        this.enableUpdateFunction = enableUpdateFunction;
    }

    public boolean isEnableDeleteFunction() {
        return this.enableDeleteFunction;
    }
}
