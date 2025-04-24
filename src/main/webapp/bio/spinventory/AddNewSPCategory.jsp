<%@page import="com.iassets.common.util.WebUtil"%>
<%@ page import="com.iassets.biomedical.util.HtmlUtil" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String langCode = WebUtil.getSessionLanguage(request); %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post"  action="bio/AddNewSPCategoryProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.AddNewSPCategory.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.AddNewSPCategory.code" /> : </td>
      <td><input type="text" name="code" id="code" size="10" required data-msg-remote='<fmt:message key="jsp.AddNewSPCategory.code.msg" />'></td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.AddNewSPCategory.name" /> : </td>
      <td><input name="name" type="text" id="name" size="40" required></td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.AddNewSPCategory.threshold" />: </td>
      <td><input name="threshold" type="text" id="threshold" size="10" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle"> <fmt:message key="jsp.AddNewSPCategory.quantity" /> : </td>
      <td><input name="quantity" type="text" id="quantity" size="10" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.AddNewSPCategory.price" /> : </td>
      <td><input name="price" type="text" id="price" size="10" required data-rule-number="true"><strong> <fmt:message key="jsp.AddNewSPCategory.currency" /></strong></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" name="button" id="button" value='<fmt:message key="jsp.AddNewSPCategory.submitBtn" />'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
  <script>
  $(function(){
	  addValidationRuleForSpCatDuplication();
  });
  </script>
</form>
</body>
</html>