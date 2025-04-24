<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%
	String langCode = WebUtil.getSessionLanguage(request);
	BioDailyContractorEvaluation bioDailyContractorEvaluation = (BioDailyContractorEvaluation) request.getAttribute("bioDailyContractorEvaluation");
%>
</head>
<body>
	<form method="post" action="bio/DailyContractorEvaluationProcess">
	  <table class="layout_grid">
	  
		  <tr>
	        <td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.contractor.eval7" />  </h1></td>
	      </tr>
	      <tr>
	        <td class="side_label_middle"> <fmt:message key="jsp.date" />:</td>
	        <td><input type="text" id="dayDate" name="dayDate" onchange="getDailyAvalation()"></td>
	      </tr>
	  
	      <tr>
	      <td colspan="2"><h2><fmt:message key="jsp.absence" /> :</h2></td>
	    </tr>
	    
	    <tr>
	        <td class="side_label_middle">  <fmt:message key="jsp.absence.employee" />  :</td>
	        <td><input type="radio" value="1" name="absenceEmployee" required>
	          <fmt:message key="jsp.exist" /> 
	          <input type="radio" value="0" name="absenceEmployee" checked="checked" required>
	          <fmt:message key="jsp.not.exist" /> </td>
	      </tr>
	    
	    <tbody id="grp1">
	    
	      <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.eng.no" />  :</td>
	        <td><input type="text" id="engNo" name="engNo" size="10" required data-rule-digits="true" onchange="sumTotal()"></td>
	      </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.teck.no" />  :</td>
	        <td><input type="text" id="teckNo" name="teckNo" size="10" required data-rule-digits="true" onchange="sumTotal()"></td>
	      </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.chem.no" />  :</td>
	        <td><input type="text" id="chemNo" name="chemNo" size="10" required data-rule-digits="true" onchange="sumTotal()"></td>
	      </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.comp.no" />  :</td>
	        <td><input type="text" id="compNo" name="compNo" size="10" required data-rule-digits="true" onchange="sumTotal()"></td>
	      </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.driver.no" />  :</td>
	        <td><input type="text" id="driverNo" name="driverNo" size="10" required data-rule-digits="true" onchange="sumTotal()"></td>
	      </tr>
	      
	      <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.absence.total.employees" />  :</td>
	        <td id="total">0</td>
	      </tr>
	      
	      </tbody>
	    
	    <tr>
	      <td colspan="2"><h2><fmt:message key="jsp.MonthlyContractorEvaluation.committedEmployess" /> :</h2></td>
	    </tr>
	    
	      <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.contractor.eval5" />  :</td>
	        <td><input type="radio" value="1" name="commitmitEmployee" required>
	          <fmt:message key="jsp.exist" /> 
	          <input type="radio" value="0" name="commitmitEmployee" checked="checked" required>
	          <fmt:message key="jsp.not.exist" /> </td>
	      </tr>  
	    
	     <tr id="grp2">
	        <td class="side_label_middle"><fmt:message key="jsp.contractor.eval6" />  :</td>
	        <td><input type="text"  id="nonComplainceNo" name="nonComplainceNo" size="10" required data-rule-digits="true"></td>
	      </tr>
	    
	     <tr>
	      <td colspan="2"><h2><fmt:message key="jsp.contractor.eval1" /> :</h2></td>
	    </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.contractor.eval2" />  :</td>
	        <td><input type="radio" value="1" name="complainSupplier" required>
	          <fmt:message key="jsp.exist" /> 
	          <input type="radio" value="0" name="complainSupplier" checked="checked" required>
	          <fmt:message key="jsp.not.exist" /> </td>
	      </tr>  
	    
	     <tr id="grp3">
	        <td class="side_label_middle"><fmt:message key="jsp.contractor.eval3" />  :</td>
	        <td><input type="text"  id="complainNo" name="complainNo" size="10" required data-rule-digits="true"></td>
	      </tr>
	      
	    <tr>
	      <td colspan="2"><h2><fmt:message key="jsp.MonthlyContractorEvaluation.pcSuspention" /> :</h2></td>
	    </tr>
	      
	       <tr>
	        <td class="side_label_middle"><fmt:message key="jsp.contractor.eval4" />  :</td>
	        <td><input type="radio" value="1" name="deviceSuspend" checked="checked" required>
	          <fmt:message key="jsp.yes" /> 
	          <input type="radio" value="0" name="deviceSuspend" required>
	          <fmt:message key="jsp.no" /> </td>
	      </tr>  
	      
	    <tr> 
	      <td colspan="2">
		      <input type="submit" value='<%=LocalizationManager.getLiteral("jsp.contractor.eval8", langCode)%>'> 
		      <%=HtmlUtil.getResetButtonHTML(langCode)%>
	      </td>
	    </tr>
	    
	  </table>
  </form>
  
  <script type="text/javascript">
