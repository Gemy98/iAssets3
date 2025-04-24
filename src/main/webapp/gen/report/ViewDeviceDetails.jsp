<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,java.util.*"%>
<%
	String langCode = WebUtil.getSessionLanguage(request);

	GenHospitalDevice deviceInfo = (GenHospitalDevice) request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
	if (deviceInfo == null)
		throw new Exception("لم يتم تعيين جهاز لإتمام العملية المطلوبة عليه");
%>
<html>
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
			<td class="side_label_middle">اسم الجهاز :</td>
			<td><%=Common.getDisplayString(deviceInfo.getName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">كود الجهاز :</td>
			<td><%=Common.getDisplayString(deviceInfo.getCode(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">الرقم التسلسلي :</td>
			<td><%=Common.getDisplayString(deviceInfo.getSerialNo(), "", true)%></td>
		</tr>
		<%-- 		<%if(deviceInfo.getCategory() != null){ %>
		<tr>
			<td class="side_label_middle">فئة الجهاز :</td>
			<td><%=Common.getDisplayString(deviceInfo.getCategory().getName(), "",true)%></td>
		</tr>
		<%}%> --%>
		<%
			if (deviceInfo.getHospitalDepartment() != null) {
		%>
		<tr>
			<td class="side_label_middle">المكان :</td>
			<td><%=Common.getDisplayString(deviceInfo.getLocation(langCode), "", true)%></td>
		</tr>
		<%
			}
		%>
		<tr>
			<td class="side_label_middle">الموديل :</td>
			<td><%=Common.getDisplayString(deviceInfo.getModel(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">الصانع :</td>
			<td><%=Common.getDisplayString(deviceInfo.getManufacturerName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">الوكيل :</td>
			<td><%=Common.getDisplayString(deviceInfo.getAgentName(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">سعر الجهاز :</td>
			<td><%=Common.getDisplayString(deviceInfo.getPrice(), "", true)%></td>
		</tr>
		<tr>
			<td class="side_label_middle">حالة الجهاز :</td>
			<td><%=Common.getDisplayString(AppUtil.getDeviceStatusName(deviceInfo, langCode), "", true)%></td>
		</tr>
		<%
			List<GenHospitalDeviceAccessory> accessories = deviceInfo.getHospitalDeviceAccessories();
			if (accessories != null && !accessories.isEmpty()) {
		%>
		<tr>
			<td colspan="2">
				<h2>ملحقات الجهاز :</h2>
				<table id="device_accessories" class="tablesorter">
					<thead>
						<tr>
							<th>م</th>
							<th>الكمية</th>
							<th>الوصف</th>
						</tr>
					</thead>
					<tbody>
						<%
							int i = 1;
								for (GenHospitalDeviceAccessory hda : accessories) {
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
				<h2>مرفقات الجهاز :</h2> <%=HtmlUtil.showUploadedFilesInViewMode(attachments, false, langCode)%>
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