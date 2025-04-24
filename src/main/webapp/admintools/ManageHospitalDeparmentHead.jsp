<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.iassets.common.entity.CommonHospitalDepartment"%>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<body>
	<form method="post" action="${processServlet}">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">${pageTitle}</h1></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.ManageHospitalDeparmentHead.departmentId" /> :</td>
				<td><select name="departmentId" id="departmentId"  required onchange='rerenderUI("${displayServlet}",true)'>
				<% 
					List<CommonHospitalDepartment> departmentList = (List<CommonHospitalDepartment>) request.getAttribute("departmentList"); 
					for(CommonHospitalDepartment department: departmentList) {
				%>
				
							<option value="<%= department.getId()%>"><%= department.getLocalizedName(WebUtil.getSessionLanguage(request)) %></option>
				<% } %>
				</select></td>
			</tr>
			<c:if test="${selectedHospDepId != null}">
				<%@include file="ManageSiteEmployee.jspf"%>
			</c:if>
		</table>
	</form>
</body>
<script type="text/javascript">
	setComboSelectedValue("departmentId",${selectedHospDepId});
</script>
</html>