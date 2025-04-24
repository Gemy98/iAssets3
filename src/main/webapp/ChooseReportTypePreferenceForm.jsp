<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.entity.*,java.util.* , com.iassets.common.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form method="post" target="_blank" rel='opener' action='<%=(String) request.getAttribute("formAction")%>'>
		<table>
			<tr>
				<td><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
			</tr>
			<tr>
				<td valign="top"><%@include	file="ChooseReportTypePreference.jsp"%></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value='<fmt:message key="jsp.ChooseReportTypePreferenceForm.submitBtn" /> ' /></td>
			</tr>
		</table>
	</form>
</body>
</html>
