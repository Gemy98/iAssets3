<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.general.entity.*,com.iassets.general.util.HtmlUtil,com.iassets.common.util.Default,com.iassets.common.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<%
List<GenLookupJobOrderCategory>  genLookupJobOrderCategoryList=(List<GenLookupJobOrderCategory>) request.getAttribute("genLookupJobOrderCategoryList");
if(genLookupJobOrderCategoryList==null)
	genLookupJobOrderCategoryList=new ArrayList<GenLookupJobOrderCategory>();


GenEndUserMaintenanceRequest maintenanceReqInfo = (GenEndUserMaintenanceRequest) request.getAttribute(Default.MAINTENANCE_REQUEST_INFO_ATTR_NAME);


// CommonUser user=WebUtil.getSessionUser(request);
%>
<body>
	<style type="text/css">
       .pure-skin-mine select{min-width: 200px}
	</style>
	<form method="post" action="gen/JobOrderOpenProcess" enctype="multipart/form-data">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">شاشة فتح أمر عمل جديد</h1></td>
			</tr>
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%></td>
			</tr>
			
<!------------------- Awad  07-09-2016 ------------------------------>
<% 
Boolean uncodedDevice = (Boolean) request.getAttribute("uncodedDevice");
if(uncodedDevice != null && uncodedDevice){
%>

      <tr>
        <td class="side_label_middle">اسم الجهاز :</td>
        <td colspan="2"><input type="text" size="40" id="uncodedDeviceName" name="uncodedDeviceName" required></td>
      </tr>

 <%      
      List<CommonHospitalLocation> hospLocationList = (List<CommonHospitalLocation>)request.getAttribute("hospLocationList");
      if (hospLocationList != null && !hospLocationList.isEmpty()){

      %>
       <tr>
        <td class="side_label_middle">الموقع :</td>
        <td colspan="2">
          <select id="hospLocation" name="hospLocation" onchange="_onChangeHospitalLocation(this);_onChangeDeviceLock(this)">
            <%
            	for (CommonHospitalLocation obj : hospLocationList ){
            %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <% }%>
      <%
      List<CommonHospitalDepartment> depList = (List<CommonHospitalDepartment>)request.getAttribute("depList");
      if (depList != null && !depList.isEmpty()){
      %>
      <tr>
        <td class="side_label_middle">القسم :</td>
        <td colspan="2">
          <select id="dep" name="dep">
            <%
            	for (CommonHospitalDepartment dep :depList ){
            %>
            <option title="<%=dep.getLocation().getId()%>"  value="<%=dep.getId()%>"><%=dep.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <% }%>
      
      <tr>
        <td class="side_label_middle">الغرفة :</td>
        <td colspan="2"><input type="text"  id="hospitalRoom" name="hospitalRoom"></td>
      </tr>
      
      <tr>
        <td class="side_label_top">الوصف الدقيق للمكان :</td>
        <td colspan="2"><textarea id="hospitalLocationDescription" name="hospitalLocationDescription"></textarea></td>
      </tr>
      <tr>
 

<%}%>
<!--------------------------end  Awad  07-09-2016 ------------------->
			
			<tr>
				<td class="side_label_middle">رقم أمر العمل :</td>
				<td><input type="text" name="jobOrderNo" id="jobOrderNo" required data-msg-remote="يوجد أمر عمل بنفس الرقم"></td>
			</tr>
			<tr>
				<td class="side_label_middle">تاريخ العطل :</td>
				<td><input type="text" name="damageDate" id="damageDate" class="caldr" required></td>
			</tr>
				
<!------------------- awad  29-3-2016 ------------------------------>
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
							if(genLookupJobOrderCategoryList!=null &&genLookupJobOrderCategoryList.size()==1){
								checked=" checked='checked' ";
								hiddenStyle="";
								disabled="";
							}
							for (GenLookupJobOrderCategory genLookupJobOrderCategory : genLookupJobOrderCategoryList){
							%>
							
							 <input required type="radio" <%=checked%> name="gmp_id" id="radio" value="<%=genLookupJobOrderCategory.getId()%>"><%=genLookupJobOrderCategory.getLocalizedName()%>
						<%}	%>
						  </td>
						</tr>
						<tr>
						  <td>
						<%
							for (GenLookupJobOrderCategory genLookupJobOrderCategory : genLookupJobOrderCategoryList){
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
			<%}%>

					</td>
				</tr>
      </table>
     </td>
	</tr>

<!--------------------------end  awad  29-3-2016 ------------------->

			<tr>
				<td class="side_label_top">وصف العطل / العمل المطلوب :</td>
				<td><textarea name="damageDescription" id="damageDescription" required></textarea></td>
			</tr>
			<%
				List<GenLookupJobOrderType> jobOrderTypes = (List <GenLookupJobOrderType>) request.getAttribute("jobOrderTypes");
				if (jobOrderTypes != null && !jobOrderTypes.isEmpty()){
			%>
			<tr>
				<td class="side_label_middle">نوع أمر العمل :</td>
				<td>          
					<%
          			for (GenLookupJobOrderType obj : jobOrderTypes){
          		    %>
				         <input required type="radio" name="type_id" id="radio" value="<%=obj.getId()%>"><%=obj.getLocalizedName()%>
				    <%}%>
                </td>
			</tr>
			<%} %>
			<%
				List<GenLookupJobOrderPriority> jobOrderPriorities = (List <GenLookupJobOrderPriority>) request.getAttribute("jobOrderPriorities");
				     if (jobOrderPriorities != null && !jobOrderPriorities.isEmpty()){
			%>
			<tr>
				<td class="side_label_middle">أولوية أمر العمل :</td>
				<td>				
					<%
					for (GenLookupJobOrderPriority obj : jobOrderPriorities){
					%>
				         <input required type="radio" name="priority_id" id="radio" value="<%=obj.getId()%>"><%=obj.getLocalizedName()%>
				    <%}%>
			    </td>
			</tr>
			
			<%} %>
			<tr>
				<td class="side_label_middle">تاريخ أمر العمل :</td>
				<td><input type="text" name="jobOrderDate" id="jobOrderDate"
					class="caldr" required></td>
			</tr>
			
			
						<% if(maintenanceReqInfo == null){%>
			
			<tr>
				<td class="side_label_top">تحميل نموذج إبلاغ عن عطل :</td>
				<td><input type="file" name="joRequestFormUrl" id="joRequestFormUrl" multiple></td>
			</tr>
				<%}%>
<%-- 						
		    <%
		    List<CommonEmployee> responsibleEngs = (List<CommonEmployee>) request.getAttribute("responsibleEngs"); 
		    	if (responsibleEngs != null && !responsibleEngs.isEmpty()){
		    	    String sClass = (responsibleEngs.size() == 1)?"class='auto_off'":"";
		    %>
			<tr>
				<td class="side_label_middle">المهندس المستلم :</td>
				<td>          
					<select id="responsibleEng" name="responsibleEng" <%=sClass%> required>
			        <%
			        	for (CommonEmployee responsibleEng : responsibleEngs){
			        %>
			            <option value="<%=responsibleEng.getId()%>"><%=responsibleEng.getName()%></option>
			        <%
			        	}
			        %>
			        </select>
                </td>
			</tr>
		    <%
		    	}
		    %>
--%>	    
		    <%
		    CommonEmployee hospDepHead = (CommonEmployee) request.getAttribute("hospDepHead");
		    	if (hospDepHead != null){
		    %>
			<tr>
				<td class="side_label_middle">مشرف القسم :</td>
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
			CommonEmployee genDepHead = (CommonEmployee) request.getAttribute("genDepHead");
				if (genDepHead != null){
			%>
			<tr>
				<td class="side_label_middle">مشرف الصيانة :</td>
				<td>          
					<select id="genDepHead" name="genDepHead" class="auto_off" required>
			            <option value="<%=genDepHead.getId()%>"><%=genDepHead.getName()%></option>
			        </select>
                </td>
			</tr>
		    <%}%>
--%>
			<tr>
				<td colspan="2"><input type='button' class='pure-skin-mine pure-button-primary'  onclick='_onClickJobOrderOpenBtn()' value="افتح أمر عمل جديد"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
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
	    	
			 if(diff >= 4 && !confirm("يرجى ملاحظة أن تاريخ فتح أمر العمل متأخر عن تاريخ العطل بأربعة أيام أو أكثر .. هل تريد الاستمرار ؟"))
				return;

			 
    	//if(confirm("هل تريد عرض تقرير أمر العمل ؟")){

	    	formObj.append("<input type='hidden' name='saveThenReturn' value='1' />");
	    	formObj.append("<input type='hidden' name='showJobOrderReport' value='1' />");
	    	formObj.prop("target", "_blank");
	        alert("سيتم عرض تقرير أمر العمل بعد الحفظ لتتمكن من طباعته أو حفظ نسخة إلكترونية منه");
	    	formObj.submit();
	    	setTimeout(function(){window.location.href = "gen/OpenJobOrderSearch"},3000);
	    	// window.location.href = "gen/OpenJobOrderSearch";
    	//}else{
    	//	formObj.submit();
    	//}
        }
    }
	
	
	/* function _onChangeGmpCategory(obj)
	{
	 
	 $("#GenLookupJobOrderCategory select").prop("disabled",true);
	 $("#gmp_"+obj.val()).prop("disabled",false);
	} */
	
	
	function _onChangeDeviceLock(obj){
		
		// $(obj).find("option:selected").index();
		markAsNotRequired("#hospitalLocationDescription");
		if(obj.selectedIndex == 0)
		   markAsRequired("#hospitalLocationDescription");	
	}
	
	
	function _onChangeGmpCategory(obj){
	 
	 $("#GenLookupJobOrderCategory select").prop("disabled",true);
	 $("#GenLookupJobOrderCategory div").css("display","none");
	 $("#gmp_"+obj.val()).prop("disabled",false);
	 $("#div_gmp_"+obj.val()).css("display","block");

	}
	
	
	$(function(){
		<%if(uncodedDevice != null && uncodedDevice){%>
			$('#hospLocation').change();
		<%}%>
		
		<%
		  String autoGenJobOrderNo = (String) request.getAttribute("autoGeneratedJobOrderNo");
		  if(autoGenJobOrderNo != null){
		%>
  		      setTextFieldValue("jobOrderNo","<%=autoGenJobOrderNo%>");
		<%}%>
		
		  $("#jobOrderNo").rules("add",{
			  remote:{
				  url:"gen/CheckJobOrderNoDuplication"
			  }
		  });
		 
	     $("#jobOrderDate" ).rules( "add", {
	 		dateGreaterThan: ['#damageDate'],
			messages: {	dateGreaterThan: "يجب أن يكون التاريخ بعد تاريخ العطل"}
		 });
	     
	     
	     <%if(maintenanceReqInfo != null){
	         String damageDesc = Common.getDisplayString(maintenanceReqInfo.getDamageDescription(), "");
	         String damageDate = DateUtil.getDateString(maintenanceReqInfo.getDamageDate());
	     %>
	        setInnerHTML("damageDescription","<%=damageDesc%>");
	        setDateValue("damageDate","<%=damageDate%>");
	            
	        
	        <%if(uncodedDevice != null && uncodedDevice){
	        	String hospitalLocationDescription=Common.getDisplayString(maintenanceReqInfo.getUncodedDeviceLocationDescription(), "");
	        	String uncodedDeviceName=Common.getDisplayString(maintenanceReqInfo.getUncodedDeviceName(), "");
	        	String hospitalRoom=Common.getDisplayString(maintenanceReqInfo.getUncodedDeviceRoom(), "");
	        	String hospLocation=Common.getDisplayString(maintenanceReqInfo.getUncodedDeviceLocation().getId(), "");
	        	String dep=Common.getDisplayString(maintenanceReqInfo.getUncodedDeviceDepartment().getId(), "");  	
	        %>
	        setComboSelectedValue("hospLocation",<%=hospLocation%>);
	        $('#hospLocation').change();
	        setComboSelectedValue("dep",<%=dep%>);
	        setTextFieldValue("uncodedDeviceName","<%=uncodedDeviceName%>");
	        setTextFieldValue("hospitalRoom","<%=hospitalRoom%>");
	        setInnerHTML("hospitalLocationDescription","<%=hospitalLocationDescription%>");
	        <%}
	        }%>
	        
         $("input[name='gmp_id']").change(function(){_onChangeGmpCategory($(this))});

	     
		 
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
