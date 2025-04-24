<%@ page contentType="text/html; charset=UTF-8" import="com.iassets.common.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="DeviceLib" tagdir="/WEB-INF/tags/bio/Device"%>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="bio/ViewDevicesByWarrantyExpire" target="_blank" rel='opener'>
		<table class="layout_grid">
		    <tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.DeviceByWarrantyExpireFilter.pFrom" />:</td>
				<td><input type="text" name="pFrom" id="pFrom" required class="caldr"></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.DeviceByWarrantyExpireFilter.pTo" /> :</td>
				<td><input type="text" name="pTo" id="pTo" class="caldr"></td>
			</tr>

		   <DeviceLib:CommonDeviceFilter catList="${ catList }"
			classificationList="${ classificationList }"
			depList="${ depList }" locationList="${ locationList }"
			showAgentAndSubcontractor="true"
			showCategoryAndClassification="true"
			showDepartmentAndLocation="true" showDeviceName="true" />

			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value='<fmt:message key="jsp.DeviceByWarrantyExpireFilter.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>