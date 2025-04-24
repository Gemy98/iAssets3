<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
	<body>
		<%
		  String langCode = WebUtil.getSessionLanguage(request);	
		  List<BioEndUserMaintenanceRequest> devices = (List<BioEndUserMaintenanceRequest>)request.getAttribute("maintenanceRequestList");
		  boolean currentUserIsEndUser = request.isUserInRole(Enums.SYS_ROLES.BIO_AND_GEN_END_USER.getRoleName());
		%>
		<table id="mreq_list" class="tablesorter"> 
			<thead>
				<tr>
				    <th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list1" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list2" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list3" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list4" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list5" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list6" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list7" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list8" /></th>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list9" /></th>
					<%if(currentUserIsEndUser){%>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list10" /></th>
					<%}else if(WebUtil.userHasRightToOpenJobOrder(request)){ %>
					<th><fmt:message key="jsp.ViewMaintenanceRequestsList.mreq_list11" /></th>
					<%}%>
				</tr>
			</thead>
			<tbody>
				<%
					int i = 1;
							for (BioEndUserMaintenanceRequest mr: devices){
				%>
				<tr>
				    <td align="center"><%=i++%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getCode(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getName(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getModel(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getCategory().getLocalizedName(langCode),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDepartment().getLocalizedName(langCode),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getEndUserName(),"-",true)%></td>
					<td><%=DateUtil.getDateString(mr.getDamageDate())%></td>
					<td><%=Common.getDisplayString(mr.getDamageDescription(),"-",true)%></td>
					<%if(currentUserIsEndUser){%>
					<td align="center">
					  <img src="image/delete-icon-22.png" style="cursor:pointer" width="22" height="22" title='<fmt:message key="jsp.ViewMaintenanceRequestsList.markMaintenanceRequestAsFake" />' onclick="markMaintenanceRequestAsFake(<%=mr.getId()%>)" />
					</td>
					<%}else if(WebUtil.userHasRightToOpenJobOrder(request)){ %>
					<td align="center">
					  <img src="image/fix-icon-22.png" style="cursor:pointer" width="22" height="22" title='<fmt:message key="jsp.ViewMaintenanceRequestsList.openJobOrderForMaintenanceRequest" />' onclick="openJobOrderForMaintenanceRequest(<%=mr.getId()%>)"  />
					</td>
					<%}%>
				</tr>
				<%}%>
			</tbody>
		</table>
		<script>
			$(function() {
				$("#mreq_list").tablesorter({
					widthFixed : false,
					widgets : [ 'zebra' ],
					headers: { 0: { sorter: false},9: { sorter: false}}
				});
			});
			
			function markMaintenanceRequestAsFake(mreqId){
				if(confirm('<fmt:message key="jsp.js.ViewMaintenanceRequestsList.markMaintenanceRequestAsFake" />' ))
				   window.location.href="bio/MarkMaintenanceRequestAsFake?<%=Default.MAINTENANCE_REQUEST_ID_PARAM_NAME%>=" + mreqId;
			}
			
			function openJobOrderForMaintenanceRequest(mreqId){
				if(confirm('<fmt:message key="jsp.js.ViewMaintenanceRequestsList.openJobOrderForMaintenanceRequest" />'))
				{
					window.opener.location.href="bio/OpenJobOrderForMaintenanceRequest?<%=Default.MAINTENANCE_REQUEST_ID_PARAM_NAME%>=" + mreqId;
					window.close();
				}
			}
		</script>
	</body>
</html>