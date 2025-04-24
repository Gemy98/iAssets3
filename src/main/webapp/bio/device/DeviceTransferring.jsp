<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.biomedical.util.HtmlUtil,com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form action="bio/DeviceTransferringProcess" method="post" enctype="multipart/form-data">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title"><fmt:message key="jsp.DeviceTransferring.pagetitle" /></h1></td>
			</tr>
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%>
				</td>
			</tr>

			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.DeviceTransferring.transferDate" /> :</td>
				<td><input type="text" name="transferDate" id="transferDate"
					class="caldr" required></td>
			</tr>

			<tr>
				<td class="side_label_top"><fmt:message key="jsp.DeviceTransferring.transferReason" /> :</td>
				<td><textarea name="transferReason" id="transferReason"
						required></textarea></td>
			</tr>
			<tr>
				<td class="side_label_top"> <fmt:message key="jsp.DeviceTransferring.transferReportUrl" /> :</td>
				<td><input type="file" name="transferReportUrl"
					id="transferReportUrl" multiple class="requiredFile"></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.DeviceTransferring.transferDestination" /> :</td>
				<td><input type="text" name="transferDestination"
					id="transferDestination" size="40" required></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="button" id="button" value='<fmt:message key="jsp.DeviceTransferring.submitbutton" />'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
			</tr>
		</table>
	</form>
  <script>
	$(function(){ 
		 $("#button").click(function(){
			 if($("form").valid())
				 return confirm('<fmt:message key="jsp.js.DeviceTransferring.confirm" />');
	     });
	});
  </script>
</body>
</html>