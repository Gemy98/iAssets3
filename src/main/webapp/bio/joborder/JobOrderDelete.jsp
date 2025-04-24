<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head> 
<body>
<form method="post" action="bio/JobOrderDeleteProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.JobOrderDelete.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %></td>
    </tr>
    <tr>
      <td  class="side_label_top"><fmt:message key="jsp.JobOrderDelete.actualCancelDate" /> : </td>
      <td><input type="text" name="actualCancelDate" id="actualCancelDate" class="caldr" required></td>
    </tr>
    <tr>
      <td  class="side_label_top"> <fmt:message key="jsp.JobOrderDelete.cancelReason" />  : </td>
      <td><textarea name="cancelReason" id="cancelReason" required></textarea></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="<fmt:message key="jsp.JobOrderDelete.submitButton" />"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
  <script>
	$(function(){
		 $("#actualCancelDate").datepicker( "setDate", new Date());
	     $("#actualCancelDate").rules( "add", {
	 		dateGreaterThan: ['#joborder_date'],
			messages: {	dateGreaterThan: ""}
		 });
	});
	</script>
</body>
</html>
