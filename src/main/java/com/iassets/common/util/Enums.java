/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.util;

import com.iassets.common.util.LocalizationManager;
import java.util.ArrayList;
import java.util.List;

public class Enums {

    public static enum GPPM_PERIOD {
        ANNUAL(1, "common.util.Enums.GPPM_PERIOD.ANNUAL"),
        SEMI_ANNUAL(2, "common.util.Enums.GPPM_PERIOD.SEMI_ANNUAL"),
        QUARTER(3, "common.util.Enums.GPPM_PERIOD.QUARTER"),
        MONTHLY(4, "common.util.Enums.GPPM_PERIOD.MONTHLY"),
        SEMI_MONTHLY(5, "common.util.Enums.GPPM_PERIOD.SEMI_MONTHLY");

        protected int id;
        protected String nameLiteralKey;

        private GPPM_PERIOD(int id, String nameLiteralKey) {
            this.id = id;
            this.nameLiteralKey = nameLiteralKey;
        }

        public int getId() {
            return this.id;
        }

        public String getName(String langCode) {
            return LocalizationManager.getLiteral(this.nameLiteralKey, langCode);
        }

        public static GPPM_PERIOD getGPPMPeriod(int id) {
            for (GPPM_PERIOD l : GPPM_PERIOD.values()) {
                if (id != l.getId()) continue;
                return l;
            }
            return null;
        }
    }

    public static enum LICENSE_STATUS {
        INACTIVE(0, "common.util.Enums.LICENSE_STATUS.INACTIVE"),
        ACTIVE(1, "common.util.Enums.LICENSE_STATUS.ACTIVE"),
        NOT_PAID(2, "common.util.Enums.LICENSE_STATUS.NOT_PAID"),
        EXPIRED_WITHIN_GRACE_PERIOD(3, "common.util.Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD"),
        EXPIRED(4, "common.util.Enums.LICENSE_STATUS.EXPIRED");

        protected int id;
        protected String messageLiteralKey;

        private LICENSE_STATUS(int id, String messageLiteralKey) {
            this.id = id;
            this.messageLiteralKey = messageLiteralKey;
        }

        public int getId() {
            return this.id;
        }

        public String getMessage(String langCode) {
            return LocalizationManager.getLiteral(this.messageLiteralKey, langCode);
        }

        public static LICENSE_STATUS getLicenseStatusEnum(int id) {
            for (LICENSE_STATUS l : LICENSE_STATUS.values()) {
                if (id != l.getId()) continue;
                return l;
            }
            return null;
        }
    }

    public static enum PROGRESS_BILL_TYPE {
        PPM(1),
        SPARE_PARTS(2);

        protected int id;

