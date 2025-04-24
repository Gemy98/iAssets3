<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<!-- <link rel="stylesheet" type="text/css" href="./jquery.datetimepicker.css"/> -->
<link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<!-- <script src="./jquery.datetimepicker.js"></script> -->
<script src="https://raw.githubusercontent.com/xdan/datetimepicker/master/build/jquery.datetimepicker.full.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.5.20/jquery.datetimepicker.css">

</head>



<body>
<%
    String langCode = WebUtil.getSessionLanguage(request);
    CommonSite sessionSite =  WebUtil.getSessionSite(request); 
 	String takeoverReportScanFiles = sessionSite.getBioTakeoverReportScan();
%>

<form action="bio/ManageContractInfoProcess" method="post" enctype="multipart/form-data" dir="${sessionScope.direction}">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.ManageContractInfo.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.contractStartDate" /> :</td>
      <td><label id="contractStartDate"></label></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.contractEndDate" /> :</td>
      <td><label id="contractEndDate"></label></td>
    </tr>
    
    <tr>
      <td colspan="2"><h2> <fmt:message key="jsp.ManageContractInfo.numOfEmployeesPerContract" /></h2></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfEngineers" /> :</td>
      <td><input type="text" id="numOfEngineers" name="numOfEngineers" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfTechnicians" /> :</td>
      <td><input type="text" id="numOfTechnicians" name="numOfTechnicians" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfChemists" /> :</td>
      <td><input type="text" id="numOfChemists" name="numOfChemists" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfItEmployees" /> :</td>
      <td><input type="text" id="numOfItEmployees" name="numOfItEmployees" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfDrivers" /> :</td>
      <td><input type="text" id="numOfDrivers" name="numOfDrivers" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.totalNumOfEmployees" /> :</td>
      <td><input type="text" id="totalNumOfEmployees" name="totalNumOfEmployees" data-rule-digits="true" readonly="readonly" size="10"></td>
    </tr>
    
    
    <tr>
      <td colspan="2"><h2> <fmt:message key="jsp.ManageContractInfo.numOfSuppliers" /></h2></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfAgents" /> :</td>
      <td><input type="text" id="numOfAgents" name="numOfAgents" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfSubContractors" /> :</td>
      <td><input type="text" id="numOfSubContractors" name="numOfSubContractors" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.ManageContractInfo.numOfOtherSuppliers" /> :</td>
      <td><input type="text" id="numOfOtherSuppliers" name="numOfOtherSuppliers" data-rule-digits="true" required="required" size="10"></td>
    </tr>
    
   <%-- <tr>
        <td class="side_label_middle">  <fmt:message key="jsp.ManageContractInfo.workTimeType" /> :</td>
        <td>
        	<input type="radio" value="1" id="fullTimeSite" name="workTimeType" required="required">
           <fmt:message key="jsp.ManageContractInfo.fullTimeSite" />
          	<input type="radio" value="0" id="partTimeSite" name="workTimeType" required="required">
           <fmt:message key="jsp.ManageContractInfo.partTimeSite" /> 
           
<!--            <input type="text" class="form_datetime" size="3" > -->
        </td>
    </tr> --%>
<!--       </tr> -->
<!-- 		<tr id="workTimeTR" style="display: none;" > -->
<!-- 	      <td class="side_label_middle"> -->
<%-- 	           <fmt:message key="jsp.ManageContractInfo.workTimeFrom" />  --%>
<!-- 	      </td> -->
<!-- 	          <td><input type="text" id="workTimeFrom" name="workTimeFrom" required="required" data-rule-digits="true" class="form_datetime" size="3" ></td> -->
<!-- 	      <td class="side_label_middle"> -->
<%-- 	          <fmt:message key="jsp.ManageContractInfo.workTimeTo" /> </td> --%>
<!-- 	          <td><input type="text" id="workTimeTo" name="workTimeTo" required="required" data-rule-digits="true" class="form_datetime" size="3" ></td> -->
<!--       </tr> -->
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.UploadTakeOverReport.bioTakeoverReportScanUrl" /> :</td>
      <td><input type="file" name="bioTakeoverReportScanUrl" id="bioTakeoverReportScanUrl" multiple="multiple" class="requiredFile" /></td>
    </tr>
    <tr>
      <td colspan="2">
      	<input type="submit" value='<fmt:message key="jsp.ManageContractInfo.submitBtn" />'>
      	<%=HtmlUtil.getResetButtonHTML(langCode)%>
      </td>
    </tr>
  </table>
