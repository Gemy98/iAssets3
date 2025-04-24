<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${param.lang != null? param.lang: cookie.userLangPref != null ? cookie.userLangPref.value : 'ar'}" scope="session" />
<c:set var="direction" value='${language == "ar" ? "rtl" : "ltr"}' scope="session" />
<% 
  // WebUtil.deleteAllCookies(request, response);
  Cookie c = new Cookie(Default.USER_LANGUAGE_PREFERRENCE_COOKIE_NAME, (String)session.getAttribute("language"));
  response.addCookie(c);
%>
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<title><%=Default.APP_NAME%> :: <fmt:message key="jsp.login.pagetitle" /></title>
<base href='http://<%=WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) %>/' />

<link type="text/css" rel="stylesheet" href="css/jquery-ui-themes-1.10.4/themes/flick/jquery-ui.min.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery-ui-themes-1.10.4/themes/flick/jquery.ui.theme.css"/>
<link rel="stylesheet" href="js/appendGrid/jquery.appendGrid-1.3.5.min.css" />
<link rel="stylesheet" href="css/pure-min.css"/>
<link rel="stylesheet" href="css/pure-skin-mine.css"/>
<!--<link rel="stylesheet" href="js/uploadify/uploadify.css"/>-->
<!--<link type="text/css" rel="stylesheet" href="css/style.css"/>-->
<link type="text/css" rel="stylesheet" href="css/style_${language == 'ar' ? 'rtl' : 'ltr'}.css"/>
<!--<link type="text/css" rel="stylesheet" href="/jOrgChart/example/css/jquery.jOrgChart.css" />-->
<script type="text/javascript" src="js/js_constants.js"></script>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<!--<script type="text/javascript" src="js/fileinput/jquery.fileinput.min.js"></script>-->
<script type="text/javascript" src="js/appendGrid/jquery.appendGrid-1.3.5.min.js"></script>
<script type="text/javascript" src="js/validation/jquery.validate.min.js"></script>
<!--<script type="text/javascript" src="js/validation/additional-methods.min.js"></script>-->
<script type="text/javascript" src="js/validation/jquery.ui.datepicker.validation.min.js"></script>
<script type="text/javascript" src="js/validation/messages_${language}.js"></script>
<!--<script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>-->
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
*:after {
	content: none;
}
</style>
<script type="text/javascript">
$(function(){

	  removeCookie("bio_stop_jo_notify");
	  removeCookie("bio_stop_spinv_notify");
	  removeCookie("bio_stop_mreq_notify");
	  removeCookie("bio_stop_ppm_notify");
	  
	  removeCookie("gen_stop_jo_notify");
	  removeCookie("gen_stop_spinv_notify");
	  removeCookie("gen_stop_mreq_notify");
	  removeCookie("gen_stop_ppm_notify");
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
                    userName: "<fmt:message key='jsp.js.login.userName.msg' />",
                    password: "<fmt:message key='jsp.js.login.password.msg' />"
                }
        });
	  
});   
</script>
</head>
<body>

<%-- language: <c:out value="${sessionScope.language}"/> --%>
<%-- direction: <c:out value="${sessionScope.direction}"/>  --%>

<%@include file="/NoScript.jsp" %>
<div id="container">
  <%@include file="/Header.jsp" %>
  
  <div id="content">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top" id="content_container"><form action="LoginProcess" method="post">
            <div id="login_form">
              <table align="center" class="layout_grid">
                <tr>
                  <td colspan="2"><%=WebUtil.getSessionMessage(request)%></td>
                </tr>
                <%
                	if(Default.ENABLE_LANGUAGE_SWITCHING){
                %>
                <tr>
                  <td>&nbsp;</td>
                  <td align="center" style="height: 45px">
                  <%
                  	for (Enums.SUPPORTED_LANGUAGES l : Enums.SUPPORTED_LANGUAGES.values()){
                  %>
                 	 <input type="radio" value="<%=l.getLanguageCode()%>" name="lang">
                 	 <%=l.getDisplayName()%>
                 	 &nbsp;&nbsp;
                  <%
                  	}
                  %>
                  </td>
                </tr>
                <%
                	}
                %>
                <tr>
                  <td class="side_label_top"><fmt:message key="jsp.login.username" /> : </td>
                  <td><input id="userName" name="userName" type="text"></td>
                </tr>
                <tr>
                  <td class="side_label_top"><fmt:message key="jsp.login.password" /> : </td>
                  <td><input id="password" name="password" type="password"></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td><input type="submit" name="button" id="button" value="<fmt:message key="jsp.login.submit" />" style="margin-bottom: 0"></td>
                </tr>
              </table>
              <!-- 
              <h1 style="text-align:center;color:#f00;line-height:50px">
              عزيزي المستخدم ... <br /> 
              يرجى العلم أنه تم وقف البرنامج مؤقتا لتطبيق بعد التحديثات الهامة <br />
              وسيتم إعادة التشغيل اليوم في أقرب وقت إن شاء الله تعالى
              </h1>
               -->
            </div>
          </form></td>
      </tr>
    </table>
  </div>
  <%
  	if(Default.ENABLE_LANGUAGE_SWITCHING){
  %>
	  <script type="text/javascript">
	  $(function() {
		  setRadioCheckedValue("lang","<%=WebUtil.getDisplayLanguagePreference(request)%>");
		  $("input[name='lang']").change(function(){
			  window.location.href = window.location.href.split('?')[0] + "?lang=" + $("input[name='lang']:checked").val();
		  });
	  });
	  </script>
  <%} %>
  <%@include file="/Footer.jsp" %>
</div>
</body>
</html>
