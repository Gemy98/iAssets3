<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
	<body>
		<%
	      String langCode = WebUtil.getSessionLanguage(request);
		  List<GenEndUserMaintenanceRequest> devices = (List<GenEndUserMaintenanceRequest>)request.getAttribute("maintenanceRequestList");
		  boolean currentUserIsEndUser = request.isUserInRole(Enums.SYS_ROLES.BIO_AND_GEN_END_USER.getRoleName());
		%>
		<table id="mreq_list" class="tablesorter">
			<thead>
				<tr>
				    <th>م</th>
					<th>كود الجهاز</th>
					<th>اسم الجهاز</th>
					<th>الموديل</th>
					<th>الموقع</th>
					<th>اسم المستخدم</th>
					<th>تاريخ العطل</th>
					<th>وصف العطل</th>
					<%if(currentUserIsEndUser){%>
					<th>حذف</th>
					<%}else if(WebUtil.userHasRightToOpenJobOrder(request)){ %>
					<th>فتح أمر عمل</th>
					<%}%>
				</tr>
			</thead>
			<tbody>
				<%
					int i = 1;
							for (GenEndUserMaintenanceRequest mr: devices){
				%>
				<tr>
				    <td align="center"><%=i++%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getCode(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getName(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getModel(),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getHospitalDevice().getLocation(langCode),"-",true)%></td>
					<td><%=Common.getDisplayString(mr.getEndUserName(),"-",true)%></td>
					<td><%=DateUtil.getDateString(mr.getDamageDate())%></td>
					<td><%=Common.getDisplayString(mr.getDamageDescription(),"-",true)%></td>
					<%if(currentUserIsEndUser){%>
					<td align="center">
					  <img src="image/delete-icon-22.png" style="cursor:pointer" width="22" height="22" title='حذف طلب الصيانة' onclick="markMaintenanceRequestAsFake(<%=mr.getId()%>)" />
					</td>
					<%}else if(WebUtil.userHasRightToOpenJobOrder(request)){ %>
					<td align="center">
					  <img src="image/fix-icon-22.png" style="cursor:pointer" width="22" height="22" title='فتح أمر عمل' onclick="openJobOrderForMaintenanceRequest(<%=mr.getId()%>)"  />
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
				if(confirm("هل تريد حذف طلب الصيانة؟"))
				   window.location.href="gen/MarkMaintenanceRequestAsFake?<%=Default.MAINTENANCE_REQUEST_ID_PARAM_NAME%>=" + mreqId;
			}
			
			function openJobOrderForMaintenanceRequest(mreqId){
				if(confirm("هل تريد فتح أمر عمل؟"))
				{
					window.opener.location.href="gen/OpenJobOrderForMaintenanceRequest?<%=Default.MAINTENANCE_REQUEST_ID_PARAM_NAME%>=" + mreqId;
					window.close();
				}
			}
		</script>
	</body>
</html>