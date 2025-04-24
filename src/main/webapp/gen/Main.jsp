<!DOCTYPE html>
<%@ page autoFlush="true" buffer="16kb" errorPage="/error/Error.jsp"
	contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.WebUtil,com.iassets.common.entity.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
  String dest = (String)request.getAttribute("dest");
 // Boolean excludeHead = (Boolean) request.getAttribute("excludeHead");
 // Boolean excludeInterface = (Boolean) request.getAttribute("excludeInterface");
 // Boolean excludeMenu = (Boolean) request.getAttribute("excludeMenu");
  String msg = WebUtil.getSessionMessage(request);
  // String path = request.getContextPath();
  int siteId = WebUtil.getSessionUser(request).getCurrentSite().getId();
  int gmpDepId = (WebUtil.getSessionUser(request).getGmpCategory() != null) ? WebUtil.getSessionUser(request).getGmpCategory().getId():0;
  
  String reportFlag = request.getParameter("report");
%>



<title><%=Default.APP_NAME%> :: برنامج إدارة قسم الصيانة الغير طبية</title>
<base
	href='http://<%=WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) %>/' />

<%@include file="/Scripts_Styles.jsp"%>

<script>
  $(function(){
	  //initPage();
	initSlideDownMenu();
	
	<%if(WebUtil.currentUserHasPrivilegeForSeeingNotifications(request)){%>
	  //alert($.cookie("gen_stop_jo_notify"));
	  //alert($.cookie("gen_stop_spinv_notify"));
  	  if(getCookie("gen_stop_mreq_notify") === undefined)
		connectToMaintenanceRequestNotifier(<%=siteId%>);
	  if(getCookie("gen_stop_jo_notify") === undefined)
	    connectToJobOrderNotifier(<%=siteId%>, <%=gmpDepId%>);
	  if(getCookie("gen_stop_spinv_notify") === undefined)
	    connectToSPInventoryNotifier(<%=siteId%>);
	  if(getCookie("gen_stop_ppm_notify") === undefined)
		 // connectToPpmScheduleNotifier(<%=siteId%>);
	  connectToPpmScheduleNotifier(<%=siteId%>,<%=gmpDepId%>);
	<%}%>
	
    <%if(reportFlag != null){ %>
    	$("form").prepend('<input type="hidden" name="report" value="<%=reportFlag%>" />');
<%}%>
	});
</script>
</head>
<body>
	<%@include file="/NoScript.jsp"%>
	<div id="container">
		<%@include file="/Header.jsp"%>
		<div id="content">
			<!-- <div id="job_order_notification_ph" style="background:red;color:white;font-weight: bold"></div> -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td colspan="2" id="notifications_Container">	
	   <%--  <%
        Long remainingDays = null;
        if(WebUtil.getSessionSite(request) != null){
        	Date today = DateUtil.getCurrentDate();
        	Date endDateWithGracePeriod = DateUtil.addDaysToDate(WebUtil.getSessionSite(request).getGenContractEndDate(), Default.LICENSE_GRACE_PERIOD_IN_DAYS);
       
        	//remainingDays = DateUtil.getDateDiff(WebUtil.getSessionSite(request).getBioContractEndDate(), DateUtil.getCurrentDate(), java.util.concurrent.TimeUnit.DAYS);
           //if(remainingDays != null && remainingDays <= Default.LICENSE_EXPIRY_NOTIFICATION_DAYS_NO){
        	remainingDays = DateUtil.getDateDiff(endDateWithGracePeriod, today, java.util.concurrent.TimeUnit.DAYS);
          if (remainingDays != null && remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS > 0 && remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS <= Default.LICENSE_EXPIRY_NOTIFICATION_DAYS_NO){
        %>


          <div id='license_expiry_notification_ph'><img src='image/warning.png' />ستنتهي صلاحية رخصة البرنامج خلال  <%=remainingDays-Default.LICENSE_GRACE_PERIOD_IN_DAYS%> يوم  (فترة السماح  <%=Default.LICENSE_GRACE_PERIOD_IN_DAYS%> يوم)،  للتجديد يرجى الاتصال بالشركة الموردة</div>
        <%}else if (remainingDays - Default.LICENSE_GRACE_PERIOD_IN_DAYS <= 0){
        %>
          <div id='license_expiry_notification_ph'><img src='image/warning.png' />لقد انتهت صلاحية البرنامج باقي   <%=remainingDays%> يوم في فترة السماح ، للتجديد يرجي الاتصال بالشركة الموردة </div>
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
		  <td width="30%" valign="top" id="side_menu_container"><%@include file="/gen/Menu.jsp"%></td>
		  <td valign="top" id="content_container">
		  <%=msg%> 
		  <jsp:include page="<%=dest%>" flush="false" />
		  </td>
		</tr>
	 </table>
   </div>
   <%@include file="/Footer.jsp"%>
</div>
</body>
</html>

