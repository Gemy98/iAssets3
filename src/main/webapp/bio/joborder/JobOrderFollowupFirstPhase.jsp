<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,java.util.*"%>
<%
  BioEndUserMaintenanceRequest maintenanceReqInfo = (BioEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);
%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<form method="post" action="bio/JobOrderFollowupFirstPhaseProcess" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.JobOrderFollowupFirstPhase.pagetitle" /></h1></td>
</tr>
<tr>
  <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %>
</td>
</tr>
<%if(maintenanceReqInfo == null){%>
<tr>
	<td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupFirstPhase.joRequestFormUrl" /> :</td>
	<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
</tr>
<%}%>
<tr>
  <td class="side_label_top"><fmt:message key="jsp.JobOrderFollowupFirstPhase.firstAction" /> :</td>
  <td><textarea name="firstAction" id="firstAction" required></textarea></td>
</tr>
<tr>
  <td class="side_label_middle">:</td>
  <td><input type="text" name="firstActionDate" id="firstActionDate" class="caldr" required></td>
</tr>

<tr>
  <td colspan="2"><strong> <fmt:message key="jsp.JobOrderFollowupFirstPhase.agentMustAttend" /> : </strong>
  <%
  Boolean agentMustAttend = jobOrderInfo.getAgentMustAttend();
  if(agentMustAttend == null){%>
    <input name="agentMustAttend" value="1" type="radio" required>
    نعم
    <input name="agentMustAttend" value="0" type="radio" required>
    لا
  <%}else out.print(HtmlUtil.getBooleanAsString(agentMustAttend,langCode));%>
  </td>
</tr>

<tr>
  <td colspan="2">
  <%
    out.print(HtmlUtil.getSaveThenReturnButtonHTML(langCode));
    out.print(HtmlUtil.getSaveThenContinueButtonHTML(langCode));
    out.print(HtmlUtil.getResetButtonHTML(langCode));
   %>
  </td>
</tr>
</table>
</form>


<script>

$(function(){
	
<%
    String firstAction = Common.getDisplayString(jobOrderInfo.getFirstAction(),"");

    Date fActionDate = jobOrderInfo.getFirstActionDate();
    if(fActionDate == null)
    	fActionDate = new Date();
    String firstActionDate =  DateUtil.getDateString(fActionDate);
    
    String requestFormFiles = jobOrderInfo.getRequestFormUrl();
%>
	
	$("#firstActionDate" ).rules( "add", {
		dateGreaterThan: ['#joborder_date'],
		messages: {	dateGreaterThan: '<fmt:message key="jsp.JobOrderFollowupFirstPhase.dateGreaterThan" />'}
	});
 
    setInnerHTML("firstAction","<%=firstAction%>");
    setDateValue("firstActionDate","<%=firstActionDate%>");
    showUploadedFilesList("joRequestFormUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(requestFormFiles)%>);

});

</script>
</body>
</html>
