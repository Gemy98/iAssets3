var manualValidationPassed = true;

function setCookie(name, value){
	$.cookie(name, value, {path: '/'});
}

function getCookie(name){
	//alert($.cookie(name));
	return $.cookie(name);
}

function removeCookie(name){
	$.removeCookie(name, {path: '/'});
	// setCookie(name, null);
}

function activateNotificationStopperCookie(obj, cookieName) {
	$(obj).closest("div").remove();
	setCookie(cookieName, "1");
}

function _onClickPrintJobOrderBtn(){
	
	var formObj = $("form:first");
	// formObj.validate();
	
    if(formObj.valid()){
	    	//formObj.append("<input type='hidden' name='saveThenReturn' value='1' />");
	    	formObj.append("<input type='hidden' name='showJobOrderReportForPrint' value='1' />");
	    	formObj.prop("target", "_blank");
	    	//alert("سيتم عرض تقرير أمر العمل بعد الحفظ لتتمكن من طباعته أو حفظ نسخة إلكترونية منه");
	    	formObj.submit();
	    	// reset all previous actions
	    	$("input[name='showJobOrderReportForPrint']").remove();
	    	formObj.prop("target", "_self");
	    	// setTimeout(function(){window.location.href = appDirVar + "/FollowupJobOrderSearch"},3000);
    }	
}

function _onClickJobOrderFollowupSaveBtn(){
	
	var formObj = $("form:first");
	// formObj.validate();
	
    if(formObj.valid()){
    	
    	if($("#closed") && $("#closed").is(":checked")){
    		formObj.submit();
    		return;
    	}
    	
    	if(confirm(app_onClickJobOrderFollowupSaveBtn_confirm)){
	    	formObj.append("<input type='hidden' name='saveThenReturn' value='1' />");
	    	formObj.append("<input type='hidden' name='showJobOrderReport' value='1' />");
	    	formObj.prop("target", "_blank");
	    	//alert("سيتم عرض تقرير أمر العمل بعد الحفظ لتتمكن من طباعته أو حفظ نسخة إلكترونية منه");
	    	formObj.submit();
	    	setTimeout(function(){window.location.href = appDirVar + "/FollowupJobOrderSearch"},3000);
    	}else{
    		formObj.submit();
    	}
    	//return true;
    }	
    
    //return false;
}

function setOtherMonthsValues(srcObj) {
	var m1 = parseInt($(srcObj).val());
	var visitNO = parseInt($('input:radio[name="pmAnnualVisitsNo"]:checked').val());
	// alert("m1 : " + m1);
	// alert("type : " + type);
	var nextVal = m1;
	
	$("select.weqa2eya_months").slice(1, visitNO).each(function(index) {
		if(m1 === parseInt(m1, 10)){// check if m1 is integer
			nextVal += 12 / visitNO;
			if (nextVal > 12)
				nextVal = nextVal % 12;
	
			$(this).val(nextVal);
		}else{
			$(this).prop('selectedIndex',0);
		}
	});
	 
}

function _onSubmitDeviceSearchForm() {
	if ($("#deviceCode").val().trim() == ""
			&& $("#deviceSerial").val().trim() == "") {
		manualValidationPassed = false;
		alert(app_onSubmitDeviceSearchForm_alert);
		return false;
	}
	manualValidationPassed = true;
	return true;
}

function _onSubmitJobOrderSearchForm() {
	if ($("#jobOrderNo").val().trim() == ""
			&& $("#deviceCode").val().trim() == ""
			&& $("#deviceSerial").val().trim() == "") {
		manualValidationPassed = false;
		alert(app_onSubmitJobOrderSearchForm_alert);
		return false;
	}
	manualValidationPassed = true;
	return true;
}

function _onSubmitJobOrderInquiryForm(){
	if ($("#jobOrderNo").val().trim() == "") {
		manualValidationPassed = false;
		alert(app_onSubmitJobOrderInquiryForm_alert);
		return false;
    }
	manualValidationPassed = true;
    return true;
}


function _onSubmitSPCategorySearchForm() {
	if ($("#code").val().trim() == "") {
		manualValidationPassed = false;
		alert(app_onSubmitSPCategorySearchForm_alert);
		return false;
	}
	manualValidationPassed = true;
	return true;
}

