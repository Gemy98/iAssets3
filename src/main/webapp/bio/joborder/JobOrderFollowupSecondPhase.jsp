<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.biomedical.util.HtmlUtil,com.iassets.common.util.Enums,com.iassets.biomedical.entity.*,java.util.Date"%>
<%BioEndUserMaintenanceRequest maintenanceReqInfo = (BioEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);
 %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script>

var continueBtnSel = "#<%=Default.SAVE_THEN_CONTINUE_BTN_NAME%>";

function _onChangeSpFirstRadio(obj){
		
    $("#grp1").hide();
    $("#grp2").hide();
    $("#grp3").hide();
    $("#final_section_contents").hide();
    $("#grp4, #grp5").hide();
    $("#grp6").hide();
    //markAsNotRequired("#closed");

	var selectedValue = $(obj).filter(':checked').val();
	if(selectedValue == 0){
	   $('input[name="spSource"]').prop('checked', false);
	   $("#final_section_contents").show();
	   $("#grp6").show();
	   $('#quotationAccepted').attr('checked', false);
	   _onClickQuotationAcceptanceButton()
	   //$(continueBtnSel).hide();
	 }else if(selectedValue == 1){
	   $("#grp1").show();
	}
	//_onChangeSPSourceRadio($('input[name="spSource"]'));
}

function _onChangeSPSourceRadio(obj){
	
    $("#grp2").hide();
    $("#grp3").hide();
    $("#final_section_contents").hide();
    $("#grp4, #grp5").hide();
    $("#grp6").show();
    //$(continueBtnSel).hide();
	//markAsNotRequired("#closed");
	
	var selectedValue = $(obj).filter(':checked').val();
	
	if(selectedValue == <%=Enums.JOBORDER_SP_SOURCES.INVENTORY.getId()%> || selectedValue == <%=Enums.JOBORDER_SP_SOURCES.PURCHASE_ORDER.getId()%>){
	   $("#final_section_contents").show();
	   //markAsRequired("#closed");
	   if(selectedValue == <%=Enums.JOBORDER_SP_SOURCES.INVENTORY.getId()%>)
		   $("#grp2").show();
	   else 
	       $("#grp3").show();
	}else if(selectedValue == <%=Enums.JOBORDER_SP_SOURCES.AGENT.getId()%>){
		$("#grp4").show();
		_onClickOfferRecievedButton();
		//$(continueBtnSel).show();
	}
}

function _onClickOfferRecievedButton(){
	
	$("#grp5").hide();
	if($("#quotationRecieved").is(":checked"))
		$("#grp5").show();
}

function _onClickQuotationAcceptanceButton(){
	if($("#quotationAccepted").is(":checked")){
		$("#quotationAcceptanceDate").datepicker( "setDate", new Date());
		$("#quotation_acceptance_container").show();
		$(continueBtnSel).show();
		markAsRequired("#secondAction");
		markAsRequired("#secondActionDate");
	}else{
		$("#quotationAcceptanceDate").val("");
		$("#quotation_acceptance_container").hide();
		$(continueBtnSel).hide();
		markAsNotRequired("#secondAction");
		markAsNotRequired("#secondActionDate");
	}	
}

function getSPInventoryContentDetails(srcObj, rowIndex){
	
	var codeVal = $(srcObj).val();
	// alert(codeVal);
	
	if (codeVal != null){
/* 		$("#inventorySpareParts_spDescription_" + rowIndex).html("");
	    $("#inventorySpareParts_spQuantity_" + rowIndex).val("");
	    $("#inventorySpareParts_spPrice_" + rowIndex).html(""); */
	    
		// reset validation error
		$("#inventorySpareParts_spQuantity_" + rowIndex).removeClass('error').next('label.error').remove();
		//var spQuantityCtrl = $('#inventorySpareParts').appendGrid('getCellCtrl', 'spQuantity', rowIndex);
		//$(spQuantityCtrl).removeClass('error').next('label.error').remove();
	    
	    if (codeVal != ""){
		  	jQuery.getJSON("bio/JobOrderSPInventoryAjax",{code: codeVal}, function(data){
			    if (typeof data.msg == 'undefined'){ 
				    $("#inventorySpareParts_spDescription_" + rowIndex).html(data.spDescription);
				    $("#inventorySpareParts_spQuantity_" + rowIndex).val(data.spQuantity);
				    $("#inventorySpareParts_spPrice_" + rowIndex).html(data.spPrice); 
			    }else {
			    	alert($(srcObj).val()+  " : " + data.msg);
				    $(srcObj).val("");
				    $("#inventorySpareParts_spDescription_" + rowIndex).html("");
				    $("#inventorySpareParts_spQuantity_" + rowIndex).val("");
				    $("#inventorySpareParts_spPrice_" + rowIndex).html("");
			    }
		    });
	   }

	}
} 

