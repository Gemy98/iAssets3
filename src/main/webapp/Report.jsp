<!DOCTYPE html>
<%@ page errorPage="/error/Error.jsp" contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<% 
   String dest = (String)request.getAttribute("dest");
   Boolean hideDefaultButtons = (Boolean) request.getAttribute("hideDefaultButtons");
   Boolean announcement = (Boolean) request.getAttribute("announcement");
%>

<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<title><%=Default.APP_NAME%> :: <fmt:message key="jsp.Report.title" /></title>
<base href='http://<%=WebUtil.getServerIpWithPortNo(request) + WebUtil.getAppContextPath(request) %>/' />

<%@include file="/Scripts_Styles.jsp" %>

<%if(report){%>
	<style type="text/css">
	  div#container{width: auto;min-width: 960px;max-width: 100%}
	  #content_container{padding:0}
	</style>
<%}%>
</head>

<body>
<%@include file="/NoScript.jsp" %>
<div id="container">
  <%@include file="/Header.jsp" %>
  <div id="content">
  <%if(announcement){%>
    <p>
		<%=WebUtil.getSessionMessage(request)%>
	</p>
   <%}else{%> 
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%--       <tr>
        <td >
         <%@include file="/SessionInfo.jsp" %>
        </td>
      </tr> --%>
      <tr> 
        <td valign="top" align="center" id="content_container">
        <%if(report != null && report){%>
	        <h1 class="page_title"><%=(String)request.getAttribute("reportTitle")%></h1>
	    <%}%>
	         <jsp:include page='<%=dest%>' flush="false" />         
        </td>
      </tr>
    </table>
	<%}%>
	
	<%if(!hideDefaultButtons){ %>
    <div align="center"><input class="close_btn" type='button' value='<fmt:message key="jsp.Report.closeWindow" />'  onclick="closeWindow()" ></div>
    <%}%>
  </div>
  <%@include file="/Footer.jsp" %>
</div>
</body>
</html>
