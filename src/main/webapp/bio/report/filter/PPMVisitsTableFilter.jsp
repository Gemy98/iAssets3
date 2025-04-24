<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="DeviceLib" tagdir="/WEB-INF/tags/bio/Device"%>
<%@ taglib prefix="UI" tagdir="/WEB-INF/tags/UI"%>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="bio/ViewPPMTable" target="_blank" rel='opener'>
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>        
	       
	       <tr>
				<td class="side_label_middle"><fmt:message key="jsp.JobOrderByOpendAndLateFilter.warranty" /> :</td>
				<td valign="top">
					<span>
						<input name="warranty" style="margin-left: 2px; margin-right: 2px;" type="checkbox" value="1"/> <%=LocalizationManager.getLiteral("jsp.JobOrderByOpendAndLateFilter.withinWarranty" ,WebUtil.getSessionLanguage(request) )%>
						<input name="warranty" style="margin-left: 2px; margin-right: 2px;" type="checkbox" value="0"/> <%=LocalizationManager.getLiteral("jsp.JobOrderByOpendAndLateFilter.outsideWarranty" ,WebUtil.getSessionLanguage(request) )%>
					</span>
				</td>
			</tr>
			
      
<%-- 			<UI:MonthYearDatePicker --%>
<%-- 				literalKey="jsp.PPMVisitsTableFilter.monthYear" --%>
<%-- 				name="monthYear" id="monthYear" --%>
<%-- 				minDate="${contractStartDate }" --%>
<%-- 				maxDate="${contractEndDate }" /> --%>
		
		   <DeviceLib:CommonDeviceFilter catList="${ catList }"
				classificationList="${ classificationList }"
				showCategoryAndClassification="true"/>
			
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value='<fmt:message key="jsp.PPMVisitsTableFilter.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>