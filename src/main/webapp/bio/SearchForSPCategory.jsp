<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	import="com.iassets.common.util.*"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<% 
   String reportFlag = request.getParameter("report");
   String target = (reportFlag != null)? "target='_blank' rel='opener'" : ""; 
   String code = request.getParameter("code");
%>
<body>
<form method="post" action='bio/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onsubmit="return _onSubmitSPCategorySearchForm();">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.SearchForSPCategory.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.SearchForSPCategory.code" /> : </td>
      <td><input type="text" name="code" id="code"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" name="button" id="button" value='<fmt:message key="jsp.SearchForSPCategory.submitBtn" />'></td>
    </tr>
  </table>
</form>
<script>  
    setTextFieldValue("code",'<%=Common.getDisplayString(code, "")%>');
</script>
</body>
</html>