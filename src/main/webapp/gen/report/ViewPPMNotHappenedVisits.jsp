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
		List<GenHospitalDevicePpmDetail> ppmList = (List<GenHospitalDevicePpmDetail>)request.getAttribute("ppmList");
	%>
	<table id="ppm_list" class="tablesorter">
		<thead>
			<tr>
			    <th>م</th>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<th>الموديل</th>
				<!-- <th>الفئة</th> -->
				<th>الوكيل</th>
				<th>سبب عدم إتمام الزيارة</th>
				<th>تقرير سبب التخلف</th>
			</tr>
		</thead>
		<tbody>
			<%
			GenHospitalDevicePpmDetail ppm = null;
			GenHospitalDevice hd = null;
					  //  String qStrFirstPart = Default.SEARCH_DEST_PARAM_NAME +"="+Default.SEARCH_DEST_FOLLOWUP_JOB_OREDR_PARAM_VALUE;
					  //  String qStr = null;
					    for (int i = 0; i< ppmList.size(); i++){
					         ppm = ppmList.get(i);
					         hd = ppm.getHospitalDevice();
					     //    qStr = "?" + qStrFirstPart + "&ppmbOrderNo=" + ppm.getJobOrderNo();
			%>
			<tr>
			    <td align="center"><%=(i+1)%></td>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getModel(),"-",true)%></td>
				<%-- <td><%=Common.getDisplayString(hd.getCategory().getName(),"-",true)%></td> --%>
				<td><%=Common.getDisplayString(ppm.getSubcontractor(),"-",true)%></td>
				<td><%=Common.getDisplayString(ppm.getAbsenceReason(),"-",true)%></td>

				<td>
				   <%= HtmlUtil.showUploadedFilesInViewMode(ppm.getAbsenceReportUrl(), false, langCode)%>
				</td>
			</tr>
			<%
				}
			%>
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
