<%@ page import="java.util.*,com.iassets.common.entity.*,com.iassets.common.util.*" language="java" pageEncoding="UTF-8"%>
<%
String langCode = WebUtil.getSessionLanguage(request);
List<CommonSite> sites = (List<CommonSite>) request.getAttribute("sites");
List<CommonEmployee> dirSupervisors = (List<CommonEmployee>) request.getAttribute("dirSupervisors");
String currAppDir = WebUtil.getCurrentlyActiveAppDirectory(request);
Enums.USER_ALLOWED_APP_TYPE currentlyActiveAppType = WebUtil.getCurrentlyActiveApp(request);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post" action="<%=currAppDir%>/AssignDirectorateSupervisorToSitesProcess">
   <%=WebUtil.getSessionMessage(request)%>
   <table class="layout_grid">
     <tr>
       <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.AssignDirectorateSupervisorToSites.pagetitle" /></h1></td> 
     </tr>
      <%
      	if(dirSupervisors != null && ! dirSupervisors.isEmpty()){
      %>
     <tr>
       <td colspan="2" align="center">
         <table id="site_list" class="data_table">
         	<tr>
         	  <th><fmt:message key="jsp.AssignDirectorateSupervisorToSites.site_list1" /></th>
         	  <th><fmt:message key="jsp.AssignDirectorateSupervisorToSites.site_list2" /></th>
         	</tr>
         <%
         	for(CommonSite s: sites){
         		
               Integer siteId = s.getId();
               
               List<CommonEmployee> siteDirSupervisors = null;
               if(currentlyActiveAppType == Enums.USER_ALLOWED_APP_TYPE.BIOMEDICAL_MAINTENANCE_APP)
            	   siteDirSupervisors = s.getBioDirectorateSupervisors();
               else if(currentlyActiveAppType == Enums.USER_ALLOWED_APP_TYPE.GENERAL_MAINTENANCE_APP)
            	   siteDirSupervisors = s.getGenDirectorateSupervisors();
               
               List<Integer> siteDirSupervisorsIds = new ArrayList<Integer>();
               for(CommonEmployee e: siteDirSupervisors)
            	   siteDirSupervisorsIds.add(e.getId());
                           
         %>
            <tr>
              <td align="center"><%=s.getLocalizedName()%><input type="hidden" name="siteId" value="<%=siteId%>" /> </td>
              <td> 
		           <%for (CommonEmployee e :dirSupervisors){%>
		                <div><input type="checkbox" name="empId_<%=siteId%>" value="<%=e.getId()%>" <%=(siteDirSupervisorsIds.contains(e.getId()))?"checked='checked'":""%>/>&nbsp;<%=e.getName(langCode)%></div>
		           <%}%>
              </td>
            </tr>
         <%}%>
         </table>
       </td>
     </tr>
     <tr>
        <td colspan="2" align="center"><input type="submit" name="button" id="button" value="<fmt:message key='jsp.AssignDirectorateSupervisorToSites.submitBtn' /> "></td>
     </tr>
     <%}else{%>
        <tr>
          <td colspan="2"><h3 class='error_msg'><fmt:message key="jsp.AssignDirectorateSupervisorToSites.error_msg" /></h3></td> 
        </tr>
     <%}%>
   </table>
</form>
</body>
</html>