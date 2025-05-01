<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="language"
	value="${param.lang != null? param.lang: cookie.userLangPref != null ? cookie.userLangPref.value : 'ar'}"
	scope="session" />
<c:set var="direction" value='${language == "en" ? "ltr" :"rtl"}'
	scope="session" />
<% 
  // WebUtil.deleteAllCookies(request, response);
  Cookie c = new Cookie(Default.USER_LANGUAGE_PREFERRENCE_COOKIE_NAME, (String)session.getAttribute("language"));
  response.addCookie(c);
%>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>suspended</title>
<base
	href='http://<%=WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) %>/' />

<link type="text/css" rel="stylesheet"
	href="css/jquery-ui-themes-1.10.4/themes/flick/jquery-ui.min.css" />
<link type="text/css" rel="stylesheet"
	href="css/jquery-ui-themes-1.10.4/themes/flick/jquery.ui.theme.css" />
<link rel="stylesheet"
	href="js/appendGrid/jquery.appendGrid-1.3.5.min.css" />
<link rel="stylesheet" href="css/pure-min.css" />
<link rel="stylesheet" href="css/pure-skin-mine.css" />
<!--<link rel="stylesheet" href="js/uploadify/uploadify.css"/>-->
<link type="text/css" rel="stylesheet" href="css/slidedown-menu.css" />
<!--<link type="text/css" rel="stylesheet" href="css/style.css"/>-->
<link type="text/css" rel="stylesheet"
	href="${language == 'en' ? 'css/style_ltr.css':'css/style_rtl.css'}" />
<!--<link type="text/css" rel="stylesheet" href="/jOrgChart/example/css/jquery.jOrgChart.css" />-->
<script type="text/javascript" src="js/js_constants.js"></script>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.min.js"></script>
<script type="text/javascript" src="js/datepicker-ar.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<!--<script type="text/javascript" src="js/fileinput/jquery.fileinput.min.js"></script>-->
<script type="text/javascript"
	src="js/appendGrid/jquery.appendGrid-1.3.5.min.js"></script>
<script type="text/javascript"
	src="js/validation/jquery.validate.min.js"></script>
<!--<script type="text/javascript" src="js/validation/additional-methods.min.js"></script>-->
<script type="text/javascript"
	src="js/validation/jquery.ui.datepicker.validation.min.js"></script>
<script type="text/javascript" src="js/validation/messages_ar.js"></script>
<!--<script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>-->
<script type="text/javascript" src="js/slidedown-menu.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<!--<script type="text/javascript" src="jOrgChart/example/jquery.jOrgChart.js"></script>-->

<style type="text/css">
label.error {
	display: block;
	margin-right: 5px !important;
	color: #F00 !important;
}

div#login_form {
	width: 35%;
	padding: 25px 50px;
	border: 1px solid #333;
	margin: 40px auto
}
</style>
<!--
<script type="text/javascript">
$(function(){

	  removeCookie("bio_stop_jo_notify");
	  removeCookie("bio_stop_spinv_notify");
	  removeCookie("gen_stop_jo_notify");
	  removeCookie("gen_stop_spinv_notify");
      //alert(getCookie("gen_stop_jo_notify"));
	  //alert(getCookie("gen_stop_spinv_notify"));
	  //$.removeCookie('reportType');
	  //$.removeCookie('reportLocation');
	
      $("body").prop("class","pure-skin-mine");
      $("form").prop("class","pure-form");
	  
      // style buttons with pure framework
      $("input[type='button']").addClass("pure-button");
      $("input[type='submit']").addClass("pure-button pure-button-primary"); 
      
      $("form").validate({
                rules: {
                    userName: "required",
                    password: "required"
                    },
                messages: {
                    userName: "من فضلك أدخل اسم المستخدم",
                    password: "من فضلك أدخل كلمة المرور"
                }
        });
	  
});   
</script>
-->
</head>
<body>
	<%-- 
language: <c:out value="${sessionScope.language}"/>
direction: <c:out value="${sessionScope.direction}"/> 
--%>
	<%-- <%@include file="/NoScript.jsp" %> --%>
	<div id="container">
		<%@include file="/Header.jsp"%>
		<div
			style="font-size: 20px; font-weight: bold; color: #f00; text-align: center; line-height: 50px; padding-top: 70px"
			id="content">
			<fmt:message key="jsp.Suspended.msg" />
			<!-- <br />
     هذا وننوه على أنه سيستمر إيقاف عمل البرنامج لحين دفع شركة المجال العربي كافة التزامتها المادية للشركة الموردة  -->
		</div>
		<%@include file="/Footer.jsp"%>
	</div>
</body>
</html>
