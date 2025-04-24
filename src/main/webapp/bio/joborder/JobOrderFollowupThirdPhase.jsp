<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.biomedical.util.HtmlUtil,com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%BioEndUserMaintenanceRequest maintenanceReqInfo = (BioEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);
 %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<form method="post" action="bio/JobOrderFollowupThirdPhaseProcess" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.JobOrderFollowupThirdPhase.pagetitle" /></h1></td>
</tr>
<tr>
  <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %></td>
</tr>
<%if(maintenanceReqInfo == null){%>
<tr>
	<td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupThirdPhase.joRequestFormUrl" />:</td>
	<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
<tr>
<%} %>
<tr>
  <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupThirdPhase.firstAction" />:</td>
  <td><%=Common.getDisplayString(jobOrderInfo.getFirstAction(),"",true)%></td>
</tr>
<tr>
  <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.firstActionDate" />:</td>
  <td>
	  <%
	  	String firstActionDate = DateUtil.getDateString(jobOrderInfo.getFirstActionDate()); 
	  	  out.print(firstActionDate);
	  %>
	  <input type="text" name="firstActionDate" id="firstActionDate" class="caldr" value="<%=firstActionDate%>" style="display:none">
  </td>
</tr>

<tr>
    <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupThirdPhase.agentReportUrl" />:</td>
    <td><%=HtmlUtil.showUploadedFilesInViewMode(jobOrderInfo.getAgentReportUrl(), false, langCode)%></td>
</tr>
 <tr>
   <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationScanUrl" /> :</td>
   <td><%=HtmlUtil.showUploadedFilesInViewMode(jobOrderInfo.getQuotationScanUrl(), false, langCode)%></td>
 </tr>
 <tr>
   <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationDate" />:</td>
   <td>
   	  <%
   	  	String quotationDate = DateUtil.getDateString(jobOrderInfo.getQuotationDate()); 
   	  	  out.print(quotationDate);
   	  %>
   <input type="text" name="quotationDate" id="quotationDate" class="caldr" value="<%=quotationDate%>" style="display:none">
   </td>
 </tr>
 <tr>
   <td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationExpireDate" />:</td>
   <td>
    <%
    	String quotationExpireDate = DateUtil.getDateString(jobOrderInfo.getQuotationExpireDate()); 
    	  out.print(quotationExpireDate);
    %>
   <input type="text" name="quotationExpireDate" id="quotationExpireDate" class="caldr" value="<%=quotationExpireDate%>" style="display:none"></td>
 </tr>
 <tr>
	<td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationAcceptanceNo" /> :</td>
	<td><%=Common.getDisplayString(jobOrderInfo.getQuotationAcceptanceNo(),"",true)%></td>
 </tr>
 <tr>
	<td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationAcceptanceDate" />:</td>
	<td>
	 <%
	 	String quotationAcceptanceDate = DateUtil.getDateString(jobOrderInfo.getQuotationAcceptanceDate()); 
	 	  out.print(quotationAcceptanceDate);
	 %>
	<input type="text" name="quotationAcceptanceDate" id="quotationAcceptanceDate" class="caldr" value="<%=quotationAcceptanceDate%>" style="display:none">
	</td>
 </tr>
 <tr>
   <td colspan="2" class="side_label_middle" style="text-align: right"><fmt:message key="jsp.JobOrderFollowupThirdPhase.quotationSpareParts" /> :</td>
 </tr>
 <tr>
   <td colspan="2"><table width="100%" id="quotationSpareParts"></table></td>
 </tr>
 <tr>
   <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupThirdPhase.secondAction" />:</td>
   <td><%=Common.getDisplayString(jobOrderInfo.getSecondAction(),"",true)%></td>
 </tr>
 <tr>
	<td class="side_label_middle"><fmt:message key="jsp.JobOrderFollowupThirdPhase.secondActionDate" />:</td>
	<td>
	 <%
	 	String secondActionDate = DateUtil.getDateString(jobOrderInfo.getSecondActionDate()); 
	 	  out.print(secondActionDate);
	 %>
	<input type="text" name="secondActionDate" id="secondActionDate" class="caldr" value="<%=secondActionDate%>" style="display:none">
	</td>
 </tr>
 
 <tr>
   <td colspan="2" class="side_label_middle" style="text-align: right"><fmt:message key="jsp.JobOrderFollowupThirdPhase.extraSpareParts" />:</td>
 </tr>
 <tr>
   <td colspan="2"><table width="100%" id="extraSpareParts"></table></td>
 </tr>

