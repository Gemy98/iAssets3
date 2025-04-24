<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"	import="com.iassets.common.util.*"%>
<html dir="rtl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<% 
   String reportFlag = request.getParameter("report");
   String target = (reportFlag != null)? "target='_blank'" : ""; 
   String code = request.getParameter("code");
%>
<body>
<form method="post" action='gen/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onsubmit="return _onSubmitSPCategorySearchForm();">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title">شاشة البحث عن صنف في المستودع</h1></td>
    </tr>
    <tr>
      <td class="side_label_middle">الكود : </td>
      <td><input type="text" name="code" id="code"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" name="button" id="button" value="ابدأ البحث"></td>
    </tr>
  </table>
</form>
<script>  
    setTextFieldValue("code",'<%=Common.getDisplayString(code, "")%>');
</script>
</body>
</html>