<!DOCTYPE html>
<%@ page import="java.util.List,com.iassets.common.entity.CommonUser,com.iassets.common.util.*" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<%
String langCode = WebUtil.getSessionLanguage(request);
List<CommonUser> users = (List<CommonUser>) request.getAttribute("users");
String currAppDir = WebUtil.getCurrentlyActiveAppDirectory(request);
%>  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title> <fmt:message key="jsp.ResetUserPassword.title" /> </title>
    </head>
    <body>
    <form method="post" action="<%=currAppDir%>/ResetUserPasswordProcess">
      <table  class="layout_grid">
        <tr>
          <td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.ResetUserPassword.pagetitle" />  </h1></td> 
        </tr>
        <%
        	if(users != null && ! users.isEmpty()){
        %>
        <tr>
          <td class="side_label_middle"><fmt:message key="jsp.ResetUserPassword.userId" />  : </td>
          <td> 
	          <select id="userId" name="userId" required>
	            <%
	            	for (CommonUser u :users){
	            %>
	            <option value="<%=u.getId()%>"><%=CommonHtmlUtil.getEmployeeNameWithJobTitle(u.getEmployee() , langCode)%></option>
	            <%}%>
	          </select>
          </td>
        </tr>
        <tr>
          <td class="side_label_middle"> <fmt:message key="jsp.ResetUserPassword.newPass" />   : </td>
          <td><input type="password" name="newPass" id="newPass" required></td>
        </tr>
        <tr>
          <td class="side_label_middle">  <fmt:message key="jsp.ResetUserPassword.confNewPass" /> : </td>
          <td><input type="password" name="confNewPass" id="confNewPass" required data-rule-equalTo="#newPass" data-msg-equalTo=' <fmt:message key="jsp.ResetUserPassword.confNewPass.msg" />'></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td><input type="submit" name="button" id="button" value='<fmt:message key="jsp.ResetUserPassword.submitBtn" />'></td>
        </tr>
        <%}else{%>
        <tr>
          <td colspan="2"><h3 class='error_msg'> <fmt:message key="jsp.ResetUserPassword.error_msg" /></h3></td> 
        </tr>
        <%}%>
      </table>
    </form>
</body>
</html>