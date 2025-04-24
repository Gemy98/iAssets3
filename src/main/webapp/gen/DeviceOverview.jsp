<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.general.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
    String langCode = WebUtil.getSessionLanguage(request);

    GenHospitalDevice deviceInfo = (GenHospitalDevice) request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
	if (deviceInfo == null)
		throw new Exception("لم يتم تعيين جهاز لإتمام العملية المطلوبة عليه");

	String code = Common.getDisplayString(deviceInfo.getCode(), "");
	String serial = Common.getDisplayString(deviceInfo.getSerialNo(),"");
	String name = Common.getDisplayString(deviceInfo.getName(),"");
	String model = Common.getDisplayString(deviceInfo.getModel(), "");
//	String category = Common.getDisplayString(deviceInfo.getCategory().getName(), "");
	String location = Common.getDisplayString(deviceInfo.getLocation(langCode), "");
	
	GenEndUserMaintenanceRequest mReqInfo = (GenEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);

%>
</head>
<body>
	<table class="layout_grid">
		<tr>
			<td class="side_label_middle">كود الجهاز :</td>
			<td><label id="code"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle">الرقم التسلسلي :</td>
			<td><label id="serial"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle">اسم الجهاز :</td>
			<td><label id="name"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle">موديل الجهاز :</td>
			<td><label id="model"></label></td>
		</tr>
<%-- 		
        <tr>
			<td class="side_label_middle">فئة الجهاز :</td>
			<td><label id="category"></label> <input type="hidden"
				id="device_category" name="device_category"
				value="<%=deviceInfo.getCategory().getId()%>" /></td>
		</tr> 
--%>
		<tr>
			<td class="side_label_middle">الموقع :</td>
			<td><label id="location"></label></td>
		</tr>
		
		<%if(mReqInfo != null){%>
		<tr>
			<td class="side_label_middle">اسم المستخدم :</td>
			<td><label id="endUserName"></label></td>
		</tr>
		<tr>
			<td class="side_label_middle">تاريخ طلب الصيانة :</td>
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
<%-- 	setInnerHTML("category","<%=category%>"); --%>
	setInnerHTML("location","<%=location%>");
	
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
