<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />

<%
	Boolean flag = (Boolean) request.getAttribute("showClosedJobOrders");
    Boolean flag2 = (Boolean) request.getAttribute("viewLate");
	boolean showClosedJobOrders = (flag != null && flag == true);
	boolean showOpenedJobOrders = (flag2 != null && flag2 == false);
	boolean showLateJobOrders = (flag2 != null && flag2 == true);
%>

<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
		String langCode = WebUtil.getSessionLanguage(request);		
		List<BioJobOrder> jobOrders = (List<BioJobOrder>)request.getAttribute("jobOrders");
	%>
	<table id="jo_list" class="tablesorter">
		<thead>
			<tr>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list1" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list2" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list3" /></th>
				<%
					if(showLateJobOrders){
				%>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list4" /></th>
				<%
					}
				%>
				<%
					if(!showClosedJobOrders){
				%>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list5" /></th>
				<%
					}
				%>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list6" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list7" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list8" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list9" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list10" /></th>
				<%
					if(showOpenedJobOrders || showClosedJobOrders){
				%>
					<th><fmt:message key="jsp.ViewJobOrdersList.jo_list11" /></th>
					<th><fmt:message key="jsp.ViewJobOrdersList.jo_list12" /></th>
				<%
					}
				%>
				<%
					if(showClosedJobOrders){
				%>
					<th><fmt:message key="jsp.ViewJobOrdersList.jo_list13" /></th>
					<th><fmt:message key="jsp.ViewJobOrdersList.jo_list14" /></th>
				<%
					}
				%>
				<%
					if(!showClosedJobOrders){
				%>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list15" /></th>
				<th><fmt:message key="jsp.ViewJobOrdersList.jo_list16" /></th>
				<%
					}
				%>
			</tr>
		</thead>
		<tbody>
			<%
				BioJobOrder jo = null;
					    BioHospitalDevice hd = null;
					    String qStr = null;
					    for (int i = 0; i< jobOrders.size(); i++){
					         jo = jobOrders.get(i);
					         hd = jo.getHospitalDevice();
			%>
			<tr>
				<td align="center"><%=(i+1)%></td>
				<td>
					<%
						String jobOrderNo = Common.getDisplayString(jo.getJobOrderNo(),"-",true); 
													  if(WebUtil.currentUserHasViewPrivilegesOnly(request) || showClosedJobOrders){
														  out.print(jobOrderNo);
													  }else{
														 
					%> 
					<a href="javascript:followupJobOrder('<%=jobOrderNo%>')" ><%=jobOrderNo%></a> 
					<%}%>
				</td>
				<td><%=DateUtil.getDateString(jo.getJobOrderDate())%></td>
				<%if(showLateJobOrders){%>
				<td><%=DateUtil.getDateString(jo.getDamageDate())%></td>
				<%}%>
				<%if(!showClosedJobOrders){%>
				<td><%=Common.getDisplayString(jo.getDamageDescription(),"-",true)%></td>
				<%}%>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCategory().getLocalizedName(langCode),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getHospitalDepartment().getLocalizedName(langCode),"-",true)%></td>

				<td>
				<%
				  if(jo.getMaintenanceRequest() != null)
					out.print(jo.getMaintenanceRequest().getEndUserName());
				  else
					out.print(HtmlUtil.showUploadedFilesInViewMode(jo.getRequestFormUrl(), false, langCode));
				%>
				</td>
				
				<%if(showOpenedJobOrders || showClosedJobOrders){%>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getAgentReportUrl(), false, langCode)%>
				</td>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getQuotationScanUrl(), false, langCode)%>
				</td>
				<%}%>
				
				<%if(showClosedJobOrders){%>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getFinalAgentReportUrl(), false, langCode)%>
				</td>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getFinalReportUrl(), false, langCode)%>
				</td>
                <%}%>
                <%if(!showClosedJobOrders){%>
                <td><%=Common.getDisplayString(jo.getCurrentPhaseName(langCode),"-",true)%></td>
				<td>
				<%-- <%=Common.getDisplayString((showClosedJobOrders)?jo.getClosedBy().getName():jo.getOpenedBy().getName(),"-",true)%> --%>
				<%=Common.getDisplayString(jo.getResponsibleEngineer().getName(langCode),"-",true)%>
				</td>
				<%}%>
			</tr>
			<%}%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#jo_list thead th:eq(1)").data("sorter", false);
			$("#jo_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers : {
					0 : {
						sorter : false
					}
				}
			});
		});
		
		function followupJobOrder(jobOrderNo){
			if(confirm('<fmt:message key="jsp.js.ViewJobOrdersList.confirm" />'))
			{
				window.opener.location.href="bio/FollowupJobOrder?purpose="+ "<fmt:message key='jsp.js.ViewJobOrdersList.msg'/>"+"&jobOrderNo=" + jobOrderNo;
				window.close();
			}
		}
	</script>
</body>
</html>
