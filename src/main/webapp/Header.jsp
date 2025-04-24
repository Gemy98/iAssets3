<%@ page import="com.iassets.common.entity.*,com.iassets.common.util.*" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><%
String hLangCode = WebUtil.getSessionLanguage(request);
CommonUser sessionUser = WebUtil.getSessionUser(request);
CommonSite sessionSite = WebUtil.getSessionSite(request);
String directorateName = (sessionUser != null) ? sessionUser.getUserDirectorateName(hLangCode) : LocalizationManager.getLiteral("jsp.Header.country", hLangCode);//(sessionSite != null) ? sessionSite.getDirectorateName() : "النسخة التجريبية - ليبيا";
CommonHospital sessionHosp = WebUtil.getSessionLocation(request);
Enums.USER_ALLOWED_APP_TYPE appType = WebUtil.getCurrentlyActiveApp(request);

String currentHospitalName = null;

if(sessionHosp != null){
	currentHospitalName = sessionHosp.getLocalizedName(hLangCode) + " - ";
    if(appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP)
    	currentHospitalName += LocalizationManager.getLiteral("jsp.Header.bio", hLangCode);
    else if(appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP)
    	currentHospitalName += LocalizationManager.getLiteral("jsp.Header.gen", hLangCode);
}else{
	currentHospitalName = LocalizationManager.getLiteral("jsp.Header.program", hLangCode);
    if(appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP)
    	currentHospitalName += " " + LocalizationManager.getLiteral("jsp.Header.bio2", hLangCode);
    else if(appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP)
    	currentHospitalName += " " + LocalizationManager.getLiteral("jsp.Header.gen2", hLangCode);
    if(sessionSite != null)
    	currentHospitalName += " - " + sessionSite.getLocalizedName(hLangCode);
}
%>

	<div id="session_info">
	<%
		if (request.getUserPrincipal() != null){ // if user logged in
			String appDir = WebUtil.getCurrentlyActiveAppDirectory(request);
	%>
	 <%@include file="/SessionInfo.jsp" %> 
        <ul id="top_menu">
        <%if(appType != null){ 
        	if(sessionSite != null || sessionHosp != null){%>
        	
        	
        	   <%--  <%if(WebUtil.getAllowedAppsOfSessionUser(request) == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP 
        	    		&& Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP){
        	    	if(appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP && sessionUser.hasSitesWithGenAppActivated()){
        	    		if(sessionSite.isGenAppActivated())
        	    		   out.print("<li><a href='gen/UserProfileDisplay'>الصيانة الغير طبية</a></li>");
        	    		else
        	    		   out.print("<li><a href='gen/ChooseSessionHospitalDisplay'>الصيانة الغير طبية</a></li>");
        	    	}else if(appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && sessionUser.hasSitesWithBioAppActivated()){
        	    		if(sessionSite.isBioAppActivated())
        	        	   out.print("<li><a href='bio/UserProfileDisplay'>الصيانة الطبية</a></li>");
        	    		else
         	    		   out.print("<li><a href='bio/ChooseSessionHospitalDisplay'>الصيانة الطبية</a></li>");
        	    	}
        	    }%> --%>
        	   
	            <li><a href="<%=appDir%>/WelcomePageDisplay?purpose=<fmt:message key='jsp.Header.WelcomePageDisplay' />">
	             <i class="fa fa-home"></i><fmt:message key='jsp.Header.WelcomePageDisplay.purpose' />
	            </a></li>
	            <li><a href="<%=appDir%>/ChangePasswordDisplay?purpose=<fmt:message key='jsp.Header.ChangePasswordDisplay' />">
	            <i class="fa fa-key"></i><fmt:message key='jsp.Header.ChangePasswordDisplay.purpose' />
	            </a></li>
	        <%}%>
	        <%if(WebUtil.currentUserHasPrivilegeForAssigningDirectorateSupervisors(request)){%>
	            <li><a href="<%=appDir%>/AssignDirectorateSupervisorToSitesDisplay?purpose= <fmt:message key='jsp.Header.AssignDirectorateSupervisorToSitesDisplay.purpose' /> ">
	            <i class="fa fa-user"></i><fmt:message key='jsp.Header.AssignDirectorateSupervisorToSitesDisplay' />
	            </a></li>
            <%}%>
		    <%if(WebUtil.userHasRightToChangeSessionHospital(request)){%>
	            <li><a href="ChooseSessionHospitalDisplay?purpose=<fmt:message key='jsp.Header.ChooseSessionHospitalDisplay.purpose' />">
	            <i class="fa fa-hospital-o"></i><fmt:message key='jsp.Header.ChooseSessionHospitalDisplay' />
	            </a></li>
            <%} 
		  }%>
         
	            <li><a href="Logout">
	            <i class="fa fa-sign-out"></i><fmt:message key='jsp.Header.Logout' />
	            </a></li>
        </ul>
	<%}%>
	</div>

<div id="header">
    <!--<div id="top"></div>-->
    <div id="left_banner_sec">
	    <img alt="iAssets" id="iassets_logo" src="image/iassets_logo.png" title="iAssets V-4.0.1" width="170" height="40" /> <!-- onclick="viewFile('\\sys\\iAssets-profile.pdf')" -->
    </div>
    <div id="logo"> 
	    <img src="image/logo_${sessionScope.language}.png" width="960" height="116" /> 
	    <span id="directorate_name"><%=directorateName%> <%-- <%=Default.DIRECTORATE_NAME%> --%></span>
	    <span id="current_hosp_name"><%=currentHospitalName%></span>
	    
<!-- begin of link app -->    
    <%
		if (request.getUserPrincipal() != null && appType != null && (sessionSite != null || sessionHosp != null)){ // if user logged in
       		if(WebUtil.getAllowedAppsOfSessionUser(request) == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP 
       	    		&& Default.ACTIVE_APPS_FOR_CURRENT_DEPLOYMENT == Enums.USER_ALLOWED_APP_TYPE.BOTH_MAINTENANCE_APP){
       	    	if(appType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP && sessionSite.isGenAppActivated())
       	    		   out.print("<a class='maintenance_app' href='gen/WelcomePageDisplay'><i class='fa fa-wrench'></i> "+LocalizationManager.getLiteral("jsp.Header.gotobio", hLangCode)+" </a>");
       	    	else if(appType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP && sessionSite.isBioAppActivated())
       	        	   out.print("<a class='maintenance_app' href='bio/WelcomePageDisplay'><i class='fa fa-medkit'></i> "+LocalizationManager.getLiteral("jsp.Header.gotogen", hLangCode)+" </a>");		
        	}
		  }
   %>

<!-- end of link app -->

    </div>
</div>