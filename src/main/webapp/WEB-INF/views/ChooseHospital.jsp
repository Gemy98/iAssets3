<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.entity.*,com.iassets.common.util.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
a#report_link {
	display: inline-block;
	padding: 12px;
	border: 1px solid #000;
	text-decoration: none;
	background-color: #000;
	color: #fff;
	margin: auto auto 20px auto;
	font-size: 20px;
	font-weight: bold
}
</style>
<%
String langCode = WebUtil.getSessionLanguage(request);
List<CommonSite> siteList = (List<CommonSite>) request.getAttribute("siteList");
List<CommonHospital> locationList = (List<CommonHospital>) request.getAttribute("locationList");
String siteId = WebUtil.getParamValue(request, "site", null);
boolean userAssignedToManySites = siteList.size() > 1;
boolean ajaxRequest = locationList != null && ! locationList.isEmpty();
Boolean showBioSubmitBtn = (Boolean) request.getAttribute("showBioSubmitBtn");
Boolean showGenSubmitBtn = (Boolean) request.getAttribute("showGenSubmitBtn");
%>
<script>

function getHospitalList(srcObj){
	/* temperory hack */
	var siteId1 = $(srcObj).val();
	if(siteId1 == 1 || siteId1 == 13){
		alert("البيانات الأساسية لهذا الموقع غير متوفرة .. يرجى إمداد الشركة المالكة للبرنامج بالبيانات المطلوبة لتفعيل الموقع");
		return;
	}
	/* end of hack */
	
	$("#hospListContainer select").empty();
	$("td#submitBtnsContainer").empty();
	
    var siteId = $(srcObj).val(); 
    if(siteId != ""){
    	$("form").prop("action","ChooseSessionHospitalDisplay");
    	$("form").validate().settings.ignore = "*";
    	$("form").submit();
/* 	   $.post("ChooseSessionHospitalDisplay",{site: siteId}, 
			   function(data){
		         $("body").html(data);
					//   document.open();
					//   document.write(data);
					//   document.close();
	   }); */
    }
<%-- 	
    $("#hospListContainer select").empty();
	 
    var siteId = $(srcObj).val(); 
    if(siteId != ""){
	    $.getJSON("ChooseSessionHospitalDisplay",{site: siteId}, function(data){
		    if (data.length > 0 ){
		    	
		         //$("#hospListContainer").append("<select id='hospital' name='hospital' class='auto_off' required></select>");
		         $.each(data, function (index, value) {
		        	 $("select[name='hospital']").append($('<option>').text(value.name).attr('value', value.id));
		         });
		         if (data.length > 1){	 
		        	 if (<%=!userAssignedToManySites%>)
		            	 $("select[name='hospital'] option").eq(0).before($("<option></option>").val("0").text("الكل"));
		        	 else
		        		 $("select[name='hospital'] option").eq(0).before($("<option></option>").val("").text("من فضلك اختر"));
		             $("select[name='hospital']").prop('selectedIndex', 0);

		         }
		    }
	    });
    } 
--%>
   
}


</script>
</head>
<body>
	<form method="post" action="ChooseSessionHospitalProcess">
		<table class="layout_grid basic_page">
			<tbody>
				<%
        if( AppUtil.ppmNotificationsPublishingAllowed() && request.isUserInRole(Enums.SYS_ROLES.BIO_DIRECTORATE_DASHBOARD.getRoleName())){
       %>
				<tr>
					<td colspan="2" align="center"><a id="report_link"
						target="_blank" rel='opener'
						href="bio/ViewDirectoratePerformanceReport"><fmt:message
								key="jsp.view.bio.directorate.monthly.performance.report" /></a></td>
				</tr>
				<%}%>
				<tr>
					<td colspan="2"><h1 class="page_title">
							<fmt:message key="jsp.ChooseHospital.pagetitle" />
						</h1></td>
				</tr>
				<% String cssClass = (userAssignedToManySites) ? "" : "class='auto_off'";%>
				<tr <%=(!userAssignedToManySites)?"style='display:none'" : ""%>>
					<td class="side_label_middle"><fmt:message
							key="jsp.ChooseHospital.siteName" />:</td>
					<td><select style="min-width: 160px" name="site" <%=cssClass%>
						onChange="getHospitalList(this)" required>
							<%if (siteList != null)
                for (CommonSite obj : siteList)
                	out.print("<option value='"+obj.getId()+"'>"+obj.getLocalizedName()+"</option>");
            %>
					</select></td>
				</tr>
				<tr>
					<td class="side_label_middle"><fmt:message
							key="jsp.ChooseHospital.hospitalOrCenter" /> :</td>
					<td id="hospListContainer"><select style="min-width: 160px"
						id='hospital' name='hospital' class='auto_off' required>
							<%if (locationList != null)
                for (CommonHospital obj : locationList)
                	out.print("<option value='"+obj.getId()+"'>"+obj.getLocalizedName()+"</option>");
              else
            	 out.print("<option>&nbsp;</option>");
            %>
					</select></td>
				</tr>

				<tr>
					<td id="submitBtnsContainer" style="padding-top: 20px" colspan="2"
						align="left">
						<%if(Boolean.TRUE == showBioSubmitBtn)
    	  out.print("<input type='submit' style='width:210px;text-align:center;' name='bioSubmitBtn' value='"+LocalizationManager.getLiteral("jsp.ChooseHospital.bio", langCode)+"' />");
        if(Boolean.TRUE == showGenSubmitBtn)
    	  out.print("<input type='submit' style='width:240px;text-align:center;' name='genSubmitBtn' value='"+LocalizationManager.getLiteral("jsp.ChooseHospital.gen", langCode)+"' />");
    	  %>
					</td>
				</tr>
		</table>
	</form>

	<script>

if (<%=(! ajaxRequest && ! userAssignedToManySites)%>){
	$("select[name='site']").change();
}
if(<%=ajaxRequest%>){
	setComboSelectedValue("site","<%=siteId%>");
	
	if(<%=locationList != null && locationList.size() > 1%>){
		if (<%=WebUtil.currentUserHasViewPrivilegesOnly(request)%>)
			 $("select[name='hospital'] option").eq(0).before($("<option></option>").val("0").text("<%=LocalizationManager.getLiteral("jsp.js.ChooseHospital.all", langCode)%>"));
		else
			 $("select[name='hospital'] option").eq(0).before($("<option></option>").val("").text("<%=LocalizationManager.getLiteral("jsp.js.ChooseHospital.choosePlz", langCode)%>"));
    }
	$("select[name='hospital']").prop('selectedIndex', 0);
}
$.removeCookie('reportType');
$.removeCookie('reportLocation');
</script>
</body>
</html>