</script>
</head>
<body>
<form method="post" action="bio/JobOrderFollowupSecondPhaseProcess" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.JobOrderFollowupSecondPhase.pagetitle" /></h1></td>
</tr>
<tr>
  <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %></td>
</tr>
<%if(maintenanceReqInfo == null){%>
<tr>
	<td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupSecondPhase.joRequestFormUrl" />:</td>
	<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
<tr>
<%} %>
<tr>
  <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupSecondPhase.firstAction" />:</td>
  <td><%=Common.getDisplayString(jobOrderInfo.getFirstAction(),"",true)%></td>
</tr>
<tr>
  <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.firstActionDate" /> :</td>
  <td>
	  <%
	  	String firstActionDate = DateUtil.getDateString(jobOrderInfo.getFirstActionDate()); 
	  	  out.print(firstActionDate);
	  %>
	  <input type="text" name="firstActionDate" id="firstActionDate" class="caldr" value="<%=firstActionDate%>" style="display:none">
  </td>
</tr>

<tr>
  <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.fixIncludingSpareParts" /> :</td>
  <td><input name="fixIncludingSpareParts" value="1" type="radio" required>
    <fmt:message key="jsp.JobOrderFollowupSecondPhase.fixIncludingSparePartsYes" />
    <input name="fixIncludingSpareParts" value="0" type="radio" required>
    <fmt:message key="jsp.JobOrderFollowupSecondPhase.fixIncludingSparePartsNo" /></td>
</tr>

<tbody id="grp1">
<%
String agentBtnAttrs = "";
String otherBtnsAttrs = "";
/* 
String agentBtnAttrs = "disabled='disabled'";
String otherBtnsAttrs = "";
if(jobOrderInfo.getAgentMustAttend()){
	agentBtnAttrs = "checked='checked'";
	otherBtnsAttrs = "disabled='disabled'";
} 
*/
%>
<tr>
  <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.spSource" /> :</td>
  <td>
    <input name="spSource" value="<%=Enums.JOBORDER_SP_SOURCES.AGENT.getId()%>" type="radio" required <%=agentBtnAttrs%>>
    <fmt:message key="jsp.JobOrderFollowupSecondPhase.spSource1" /> 
    <input name="spSource" value="<%=Enums.JOBORDER_SP_SOURCES.INVENTORY.getId()%>" type="radio" required <%=otherBtnsAttrs%>>
    <fmt:message key="jsp.JobOrderFollowupSecondPhase.spSource2" /> 
   <%--    
    <input name="spSource" value="<%=Enums.JOBORDER_SP_SOURCES.PURCHASE_ORDER.getId()%>" type="radio" required <%=otherBtnsAttrs%>>
    أمر شراء مباشر
   --%>
   </td>
</tr>
</tbody>

<tbody id="grp2">
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.inventorySpareParts" />:</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" id="inventorySpareParts"></table></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.spTransDate" /> :</td>
    <td><input type="text" name="spTransDate" id="spTransDate" class="caldr" required></td>
  </tr>
</tbody>

<tbody id="grp3">
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.poSpareParts" />:</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" id="poSpareParts"></table></td>
  </tr>
    <td class="side_label_top"> <fmt:message key="jsp.JobOrderFollowupSecondPhase.spPoScanUrl" />:</td>
    <td><input type="file" name="spPoScanUrl" id="spPoScanUrl" multiple class="requiredFile" /></td>
  </tr>
</tbody>

<%@ include file="FinalActionSection.jsp" %>

<tbody id="grp4">
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupSecondPhase.agentReportUrl" />:</td>
    <td><input type="file" name="agentReportUrl" id="agentReportUrl" multiple class="requiredFile" /></td>
  </tr>
  <tr>
	<td colspan="2" class="side_label_middle" style="text-align: right">
		<input name="quotationRecieved" id="quotationRecieved" type="checkbox" value="1" onclick="_onClickOfferRecievedButton()"><fmt:message key="jsp.JobOrderFollowupSecondPhase.offerRecievedButton" />
	</td>
  </tr>
