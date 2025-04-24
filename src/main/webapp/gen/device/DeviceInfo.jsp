<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,com.iassets.common.entity.*,java.util.*"%>
<html dir="rtl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
	String langCode = WebUtil.getSessionLanguage(request);

    GenHospitalDevice deviceInfo = (GenHospitalDevice)request.getAttribute(Default.DEVICE_INFO_ATTR_NAME);
    boolean inUpdateMode = deviceInfo != null;
    
    int deviceAccessoriesCount = 0;
    if (deviceInfo !=null && deviceInfo.getHospitalDeviceAccessories() != null)
        deviceAccessoriesCount = deviceInfo.getHospitalDeviceAccessories().size();
    
    CommonHospital currentLocation = (CommonHospital)request.getAttribute("currentLocation");
    

%>
</head>
<body>
<form method="post" action="gen/DeviceInfoProcess" enctype="multipart/form-data">
  <table class="layout_grid">
    <tbody>
      <tr>
        <td colspan="2"><h1 class="page_title"> <%=(inUpdateMode == true)?"شاشة تعديل بيانات جهاز":"شاشة إضافة جهاز جديد"%> </h1></td>
      </tr>
      <tr>
        <td class="side_label_middle">الكود  :</td>
        <td><input type="text" id="code" name="code" data-msg-remote="يوجد  جهاز بنفس الكود" required></td>
      </tr>
      <tr>
        <td class="side_label_middle">الرقم التسلسلي :</td>
        <td><input type="text" id="serial" name="serial"  data-msg-remote="يوجد  جهاز بنفس الرقم التسلسلي" required></td>
      </tr>
      <tr>
        <td class="side_label_middle">اسم الجهاز :</td>
        <td><input type="text" size="40" id="name" name="name" required></td>
      </tr>
      <tr>
        <td class="side_label_middle">الشركة الصانعة :</td>
        <td><input type="text" size="40" id="manufacturerName" name="manufacturerName" required></td>
      </tr>
      <tr>
        <td class="side_label_middle">الموديل :</td>
        <td><input type="text" id="model" name="model" required></td>
      </tr>
      <tr>
		<td class="side_label_top">ملاحظات / مواصفات :</td>
		<td><textarea id="specs" name="specs"></textarea></td>
	  </tr>
      <%--
      	List<GenLookupDeviceCategory> catList = (List <GenLookupDeviceCategory>) request.getAttribute("catList");
           if (catList != null && !catList.isEmpty()){
      %>
      <tr>
        <td class="side_label_middle">الفئة :</td>
        <td colspan="2" valign="top">
          <span id="category_radio_container" dir="ltr">
          <%
          	for (GenLookupDeviceCategory obj : catList){
          %>
          <input required type="radio" name="category" id="radio" value="<%=obj.getId()%>"><%=obj.getName()%>
          <%}%>
          </span>
        </td>
      </tr>
      <%}%>
      --%>
      <tr>
        <td class="side_label_middle">الوكيل :</td>
        <td><input type="text" id="agentName" name="agentName"></td>
      </tr>
      <tr>
        <td class="side_label_middle">مقاول الباطن :</td>
        <td><input type="text" id="subcontractor" name="subcontractor"></td>
      </tr>
            
      <%      
      List<CommonHospitalLocation> hospLocationList = (List<CommonHospitalLocation>)request.getAttribute("hospLocationList");
      if (hospLocationList != null && !hospLocationList.isEmpty()){

      %>
       <tr>
        <td class="side_label_middle">الموقع :</td>
        <td>
          <select id="hospLocation" name="hospLocation" onchange="_onChangeHospitalLocation(this)" required>
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
        <td>
          <select id="dep" name="dep" required>
            <%
            	for (CommonHospitalDepartment dep :depList ){
            %>
            <option title="<%=dep.getLocation().getId()%>"  value="<%=dep.getId()%>"><%=dep.getLocalizedName()%></option>
            <%}%>
          </select>
        </td>
      </tr>
      <% }%>
      
      <%if (!currentLocation.getHealthCenter()){ %>
      <tr>
        <td class="side_label_middle">الغرفة :</td>
        <td><input type="text"  id="hospitalRoom" name="hospitalRoom"></td>
      </tr>
      
      <tr>
        <td class="side_label_top">الوصف الدقيق للمكان :</td>
        <td><textarea id="hospitalLocationDescription" name="hospitalLocationDescription"></textarea></td>
      </tr>
      <tr>
      <%} %>
     
      <tr>
        <td class="side_label_middle">تاريخ التركيب والتشغيل :</td>
        <td><input type="text" class="caldr" id="operationDate" name="operationDate"></td>
      </tr>
      <tr>
        <td class="side_label_middle">رقم التعميد  :</td>
        <td><input type="text" id="ta3midNo" name="ta3midNo"></td>
      </tr>
      <tr>
        <td class="side_label_middle">سعر الجهاز :</td>
        <td><input type="text" id="price" name="price"  data-rule-number="true">
          <strong> ريال سعودي</strong></td>
      </tr>
      <tr>
        <td class="side_label_top">مرفقات الجهاز :</td>
        <td><input type="file" name="otherAttachments" id="otherAttachments" multiple></td>
      </tr>
      <tr>
        <td class="side_label_middle"> داخل الضمان :</td>
        <td><input type="radio" value="0" name="withinWarranty" required>
          لا
          <input type="radio" value="1" name="withinWarranty" required>
          نعم </td>
      </tr>
    <tbody id="grp1">
      <tr>
        <td class="side_label_middle">تاريخ انتهاء الضمان :</td>
        <td><input type="text" class="caldr" id="warrantyExpireDate" name="warrantyExpireDate" required></td>
      </tr>
    </tbody>
    <tbody id="grp2">
      <tr>
        <td class="side_label_middle"> داخل العقد :</td>
        <td><input type="radio" value="0" name="withinContract">
          لا
          <input type="radio" value="1" name="withinContract">
          نعم </td>
      </tr>
    </tbody>
    <tbody id="grp3">
      <tr>
        <td class="side_label_middle"> منقول من موقع آخر :</td>
        <td><input type="radio" value="0" name="addedFromAnotherLocation">
          لا
          <input type="radio" value="1" name="addedFromAnotherLocation">
          نعم</td>
      </tr>
    </tbody>
    <tbody id="grp4">
      <tr>
        <td class="side_label_middle">وارد من :</td>
        <td><input type="text" id="locationName" name="locationName" required></td>
      </tr>
      <tr>
        <td class="side_label_middle">ورد بتاريخ :</td>
        <td><input type="text" class="caldr" id="addDate" name="addDate" required></td>
      </tr>
    </tbody>
    <tr>
      <td colspan="2"><h2>ملحقات الجهاز :</h2></td>
    </tr>
    <tr>
      <td colspan="2"><table width="100%" id="deviceAccessories">
        </table></td>
    </tr>
    <tr>
      <td colspan="2"><h2>بيانات زيارات الوكيل :</h2></td>
    </tr>
    <tr>
      <td class="side_label_middle">عدد الزيارات السنوية :</td>
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
      <td class="side_label_middle">الشهر الأول :</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months", langCode)%></td>
    </tr>
    <tr id="m2">
      <td class="side_label_middle">الشهر الثاني :</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months", langCode)%></td>
    </tr>
    <tr id="m3">
      <td class="side_label_middle">الشهر الثالث :</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months" , langCode)%></td>
    </tr>
    <tr id="m4">
      <td class="side_label_middle">الشهر الرابع :</td>
      <td><%=HtmlUtil.getMonthesAsHtmlSelect("pmVisitsMonths", "weqa2eya_months", langCode)%></td>
    </tr>
    
      <%
      List<GenLookupInternalPpmPeriod> ppmPeriods = (List<GenLookupInternalPpmPeriod>) request.getAttribute("ppmPeriods");
      if (ppmPeriods != null && !ppmPeriods.isEmpty()) {
      %>
    	  
    <tr>
    	<td colspan="2"><h2>Specs</h2>
    	</td>
    	</tr>
    	   <tr> 
      <td colspan="2">
			<table style="width:60%" class="data_table">
		       	 <tr>
			     	 <th>spec_HP</th>
			     	  <td><input type="text" size="10"  id=spec_HP name="spec_HP"  data-rule-number="true"></td>
			      	 <th>spec_RPM</th>
			      	  <td><input type="text" size="10"  id="spec_RPM" name="spec_RPM"  data-rule-number="true"></td>
			      	  
			     </tr>
			     <tr>
			      	 <th>spec_P</th>
			      	  <td><input type="text" size="10"  id="spec_P" name="spec_P"  data-rule-number="true"></td>
			      	 <th>spec_T</th>
			      	  <td><input type="text" size="10"  id="spec_T" name="spec_T"  data-rule-number="true"></td>
			      </tr>
			     <tr>	  
			      	  
			      	 <th>spec_H</th>
			      	  <td><input type="text" size="10"  id="spec_H" name="spec_H"  data-rule-number="true"></td>
			      	 <th>spec_KG</th>
			      	  <td><input type="text" size="10"  id="spec_KG" name="spec_KG"  data-rule-number="true"></td>
			      	  
			      	  </tr>
			     <tr>
			      	 <th>spec_Q</th>
			      	  <td><input type="text" size="10"  id="spec_Q" name="spec_Q"  data-rule-number="true"></td>
			      	 <th>spec_PH</th>
			      	  <td><input type="text" size="10"  id="spec_PH" name="spec_PH"  data-rule-number="true"></td>
			      	  </tr>
			     <tr>
			      	  
			      	 <th>spec_IP</th>
			      	  <td><input type="text" size="10"  id="spec_IP" name="spec_IP"  data-rule-number="true"></td>
			      	 <th>spec_A</th>
			      	  <td><input type="text" size="10"  id="spec_A" name="spec_A"  data-rule-number="true"></td>
			      	  
			      	  </tr>
			     <tr>
			      	 <th>spec_V</th>
			      	  <td><input type="text" size="10"  id="spec_V" name="spec_V"  data-rule-number="true"></td>
			      	 <th>spec_KW</th>
			      	  <td><input type="text" size="10"  id="spec_KW" name="spec_KW"  data-rule-number="true"></td>
			      	 </tr>
			    
			      	 
			      </tr>
			     <tr> 
			    
			      </table>      
      </td>
    </tr>
      
    	
    	
    	  
    <tr>
      <td colspan="2"><h2>جدولة الصيانة الوقائية :</h2></td>
    </tr>
    
    
<%
List<GenLookupJobOrderCategory>  genLookupJobOrderCategoryList=(List<GenLookupJobOrderCategory>) request.getAttribute("genLookupJobOrderCategoryList");
if(genLookupJobOrderCategoryList==null)
	genLookupJobOrderCategoryList=new ArrayList<GenLookupJobOrderCategory>();

	%>						
						
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
							int ppmCatId=deviceInfo.getLookupCategoryId();
/* 				when we check category by roles and only one result returned 			
if(genLookupJobOrderCategoryList!=null &&genLookupJobOrderCategoryList.size()==1){
								checked=" checked='checked' ";
								hiddenStyle="";
								disabled="";
							} */
							for (GenLookupJobOrderCategory genLookupJobOrderCategory : genLookupJobOrderCategoryList){
							if(ppmCatId==genLookupJobOrderCategory.getId())
								checked=" checked='checked' ";
								else
								checked="";
							%>
							
							 <input required type="radio" <%=checked%> name="gmp_id" id="radio" value="<%=genLookupJobOrderCategory.getId()%>"><%=genLookupJobOrderCategory.getLocalizedName()%>
						<%}	%>
						  </td>
						</tr>
    
    <tr>
	    <td colspan="2">
		    <table style="width:80%" class="data_table">
		       	 <tr>
			     	 <th>المدة</th>
			      	 <th>نموذج الإجراءات</th>
			      </tr>
			  <%
		      	for (GenLookupInternalPpmPeriod ppmPeriod : ppmPeriods ) {
		      %>
		      
			      <tr>
			     	 <td valign="top"><input type="checkbox" onclick="_onClickPpmPeriod(this)" name="ppmPeriod" value="<%=ppmPeriod.getId()%>" />&nbsp;<%=ppmPeriod.getName()%></td>
			      	 <td class="checklist_container" valign="top"><input type="file" name="checklist_file_<%=ppmPeriod.getId()%>" id="checklist_file_<%=ppmPeriod.getId()%>"></td>
			      </tr>
		      
		      <%}%>
		      </table>
	      </td>
    </tr>
   <%}%>
     

     
    <tr> 
      <td colspan="2"><input type="submit" value='<%=(inUpdateMode == true)?"عدل بيانات الجهاز":"أضف الجهاز"%>'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
    
       
    
  </table>
