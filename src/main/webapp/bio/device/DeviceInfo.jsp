<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*,com.iassets.common.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
	String langCode = WebUtil.getSessionLanguage(request);

    BioHospitalDevice deviceInfo = (BioHospitalDevice)request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
    boolean inUpdateMode = deviceInfo != null;
    
    int deviceAccessoriesCount = 0;
    if (deviceInfo !=null && deviceInfo.getHospitalDeviceAccessories() != null)
        deviceAccessoriesCount = deviceInfo.getHospitalDeviceAccessories().size();
    
    CommonHospital currentLocation = (CommonHospital)request.getAttribute("currentLocation");
%>
</head>
<body>
<form method="post" action="bio/DeviceInfoProcess" enctype="multipart/form-data">
  <table class="layout_grid">
    <tbody>
      <tr>
        <td colspan="2"><h1 class="page_title"> <%=(inUpdateMode == true)? LocalizationManager.getLiteral("jsp.DeviceInfo.pagetitleupdate", langCode): LocalizationManager.getLiteral("jsp.DeviceInfo.pagetitlenew", langCode) %> </h1></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.code" />  :</td>
        <td><input type="text" id="code" name="code" data-msg-remote="<fmt:message key="jsp.DeviceInfo.codemsg" />" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.serial" /> :</td>
        <td><input type="text" id="serial" name="serial"  data-msg-remote="<fmt:message key="jsp.DeviceInfo.serialmsg" />" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.name" />:</td>
        <td><input type="text" size="40" id="name" name="name" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.manufacturerName" />:</td>
        <td><input type="text" size="40" id="manufacturerName" name="manufacturerName" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.model" />:</td>
        <td><input type="text" id="model" name="model" required></td>
      </tr>
      <%
      	List<BioLookupDeviceFunctionalClassification> classificationList = (List <BioLookupDeviceFunctionalClassification>) request.getAttribute("classificationList");
            if (classificationList != null && !classificationList.isEmpty()){
      %>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.classification" /> :</td>
        <td>
           <select id="classification" name="classification" required>
            <%
            for (BioLookupDeviceFunctionalClassification obj : classificationList){
            %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <%}%>
      
      <%
      	List<BioLookupDeviceCategory> catList = (List <BioLookupDeviceCategory>) request.getAttribute("catList");
            if (catList != null && !catList.isEmpty()){
      %>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.category" /> :</td>
        <td valign="top">
          <span id="category_radio_container" dir="ltr">
          <%
          	for (BioLookupDeviceCategory obj : catList){
          %>
          <input required type="radio" name="category" id="radio" value="<%=obj.getId()%>"><%=obj.getLocalizedName()%>
          <%}%>
          </span>
        </td>
      </tr>
      <%}%>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.agentName" /> :</td>
        <td><input type="text" id="agentName" name="agentName"></td>
      </tr>
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.subcontractor" />  :</td>
        <td><input type="text" id="subcontractor" name="subcontractor"></td>
      </tr>
            <%      
      List<CommonHospitalLocation> hospLocationList = (List<CommonHospitalLocation>)request.getAttribute("hospLocationList");
      if (hospLocationList != null && !hospLocationList.isEmpty()){

      %>
       <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.hospLocation" /> :</td>
        <td>
          <select id="hospLocation" name="hospLocation" required>
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
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.dep" /> :</td>
        <td>
          <select id="dep" name="dep" required>
            <%
            	for (CommonHospitalDepartment obj :depList ){
            %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <%}%>
            <%if (!currentLocation.getHealthCenter()){ %>
				<tr>
					<td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.hospitalRoomFloor" /> :</td>
					<td><input type="text" id="hospitalRoomFloor" name="hospitalRoomFloor"></td>
				</tr>
				<tr>
					<td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.hospitalRoom" /> :</td>
					<td><input type="text" id="hospitalRoom" name="hospitalRoom"></td>
				</tr>

				<tr>
					<td class="side_label_top"> <fmt:message key="jsp.DeviceInfo.hospitalLocationDescription" /> :</td>
					<td><textarea id="hospitalLocationDescription"
							name="hospitalLocationDescription"></textarea></td>
				</tr>
				<tr>
					<%
						}
					%>
				
				<tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.operationDate" />:</td>
        <td><input type="text" class="caldr" id="operationDate" name="operationDate"></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.ta3midNo" /> :</td>
        <td><input type="text" id="ta3midNo" name="ta3midNo"></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.price" />:</td>
        <td><input type="text" id="price" name="price"  data-rule-number="true">
          <strong><fmt:message key="jsp.DeviceInfo.currency" /> </strong></td>
      </tr>
      <tr>
        <td class="side_label_top"> <fmt:message key="jsp.DeviceInfo.devicePic" />:</td>
        <td><input type="file" name="devicePic" id="devicePic"></td>
      </tr>
      <tr>
        <td class="side_label_top"> <fmt:message key="jsp.DeviceInfo.otherAttachments" />:</td>
        <td><input type="file" name="otherAttachments" id="otherAttachments" multiple></td>
      </tr>
      <tr>
        <td class="side_label_middle">  <fmt:message key="jsp.DeviceInfo.withinWarranty" /> :</td>
        <td><input type="radio" value="0" name="withinWarranty" required>
           <fmt:message key="jsp.DeviceInfo.withinWarrantyNo" />
          <input type="radio" value="1" name="withinWarranty" required>
           <fmt:message key="jsp.DeviceInfo.withinWarrantyYes" /> </td>
      </tr>
    <tbody id="grp1">
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.warrantyexpireDate" />:</td>
        <td><input type="text" class="caldr" id="warrantyExpireDate" name="warrantyExpireDate" required></td>
      </tr>
    </tbody>
    <tbody id="grp2">
      <tr>
        <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.withinContract" /> :</td>
        <td><input type="radio" value="0" name="withinContract">
          <fmt:message key="jsp.DeviceInfo.withinContractNo" />
          <input type="radio" value="1" name="withinContract">
          <fmt:message key="jsp.DeviceInfo.withinContractYes" /> </td>
      </tr>
    </tbody>
    <tbody id="grp3">
      <tr>
        <td class="side_label_middle">  <fmt:message key="jsp.DeviceInfo.addedFromAnotherLocation" />  :</td>
        <td><input type="radio" value="0" name="addedFromAnotherLocation">
          <fmt:message key="jsp.DeviceInfo.addedFromAnotherLocationNo" /> 
          <input type="radio" value="1" name="addedFromAnotherLocation">
          <fmt:message key="jsp.DeviceInfo.addedFromAnotherLocationYes" /> </td>
      </tr>
    </tbody>
    <tbody id="grp4">
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.locationName" />  :</td>
        <td><input type="text" id="locationName" name="locationName" required></td>
      </tr>
      <tr>
        <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.addDate" /> :</td>
        <td><input type="text" class="caldr" id="addDate" name="addDate" required></td>
      </tr>
    </tbody>
    <tr>
      <td colspan="2"><h2> <fmt:message key="jsp.DeviceInfo.deviceAccessories" />:</h2></td>
    </tr>
    <tr>
      <td colspan="2"><table width="100%" id="deviceAccessories">
        </table></td>
    </tr>
    <tr>
      <td colspan="2"><h2> <fmt:message key="jsp.DeviceInfo.pmAnnual" /> :</h2></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.DeviceInfo.pmAnnualVisitsNo" />  :</td>
      <td>
        <input type="radio" value="1" name="pmAnnualVisitsNo" required>
        1
        <input type="radio" value="2" name="pmAnnualVisitsNo" required>
        2
        <input type="radio" value="3" name="pmAnnualVisitsNo" required>
        3
        <input type="radio" value="4" name="pmAnnualVisitsNo" required>
        4
      </td>
    </tr>
    
    <tr id="m1">
      <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.pmVisitsMonths1" />:</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months",langCode)%></td>
    </tr>
    <tr id="m2">
      <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.pmVisitsMonths2" />:</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months", langCode)%></td>
    </tr>
    <tr id="m3">
      <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.pmVisitsMonths3" />:</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months", langCode)%></td>
    </tr>
    <tr id="m4">
      <td class="side_label_middle"> <fmt:message key="jsp.DeviceInfo.pmVisitsMonths4" />:</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months",langCode)%></td>
    </tr>
    <tr> 
      <td colspan="2"><input type="submit" value='<%=(inUpdateMode == true)? LocalizationManager.getLiteral("jsp.DeviceInfo.deviceinUpdateMode", langCode) : LocalizationManager.getLiteral("jsp.DeviceInfo.deviceinAddMode", langCode) %>'> <%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
