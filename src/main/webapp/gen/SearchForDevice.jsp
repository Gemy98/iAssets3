<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>البحث عن جهاز</title>
</head>
<body>
<%  
    String reportFlag = request.getParameter("report");
    String target = (reportFlag != null)? "target='_blank'" : ""; 
	String code = request.getParameter("deviceCode");
	String sn = request.getParameter("deviceSerial");
%>
<form method="post" action='gen/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onsubmit="return _onSubmitDeviceSearchForm();">
      <table class="layout_grid">
        <tr>
          <td colspan="2"><h1 class="page_title">شاشة البحث عن جهاز</h1></td>
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
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
      </table> 
     <script>
          setTextFieldValue("deviceCode",'<%=Common.getDisplayString( code, "")%>');
	  	  setTextFieldValue("deviceSerial",'<%=Common.getDisplayString( sn, "")%>');
     </script>
</form>
</body>
</html>
