<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"	import="com.iassets.common.util.*,com.iassets.common.entity.*,java.util.*" %>
<html dir="rtl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="gen/<%=WebUtil.getSearchProcessURL(request)%>" target="_blank">
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
<%--             
            <%
            	List<GenLookupDeviceCategory> catList = (List <GenLookupDeviceCategory>) request.getAttribute("catList");
            		      if (catList != null && !catList.isEmpty()){
            %>
            <tr>
				<td class="side_label_middle">الفئة :</td>
				<td><select id="category" name="category" class="auto_add_all auto_off">
						<%
							for (GenLookupDeviceCategory obj : catList) {
						%>
						<option value="<%=obj.getId()%>"><%=obj.getName()%></option>
						<%}%>
				</select></td>
			</tr>
			<%}%> 
--%>
			<tr>
				<td class="side_label_middle">القسم :</td>
				<td><select id="dep" name="dep" class="auto_add_all auto_off">
			<%
				List<CommonHospitalDepartment> depList = (List<CommonHospitalDepartment>)request.getAttribute("depList");
					      if (depList != null && !depList.isEmpty()){
			%>
			
						<%
										for (CommonHospitalDepartment obj : depList) {
									%>
						<option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
						<%}%>
				
			<%}%>
			</select></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="عرض"></td>
			</tr>
		</table>
	</form>
	
	<script>
	
    function getDepListAjax(){
    	var depList = $('#dep option');
    	var parentTR = $("#dep").closest('tr');
    	depList.slice(1).remove();
    	parentTR.hide();
    
    	var reportType = $("input:radio[name='reportType']:checked").val();
  	 
        if(reportType != ""  && reportType != '<%=Enums.REQUESTED_REPORT_TYPE.FOR_ALL_SITE_LOCATIONS.getId()%>'){
    	    $.getJSON("DevicesByCatAndDepFilterDisplay",$("form:first").serialize(), function(data){
    		    if (data.length > 0 ){
       		         $.each(data, function (index, value) {$("#dep").append($('<option>').text(value.name).attr('value', value.id))});
       		         parentTR.show();
    		    }
    	    });
        }
    }
    
    $(function(){
    	$("input[name='reportType'], #reportLocation").change(function(){getDepListAjax()});
    	$("input[name='reportType']:checked").change();
    });
    

	</script>
</body>
</html>