<%@ page import="java.util.List,com.iassets.common.entity.CommonEmployee,com.iassets.common.util.*" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String langCode = WebUtil.getSessionLanguage(request);
    List<CommonEmployee> employees = (List<CommonEmployee>) request.getAttribute("activeEmployees");
    String currAppDir = WebUtil.getCurrentlyActiveAppDirectory(request);


%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <fmt:message key="jsp.UpdateEmployeeInfo.title" /></title>
</head>
<body>

 <form method="post" action="<%=currAppDir%>/UpdateEmployeeInfoProcess">
   <table  class="layout_grid">
     <tr>
       <td colspan="2"><h1 class="page_title">  <fmt:message key="jsp.UpdateEmployeeInfo.pagetitle" /> </h1></td> 
     </tr>
      <%
      	if(employees != null && ! employees.isEmpty()){
      %>
     <tr>
       <td class="side_label_middle">  <fmt:message key="jsp.UpdateEmployeeInfo.empId" />  : </td>
       <td>
     	 <select id="empId" name="empId" required>
           <%
           	for (CommonEmployee e :employees){
           %>
           <option value="<%=e.getId()%>"><%=CommonHtmlUtil.getEmployeeNameWithJobTitle(e , langCode)%></option>
           <%}%>
         </select>
       </td>
     </tr>
     <jsp:include page='<%="/"+ currAppDir + "/EmployeeInfoDisplay"%>' />
     <tr>
          <td>&nbsp;</td>
          <td><input type="submit" name="button" id="button" value=' <fmt:message key="jsp.UpdateEmployeeInfo.submitBtn" />'></td>
     </tr>
     <%}else{%>
        <tr>
          <td colspan="2"><h3 class='error_msg'> <fmt:message key="jsp.UpdateEmployeeInfo.error_msg" /></h3></td> 
        </tr>
      <%}%>
   </table>
</form>
<script type="text/javascript">
function _onchangeEmployee(){
	var myForm = $("form:first");
	if ($("#empId").prop("selectedIndex") != 0){
 		$("#content_container").load("<%=currAppDir%>/UpdateEmployeeInfoDisplay", {
			empId : $("#empId").val()
		}, function() {
			initPage()
		});
/* 		myForm..validate().cancelSubmit = true;
		myForm.prop("action","<%=currAppDir%>/UpdateEmployeeInfoDisplay");
		myForm.submit(); */
	}else{
		$("form")[0].reset();
		$("#empId").prop("selectedIndex",0);
	}
}

$(function(){
	<%
	String empId = request.getParameter("empId");
	if(empId == null)
		empId = "";
	%>
	setComboSelectedValue("empId","<%=empId%>");
	$("#empId").change(function(){_onchangeEmployee()});
});
</script>
</body>
</html>