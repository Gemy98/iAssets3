<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.biomedical.entity.*,com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
	// boolean isPPM = (Boolean)request.getAttribute("isPPM");
	BioPPMProgressBill ppmBill = (BioPPMProgressBill) request.getAttribute("lastPPMProgressBill");
	BioSparePartsProgressBill spBill = (BioSparePartsProgressBill) request.getAttribute("lastSparePartsProgressBill");
	boolean inUpdateMode = ppmBill != null || spBill != null;
	String sparePartsValue = Common.formatMoneyValue(WebUtil.getSessionSite(request).getBioSparePartsValue());
    Float remainedValueAfterLastSPPB = (Float) request.getAttribute("remainedValueAfterLastSPPB");
    Float payedValueAfterLastSPPB = (Float) request.getAttribute("payedValueAfterLastSPPB");
    String langCode = WebUtil.getSessionLanguage(request);
%>
</head>
<body>
<form method="post" action="bio/ProgressBillInfoProcess" enctype="multipart/form-data">
  <table class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"><%=(!inUpdateMode) ? LocalizationManager.getLiteral("jsp.ProgressBillInfo.pagetitle.inAddMode", langCode) : LocalizationManager.getLiteral("jsp.ProgressBillInfo.pagetitle.inUpdateMode", langCode) %></h1></td>
    </tr>
    <%if (!inUpdateMode){%>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.pbType" />:</td>
      <td><input type="radio" value="<%=Enums.PROGRESS_BILL_TYPE.PPM.getId()%>" name="pbType" required>
        <fmt:message key="jsp.ProgressBillInfo.pbType1" />
        <input type="radio" value="<%=Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()%>" name="pbType" required>
        <fmt:message key="jsp.ProgressBillInfo.pbType2" /></td>
    </tr>
    <tr>
       <td colspan="2">&nbsp;</td>
    </tr>
    <%}%>
    <%if(!inUpdateMode || ppmBill != null){%>
    <tbody id="ppm">
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.ProgressBillInfo.from" />:</td>
        <td><input type="text" id="from" name="from" class="caldr" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.ProgressBillInfo.to" /> :</td>
        <td><input type="text" id="to" name="to" class="caldr" required></td>
      </tr>
    </tbody>
    <%}%>
    <%if(!inUpdateMode || spBill != null){%>
    <tbody id="sp">
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.sparePartsValue" />:</td>
        <td><input type="text" id="sparePartsValue" name="sparePartsValue" disabled="disabled" data-rule-number="true">  <fmt:message key="jsp.ProgressBillInfo.pbValue.currency" /> </td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.remainedBudgetBeforePb" /> :</td>
        <td><input type="text" id="remainedBudgetBeforePb" name="remainedBudgetBeforePb" required data-rule-number="true" onblur="updateBudgetValues(this)">  <fmt:message key="jsp.ProgressBillInfo.pbValue.currency" /></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.payedBudgetBeforePb" /> :</td>
        <td><input type="text" id="payedBudgetBeforePb" name="payedBudgetBeforePb" required data-rule-number="true" onblur="updateBudgetValues(this)"><fmt:message key="jsp.ProgressBillInfo.pbValue.currency" /></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td class="side_label_top"> <fmt:message key="jsp.ProgressBillInfo.serialScan" />:</td>
        <td><input type="file" id="serialScan" name="serialScan" class="requiredFile"></td>
      </tr>
    </tbody>
    <%}%>
    <tbody id="common">
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.paymentNo" />:</td>
        <td><input type="text" id="paymentNo" name="paymentNo" required data-rule-integer="true" data-msg-remote='<fmt:message key="jsp.ProgressBillInfo.paymentNo.msg" />' ></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.pbValue" />:</td>
        <td><input type="text" id="pbValue" name="pbValue" required data-rule-number="true" onblur="updateBudgetValues(this)"> <fmt:message key="jsp.ProgressBillInfo.pbValue.currency" /></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.issueNo" />:</td>
        <td><input type="text" id="issueNo" name="issueNo" required data-msg-remote='<fmt:message key="jsp.ProgressBillInfo.issueNo.msg" />' ></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.ProgressBillInfo.issueDate" />:</td>
        <td><input type="text" id="issueDate" name="issueDate" required data-msg-remote='<fmt:message key="jsp.ProgressBillInfo.issueDate.msg" />' class="caldr" ></td>
      </tr>
    </tbody>
    <tr id="action_btns"> 
      <td colspan="2"><input type="submit" value='<%=(!inUpdateMode)?LocalizationManager.getLiteral("jsp.ProgressBillInfo.submitBtn.inAddMode", langCode) :LocalizationManager.getLiteral("jsp.ProgressBillInfo.submitBtn.inUpdateMode", langCode)%>'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
