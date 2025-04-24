<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.biomedical.entity.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.common.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<%
BioEndUserMaintenanceRequest maintenanceReqInfo = (BioEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);
%>
<body>
	<style type="text/css">
       .pure-skin-mine select{min-width: 200px}
	</style>
	<form method="post" action="bio/JobOrderOpenProcess" enctype="multipart/form-data">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title"><fmt:message key="jsp.JobOrderOpen.pagetitle" /></h1></td>
			</tr>
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%></td>
			</tr>
			<tr>
				<td class="side_label_middle"><fmt:message key="jsp.JobOrderOpen.jobOrderNo" /> :</td>
				<td><input type="text" name="jobOrderNo" id="jobOrderNo" readonly="readonly" required data-msg-remote="يوجد أمر عمل بنفس الرقم"></td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.damageDate" /> :</td>
				<td><input type="text" name="damageDate" id="damageDate" class='caldr<%=(maintenanceReqInfo == null)?"":" noteditable"%>' required></td>
			</tr>
			<tr>
				<td class="side_label_top"> <fmt:message key="jsp.JobOrderOpen.damageDescription" /> :</td>
				<td><textarea name="damageDescription" id="damageDescription" required></textarea></td>
			</tr>
			<%
			List<BioLookupJobOrderType> jobOrderTypes = (List <BioLookupJobOrderType>) request.getAttribute("jobOrderTypes");
				if (jobOrderTypes != null && !jobOrderTypes.isEmpty()){
			%>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.jobOrderTypes" /> :</td>
				<td>          
				<%
         		for (BioLookupJobOrderType obj : jobOrderTypes){
         		%>
			         <input required type="radio" name="type_id" id="radio" value="<%=obj.getId()%>"><%=obj.getLocalizedName(langCode)%>
			    <%}%>
                </td>
			</tr>
			<%} %>
			<%
				List<BioLookupJobOrderPriority> jobOrderPriorities = (List <BioLookupJobOrderPriority>) request.getAttribute("jobOrderPriorities");
				    if (jobOrderPriorities != null && !jobOrderPriorities.isEmpty()){
			%>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.jobOrderPriorities" /> :</td>
				<td>				
			<%
			  for (BioLookupJobOrderPriority obj : jobOrderPriorities){
			%>
		         <input required type="radio" name="priority_id" id="radio" value="<%=obj.getId()%>"><%=obj.getLocalizedName(langCode)%>
		    <%}%>
			    </td>
			</tr>
			
			<%} %>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.jobOrderDate" /> :</td>
				<td><input type="text" name="jobOrderDate" id="jobOrderDate" class="caldr" required></td>
			</tr>
			<% if(maintenanceReqInfo == null){%>
			<tr>
				<td class="side_label_top"> <fmt:message key="jsp.JobOrderOpen.joRequestFormUrl" />:</td>
				<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
			</tr>
			<%}%>			
		    <%
		    List<CommonEmployee> responsibleEngs = (List<CommonEmployee>) request.getAttribute("responsibleEngs"); 
		    	if (responsibleEngs != null && !responsibleEngs.isEmpty()){
		    	    String sClass = (responsibleEngs.size() == 1)?"class='auto_off'":"";
		    %>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.responsibleEng" /> :</td>
				<td>          
					<select id="responsibleEng" name="responsibleEng" <%=sClass%> required>
			        <%
			        	for (CommonEmployee responsibleEng : responsibleEngs){
			        %>
			            <option value="<%=responsibleEng.getId()%>"><%=responsibleEng.getName(langCode)%></option>
			        <%
			        	}
			        %>
			        </select>
                </td>
			</tr>
		    <%
		    	}
		    %>
		    
		    
			<%
 		    CommonEmployee hospDepHead = (CommonEmployee) request.getAttribute("hospDepHead");
 		    	if (hospDepHead != null){
		    %>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.JobOrderOpen.hospDepHead" /> :</td>
				<td> <%=hospDepHead.getName(langCode)%>         
<%-- 					
<select id="hospDepHead" name="hospDepHead" class="auto_off" required>
	<option value="<%=hospDepHead.getId()%>"><%=hospDepHead.getName()%></option>
</select> 
--%>
                </td>
			</tr>
		    <%
		    	}
		    %> 

