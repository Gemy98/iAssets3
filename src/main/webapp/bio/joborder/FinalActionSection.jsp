<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.biomedical.util.HtmlUtil,com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	BioJobOrder finalSecJobOrderInfo = (BioJobOrder) request.getAttribute(Default.JOB_ORDER_INFO_ATTR_NAME);
    boolean joLocationIsHealthCenter = finalSecJobOrderInfo.getHospitalDevice().getHospital().getHealthCenter();//WebUtil.getSessionLocation(request).getHealthCenter();
	String finalAction = Common.getDisplayString(finalSecJobOrderInfo.getFinalAction(), "");
	String finalActionDate = DateUtil.getDateString(finalSecJobOrderInfo.getFinalActionDate());
	String finalAgentReportFiles = finalSecJobOrderInfo.getFinalAgentReportUrl();
	String finalReportFiles = finalSecJobOrderInfo.getFinalReportUrl();
%>
<html>
<head>
<script>

function _onClickPrepareForPrintingButton(btnObj){
	
	if($(btnObj).is(":checked")){
		//$("#actualCloseDate").datepicker( "setDate", new Date());
		$("#print_button_container").show();
		<%if(!joLocationIsHealthCenter){%>
			// markAsRequired("#joRequestFormUrl");
		<%}%>
		markAsRequired("#finalAction");
		markAsRequired("#finalActionDate");
		markAsRequired("#finalAgentReportUrl");
		//markAsRequired("#finalReportUrl");
	}else{
		//$("#actualCloseDate").val("");
		$("#print_button_container").hide();
		if(! $("#closed").is(":checked")){
			<%if(!joLocationIsHealthCenter){%>
				// markAsNotRequired("#joRequestFormUrl");
			<%}%>
			markAsNotRequired("#finalAction");
			markAsNotRequired("#finalActionDate");
			markAsNotRequired("#finalAgentReportUrl");
			//markAsNotRequired("#finalReportUrl");
		}
	}
}

function _onClickCloseButton(btnObj){
	
	if($(btnObj).is(":checked")){
		$("#actualCloseDate").datepicker( "setDate", new Date());
		$("#close_date_container").show();
		<%if(!joLocationIsHealthCenter){%>
			// markAsRequired("#joRequestFormUrl");
		<%}%>
		markAsRequired("#finalAction");
		markAsRequired("#finalActionDate");
		markAsRequired("#finalAgentReportUrl");
		markAsRequired("#finalReportUrl");
	}else{
		$("#actualCloseDate").val("");
		$("#close_date_container").hide();
		if(! $("#prepareForPrinting").is(":checked")){
			<%if(!joLocationIsHealthCenter){%>
				// markAsNotRequired("#joRequestFormUrl");
			<%}%>
			markAsNotRequired("#finalAction");
			markAsNotRequired("#finalActionDate");
			markAsNotRequired("#finalAgentReportUrl");
		}
		markAsNotRequired("#finalReportUrl");
	}
}

$(function(){
	$("#finalActionDate" ).rules( "add", {
		dateGreaterThan: ["#firstActionDate","#spTransDate","#secondActionDate"],
		messages: {	dateGreaterThan: '<fmt:message key="jsp.js.FinalActionSection.dateGreaterThan" />' }
	});
	
	$("#print_button_container").hide();
	
	<%if (WebUtil.userHasRightToCloseJobOrder(request)) {%>
		$("#close_date_container").hide();

		$("#actualCloseDate" ).rules( "add", {
			dateGreaterThan: ["#finalActionDate"],
			messages: {	dateGreaterThan: '<fmt:message key="jsp.js.FinalActionSection.dateGreaterThan2" />'}
		});
   <%}%>
	
    setInnerHTML("finalAction","<%=finalAction%>");
    setDateValue("finalActionDate","<%=finalActionDate%>");
	showUploadedFilesList("finalAgentReportUrl",<%=HtmlUtil.arrayFromJavaToJavaScript(finalAgentReportFiles)%>);
	showUploadedFilesList("finalReportUrl",<%=HtmlUtil.arrayFromJavaToJavaScript(finalReportFiles)%>);
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<!-- <table class="layout_grid"> -->
	<tbody id="final_section_contents">
		<tr>
			<td class="side_label_top"> <fmt:message key="jsp.FinalActionSection.finalAction" />:</td>
			<td><textarea name="finalAction" id="finalAction"></textarea></td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message key="jsp.FinalActionSection.finalActionDate" /> :</td>
			<td><input type="text" name="finalActionDate"
				id="finalActionDate" class="caldr"></td>
		</tr>
		<%
			if (finalSecJobOrderInfo.getAgentMustAttend()) {
		%>
		<tr>
			<td class="side_label_top"><fmt:message key="jsp.FinalActionSection.finalAgentReportUrl" />:</td>
			<td><input type="file" name="finalAgentReportUrl" id="finalAgentReportUrl" multiple></td>
		</tr>
		<%
			}
		%>

  <!-- 
        <tr>
			<td class="side_label_top">تقرير أمر العمل النهائي :</td>
			<td><input type="file" name="finalReportUrl" id="finalReportUrl" multiple></td>
		</tr>
  -->
		
		<!-- final job order printing problem fix -->
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" class="side_label_middle" style="text-align: right">
				<input name="prepareForPrinting" id="prepareForPrinting" type="checkbox" value="1"
				onclick="_onClickPrepareForPrintingButton(this)"> <fmt:message key="jsp.FinalActionSection.prepareForPrintingButton" />
			</td>
		</tr>
		
		<tr id="print_button_container">
			<td colspan="2">
			   <input style="margin-bottom: 10px;margin-top: 10px;background-color: #000;font-weight: bold" type='button' onclick='_onClickPrintJobOrderBtn()' value='<fmt:message key="jsp.FinalActionSection.printJobOrderBtn" />' />
			</td>
		</tr>
		
		<!-- end of final job order printing problem fix -->
		
		<%
			if (WebUtil.userHasRightToCloseJobOrder(request)) {
		%>	
		<tr>
			<td colspan="2" class="side_label_middle" style="text-align: right">
				<input name="closed" id="closed" type="checkbox" value="1"
				onclick="_onClickCloseButton(this)"> <fmt:message key="jsp.FinalActionSection.closeButton" />
			</td>
		</tr>
		<tbody id="close_date_container">
			<tr>
				<td class="side_label_top"><fmt:message key="jsp.FinalActionSection.finalReportUrl" /> :</td>
				<td><input type="file" name="finalReportUrl" id="finalReportUrl" multiple></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.FinalActionSection.actualCloseDate" />:</td>
				<td><input type="text" name="actualCloseDate" id="actualCloseDate" class="caldr" required></td>
			</tr>
		</tbody>
		<%
			}
		%>
		<%-- 
		<tr>
			<td colspan="2">
			  <%
			  out.print(HtmlUtil.getSaveThenReturnButtonHTML());
			  
			 // if(finalSecJobOrderInfo.getLastCompletedPhaseId() < Enums.JOBORDER_FOLLOWUP_PHASES.SECOND_ACTION_TAKEN.getId() && finalSecJobOrderInfo.getAgentMustAttend())
			 //    out.print(HtmlUtil.getSaveThenContinueButtonHTML());
		
			  if(WebUtil.userHasRightToUpdateCompletedPhasesOfJobOrderFollowup(request))
				 out.print(HtmlUtil.getSaveThenGoBackButtonHTML());
			  
			  out.print(HtmlUtil.getResetButtonHTML());
			  %>
			</td>
		</tr> 
		--%>
	</tbody>
	<!-- </table> -->
</body>
</html>