<script>  
	function revalidateRelatedFields(){
		//if($("#issueNo").val()){
			// alert("start validation");
			//if ($("#issueNo").valid() && $("#issueNo").valid()){;}
			// $("form").validate().element($("#issueNo"));
		//}	   
	}
	
    function updateBudgetValues(obj){
    	  
    	  var totalValue = parseFloat($("#sparePartsValue").val()) || 0;
    	  var pbValue = parseFloat($("#pbValue").val()) || 0;
    	  var remainedValue = parseFloat($("#remainedBudgetBeforePb").val()) || 0;
    	  var payedValue = parseFloat($("#payedBudgetBeforePb").val()) || 0;
    	  
    	  if(obj.id == "remainedBudgetBeforePb"){
    		  $("#payedBudgetBeforePb").val((totalValue - remainedValue).toFixed(2));
    	  } else if(obj.id == "payedBudgetBeforePb"){
    		  $("#remainedBudgetBeforePb").val((totalValue - payedValue).toFixed(2));
    	  } else if(obj.id == "pbValue"){ // only executed for sp progress bill
    		 // $("#remainedBudgetBeforePb").val(remainedValue -  pbValue);
    		 // $("#payedBudgetBeforePb").val(payedValue +  pbValue);
    	  }
      }
      
	  $(function(){
		  
		 <%if(!inUpdateMode || ppmBill != null){%>
		     $("#to").rules( "add", {
		 		dateGreaterThan: ['#from'],
				messages: {	dateGreaterThan:'<fmt:message key="jsp.js.ProgressBillInfo.dateGreaterThan" />'}
			 });
	     <%}%>
	     
		  $("#paymentNo").rules("add",{
			  remote:{
				  url:"bio/CheckProgressBillValidity",
				  data: {
					  //paymentNo: function(){return $("#paymentNo").val()},
					  pbId: function(){if($("#pbId").length) return $("#pbId").val(); return "0"},
					  pbType : function (){return ($("#pbType").val() || $("input[name='pbType']:checked").val())}
				         }
			          }
		  });
		  
		  $("#issueNo").rules("add",{
			   remote:{
				  url:"bio/CheckProgressBillValidity",
				  data: {
					  //issueNo: function(){return $("#issueNo").val()},
					  pbId: function(){if($("#pbId").length) return $("#pbId").val(); return "0"},
					  issueDate: function(){return $("#issueDate").val()}	
				         }
			          }
		  });
		  
		  $("#issueDate").rules("add",{
			  remote:{
				  url:"bio/CheckProgressBillValidity",
				  data: {		  
					  pbId: function(){if($("#pbId").length) return $("#pbId").val(); return "0"},
					  issueNo: function(){return $("#issueNo").val()},
					  //issueDate: function(){return $("#issueDate").val()},
					  pbType : function (){return ($("#pbType").val() || $("input[name='pbType']:checked").val())}
				         }
			          }
		  });
		  
		  // $('#issueDate').datepicker( 'option' , 'onClose', function() {revalidateRelatedFields()} );
		  
	   	  setTextFieldValue("sparePartsValue","<%=sparePartsValue%>"); 

		  <%if(!inUpdateMode){%>
		      var newPPMPB_PaymentNo = <%=(Integer)request.getAttribute("newPPMPB_PaymentNo")%>;
		      var newSPPB_PaymentNo = <%=(Integer)request.getAttribute("newSPPB_PaymentNo")%>; 
		      
		      function _onChangePBType(obj){
		    	  // clear common fields
		    	  $("tbody#common input[type='text']").val("");
		    	  markAsValid("tbody#common input[type='text']")
		    	  // set payment no serial
		    	  var selectedValue = $(obj).filter(':checked').val();
		    	  if(selectedValue == "<%=Enums.PROGRESS_BILL_TYPE.PPM.getId()%>")
		    	     $("#paymentNo").val(newPPMPB_PaymentNo);
		    	  else if(selectedValue == "<%=Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()%>")
		    	     $("#paymentNo").val(newSPPB_PaymentNo);
		      }
		
	    	  attachConditionalDisplayHandlerToRadio('pbType', {"<%=Enums.PROGRESS_BILL_TYPE.PPM.getId()%>":["ppm","common","action_btns"],"<%=Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()%>":["sp","common","action_btns"]});
	    	  $('input:radio[name="pbType"]').change(function(){_onChangePBType(this)});
	    	  setTextFieldValue("remainedBudgetBeforePb","<%=(remainedValueAfterLastSPPB == null) ? sparePartsValue : "" + remainedValueAfterLastSPPB%>");
		   	  setTextFieldValue("payedBudgetBeforePb","<%=(payedValueAfterLastSPPB== null) ? "0" : "" + payedValueAfterLastSPPB%>");
	      <%}else{%>
	      
		  <% if (ppmBill !=  null){
			    int id = ppmBill.getId(); 
		   		String from = DateUtil.getDateString(ppmBill.getFrom());
		   		String to = DateUtil.getDateString(ppmBill.getTo());
		   		String paymentNo = Common.getDisplayString(ppmBill.getProgressBill().getPaymentNo(),"");
		   		String pbValue = Common.formatMoneyValue(ppmBill.getProgressBill().getPbValue());
		   		String issueNo = Common.getDisplayString(ppmBill.getProgressBill().getIssueNo(),"");
		   		String issueDate = DateUtil.getDateString(ppmBill.getProgressBill().getIssueDate());
		  %>
	   		appendIdElement("ppmId","<%=id%>");
	   		appendIdElement("pbId","<%=ppmBill.getProgressBill().getId()%>");
	   		appendIdElement("pbType" , "<%=Enums.PROGRESS_BILL_TYPE.PPM.getId()%>" );
	   		setDateValue("from","<%=from%>");
	   		setDateValue("to","<%=to%>");
	   		setTextFieldValue("paymentNo","<%=paymentNo%>");
	   	    setTextFieldValue("pbValue","<%=pbValue%>");
	   		setTextFieldValue("issueNo","<%=issueNo%>"); 
	   		setDateValue("issueDate","<%=issueDate%>");

	   	 <% } else if(spBill != null) { 
		        int id = spBill.getId(); 
		   		String paymentNo = Common.getDisplayString(spBill.getProgressBill().getPaymentNo(),"");
		   		Float pbFloatValue = spBill.getProgressBill().getPbValue();
		   		String pbValue = Common.formatMoneyValue(pbFloatValue);
		   		String issueNo = Common.getDisplayString(spBill.getProgressBill().getIssueNo(),"");
		   		String issueDate = DateUtil.getDateString(spBill.getProgressBill().getIssueDate());
		   		String serialScan = spBill.getSerialScan();
		   		String remainedBudgetBeforePb = Common.formatMoneyValue(spBill.getRemainedBudgetAfterPB() + pbFloatValue);
		   		String payedBudgetBeforePb = Common.formatMoneyValue(spBill.getPayedBudgetAfterPB() - pbFloatValue);
		 %>
		    appendIdElement("spId","<%=id%>");
		    appendIdElement("pbId","<%=spBill.getProgressBill().getId()%>");
	   		appendIdElement("pbType" , "<%=Enums.PROGRESS_BILL_TYPE.SPARE_PARTS.getId()%>" );
	   		setTextFieldValue("paymentNo","<%=paymentNo%>");
	   		setTextFieldValue("pbValue","<%=pbValue%>");
	   		setTextFieldValue("issueNo","<%=issueNo%>");
	   	    setDateValue("issueDate","<%=issueDate%>");
			showUploadedFilesList("serialScan", <%=HtmlUtil.arrayFromJavaToJavaScript(serialScan)%>); 
	   		setTextFieldValue("remainedBudgetBeforePb","<%=remainedBudgetBeforePb%>");
	   		setTextFieldValue("payedBudgetBeforePb","<%=payedBudgetBeforePb%>");
		 <% }
		  } %>  
     });
</script>
</body>
</html>
