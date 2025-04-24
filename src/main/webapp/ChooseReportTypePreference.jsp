<%@ page pageEncoding="UTF-8" import="com.iassets.common.entity.*, java.util.*, com.iassets.common.util.*,org.springframework.beans.factory.annotation.*,org.springframework.web.context.support.SpringBeanAutowiringSupport"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><%!
    public void jspInit(){
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

	@Autowired
	@Qualifier("commonDBQueryManager")

	protected com.iassets.common.DB.CommonDBQueryManager dbQueryManager;
%>

<%
  String languageCode = WebUtil.getSessionLanguage(request);
  CommonSite currentSite = WebUtil.getSessionUser(request).getCurrentSite();
  CommonHospital currentLocation = WebUtil.getSessionLocation(request);
  if (currentSite.getContainsSeveralLocations() && request.getParameter("report") != null) {
%>

<table class="layout_grid">
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.ChooseReportTypePreference.reportType" />:</td>
    <td valign="top">  
    <table id="report_type_options_layout">
	  <tr>
	    <td colspan="3">
	    <input name="reportType" type="radio" value="<%=Enums.REQUESTED_REPORT_TYPE.FOR_ALL_SITE_LOCATIONS.getId()%>">
	     <fmt:message key="jsp.ChooseReportTypePreference.mogama3" /></td>
	  </tr>
	  <%
	  	if(currentLocation != null){
	  %>
	  <tr>
	    <td colspan="3">
	    <input name="reportType" type="radio" value="<%=Enums.REQUESTED_REPORT_TYPE.FOR_CURRENT_LOCATION_ONLY.getId()%>">
	    <fmt:message key="jsp.ChooseReportTypePreference.hospitalOrCenter" /> </td>
	  </tr>
	  <%
	  	}
	  %>
	  <tr>
	    <td>
	      <input name="reportType" type="radio"  value="<%=Enums.REQUESTED_REPORT_TYPE.FOR_ANOTHER_LOCATION.getId()%>">
		  
		  <% if (currentLocation != null){ %>
	      &nbsp; <fmt:message key="jsp.ChooseReportTypePreference.reportType1" />
	      <%}else{ %>
	      &nbsp; <fmt:message key="jsp.ChooseReportTypePreference.reportType2" />
	      <%}%>
        </td>
	    <td>&nbsp;&nbsp;&nbsp;</td>
	    <td>
		    <select class="auto_off" name="reportLocation" id="reportLocation" onChange="setLocationCookie()">
		          <%
		          	List<CommonHospital> locationList = dbQueryManager.getSiteActiveLocations(currentSite.getId(), languageCode);
		          		      
		          		          if (locationList != null){
		          		            for (CommonHospital obj : locationList){
		          %>
		          <option value="<%=obj.getId()%>"><%=obj.getLocalizedName(languageCode)%></option>
		          <%}
		          }%>
		    </select>      
	    </td>
	  </tr>
  </table>
  </td>
  </tr>
</table>

<script>

  function setReportTypeCookie (){
  	$.removeCookie('reportType');
	$("#reportLocation").css('visibility', 'hidden');

	var reportType = $("input:radio[name='reportType']:checked").val();
	$.cookie('reportType', reportType);
	
	if (reportType == <%=Enums.REQUESTED_REPORT_TYPE.FOR_ANOTHER_LOCATION.getId()%>)
		$("#reportLocation").css('visibility', '');
	
  }
  
   function setLocationCookie(){
	    $.removeCookie('reportLocation');
		$.cookie('reportLocation', $('#reportLocation').val());
   }

   $(function(){
	   $("#reportLocation").css('visibility', 'hidden');
	   
	   // remove the current location from list
	
	   <%if(currentLocation != null){%>
	       $("#reportLocation option[value='<%=currentLocation.getId()%>']").remove();
	   <%}%>
	  
	   
	   $("input[name=reportType]").change(function(){setReportTypeCookie()});
	   
	   if($.cookie('reportType'))
	      setRadioCheckedValue("reportType",$.cookie('reportType'));
	   else
		  setRadioCheckedValue("reportType",'<%=Enums.REQUESTED_REPORT_TYPE.FOR_ALL_SITE_LOCATIONS.getId()%>'); 
	   
	   $("input:radio[name='reportType']:checked").change();
	   
	   if($.cookie('reportLocation')){
		  setComboSelectedValue("reportLocation", $.cookie('reportLocation'));
	      $("#reportLocation").change();
	   }
   })
  
</script>
<%} %>
