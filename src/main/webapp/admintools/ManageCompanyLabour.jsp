<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.iassets.common.entity.CommonEmployee,com.iassets.common.entity.CommonLookupUserType"%>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<body>
	<form method="post" action="${processServlet}">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">${pageTitle}</h1></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.ManageCompanyLabour.userType" />:</td>
				<td>
					<select name="userType"	id="userType" onchange="_onChangUserType()"> 
					<%
						 List<CommonLookupUserType> managedUserTypes = (List<CommonLookupUserType>) request.getAttribute("managedUserTypes");
                                    for (CommonLookupUserType uType : managedUserTypes) {
                              %>
                               <option
                                  value="<%=uType.getId()%>"><%=uType.getJobTitle(WebUtil.getSessionLanguage(request))%></option>
                           <%
                            }
                           %>
						
						
					</select>
				</td>
			</tr>
			<c:if test="${selectedUserTypeId != null}">
				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.ManageCompanyLabour.empId" />:</td>
					<td>
						<select name="empId" id="empId"	class="auto_off" onchange='rerenderUI("${displayServlet}",true)'>
							<option value="0"><fmt:message key="jsp.ManageCompanyLabour.newEmp" /></option>
							 <%
                                 List<CommonEmployee> employeeList = (List<CommonEmployee>) request.getAttribute("employeeList");
                                    for (CommonEmployee employee : employeeList) {
                              %>
                               <option
                                  value="<%=employee.getId()%>"><%=employee.getName(WebUtil.getSessionLanguage(request))%></option>
                           <%
                            }
                           %>
						</select>
					</td>
				</tr>
				<%@include file="ManageSiteEmployee.jspf"%>
			</c:if>
		</table>
	</form>
</body>

<script type="text/javascript">

	setComboSelectedValue("userType","${selectedUserTypeId}");
	setComboSelectedValue("empId", "${selectedEmployeeId}");
	
	function _onChangUserType() {
		$("#empId").remove();
		rerenderUI("${displayServlet}",true)
	}

</script>
</html>