<script type="text/javascript">

$("select[name='pmVisitsMonths']:first").prop("required",true);

function _onChangeQ1(obj){
	var selectedValue = $(obj).filter(':checked').val();
    hideAll(["grp1","grp2","grp3","grp4"]);
	$('input[name="withinContract"]').prop('checked', false);
	$('input[name="addedFromAnotherLocation"]').prop('checked', false);
	if(selectedValue == 0)
	  $("#grp2").show();
	else
	  $("#grp1").show();
}
function _onChangeQ2(obj){
	var selectedValue = $(obj).filter(':checked').val();
        hideAll(["grp3","grp4"]);
	$('input[name="addedFromAnotherLocation"]').prop('checked', false);
	if(selectedValue == 0)
	  $("#grp3").show();
}
function _onChangeQ3(obj){ 
	var selectedValue = $(obj).filter(':checked').val();
	$("#grp4").hide();
	if(selectedValue == 1)
	  $("#grp4").show();
}

function agentAndSubcontractorRequired(){
	var checkedValue = $("input[name='category']:checked").val();
	return (checkedValue == <%=Enums.DEVICE_CATEGORY.A.getDBId()%> || checkedValue == <%=Enums.DEVICE_CATEGORY.B.getDBId()%>);
}

function _onChangeCategory(){
	markAsNotRequired("#agentName , #subcontractor");
	if(agentAndSubcontractorRequired() == true)
	   markAsRequired("#agentName , #subcontractor");	
}