</tbody>
<tbody id="grp5"> 
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationScanUrl" />:</td>
    <td><input type="file" name="quotationScanUrl" id="quotationScanUrl" multiple class="requiredFile" /></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationDate" /> :</td>
    <td><input type="text" name="quotationDate" id="quotationDate" class="caldr" required></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationExpireDate" /> :</td>
    <td><input type="text" name="quotationExpireDate" id="quotationExpireDate" class="caldr" required></td>
  </tr>
  <tr>
    <td class="side_label_middle"> <fmt:message key="jsp.JobOrderFollowupSecondPhase.agentSpareParts" /> :</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" id="agentSpareParts"></table></td>
  </tr>
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupSecondPhase.secondAction" />:</td>
    <td><textarea name="secondAction" id="secondAction"></textarea></td>
  </tr>
  <tr>
	<td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupSecondPhase.secondActionDate" />:</td>
	<td><input type="text" name="secondActionDate" id="secondActionDate" class="caldr"></td>
  </tr>
  <%
  	if (WebUtil.userHasRightToAcceptSparePartsQuotation(request)) {
  %>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2" class="side_label_middle" style="text-align: right">
			<input name="quotationAccepted" id="quotationAccepted" type="checkbox" value="1" onclick="_onClickQuotationAcceptanceButton()"><fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationAcceptanceButton" /></td>
	</tr>
	<tbody id="quotation_acceptance_container">
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationAcceptanceNo" /> :</td>
			<td><input type="text" name="quotationAcceptanceNo" id="quotationAcceptanceNo" required></td>
		</tr>
		<tr>
			<td class="side_label_middle"> <fmt:message key="jsp.JobOrderFollowupSecondPhase.quotationAcceptanceDate" /> :</td>
			<td><input type="text" name="quotationAcceptanceDate" id="quotationAcceptanceDate" class="caldr" required></td>
		</tr>
	</tbody>
  <%
  	}
  %>
</tbody>
<tbody id="grp6">
	<tr>
		<td colspan="2">
		  <%
		  	out.print(HtmlUtil.getSaveThenReturnButtonHTML(langCode));
		  		  
		  		  out.print(HtmlUtil.getSaveThenContinueButtonHTML(langCode));
		  	
		  		  if(WebUtil.userHasRightToUpdateCompletedPhasesOfJobOrderFollowup(request))
		  	 out.print(HtmlUtil.getSaveThenGoBackButtonHTML(langCode));
		  		  
		  		  out.print(HtmlUtil.getResetButtonHTML(langCode));
		  %>
		</td>
	</tr>
