/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  javax.servlet.jsp.jstl.core.Config
 *  net.sf.jasperreports.engine.JRDataSource
 *  net.sf.jasperreports.engine.JREmptyDataSource
 *  net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
 *  org.springframework.web.context.support.SpringBeanAutowiringSupport
 */
package com.iassets.common.servlet;

import com.iassets.common.bo.JRBean;
import com.iassets.common.bo.Message;
import com.iassets.common.entity.CommonDirectorate;
import com.iassets.common.entity.CommonHospital;
import com.iassets.common.entity.CommonOperatingCompany;
import com.iassets.common.entity.CommonSite;
import com.iassets.common.entity.CommonUser;
import com.iassets.common.util.AppUtil;
import com.iassets.common.util.Common;
import com.iassets.common.util.Enums;
import com.iassets.common.util.JasperUtil;
import com.iassets.common.util.LocalizationManager;
import com.iassets.common.util.MultiFilesResourceBundle;
import com.iassets.common.util.WebUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public abstract class BasicServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext((Object)((Object)this), (ServletContext)this.getServletContext());
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String reqURI = WebUtil.getRequestUriLastSegment(request);
        if (!(!this.userAuthenticated(request) || reqURI.contains("Logout") || reqURI.contains("ChooseSessionHospitalDisplay") || reqURI.contains("ChooseSessionHospitalProcess") || reqURI.contains("ChooseApplicationDisplay"))) {
            CommonSite sessionSite = this.getSessionSite(request);
            Enums.USER_ALLOWED_APP_TYPE currentActiveApp = WebUtil.getCurrentlyActiveApp(request);
            if (sessionSite != null && currentActiveApp != null) {
                Enums.LICENSE_STATUS genLicStatus;
                if (currentActiveApp == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                    Enums.LICENSE_STATUS bioLicStatus = AppUtil.getBioLicenseStatus(sessionSite);
                    if (bioLicStatus != Enums.LICENSE_STATUS.ACTIVE && bioLicStatus != Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD) {
                        this.announceErorrMessage(request, response, bioLicStatus.getMessage(this.getSessionLanguage(request)), true);
                        return;
                    }
                } else if (currentActiveApp == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && (genLicStatus = AppUtil.getGenLicenseStatus(sessionSite)) != Enums.LICENSE_STATUS.ACTIVE && genLicStatus != Enums.LICENSE_STATUS.EXPIRED_WITHIN_GRACE_PERIOD) {
                    this.announceErorrMessage(request, response, genLicStatus.getMessage(this.getSessionLanguage(request)), true);
                    return;
                }
            }
        }
        super.service(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected boolean userAuthenticated(HttpServletRequest request) {
        return request.getUserPrincipal() != null;
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        this.forward(request, response, dest, false, false, false);
    }

    protected void basicForward(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        request.getRequestDispatcher(dest).forward((ServletRequest)request, (ServletResponse)response);
    }

    protected void include(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        request.getRequestDispatcher(dest).include((ServletRequest)request, (ServletResponse)response);
    }

    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + dest);
    }

    protected void sendRedirectRelativeToCurrentAppDir(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/" + this.getCurrentlyActiveAppDir(request) + dest);
    }

    protected void viewHTMLReport(HttpServletRequest request, HttpServletResponse response, String dest, String reportTitle) throws ServletException, IOException {
        this.viewBasicTemplate(request, response, dest, false, false, reportTitle, null);
    }

    protected void announceErorrMessage(HttpServletRequest request, HttpServletResponse response, String errorMsg, boolean hideDefaultButtons) throws ServletException, IOException {
        this.viewBasicTemplate(request, response, null, false, hideDefaultButtons, null, errorMsg);
    }

    protected void announceErorrMessage(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
        this.announceErorrMessage(request, response, errorMsg, false);
    }

    protected void forwardToBasicTemplate(HttpServletRequest request, HttpServletResponse response, String dest) throws ServletException, IOException {
        this.viewBasicTemplate(request, response, dest, true, true, null, null);
    }

    protected void viewPDFReport(String jasperFileName, String reportTitle, Collection data, HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String langCode = this.getSessionLanguage(request);
        CommonSite sessionSite = this.getSessionSite(request);
        JRBean b = new JRBean(sessionSite);
        String path = this.getReportsRootPath(request) + "/";
        b.setJasperPrintPath(path + jasperFileName);
        if (data != null && data.size() > 0) {
            b.setDataSource((JRDataSource)new JRBeanCollectionDataSource(data));
        } else {
            b.setDataSource((JRDataSource)new JREmptyDataSource());
        }
        params.put("repoTitle", reportTitle);
        params.put("REPORT_RESOURCE_BUNDLE", new MultiFilesResourceBundle(new Locale(this.getSessionLanguage(request))));
        if (sessionSite != null) {
            CommonHospital sessionLocation = this.getSessionLocation(request);
            CommonOperatingCompany comp = null;
            if (WebUtil.getCurrentlyActiveApp(request) == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                comp = sessionSite.getBioOperatingCompany();
            } else if (WebUtil.getCurrentlyActiveApp(request) == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP) {
                comp = sessionSite.getGenOperatingCompany();
            }
            params.put("locationName", sessionLocation != null ? sessionLocation.getLocalizedName(langCode) : "\u0643\u0634\u0641 \u0645\u062c\u0645\u0639 - " + sessionSite.getLocalizedName(langCode));
            params.put("operatingCompany", comp.getLocalizedName(langCode));
            b.setDisplayName(sessionSite.getLocalizedName(langCode) + "_" + jasperFileName.replace(".jasper", "") + "_" + System.currentTimeMillis());
        } else {
            b.setDisplayName(this.getSessionDirectorate(request).getLocalizedName("en").replace(" ", "") + "_" + jasperFileName.replace(".jasper", "") + "_" + System.currentTimeMillis());
        }
        params.put("directorate", this.getSessionDirectorate(request).getLocalizedName(langCode));
        b.setParams(params);
        try {
            JasperUtil.exportReportToResponseOutputStreamAsPDF(b, response);
        }
        catch (Exception ex) {
            Common.log(ex);
            throw new ServletException((Throwable)ex);
        }
    }

    protected void setSessionUser(HttpServletRequest request, CommonUser user) {
        request.getSession(true).setAttribute("user", (Object)user);
    }

    public CommonUser getSessionUser(HttpServletRequest request) {
        return WebUtil.getSessionUser(request);
    }

    protected void setSessionLocation(HttpServletRequest request, CommonHospital hospital) {
        request.getSession(true).setAttribute("sessionHospital", (Object)hospital);
    }

    public CommonHospital getSessionLocation(HttpServletRequest request) {
        HttpSession s;
        if (WebUtil.getParamValue(request, "ihf", null) != null) {
            return null;
        }
        boolean reportRequested = request.getParameter("report") != null;
        int reportType = WebUtil.getParamValueAsInteger(request, "reportType", 0);
        if (reportRequested && reportType != 0) {
            if (reportType == Enums.REQUESTED_REPORT_TYPE.FOR_ALL_SITE_LOCATIONS.getId()) {
                return null;
            }
            if (reportType == Enums.REQUESTED_REPORT_TYPE.FOR_ANOTHER_LOCATION.getId()) {
                return this.getSessionLocationById(request);
            }
        }
        if ((s = request.getSession(false)) != null) {
            return (CommonHospital)s.getAttribute("sessionHospital");
        }
        return null;
    }

    public int getSessionLocationId(HttpServletRequest request) {
        CommonHospital currentLocation = this.getSessionLocation(request);
        return currentLocation != null ? currentLocation.getId() : 0;
    }

    public CommonDirectorate getSessionDirectorate(HttpServletRequest request) {
        return WebUtil.getSessionDirectorate(request);
    }

    public CommonSite getSessionSite(HttpServletRequest request) {
        return WebUtil.getSessionSite(request);
    }

    public int getSessionSiteId(HttpServletRequest request) {
        return this.getSessionSite(request).getId();
    }

    protected void setMessage(HttpServletRequest request, Message msg) {
        request.getSession(true).setAttribute("msg", (Object)msg);
    }

    protected Message getMessage(HttpServletRequest request) {
        return (Message)this.removeSessionAttribute(request, "msg");
    }

    protected void clearSessionMessage(HttpServletRequest request) {
        this.removeSessionAttribute(request, "msg");
    }

    protected void setDestination(HttpServletRequest request, String dest) {
        request.getSession(true).setAttribute("dest", (Object)dest);
    }

    protected String getDestination(HttpServletRequest request) {
        return (String)this.removeSessionAttribute(request, "dest");
    }

    protected void setSessionAttribute(HttpServletRequest request, String attrName, Object attrValue) {
        request.getSession(true).setAttribute(attrName, attrValue);
    }

    protected Object getSessionAttribute(HttpServletRequest request, String attrName) {
        HttpSession s = request.getSession(false);
        return s != null ? s.getAttribute(attrName) : null;
    }

    protected Object removeSessionAttribute(HttpServletRequest request, String attrName) {
        Object o = null;
        HttpSession s = request.getSession(false);
        if (s != null) {
            o = s.getAttribute(attrName);
            s.removeAttribute(attrName);
        }
        return o;
    }

    protected String getSearchDestination(Map<String, String> destinations, HttpServletRequest request) throws ServletException {
        String dest = destinations.get(WebUtil.getSearchProcessURL(request));
        if (dest == null) {
            throw new ServletException(LocalizationManager.getLiteral("servlet.BasicServlet.msg1", this.getSessionLanguage(request)));
        }
        return dest;
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String dest, boolean excludeInterface, boolean excludeMenu, boolean excludeHead) throws ServletException, IOException {
        request.setAttribute("excludeInterface", (Object)excludeInterface);
        request.setAttribute("excludeMenu", (Object)excludeMenu);
        request.setAttribute("excludeHead", (Object)excludeHead);
        request.setAttribute("containsForm", (Object)true);
        request.setAttribute("dest", (Object)dest);
        request.getRequestDispatcher("/" + this.getCurrentlyActiveAppDir(request) + "/Main.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }

    protected void viewBasicTemplate(HttpServletRequest request, HttpServletResponse response, String dest, boolean containsForm, boolean hideDefaultButtons, String reportTitle, String announcement) throws ServletException, IOException {
        if (announcement != null) {
            this.setMessage(request, new Message(announcement, Message.MESSAGE_TYPE.ERROR));
        } else if (reportTitle != null) {
            request.setAttribute("reportTitle", (Object)reportTitle);
        }
        request.setAttribute("report", (Object)(!containsForm && reportTitle != null ? 1 : 0));
        request.setAttribute("announcement", (Object)(announcement != null ? 1 : 0));
        request.setAttribute("containsForm", (Object)containsForm);
        request.setAttribute("hideDefaultButtons", (Object)hideDefaultButtons);
        request.setAttribute("dest", (Object)dest);
        request.getRequestDispatcher("/Report.jsp").forward((ServletRequest)request, (ServletResponse)response);
    }

    protected String getReportsRootPath(HttpServletRequest request) {
        return this.getServletContext().getRealPath("/WEB-INF/") + "/reports/" + this.getCurrentlyActiveAppDir(request);
    }

    protected abstract CommonHospital getSessionLocationById(HttpServletRequest var1);

    protected String getCurrentlyActiveAppDir(HttpServletRequest request) {
        return WebUtil.getCurrentlyActiveAppDirectory(request);
    }

    protected void setSessionLanguage(HttpServletRequest request) {
        CommonUser sessionUser;
        HttpSession session = request.getSession();
        CommonSite sessionSite = this.getSessionSite(request);
        if (sessionSite != null && !AppUtil.userTypeAllowedToOverrideDisplayLanguageRestrictions((sessionUser = this.getSessionUser(request)).getUserType())) {
            Enums.USER_ALLOWED_APP_TYPE currentlyActiveApp = WebUtil.getCurrentlyActiveApp(request);
            if (currentlyActiveApp == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP) {
                if (sessionSite.getBioAppSupportedLang() != null) {
                    Config.set((HttpSession)session, (String)"javax.servlet.jsp.jstl.fmt.locale", (Object)new Locale(sessionSite.getBioAppSupportedLang()));
                }
            } else if (currentlyActiveApp == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && sessionSite.getGenAppSupportedLang() != null) {
                Config.set((HttpSession)session, (String)"javax.servlet.jsp.jstl.fmt.locale", (Object)new Locale(sessionSite.getGenAppSupportedLang()));
            }
        }
    }

    protected String getSessionLanguage(HttpServletRequest request) {
        return WebUtil.getSessionLanguage(request);
    }

    protected void setCurrentlyActiveAppInSession(HttpServletRequest request, Enums.USER_ALLOWED_APP_TYPE activeApp) {
        this.setSessionAttribute(request, "userCurrentlyActiveApp", (Object)activeApp);
    }

    protected Enums.USER_ALLOWED_APP_TYPE getCurrentlyActiveAppFromSession(HttpServletRequest request) {
        return WebUtil.getCurrentlyActiveAppFromSession(request);
    }
}