function getPPMVisit() {

	var subcontractor = $("#subcontractor").val();
	var selectedMonth = $("#visitMonth").val();
	var selectedYear = $("#visitYear").val();
	var deviceId = $("#deviceId").val();
	var dest = $("#dest").val();

	// if (selectedMonth > 0 && selectedYear > 0).prop("selectedIndex")

	if ($("#visitMonth").prop("selectedIndex") != 0
			&& $("#visitYear").prop("selectedIndex") != 0)
		$("#content_container").load(appDirVar + "/PPMVisitDisplay", {
			visitMonth : selectedMonth,
			visitYear : selectedYear,
			deviceId : deviceId,
			subcontractor: subcontractor,
			dest : dest
		}, function() {
			initPage()
		});

}

function initPage() {
	// disable right click  
//	document.addEventListener('contextmenu', event => event.preventDefault());
	  
	$("body").prop("class", "pure-skin-mine");
	// $("form").prop("class", "pure-form");
	$("form").addClass("pure-form");
	$("table.layout_grid tr:last > td").each(
     function(){
		if($(this).find("input#biomedapp_return_btn").length > 0)
		   $(this).css({"padding-top":"30px","padding-right":"50px"});		        
	});
 

	// init calendar elements
	// beforeshow written in a way to make field is not editable
	$("input[class~='caldr']").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "-30:+5",
		beforeShow : function(input, inst) {if($(input).hasClass("noteditable")) return false;}
	}).attr("readonly","true");

	// init file upload elements
	initFileInputElements();

	// $("select:not(.auto_off)").each(function(){alert("init :: "+this.name + "
	// :: "+$(this).find("option[selected='selected']").size() )})
	// add default option to dropdown lists
	$("select:not(.auto_off)").prepend("<option value=''>"+app_initPage_plzSelect+"</option>").each(
			function() {
				if ($(this).find("option[selected='selected']").size() == 0)
					this.selectedIndex = 0
			});
	$("select.auto_add_all").prepend("<option>"+app_initPage_alloptions+"</option>").each(
			function() {
				if ($(this).find("option[selected='selected']").size() == 0)
					this.selectedIndex = 0
			});

	// style buttons with pure framework
	$("input[type='button']").addClass("pure-button");
	$("input[type='submit']").addClass("pure-button pure-button-primary");

	// add margin between horizontal radio group
	$("input[type='radio']").css("margin-left", "2px");
	$("input[type='radio']").next().css("margin-right", "15px");

	// special css to handle the category radios properly
	$("input[type='radio'][name='category']").css("margin-right", "2px");
	$("input[type='radio'][name='category']").next().css("margin-left", "15px");

	// add margin between horizontal checkbox group
	$("input[type='checkbox']").css("margin-left", "2px");
	$("input[type='checkbox']").next().css("margin-right", "15px");
	
	// special css to handle the category checkboxes properly
	$("input[type='checkbox'][name='category']").css("margin-right", "2px");
	$("input[type='checkbox'][name='category']").next().css("margin-left", "20px");

	appendCommonQueryStrToReportsMenuItems();

	$("form").attr('novalidate', 'novalidate');
	
	if($("form").not("[class~='manual_validate']").length){
		$("form").not("[class~='manual_validate']").validate({
	
							// ignore: ":hidden:not(input:file)", // to allow hidden
							// fields to validated
	                        // debug: true,
							errorPlacement : function(error, element) { // to adjust
								// the radio and checkbox group msg placement
								if ($(element).is("input:file")) {
									if (element.closest("label").parent().find("span[class='requiredField']").length > 0)
										error.insertAfter(element.closest("label").parent().find("span[class='requiredField']"));
									else
										error.insertAfter(element.closest("label"))
								} else
										error.appendTo(element.parent());	
							},
					        submitHandler: function(form) { // <- pass 'form' argument in
					            // var submitBtn = $(form).find("input[type='submit']:first");
					            var formTarget = $(form).attr("target");
					            var overlayDiv = "<div class='dimmed'><p><img width='128' height='128' src='image/progressbar.gif' />"+app_initPage_waiting_inprogress+"</p></div>"
					            // if(submitBtn && formTarget != "_blank"){
					           if(manualValidationPassed == true){
						            if(formTarget != "_blank"){
						            	$("#container").append(overlayDiv);
						                //submitBtn.attr("disabled", true);
						            	//submitBtn.attr("value","جاري إتمام العملية ...");
						            }
						            // alert("submitting");
						            form.submit();
					           }
					        }
					        
						// debug:false,
						// focusInvalid: false,
						// onfocusout: false
						// onkeyup: false
						// rules:{
						// plannedStartDate: {
						// required:true,
						// dpDate:true,
						// dpCompareDate: ['before', '#plannedEndDate']
						// },
						// plannedEndDate:{
						// required:true,
						// dpDate:true,
						// dpCompareDate: ['after', '#plannedStartDate']
						// }
						//			  
						// }
						});
	
		initRequiredFields();
	}

	// $("form select[selectrequired]").each((function(){$(this).rules("add",
	// {selectrequired: true})}))
	// markAsRequired("form *[required]");
}

