<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="java.util.List,com.iassets.common.util.*,com.iassets.common.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>بيانات الموقع</title>
<%
	String langCode = WebUtil.getSessionLanguage(request);
    CommonUser user= (CommonUser)request.getAttribute("userProfile");	
    CommonSite site = user.getCurrentSite(); 
    // CommonHospital location = (CommonHospital)request.getAttribute("location"); 
   
    String siteName = WebUtil.getCurrentSiteName(request, langCode);
    
    Integer hospNo = site.getAffiliatedHospitalsNo();
	String affHospitalsNo  = (hospNo != null && hospNo > 0)? "" + hospNo : null; 
	String affhealthCentersNo  = "" + site.getAffiliatedHealthCentersNo(); 
	String companyName = site.getGenOperatingCompany().getLocalizedName(langCode); 
	String siteManager = site.getGenSiteManagerName(langCode);
	List<CommonEmployee> supervisors = site.getGenDepartmentSupervisors();
	String headName = site.getGenDepartmentHeadName(langCode);
	String directorName = site.getHospitalDirectorName(langCode);
	List<CommonEmployee> directorateSupervisors = site.getGenDirectorateSupervisors();
	Integer bNo = site.getBedNo();
	String bedNo = (bNo != null && bNo > 0) ? "" + bNo : null;
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td colspan="2"><h1 class="page_title">بيانات الموقع</h1></td>
  </tr>
  <tr>
    <td class="side_label_middle">اسم الموقع : </td>
    <td><%=Common.getDisplayString(siteName, "", true)%></td>
  </tr>
  <%
  	if(site.getContainsSeveralLocations()){
  %>
  <tr>
    <td  class="side_label_middle">عدد المستشفيات التابعة : </td>
    <td><%=Common.getDisplayString(affHospitalsNo, "لا يوجد", true)%></td>
  </tr>
  <tr>
    <td  class="side_label_middle">عدد المراكز الصحية التابعة : </td>
    <td><%=Common.getDisplayString(affhealthCentersNo, "لا يوجد", true)%></td>
  </tr>
  <%
  	}
  %>
  <tr>
    <td class="side_label_middle">الشركة المشغلة : </td>
    <td><%=Common.getDisplayString(companyName, "", true)%></td>
  </tr>
  
  <tr>
    <td  class="side_label_middle">مدير الموقع : </td>
    <td><%=Common.getDisplayString(siteManager, "", true)%></td>
  </tr>
  <%
  	if(supervisors != null && ! supervisors.isEmpty()){
  %>
  <tr>
    <td class="side_label_top">مشرفي الصيانة الطبية : </td>
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
    <td class="side_label_middle"> رئيس قسم الصيانة الطبية : </td>
    <td><%=Common.getDisplayString(headName, "", true)%></td>
  </tr>
  <%}
  if(directorName != null){
  %>
  <tr>
    <td  class="side_label_middle"> مدير المستشفى : </td>
    <td><%=Common.getDisplayString(directorName, "", true)%></td>
  </tr>
 <%}
  	if(directorateSupervisors != null && ! directorateSupervisors.isEmpty()){
  %>
  <tr>
    <td class="side_label_top">مشرفي المديرية : </td>
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
    <td class="side_label_middle">عدد الأسرة : </td>
    <td><%=Common.getDisplayString(bedNo, "لا يوجد", true)%></td>
  </tr>
  <%}%>
</table>
</body>
</html>
