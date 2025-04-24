<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.general.util.HtmlUtil,com.iassets.common.entity.*,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<%
List<GenLookupJobOrderCategory>  genLookupJobOrderCategoryList=(List<GenLookupJobOrderCategory>) request.getAttribute("genLookupJobOrderCategoryList");
if(genLookupJobOrderCategoryList==null)
	genLookupJobOrderCategoryList=new ArrayList<GenLookupJobOrderCategory>();


GenEndUserMaintenanceRequest maintenanceReqInfo = (GenEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);


%>
<body>
<form method="post" action="gen/JobOrderFollowupFirstPhaseProcess" enctype="multipart/form-data">
<table class="layout_grid">
<tr>
  <td colspan="2"><h1 class="page_title">الإجراء الأول لأمر عمل مفتوح</h1></td>
</tr>
<tr>
  <td colspan="2"><%@ include file="../DeviceAndJobOrderInfo.jsp" %>
</td>
</tr>

<!------------------- awad  4-4-2016 ------------------------------>
<!-- add new tr for ta7weel ela qesm a5ar -->
<tr>
  <td class="side_label_middle"> تحويل لقسم اخر :</td>
<td>
<input type="radio" name="other_depart" value="1" />نعم
<input type="radio" name="other_depart" value="2" checked="checked" />لا
</td>
</tr>
<tbody id="other_gmp_departments" style="display: none;">

			<tr>
				<td class="side_label_top">القسم المختص:</td>
				<td>
					<table id="GenLookupJobOrderCategory">
						<tr>
						<td>
							<%
							String checked="";
							String hiddenStyle=" style='display: none;' ";
							String disabled=" disabled='disabled'";
							if(genLookupJobOrderCategoryList!=null &&genLookupJobOrderCategoryList.size()==1)
							{
								checked=" checked='checked' ";
								hiddenStyle="";
								disabled="";
							}
								for (GenLookupJobOrderCategory genLookupJobOrderCategory : genLookupJobOrderCategoryList) 
							{
							%>
							
								 <input required type="radio" <%=checked%> name="gmp_id" id="radio" value="<%=genLookupJobOrderCategory.getId()%>"><%=genLookupJobOrderCategory.getLocalizedName(langCode)%>
						<%}	%>
</td>
						</tr>
						<tr>
						  <td>
						<%
							for (GenLookupJobOrderCategory genLookupJobOrderCategory : genLookupJobOrderCategoryList)
							{
						%>
						
								<%
									if (genLookupJobOrderCategory.getSubcategories() != null
												&& genLookupJobOrderCategory.getSubcategories().size() > 0) {
											String classAutoOoff = "";
											if (genLookupJobOrderCategory.getSubcategories().size() == 1)
												classAutoOoff = "class='auto_off'";
								%> 
								
							<div id="div_gmp_<%=genLookupJobOrderCategory.getId()%>" <%=hiddenStyle%>>
					<select required <%=classAutoOoff%> <%=disabled%> 	id="gmp_<%=genLookupJobOrderCategory.getId()%>"	name="gmp_sub_<%=genLookupJobOrderCategory.getId()%>">
									<%
										for (GenLookupJobOrderSubcategory GenLookupJobOrderSubcategory : genLookupJobOrderCategory
														.getSubcategories()) {
									%>
									<option value="<%=GenLookupJobOrderSubcategory.getId()%>"><%=GenLookupJobOrderSubcategory.getLocalizedName()%></option>
									<%}%>

							</select> 
							</div>
								<%}%>

							


							<%
								}
							%>

					</td>
						</tr>


					</table>


				</td>
			</tr>


<!-- add new tr for other departments -->
</tbody>
<!--------------------------end  awad  4-4-2016 ---------------------------->


<tbody id="fp_form">
<!-- add new tr for the responsible employee  -->
		    <%
		    List<CommonEmployee> responsibleEngs = (List<CommonEmployee>) request.getAttribute("responsibleEngs"); 
		    	if (responsibleEngs != null && !responsibleEngs.isEmpty()){
		    	    String sClass = (responsibleEngs.size() == 1)?"class='auto_off'":"";        	 
		    %>
			<tr>
				<td class="side_label_middle">المهندس المستلم :</td>
				<td>          
					<select id="responsibleEng" name="responsibleEng" required <%=sClass%>>
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
			
			<%}%>
			
		    <%
  	         CommonEmployee gmpDepSupervisor = (CommonEmployee) request.getAttribute("gmpDepSupervisor"); 
   	         if (gmpDepSupervisor != null){	    	        	 
			 %>
			<tr>
				<td class="side_label_middle">مشرف القسم :</td>
				<td>          
					<select id="gmpDepSupervisor" name="gmpDepSupervisor" class="auto_off" required>
			            <option value="<%=gmpDepSupervisor.getId()%>"><%=gmpDepSupervisor.getName(langCode)%></option>
			        </select>
                </td>
			</tr>
			<%}%>
			
<%if(maintenanceReqInfo == null){%>			
<tr>
	<td class="side_label_top">تحميل نموذج إبلاغ عن عطل :</td>
	<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
</tr>
<%} %>
<tr>
  <td class="side_label_top">الإجراء الأول :</td>
  <td><textarea name="firstAction" id="firstAction" required></textarea></td>
</tr>
<tr>
  <td class="side_label_middle">تاريخ الإجراء الأول :</td>
  <td><input type="text" name="firstActionDate" id="firstActionDate" class="caldr" required></td>
</tr>

<tr>
  <td colspan="2"><strong>العطل يستلزم حضور الوكيل : </strong>
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
</tbody>






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
//awad
function _onChangeGmpCategory(obj)
{
 
 $("#GenLookupJobOrderCategory select").prop("disabled",true);
 $("#GenLookupJobOrderCategory div").css("display","none");
 $("#gmp_"+obj.val()).prop("disabled",false);
 $("#div_gmp_"+obj.val()).css("display","block");

}
function _onChangeOtherDepartment(obj)
{
	if(obj.val()==1){
	 $("#other_gmp_departments").css("display","");
	 $("#fp_form").css("display","none");
	 
	  $("#saveThenContinue").css("display","none");
	  $("#saveThenGoBack").css("display","none");
	 
	}
	else
	{
		 $("#other_gmp_departments").css("display","none");
		 $("#fp_form").css("display","");
		 
	  $("#saveThenContinue").css("display","");
	  $("#saveThenGoBack").css("display","");
	}
}

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
		messages: {	dateGreaterThan: "يجب أن يكون التاريخ بعد تاريخ فتح أمر العمل"}
	});
 
    setInnerHTML("firstAction","<%=firstAction%>");
    setDateValue("firstActionDate","<%=firstActionDate%>");
    showUploadedFilesList("joRequestFormUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(requestFormFiles)%>);

	//awad
    $("input[name='gmp_id']").change(function(){_onChangeGmpCategory($(this))});
    $("input[name='other_depart']").change(function(){_onChangeOtherDepartment($(this))});

    
    
});

</script>
</body>
</html>
