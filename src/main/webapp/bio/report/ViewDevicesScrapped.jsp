<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
		String langCode = WebUtil.getSessionLanguage(request);	
		List<BioHospitalDeviceScrappingInfo> scrappingInfoList = (List<BioHospitalDeviceScrappingInfo>) request.getAttribute("scrappingInfoList");
	%>
	<table id="device_list" class="tablesorter">
		<thead>
			<tr>
			    <th><fmt:message key="jsp.ViewDevicesScrapped.device_list1" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list2" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list3" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list4" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list5" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list6" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list7" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list8" /></th>
			</tr>
		</thead>
		<tbody>
			<%
				int i = 1;
			    BioHospitalDevice hd = null;
				for (BioHospitalDeviceScrappingInfo scrappInfo: scrappingInfoList){
					hd = scrappInfo.getHospitalDevice();
			%>
			<tr>
			    <td align="center"><%=i++%></td>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getSerialNo(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCategory().getLocalizedName(langCode),"-",true)%></td>
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
