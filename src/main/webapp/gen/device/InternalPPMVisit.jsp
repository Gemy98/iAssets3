<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="java.util.*,com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,com.iassets.common.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
	String rChecked="";
	List<GenHospitalDeviceInternalPpmNotificationSchedule> ppmNotificationSchedules = (List<GenHospitalDeviceInternalPpmNotificationSchedule>) request.getAttribute("ppmNotificationSchedules");
	if(ppmNotificationSchedules != null && ppmNotificationSchedules.size() == 1)		
	   rChecked="checked='checked' ";
%>
</head>
<body>
<form action="gen/InternalPPMVisitProcess" method="post" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="2"><h1 class="page_title">شاشة تسجيل بيانات الصيانة الوقائية</h1></td>
</tr>
<tr>
  <td colspan="2"><%@ include file="../DeviceOverview.jsp" %></td>
</tr>
<%
if(ppmNotificationSchedules == null || ppmNotificationSchedules.isEmpty()){
	out.print("<tr><td colspan='2'><h3 class='error_msg'>لا توجد حاليا صيانة وقائية مجدولة لهذا الجهاز</h3></td></tr>");
	return;
}
%>
<%
String periodName = null;
String checkListUrl= null;
String plannedDate = null;
for(GenHospitalDeviceInternalPpmNotificationSchedule ns:ppmNotificationSchedules){
	 periodName = Common.getDisplayString(ns.getScheduledPPM().getPpmPeriod().getName(),"-",true);
	 checkListUrl= ns.getScheduledPPM().getChecklistFileUrl();
	 plannedDate = DateUtil.getDateString(ns.getPlannedDate());
 %>
<tr>
  <td colspan="2">
  <input type="radio" name="ppm_notify_id" <%=rChecked %> value="<%=ns.getId() %>">
  <%=periodName%> - مجدول بتاريخ - <%=plannedDate%> &nbsp;
  <%= HtmlUtil.showUploadedFilesInViewMode(checkListUrl, false, langCode)%>
  </td>
</tr>
<%}%>
<tr>
  <td colspan="2">&nbsp;</td>
</tr>
<tr>
  <td class="side_label_middle">حالة الزيارة :</td>
  <td>
    <input type="radio" name="ppm_Status" value="1" required>
    تمت
    <input type="radio" name="ppm_Status" value="2" required>
    لم تتم 
  </td>
</tr>

<tbody id="grp1">
  <tr>
    <td class="side_label_middle">تاريخ الزيارة :</td>
    <td><input type="text" name="visitDate" id="visitDate" class="caldr" required></td>
  </tr>

  <tr>
    <td class="side_label_top">تقرير الزيارة :</td>
    <td><input type="file" name="internal_ppm_file" id="internal_ppm_file" multiple class='requiredFile'></td>
  </tr>
  
  <%
		    List<CommonEmployee> responsibleEngs = (List<CommonEmployee>) request.getAttribute("responsibleEngs"); 
		    	if (responsibleEngs != null && ! responsibleEngs.isEmpty()){
		    	    String sClass = (responsibleEngs.size() == 1)? "class='auto_off'":"";
		    %>
			<tr>
				<td class="side_label_middle">الموظف المسؤول :</td>
				<td>          
					<select id="responsibleEng" name="responsibleEng" <%=sClass%> required>
			        <%for (CommonEmployee responsibleEng : responsibleEngs){%>
			            <option value="<%=responsibleEng.getId()%>"><%=responsibleEng.getName(langCode)%></option>
			        <%}%>
			        </select>
                </td>
			</tr>
		    <%
		    	}
		    %>
  
</tbody>

<tbody id="grp2">
  <tr>
    <td class="side_label_top">السبب</td>
    <td><textarea name="absenceReason" id="absenceReason" required></textarea></td>
  </tr>
</tbody>

<tr>
  <td colspan="2"><input type="submit" name="save" id="button" value="إتمام العملية"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
</tr>

</table>
</form>
<script>



$(function(){
	/*
	// to overcome the ajax load order of javascript code 
	if($("#visitDate").hasClass('hasDatepicker') == false){
		$("#visitDate").datepicker( {dateFormat : 'dd/mm/yy', changeMonth: true, changeYear: true, yearRange:"-1:+1"});
	}
	
    
    //$("input[name='visitStatus']").attr("required",true);
    if(! $.validate)
    	$("form").validate({   	  
	    	errorPlacement: function(error, element) {  // to adjust the radio and checkbox group msg placement
		  		  if($(element).is("input:file"))
		 			 element.closest("label").parent().append(error);
		 		  else
		 		     error.appendTo(element.parent());
	    	}
 	  });
    
    //$("input[name='visitStatus']").rules("add",{required:true});
	*/


 
    attachConditionalDisplayHandlerToRadio('ppm_Status', {"1":"grp1","2":"grp2"});
    
  
});
  
</script>
</body>
</html>
