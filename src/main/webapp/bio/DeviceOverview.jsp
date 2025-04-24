<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
    String langCode = WebUtil.getSessionLanguage(request);

    BioHospitalDevice deviceInfo = (BioHospitalDevice) request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
	if (deviceInfo == null)
		throw new Exception(LocalizationManager.getLiteral("jsp.DeviceOverview.exception", langCode));

	String code = Common.getDisplayString(deviceInfo.getCode(), "");
	String serial = Common.getDisplayString(deviceInfo.getSerialNo(),"");
	String name = Common.getDisplayString(deviceInfo.getName(),"");
	String model = Common.getDisplayString(deviceInfo.getModel(), "");
	String category = Common.getDisplayString(deviceInfo.getCategory().getLocalizedName(langCode), "");
	String dep = Common.getDisplayString(deviceInfo.getHospitalDepartment().getLocalizedName(langCode), "");
	
	BioEndUserMaintenanceRequest mReqInfo = (BioEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);
%>
</head>
<body>
	<table class="layout_grid">
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.DeviceOverview.code" /> :</td>
			<td><label id="code"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.DeviceOverview.serial" /> :</td>
			<td><label id="serial"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.DeviceOverview.name" /> :</td>
			<td><label id="name"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.DeviceOverview.model" /> :</td>
			<td><label id="model"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle">  <fmt:message key="jsp.DeviceOverview.category" /> :</td>
			<td><label id="category"></label> <input type="hidden"
				id="device_category" name="device_category"
				value="<%=deviceInfo.getCategory().getId()%>" /></td>
		</tr>
		<tr>
			<td class="side_label_middle">  <fmt:message key="jsp.DeviceOverview.dep" /> :</td>
			<td><label id="dep"></label></td>
		</tr>
		<%if(mReqInfo != null){%>
		<tr>
			<td class="side_label_middle">  <fmt:message key="jsp.DeviceOverview.endUserName" /> :</td>
			<td><label id="endUserName"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.DeviceOverview.mReqDate" /> :</td>
			<td><label id="mReqDate"></label></td>
		</tr>
		<%}%>
	</table>
	<script>	
	$(function(){
	appendIdElement("deviceId","<%=deviceInfo.getId()%>");
	setInnerHTML("code","<%=code%>");
	setInnerHTML("serial","<%=serial%>");
	setInnerHTML("name","<%=name%>");
	setInnerHTML("model","<%=model%>");
	setInnerHTML("category","<%=category%>");
	setInnerHTML("dep","<%=dep%>");
	<%if(mReqInfo != null){
	     String endUserName = Common.getDisplayString(mReqInfo.getEndUserName(),"-");
	     String mReqDate = DateUtil.getDateString(mReqInfo.getRequestedIn());
	 %>
	    appendIdElement("<%=Default.MAINTENANCE_REQUEST_ID_PARAM_NAME%>","<%=mReqInfo.getId()%>");
	    setInnerHTML("endUserName","<%=endUserName%>");
		setInnerHTML("mReqDate","<%=mReqDate%>");
	<%}%>
	});
	</script>
</body>

</html>
