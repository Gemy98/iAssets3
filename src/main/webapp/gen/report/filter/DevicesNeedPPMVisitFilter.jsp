<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.Default,com.iassets.general.util.HtmlUtil,com.iassets.common.entity.*,java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="gen/ViewDevicesNeedPPMVisit" target="_blank">
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
			<tr>
				<td class="side_label_middle">خلال شهر :</td>
				<td><%=HtmlUtil.getMonthesAsHtmlSelect("month", "auto_off" , languageCode)%></td>
			</tr>
<%-- 			
            <tr>
				<td class="side_label_middle">الفئة :</td>
				<td><select id="category" name="category" class="auto_add_all auto_off">
						<%
							List<GenLookupDeviceCategory> catList = (List<GenLookupDeviceCategory>) request.getAttribute("catList");
										 if (catList != null)
											for (GenLookupDeviceCategory obj : catList) {
						%>
						<option value="<%=obj.getId()%>"><%=obj.getName()%></option>
						<%
							}
						%>
				</select></td>
			</tr> 
--%>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="عرض"></td>
			</tr>
		</table>
	</form>
</body>
</html>
