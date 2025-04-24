<!DOCTYPE html>
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
		List<BioHospitalDevice> deviceList = (List<BioHospitalDevice>) request.getAttribute("deviceList");
	%>
	<table id="device_list" class="tablesorter">
		<thead>
			<tr>
			    <th><fmt:message key="jsp.ViewDevicesScrapped.device_list1" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list2" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list3" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list4" /></th>
				<th><fmt:message key="jsp.ViewDevicesScrapped.device_list5" /></th>
				
				<th><fmt:message key="jsp.ViewDeviceDetails.manufacturerName" /></th>
				<th><fmt:message key="jsp.ViewDeviceDetails.agentName" /></th>
				<th><fmt:message key="jsp.DeviceInfo.subcontractor" /></th>
				<th><fmt:message key="jsp.DeviceInfo.pmAnnualVisitsNo" /></th>
				<th><fmt:message key="jsp.PPMVisit.visitMonth" /></th>	
		  </tr>
		</thead>
		<tbody>
			<%
				int i = 1;
				for (BioHospitalDevice hd: deviceList){
			%>
			<tr>
			    <td align="center"><%=i++%></td>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%> 
				<!-- <a href="javascript:followupPPMVisit(<%=DateUtil.getYear() %>,<%=DateUtil.getMonth() %>,'<%=hd.getId()%>')" ><%=Common.getDisplayString(hd.getName(),"-",true)%></a>-->
				 </td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getSerialNo(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCategory().getLocalizedName(langCode),"-",true)%></td>
				
				<td><%=Common.getDisplayString(hd.getManufacturerName(),"-",true) %></td>
				<td><%=Common.getDisplayString(hd.getAgentName(),"-",true)%></td>								
				<td><%=Common.getDisplayString(hd.getSubcontractor(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getPmAnnualVisitsNo(),"-",true)%></td>
				<td><%=Common.getDisplayString(AppUtil.getPPMVisitMonthsNamesAsString(hd,langCode),"-",true)%></td>
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
		
		function followupPPMVisit(visitYear,visitMonth,deviceId)
		{
			if(confirm('<fmt:message key="jsp.Menu.PpmFollowup.confirm" />'))
			{
				window.opener.location.href="bio/PPMVisitDisplay?purpose="+ "<jsp.Menu.PpmFollowup'/>"+"&visitMonth=" + visitMonth+"&visitYear="+visitYear+"&deviceId="+deviceId+"&PPMReportFlag=f";
				window.close();
			}
		}
	</script>
</body>
</html>
