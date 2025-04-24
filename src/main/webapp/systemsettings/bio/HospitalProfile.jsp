<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="java.util.List,com.iassets.common.util.*,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title> <fmt:message key="jsp.HospitalProfile.pagetitle" /></title>
<%
	String langCode = WebUtil.getSessionLanguage(request);

	CommonUser user= (CommonUser)request.getAttribute("userProfile");	
    CommonSite site = user.getCurrentSite(); 
    // CommonHospital location = (CommonHospital)request.getAttribute("location"); 
   
   	String siteName = WebUtil.getCurrentSiteName(request, langCode);

	Integer hospNo = site.getAffiliatedHospitalsNo();
	String affHospitalsNo = (hospNo != null && hospNo > 0) ? "" + hospNo : null;
	String affhealthCentersNo = "" + site.getAffiliatedHealthCentersNo();
	String companyName = site.getBioOperatingCompany().getLocalizedName(langCode);
	String siteManager = site.getBioSiteManagerName(langCode);
	List<CommonEmployee> supervisors = site.getBioDepartmentSupervisors();
	String headName = site.getBioDepartmentHeadName(langCode);
	String directorName = site.getHospitalDirectorName(langCode);
	List<CommonEmployee> directorateSupervisors = site.getBioDirectorateSupervisors();
	Integer bNo = site.getBedNo();
	String bedNo = (bNo != null && bNo > 0) ? "" + bNo : null;
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.HospitalProfile.pagetitle" /></h1></td>
  </tr>
  <tr>
    <td class="side_label_middle"> <fmt:message key="jsp.HospitalProfile.siteName" />: </td>
    <td><%=Common.getDisplayString(siteName, "", true)%></td>
  </tr>
  <%
  	if(site.getContainsSeveralLocations()){
  %>
  <tr>
    <td  class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.affHospitalsNo" />  : </td>
    <td><%=Common.getDisplayString(affHospitalsNo,"<fmt:message key='jsp.HospitalProfile.notfound' />", true)%></td>
  </tr>
  <tr>
    <td  class="side_label_middle"> <fmt:message key="jsp.HospitalProfile.affhealthCentersNo" /> : </td>
    <td><%=Common.getDisplayString(affhealthCentersNo, "<fmt:message key='jsp.HospitalProfile.notfound' />", true)%></td>
  </tr>
  <%
  	}
  %>
  <tr>
    <td class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.companyName" /> : </td>
    <td><%=Common.getDisplayString(companyName, "", true)%></td>
  </tr>
  
  <tr>
    <td  class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.siteManager" />  : </td>
    <td><%=Common.getDisplayString(siteManager, "", true)%></td>
  </tr>
  <%
  	if(supervisors != null && ! supervisors.isEmpty()){
  %>
  <tr>
    <td class="side_label_top">  <fmt:message key="jsp.HospitalProfile.supervisors" /> : </td>
    <%
    	if(supervisors.size() == 1){
    %>
	    <td><%=Common.getDisplayString(supervisors.get(0).getName(langCode), "", true)%></td>
	    <%
	    }else{
	    %>
	    <td valign="top">
	      <ol style="margin:15px 15px 0 0;padding: 0">
	        <%
	        	for(CommonEmployee s : supervisors){
	        %>
	          <li><%=Common.getDisplayString(s.getName(langCode), "", true)%></li>
	        <%}%>
	      </ol>
	    </td>
	    <%}%>
  </tr>
  <%}
    if(headName != null){
    %>
  <tr>
    <td class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.headName" />  : </td>
    <td><%=Common.getDisplayString(headName, "", true)%></td>
  </tr>
  <%}
  if(directorName != null){
  %>
  <tr>
    <td  class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.directorName" /> : </td>
    <td><%=Common.getDisplayString(directorName, "", true)%></td>
  </tr>
  <%}
  	if(directorateSupervisors != null && ! directorateSupervisors.isEmpty()){
  %>
  <tr>
    <td class="side_label_top"> <fmt:message key="jsp.HospitalProfile.directorateSupervisors" />  : </td>
    <%
    	if(directorateSupervisors.size() == 1){
    %>
	    <td><%=Common.getDisplayString(directorateSupervisors.get(0).getName(langCode), "", true)%></td>
	    <%
	    }else{
	    %>
	    <td valign="top">
	      <ol style="margin:15px 15px 0 0;padding: 0">
	        <%
	        	for(CommonEmployee s : directorateSupervisors){
	        %>
	          <li><%=Common.getDisplayString(s.getName(langCode), "", true)%></li>
	        <%}%>
	      </ol>
	    </td>
	    <%}%>
  </tr>
  <%}
  if(bedNo != null){%>
  <tr>
    <td class="side_label_middle">  <fmt:message key="jsp.HospitalProfile.bedNo" />  : </td>
    <td><%=Common.getDisplayString(bedNo,"<fmt:message key='jsp.HospitalProfile.notfound' />", true) %></td>
  </tr>
  <%}%>
</table>
</body>
</html>
