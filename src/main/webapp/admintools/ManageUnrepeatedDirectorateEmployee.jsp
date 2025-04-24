<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<body>
	<form method="post" action ="${processServlet}">
		<table class="layout_grid">
		  <tr>
		    <td colspan="2"><h1 class="page_title">${pageTitle}</h1></td>
		  </tr>
		  <%@include file="ManageSiteEmployee.jspf" %>
		</table>
	</form>
</body>
</html>