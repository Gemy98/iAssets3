<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
	String langCode = WebUtil.getSessionLanguage(request);	
	List<GenHospitalDeviceInternalPpmNotificationSchedule> nsList = (List<GenHospitalDeviceInternalPpmNotificationSchedule>)request.getAttribute("nsList");
	%>
	<table id="ppm_list" class="tablesorter">
		<thead>
			<tr>
			    <th>م</th>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<th>الرقم التسلسلي</th>
				<th>الصانع</th>
				<th>الموديل</th>
				<th>المكان</th>
				<th>نوع الصيانة</th>
				<th>التاريخ المجدول للصيانة</th>
			</tr>
		</thead>
		<tbody>
			<%
			GenHospitalDeviceInternalPpmNotificationSchedule nsObj = null;
			GenHospitalDevice device = null;
			for (int i = 0; i< nsList.size(); i++){
				nsObj = nsList.get(i);
				device = nsObj.getHospitalDevice();
			%>
			<tr>
			    <td align="center"><%=(i+1)%></td>
				<td><%=Common.getDisplayString(device.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(device.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(device.getSerialNo(),"-",true)%></td>
				<td><%=Common.getDisplayString(device.getManufacturerName(),"-",true)%></td>
				<td><%=Common.getDisplayString(device.getModel(),"-",true)%></td>
				<td><%=Common.getDisplayString(device.getLocation(langCode),"-",true)%></td>
				<td><%=Common.getDisplayString(Enums.GPPM_PERIOD.getGPPMPeriod(nsObj.getPpmPeriodId()).getName(langCode) ,"-",true)%></td>
				<td><%=DateUtil.getDateString(nsObj.getPlannedDate())%></td>
			</tr>
			<%}%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#ppm_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers: { 0: { sorter: false} }
			});
		});
	</script>
</body>
</html>
