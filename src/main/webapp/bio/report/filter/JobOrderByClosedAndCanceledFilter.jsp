<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"	import="com.iassets.common.util.WebUtil, com.iassets.common.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="DeviceLib" tagdir="/WEB-INF/tags/bio/Device"%>
<%@ taglib prefix="UI" tagdir="/WEB-INF/tags/UI"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<% String langCode = WebUtil.getSessionLanguage(request); %>
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action='bio/<%=WebUtil.getSearchProcessURL(request)%>' target="_blank" rel='opener'>
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
				
			<tr>
				<td class="side_label_middle"><fmt:message key="${sideLabelKey }" />  :</td>
				<td> 
				
				<select id="employeeId" name="employeeId" class="auto_add_all auto_off">
						<%
							List<CommonEmployee> employeesList = (List<CommonEmployee>) request.getAttribute("employeesList");
										 if (employeesList != null)
											for (CommonEmployee obj : employeesList) {
						%>
						<option value="<%=obj.getId()%>"><%=obj.getName(WebUtil.getSessionLanguage(request))%></option>
						<%}%>
				</select>
				</td>
			</tr>

			<UI:MonthYearDatePicker
				literalKey="jsp.JobOrderByClosedAndCanceledFilter.monthYear"
				name="monthYear" id="monthYear"
				minDate="${contractStartDate }"
				maxDate="${contractEndDate }" />
				
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value='<fmt:message key="jsp.JobOrderByClosedAndCanceledFilter.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>