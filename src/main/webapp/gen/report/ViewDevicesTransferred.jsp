<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,java.util.*"%>
	<%String langCode = WebUtil.getSessionLanguage(request); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
		List<GenHospitalDeviceTransferringInfo> transferringInfoList = (List<GenHospitalDeviceTransferringInfo>) request.getAttribute("transferringInfoList");
	%>
	<table id="device_list" class="tablesorter">
		<thead>
			<tr>
			    <th>م</th>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<th>الرقم التسلسلي</th>
				<!-- <th>الفئة</th> -->
				<th>تاريخ النقل</th>
				<th>سبب النقل</th>
				<th>الجهة الصادر إليها</th>
				<th>تقرير النقل/السحب</th>
			</tr>
		</thead>
		<tbody>
			<%
			int i = 1;
			GenHospitalDevice hd = null;
			for (GenHospitalDeviceTransferringInfo transInfo: transferringInfoList){
				hd = transInfo.getHospitalDevice();
			%>
			<tr>
			    <td align="center"><%=i++%></td>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getSerialNo(),"-",true)%></td>
				<%-- <td><%=Common.getDisplayString(hd.getCategory().getName(),"-",true)%></td> --%>
				<td><%=DateUtil.getDateString(transInfo.getTransferDate())%></td>
				<td><%=Common.getDisplayString(transInfo.getTransferReason(),"-",true)%></td>
				<td><%=Common.getDisplayString(transInfo.getTransferDestination(),"-",true)%></td>
				<td>
				   <%= HtmlUtil.showUploadedFilesInViewMode(transInfo.getTransferReportUrl(), false, langCode)%>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#device_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers: { 0: { sorter: false} }
			});
		});
	</script>
</body>
</html>
