<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><%
	
    String siLangCode = WebUtil.getSessionLanguage(request);
    String purpose = request.getParameter("purpose");
	if (purpose != null) // save in session
		request.getSession().setAttribute("purpose", purpose);
	else
		// get from session
		purpose = (String) request.getSession().getAttribute("purpose");
	
	CommonUser currentUser = WebUtil.getSessionUser(request);	
	// String currentSiteName = WebUtil.getCurrentSiteName(request);
%>
<%
	//if (currentSiteName != null) {
%>
<%-- <label class="breadcrumbs" id="current_site_name">الموقع الحالي:&nbsp;<%=currentSiteName%></label> --%>
<%//}
if (currentUser != null) {
%>
<label class="breadcrumbs" id="current_user_name"> <fmt:message key="jsp.SessionInfo.hello" /> &nbsp;<%=currentUser.getName(siLangCode)%></label>
<%}if (purpose != null) {
%>
<label class="breadcrumbs" id="current_function_name">
... 
 <fmt:message key="jsp.SessionInfo.required" /> :&nbsp;<%=purpose%><%-- <%=new String(purpose.getBytes(), "utf-8")%> --%>
 
 <!-- new String(purpose.getBytes("iso-8859-1"), "utf-8") --> 
 
</label>
<%}%>