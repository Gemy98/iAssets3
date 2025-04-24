<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
	GenHospitalDevicePpmDetail ppmVisit = (GenHospitalDevicePpmDetail) request.getAttribute("ppmVisit");
%>
</head>
<body>
<form action="gen/PPMVisitProcess" method="post" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="4"><h1 class="page_title">شاشة بيانات زيارة صيانة الوكيل</h1></td>
</tr>
<tr>
  <td colspan="4"><%@ include file="../DeviceOverview.jsp" %>
</td>
</tr>
<tr>
  <td class="side_label_middle">شهر الصيانة :</td>
  <td><%= HtmlUtil.getDevicePPMVisitMonthesAsHtmlSelect("visitMonth", deviceInfo,langCode) %></td>
  <td class='side_label_middle'>سنة الصيانة :</td>
  <td><select name="visitYear" id="visitYear"></select></td>
</tr>
<tr>
  <td class="side_label_middle">مقاول الباطن :</td>
  <td colspan="3"><input type="text" name="subcontractor" id="subcontractor" required></td>
</tr>
<tr>
  <td class="side_label_middle">حالة الزيارة :</td>
  <td colspan="3">
    <input type="radio" name="visitStatus" value="1" required>
    تمت
    <input type="radio" name="visitStatus" value="2" required>
    لم تتم
    <input type="radio" name="visitStatus" value="3" required>
    متأخرة</td>
</tr>
<tbody id="grp1">
  <tr>
    <td class="side_label_middle">تاريخ الزيارة :</td>
    <td colspan="3"><input type="text" name="visitDate" id="visitDate" class="caldr" required></td>
  </tr>
<%--  <% 
 boolean deviceCatNotC = deviceInfo.getCategory().getId() != Enums.DEVICE_CATEGORY.C.getDBId();
 if (deviceCatNotC){ %> --%>
  <tr>
    <td class="side_label_middle">سعر الزيارة :</td>
    <td colspan="3"><input type="text" name="visitCost" id="visitCost" required data-rule-number="true"></td>
  </tr>
<%--  <%}%> --%>
  <tr>
    <td class="side_label_top">تقرير الزيارة :</td>
    <td colspan="3"><input type="file" name="visitReportURL" id="visitReportURL" multiple class='requiredFile'></td>
  </tr>
</tbody>
<tbody id="grp2">
  <tr>
    <td class="side_label_middle">حسميات :</td>
    <td colspan="3"><input type="text" name="delayPenalty" id="delayPenalty" required data-rule-number="true"></td>
  </tr>
</tbody>
<tbody id="grp3">
  <tr>
    <td class="side_label_top">أسباب عدم الحضور :</td>
    <td colspan="3"><textarea name="absenceReason" id="absenceReason" required></textarea></td>
  </tr>
  <tr>
    <td class="side_label_top">تقرير بعدم الحضور :</td>
    <td colspan="3"><input type="file" name="absenceReportURL" id="absenceReportURL" multiple  class="requiredFile"></td>
  </tr>
</tbody>
<tr>
  <td colspan="4"><input type="submit" name="save" id="button" value="إتمام العملية"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
</tr>
</table>
</form>
<script>

$("#visitMonth , #visitYear").attr("required",true);

$(function(){
	
	// to overcome the ajax load order of javascript code 
	if($("#visitDate").hasClass('hasDatepicker') == false){
		$("#visitDate").datepicker( {dateFormat : 'dd/mm/yy', changeMonth: true, changeYear: true, yearRange:"-1:+1"});
	}
	
    var currentYear = new Date().getFullYear();
    for (var i=currentYear-1; i <=currentYear+1 ; i++)
	   $("#visitYear").append(new Option(i, i));
    
    
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
	  
<%
String subcontractor = request.getParameter("subcontractor");
if(subcontractor == null){
	//if(deviceCatNotC)
	  subcontractor = deviceInfo.getSubcontractor();
	//else
	//  subcontractor = deviceInfo.getHospital().getSite().getGenOperatingCompany().getName();
}

if (ppmVisit != null){
	String ppmId = "" + ppmVisit.getId();
    // String visitMonth = Common.getDisplayString(ppmVisit.getVisitMonth() , "");
    // String visitYear = Common.getDisplayString(ppmVisit.getVisitYear() , "");
    int visitStatus = ppmVisit.getVisitStatus();
    String visitDate = DateUtil.getDateString(ppmVisit.getVisitDate());
    String visitCost = Common.getDisplayString(ppmVisit.getVisitCost(),"");
    subcontractor = ppmVisit.getSubcontractor();
    String delayPenalty = Common.getDisplayString(ppmVisit.getDelayPenalty(),"");
    String absenceReason  = Common.getDisplayString(ppmVisit.getAbsenceReason() , "");
    String visitReportFiles = ppmVisit.getVisitReportUrl();
    String absenceReportFiles = ppmVisit.getAbsenceReportUrl();
%>
	appendIdElement("ppmId","<%=ppmId%>");
	
	showUploadedFilesList("visitReportURL", <%=HtmlUtil.arrayFromJavaToJavaScript(visitReportFiles)%>);
	showUploadedFilesList("absenceReportURL", <%=HtmlUtil.arrayFromJavaToJavaScript(absenceReportFiles)%>);

    setRadioCheckedValue("visitStatus",'<%=visitStatus%>');
    $("input:radio[name='visitStatus']:checked").change();
    
    setDateValue("visitDate","<%=visitDate%>");
    setTextFieldValue("visitCost","<%=visitCost%>");

    setTextFieldValue("delayPenalty","<%=delayPenalty%>");	
    setInnerHTML("absenceReason","<%=absenceReason%>");

<%}
subcontractor = Common.getDisplayString(subcontractor,"");
%>
 
	setTextFieldValue("subcontractor","<%=subcontractor%>");
	
    setComboSelectedValue("visitMonth",'<%=request.getParameter("visitMonth")%>');
    setComboSelectedValue("visitYear",'<%=request.getParameter("visitYear")%>');
    
    $("#visitMonth").change(function(){getPPMVisit()});
    $("#visitYear").change(function(){getPPMVisit()});
    
    attachConditionalDisplayHandlerToRadio('visitStatus', {"1":"grp1","3":["grp1","grp2"],"2":"grp3"});
    
  
});
  
</script>
</body>
</html>
