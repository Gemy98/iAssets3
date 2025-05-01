<%@ tag body-content="empty" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" import="com.iassets.common.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="i18n.jsp_literals" />

<%@ attribute name="classificationList" required="false"
	type="java.util.List"%>
<%@ attribute name="catList" required="false" type="java.util.List"%>
<%@ attribute name="locationList" required="false" type="java.util.List"%>
<%@ attribute name="depList" required="false" type="java.util.List"%>

<%@ attribute name="showCategoryAndClassification" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="showDepartmentAndLocation" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="showDeviceName" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="showAgentAndSubcontractor" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="showManufacturerNameAndModel" required="false"
	type="java.lang.Boolean"%>


<c:if
	test="${showCategoryAndClassification != null && showCategoryAndClassification == true }">
	<c:if
		test="${classificationList != null  && !classificationList.isEmpty()}">
		<tr>
			<td class="side_label_middle"><fmt:message
					key="jsp.CommonDevicesFilter.classification" /> :</td>
			<td><select id="classification" name="classification"
				class="auto_add_all auto_off">
					<c:forEach items="${classificationList }" var="classification">
						<option value="${ classification.getId() }">${ classification.getLocalizedName() }</option>
					</c:forEach>
			</select></td>
		</tr>
	</c:if>


	<c:if test="${catList != null  && !catList.isEmpty()}">
		<tr>
			<td class="side_label_middle"><fmt:message
					key="jsp.CommonDevicesFilter.category" /> :</td>
			<td valign="top"><span dir="ltr"> <c:forEach
						items="${catList }" var="cat">
						<input style="margin-left: 2px; margin-right: 2px;"
							name="category" type="checkbox" value="${ cat.getId() }" /> ${ cat.getLocalizedName() }
						</c:forEach>
			</span></td>
		</tr>
	</c:if>
</c:if>

<c:if
	test="${showDepartmentAndLocation != null && showDepartmentAndLocation == true }">
	<c:if test="${locationList != null  && !locationList.isEmpty()}">
		<tr>
			<td class="side_label_middle"><fmt:message
					key="jsp.CommonDevicesFilter.building" /> :</td>
			<td><select id="hospLocation" name="hospLocation"
				class="auto_add_all auto_off">
					<c:forEach items="${locationList }" var="location">
						<option value="${ location.getId() }">${ location.getLocalizedName() }</option>
					</c:forEach>
			</select></td>
		</tr>
	</c:if>

	<c:if test="${depList != null  && !depList.isEmpty()}">
		<tr>
			<td class="side_label_middle"><fmt:message
					key="jsp.CommonDevicesFilter.dep" /> :</td>
			<td><select id="dep" name="dep" class="auto_add_all auto_off">
					<c:forEach items="${depList }" var="dep">
						<option value="${ dep.getId() }">${ dep.getLocalizedName() }</option>
					</c:forEach>
			</select></td>
		</tr>
	</c:if>
</c:if>

<c:if test="${showDeviceName != null && showDeviceName == true }">
	<tr>
		<td class="side_label_middle"><fmt:message
				key="jsp.CommonDevicesFilter.name" />:</td>
		<td><input type="text" size="25" id="name" name="name"></td>
	</tr>
</c:if>

<c:if
	test="${showAgentAndSubcontractor != null && showAgentAndSubcontractor == true }">
	<tr>
		<td class="side_label_middle"><fmt:message
				key="jsp.CommonDevicesFilter.agentName" /> :</td>
		<td><input type="text" size="25" id="agentName" name="agentName"></td>
	</tr>
	<tr>
		<td class="side_label_middle"><fmt:message
				key="jsp.CommonDevicesFilter.subcontractor" /> :</td>
		<td><input type="text" size="25" id="subcontractor"
			name="subcontractor"></td>
	</tr>
</c:if>

<c:if
	test="${showManufacturerNameAndModel != null && showManufacturerNameAndModel == true }">
	<tr>
		<td class="side_label_middle"><fmt:message
				key="jsp.CommonDevicesFilter.manufacturerName" />:</td>
		<td><input type="text" size="25" id="manufacturerName"
			name="manufacturerName"></td>
	</tr>
	<tr>
		<td class="side_label_middle"><fmt:message
				key="jsp.CommonDevicesFilter.model" />:</td>
		<td><input type="text" size="25" id="model" name="model"></td>
	</tr>
</c:if>

<script>
	
	<c:if test="${showDepartmentAndLocation != null && showDepartmentAndLocation == true }">
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
	</c:if>
    
    <c:if test="${showAgentAndSubcontractor != null && showAgentAndSubcontractor == true }">
		 // autocomplete for device name
		 // bioLookupDeviceNameAutocompleteJsonArray value set once in js_constants.js
		 // bioLookupSubcontractorAutocompleteJsonArray value set once in js_constants.js
		$("#name").autocomplete({
			source : function(request, response) { 
				var results = $.map(bioLookupDeviceNameAutocompleteJsonArray, function (tag) {
					if (tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0) {
					    return tag;
					}
				});
				response(results);
			}
		});
		
		$("#agentName, #subcontractor").autocomplete({
			source : function(request, response) {
				var results = $.map(bioLookupSubcontractorAutocompleteJsonArray, function (tag) {
					if (tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0) {
					    return tag;
					}
				});
				response(results);
		    }
		});
	</c:if>

	</script>