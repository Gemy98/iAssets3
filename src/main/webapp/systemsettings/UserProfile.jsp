<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><fmt:message key="jsp.UserProfile.pagetitle" /></title>
<%
	String langCode = WebUtil.getSessionLanguage(request);

    CommonUser user = (CommonUser)request.getAttribute("userProfile");	
	String userName  = Common.getDisplayString(user.getName(langCode), "", true); 
	String jobTitle = Common.getDisplayString(user.getJobTitle(langCode), "", true);
	String mobile = Common.getDisplayString(user.getMobile(), " - ", true);
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.UserProfile.pagetitle" /></h1></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.UserProfile.userName" />  : </td>
    <td><%=userName%></td>
  </tr>
  <tr>
    <td class="side_label_middle"> <fmt:message key="jsp.UserProfile.jobTitle" /> : </td>
    <td><%=jobTitle%></td>
  </tr>
  <tr>
    <td class="side_label_middle"> <fmt:message key="jsp.UserProfile.mobile" /> : </td>
    <td><%=mobile%></td>
  </tr>
</table>
</body>
</html>