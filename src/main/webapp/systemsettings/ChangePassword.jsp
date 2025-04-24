<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<% String currAppDir = WebUtil.getCurrentlyActiveAppDirectory(request); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>  <fmt:message key="jsp.ChangePassword.pagetitle" /> </title>
</head>
<body>
	<form method="post" action="<%=currAppDir%>/ChangePasswordProcess">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.ChangePassword.pagetitle" /> </h1></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.ChangePassword.currentPass" />  :</td>
				<td><input type="password" name="currentPass" id="currentPass" required></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.ChangePassword.newPass" />  :</td>
				<td><input type="password" name="newPass" id="newPass" required></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.ChangePassword.confNewPass" />  :</td>
				<td><input type="password" name="confNewPass" id="confNewPass"
					required data-rule-equalTo="#newPass"
					data-msg-equalTo='<fmt:message key="jsp.ChangePassword.confNewPass.msg" />'></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="button" id="button" value='<fmt:message key="jsp.ChangePassword.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>