</tbody>
</table>
</form>
<script>
$(function(){
	
<%BioJobOrder jobOrder = jobOrderInfo;

	int jobOrderSparePartsCount = 0 ;
	if (jobOrder.getJobOrderSpareParts()!= null )
	    jobOrderSparePartsCount = jobOrder.getJobOrderSpareParts().size();
	
	int sparePartsInQuotationCount = 0 ;
	if (jobOrder.getSparePartsInsideQuotation()!= null )
		sparePartsInQuotationCount = jobOrder.getSparePartsInsideQuotation().size();
	
	String requestFormFiles = jobOrder.getRequestFormUrl();
	
    Boolean fixIncludingSpareParts = jobOrder.getFixIncludingSpareParts();
    Integer spSource = jobOrder.getSpSource();
    
    
    Date spTransDate = jobOrder.getInventoryTransactionDate();
    //if(spTransDate == null)
    	//spTransDate = new Date();
    
    String spTransDateStr = DateUtil.getDateString(spTransDate);
    
    String spPoScanUrl =  jobOrder.getSpPoScanUrl();

    String agentReportFiles = jobOrder.getAgentReportUrl();
    Boolean quotationRecieved = jobOrder.getQuotationRecieved();
    String quotationScannedFiles = jobOrder.getQuotationScanUrl();
    String quotationDate =  DateUtil.getDateString(jobOrder.getQuotationDate());
    String quotationExpireDate =  DateUtil.getDateString(jobOrder.getQuotationExpireDate());
    String secondAction = Common.getDisplayString(jobOrder.getSecondAction(),"");
    String secondActionDate =  DateUtil.getDateString(jobOrder.getSecondActionDate());%>
    $(continueBtnSel).hide();

	$("#spTransDate").rules( "add", {
		dateGreaterThan: ["#firstActionDate"],
		messages: {	dateGreaterThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.spTransDate' />"}
	});
		
	$("#quotationDate").rules( "add", {
		dateGreaterThan: ["#firstActionDate"],
		messages: {	dateGreaterThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.quotationDate' />"}
	});
	
 	$("#quotationExpireDate").rules( "add", {
		dateGreaterThan: ["#quotationDate"],
		messages: {	dateGreaterThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.quotationExpireDate' />"}
	});
 	
	$("#secondActionDate").rules( "add", {
		dateLessThan: ["#quotationExpireDate"],
		dateGreaterThan: ["#quotationDate"],
		messages: {	dateLessThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.secondActionDate.dateLessThan' />", dateGreaterThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.secondActionDate.dateGreaterThan' />"}
	});
 	
	
	<% if(WebUtil.userHasRightToAcceptSparePartsQuotation(request)){%>
	
		$("#quotation_acceptance_container").hide();
		
		$("#quotationAcceptanceDate" ).rules( "add", {
			dateLessThan: ["#quotationExpireDate"],
			dateGreaterThan: ["#secondActionDate"],
			messages: {	dateLessThan: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.quotationAcceptanceDate.dateLessThan' />", dateGreaterThan:"<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.quotationAcceptanceDate.dateGreaterThan' />"}
		});
	
	<%}%>
	
	showUploadedFilesList("joRequestFormUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(requestFormFiles)%>);
	
	$('input:radio[name="fixIncludingSpareParts"]').change(function(){_onChangeSpFirstRadio(this)});
	setRadioCheckedValue("fixIncludingSpareParts",'<%=(fixIncludingSpareParts != null && fixIncludingSpareParts) ? "1" : "0"%>');	
	$('input:radio[name="fixIncludingSpareParts"]:checked').change(); 
	
	$('input:radio[name="spSource"]').change(function(){_onChangeSPSourceRadio(this)});
	setRadioCheckedValue("spSource",'<%=spSource%>');
	$('input:radio[name="spSource"]:checked').change(); 
	
	setCheckedValues("quotationRecieved",'<%=(quotationRecieved != null && quotationRecieved) ? "1" : "0"%>');	
	_onClickOfferRecievedButton();
	showUploadedFilesList("agentReportUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(agentReportFiles)%>);
	showUploadedFilesList("quotationScanUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(quotationScannedFiles)%>);
	setDateValue("quotationDate","<%=quotationDate%>");
	setDateValue("quotationExpireDate","<%=quotationExpireDate%>"); 
    setInnerHTML("secondAction","<%=secondAction%>");
    setDateValue("secondActionDate","<%=secondActionDate%>"); 
	
	$('#agentSpareParts').appendGrid({
	    initRows: 0,
	    i18n: {rowEmpty:'<fmt:message key="jsp.js.JobOrderFollowupSecondPhase.agentSpareParts.rowEmpty" />' },
	    columns: [
	            { name: 'accDescription', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.agentSpareParts.accDescription' />"  , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '250px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true } },
	            { name: 'accQuantity', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.agentSpareParts.accQuantity' />" , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-digits': true } },
	            { name: 'accPrice', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.agentSpareParts.accPrice' />" , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '100px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-number': true } },
	        ]
	}); 
	
    
	showUploadedFilesList("spPoScanUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(spPoScanUrl)%>);
	
	$('#poSpareParts').appendGrid({
	    initRows: 0,
	    i18n: {rowEmpty:"<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.poSpareParts.rowEmpty' />"},
	    columns: [
	            { name: 'accDescription', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.poSpareParts.accDescription' />"  , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '250px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true } },
	            { name: 'accQuantity', display: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.poSpareParts.accQuantity' />" , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-digits': true } },
	            { name: 'accPrice', display: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.poSpareParts.accPrice' />"   , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '100px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-number': true } },
	        ]
	}); 
	

    // $("#spTransDate").datepicker( "setDate", new Date());
	setDateValue("spTransDate","<%=spTransDateStr%>");
	
	$('#inventorySpareParts').appendGrid({
	    initRows: 0,
	    i18n: {rowEmpty: "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.inventorySpareParts.rowEmpty' />"},
	    columns: [
	           { name: 'spCode', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.inventorySpareParts.spCode' />", type: 'text' ,  displayCss:{'text-align':'center'}, ctrlCss: {border :'none', display:'block', width: '70px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }}, 
	           { name: 'spDescription', display:  "<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.inventorySpareParts.spDescription' />"  , type: 'custom', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', margin: 'auto', 'font-size':'.9em'} ,
	               customBuilder: function (parent, idPrefix, name, uniqueIndex) {
	                   return _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex);
	                },
	                customSetter: function (idPrefix, name, uniqueIndex, value) {
	                    _appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value);
	                }
              },
			  { name: 'spQuantity', display:"<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.inventorySpareParts.spQuantity' />"  , type: 'text' , displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: {'data-msg-remote'  : 'الكمية المتاحة أقل من المطلوب',   'data-rule-digits': true} },
	          { name: 'spPrice', display:"<fmt:message key='jsp.js.JobOrderFollowupSecondPhase.inventorySpareParts.spPrice' />"  , type: 'custom', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', margin: 'auto', 'font-size':'.9em'},
				  customBuilder: function (parent, idPrefix, name, uniqueIndex) {
					  return _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex);
                   } ,
	                customSetter: function (idPrefix, name, uniqueIndex, value) {
	                	_appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value);
	               }
	          },
	        ]
 	        , afterRowAppended: function (caller, parentRowIndex, addedRowIndex) {	
	        	   var uniqueIndex = $(caller).appendGrid('getUniqueIndex', addedRowIndex);
	        	   // alert("uniqueIndex : " + uniqueIndex);
		           
	        	   var spCode = $(caller).appendGrid('getCellCtrl', 'spCode', uniqueIndex);
		           $(spCode).blur(function(){
		        		  getSPInventoryContentDetails(this , parseInt(uniqueIndex));
		           });
		           
		           var spQuantity    = $(caller).appendGrid('getCellCtrl', 'spQuantity', uniqueIndex);
		          // var spPrice       = $(caller).appendGrid('getCellCtrl', 'spPrice', uniqueIndex);
		          // var spDescription = $(caller).appendGrid('getCellCtrl', 'spDescription', uniqueIndex);
	  
 		     	  $(spQuantity).rules("add",{
			     	remote:{
			     		url: "bio/JobOrderSPInventoryAjax",
			     		data: {
			     		      code: function(){return $(spCode).val()},
			     		      quantity: function(){return $(spQuantity).val()}		
			     			 }
			     	}
		         });
		   }
		}); 
	
	
<%if (fixIncludingSpareParts != null && fixIncludingSpareParts && spSource != null){
  BioJobOrderSparePart josp = null;
  if(jobOrderSparePartsCount > 0){
	  if(spSource == Enums.JOBORDER_SP_SOURCES.INVENTORY.getId())
		  for (int i = 0 ; i < jobOrderSparePartsCount ;  i++){
			  josp = jobOrder.getJobOrderSpareParts().get(i);
			  out.print("$('#inventorySpareParts').appendGrid('appendRow', [{");
			  out.print("spCode:'"+josp.getSpInventoryCategoryId().getCode()+"', spDescription:'"+josp.getSpInventoryCategoryId().getName()+"', spQuantity:'"+josp.getQuantity()+"', spPrice:'"+josp.getSpInventoryCategoryId().getAvgUnitPrice()+"'");
			  out.print("}]);");
		  }
	  else if(spSource == Enums.JOBORDER_SP_SOURCES.PURCHASE_ORDER.getId())
		  for (int i = 0 ; i < jobOrderSparePartsCount ;  i++){
			  josp = jobOrder.getJobOrderSpareParts().get(i);
			  out.print("$('#poSpareParts').appendGrid('appendRow', [{");
			  out.print("accDescription:'"+josp.getDescription()+"', accQuantity:'"+josp.getQuantity()+"', accPrice:'"+josp.getPrice()+"'");
			  out.print("}]);");
		  } 
  
  }else if(spSource == Enums.JOBORDER_SP_SOURCES.AGENT.getId() && sparePartsInQuotationCount > 0){
	  for (int i = 0 ; i < sparePartsInQuotationCount ;  i++){
		  josp = jobOrder.getSparePartsInsideQuotation().get(i);
		  out.print("$('#agentSpareParts').appendGrid('appendRow', [{");
		  out.print("accDescription:'"+josp.getDescription()+"', accQuantity:'"+josp.getQuantity()+"', accPrice:'"+josp.getPrice()+"'");
		  out.print("}]);");
	  } 
  }
}%>
    
});

</script>
</body>
</html>
