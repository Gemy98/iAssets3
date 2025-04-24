<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.common.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><% String langCode = WebUtil.getSessionLanguage(request); %><html dir="${sessionScope.direction}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
	CommonEmployee employee = (CommonEmployee) request.getAttribute("employeeObj");
	boolean inUpdateMode = employee != null;
%>
</head>
<body>
	<form method="post" action="bio/OperatingCompanyLaborerInfoProcess" enctype="multipart/form-data">
		<table class="layout_grid">
			<tbody>
				<tr>
					<td colspan="2"><h1 class="page_title"><%=(inUpdateMode == true) ?LocalizationManager.getLiteral("jsp.OperatingCompanyLaborerInfo.pagetitle.inUpdateMode" ,langCode) : LocalizationManager.getLiteral("jsp.OperatingCompanyLaborerInfo.pagetitle.inAddMode",langCode)%></h1></td>
				</tr>
				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.OperatingCompanyLaborerInfo.employeeNo" />:</td>
					<td><input type="text" id="employeeNo"	name="employeeNo" data-msg-remote="<fmt:message key='jsp.OperatingCompanyLaborerInfo.employeeNo.requiredmsg' />" required></td>
				</tr>
				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.OperatingCompanyLaborerInfo.nameAr" />:</td>
					<td><input type="text" id="nameAr" name="nameAr" required data-rule-arName="true" data-msg-arName="<fmt:message key='jsp.OperatingCompanyLaborerInfo.nameAr.requiredmsg' />"></td>
				</tr>
				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.OperatingCompanyLaborerInfo.nameEn" />:</td>
					<td><input type="text" id="nameEn" name="nameEn" required data-rule-enName="true" data-msg-enName="<fmt:message key='jsp.OperatingCompanyLaborerInfo.nameEn.requiredmsg' />"> </td>
				</tr>

				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.OperatingCompanyLaborerInfo.jobTitle" />:</td>
				<%
					List<CommonLookupUserType> jobTitleList = (List<CommonLookupUserType>) request.getAttribute("jobTitleList");
					if ((employee == null || employee.getUserType().getId() != Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()) && jobTitleList != null && ! jobTitleList.isEmpty()) {
				%>
					<td>
						<select id="jobTitle" name="jobTitle" required>
							<%for (CommonLookupUserType obj : jobTitleList) {%>
							<option value="<%=obj.getId()%>"><%=obj.getJobTitle(langCode)%></option>
							<%}%>
						</select>
					</td>
			   <% } else { %>
					<td> 
						<%= LocalizationManager.getLiteral("common_lookup_user_type.job6", langCode) %>
					</td>
			   <% } %>
				</tr>

				<%
					List<CommonLookupNationality> nationalityList = (List<CommonLookupNationality>) request.getAttribute("nationalityList");
					if (nationalityList != null && !nationalityList.isEmpty()) {
				%>
				<tr>
					<td class="side_label_middle"> <fmt:message key="jsp.OperatingCompanyLaborerInfo.nationality" /> :</td>
					<td>
						<select id="nationality" name="nationality" required>
							<%for (CommonLookupNationality obj : nationalityList) {%>
							<option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
							<%}%>
						</select>
					</td>
				</tr>
				<%
					}
				%>

				<tr>
					<td class="side_label_middle">  <fmt:message key="jsp.OperatingCompanyLaborerInfo.mobile" />  :</td>
					<td><input type="text" id="mobile" name="mobile" maxlength="10" data-rule-minlength="10" data-msg-minlength="<fmt:message key='jsp.OperatingCompanyLaborerInfo.mobile.msg1' />" data-rule-range ="0500000000,0599999999" data-msg-range="<fmt:message key='jsp.OperatingCompanyLaborerInfo.mobile.msg2' />"></td>
				</tr>
				<tr>
					<td class="side_label_middle"> <fmt:message key="jsp.OperatingCompanyLaborerInfo.salary" />  :</td>
					<td><input type="text" id="salary" name="salary" required data-rule-number="true"> 
					<fmt:message key="jsp.OperatingCompanyLaborerInfo.currency" /></td>
				</tr>

				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.OperatingCompanyLaborerInfo.employmentDate" /> :</td>
					<td><input type="text" class="caldr" id="employmentDate" name="employmentDate" required></td>
				</tr>

				<tr>
					<td class="side_label_top"> <fmt:message key="jsp.OperatingCompanyLaborerInfo.approvalScanUrl" />:</td>
					<td><input type="file" name="approvalScanUrl" id="approvalScanUrl" multiple="multiple" class="requiredFile" /></td>
				</tr>

				<tr>
					<td colspan="2"><input type="submit" value='<%=(inUpdateMode == true) ? LocalizationManager.getLiteral("jsp.OperatingCompanyLaborerInfo.submit.inUpdateMode", langCode) : LocalizationManager.getLiteral("jsp.OperatingCompanyLaborerInfo.submit.inAddMode", langCode)%>'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
				</tr>
		</table>
	</form>
	
	<jsp:include page="/bio/ViewOperatingCompanyOnsiteLaborers_Segment" flush="true" /> 
<%-- 	
<jsp:param name="excludeSiteManager" value="0"/>
</jsp:include> 		 --%>
	

<script>  
	  $(function(){ 
		  
		  <% if (employee !=  null){
			    int id = employee.getId(); 
		   		String empNo = Common.getDisplayString(employee.getEmployeeNo(),"");
		   		String nameAr = Common.getDisplayString(employee.getName(Enums.SUPPORTED_LANGUAGES.ARABIC.getLanguageCode()),"");
		   		String nameEn = Common.getDisplayString(employee.getName(Enums.SUPPORTED_LANGUAGES.ENGLISH.getLanguageCode()),"");
		   		String jobTitle = (employee.getUserType() != null )? "" + employee.getUserType().getId():"";
		   		String nationality = (employee.getNationality() != null )? "" + employee.getNationality().getId():"";
		   		String mobile = Common.getDisplayString(employee.getMobile(),"");
		   		String salary = Common.getDisplayString(employee.getContractSalary(),"");
		   		String employmentDate = DateUtil.getDateString(employee.getEmploymentDate());
		   		String approvalScanFiles = employee.getApprovalScan();
		  %>
	   		appendIdElement("empId","<%=id%>");
	   		
	   		setTextFieldValue("employeeNo","<%=empNo%>");
	   		setTextFieldValue("nameAr","<%=nameAr%>");
	   		setTextFieldValue("nameEn","<%=nameEn%>");
	   	    setComboSelectedValue("jobTitle","<%=jobTitle%>");
	   		setComboSelectedValue("nationality","<%=nationality%>"); 
	   		setTextFieldValue("mobile","<%=mobile%>");
	   		setTextFieldValue("salary","<%=salary%>");
	   		setDateValue("employmentDate","<%=employmentDate%>");
	   	    showUploadedFilesList("approvalScanUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(approvalScanFiles)%>); 
	   	 <% } %>
	   	 $("#employeeNo").rules("add",{
			  remote:{
				  url:"bio/CheckEmployeeInfoDuplication",
				  data: {
					    // employeeNo: function(){return $("#employeeNo").val()},
				        empId: function(){if($("#empId").length) return $("#empId").val(); return "0"}		
				      }
			 }
		  });
     });
</script>


</body>
</html>