        private PROGRESS_BILL_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum REQUESTED_REPORT_TYPE {
        FOR_ALL_SITE_LOCATIONS(1),
        FOR_CURRENT_LOCATION_ONLY(2),
        FOR_ANOTHER_LOCATION(3);

        protected int id;

        private REQUESTED_REPORT_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum JOB_ORDER_TYPE {
        EQUIPMENT_OUT_OF_ORDER(1),
        INSTALLATION(2),
        CALIBRATION(3),
        PPM(4);

        protected int dbId;

        private JOB_ORDER_TYPE(int dbId) {
            this.dbId = dbId;
        }

        public int getDBId() {
            return this.dbId;
        }
    }

    public static enum JOB_ORDER_PRIORITY {
        NORMAL(1),
        URGENT(2),
        TOP_URGENT(3);

        protected int dbId;

        private JOB_ORDER_PRIORITY(int dbId) {
            this.dbId = dbId;
        }

        public int getDBId() {
            return this.dbId;
        }
    }

    public static enum DEVICE_CATEGORY {
        A(1),
        B(2),
        C(3);

        protected int dbId;

        private DEVICE_CATEGORY(int dbId) {
            this.dbId = dbId;
        }

        public int getDBId() {
            return this.dbId;
        }
    }

    public static enum JOB_ORDER_STATUS {
        CLOSED(0),
        CANCELLED(1),
        OPENED(2),
        LATE(3);

        protected int status;

        private JOB_ORDER_STATUS(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }
    }

    public static enum DEVICE_STATUS {
        SCRAPPED(0),
        WORK_PROPERLY(1),
        UNDER_MAINTENANCE(2),
        TRANSFERRED(3),
        IDLE(4);

        protected int status;

        private DEVICE_STATUS(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }
    }

    public static enum REPORT_TITLE {
        PROGRESS_BILL_PPM("common.util.Enums.REPORT_TITLE.PROGRESS_BILL_PPM"),
        PROGRESS_BILL_SPARE_PARTS("common.util.Enums.REPORT_TITLE.PROGRESS_BILL_SPARE_PARTS"),
        ONSITE_OPERATING_COMPANY_LABORERS("common.util.Enums.REPORT_TITLE.ONSITE_OPERATING_COMPANY_LABORERS"),
        HOSPITAL_DEVICES("common.util.Enums.REPORT_TITLE.HOSPITAL_DEVICES"),
        HOSPITAL_DEVICES_STATUS("common.util.Enums.REPORT_TITLE.HOSPITAL_DEVICES_STATUS"),
        DEVICES_UNDER_MAINTENANCE("common.util.Enums.REPORT_TITLE.DEVICES_UNDER_MAINTENANCE"),
        DEVICE_HISTORY("common.util.Enums.REPORT_TITLE.DEVICE_HISTORY"),
        DEVICE_ACCESSORIES("common.util.Enums.REPORT_TITLE.DEVICE_ACCESSORIES"),
        DEVICE_BY_WARRANTY_EXPIRE("common.util.Enums.REPORT_TITLE.DEVICE_BY_WARRANTY_EXPIRE"),
        PPM_TABLE("common.util.Enums.REPORT_TITLE.PPM_TABLE"),
        PPM_TABLE_WITHIN_ALL("common.util.Enums.REPORT_TITLE.PPM_TABLE_ALL"),
        PPM_TABLE_ALL("common.util.Enums.REPORT_TITLE.PPM_TABLE_ALL"),
        PPM_TABLE_WITHIN_WARRANTY("common.util.Enums.REPORT_TITLE.PPM_TABLE_WITHIN_WARRANTY"),
        PPM_TABLE_OUTSIDE_WARRANTY("common.util.Enums.REPORT_TITLE.PPM_TABLE_OUTSIDE_WARRANTY"),
        PPM_NOT_RECORDED_VISITS("common.util.Enums.REPORT_TITLE.PPM_NOT_RECORDED_VISITS"),
        DEVICES_NEED_PPM_VISIT("common.util.Enums.REPORT_TITLE.DEVICES_NEED_PPM_VISIT"),
        PPM_ATTACHED_FILES("common.util.Enums.REPORT_TITLE.PPM_ATTACHED_FILES"),
        DEVICES_NOT_IN_CONTRACT("common.util.Enums.REPORT_TITLE.DEVICES_NOT_IN_CONTRACT"),
        DEVICES_FROM_OTHER_SITE("common.util.Enums.REPORT_TITLE.DEVICES_FROM_OTHER_SITE"),
        TRANSFERRED_DEVICES("common.util.Enums.REPORT_TITLE.TRANSFERRED_DEVICES"),
        SCRAPPED_DEVICES("common.util.Enums.REPORT_TITLE.SCRAPPED_DEVICES"),
        NOT_HAPPENED_PPM_VISITS("common.util.Enums.REPORT_TITLE.NOT_HAPPENED_PPM_VISITS"),
        JOB_ORDER_CANCELLED("common.util.Enums.REPORT_TITLE.JOB_ORDER_CANCELLED"),
        JOB_ORDER_CLOSED("common.util.Enums.REPORT_TITLE.JOB_ORDER_CLOSED"),
        LATE_JOB_ORDERS("common.util.Enums.REPORT_TITLE.LATE_JOB_ORDERS"),
        LATE_JOB_ORDERS_WITHIN_WARRANTY("common.util.Enums.REPORT_TITLE.LATE_JOB_ORDERS_WITH_WARRANTY"),
        LATE_JOB_ORDERS_OUTSIDE_WARRANTY("common.util.Enums.REPORT_TITLE.LATE_JOB_ORDERS_OUTSIDE_WARRANTY"),
        OPENED_JOB_ORDERS("common.util.Enums.REPORT_TITLE.OPENED_JOB_ORDERS"),
        OPENED_JOB_ORDERS_WITHIN_WARRANTY("common.util.Enums.REPORT_TITLE.OPENED_JOB_ORDERS_WITH_WARRANTY"),
        OPENED_JOB_ORDERS_OUTSIDE_WARRANTY("common.util.Enums.REPORT_TITLE.OPENED_JOB_ORDERS_OUTSIDE_WARRANTY"),
        SP_INVENTORY_CONTENT("common.util.Enums.REPORT_TITLE.SP_INVENTORY_CONTENT"),
        SP_INVENTORY_CONTENT_UNDER_THRESHOLD("common.util.Enums.REPORT_TITLE.SP_INVENTORY_CONTENT_UNDER_THRESHOLD"),
        DEVICE_DETAILS("common.util.Enums.REPORT_TITLE.DEVICE_DETAILS"),
        BIO_MAINTENANCE_REQUEST_LIST("common.util.Enums.REPORT_TITLE.BIO_MAINTENANCE_REQUEST_LIST"),
        GEN_MAINTENANCE_REQUEST_LIST("common.util.Enums.REPORT_TITLE.GEN_MAINTENANCE_REQUEST_LIST"),
        BIO_DIRECTORATE_MONTHLY_MAINTENANCE_PERFORMANCE("common.util.Enums.REPORT_TITLE.BIO_DIRECTORATE_MONTHLY_MAINTENANCE_PERFORMANCE");

        protected String reportTitleLiteralKey;

        private REPORT_TITLE(String reportTitleLiteralKey) {
            this.reportTitleLiteralKey = reportTitleLiteralKey;
        }

        public String getReportTitle(String langCode) {
            return LocalizationManager.getLiteral(this.reportTitleLiteralKey, langCode);
        }
    }

    public static enum PPM_VISIT_STATUS {
        IN_TIME(1, "common.util.Enums.PPM_VISIT_STATUS.IN_TIME"),
        NOT_HAPPENED(2, "common.util.Enums.PPM_VISIT_STATUS.NOT_HAPPENED"),
        LATE(3, "common.util.Enums.PPM_VISIT_STATUS.LATE");

        protected int id;
        protected String nameLiteralKey;

        private PPM_VISIT_STATUS(int id, String nameLiteralKey) {
            this.id = id;
            this.nameLiteralKey = nameLiteralKey;
        }

        public int getId() {
            return this.id;
        }

        public String getName(String langCode) {
            return LocalizationManager.getLiteral(this.nameLiteralKey, langCode);
        }
    }

    public static enum YEAR_MONTHS {
        JAN("1", "common.util.Enums.YEAR_MONTHS.m1"),
        FEB("2", "common.util.Enums.YEAR_MONTHS.m2"),
        MAR("3", "common.util.Enums.YEAR_MONTHS.m3"),
        APR("4", "common.util.Enums.YEAR_MONTHS.m4"),
        MAY("5", "common.util.Enums.YEAR_MONTHS.m5"),
        JUN("6", "common.util.Enums.YEAR_MONTHS.m6"),
        JUL("7", "common.util.Enums.YEAR_MONTHS.m7"),
        AUG("8", "common.util.Enums.YEAR_MONTHS.m8"),
        SEP("9", "common.util.Enums.YEAR_MONTHS.m9"),
        OCT("10", "common.util.Enums.YEAR_MONTHS.m10"),
        NOV("11", "common.util.Enums.YEAR_MONTHS.m11"),
        DEC("12", "common.util.Enums.YEAR_MONTHS.m12");

        String id;
        String nameLiteral;

        private YEAR_MONTHS(String id, String nameLiteral) {
            this.id = id;
            this.nameLiteral = nameLiteral;
        }

        public String getId() {
            return this.id;
        }

        public String getName(String langCode) {
            return LocalizationManager.getLiteral(this.nameLiteral, langCode);
        }

        public static String getNameById(String id, String langCode) {
            for (YEAR_MONTHS i : YEAR_MONTHS.values()) {
                if (!i.getId().equals(id)) continue;
                return i.getName(langCode);
            }
            return "no match found";
        }
    }

    public static enum JOBORDER_SP_SOURCES {
        INVENTORY(1),
        AGENT(2),
        PURCHASE_ORDER(3);

        int id;

        private JOBORDER_SP_SOURCES(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum JOBORDER_FOLLOWUP_PHASES {
        NOT_HANDLED_YET(0),
        FIRST_ACTION_TAKEN(1),
        AGENT_REPORT_UPLOADED(21),
        QUOTATION_RECIEVED(22),
        SECOND_ACTION_TAKEN(2),
        FINAL_ACTION_TAKEN(3);

        int id;

        private JOBORDER_FOLLOWUP_PHASES(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }

    public static enum USER_TYPE {
        HOSPITAL_DIRECTOR(1),
        HOSPITAL_ASSISTANT_DIRECTOR(2),
        HOSPITAL_DEPARTMENT_SUPERVISOR(5),
        BIOMEDICAL_DEPARTMENT_HEAD(3),
        BIOMEDICAL_DEPARTMENT_SUPERVISOR(4),
        BIOMEDICAL_SITE_MANGER(6),
        BIOMEDICAL_ENGINEER(7),
        BIOMEDICAL_TECHNICIAN(8),
        BIOMEDICAL_CHEMIST(16),
        BIOMEDICAL_DIRECTORATE_ADMIN(9),
        BIOMEDICAL_DIRECTORATE_VICE_ADMIN(10),
        BIOMEDICAL_DIRECTORATE_ASSISTANT_ADMIN(11),
        BIOMEDICAL_COMPUTER_OPERATOR(13),
        BIOMEDICAL_INVENTORY_KEEPER(14),
        BIOMEDICAL_DRIVER(17),
        GENERAL_DEPARTMENT_HEAD(103),
        GENERAL_DEPARTMENT_SUPERVISOR(104),
        GENERAL_SITE_MANGER(106),
        GENERAL_SPECIALIST_SITE_MANGER(105),
        GENERAL_ENGINEER(107),
        GENERAL_TECHNICIAN(108),
        GENERAL_DIRECTORATE_ADMIN(109),
        GENERAL_DIRECTORATE_VICE_ADMIN(110),
        GENERAL_DIRECTORATE_ASSISTANT_ADMIN(111),
        GENERAL_INVENTORY_KEEPER(114),
        DIRECTORATE_SUPER_ADMIN(1000),
        DIRECTORATE_VICE_SUPER_ADMIN(1001),
        BIO_REGIONAL_DIRECTOR(2000),
        GEN_REGIONAL_DIRECTOR(2001);

        protected int id;

        private USER_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static USER_TYPE getUserTypeById(int id) {
            for (USER_TYPE ut : USER_TYPE.values()) {
                if (id != ut.getId()) continue;
                return ut;
            }
            return null;
        }
    }

    public static enum SYS_ROLES {
        BIO_REGIONAL_DIRECTOR("bioRegionDirector"),
        GEN_REGIONAL_DIRECTOR("genRegionDirector"),
        DIRECTORATE_SUPER_ADMIN("dirsuperadmin"),
        BIO_AND_GEN_INSPECTOR("bioNgenInspector"),
        BIO_AND_GEN_END_USER("bioNgenEndUser"),
        BIO_SITE_MANAGER("bioSiteManager"),
        BIO_ENGINEER("bioEng"),
        BIO_INSPECTOR("bioInspector"),
        BIO_INVENTORY_KEEPER("bioInventoryKeeper"),
        BIO_SITES_ADMINISTRATOR("bioSitesAdministration"),
        BIO_DIRECTORATE_SUPERVISORS_ASSIGNATION("bioDirSupervisorsAssignation"),
        BIO_ONSITE_ADMINISTRATION("bioOnsiteAdministration"),
        BIO_CONTRACTOR_EVALUATION("bioContractorEvaluation"),
        BIO_DIRECTORATE_DASHBOARD("bioDirectorateDashboard"),
        BIO_SITE_DASHBOARD("bioSiteDashboard"),
        GEN_SITE_MANAGER("genSiteManager"),
        GEN_ENGINEER("genEng"),
        GEN_INSPECTOR("genInspector"),
        GEN_INVENTORY_KEEPER("genInventoryKeeper"),
        GEN_PERFORMANCE_EVALUATOR("genPerformanceEvaluator"),
        GEN_SITES_ADMINISTRATOR("genSitesAdministration"),
        GEN_DIRECTORATE_SUPERVISORS_ASSIGNATION("genDirSupervisorsAssignation");

        String roleName;

        private SYS_ROLES(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return this.roleName;
        }
    }

    public static enum USER_ALLOWED_APP_TYPE {
        BIOMEDICAL_MAINTENANCE_APP(1, "bio"),
        GENERAL_MAINTENANCE_APP(2, "gen"),
        BOTH_MAINTENANCE_APP(3, "undetermined");

        int id;
        String appDir;

        private USER_ALLOWED_APP_TYPE(int id, String appDir) {
            this.id = id;
            this.appDir = appDir;
        }

        public int getId() {
            return this.id;
        }

        public String getAppDirectory() {
            return this.appDir;
        }

        public static USER_ALLOWED_APP_TYPE getAppTypeEnum(int appTypeId) {
            for (USER_ALLOWED_APP_TYPE t : USER_ALLOWED_APP_TYPE.values()) {
                if (appTypeId != t.getId()) continue;
                return t;
            }
            return null;
        }
    }

    public static enum SUPPORTED_LANGUAGES {
        ARABIC("ar", "\u0639\u0631\u0628\u064a"),
        ENGLISH("en", "english");

        String code;
        String displayName;

        private SUPPORTED_LANGUAGES(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getLanguageCode() {
            return this.code;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public static List<String> getSupportedLanguagesCodes() {
            ArrayList<String> list = new ArrayList<String>();
            for (SUPPORTED_LANGUAGES l : SUPPORTED_LANGUAGES.values()) {
                list.add(l.getLanguageCode());
            }
            return list;
        }

        public static SUPPORTED_LANGUAGES getLanguageEnumByCode(String code) {
            for (SUPPORTED_LANGUAGES l : SUPPORTED_LANGUAGES.values()) {
                if (!code.equals(l.getLanguageCode())) continue;
                return l;
            }
            return null;
        }

        public static String getDefaultLanguageCode() {
            return ARABIC.getLanguageCode();
        }
    }

    public static enum OPERATING_COMPANY_LABOR_CLASSIFICATION {
        MO3TAMD,
        NON_MO3TAMED;

    }
}
