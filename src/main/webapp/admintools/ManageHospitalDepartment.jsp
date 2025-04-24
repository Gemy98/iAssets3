<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*,com.iassets.common.util.*, com.iassets.common.entity.CommonHospitalDepartment,com.iassets.common.entity.CommonHospitalLocation"%>
<%
 String langCode = WebUtil.getSessionLanguage(request);
 List<String> supportedLanguages  = (List<String>) request.getAttribute("supportedLanguages"); 
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
     <form method="post" action="${processServlet}">
          <table class="layout_grid basic_page">
               <tbody>
                    <tr>
                         <td><h1 class="page_title"> <fmt:message key="jsp.ManageHospitalDepartment.pagetitle" /></h1></td>
                    </tr>
                    <tr>
                         <td>
                              <table class="data_table" id="departments_table">
                                   <tr>
                                        <th> <fmt:message key="jsp.ManageHospitalDepartment.departments_table1" /></th>
                                        <% for(String lang : supportedLanguages) {                                   	
                                        %>
                                        	<th> <%=LocalizationManager.getLiteral("jsp.ManageHospitalDepartment.departments_table2", langCode) + "_" + lang %></th>
                                        <% } %>
                                        <th> <fmt:message key="jsp.ManageHospitalDepartment.departments_table3" /></th>
                                        <th> <fmt:message key="jsp.ManageHospitalDepartment.departments_table4" /></th>
                                   </tr>
							<%
							List<CommonHospitalDepartment> departments = (List<CommonHospitalDepartment>) request.getAttribute("departments");
							
							for (int i = 0; i < departments.size(); i++) {
								   CommonHospitalDepartment department = departments.get(i);
							 
							%>
                                        <tr>
                                             <td><%= i+1  %></td>
                                           <% for(String lang : supportedLanguages) { %>
                                             <td><input
                                                  name='department_name_<%=lang + "_" + department.getId() %>'
                                                  value="<%=department.getLocalizedName(lang)%>"
                                                  type="text" size="20"
                                                  required="required" /></td>
                                            <%} %>
                                            
                                             <td><select
                                                  name='department_building_<%= department.getId() %>'
                                                  required="required">
                                                  	<%
													List<CommonHospitalLocation> locations = (List<CommonHospitalLocation>) request.getAttribute("locations");
													   for (CommonHospitalLocation location : locations) { 
													%> 
	 													<option  value="<%=location.getId()%>"
	                                                    	<%= department.getLocation().getId() == location.getId() ? "selected=\"selected\"" : ""%> 
	                                                    >   <%= location.getLocalizedName(WebUtil.getSessionLanguage(request))%>
	                                                    </option>
                                                 <% } %>
                                        </select></td>
                                             <td><input
                                                  name='delete_department_${department.id}'
                                                  type="checkbox" /></td>
                                        </tr>
                                   <% } %>
                                   <c:if
                                        test="${ departments == null  || departments.size() == 0}">
                                        <tr class="_emptyRowMarker">
                                             <td colspan='<%= supportedLanguages.size()+3 %>'> <fmt:message key="jsp.ManageHospitalDepartment.departments_table5" /> </td>
                                        </tr>
                                   </c:if>
                              </table>
                         </td>
                    </tr>
                    <tr>
                         <td style="padding-top: 5px"><input
                              type="button" name="add_row" id="add_row"
                              value='<fmt:message key="jsp.ManageHospitalDepartment.add_row" />' onclick="addRow()"></td>
                    </tr>
                    <tr>
                         <td style="padding-top: 20px"
                              align="left"><input type="submit"
                              name="button" id="button"
                              value='<fmt:message key="jsp.ManageHospitalDepartment.submitBtn" />'></td>
                    </tr>
          </table>
     </form>
     <script>
      function deleteRow(eventSource) {
       var temp = '<%=LocalizationManager.getLiteral("jsp.js.ManageHospitalDepartment.emptyRowHtml", langCode)%>';
       var temp2 = '<%=supportedLanguages.size()+3 %>';
       var emptyRowHtml = "<tr><td colspan='"+temp2+"'>" +temp+ "</td></tr>";
       var minRowsCount = 2;
       deleteRowFromTable(eventSource, minRowsCount, emptyRowHtml);
      }

      function addRow() {
       var index = $('#departments_table tr').length - $('#departments_table tr._emptyRowMarker').length;
       var rowHtml = "<tr><td>" + index + "</td>";
         <% for(String lang : supportedLanguages) { %>
         	rowHtml += "<td><input type='text' name='department_name_<%=lang%>' required='required'/></td>";
         <% } %>
         rowHtml += "<td><select name='department_building' required='required' aria-required='true'>"
         rowHtml += "<option value><%=LocalizationManager.getLiteral("jsp.js.ManageHospitalDepartment.plzSelect", langCode)%></option>";
         
         <%
			List<CommonHospitalLocation> locations = (List<CommonHospitalLocation>) request.getAttribute("locations");
			   for (CommonHospitalLocation location : locations ) { 
			 
			%>
       		rowHtml += "<option value='<%= location.getId() %>'><%= location.getLocalizedName(WebUtil.getSessionLanguage(request))%></option>"
       <% } %>
       rowHtml += "</select><span class='requiredField'>*</span></td>"
         + "<td><input type='button' onclick='deleteRow(this)' value='<%= LocalizationManager.getLiteral("jsp.js.ManageHospitalDepartment.delete", langCode) %>'></td></tr>";
       var selector = "#departments_table";
       addRowToTable(selector, rowHtml);
      }
     </script>
</body>
</html>
