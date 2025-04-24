<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<%
	CommonSite sessionSite =  WebUtil.getSessionSite(request); 
	String langCode = WebUtil.getSessionLanguage(request);
	BioDashboardContractorEvalMeasures bioDashboardContractorEvalMeasures = (BioDashboardContractorEvalMeasures) request.getAttribute("bioDashboardContractorEvalMeasures");
	BioMonthlyContractorEvaluation bioMonthlyContractorEvaluation = (BioMonthlyContractorEvaluation) request.getAttribute("bioMonthlyContractorEvaluation");
	Boolean isUpdateMode = (Boolean) request.getAttribute("isUpdateMode");
%>
<form id="mainForm"  action="bio/MonthlyContractorEvaluationProcess" method="post">
  <table cellspacing="10"  cellpadding="10">
    <tr>
      <td colspan="2"><h1 class="page_title" id="page_title"><fmt:message key="jsp.MonthlyContractorEvaluation.pagetitleAdd" /></h1></td>
    </tr>
    
    <tr>
      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.month" /> : 
      	 <select id="month" name="month">
      	 	<c:forEach begin="1" end="12" varStatus="loop">
              <option value="${loop.index}">${loop.index}</option>
			</c:forEach>
          </select>
       </td>
       <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.year" /> : 
      	 <select id="year" name="year">
      	 	<c:forEach begin="${ startYear }" end="${ endYear }" varStatus="loop">
            <option value="${loop.index}">${loop.index}</option>
			</c:forEach>
          </select>
       </td>
    </tr>
  </table>
  <table class="layout_grid" id="mainFormTable" style="visibility: ${displayTable}">
<!-- 	    <tr> -->
<%-- 	      <td colspan="2"><h2> <fmt:message key="jsp.MonthlyContractorEvaluation.numUnassignedEmployee" /></h2></td> --%>
<!-- 	    </tr> -->
	
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnassignedEngineers" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfUnassignedEngineers" name="numOfUnassignedEngineers" data-rule-digits="true" required="required" size="10" > -->
<!-- 	      <h4 style="display: inline" id="numOfContractEngineers"></h4></td> -->
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnassignedTechnicians" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfUnassignedTechnicians" name="numOfUnassignedTechnicians" data-rule-digits="true" required="required" size="10" > -->
<!-- 	       <h4 style="display: inline" id="numOfContractTechnicians"></h4></td> -->
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnassignedChemists" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfUnassignedChemists" name="numOfUnassignedChemists" data-rule-digits="true" required="required" size="10" > -->
<!-- 	      <h4 style="display: inline" id="numOfContractChemists"></h4></td> -->
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnassignedItEmployees" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfUnassignedItEmployees" name="numOfUnassignedItEmployees" data-rule-digits="true" required="required" size="10" > -->
<!-- 	        <h4 style="display: inline" id="numOfContractItEmployees"></h4></td> -->
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnassignedDrivers" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfUnassignedDrivers" name="numOfUnassignedDrivers" data-rule-digits="true" required="required" size="10" > -->
<!-- 	       <h4 style="display: inline" id="numOfContractDrivers"></h4></td> -->
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.totalNumOfUnassignedEmployees" /> :</td> --%>
<!-- 	      <td><input type="text" id="totalNumOfUnassignedEmployees" name="totalNumOfUnassignedEmployees" data-rule-digits="true" readonly="readonly" size="10" > -->
<!-- 	     <h4 style="display: inline" id="totalNumOfContractEmployees"></h4></td> -->
<!-- 	    </tr> -->
	    
	    
<!-- 	    <tr> -->
<%-- 	      <td colspan="2"><h2> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceDays" /></h2></td> --%>
<!-- 	    </tr> -->
	    
<!-- 	     <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceEngineers" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfAbsenceEngineers" name="numOfAbsenceEngineers" data-rule-digits="true" required="required" size="10" > -->
<%-- 	       <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceTechnicians" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfAbsenceTechnicians" name="numOfAbsenceTechnicians" data-rule-digits="true" required="required" size="10" > -->
<%-- 	      <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceChemists" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfAbsenceChemists" name="numOfAbsenceChemists" data-rule-digits="true" required="required" size="10" > -->
<%-- 	      <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceItEmployees" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfAbsenceItEmployees" name="numOfAbsenceItEmployees" data-rule-digits="true" required="required" size="10" > -->
<%-- 	      <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfAbsenceDrivers" /> :</td> --%>
<!-- 	      <td><input type="text" id="numOfAbsenceDrivers" name="numOfAbsenceDrivers" data-rule-digits="true" required="required" size="10" > -->
<%-- 	      <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
<!-- 	    <tr> -->
<%-- 	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.totalNumOfAbsenceEmployees" /> :</td> --%>
<!-- 	      <td><input type="text" id="totalNumOfAbsenceEmployees" name="totalNumOfAbsenceEmployees" data-rule-digits="true" readonly="readonly" size="10" > -->
<%-- 	      <fmt:message key="jsp.MonthlyContractorEvaluation.day" /> </td> --%>
<!-- 	    </tr> -->
	   
