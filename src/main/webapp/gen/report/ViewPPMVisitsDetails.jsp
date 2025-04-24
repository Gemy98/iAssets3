<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
	
	    String langCode = WebUtil.getSessionLanguage(request);	

		List<GenHospitalDevicePpmDetail> ppmList = (List<GenHospitalDevicePpmDetail>)request.getAttribute("ppmList");
	%>
	<p style="text-align: right; color: red; ">* الشهر المجدول هو شهر الزيارة حسب نموذج 18</p>
	<table id="ppm_list" class="tablesorter">
		<thead>
			<tr>
			    <th>م</th>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<th>الرقم التسلسلي</th>
				<!-- <th>الفئة</th> -->
				<th>الشهر المجدول</th>
				<th>تاريخ الزيارة الفعلي</th>
				<th>حالة الزيارة</th>
				<th>القائم بالزيارة</th>
				<th>تقرير الزيارة</th>
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
				<td><%=Common.getDisplayString(hd.getSerialNo(),"-",true)%></td>
				<%-- <td><%=Common.getDisplayString(hd.getCategory().getName(),"-",true)%></td> --%>
				<td><%=Common.getDisplayString(Enums.YEAR_MONTHS.getNameById(""+ ppm.getVisitMonth() , langCode),"-",true)%></td>
				<td><%=DateUtil.getDateString(ppm.getVisitDate())%></td>
				<td><%=(Enums.PPM_VISIT_STATUS.IN_TIME.getId() == ppm.getVisitStatus())?"في موعدها":"تمت متأخرة"%></td>
				<td><%=Common.getDisplayString(ppm.getSubcontractor(),"-",true)%></td>
				<td>
			    	<%= HtmlUtil.showUploadedFilesInViewMode(ppm.getVisitReportUrl(), false, langCode)%>
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
