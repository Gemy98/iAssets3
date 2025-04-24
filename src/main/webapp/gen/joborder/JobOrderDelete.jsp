<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head> 
<body>
<form method="post" action="gen/JobOrderDeleteProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title">شاشة حذف أمر عمل</h1></td>
    </tr>
    <tr>
      <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %></td>
    </tr>
    <tr>
      <td  class="side_label_top">تاريخ الحذف : </td>
      <td><input type="text" name="actualCancelDate" id="actualCancelDate" class="caldr" required></td>
    </tr>
    <tr>
      <td  class="side_label_top">اسباب الحذف : </td>
      <td><textarea name="cancelReason" id="cancelReason" required></textarea></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="احذف أمر العمل"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
  <script>
	$(function(){
		 $("#actualCancelDate").datepicker( "setDate", new Date());
	     $("#actualCancelDate").rules( "add", {
	 		dateGreaterThan: ['#joborder_date'],
			messages: {	dateGreaterThan: "يجب أن يكون تاريخ الحذف بعد تاريخ فتح أمر العمل"}
		 });
	});
	</script>
</body>
</html>
