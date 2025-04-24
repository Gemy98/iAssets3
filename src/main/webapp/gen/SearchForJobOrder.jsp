<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<% 
   String reportFlag = request.getParameter("report");   
   String target = (reportFlag != null)? "target='_blank'" : ""; 
   String jobOrderNo = request.getParameter("jobOrderNo");
   String sn = request.getParameter("deviceSerial");
   String code = request.getParameter("deviceCode");
%>
<body>
<form method="post" action='gen/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onSubmit="return _onSubmitJobOrderSearchForm();">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title">شاشة البحث عن أمر عمل</h1></td>
    </tr>
    <tr>
      <td class="side_label_middle">رقم امر العمل : </td>
      <td><input type="text" name="jobOrderNo" id="jobOrderNo"></td>
    </tr>
    <tr>
      <td class="side_label_middle">كود الجهاز : </td>
      <td><input type="text" name="deviceCode" id="deviceCode"></td>
    </tr>
    <tr>
      <td class="side_label_middle">الرقم التسلسلي : </td>
      <td><input type="text" name="deviceSerial" id="deviceSerial"></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="submit" name="button" id="button" value="ابدأ البحث"></td>
    </tr>
  </table>
</form>
<script> 
      setTextFieldValue("jobOrderNo",'<%=Common.getDisplayString( jobOrderNo, "")%>');
      setTextFieldValue("deviceCode",'<%=Common.getDisplayString( code, "")%>');
      setTextFieldValue("deviceSerial",'<%=Common.getDisplayString( sn, "")%>');
</script>
</body>
</html>
