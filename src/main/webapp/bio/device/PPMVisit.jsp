<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
	BioHospitalDevicePpmDetail ppmVisit = (BioHospitalDevicePpmDetail) request.getAttribute("ppmVisit");
%>
</head>
<body>
<form action="bio/PPMVisitProcess" method="post" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="4"><h1 class="page_title"><fmt:message key="jsp.PPMVisit.pagetitle" /></h1></td>
</tr>
<tr>
  <td colspan="4"><%@ include file="../DeviceOverview.jsp" %>
</td>
</tr>

<!-- ppm form printing problem fix -->
<!-- <tr>
	<td colspan="2">&nbsp;</td>
</tr> -->
<!-- <tr>
	<td colspan="4" class="side_label_middle" style="text-align: right">
		<input name="prepareForPrinting" id="prepareForPrinting" type="checkbox" value="1"
		onclick="_onClickPrepareForPrintingButton(this)"> طباعة نموذج الصيانة الوقائية (في حالة إنجازها عن طريق المقاول الرئيسي)
	</td>
</tr> -->

<tr id="print_button_container">
	<td colspan="4">
	   <input style="margin-bottom: 10px;margin-top: 10px;background-color: #000;font-weight: bold" type='button' onclick='_onClickPrintPPMFormBtn()' value='<fmt:message key="jsp.PPMVisit.printPPMFormBtn" />' />
	   (<fmt:message key="jsp.PPMVisit.printPPMFormBtnNote" />)
	</td>
</tr>

<!-- end of ppm form printing problem fix -->


<tr>
  <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.visitMonth" /> :</td>
  <td><%= HtmlUtil.getDevicePPMVisitMonthesAsHtmlSelect("visitMonth", deviceInfo,langCode) %></td>
  <td class='side_label_middle'><fmt:message key="jsp.PPMVisit.visitYear" /> :</td>
  <td><select name="visitYear" id="visitYear"></select></td>
</tr>
<tr>
  <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.subcontractor" />:</td>
  <td colspan="3"><input type="text" name="subcontractor" id="subcontractor" required></td>
</tr>
<tr>
  <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.visitStatus" /> :</td>
  <td colspan="3">
    <input type="radio" name="visitStatus" value="1" required>
    <fmt:message key="jsp.PPMVisit.visitStatus1" />
    <input type="radio" name="visitStatus" value="2" required>
     <fmt:message key="jsp.PPMVisit.visitStatus2" />
    <input type="radio" name="visitStatus" value="3" required>
    <fmt:message key="jsp.PPMVisit.visitStatus3" /></td>
</tr>
<tbody id="grp1">
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.visitDate" />:</td>
    <td colspan="3"><input type="text" name="visitDate" id="visitDate" class="caldr" required></td>
  </tr>
 <% 
 boolean deviceCatNotC = deviceInfo.getCategory().getId() != Enums.DEVICE_CATEGORY.C.getDBId();
 if (deviceCatNotC){ %>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.visitCost" /> :</td>
    <td colspan="3"><input type="text" name="visitCost" id="visitCost" required data-rule-number="true"></td>
  </tr>
 <%}%>
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.PPMVisit.visitReportURL" /> :</td>
    <td colspan="3"><input type="file" name="visitReportURL" id="visitReportURL" multiple <%=(deviceCatNotC)?"class='requiredFile'":"" %>></td>
  </tr>
</tbody>
<tbody id="grp2">
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.PPMVisit.delayPenalty" /> :</td>
    <td colspan="3"><input type="text" name="delayPenalty" id="delayPenalty" required data-rule-number="true"></td>
  </tr>
</tbody>
<tbody id="grp3">
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.PPMVisit.absenceReason" /> :</td>
    <td colspan="3"><textarea name="absenceReason" id="absenceReason" required></textarea></td>
  </tr>
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.PPMVisit.absenceReportURL" />  :</td>
    <td colspan="3"><input type="file" name="absenceReportURL" id="absenceReportURL" multiple  class="requiredFile"></td>
  </tr>
</tbody>
<tr>
  <td colspan="4"><input type="submit" name="save" id="button" value='<fmt:message key="jsp.PPMVisit.submitbutton" />'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
</tr>
</table>
</form>
<script>

// $("#print_button_container").hide();

function _onClickPrepareForPrintingButton(btnObj){
	if($(btnObj).is(":checked"))
		$("#print_button_container").show();
	else
		$("#print_button_container").hide();
}

function _onClickPrintPPMFormBtn(){
    var win = window.open('bio/ViewPPMForm?deviceId=' + $("#deviceId").val(), '_blank');
    if(win)
    // Browser has allowed it to be opened
       win.focus();
    else
    // Broswer has blocked it
       alert('<fmt:message key="jsp.js.PPMVisit.alert" />');
}

$("#visitMonth , #visitYear").attr("required",true);

$(function(){
	
	// to overcome the ajax load order of javascript code 
	if($("#visitDate").hasClass('hasDatepicker') == false){
		$("#visitDate").datepicker( {dateFormat : 'dd/mm/yy', changeMonth: true, changeYear: true, yearRange:"-1:+1"});
	}
	
    var currentYear = new Date().getFullYear();
    for (var i=currentYear-4; i <=currentYear+4 ; i++)
	   $("#visitYear").append(new Option(i, i));
    
    
    //$("input[name='visitStatus']").attr("required",true);
    if(! $.validate)
    	$("form").validate({   	  
	    	errorPlacement: function(error, element) {  // to adjust the radio and checkbox group msg placement
		  		  if($(element).is("input:file"))
		 			 element.closest("label").parent().append(error);
		 		  else
		 		     error.appendTo(element.parent());
	    	},
	        submitHandler: function(form) { // <- pass 'form' argument in
	            // var submitBtn = $(form).find("input[type='submit']:first");
	            var formTarget = $(form).attr("target");
	            var overlayDiv = "<div class='dimmed'><p><img width='128' height='128' src='image/progressbar.gif' /><fmt:message key='jsp.js.DeviceScrapping.overlayDiv' /></p></div>"
	            // if(submitBtn && formTarget != "_blank"){
	            if(formTarget != "_blank"){
	            	$("#container").append(overlayDiv);
	                //submitBtn.attr("disabled", true);
	            	//submitBtn.attr("value","جاري إتمام العملية ...");
	            }
	            form.submit(); // <- use 'form' argument here.
	        }
 	  });
    
    //$("input[name='visitStatus']").rules("add",{required:true});
	  
<%
String subcontractor = request.getParameter("subcontractor");
if(subcontractor == null){
	if(deviceCatNotC)
	  subcontractor = deviceInfo.getSubcontractor();
	else
	  subcontractor = deviceInfo.getHospital().getSite().getBioOperatingCompany().getLocalizedName(langCode);
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