<%@ include file="FinalActionSection.jsp" %>


<tbody id="grp6">
	<tr>
		<td colspan="2">
		  <%
		  	out.print(HtmlUtil.getSaveThenReturnButtonHTML(langCode));  
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
	
	int sparePartsInQuotationCount = 0 ;
	if (jobOrder.getSparePartsInsideQuotation()!= null )
		sparePartsInQuotationCount = jobOrder.getSparePartsInsideQuotation().size();
	
	int sparePartsOutQuotationCount = 0 ;
	if (jobOrder.getSparePartsOutsideQuotation()!= null )
		sparePartsOutQuotationCount = jobOrder.getSparePartsOutsideQuotation().size();
	
	String requestFormFiles = jobOrderInfo.getRequestFormUrl();%>

	showUploadedFilesList("joRequestFormUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(requestFormFiles)%>);
	
	$('#quotationSpareParts').appendGrid({
	    initRows: 0,
	    i18n: {rowEmpty: '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.quotationSpareParts.rowEmpty" />'},
	    columns: [
	            { name: 'accDescription', display: '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.quotationSpareParts.accDescription" />'   , type: 'custom', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', margin: 'auto', width: '200px', 'font-size':'.9em'}, 
		               customBuilder: function (parent, idPrefix, name, uniqueIndex) {
		                   return _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex);
		                },
		                customSetter: function (idPrefix, name, uniqueIndex, value) {
		                    _appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value);
		                }
		        },
	            { name: 'accQuantity', display:  '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.quotationSpareParts.accQuantity" />'  , type: 'custom', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', margin: 'auto', 'font-size':'.9em'}, 
		               customBuilder: function (parent, idPrefix, name, uniqueIndex) {
		                   return _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex);
		                },
		                customSetter: function (idPrefix, name, uniqueIndex, value) {
		                    _appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value);
		                }
	            },
	            { name: 'accPrice', display:  '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.quotationSpareParts.accPrice" />'   , type: 'custom', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', margin: 'auto', 'font-size':'.9em'}, 
		               customBuilder: function (parent, idPrefix, name, uniqueIndex) {
		                   return _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex);
		                },
		                customSetter: function (idPrefix, name, uniqueIndex, value) {
		                    _appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value);
		                }	
	            },
	        ],
	        hideButtons: {
	        	append: true,
	        	removeLast: true,
	        	insert: true,
	            remove: true,
	            moveUp: true,
	            moveDown: true
	        },
	        
	        hideRowNumColumn: false
	}); 
	
   
	
	$('#extraSpareParts').appendGrid({
	    initRows: 0,
	    i18n: {rowEmpty:'<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.extraSpareParts.rowEmpty" />' },
	    columns: [
	            { name: 'accDescription', display:  '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.extraSpareParts.accDescription" />'   , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '300px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true } },
	            { name: 'accQuantity', display:   '<fmt:message key="jsp.js.JobOrderFollowupThirdPhase.extraSpareParts.accQuantity" />'  , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-digits': true } },
	           // { name: 'accPrice', display: 'سعر القطعة' , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '100px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: { 'data-rule-number': true } },
	        ]
	});
	

<%BioJobOrderSparePart josp = null;

if(sparePartsInQuotationCount > 0){
	  for (int i = 0 ; i < sparePartsInQuotationCount ;  i++){
		  josp = jobOrder.getSparePartsInsideQuotation().get(i);
		  out.print("$('#quotationSpareParts').appendGrid('appendRow', [{");
		  out.print("accDescription:'"+josp.getDescription()+"', accQuantity:'"+josp.getQuantity()+"', accPrice:'"+josp.getPrice()+"'");
		  out.print("}]);");
	  } 
}

if(sparePartsOutQuotationCount > 0){
	  for (int i = 0 ; i < sparePartsOutQuotationCount ;  i++){
		  josp = jobOrder.getSparePartsOutsideQuotation().get(i);
		  out.print("$('#extraSpareParts').appendGrid('appendRow', [{");
		  out.print("accDescription:'"+josp.getDescription()+"', accQuantity:'"+josp.getQuantity()+"'");
		  out.print("}]);");
	  } 
}%>
    
});

</script>
</body>
</html>