$(function(){
	 
	   hideAll(["grp1","grp2","grp3","grp4"]);
	   $('input:radio[name="withinWarranty"]').change(function(){_onChangeQ1(this)});
	   $('input:radio[name="withinContract"]').change(function(){_onChangeQ2(this)});
	   $('input:radio[name="addedFromAnotherLocation"]').change(function(){_onChangeQ3(this)});
           
           
	   //$("select.weqa2eya_months").css("width","100px");
	   $("select.weqa2eya_months").slice(1, 4).prop("disabled",true);
	   
	   /*
	   // handled at server side
 	   $("form").submit(function(){
		   if($("form").valid()){
			   var visitNO = parseInt($('input:radio[name="pmAnnualVisitsNo"]:checked').val());
			   $("select.weqa2eya_months").slice(1, visitNO).removeAttr("disabled");
	       }
	    }); 
	   
	   */
	   
           $("select[name='pmVisitsMonths']:first").change(function(){setOtherMonthsValues(this)});
           $('input:radio[name="pmAnnualVisitsNo"]').change(function(){$("select[name='pmVisitsMonths']:first").change();});
           
          $('#deviceAccessories').appendGrid({
            caption: null,
            initRows: 0,
            i18n: {rowEmpty:'<fmt:message key="jsp.js.DeviceInfo.rowEmpty" />'},
            columns: [
                    { name: 'accQuantity', display: '<fmt:message key="jsp.js.DeviceInfo.accQuantity" />', type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: {'data-rule-digits': true } },
                    { name: 'accDescription', display: '<fmt:message key="jsp.js.DeviceInfo.accDescription" />', type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '300px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true } },
					
                ]
          }); 
//
//	   attachConditionalDisplayHandlerToRadio('annualVisitNo', {"2":"select_grp1","4":["select_grp1","select_grp2"]});
//
//	   $("select.weqa2eya_months").css("width","70px");
//           $("#pmVisitsMonths").change(function(){setOtherMonthsValues(this)});
//	   $('input:radio[name="pmAnnualVisitsNo"]').change(function(){$("#pmVisitsMonths").change();});
//    
//
//	   $('#deviceAccessories').appendGrid({
//            caption: null,
//            initRows: 0,
//            i18n: {rowEmpty:'لم يتم إضافة أي ملحقات للجهاز'},
//            columns: [
//                    { name: 'accQuantity', display: 'الكمية', type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'} },
//                    { name: 'accDescription', display: 'الوصف' , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '350px', margin: 'auto', 'font-size':'.9em'} },
//					
//                ]
//        }); 
//});

<%if(deviceInfo != null){

    String id = "" + deviceInfo.getId(); 
	        
	String code  = Common.getDisplayString(deviceInfo.getCode(),"");
	String serial = Common.getDisplayString(deviceInfo.getSerialNo(),"");
	String name = Common.getDisplayString(deviceInfo.getName(),"");
	String manufacturerName = Common.getDisplayString(deviceInfo.getManufacturerName(),"");
	String model = Common.getDisplayString(deviceInfo.getModel(),"");
	
	String classification = "";
	if(deviceInfo.getFunctionalClassification() != null)
		classification = "" + deviceInfo.getFunctionalClassification().getId();

	String category = "";
	if(deviceInfo.getCategory() != null)
	   category = ""+ deviceInfo.getCategory().getId();	
	
	String agentName = Common.getDisplayString(deviceInfo.getAgentName(),"");
	
	String subcontractor = Common.getDisplayString(deviceInfo.getSubcontractor(),"");
	
	String hospLocation = "";
	if(deviceInfo.getHospitalLocation() != null)
		hospLocation = "" + deviceInfo.getHospitalLocation().getId();
	
	String dep = "";
	if(deviceInfo.getHospitalDepartment() != null)
	   dep = "" + deviceInfo.getHospitalDepartment().getId();
   
	String hospitalRoomFloor =  Common.getDisplayString(deviceInfo.getFloor(),"");
	String hospitalRoom = Common.getDisplayString(deviceInfo.getHospitalRoom(),"");
	String hospitalLocationDescription =  Common.getDisplayString(deviceInfo.getHospitalLocationDescription(),"");

	String operationDate = DateUtil.getDateString(deviceInfo.getOperationDate());
	String ta3midNo = Common.getDisplayString(deviceInfo.getTa3midNo(),"");
	String price = Common.getDisplayString(deviceInfo.getPrice(),"");
    String otherAttachments = deviceInfo.getOtherAttachments();
    String devicePic = deviceInfo.getDevicePicture();
	
	Boolean withinWarranty = deviceInfo.getWithinWarranty(); 
	String warrantyExpireDate = DateUtil.getDateString(deviceInfo.getWarrantyExpireDate());
	
	Boolean withinContract = deviceInfo.getWithinContract();
        
	Boolean addedFromAnotherLocation = deviceInfo.getAddedFromAnotherLocation();
	String locationName = Common.getDisplayString(deviceInfo.getLocationName(),"");
	String addDate = DateUtil.getDateString(deviceInfo.getAddDate());
	
	String pmAnnualVisitsNo = Common.getDisplayString(deviceInfo.getPmAnnualVisitsNo(),"");
	
	String pmVisitsMonths = "";
	String[] pmVisitsMonthsAry = AppUtil.getPPMVisitMonths(deviceInfo);
	if(pmVisitsMonthsAry != null && pmVisitsMonthsAry.length > 0)
	   pmVisitsMonths = AppUtil.getPPMVisitMonths(deviceInfo)[0];%>
/* set prev values */
    appendIdElement("deviceId","<%=id%>");

    setTextFieldValue("code","<%=code%>");
    setTextFieldValue("serial","<%=serial%>");
    setTextFieldValue("name","<%=name%>");
    setTextFieldValue("manufacturerName","<%=manufacturerName%>");
    setTextFieldValue("model","<%=model%>");
    setComboSelectedValue("classification","<%=classification%>");
    setRadioCheckedValue("category","<%=category%>");
    setTextFieldValue("agentName","<%=agentName%>");
    setTextFieldValue("subcontractor","<%=subcontractor%>");
    setComboSelectedValue("hospLocation","<%=hospLocation%>");
    setComboSelectedValue("dep","<%=dep%>");
	
	setTextFieldValue("hospitalRoomFloor","<%=hospitalRoomFloor%>");
	setTextFieldValue("hospitalRoom","<%=hospitalRoom%>");
	setInnerHTML("hospitalLocationDescription","<%=hospitalLocationDescription%>");
    
	setDateValue("operationDate","<%=operationDate%>");
    setTextFieldValue("ta3midNo","<%=ta3midNo%>");
    setTextFieldValue("price","<%=price%>");
    
  
    //////
    
     <%if (withinWarranty != null) {%>
    	 
    	setRadioCheckedValue("withinWarranty",'<%=(withinWarranty ? "1" : "0")%>');
    	$('input:radio[name="withinWarranty"]:checked').change();
    	setTextFieldValue("warrantyExpireDate","<%=warrantyExpireDate%>");
    
    	<%if (! withinWarranty && withinContract != null){%>
    
	    	setRadioCheckedValue("withinContract",'<%=(withinContract ? "1" : "0" ) %>');
	    	<%if (!withinContract)%>
	    		$('input:radio[name="withinContract"]:checked').change();
	    
	    	<%if (!withinContract && addedFromAnotherLocation  != null){%>
	    		setRadioCheckedValue("addedFromAnotherLocation",'<%=(addedFromAnotherLocation ? "1" : "0")%>');
	    		<%if (addedFromAnotherLocation){%>
	    			$('input:radio[name="addedFromAnotherLocation"]:checked').change();
	    			setTextFieldValue("locationName","<%=locationName%>");
	    		    setDateValue("addDate","<%=addDate%>");
	    		    
	    	<%		}
	    		}
    		}
		}%>
    
    /////

    
    showUploadedFilesList("otherAttachments", <%=HtmlUtil.arrayFromJavaToJavaScript(otherAttachments)%>);
    showUploadedFilesList("devicePic", <%=HtmlUtil.arrayFromJavaToJavaScript(devicePic)%>);
    setRadioCheckedValue("pmAnnualVisitsNo","<%=pmAnnualVisitsNo%>");
    setComboSelectedValue("pmVisitsMonths","<%=pmVisitsMonths%>");

   <%if(deviceAccessoriesCount > 0){
	  BioHospitalDeviceAccessory acc = null;
	  for (int i = 0 ; i < deviceAccessoriesCount;  i++){
		  acc = deviceInfo.getHospitalDeviceAccessories().get(i);
		  out.print("$('#deviceAccessories').appendGrid('appendRow', [{");
		  out.print("accQuantity:'"+acc.getQuantity()+"', accDescription:'"+acc.getDescription()+"'");
		  out.print("}]);");
	  }
   }%>
    
<%}%>
//    $(function(){

           
	  // $('input:radio[name="withinContract"]:checked').change();
	  // $('input:radio[name="addedFromAnotherLocation"]:checked').change();
//
	   

     attachConditionalDisplayHandlerToRadio('pmAnnualVisitsNo', {"1":"m1","2":["m1","m2"],"3":["m1","m2","m3"],"4":["m1","m2","m3","m4"]});
//         $('input:radio[name="pmAnnualVisitsNo"]:checked').change();


// for validation
	  //$("input[name='withinWarranty']").rules("add",{required:true});
	  //$("select[name='pmAnnualVisitsNo']").rules("add",{required:true});
	  // $("input[name='category']").rules("add",{required:true});
      $("input[name='category']").change(function(){_onChangeCategory()});
	  $("input[name='category']:checked").change();
	  
	  $("#code").rules("add",{
		  remote:{
			  url:"bio/CheckDeviceInfoDuplication",
			  data: {
					  // code: function(){return $("#code").val()},
					  deviceId: function(){if($("#deviceId").length) return $("#deviceId").val(); return "0"}		
			         }
		          }
	  });
	  
	  $("#serial").rules("add",{
		  remote:{
			  url:"bio/CheckDeviceInfoDuplication",
			  data: {
					  // serial: function(){return $("#serial").val()},
					  deviceId: function(){if($("#deviceId").length) return $("#deviceId").val(); return "0"}		
			         }
		          }
	  });
	  
 
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
			//var results = $.ui.autocomplete.filter(bioLookupDeviceNameAutocompleteJsonArray, request.term);
			//response(results.slice(0, 10));
		}
	});
		 // commented for future use
	// 	var bioLookupSubcontractorAutocompleteJsonArraychange  = function( event, ui ) {
	// 	    if(ui.item == null) 
	// 	    {
	// 	        this.value='';
	// 	        alert('Please select a value from the list'); // or a custom message
	// 	    }
	// 	}
		
		 

 	$("#subcontractor, #agentName").autocomplete({
		source : function(request, response) {
			var results = $.map(bioLookupSubcontractorAutocompleteJsonArray, function (tag) {
				if (tag.toUpperCase().indexOf(request.term.toUpperCase()) === 0) {
				    return tag;
				}
			});
			response(results);
			//	var results = $.ui.autocomplete.filter(bioLookupSubcontractorAutocompleteJsonArray, request.term);
			//  response(results.slice(0, 10));
	    }
	// 	, change: bioLookupSubcontractorAutocompleteJsonArraychange
	}); 
	
	 
});
</script>
 
</body>
</html>
