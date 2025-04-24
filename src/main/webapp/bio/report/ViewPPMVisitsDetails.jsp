<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.common.util.Enums,com.iassets.common.util.DateUtil,com.iassets.biomedical.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
		String langCode = WebUtil.getSessionLanguage(request);	
		List<BioHospitalDevicePpmDetail> ppmList = (List<BioHospitalDevicePpmDetail>)request.getAttribute("ppmList");
	%>
	<p style="text-align: right; color: red; "> <fmt:message key="jsp.ViewPPMVisitsDetails.note" /> </p>
	<table id="ppm_list" class="tablesorter">
		<thead>
			<tr>
			    <th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list1" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list2" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list3" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list4" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list5" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list6" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list7" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list8" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list9" /></th>
				<th><fmt:message key="jsp.ViewPPMVisitsDetails.ppm_list10" /></th>
			</tr>
		</thead>
		<tbody>
			<%
				BioHospitalDevicePpmDetail ppm = null;
					    BioHospitalDevice hd = null;
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
				<td><%=Common.getDisplayString(hd.getCategory().getLocalizedName(langCode),"-",true)%></td>
				<td><%=Common.getDisplayString(Enums.YEAR_MONTHS.getNameById(""+ ppm.getVisitMonth() , langCode),"-",true)%></td>
				<td><%=DateUtil.getDateString(ppm.getVisitDate())%></td>
				<td><%=(Enums.PPM_VISIT_STATUS.IN_TIME.getId() == ppm.getVisitStatus())? LocalizationManager.getLiteral("jsp.ViewPPMVisitsDetails.VisitStatus2", langCode):LocalizationManager.getLiteral("jsp.ViewPPMVisitsDetails.VisitStatus1", langCode)%></td>
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
