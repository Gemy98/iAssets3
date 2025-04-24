<!DOCTYPE html>
<%@ page autoFlush="true" buffer="16kb" errorPage="/error/Error.jsp" contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.common.entity.*,java.util.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%

  String langCode = WebUtil.getSessionLanguage(request);

  String dest = (String)request.getAttribute("dest");
 // Boolean excludeHead = (Boolean) request.getAttribute("excludeHead");
 // Boolean excludeInterface = (Boolean) request.getAttribute("excludeInterface");
 // Boolean excludeMenu = (Boolean) request.getAttribute("excludeMenu");
  String msg = WebUtil.getSessionMessage(request);
  // String path = request.getContextPath();
  int siteId = WebUtil.getSessionUser(request).getCurrentSite().getId();
  
  String reportFlag = request.getParameter("report");
%>



<title><%=Default.APP_NAME%> :: <fmt:message key="jsp.Main.pagetitle" /></title>
<base href='http://<%=WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) %>/' />

<%@include file="/Scripts_Styles.jsp" %>

<script>
  $(function(){
	  //initPage();
	initSlideDownMenu();
	
	<%if(WebUtil.currentUserHasPrivilegeForSeeingNotifications(request)){%>
	  //alert($.cookie("bio_stop_jo_notify"));
	  //alert($.cookie("bio_stop_spinv_notify"));
	  if(getCookie("bio_stop_mreq_notify") === undefined)
		connectToMaintenanceRequestNotifier(<%=siteId%> , "<%=langCode%>");
	  if(getCookie("bio_stop_jo_notify") === undefined)
	    connectToJobOrderNotifier(<%=siteId%>, "<%=langCode%>");
	  if(getCookie("bio_stop_spinv_notify") === undefined)
	    connectToSPInventoryNotifier(<%=siteId%>, "<%=langCode%>");
	  
	  
	  <% if(AppUtil.ppmNotificationsPublishingAllowed()){ %>
	    if(getCookie("bio_stop_ppm_notify") === undefined)
		  connectToBioPpmScheduleNotifier(<%=siteId%>, "<%=langCode%>");
	  <%}%>  
	    
	<%}%>
	
    <%if(reportFlag != null){ %>
    	$("form").prepend('<input type="hidden" name="report" value="<%=reportFlag%>" />');
    <%}%>
  });
</script>
</head>
<body>
<%@include file="/NoScript.jsp" %>
<div id="container">
  <%@include file="/Header.jsp" %>
  <div id="content">
    <!-- <div id="job_order_notification_ph" style="background:red;color:white;font-weight: bold"></div> -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2" id="notifications_Container">
        <%--  <%
        Long remainingDays = null;
        if(WebUtil.getSessionSite(request) != null){
        	Date today = DateUtil.getCurrentDate();
        	Date endDateWithGracePeriod = DateUtil.addDaysToDate(WebUtil.getSessionSite(request).getBioContractEndDate(), Default.LICENSE_GRACE_PERIOD_IN_DAYS);
       
        	//remainingDays = DateUtil.getDateDiff(WebUtil.getSessionSite(request).getBioContractEndDate(), DateUtil.getCurrentDate(), java.util.concurrent.TimeUnit.DAYS);
           //if(remainingDays != null && remainingDays <= Default.LICENSE_EXPIRY_NOTIFICATION_DAYS_NO){
        	remainingDays = DateUtil.getDateDiff(endDateWithGracePeriod, today, java.util.concurrent.TimeUnit.DAYS);
          if (remainingDays != null && remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS > 0 && remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS <= Default.LICENSE_EXPIRY_NOTIFICATION_DAYS_NO){
        %>

          <div id='license_expiry_notification_ph'>
             <img src='image/warning.png' /> 
         	 <fmt:message key="jsp.Main.license_expiry_notification_ph1">
         	    <fmt:param value="<%=remainingDays-Default.LICENSE_GRACE_PERIOD_IN_DAYS%>" />
         	    <fmt:param value="<%=Default.LICENSE_GRACE_PERIOD_IN_DAYS%>" />
             </fmt:message>
          </div>
        <%}else if (remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS <= 0){
        %>
          <div id='license_expiry_notification_ph'>
             <img src='image/warning.png' /> 
             <fmt:message key="jsp.Main.license_expiry_notification_ph2">
                <fmt:param value="<%=remainingDays%>" /> 
             </fmt:message>
          </div>
        <%}}%>

          <div id="jo_notifications_holder"></div>
          <div id="spinv_notifications_holder"></div>
        --%>
        </td>
      </tr>

   <%--  
      <tr>
        <td colspan='2'>
         <%@include file="SessionInfo.jsp" %>
        </td>
      </tr> 
    --%>
        
      <tr> 
        <td width="30%" valign="top" id="side_menu_container">
         <%--  <%=javax.servlet.jsp.jstl.core.Config.get(session, javax.servlet.jsp.jstl.core.Config.FMT_LOCALE) %> --%>
          <%@include file="/bio/Menu.jsp" %>
        </td>
        <td valign="top" id="content_container">
        <%=msg%>
        <jsp:include page="<%=dest%>" flush="false" />
        </td>
      </tr>
    </table>
  </div>
  <%@include file="/Footer.jsp" %>
</div>
</body>
</html>

