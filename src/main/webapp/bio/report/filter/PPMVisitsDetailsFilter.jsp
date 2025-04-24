<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"	import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,com.iassets.common.entity.*,java.util.List"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String langCode = WebUtil.getSessionLanguage(request); %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="bio/ViewPPMVisitsDetails" target="_blank" rel='opener'>
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
                <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
            <tr>
				<td class="side_label_middle"><fmt:message key="jsp.PPMVisitsDetailsFilter.category" /> :</td>
				<td><select id="category" name="category" class="auto_add_all auto_off">
						<%
							List<BioLookupDeviceCategory> catList = (List<BioLookupDeviceCategory>) request.getAttribute("catList");
										 if (catList != null)
											for (BioLookupDeviceCategory obj : catList) {
						%>
						<option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
						<%}%>
				</select></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.PPMVisitsDetailsFilter.month" /> :</td>
				<td><%=HtmlUtil.getMonthesAsHtmlSelect("month","auto_add_all auto_off",langCode)%></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.PPMVisitsDetailsFilter.year" /> :</td>
				<td><select name="year" id="year" class="auto_add_all auto_off"></select> 
				<script>
					var currentYear = new Date().getFullYear();
					for (var i = 2014; i <= currentYear + 1; i++)
						$("#year").append(new Option(i, i));
				</script></td>
			</tr>
			<tr>
				<td>&nbsp;</td><td><input type="submit"	value='<fmt:message key="jsp.PPMVisitsDetailsFilter.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>