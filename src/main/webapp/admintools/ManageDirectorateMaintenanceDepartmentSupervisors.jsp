<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.iassets.common.entity.CommonEmployee"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<body>
	<form method="post" action="${processServlet}">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">${pageTitle}</h1></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.ManageDirectorateMaintenanceDepartmentSupervisors.supervisorId" /> :</td>
				<td><select name="supervisorId" id="supervisorId" class="auto_off" onchange='rerenderUI("${displayServlet}",true)'>
						<option value="0"> <fmt:message key="jsp.ManageDirectorateMaintenanceDepartmentSupervisors.newSupervisor" /> </option>
						<%
                                 List<CommonEmployee> supervisorsList = (List<CommonEmployee>) request.getAttribute("supervisorsList");
                                    for (CommonEmployee supervisor : supervisorsList) {
                              %>
                               <option
                                  value="<%=supervisor.getId()%>"><%=supervisor.getName(WebUtil.getSessionLanguage(request))%></option>
                           <%
                            }
                           %>
				</select></td>
			</tr>
			<%@include file="ManageSiteEmployee.jspf"%>
		</table>
	</form>
</body>
<script type="text/javascript">
	setComboSelectedValue("supervisorId", "${selectedSupervisorId}");
</script>
</html>