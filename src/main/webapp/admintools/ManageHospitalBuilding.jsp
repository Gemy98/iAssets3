<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*, com.iassets.common.util.*, com.iassets.common.entity.CommonHospitalLocation"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<% List<String> supportedLanguages  = (List<String>) request.getAttribute("supportedLanguages"); 
       String langCode = WebUtil.getSessionLanguage(request);	
	%>
	<form method="post" action="${processServlet }">
		<table class="layout_grid basic_page">
			<tbody>
				<tr>
					<td><h1 class="page_title"><fmt:message key="jsp.ManageHospitalBuilding.pagetitle" /></h1></td>
				</tr>
				<tr>
					<td>
						<table class="data_table" id="buildings_table">
							<tr>
								<th> <fmt:message key="jsp.ManageHospitalBuilding.buildings_table1" /></th>
								<% for(String lang : supportedLanguages) {                                   	
                                %>
                                    <th> <%=LocalizationManager.getLiteral("jsp.ManageHospitalBuilding.buildings_table2", langCode) + "_" + lang %></th>
                                <% } %>
								<th> <fmt:message key="jsp.ManageHospitalBuilding.buildings_table3" /></th>
							</tr>
							<%
							List<CommonHospitalLocation> locations = (List<CommonHospitalLocation>) request.getAttribute("locations");
							   for (int i = 0; i < locations.size(); i++) {
								   CommonHospitalLocation building = locations.get(i);
							 
							%>
								<tr>
									<td><%= i  +1  %></td>
									<% for(String lang : supportedLanguages) { %>
                                       <td><input
                                                  name='building_name_<%=lang + "_" + building.getId() %>'
                                                  value='<%= building.getLocalizedName(lang)%>'
                                                  type="text" size="20"
                                                  required="required" /></td>
                                    <%} %>
									<td><input name='delete_building_<%= building.getId() %>'
										type="checkbox" /></td>
								</tr>
							
							<%  } %>
							<c:if test="${ locations == null  || locations.size() == 0}">
								<tr class="_emptyRowMarker">
									<td colspan='<%= supportedLanguages.size()+2 %>'> <fmt:message key="jsp.ManageHospitalBuilding.buildings_table4" /> </td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-top: 5px"><input type="button"
						name="add_row" id="add_row" value='<fmt:message key="jsp.ManageHospitalBuilding.add_row" />'  onclick="addRow()"></td>
				</tr>
				<tr>
					<td style="padding-top: 20px" align="left"><input
						type="submit" name="button" id="button" value='<fmt:message key="jsp.ManageHospitalBuilding.submitBtn" />'></td>
				</tr>
		</table>
	</form>
	<script>
		function deleteRow(eventSource) {
			var emptyRowHtml = "<tr><td colspan='<%=supportedLanguages.size()+2 %>'><fmt:message key='jsp.js.ManageHospitalBuilding.emptyRowHtml' /></td></tr>";
			var minRowsCount = 2;
			deleteRowFromTable(eventSource, minRowsCount, emptyRowHtml);
		}

		function addRow() {
			var index = $('#buildings_table tr').length
					- $('#buildings_table tr._emptyRowMarker').length;
			
			var rowHtml = "'<tr><td>" + index; 
			         
		<% for(String lang : supportedLanguages) { %>
			  rowHtml += "<td><input type='text' name='building_name_<%=lang%>' required='required'/></td>";
		<% } %>
			rowHtml +=	"<td><input type='button' onclick='deleteRow(this)' value='" + "<fmt:message key='jsp.js.ManageHospitalBuilding.rowHtml' />" +"'></td></tr>";
			         
			var selector = "#buildings_table";
			addRowToTable(selector, rowHtml);
		}
	</script>
</body>
</html>
