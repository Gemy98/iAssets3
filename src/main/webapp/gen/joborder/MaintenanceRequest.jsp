<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.general.entity.*,com.iassets.general.util.HtmlUtil,com.iassets.common.util.Default,com.iassets.common.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form method="post" action="gen/MaintenanceRequestProcess">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">شاشة  طلب صيانة </h1></td>
			</tr>	
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%></td>
			</tr>
			
			<!------------------- Awad  07-09-2016 ------------------------------>
<% 
Boolean uncodedDevice = (Boolean) request.getAttribute("uncodedDevice");
if(uncodedDevice != null && uncodedDevice){
%>

      <tr>
        <td class="side_label_middle">اسم الجهاز :</td>
        <td colspan="2"><input type="text" size="40" id="uncodedDeviceName" name="uncodedDeviceName" required></td>
      </tr>

 <%      
      List<CommonHospitalLocation> hospLocationList = (List<CommonHospitalLocation>)request.getAttribute("hospLocationList");
      if (hospLocationList != null && !hospLocationList.isEmpty()){

      %>
       <tr>
        <td class="side_label_middle">الموقع :</td>
        <td colspan="2">
          <select id="hospLocation" name="hospLocation" onchange="_onChangeHospitalLocation(this);_onChangeDeviceLock(this)">
            <%
            	for (CommonHospitalLocation obj : hospLocationList ){
            %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <% }%>
      <%
      List<CommonHospitalDepartment> depList = (List<CommonHospitalDepartment>)request.getAttribute("depList");
      if (depList != null && !depList.isEmpty()){
      %>
      <tr>
        <td class="side_label_middle">القسم :</td>
        <td colspan="2">
          <select id="dep" name="dep">
            <%
            	for (CommonHospitalDepartment dep :depList ){
            %>
            <option title="<%=dep.getLocation().getId()%>"  value="<%=dep.getId()%>"><%=dep.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <% }%>
      
      <tr>
        <td class="side_label_middle">الغرفة :</td>
        <td colspan="2"><input type="text"  id="hospitalRoom" name="hospitalRoom"></td>
      </tr>
      
      <tr>
        <td class="side_label_top">الوصف الدقيق للمكان :</td>
        <td colspan="2"><textarea id="hospitalLocationDescription" name="hospitalLocationDescription"></textarea></td>
      </tr>
      <tr>
 

<%}%>
<!--------------------------end  Awad  07-09-2016 ------------------->
			
			
			
			<tr>
				<td class="side_label_middle">تاريخ العطل :</td>
				<td><input type="text" name="damageDate" id="damageDate" class="caldr" required></td>
			</tr>
			<tr>
				<td class="side_label_top">وصف العطل / العمل المطلوب :</td>
				<td><textarea name="damageDescription" id="damageDescription" required></textarea></td>
			</tr>
			<tr>
				<td class="side_label_middle">اسم المستخدم:</td>
				<td><input type="text" size="40" name="endUserName" id="endUserName" required></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value=" ارسال طلب الصيانة "><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
			</tr>
		</table>
	</form>
</body>
</html>
