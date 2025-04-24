/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  javax.servlet.jsp.jstl.core.Config
 *  javax.websocket.Session
 */
package com.iassets.common.util;

import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonHospitalDepartment;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Common;
import com.iassets.common.util.DateUtil;
import com.iassets.common.util.Default;
import com.iassets.common.util.Enums;
import com.iassets.common.util.LocalizationManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import javax.websocket.Session;

public class WebUtil {
    public static Locale getSessionLocale(HttpServletRequest request) {
        return (Locale)Config.get((HttpSession)request.getSession(), (String)"javax.servlet.jsp.jstl.fmt.locale");
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals(name)) continue;
                return cookie;
            }
        }
        return null;
    }

    public static String getDisplayLanguagePreference(HttpServletRequest request) {
        String langParam = WebUtil.getParamValue(request, "lang", null);
        if (langParam != null) {
            return langParam;
        }
        Locale sessionLocal = WebUtil.getSessionLocale(request);
        if (sessionLocal != null) {
            return sessionLocal.getLanguage();
        }
        Cookie langCookie = WebUtil.getCookie(request, "userLangPref");
        if (langCookie != null) {
            return langCookie.getValue();
        }
        return LocalizationManager.DEFAULT_LANGUAGE_CODE;
    }

    public static String getSessionLanguage(HttpServletRequest request) {
        Locale sessionLocal = WebUtil.getSessionLocale(request);
        if (sessionLocal != null) {
            return sessionLocal.getLanguage();
        }
        return LocalizationManager.DEFAULT_LANGUAGE_CODE;
    }

    public static Enums.USER_ALLOWED_APP_TYPE getAllowedAppsOfSessionUser(HttpServletRequest request) {
        CommonUser sUser = WebUtil.getSessionUser(request);
        return AppUtil.getAllowedAppsOfUser(sUser);
    }

    public static Enums.USER_ALLOWED_APP_TYPE getCurrentlyActiveAppFromSession(HttpServletRequest request) {
        return (Enums.USER_ALLOWED_APP_TYPE)((Object)request.getSession().getAttribute("userCurrentlyActiveApp"));
    }

    public static Enums.USER_ALLOWED_APP_TYPE getCurrentlyActiveApp(HttpServletRequest request) {
        Enums.USER_ALLOWED_APP_TYPE c = WebUtil.getCurrentlyActiveAppFromSession(request);
        if (c != null) {
            return c;
        }
        CommonUser sessionUser = WebUtil.getSessionUser(request);
        Enums.USER_ALLOWED_APP_TYPE t = WebUtil.getAllowedAppsOfSessionUser(request);
        if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
            if ((t == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP || t == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) && sessionUser.hasSitesWithBioAppActivated()) {
                return Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
            }
        } else if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
            if ((t == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP || t == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) && sessionUser.hasSitesWithGenAppActivated()) {
                return Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
            }
        } else if (Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) {
            if (t == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP && sessionUser.hasSitesWithBioAppActivated()) {
                return Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
            }
            if (t == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && sessionUser.hasSitesWithGenAppActivated()) {
                return Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
            }
            if (t == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP) {
                String uri;
                if (sessionUser.hasSitesWithBioAppActivated() && !sessionUser.hasSitesWithGenAppActivated()) {
                    return Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
                }
                if (sessionUser.hasSitesWithGenAppActivated() && !sessionUser.hasSitesWithBioAppActivated()) {
                    return Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
                }
                if (sessionUser.hasSitesWithBioAppActivated() && sessionUser.hasSitesWithGenAppActivated() && (uri = WebUtil.getFullRequestUri(request, false)) != null) {
                    if (uri.contains("/bio/")) {
                        return Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP;
                    }
                    if (uri.contains("/gen/")) {
                        return Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP;
                    }
                }
            }
        }
        return null;
    }

    public static String getCurrentlyActiveAppDirectory(HttpServletRequest request) {
        Enums.USER_ALLOWED_APP_TYPE currentlyActiveApp = WebUtil.getCurrentlyActiveApp(request);
        return currentlyActiveApp != null ? currentlyActiveApp.getAppDirectory() : "";
    }

    public static String getServerIpWithPortNo(HttpServletRequest request) {
        return request.getServerName() + ":" + request.getServerPort();
    }

    public static String getAppContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    public static CommonDirectorate getSessionDirectorate(HttpServletRequest request) {
        CommonUser user = WebUtil.getSessionUser(request);
        return user != null ? user.getUserDirectorate() : null;
    }

    public static CommonSite getSessionSite(HttpServletRequest request) {
        CommonUser user = WebUtil.getSessionUser(request);
        return user != null ? user.getCurrentSite() : null;
    }

    public static String getCurrentSiteName(HttpServletRequest request, String langCode) {
        CommonSite site = WebUtil.getSessionSite(request);
        if (site == null) {
            return null;
        }
        CommonHospital location = WebUtil.getSessionLocation(request);
        String siteName = null;
        siteName = location == null ? site.getLocalizedName(langCode) : (site.getContainsSeveralLocations() == false ? location.getLocalizedName(langCode) : site.getLocalizedName(langCode) + " - " + location.getLocalizedName(langCode));
        return siteName;
    }

    public static boolean currentLocationIsHealthCenter(HttpServletRequest request) {
        return WebUtil.getSessionLocation(request).getHealthCenter();
    }

    public static boolean currentUserBelongsToMovingTeam(HttpServletRequest request) {
        return WebUtil.getSessionUser(request).getMovingTeam();
    }

    public static boolean userHasRightToChangeSessionHospital(HttpServletRequest request) {
        CommonUser sessionUser = WebUtil.getSessionUser(request);
        return sessionUser.getUserType().intValue() != Enums.USER_TYPE.BIOMEDICAL_INVENTORY_KEEPER.getId() && sessionUser.getUserType().intValue() != Enums.USER_TYPE.GENERAL_INVENTORY_KEEPER.getId() && sessionUser.getUserType().intValue() != Enums.USER_TYPE.HOSPITAL_DEPARTMENT_SUPERVISOR.getId() && (WebUtil.currentUserBelongsToMovingTeam(request) || WebUtil.getSessionUser(request).getActiveSites().size() > 1);
    }

    public static boolean userHasRightToOpenJobOrder(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
    }

    public static boolean userHasRightToCloseJobOrder(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
    }

    public static boolean userHasRightToAcceptSparePartsQuotation(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
    }

    public static boolean userHasRightToWithdrawFromSPInventoryWithoutJobOrder(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.BIO_INVENTORY_KEEPER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_INVENTORY_KEEPER.getRoleName());
    }

    public static boolean userHasRightToUpdateCompletedPhasesOfJobOrderFollowup(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
    }

    public static boolean currentUserHasPrivilegeForAssigningDirectorateSupervisors(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_DIRECTORATE_SUPERVISORS_ASSIGNATION.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_DIRECTORATE_SUPERVISORS_ASSIGNATION.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.DIRECTORATE_SUPER_ADMIN.getRoleName());
    }

    public static boolean currentUserHasPrivilegeForSiteAdministration(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITES_ADMINISTRATOR.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITES_ADMINISTRATOR.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.DIRECTORATE_SUPER_ADMIN.getRoleName());
    }

    public static boolean currentUserHasViewPrivilegesOnly(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_INSPECTOR.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_INSPECTOR.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.BIO_AND_GEN_INSPECTOR.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.DIRECTORATE_SUPER_ADMIN.getRoleName());
    }

    public static boolean currentUserHasPrivilegeForSeeingNotifications(HttpServletRequest request) {
        return request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()) || request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
    }

    public static boolean sessionUserHasPrivilegeToHandleGivenGmpDepartment(HttpServletRequest request, int deparmentId) {
        return AppUtil.userHasPrivilegeToHandleGivenGmpDepartment(WebUtil.getSessionUser(request), deparmentId);
    }

    public static CommonUser getSessionUser(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s != null) {
            return (CommonUser)s.getAttribute("user");
        }
        return null;
    }

    public static CommonHospital getSessionLocation(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s != null) {
            return (CommonHospital)s.getAttribute("sessionHospital");
        }
        return null;
    }

    public static String getSessionMessage(HttpServletRequest request) {
        Message msg = null;
        HttpSession s = request.getSession(false);
        if (s != null) {
            msg = (Message)s.getAttribute("msg");
            s.removeAttribute("msg");
        }
        if (msg != null) {
            if (msg.getType() == Message.MESSAGE_TYPE.ERROR) {
                return "<h3 class='error_msg'>" + msg.getText() + "</h3>";
            }
            if (msg.getType() == Message.MESSAGE_TYPE.WARNING) {
                return "<h3 class='warning_msg'>" + msg.getText() + "</h3>";
            }
            if (msg.getType() == Message.MESSAGE_TYPE.SUCCESS) {
                return "<h3 class='success_msg'>" + msg.getText() + "</h3>";
            }
        }
        return "";
    }

    public static Integer getParamValueAsInteger(HttpServletRequest request, String paramName, Integer defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    public static Float getParamValueAsFloat(HttpServletRequest request, String paramName, Float defaultValue) {
        try {
            return Float.valueOf(Float.parseFloat(request.getParameter(paramName)));
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getParamValue(HttpServletRequest request, String paramName, String defaultValue) {
        String inputStr = request.getParameter(paramName);
        if (inputStr != null && !inputStr.trim().isEmpty()) {
            return inputStr;
        }
        return defaultValue;
    }

    public static String[] getParamValuesAsStringArray(HttpServletRequest request, String paramName, String[] defaultValue) {
        String[] inputStr = request.getParameterValues(paramName);
        if (inputStr != null && inputStr.length != 0) {
            return inputStr;
        }
        return defaultValue;
    }

    public static Integer[] getParamValuesAsIntegerArray(HttpServletRequest request, String paramName, Integer[] defaultValue) {
        String[] inputStr = request.getParameterValues(paramName);
        if (inputStr != null && inputStr.length != 0) {
            Integer[] inputInt = new Integer[inputStr.length];
            for (int i = 0; i < inputStr.length; ++i) {
                String value = inputStr[i];
                inputInt[i] = Integer.parseInt(value);
            }
            return inputInt;
        }
        return defaultValue;
    }

    public static Date getParamValueAsDate(HttpServletRequest request, String paramName, Date defaultValue) {
        try {
            Date d = DateUtil.getDateFromString(request.getParameter(paramName));
            return d != null ? d : defaultValue;
        }
        catch (Exception e) {
            return defaultValue;
        }
    }

    public static String decodeQueryStringParameter(HttpServletRequest request, String paramName) {
        try {
            String value = URLDecoder.decode(request.getParameter(paramName), "UTF-8");
            return value != null ? value : "unnamed";
        }
        catch (UnsupportedEncodingException e) {
            Common.log(e);
            return "unnamed";
        }
    }

    private static String getFullRequestUri(HttpServletRequest request, boolean getLast) {
        String originalRequestUri = null;
        if (!getLast) {
            originalRequestUri = (String)request.getAttribute("javax.servlet.forward.request_uri");
        }
        if (originalRequestUri == null) {
            originalRequestUri = request.getServletPath();
        }
        return originalRequestUri;
    }

    public static String getRequestUriLastSegment(HttpServletRequest request, boolean getLast) {
        String originalFullRequestUri = WebUtil.getFullRequestUri(request, getLast);
        return originalFullRequestUri.substring(originalFullRequestUri.lastIndexOf("/") + 1);
    }

    public static String getRequestUriLastTwoSegments(HttpServletRequest request) {
        String originalFullRequestUri = WebUtil.getFullRequestUri(request, false);
        int g = originalFullRequestUri.lastIndexOf("/gen/");
        if (g != -1) {
            return originalFullRequestUri.substring(g + 1);
        }
        int b = originalFullRequestUri.lastIndexOf("/bio/");
        if (b != -1) {
            return originalFullRequestUri.substring(b + 1);
        }
        return WebUtil.getRequestUriLastSegment(request);
    }

    public static String getRequestUriLastSegment(HttpServletRequest request) {
        return WebUtil.getRequestUriLastSegment(request, false);
    }

    public static String getSearchDisplayURL(HttpServletRequest request) {
        return WebUtil.getRequestUriLastSegment(request) + "Search";
    }

    public static String getSearchProcessURL(HttpServletRequest request) {
        return WebUtil.getRequestUriLastSegment(request).replace("Search", "");
    }

    private static String getRequestParameterValueFromEndPointSession(Session session, String paramName) {
        List list = (List)session.getRequestParameterMap().get(paramName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (String)list.get(0);
    }

    public static String getStringRequestParameterValueFromEndPointSession(Session session, String paramName) {
        return WebUtil.getRequestParameterValueFromEndPointSession(session, paramName);
    }

    private static Integer getIntegerRequestParameterValueFromEndPointSession(Session session, String paramName) {
        try {
            return Integer.parseInt(WebUtil.getRequestParameterValueFromEndPointSession(session, paramName));
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Integer getSiteIdFromEndPointSession(Session session) {
        return WebUtil.getIntegerRequestParameterValueFromEndPointSession(session, "siteId");
    }

    public static Integer getPPMCatIdFromEndPointSession(Session session) {
        return WebUtil.getIntegerRequestParameterValueFromEndPointSession(session, "gmpDepId");
    }

    public static Integer getGmpDepartmentIdFromEndPointSession(Session session) {
        return WebUtil.getIntegerRequestParameterValueFromEndPointSession(session, "gmpDepId");
    }

    public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                cookies[i].setValue(null);
                cookies[i].setPath("/");
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
    }

    public static String getLastSegmentOfProcessServletUrl(HttpServletRequest displayServletRequest) {
        String uri = WebUtil.getRequestUriLastSegment(displayServletRequest);
        return uri.replace("Display", "Process");
    }

    public static String getLastSegmentOfDisplayServletUrl(HttpServletRequest processServletRequest) {
        String uri = WebUtil.getRequestUriLastSegment(processServletRequest);
        return uri.replace("Process", "Display");
    }

    public static boolean sessionUserIsDirectorateUser(HttpServletRequest request) {
        CommonUser user = WebUtil.getSessionUser(request);
        return AppUtil.userBelongsToDirectorate(Enums.USER_TYPE.getUserTypeById(user.getUserType()));
    }

    public static void prepareCommonDeviceFilterParams(HttpServletRequest request, CommonHospitalDepartment sessionUserDepartment, Map<String, Object> params) {
        Integer department = sessionUserDepartment != null ? sessionUserDepartment.getId() : WebUtil.getParamValueAsInteger(request, "dep", null);
        Integer classification = WebUtil.getParamValueAsInteger(request, "classification", null);
        Integer[] categoryArr = WebUtil.getParamValuesAsIntegerArray(request, "category", null);
        if (categoryArr != null && categoryArr.length == 3) {
            categoryArr = null;
        }
        Integer hospLocation = WebUtil.getParamValueAsInteger(request, "hospLocation", null);
        String name = WebUtil.getParamValue(request, "name", null);
        String agentName = WebUtil.getParamValue(request, "agentName", null);
        String subcontractor = WebUtil.getParamValue(request, "subcontractor", null);
        String manufacturerName = WebUtil.getParamValue(request, "manufacturerName", null);
        String model = WebUtil.getParamValue(request, "model", null);
        params.put("classification", classification);
        params.put("categoryArr", categoryArr == null ? null : Arrays.asList(categoryArr));
        params.put("hospLocation", hospLocation);
        params.put("department", department);
        params.put("name", name == null ? null : "%" + name + "%");
        params.put("agentName", agentName == null ? null : "%" + agentName + "%");
        params.put("subcontractor", subcontractor == null ? null : "%" + subcontractor + "%");
        params.put("manufacturerName", manufacturerName == null ? null : "%" + manufacturerName + "%");
        params.put("model", model == null ? null : "%" + model + "%");
    }
}