<%-- 		
		    <%
		    CommonEmployee biomedDepHead = (CommonEmployee) request.getAttribute("biomedDepHead");
		    	if (biomedDepHead != null){
		    %>
			<tr>
				<td class="side_label_middle">مشرف الصيانة :</td>
				<td>          
					<select id="biomedDepHead" name="biomedDepHead" class="auto_off" required>
			            <option value="<%=biomedDepHead.getId()%>"><%=biomedDepHead.getName()%></option>
			        </select>
                </td>
			</tr>
		    <%}%>
--%>
			<tr>
				<td colspan="2"><input type='button' class='pure-skin-mine pure-button-primary'  onclick='_onClickJobOrderOpenBtn()' value=" <fmt:message key="jsp.JobOrderOpen.jobOrderOpenBtn" /> "><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
			</tr>
		</table>
	</form>
	<script>
	
	$("#jobOrderNo").focus(); // Very important to trigger element validation immediately
	
	function _onClickJobOrderOpenBtn(){
		    
		var formObj = $("form:first");
		// formObj.validate();
		
	    if(formObj.valid()){
	    	
			var joDate =  $("#jobOrderDate").datepicker("getDate").getTime();
			var damDate = $("#damageDate").datepicker("getDate").getTime();
			var diff = Math.ceil((joDate -damDate)/(24*60*60*1000));
	    	
			 if(diff >= 4 && !confirm('<fmt:message key="jsp.js.JobOrderOpen.jobOrderOpenBtnConfirm" />' ))
				return;

			 
    	//if(confirm("هل تريد عرض تقرير أمر العمل ؟")){

	    	formObj.append("<input type='hidden' name='saveThenReturn' value='1' />");
	    	formObj.append("<input type='hidden' name='showJobOrderReport' value='1' />");
	    	formObj.prop("target", "_blank");
	        alert('<fmt:message key="jsp.js.JobOrderOpen.jobOrderOpenBtnAlert" />' );
	    	formObj.submit();
	    	setTimeout(function(){window.location.href = "bio/OpenJobOrderSearch"},3000);
	    	// window.location.href = "bio/OpenJobOrderSearch";
    	//}else{
    	//	formObj.submit();
    	//}
        }
    }
	
	$(function(){
		
		<%
		  String autoGenJobOrderNo = (String) request.getAttribute("autoGeneratedJobOrderNo");
		  if(autoGenJobOrderNo != null){
		%>
  		      setTextFieldValue("jobOrderNo","<%=autoGenJobOrderNo%>");
		<%}%>
		
		  $("#jobOrderNo").rules("add",{
			  remote:{
				  url:"bio/CheckJobOrderNoDuplication"
			  }
		  });
		 
	     $("#jobOrderDate" ).rules( "add", {
	 		dateGreaterThan: ['#damageDate'],
			messages: {	dateGreaterThan:'<fmt:message key="jsp.js.JobOrderOpen.jobOrderDate.dateGreaterThan" />' }
		 });
	     
	     <%if(maintenanceReqInfo != null){
	         String damageDesc = Common.getDisplayString(maintenanceReqInfo.getDamageDescription(), "");
	         String damageDate = DateUtil.getDateString(maintenanceReqInfo.getDamageDate());
	     %>
	        setInnerHTML("damageDescription","<%=damageDesc%>");
	        setDateValue("damageDate","<%=damageDate%>");
	        //$("#damageDate").attr("readonly","readonly")
	        //$("#damageDate").datepicker("option", "beforeShow", function(elem) {if ($(elem).attr('readonly')) {return false}});
		 <%}%>
/* 		
        $("form").submit(function(){
			 
			 var joDate =  $("#jobOrderDate").datepicker("getDate").getTime();
			 var damDate = $("#damageDate").datepicker("getDate").getTime();
			 var diff = Math.ceil((joDate -damDate)/(24*60*60*1000));
			 if($("form").valid() && diff >= 4)
			    return confirm("يرجى ملاحظة أن تاريخ فتح أمر العمل متأخر عن تاريخ العطل بأربعة أيام أو أكثر .. هل تريد الاستمرار ؟")
			    
			  return true;
		 }); 
*/
	 
	});
	</script>
</body>
</html>
