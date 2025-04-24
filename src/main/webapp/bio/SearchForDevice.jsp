<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<%  
    String reportFlag = request.getParameter("report");
    String target = (reportFlag != null)? "target='_blank' rel='opener'" : ""; 
	String code = request.getParameter("deviceCode");
	String sn = request.getParameter("deviceSerial");
%>
<form method="post" action='bio/<%=WebUtil.getSearchProcessURL(request)%>' <%=target%> onsubmit="return _onSubmitDeviceSearchForm();">
      <table class="layout_grid">
        <tr>
          <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.SearchForDevice.pagetitle" /></h1></td>
        </tr>
        <tr>
          <td class="side_label_middle">  <fmt:message key="jsp.SearchForDevice.deviceCode" /> : </td>
          <td><input type="text" name="deviceCode" id="deviceCode"></td>
        </tr>
        <tr>
          <td class="side_label_middle">  <fmt:message key="jsp.SearchForDevice.deviceSerial" /> : </td>
          <td><input type="text" name="deviceSerial" id="deviceSerial"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td><input type="submit" name="button" id="button" value= ' <fmt:message key="jsp.SearchForDevice.submitBtn" />'></td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
      </table> 
     <script>
          setTextFieldValue("deviceCode",'<%=Common.getDisplayString( code, "")%>');
	  	  setTextFieldValue("deviceSerial",'<%=Common.getDisplayString( sn, "")%>');
// 	  	  setTextFieldValue("deviceCode",'<fmt:message key="searchfordevice.pagetitle" />');
     </script>
</form>
</body>
</html>
