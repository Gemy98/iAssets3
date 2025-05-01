<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"
	import="org.apache.commons.lang.StringEscapeUtils,com.iassets.common.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<%
    if (request.getAttribute("includeContent") == null) {
        request.setAttribute("includeContent", "1");
        request.setAttribute("dest", "/error/Error.jsp");
        pageContext.forward("/" + WebUtil.getCurrentlyActiveAppDirectory(request) + "/Main.jsp");
        return;
    }
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Error</title>
</head>
<body>
	<%
    //Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if(statusCode != null && (statusCode == 401 || statusCode == 403)){
%>

	<h1 class='error_msg'>
		<fmt:message key="jsp.Error.msg1" />
	</h1>

	<%}else{ %>

	<h1 class='error_msg'>
		<fmt:message key="jsp.Error.msg2" />
	</h1>
	<h1 class='error_msg'>
		<fmt:message key="jsp.Error.msg3" />
	</h1>

	<h4 dir='ltr'>Error Message:</h4>

	<p dir='ltr' style="white-space: pre-wrap">
		<%=StringEscapeUtils.escapeHtml(exception.getMessage())%>
	</p>

	<%}%>

</body>
</html>
