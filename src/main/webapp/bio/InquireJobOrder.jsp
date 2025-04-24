<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<% 
   String reportFlag = request.getParameter("report");   
   String target = (reportFlag != null)? "target='_blank' rel='opener'" : ""; 
   String jobOrderNo = request.getParameter("jobOrderNo");
%>
<body>
<form method="post" action='bio/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onSubmit="return _onSubmitJobOrderInquiryForm();">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.InquireJobOrder.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.InquireJobOrder.jobOrderNo" />: </td>
      <td><input type="text" name="jobOrderNo" id="jobOrderNo"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" name="button" id="button" value='<fmt:message key="jsp.InquireJobOrder.submitBtn" />'></td>
    </tr>
  </table>
</form>
<script> 
      setTextFieldValue("jobOrderNo",'<%=Common.getDisplayString( jobOrderNo, "")%>');
</script>
</body>
</html>