function initNotification(cPath) {
	// init the job order notifications socket
	/*
	 * var eventSource = new EventSource(cPath +
	 * "/JobOrderNotificationsEmitter"); eventSource.onmessage = function(event) {
	 * $("#job_order_notification_ph").html(event.data); //alert(event.data);
	 */
	// }
}

function appendCommonQueryStrToReportsMenuItems() {

	// $("li.reports_menu_items > ul > li > a").each(
	$("#dhtmlgoodies_slidedown_menu > ul > li:not(.no_append) > ul a").each(
	//$("#dhtmlgoodies_slidedown_menu a").each(
			function() {

				// initially equal to the original href
				var newHref = $(this).prop("href");

				if (newHref.indexOf("purpose=") == -1) {
					newHref += ((newHref.indexOf("?") == -1) ? "?" : "&")
							+ "purpose=" + encodeURIComponent($(this).html());
					$(this).prop("href", newHref);
				}
			});

	// add report flag
	$("li.reports_menu_items > ul > li > a").each(function() {

		if($(this).attr('target') == '_blank')
		    $(this).click(function(){returnToWelcomePage()});
		
		// initially equal to the original href
		var newHref = $(this).prop("href");

		if (newHref.indexOf("report=") == -1) {
			newHref += ((newHref.indexOf("?") == -1) ? "?" : "&") + "report=1";
			$(this).prop("href", newHref);
		}
	});
}

function returnToWelcomePage(){
	window.location.href = $("#home_lnk").attr("href");
}

// init the notifications websocket
var appServerUrl = "ws://"+appServerIP + appContext;
var jobOrderWsCon;

function connectToJobOrderNotifier(siteId, langCode , gmpDepId) {
	var url = appServerUrl + "/"+appDirVar+"/JobOrdersNotifier";
	
	//alert(siteId);
	//alert(gmpDepId);
	
	if(siteId && siteId != 0)
		url += "?siteId=" + siteId;
	
	if(gmpDepId && gmpDepId != 0)
		url += "&gmpDepId=" + gmpDepId;
	
	url+= "&langCode=" + langCode ;
	
	jobOrderWsCon = new WebSocket(url);

	jobOrderWsCon.onopen = function() {
		console.log('WebSocket connection has been opened');
	}

	jobOrderWsCon.onclose = function() {
		console.log('WebSocket connection has been closed');
	}

	jobOrderWsCon.onerror = function(error) {
		console.log('WebSocket connection has error detected: ' + error);
	}

	jobOrderWsCon.onmessage = function(event) {
		$("#jo_notifications_ph").remove();
		var data = event.data;
		$("#notifications_Container").append(event.data);
		
	}
}

//----------------------------------------------------------------
var ppmScheduleWsCon;

function connectToPpmScheduleNotifier(siteId,gmpDepId) {
	var url = appServerUrl + "/"+ appDirVar +"/PpmNotifier";
	if(siteId && siteId != 0)
		url += "?siteId=" + siteId+"&gmpDepId=" + gmpDepId;
	
		else
			url += "?gmpDepId=" + gmpDepId;
	ppmScheduleWsCon = new WebSocket(url);

	ppmScheduleWsCon.onopen = function() {
		console.log('WebSocket connection has been opened');
	}

	ppmScheduleWsCon.onclose = function() {
		console.log('WebSocket connection has been closed');
	}

	ppmScheduleWsCon.onerror = function(error) {
		console.log('WebSocket connection has error detected: ' + error);
	}

	ppmScheduleWsCon.onmessage = function(event) {
		$("#ppm_notifications_ph").remove();
		var data = event.data;
		$("#notifications_Container").append(event.data);
	}
}
//----------------------------------------------------------------

