/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo;

public class FileUploadConfig {
    public UPLOADS_TYPE type;
    private String uploadSubDirectory;

    public FileUploadConfig(UPLOADS_TYPE type) {
        this.type = type;
    }

    public UPLOADS_TYPE getType() {
        return this.type;
    }

    public void setType(UPLOADS_TYPE type) {
        this.type = type;
    }

    public String getUploadSubDirectory() {
        return this.uploadSubDirectory + "/" + this.type.getSubDirectory();
    }

    public void setUploadSubDirectory(String uploadSubDirectory) {
        this.uploadSubDirectory = uploadSubDirectory;
    }

    public static enum UPLOADS_TYPE {
        DEVICE_UPLOADS("equip/"),
        PPM_UPLOADS("ppm/"),
        INTERNAL_PPM_UPLOADS("ppm/internal/"),
        JOBORDER_UPLOADS("jo/"),
        SITE_ADMINISTRATION_UPLOADS("sadmin/"),
        SPARE_PARTS_WAREHOUSE("spwh/");

        String subDirectory;

        private UPLOADS_TYPE(String subDirectory) {
            this.subDirectory = subDirectory;
        }

        public String getSubDirectory() {
            return this.subDirectory;
        }
    }
}
