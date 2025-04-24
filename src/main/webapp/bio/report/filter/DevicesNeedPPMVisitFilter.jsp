<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.Default,com.iassets.biomedical.util.HtmlUtil,com.iassets.common.entity.*,java.util.List"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="DeviceLib" tagdir="/WEB-INF/tags/bio/Device"%>
<%@ taglib prefix="UI" tagdir="/WEB-INF/tags/UI"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="bio/ViewDevicesNeedPPMVisit" target="_blank" rel='opener'>
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
            
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.DevicesNeedPPMVisitFilter.month" /> :</td>
				<td><%=HtmlUtil.getMonthesAsHtmlSelect("month", "auto_off" , languageCode)%></td>
			</tr>
	
		   <DeviceLib:CommonDeviceFilter catList="${ catList }"
				classificationList="${ classificationList }"
				depList="${ depList }" locationList="${ locationList }"
				showAgentAndSubcontractor="true"
				showCategoryAndClassification="true"
				showDepartmentAndLocation="true" 
				showDeviceName="true" showManufacturerNameAndModel="true" />
				
			<tr>
		    	<td class="side_label_middle"> <fmt:message key="jsp.DevicesNeedPPMVisitFilter.deviceStatus" />:</td>
		   		<td> 
		   			  <select id="deviceStatus" name="deviceStatus" class="auto_add_all auto_off">
			        	    <option value="<%= Enums.DEVICE_STATUS.WORK_PROPERLY.getStatus() %>">
								<%=LocalizationManager.getLiteral("jsp.DevicesNeedPPMVisitFilter.workProperly" ,WebUtil.getSessionLanguage(request) )%>
							</option>
			        	    <option value="<%= Enums.DEVICE_STATUS.UNDER_MAINTENANCE.getStatus() %>">
								<%=LocalizationManager.getLiteral("jsp.DevicesNeedPPMVisitFilter.underMaintenance" ,WebUtil.getSessionLanguage(request) )%>
							</option>
			          </select>
			    </td>
		  	</tr>
	  	
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value='<fmt:message key="jsp.DevicesNeedPPMVisitFilter.submitBtn" />'></td>
			</tr>
		</table>
	</form>
</body>
</html>
