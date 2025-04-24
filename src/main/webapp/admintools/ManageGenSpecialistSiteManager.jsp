<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.iassets.general.entity.GenLookupJobOrderCategory"%>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<body>
	<form method="post" action="${processServlet}">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">${pageTitle}</h1></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.ManageGenSpecialistSiteManager.category" /> :</td>
				<td>
				    				<%
				List<GenLookupJobOrderCategory> categoryList = (List<GenLookupJobOrderCategory>) request.getAttribute("categoryList");
				  for (GenLookupJobOrderCategory  category: categoryList) {
				%>
				<input name="gmpId" onchange='rerenderUI("gen/ManageSpecialistSiteManagerDisplay",true)' type="radio" required value="<%= category.getId() %>" />
						 <%= category.getLocalizedName(WebUtil.getSessionLanguage(request)) %>
				<% } %>
			    </td>
			</tr>
			<c:if test="${selectedGmpId != null }">
				<%@include file="ManageSiteEmployee.jspf"%>
			</c:if>
		</table>
	</form>
</body>
<script type="text/javascript">
	setRadioCheckedValue("gmpId", "${selectedGmpId}");
</script>
</html>