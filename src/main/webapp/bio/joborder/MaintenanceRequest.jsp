<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.biomedical.entity.*,com.iassets.biomedical.util.HtmlUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form method="post" action="bio/MaintenanceRequestProcess">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.MaintenanceRequest.pagetitle" /></h1></td>
			</tr>	
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%></td>
			</tr>
			<tr>
				<td class="side_label_middle">  <fmt:message key="jsp.MaintenanceRequest.damageDate" />  :</td>
				<td><input type="text" name="damageDate" id="damageDate" class="caldr" required></td>
			</tr>
			<tr>
				<td class="side_label_top">  <fmt:message key="jsp.MaintenanceRequest.damageDescription" />:</td>
				<td><textarea name="damageDescription" id="damageDescription" required></textarea></td>
			</tr>
			<tr>
				<td class="side_label_middle">  <fmt:message key="jsp.MaintenanceRequest.endUserName" /> :</td>
				<td><input type="text" size="40" name="endUserName" id="endUserName" required></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value=" <fmt:message key="jsp.MaintenanceRequest.submitBtn" />"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
			</tr>
		</table>
	</form>
</body>
</html>