//---------------------------------bioPPMScduleWsCon-------------------------------
var bioPPMScduleWsCon;

function connectToBioPpmScheduleNotifier(siteId, langCode) {
	var url = appServerUrl + "/"+ appDirVar +"/BioPpmNotifier";
	if(siteId && siteId != 0)
		url += "?siteId=" + siteId + "&langCode=" + langCode;
	
	bioPPMScduleWsCon = new WebSocket(url);

	bioPPMScduleWsCon.onopen = function() {
		console.log('WebSocket connection has been opened bioPPMScduleWsCon');
	}

	bioPPMScduleWsCon.onclose = function() {
		console.log('WebSocket connection has been closed bioPPMScduleWsCon');
	}

	bioPPMScduleWsCon.onerror = function(error) {
		console.log('WebSocket connection has error detected bioPPMScduleWsCon: ' + error);
	}

	bioPPMScduleWsCon.onmessage = function(event) {
		$("#bioppm_notifications_ph").remove();
		var data = event.data;
		$("#notifications_Container").append(event.data);
	}
}
//----------------------------------------------------------------


var spInventoryWsCon;

function connectToSPInventoryNotifier(siteId,langCode) {
	var url = appServerUrl + "/"+ appDirVar +"/SPInventoryNotifier";
	if(siteId && siteId != 0)
		url += "?siteId=" + siteId+"&langCode="+langCode;
	
	spInventoryWsCon = new WebSocket(url);

	spInventoryWsCon.onopen = function() {
		console.log('WebSocket connection has been opened');
	}

	spInventoryWsCon.onclose = function() {
		console.log('WebSocket connection has been closed');
	}

	spInventoryWsCon.onerror = function(error) {
		console.log('WebSocket connection has error detected: ' + error);
	}

	spInventoryWsCon.onmessage = function(event) {
		$("#sp_notifications_ph").remove();
		var data = event.data;
		$("#notifications_Container").append(event.data);
	}
}

var maintenanceRequestWsCon;

function connectToMaintenanceRequestNotifier(siteId,langCode) {
	var url = appServerUrl + "/"+ appDirVar +"/MaintenanceRequestsNotifier";
	if(siteId && siteId != 0)
		url += "?siteId=" + siteId +"&langCode="+langCode;
	
	maintenanceRequestWsCon = new WebSocket(url);

	maintenanceRequestWsCon.onopen = function() {
		console.log('WebSocket connection has been opened');
	}

	maintenanceRequestWsCon.onclose = function() {
		console.log('WebSocket connection has been closed');
	}

	maintenanceRequestWsCon.onerror = function(error) {
		console.log('WebSocket connection has error detected: ' + error);
	}

	maintenanceRequestWsCon.onmessage = function(event) {
		$("#mreq_notifications_ph").remove();
		var data = event.data;
		$("#notifications_Container").append(event.data);
	}
}

function addValidationRuleForSpCatDuplication() {
	$("#code").rules("add", {
		remote : {
			url : appDirVar + "/CheckSPCategoryDuplication",
			data : {
				// code : function() {return $("#code").val()},
				spCatId : function() {if ($("#spCatId").length)	return $("#spCatId").val();	return "0"}
			  }
		}
	});
}

function closeWindow() {
	//window.open('','_parent',''); //_self
	window.open('','_self','');
	window.close();
	//if (window.opener != null && !window.opener.closed) {
		//alert("parent exists");
		//window.close();
	//} else {
		//window.history.back();
	//}
}

// used in gen DeviceInfo.jsp and gen uncoded device JobOrderOpen.jsp
function _onChangeHospitalLocation(obj){
	hideAllDepartmentsOptions();
	var selectedLoc = $(obj).find(":selected").val();
	$('#dep').children('option[title=' + selectedLoc + ']').show();
}

function hideAllDepartmentsOptions(){
	$('#dep').children('option:not(:first)').hide();
}

function rerenderUI(displayerURL, skipFormValidation) {
	var formObj = $("form:first");
	if(skipFormValidation === true)
	  formObj.validate().cancelSubmit = true;
	formObj.attr("action", displayerURL);
	formObj.submit();
}