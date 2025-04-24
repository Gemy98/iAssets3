<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,java.util.*, com.iassets.common.entity.*"%>
<%
	String langCode = WebUtil.getSessionLanguage(request);
    Boolean flag = (Boolean) request.getAttribute("reportRequested");
	boolean reportRequested = (flag != null && flag == true);
	boolean showActionColumn = ! reportRequested && request.isUserInRole(Enums.SYS_ROLES.BIO_ONSITE_ADMINISTRATION.getRoleName());
	List<CommonEmployee> operatingCompanyLaborers = (List<CommonEmployee>)request.getAttribute("operatingCompanyLaborers");

%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<%if (operatingCompanyLaborers != null && ! operatingCompanyLaborers.isEmpty()){%>

	<table id="cse_list" class='<%=(reportRequested)?"tablesorter":"data_table"%>' <%=(!reportRequested)?"style='width:100%;margin-top:0'":""%>>
		<thead>
			<tr>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list1" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list2" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list3" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list4" /></th>
				<%if(reportRequested){%>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list5" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list6" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list7" /></th>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list8" /></th>
				<%}%>
				<%if(showActionColumn){%>
				<th><fmt:message key="jsp.ViewOperatingCompanyOnsiteLaborers_Segment.cse_list9" /></th>
				<%}%>				
			</tr>
		</thead>
		<tbody>
			<%
			CommonEmployee emp = null;
		    for (int i = 0; i< operatingCompanyLaborers.size(); i++){
		         emp = operatingCompanyLaborers.get(i);
			%>
			<tr>
				<td align="center"><%=(i+1)%></td>
				<td align="center"><%=Common.getDisplayString(emp.getEmployeeNo(),"-",true) %></td>
				<td align="center"><%=Common.getDisplayString(emp.getName(langCode),"-",true)%></td>
				<td align="center"><%=Common.getDisplayString(emp.getJobTitle(langCode),"-",true)%></td>
				<%if(reportRequested){%>
				<td align="center"><%=Common.getDisplayString((emp.getNationality() == null)?null:emp.getNationality().getLocalizedName(langCode),"-",true)%></td>
				<td align="center"><%=Common.formatMoneyValue(emp.getContractSalary())%></td>
				<td align="center"><%=DateUtil.getDateString(emp.getEmploymentDate())%></td>
				<td align="center"><%=CommonHtmlUtil.showUploadedFilesInViewMode(emp.getApprovalScan(), false, langCode)%></td>
				<%}%>
				<%if(showActionColumn){%>
				<td align="center">
				  <img src="image/fix-icon-22.png" style="margin-left:5px;cursor:pointer" width="22" height="22" title="<%=LocalizationManager.getLiteral("jsp.ViewOperatingCompanyOnsiteLaborers_Segment.updateEmployee", langCode) %>" onclick="updateEmployee('<%=emp.getId()%>')"  />
				<% if (emp.getUserType().getId() != Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()) { %> 
				  	<img src="image/delete-icon-22.png" style="cursor:pointer" width="22" height="22" title="<%=LocalizationManager.getLiteral("jsp.ViewOperatingCompanyOnsiteLaborers_Segment.deleteEmployee", langCode) %>" onclick="deleteEmployee('<%=emp.getId()%>')" />
				<%} %>			
				</td>
				<%}%>			
			</tr>
			<%}%>
		</tbody>
	</table>
 	<script>
 	<%if(reportRequested){%>
		$(function() {
			//$("#cse_list thead th:eq(1)").data("sorter", false);
			$("#cse_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers : {
					0 : {sorter : false}, 7:{sorter : false}
				}
			});
		});
		
	<%}else{%>

		function deleteEmployee(empId){
			if(confirm("<fmt:message key='jsp.ViewOperatingCompanyOnsiteLaborers_Segment.deleteEmployeeConfirm' />")){
				window.location.href="bio/OperatingCompanyLaborerInfoProcess?operation=0&empId="+empId;
			}
		}
			
		function updateEmployee(empId){
			if(confirm("<fmt:message key='jsp.ViewOperatingCompanyOnsiteLaborers_Segment.updateEmployeeConfirm' />")){
				window.location.href="bio/OperatingCompanyLaborerInfoDisplay?empId="+empId;
			}
		}
	<%}%>
	</script>
	
<%}%>
</body>
</html>