$(function(){
	
	 $( "#dayDate" ).datepicker({ maxDate: 0,
		 	dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : "-30:+5",
			beforeShow : function(input, inst) {if($(input).hasClass("noteditable")) return false;}
		}).attr("readonly","true");

	
	<%
		if(bioDailyContractorEvaluation != null) {
		
			String engNo = "" + bioDailyContractorEvaluation.getAbsBioengNo(); 
			String chemNo = "" + bioDailyContractorEvaluation.getAbsBiochemNo(); 
			String teckNo = "" + bioDailyContractorEvaluation.getAbsBiotechNo(); 
			String driverNo = "" + bioDailyContractorEvaluation.getAbsDriverNo(); 
			String itEmpNo = "" + bioDailyContractorEvaluation.getAbsItempNo(); 
			String complainNo ="" + bioDailyContractorEvaluation.getSuppliersComplainsNo(); 
			String evalDate = DateUtil.getDateString(bioDailyContractorEvaluation.getEvalDate());
			String uniformViolentEmpNo = "" + bioDailyContractorEvaluation.getUniformViolentEmpNo(); 
	%>
			setTextFieldValue("engNo","<%= engNo %>");
			setTextFieldValue("teckNo","<%=teckNo%>");
			setTextFieldValue("chemNo","<%=chemNo%>");
			setTextFieldValue("compNo","<%=itEmpNo%>");
			setTextFieldValue("driverNo","<%=driverNo%>");
			setTextFieldValue("complainNo","<%=complainNo%>");
			setTextFieldValue("nonComplainceNo","<%=uniformViolentEmpNo%>");
			
	 		setDateValue("dayDate","<%=evalDate%>");

			setRadioCheckedValue("absenceEmployee","<%= bioDailyContractorEvaluation.getEmployeeAbsent() %>");
			setRadioCheckedValue("commitmitEmployee","<%= bioDailyContractorEvaluation.getEmployeeCommitmit() %>");
			setRadioCheckedValue("complainSupplier","<%= bioDailyContractorEvaluation.getSupplierComplain() %>");
			setRadioCheckedValue("deviceSuspend","<%= bioDailyContractorEvaluation.getPcSuspended() %>");
	<%}%>
	
	
	 attachConditionalDisplayHandlerToRadio('absenceEmployee', {"1":"grp1"});
	 attachConditionalDisplayHandlerToRadio('commitmitEmployee', {"1":"grp2"});
	 attachConditionalDisplayHandlerToRadio('complainSupplier', {"1":"grp3"});
	 sumTotal();
})

function getDailyAvalation() {
	var formObj = $("form:first");
	formObj.validate().cancelSubmit = true;
	formObj.attr("action", "bio/DailyContractorEvaluationDisplay");
	formObj.submit();
}

function sumTotal() {
	var s=Number($("input[name='engNo']").val()) + Number($("input[name='teckNo']").val()) +
	Number($("input[name='chemNo']").val()) + Number($("input[name='compNo']").val())+
	Number($("input[name='driverNo']").val());
	setInnerHTML("total", s);
	
}

</script>
  
</body>
</html>