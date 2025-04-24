<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.common.entity.*"%>
<%
	CommonEmployee emp = (CommonEmployee)request.getAttribute("employee");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<body>
    <tr>
      <td  class="side_label_middle"> <fmt:message key="jsp.EmployeeInfoForm.nameAr" /> : </td>
      <td><input type="text" name="nameAr" size="40" id="nameAr" required data-rule-arName="true" data-msg-arName='<fmt:message key="jsp.EmployeeInfoForm.nameAr.msg" />'></td>
    </tr>
    <tr>
      <td  class="side_label_middle"> <fmt:message key="jsp.EmployeeInfoForm.nameEn" />  : </td>
      <td><input type="text" name="nameEn" size="40" id="nameEn" required data-rule-enName="true" data-msg-enName='<fmt:message key="jsp.EmployeeInfoForm.nameEn.msg" />'></td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.EmployeeInfoForm.mobile" />: </td>
      <td><input type="text" id="mobile" name="mobile" maxlength="10" data-rule-minlength="10" data-msg-minlength='<fmt:message key="jsp.EmployeeInfoForm.mobile.msg1" />' data-rule-range ="0500000000,0599999999" data-msg-range='<fmt:message key="jsp.EmployeeInfoForm.mobile.msg2" />'></td>
    </tr>

    <%
    if (emp != null){
        String  nameAr = Common.getDisplayString(emp.getName(Enums.SUPPORTED_LANGUAGES.ARABIC.getLanguageCode()) , "");
        String  nameEn = Common.getDisplayString(emp.getName(Enums.SUPPORTED_LANGUAGES.ENGLISH.getLanguageCode()) , "");
        String  mobile = Common.getDisplayString(emp.getMobile() , ""); 
    %>
   <script>
   $(function(){
	   appendIdElement("employeeId","<%=emp.getId()%>");
	   setTextFieldValue("nameAr", "<%=nameAr%>");
	   setTextFieldValue("nameEn", "<%=nameEn%>");
	   setTextFieldValue("mobile", "<%=mobile%>");
   });
   </script>
   
   <%}%>
</body>
</html>