<!-- 	    <tr> -->
<%-- 	      <td colspan="2"><h2> <fmt:message key="jsp.MonthlyContractorEvaluation.paidEmployess" /></h2></td> --%>
<!-- 	    </tr> -->
	    
	     <tr>
	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUnpaidEmployess" /> :</td>
	      <td><input type="text" id="numOfUnpaidEmployess" name="numOfUnpaidEmployess" data-rule-digits="true" required="required" size="10" ></td>
	    </tr>
	
<!-- 	    <tr> -->
<%-- 	      <td colspan="2"><h2> <fmt:message key="jsp.MonthlyContractorEvaluation.committedEmployess" /></h2></td> --%>
<!-- 	    </tr> -->
	    
	    <tr>
	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.totalNumOfAbsenceEmployees" /> :</td>
	      <td><h4 style="display: inline" id="totalNumOfAbsenceEmployees"></h4></td>
	    </tr>

	     <tr>
	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfUncommittedEmployess" /> :</td>
	      <td><h4 style="display: inline" id="numOfUncommittedEmployess"></h4></td>
	    </tr>
	
<!-- 	    <tr> -->
<%-- 	      <td colspan="2"><h2> <fmt:message key="jsp.MonthlyContractorEvaluation.pcSuspention" /></h2></td> --%>
<!-- 	    </tr> -->
	    
	     <tr>
	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfPcSuspended" /> :</td>
	      <td><h4 style="display: inline" id="numOfPcSuspended"></h4></td>
	    </tr>

		<tr>
	      <td class="side_label_top"> <fmt:message key="jsp.MonthlyContractorEvaluation.numOfComplains" /> :</td>
	      <td><h4 style="display: inline" id="numOfComplains"></h4></td>
	    </tr>
	
	
	    <tr>
	      <td colspan="2">
	      	<input id="submitButtn" type="submit" value='<fmt:message key="jsp.MonthlyContractorEvaluation.saveBtn" />'>
	        <%=HtmlUtil.getResetButtonHTML(langCode)%> 
	      </td>
	    </tr>
  </table>
</form>

<script  type="text/javascript">

$("#year, #month").on('change', function() {
	
// 	$('#mainFormTable').css({"visibility": "collapse"});
	
	var year = $('#year').val();
	var month = $('#month').val();
	
	if (year == "" || month == ""){
		$('#mainFormTable').css({"visibility": "collapse"});
		return;
	}
	
	var formObj = $("form:first");
	formObj.validate().cancelSubmit = true;
	formObj.attr("action", "bio/MonthlyContractorEvaluationDisplay");
	formObj.submit();
	
// 	 $.ajax({
// 		 url: "bio/MonthlyContractorEvaluationDisplay?ajaxMode=1&year="+year+"&month="+month, 
// 		 success: function(result) {
			 
// 			if (result == "") {
// 				return;
// 			}
			
// 			var jsonResult = JSON.parse(result);
			
// 			setTextFieldValue("numOfUnassignedEngineers", jsonResult.numOfUnassignedEngineers);
// 			setTextFieldValue("numOfUnassignedTechnicians", jsonResult.numOfUnassignedTechnicians);
// 			setTextFieldValue("numOfUnassignedChemists", jsonResult.numOfUnassignedChemists);
// 			setTextFieldValue("numOfUnassignedItEmployees", jsonResult.numOfUnassignedItEmployees);
// 			setTextFieldValue("numOfUnassignedDrivers", jsonResult.numOfUnassignedDrivers);
// 			setTextFieldValue("totalNumOfUnassignedEmployees", jsonResult.totalNumOfUnassignedEmployees);	
			
// 			setInnerHTML("numOfContractEngineers", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.numOfContractEngineers + ")");
// 			setInnerHTML("numOfContractTechnicians", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.numOfContractTechnicians + ")");
// 			setInnerHTML("numOfContractChemists", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.numOfContractChemists + ")");
// 			setInnerHTML("numOfContractItEmployees", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.numOfContractItEmployees + ")");
// 			setInnerHTML("numOfContractDrivers", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.numOfContractDrivers + ")");
// 			setInnerHTML("totalNumOfContractEmployees", "(" + '<fmt:message key="jsp.MonthlyContractorEvaluation.numPerContract" />' + " : " + jsonResult.totalNumOfContractEmployees + ")");

// 			setTextFieldValue("numOfAbsenceEngineers", jsonResult.numOfAbsenceEngineers);
// 			setTextFieldValue("numOfAbsenceTechnicians", jsonResult.numOfAbsenceTechnicians);
// 			setTextFieldValue("numOfAbsenceChemists", jsonResult.numOfAbsenceChemists);
// 			setTextFieldValue("numOfAbsenceItEmployees", jsonResult.numOfAbsenceItEmployees);
// 			setTextFieldValue("numOfAbsenceDrivers", jsonResult.numOfAbsenceDrivers);
// 			setTextFieldValue("totalNumOfAbsenceEmployees", jsonResult.totalNumOfAbsenceEmployees);
			
// 			setTextFieldValue("numOfUnpaidEmployess", jsonResult.numOfUnpaidEmployess);
// 			setInnerHTML("totalNumOfAbsenceEmployees", jsonResult.totalNumOfAbsenceEmployees);
// 			setInnerHTML("numOfUncommittedEmployess", jsonResult.numOfUncommittedEmployess);
// 			setInnerHTML("numOfPcSuspended", jsonResult.numOfPcSuspended);
			
// 			setInnerHTML("numOfSubcontractor", jsonResult.numOfSubcontractor);
// 			setInnerHTML("numOfComplains", jsonResult.numOfComplains);
			
// 			if( jsonResult.isUpdateMode){
// 				setInnerHTML('page_title', '<fmt:message key="jsp.MonthlyContractorEvaluation.pagetitleEdit" />')
// 				$("#submitButtn").prop('value', '<fmt:message key="jsp.MonthlyContractorEvaluation.editBtn" />');
// 			} else {
// 				setInnerHTML('page_title', '<fmt:message key="jsp.MonthlyContractorEvaluation.pagetitleAdd" />')
// 				$("#submitButtn").prop('value', '<fmt:message key="jsp.MonthlyContractorEvaluation.saveBtn" />');
// 			}
// 			$('#mainFormTable').css({"visibility": "visible"});
			
// 	    }});
	
});

	$( "#mainForm" ).validate({
	  rules: {
	    field: {
	      required: true,
	      digits: true
	    }
	  }
	});