</form>


<script  type="text/javascript">
  <%
	   String contractStartDate = DateUtil.getDateString(sessionSite.getBioContractStartDate()); 
	   String contractEndDate = DateUtil.getDateString(sessionSite.getBioContractEndDate()); 
	   
	   String numOfEngineers = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfEngineers(),"");
	   String numOfTechnicians = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfTechnicians(),"");
	   String numOfChemists = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfChemists(),"");
	   String numOfItEmployees = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfItEmployees(),"");
	   String numOfDrivers = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfDrivers(),"");
	   String totalNumOfEmployees = Common.getDisplayString(sessionSite.getBioSiteContract().getTotalNumOfEmployees(),"");
	   
	   String numOfAgents = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfAgents(),"");
	   String numOfSubContractors = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfSubContractors(),"");
	   String numOfOtherSuppliers = Common.getDisplayString(sessionSite.getBioSiteContract().getNumOfOtherSuppliers(),"");
	   
	   Boolean workTimeType = sessionSite.getBioSiteContract().getWorkTimeType();

	   String workTimeFrom = Common.getDisplayString(sessionSite.getBioSiteContract().getWorkTimeFrom(),"");
	   String workTimeTo = Common.getDisplayString(sessionSite.getBioSiteContract().getWorkTimeTo(),"");
	   
   %>
   $(function(){ 
	   
	   setInnerHTML("contractStartDate","<%=contractStartDate%>");
	   setInnerHTML("contractEndDate","<%=contractEndDate%>");
	   
	   setTextFieldValue("numOfEngineers","<%=numOfEngineers%>");
	   setTextFieldValue("numOfTechnicians","<%=numOfTechnicians%>");
	   setTextFieldValue("numOfChemists","<%=numOfChemists%>");
	   setTextFieldValue("numOfItEmployees","<%=numOfItEmployees%>");
	   setTextFieldValue("numOfDrivers","<%=numOfDrivers%>");
	   setTextFieldValue("totalNumOfEmployees","<%=totalNumOfEmployees%>");
	   
	   setTextFieldValue("numOfAgents","<%=numOfAgents%>");
	   setTextFieldValue("numOfSubContractors","<%=numOfSubContractors%>");
	   setTextFieldValue("numOfOtherSuppliers","<%=numOfOtherSuppliers%>");
	   
	   setRadioCheckedValue("workTimeType",'<%=(workTimeType == null || workTimeType ? "1" : "0")%>');
		$('input:radio[name="workTimeType"]:checked').change();
	   <%-- setTextFieldValue("workTimeFrom","<%=workTimeFrom%>");
	   setTextFieldValue("workTimeTo","<%=workTimeTo%>"); --%>
	   
	   showUploadedFilesList("bioTakeoverReportScanUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(takeoverReportScanFiles)%>);
	
	   $("#numOfEngineers, #numOfTechnicians, #numOfChemists, #numOfItEmployees, #numOfDrivers").blur(function (){
		   var totalSum = 0;
	
		   var val = $('#numOfEngineers').val();
		   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	
		   val = $('#numOfTechnicians').val();
		   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	
		   val = $('#numOfChemists').val();
		   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
		   
		   val = $('#numOfItEmployees').val();
		   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
		   
		   val = $('#numOfDrivers').val();
		   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
		   
		   $('#totalNumOfEmployees').val(totalSum);
	   });
	   
// 	   $('#fullTimeSite').click(function() {
// 		      $('#workTimeTR').hide();
// 		      $('#workTimeFrom').attr('disabled', true);
// 		      $('#workTimeTo').attr('disabled', true);
// 		});
	
// 	   $('#partTimeSite').click(function() {
// 		      $('#workTimeTR').show();
// 		      $('#workTimeFrom').attr('disabled', false);
// 		      $('#workTimeTo').attr('disabled', false);
// 		});
	   
// 	   $('.form_datetime').datetimepicker({
// 			datepicker:false,
// 			format:'H:i',
// 			step:30
// 		});
   
 });
   
   
</script>
</body>
</html>
