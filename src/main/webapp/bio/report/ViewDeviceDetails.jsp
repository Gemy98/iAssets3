<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*, com.iassets.biomedical.util.*, com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<%
	String sLangCode = WebUtil.getSessionLanguage(request);
	BioHospitalDevice deviceInfo = (BioHospitalDevice) request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
	if (deviceInfo == null)
		throw new Exception(LocalizationManager.getLiteral("jsp.ViewDeviceDetails.exception", sLangCode));
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<style>
div#container {
	width: 960px
}

table.layout_grid {
	border: 1px solid #aaa;
	width: auto;
	margin: 20px auto 20px auto;
	border-collapse: collapse;
}

table.layout_grid>tbody>tr>td {
	border: 1px solid #aaa;
	padding: 10px !important
}

table.layout_grid>tbody>tr>td+td {
	min-width: 200px
}
</style>
<body>
	<table class="layout_grid">
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.name' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.code' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getCode(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.serialNo' />:</td>
			<td><%=Common.getDisplayString(deviceInfo.getSerialNo(), "", true)%></td>
		</tr>
		<%
			if (deviceInfo.getCategory() != null) {
		%>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.category' />:</td>
			<td><%=Common.getDisplayString(deviceInfo.getCategory().getLocalizedName(sLangCode), "", true)%></td>
		</tr>
		<%
			}
		%>
		<%
			if (deviceInfo.getHospitalDepartment() != null) {
		%>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.hospitalDepartment' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getHospitalDepartment().getLocalizedName(sLangCode), "",
						true)%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.model' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getModel(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.manufacturerName' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getManufacturerName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.agentName' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getAgentName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.price' /> :</td>
			<td><%=Common.getDisplayString(deviceInfo.getPrice(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message
					key='jsp.ViewDeviceDetails.status' /> :</td>
			<td><%=Common.getDisplayString(AppUtil.getDeviceStatusName(deviceInfo, sLangCode), "", true)%></td>
		</tr>
		<%
			List<BioHospitalDeviceAccessory> accessories = deviceInfo.getHospitalDeviceAccessories();
			if (accessories != null && !accessories.isEmpty()) {
		%>
		<tr>
			<td colspan="2">
				<h2>
					<fmt:message key='jsp.ViewDeviceDetails.device_accessories' />
					:
				</h2>
				<table id="device_accessories" class="tablesorter">
					<thead>
						<tr>
							<th><fmt:message
									key='jsp.ViewDeviceDetails.device_accessories1' /></th>
							<th><fmt:message
									key='jsp.ViewDeviceDetails.device_accessories2' /></th>
							<th><fmt:message
									key='jsp.ViewDeviceDetails.device_accessories3' /></th>
						</tr>
					</thead>
					<tbody>
						<%
							int i = 1;
								for (BioHospitalDeviceAccessory hda : accessories) {
						%>
						<tr>
							<td align="center"><%=i++%></td>
							<td><%=Common.getDisplayString(hda.getQuantity(), "-", true)%></td>
							<td><%=Common.getDisplayString(hda.getDescription(), "-", true)%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table> <script>
					$(function() {
						$("#device_accessories").tablesorter({
							widthFixed : false,
							widgets : [ 'zebra' ],
							headers : {
								0 : {
									sorter : false
								},
								1 : {
									sorter : false
								},
								2 : {
									sorter : false
								}
							}
						});
					});
				</script>
			</td>
		</tr>
		<%
			}
		%>

		<%
			String attachments = deviceInfo.getOtherAttachments();
			if (attachments != null) {
		%>
		<tr>
			<td colspan="2">
				<h2>
					<fmt:message key='jsp.ViewDeviceDetails.attachments' />
					:
				</h2> <%=HtmlUtil.showUploadedFilesInViewMode(attachments, false, sLangCode)%>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<script>
		
	</script>
</body>
</html>