//    $("#numOfUnassignedEngineers, #numOfUnassignedTechnicians, #numOfUnassignedChemists, #numOfUnassignedItEmployees, #numOfUnassignedDrivers").blur(function (){
// 	   var totalSum = 0;

// 	   var val = $('#numOfUnassignedEngineers').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));

// 	   val = $('#numOfUnassignedTechnicians').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));

// 	   val = $('#numOfUnassignedChemists').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   val = $('#numOfUnassignedItEmployees').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   val = $('#numOfUnassignedDrivers').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   $('#totalNumOfUnassignedEmployees').val(totalSum);
//    });
   
//    $("#numOfAbsenceEngineers, #numOfAbsenceTechnicians, #numOfAbsenceChemists, #numOfAbsenceItEmployees, #numOfAbsenceDrivers").blur(function (){
// 	   var totalSum = 0;

// 	   var val = $('#numOfAbsenceEngineers').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));

// 	   val = $('#numOfAbsenceTechnicians').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));

// 	   val = $('#numOfAbsenceChemists').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   val = $('#numOfAbsenceItEmployees').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   val = $('#numOfAbsenceDrivers').val();
// 	   totalSum += (isNaN(parseInt(val)) ? 0 : parseInt(val));
	   
// 	   $('#totalNumOfAbsenceEmployees').val(totalSum);
//    });
	   
	<%if (  bioDashboardContractorEvalMeasures != null) { %>
			setInnerHTML("totalNumOfAbsenceEmployees", <%= bioDashboardContractorEvalMeasures.getSumTotalAbsentEmpNo() %>);
			setInnerHTML("numOfUncommittedEmployess", <%= bioDashboardContractorEvalMeasures.getSumUniformViolentEmpNo() %>);
			setInnerHTML("numOfPcSuspended", <%=  bioDashboardContractorEvalMeasures.getSumPcSuspended() %>);
			setInnerHTML("numOfComplains", <%= bioDashboardContractorEvalMeasures.getSumSuppliersComplainsNo() %>);
			$('#mainFormTable').css({"visibility": "visible"});
	<%} %>
	
	<%if (isUpdateMode != null && isUpdateMode) { %>
			setInnerHTML('page_title', '<fmt:message key="jsp.MonthlyContractorEvaluation.pagetitleEdit" />')
			$("#submitButtn").prop('value', '<fmt:message key="jsp.MonthlyContractorEvaluation.editBtn" />');
	<%	} else { %>
			setInnerHTML('page_title', '<fmt:message key="jsp.MonthlyContractorEvaluation.pagetitleAdd" />')
			$("#submitButtn").prop('value', '<fmt:message key="jsp.MonthlyContractorEvaluation.saveBtn" />');
	<%} %>
		

	    setComboSelectedValue("month","${month}");
	
	    setComboSelectedValue("year","${year}");
    

	<%if (  bioMonthlyContractorEvaluation != null) { %>
				setTextFieldValue("numOfUnpaidEmployess", <%= bioMonthlyContractorEvaluation.getUnpaidEmpNo() %>);
	<%} %>
</script>

</body>
</html>