</form>
<script type="text/javascript">

$("select[name='pmVisitsMonths']:first").prop("required",true);

function _onClickPpmPeriod(obj){
	var fileContainer = $(obj).closest("tr").find("td:eq(1)");
	var checklistObjIdSelector = "#checklist_file_" + $(obj).val();

	if($(obj).is(":checked")){
		fileContainer.children().show();
		markAsRequired(checklistObjIdSelector);
	}else{
		fileContainer.children().hide();
		markAsNotRequired(checklistObjIdSelector);
	}
}

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

<%--
function agentAndSubcontractorRequired(){
	var checkedValue = $("input[name='category']:checked").val();
	return (checkedValue == <%=Enums.DEVICE_CATEGORY.A.getDBId()%> || checkedValue == <%=Enums.DEVICE_CATEGORY.B.getDBId()%>);
}

function _onChangeCategory(){
	markAsNotRequired("#agentName , #subcontractor");
	if(agentAndSubcontractorRequired() == true)
	   markAsRequired("#agentName , #subcontractor");	
} 
--%>
$(function(){
	   
		$("td.checklist_container").children().hide();   
		hideAllDepartmentsOptions();
	   
	   
	   hideAll(["grp1","grp2","grp3","grp4"]);
	   $('input:radio[name="withinWarranty"]').change(function(){_onChangeQ1(this)});
	   $('input:radio[name="withinContract"]').change(function(){_onChangeQ2(this)});
	   $('input:radio[name="addedFromAnotherLocation"]').change(function(){_onChangeQ3(this)});
           
           
	   //$("select.weqa2eya_months").css("width","100px");
	   $("select.weqa2eya_months").slice(1, 4).prop("disabled",true);
	   $("form").submit(function(){
		   if($("form").valid()){
			   var visitNO = parseInt($('input:radio[name="pmAnnualVisitsNo"]:checked').val());
			   $("select.weqa2eya_months").slice(1, visitNO).removeAttr("disabled");
	       }
	    });
	   
           $("select[name='pmVisitsMonths']:first").change(function(){setOtherMonthsValues(this)});
           $('input:radio[name="pmAnnualVisitsNo"]').change(function(){$("select[name='pmVisitsMonths']:first").change();});
           
          $('#deviceAccessories').appendGrid({
            caption: null,
            initRows: 0,
            i18n: {rowEmpty:'لم يتم إضافة أي ملحقات للجهاز'},
            columns: [
                    { name: 'accQuantity', display: 'الكمية', type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '50px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true }, ctrlAttr: {'data-rule-digits': true } },
                    { name: 'accDescription', display: 'الوصف' , type: 'text', displayCss:{'text-align':'center'}, ctrlCss: { display:'block', width: '300px', margin: 'auto', 'font-size':'.9em'}, ctrlProp: { required: true } },
					
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
	String specs = Common.getDisplayString(deviceInfo.getSpecs(), "");
	
	/*
	String category = "";
	if(deviceInfo.getCategory() != null)
	   category = ""+ deviceInfo.getCategory().getId();	
	*/
	
	String agentName = Common.getDisplayString(deviceInfo.getAgentName(),"");
	
	String subcontractor = Common.getDisplayString(deviceInfo.getSubcontractor(),"");
	
	String hospLocation="";
	if(deviceInfo.getHospitalLocation() != null)
		hospLocation = "" + deviceInfo.getHospitalLocation().getId();
	
	
	String dep = "";
	if(deviceInfo.getHospitalDepartment() != null)
	   dep = "" + deviceInfo.getHospitalDepartment().getId();
	
	String hospitalLocationDescription = Common.getDisplayString(deviceInfo.getHospitalLocationDescription(),"");
	String hospitalRoom = Common.getDisplayString(deviceInfo.getHospitalRoom(),"");

	
	
	String operationDate = DateUtil.getDateString(deviceInfo.getOperationDate());
	String ta3midNo = Common.getDisplayString(deviceInfo.getTa3midNo(),"");
	String price = Common.getDisplayString(deviceInfo.getPrice(),"");
    String otherAttachments = deviceInfo.getOtherAttachments();
	
	Boolean withinWarranty = deviceInfo.getWithinWarranty(); 
	String warrantyExpireDate = DateUtil.getDateString(deviceInfo.getWarrantyExpireDate());
	
	Boolean withinContract = deviceInfo.getWithinContract();
        
	Boolean addedFromAnotherLocation = deviceInfo.getAddedFromAnotherLocation();
	String locationName = Common.getDisplayString(deviceInfo.getLocationName(),"");
	String addDate = DateUtil.getDateString(deviceInfo.getAddDate());
	
	String pmAnnualVisitsNo = Common.getDisplayString(deviceInfo.getPmAnnualVisitsNo(),"");
	
	String spec_HP  = Common.getDisplayString(deviceInfo.getSpecHP (),"");
	String spec_RPM = Common.getDisplayString(deviceInfo.getSpecRPM(),"");
	String spec_P   = Common.getDisplayString(deviceInfo.getSpecP  (),"");
	String spec_T   = Common.getDisplayString(deviceInfo.getSpecT  (),"");
	String spec_H   = Common.getDisplayString(deviceInfo.getSpecH  (),"");
	String spec_KG  = Common.getDisplayString(deviceInfo.getSpecKG (),"");
	String spec_Q   = Common.getDisplayString(deviceInfo.getSpecQ  (),"");
	String spec_PH  = Common.getDisplayString(deviceInfo.getSpecPH (),"");
	String spec_IP  = Common.getDisplayString(deviceInfo.getSpecIP (),"");
	String spec_A   = Common.getDisplayString(deviceInfo.getSpecA  (),"");
	String spec_V   = Common.getDisplayString(deviceInfo.getSpecV  (),"");
	String spec_KW  = Common.getDisplayString(deviceInfo.getSpecKW (),"");	
	
	
	
	String pmVisitsMonths = "";
	String[] pmVisitsMonthsAry = HtmlUtil.getPPMVisitMonths(deviceInfo);
	if(pmVisitsMonthsAry != null && pmVisitsMonthsAry.length > 0)
	   pmVisitsMonths = HtmlUtil.getPPMVisitMonths(deviceInfo)[0];
	   
	List<GenHospitalDeviceInternalPpmSchedule> ppmSchedules = deviceInfo.getHospitalDeviceInternalPpmSchedules();
	if(ppmSchedules==null)
		ppmSchedules=new ArrayList<GenHospitalDeviceInternalPpmSchedule>();
	   %>
/* set prev values */
    appendIdElement("deviceId","<%=id%>");

    setTextFieldValue("code","<%=code%>");
    setTextFieldValue("serial","<%=serial%>");
    setTextFieldValue("name","<%=name%>");
    setTextFieldValue("manufacturerName","<%=manufacturerName%>");
    setTextFieldValue("model","<%=model%>");
    setInnerHTML("specs","<%=specs%>");
  <%--   setRadioCheckedValue("category","<%=category%>"); --%>
    setTextFieldValue("agentName","<%=agentName%>");
    setTextFieldValue("subcontractor","<%=subcontractor%>");
    
    setComboSelectedValue("hospLocation","<%=hospLocation%>");
	$('#hospLocation').change();
    setComboSelectedValue("dep","<%=dep%>");

    setTextFieldValue("hospitalRoom","<%=hospitalRoom%>");
    setInnerHTML("hospitalLocationDescription","<%=hospitalLocationDescription%>");
    setDateValue("operationDate","<%=operationDate%>");
    setTextFieldValue("ta3midNo","<%=ta3midNo%>");
    setTextFieldValue("price","<%=price%>");
    
    setTextFieldValue("spec_HP","<%=spec_HP %>");
    setTextFieldValue("spec_RPM","<%=spec_RPM%>");
    setTextFieldValue("spec_P","<%=spec_P  %>");
    setTextFieldValue("spec_T","<%=spec_T  %>");
    setTextFieldValue("spec_H","<%=spec_H  %>");
    setTextFieldValue("spec_KG","<%=spec_KG %>");   
    setTextFieldValue("spec_Q","<%=spec_Q  %>");
    setTextFieldValue("spec_PH","<%=spec_PH %>");
    setTextFieldValue("spec_IP","<%=spec_IP %>");
    setTextFieldValue("spec_A","<%=spec_A  %>");
    setTextFieldValue("spec_V","<%=spec_V  %>");
    setTextFieldValue("spec_KW","<%=spec_KW %>");
  
    
  
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
    setRadioCheckedValue("pmAnnualVisitsNo","<%=pmAnnualVisitsNo%>");
    setComboSelectedValue("pmVisitsMonths","<%=pmVisitsMonths%>");

   <%if(deviceAccessoriesCount > 0){
	   GenHospitalDeviceAccessory acc = null;
	  for (int i = 0 ; i < deviceAccessoriesCount;  i++){
		  acc = deviceInfo.getHospitalDeviceAccessories().get(i);
		  out.print("$('#deviceAccessories').appendGrid('appendRow', [{");
		  out.print("accQuantity:'"+acc.getQuantity()+"', accDescription:'"+acc.getDescription()+"'");
		  out.print("}]);");
	  }
   }
   

	for(GenHospitalDeviceInternalPpmSchedule ppmSchedule:ppmSchedules){
		Integer ppmPeriodId = ppmSchedule.getPpmPeriod().getId();
		String cbSelector = "input:checkbox[name='ppmPeriod'][value='" + ppmPeriodId + "']";
	%>
		$("<%=cbSelector%>").prop('checked',true);
		showUploadedFilesList("checklist_file_"+ <%=ppmPeriodId%>, <%=HtmlUtil.arrayFromJavaToJavaScript(ppmSchedule.getChecklistFileUrl())%>);
		_onClickPpmPeriod($("<%=cbSelector%>"));
	<%
	}
}%>
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
      //$("input[name='category']").change(function(){_onChangeCategory()});
	  //$("input[name='category']:checked").change();
	  
	  $("#code").rules("add",{
		  remote:{
			  url:"gen/CheckDeviceInfoDuplication",
			  data: {
					  // code: function(){return $("#code").val()},
					  deviceId: function(){if($("#deviceId").length) return $("#deviceId").val(); return "0"}		
			         }
		          }
	  });
	  
	  $("#serial").rules("add",{
		  remote:{
			  url:"gen/CheckDeviceInfoDuplication",
			  data: {
					  // serial: function(){return $("#serial").val()},
					  deviceId: function(){if($("#deviceId").length) return $("#deviceId").val(); return "0"}		
			         }
		          }
	  });
	  
});

</script>
</body>
</html>
