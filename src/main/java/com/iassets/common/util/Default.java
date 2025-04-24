/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.logging.LOGGER_TYPE;
import com.iassets.common.util.Enums;
import com.iassets.common.util.PropertiesUtil;

public class Default {
    public static final String SESSION_LANGUAGE_ATTR_NAME = "language";
    public static final String USER_LANGUAGE_PREFERRENCE_COOKIE_NAME = "userLangPref";
    public static final String APP_NAME = "iAssets";
    public static final String BIO_APP_DIR = "bio";
    public static final String GEN_APP_DIR = "gen";
    public static final boolean ENABLE_LANGUAGE_SWITCHING = Integer.parseInt(PropertiesUtil.getSystemProperty("enableLangSwitching")) == 1;
    public static final int PPM_NOTIFICATIONS_START_DAY_OF_MONTH = Integer.parseInt(PropertiesUtil.getSystemProperty("ppmNotificationsStartDayOfMonth"));
    public static final String FILES_UPLOAD_DIR = PropertiesUtil.getSystemProperty("uploadDir");
    public static final Enums.USER_ALLOWED_APP_TYPE ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT = Enums.USER_ALLOWED_APP_TYPE.getAppTypeEnum(Integer.parseInt(PropertiesUtil.getSystemProperty("activeApps")));
    public static final String BIO_REPORT_HEADER = PropertiesUtil.getSystemProperty("bio_repohead");
    public static final String GEN_REPORT_HEADER = PropertiesUtil.getSystemProperty("gen_repohead");
    public static final String USERNAME_SUFFIX = "_old";
    public static final String PASSWORD = "123";
    public static final String MULTIVALUE_SEPARATOR = "$";
    public static final String FILE_NAME_SEPARATOR = "_sfn_";
    public static final String SEARCH_DEST_PARAM_NAME = "dest";
    public static final String JOBORDER_PHASE_ATTR_NAME = "phaseId";
    public static final String SAVE_THEN_RETURN_BTN_NAME = "saveThenReturn";
    public static final String SAVE_THEN_CONTINUE_BTN_NAME = "saveThenContinue";
    public static final String SAVE_THEN_GO_BACK_BTN_NAME = "saveThenGoBack";
    public static final String REPORT_TITLE_PARAM_NAME = "repoTitle";
    public static final String GENERAL_ERROR_MESSAGE_LITERAL_KEY = "com.iassets.common.util.Default.GENERAL_ERROR_MESSAGE_LITERAL_KEY";
    public static final String DATA_TAMPERED_MESSAGE_LITERAL_KEY = "com.iassets.common.util.Default.DATA_TAMPERED_MESSAGE_LITERAL_KEY";
    public static final String SESSION_EXPIRY_MESSAGE_LITERAL_KEY = "com.iassets.common.util.Default.SESSION_EXPIRY_MESSAGE_LITERAL_KEY";
    public static final String REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY = "com.iassets.common.util.Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY";
    public static final int RESPONSE_OUTPUT_STREAM_BUFFER_SIZE = 8192;
    public static final String DEVICE_INFO_ATTR_NAME = "deviceInfoObj";
    public static final String JOB_ORDER_INFO_ATTR_NAME = "jobOrderInfoObj";
    public static final String SP_CATEGORY_INFO_ATTR_NAME = "spCategoryObj";
    public static final String LAST_SP_PROGRESS_BILL_INFO_ATTR_NAME = "lastSpPbInfoObj";
    public static final String MAINTENANCE_REQUEST_ID_PARAM_NAME = "mReqId";
    public static final String MAINTENANCE_REQUEST_INFO_ATTR_NAME = "mReqInfoObj";
    public static final int MAX_ALLOWED_ATTACHMENT_SIZE_IN_MB = 5;
    public static final int MAX_ALLOWED_ATTACHMENTS_NO = 5;
    public static final int UNCODED_DEVICE_ID = 1;
    public static final String UNCODED_DEVICE_CODE = "000";
    public static final String ENDPOINT_NOTIFICATIONS_REPORT_FLAG_NAME = "ihf";
    public static final LOGGER_TYPE lOG_LEVEL = LOGGER_TYPE.FULLY_FEATURED;
    public static final long LICENSE_EXPIRY_NOTIFICATION_DAYS_NO = 30L;
    public static final long LICENSE_GRACE_PERIOD_IN_DAYS = 300L;
    public static final int MAX_ALLOWED_PASSWORD_CHANGE_COUNT = 2;
    public static final int MAX_ALLOWED_USERNAME_CHANGE_COUNT = 2;
    public static final String USERNAME_SEPARATOR = "@";
    public static final int NUM_OF_BIO_DEVICES_CATEGORIES = 3;

    public static void main(String[] args) {
        int x = Math.round(14.0f);
        System.out.println(x + "%");
    }
}
