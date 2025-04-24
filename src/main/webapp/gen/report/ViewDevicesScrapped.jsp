<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
	    String langCode = WebUtil.getSessionLanguage(request);
		List<GenHospitalDeviceScrappingInfo> scrappingInfoList = (List<GenHospitalDeviceScrappingInfo>) request.getAttribute("scrappingInfoList");
	%>
	<table id="device_list" class="tablesorter">
		<thead>
			<tr>
			    <th>م</th>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<th>الرقم التسلسلي</th>
				<!-- <th>الفئة</th> -->
				<th>تاريخ التكهين</th>
				<th>سبب التكهين</th>
				<th>محضر التكهين</th>
			</tr>
		</thead>
		<tbody>
			<%
			int i = 1;
			GenHospitalDevice hd = null;
			for (GenHospitalDeviceScrappingInfo scrappInfo: scrappingInfoList){
				hd = scrappInfo.getHospitalDevice();
			%>
			<tr>
			    <td align="center"><%=i++%></td>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getSerialNo(),"-",true)%></td>
				<%-- <td><%=Common.getDisplayString(hd.getCategory().getName(),"-",true)%></td> --%>
				<td><%=DateUtil.getDateString(scrappInfo.getScrappingDate())%></td>
				<td><%=Common.getDisplayString(scrappInfo.getScrappingReason().getLocalizedName(langCode),"-",true)%></td>
				
				<td>
				   <%= HtmlUtil.showUploadedFilesInViewMode(scrappInfo.getScrappingFinalReportUrl(), false, langCode)%>
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
