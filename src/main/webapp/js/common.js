// JavaScript Document

function addRowToTable(tableSelector, rowHtml){
     $(tableSelector).find("tr[class='_emptyRowMarker']").remove();
	 $(tableSelector).append(rowHtml);
 }
     
 function deleteRowFromTable(eventSource, minRowsCount, emptyRowHtml){
	 var containerTr = $(eventSource).closest('tr');
	 var parentTable = containerTr.closest('table');
	 containerTr.remove();
	 var tableRowsCount  = parentTable.find('tr').length;
	 if(tableRowsCount < parseInt(minRowsCount)){
		 var emptyTr = $(emptyRowHtml);
		 emptyTr.addClass("_emptyRowMarker");
		 emptyTr.appendTo(parentTable);
	 }
 }

function _appendGridLabelCustomBuilder(parent, idPrefix, name, uniqueIndex){
    var ctrlId = idPrefix + '_' + name + '_' + uniqueIndex;
    var ctrl = document.createElement('span');
    $(ctrl).attr({ id: ctrlId, name: ctrlId }).appendTo(parent);
    return ctrl;
}

function _appendGridLabelCustomSetter(idPrefix, name, uniqueIndex, value){
    // Prepare the control ID/name by using idPrefix, column name and uniqueIndex
    var ctrlId = idPrefix + '_' + name + '_' + uniqueIndex;
    $('#' + ctrlId).html(value);	
}

function _appendGridLabelCustomGetter(idPrefix, name, uniqueIndex){
    // Prepare the control ID/name by using idPrefix, column name and uniqueIndex
    var ctrlId = idPrefix + '_' + name + '_' + uniqueIndex;
    return $('#' + ctrlId).html();	
}

function getParameter(paramName) {
  var qStr = window.location.search.substring(1);
  var parameters = qStr.split("&");
  var paramParts;
	for (var i=0;i<parameters.length;i++) {
		paramParts = parameters[i].split("=");
		if (paramParts[0] == paramName) 
			return decodeURIComponent(paramParts[1]);
	}
}

function setTextFieldValue(name, value){
     $("input[name='"+name+"']").val(value)
}

function setInnerHTML(id, html){
     $("#"+id).html(html)
}

function setComboSelectedValue(name, value){

	 if($("select[name='"+name+"']").find("option[value='"+value+"']").size() > 0){
       $("select[name='"+name+"']").find("option[value='"+value+"']").attr("selected","selected");
       $("select[name='"+name+"']").val(value);
     }
// alert("setComboSelectedValue::"+ name +":: "+
// $("select[name='"+name+"']").find("option[selected=selected]").size());
// alert($("select[name='"+name+"']").val()+" is the value")
// }else
// $("select[name='"+name+"']").prop('selectedIndex', 0);
}

 
function setRadioCheckedValue(name, value){
    $("input[name='"+name+"'][value='"+value+"']").prop("checked", "checked");
}

function setCheckedValues(name, values){
	for(var i = 0; i < values.length; i++)
       $("input[name='"+name+"'][value='"+values[i]+"']").prop("checked", "checked");
}

function setDateValue(name, value){
    $("input[name='"+name+"']").datepicker( "setDate", value );   
}

function appendIdElement(name, value){
    $("body form").append("<input type='hidden' id='"+ name +"' name='"+ name +"' value='"+ value +"' />");
}


/*
 * function getDecodedParameter(paramName){ encodedParam =
 * getParameter(paramName); return decodeURIComponent(encodedParam) }
 */

function attachConditionalDisplayHandlerToRadio(radioGroupName , mapping){
	// initially hide all affected containers
	hideAll(getValuesFromArrayLikeObject(mapping));
	
	// attach onChange handler for each radio in the radio group
	$("input:radio[name="+radioGroupName+"]").change(function(){
		_toggleDisplayOnRadio.call(this, mapping);
	});
	
	// trigger change event for initially checked radio .. usefull when page in
	// update mode
	$("input:radio[name="+radioGroupName+"]:checked").trigger("change",mapping);
}

function getValuesFromArrayLikeObject(aryLikeObj){
	var keys = Object.keys(aryLikeObj);
	var values = [];
	for (var i=0; i<keys.length; i++) 
  	  values[i] = aryLikeObj[keys[i]];
	return values;
}

function hideAll(ids){
	$.each(ids,function(index,value){
		if(! $.isArray(value))
		   value = [value]
		for (var i=0; i<value.length; i++) 
 	   	   $("#"+value[i]).hide();
	});
}

function _toggleDisplayOnRadio(mapping){
	
	hideAll(getValuesFromArrayLikeObject(mapping));
	var radio = $(this);
	var key = radio.val();
	var containersIds = mapping[key];
	if(! $.isArray(containersIds))
	   containersIds = [containersIds];
	if(radio.is(':checked'))
		$.each(containersIds,function(index,value){$("#"+value).show()});
// else
// $.each(containersIds,function(index,value){$("#"+value).hide();});
}

function initFileInputElements(){
	
	  $("input[type='file']").addClass("file_input").wrap("<label></label>");
	  $("input[type='file']").each(function(){
		  $(this).change(function(){$(this).valid();removePreviousList(this);showSelectedFileList(this)});
		  $(this).closest("label").addClass("upload_label").prepend(($(this).prop('multiple'))? common_initFileInputElements_uploadfiles  :  common_initFileInputElements_uploadfile );
		  if($(this).hasClass('requiredFile')) 
			 $(this).attr("data-rule-filerequired", true);
		  $(this).attr("data-rule-filesize","5");
		  $(this).attr("data-rule-maxattach","5");
		  //$(this).attr("data-rule-accept","image/*");
		  //$(this).attr("data-msg-accept","لا يسمح  بإرفاق  ملفات بامتداد غير امتداد الصور");
	  });
	  

	/*
	 * // hide all file elements $("input[type='file']").hide();
	 * //$("input[type='file']").css("position","relative");
	 * $("input[type='file']").change( function(){ removePreviousList(this);
	 * showSelectedFileList(this); });
	 *  // create mask button for every file element
	 * $("input[type='file']").each( function(){ var btnTxt =
	 * ($(this).prop('multiple'))? 'حمل الملفات' : 'حمل الملف';
	 * $(this).parent().prepend('<input type="button"
	 * name="'+this.name+'_mask_btn" value="'+ btnTxt +'"
	 * class="file_input_mask_btn" />'); //
	 * $(this).focus(function(){$("input[name='"+this.name+"_mask_btn']").trigger("focus")});
	 * });
	 *  // attach click events for the mask buttons
	 * $(".file_input_mask_btn").each( function(){ var actualInputFileName =
	 * this.name.replace("_mask_btn", "");
	 * $(this).click(function(){$("input[type=file][name="+actualInputFileName+"]").click();});
	 * });
	 */
}

function getSizeStr(size) {
	var sizeStr = "";
	var sizeKB = size / 1024;
	if (parseInt(sizeKB) > 1024) {
		var sizeMB = sizeKB / 1024;
		sizeStr = sizeMB.toFixed(2) + " MB";
	} else {
		sizeStr = sizeKB.toFixed(2) + " KB";
	}
	return sizeStr;
}

function showSelectedFileList(fileInputObj){
	if($(fileInputObj).valid()){
		var selecteFiles = fileInputObj.files;
		var listHTML = "";
		if(selecteFiles.length > 0){
			listHTML += "<div class='file-list'>"
			for(var i =0; i < selecteFiles.length ; i++){
			  listHTML += "<div>" + selecteFiles[i].name + " (" + getSizeStr(selecteFiles[i].size) + ")" + "</div>";
			}
			// listHTML += "<div class='clearer'></div>";
			listHTML += "</div>";
			$(fileInputObj).closest("label").parent().append(listHTML);
	   }
   }else
	   $(fileInputObj).val(""); // clear the field value
}

function removePreviousList(fileInputObj){
	$(fileInputObj).closest("label").siblings("div.file-list").remove();
}

function viewFile(fileName){
    var win = window.open('FileViewer?file_name='+fileName, '_blank');
    if(win)
    // Browser has allowed it to be opened
       win.focus();
    else
    // Broswer has blocked it
       alert(common_viewFile_alert);
}

function deleteFile(obj, fileName){
	if(confirm(common_deleteFile_confirm)){
		var ul = $(obj).closest("ul.uploadedFilesList");
		if(ul.find("li").length == 1)
			ul.remove();
		else
		   $(obj).parent("li").remove();
		
		/*
		$.ajax({
			type: "POST",
			url:  "FileDelete",
			data: { 'fileName': fileName}
			})
			.done(function() {
				alert("file deleted correctly");
				$(obj).parent("li").remove();
		});
		*/
	}
}

function showUploadedFilesList(fileInputName , filesAry){
	if(filesAry.length > 0){

		var listHtml = "<ul id='uploadedFiles_" + fileInputName + "' class='uploadedFilesList'>"
		for (var  i = 0 ; i < filesAry.length ; i++){
			var fileDisplayName = filesAry[i].substring(filesAry[i].lastIndexOf("_sfn_")+5);
			//var fileDisplayName = "مرفق رقم " + (i+1) ;
			listHtml += "<li>";
			listHtml += "<a href ='javascript:viewFile(\""+filesAry[i]+"\")'>" + fileDisplayName + "</a>";
			listHtml += "<img title='"+common_showUploadedFilesList_deleteFile+"' src='image/delete-file-icon.png' onclick='deleteFile(this, \""+filesAry[i]+"\")' />";
			listHtml += "<input type='hidden' name='" + fileInputName + "_PrevUploadedFiles' value='"+filesAry[i]+"' />";
			listHtml += "</li>";
		}
		listHtml += "</ul>";
		if($("input[name='" + fileInputName + "']").closest("label").length > 0)
		  $("input[name='" + fileInputName + "']").closest("label").parent().append(listHtml);
		else
		  $("input[name='" + fileInputName + "']").parent().append(listHtml);
    }
}

function markAsRequired(selector){
	
	markAsNotRequired(selector);
	
	$(selector).each(function(){
				
		if($(this).rules().required != true){
			if($(this).is("input:file")){
				$(this).rules("add",{filerequired: true});
				$(this).addClass("requiredFile");
			}else
		    	$(this).rules("add",{required: true});
	    }
		
		//var elem = ($(this).is("input:file")) ? $(this).closest("label") : $(this);
		
		//if (! elem.next("span[class='requiredField']:first").length)
		//	    elem.after("<span class='requiredField'>*</span>");
			
		// $("form").data('validator').element(this);
		// $(this).valid();
		initRequiredFields(selector);
	}); 
}

function markAsNotRequired(selector){
	
	$(selector).each(function(){
		$(this).removeClass("error");
		
		if($(this).is("input:file")){
			$(this).rules("remove","filerequired");
			$(this).removeClass("requiredFile");
		}else
			$(this).rules("remove","required");
		
		var elem = ($(this).is("input:file")) ? $(this).closest("label") : $(this);
		
		elem.next("span[class='requiredField']").remove();
		elem.siblings(".error").remove();
	});
}

function markAsValid(selector){
	
	$(selector).each(function(){
		$(this).removeClass("error");
		var elem = ($(this).is("input:file")) ? $(this).closest("label") : $(this);
		// elem.next("span[class='requiredField']").remove();
		elem.siblings(".error").remove();
	});
}


function initRequiredFields(selector){
	
	var targetElems;
	if(selector)
	   targetElems = $(selector);
	else
	   targetElems = $("form").find("input, select, textarea");
	
	targetElems.each(function(){
		//! $(this).is("input:radio") && ! $(this).is("input:checkbox") && 
		if(($(this).rules().required == true || $(this).hasClass("requiredFile")) && ! $(this).next("span[class='requiredField']:first").length){
			
			if($(this).is("input:radio") ||  $(this).is("input:checkbox")){
			
				if(! $("input[name='"+$(this).attr('name')+"']").length) // single radio or checkbox
					$(this).parent().append("<span class='requiredField'>*</span>");
				
				else if($("input[name='"+$(this).attr('name')+"']").index($(this)) == $("input[name='"+$(this).attr('name')+"']").length - 1)
					if($(this).parent().prop("id") == "category_radio_container")
						$(this).parent().prepend("<span class='requiredField'>*</span>");
					else
					    $(this).parent().append("<span class='requiredField'>*</span>");
				
			}
			
		    else if($(this).is("input:file"))
			    $(this).closest("label").after("<span class='requiredField'>*</span>");
			
			else
			    $(this).after("<span class='requiredField'>*</span>");
	    }
	});
}

function escapeSpecialChars(str){
	return encodeURI(str);
}

function appendLookupTextToFormParams (){
	var prefix = "selectedText_";

	$("input[type=checkbox]:checked").each(function (){
		var text  = $(this).nextUtil("#r22");//;$(this).next('label').text();
		var name  = $(this).attr('name');
		//alert(text);
		//alert(name);
		
		$("body form").append("<input type='hidden'  name='"+prefix+"_"+name+"' value='"+text+"' />");
	
	});
	
	$("input[type=radio]:checked").each(function (){
		var text  = $(this).next('label').text();
		var name  = $(this).attr('name');
		//alert(text);
		//alert(name);		
		$("body form").append("<input type='hidden'  name='"+prefix+"_"+name+"' value='"+text+"' />");
	});
	
	$("select option:selected").each(function () {
		var text  = $(this).text();
		var name  = $(this).closest("select").attr('name');
		//alert(text);
		//alert(name);		
		$("body form").append("<input type='hidden'  name='"+prefix+"_"+name+"' value='"+text+"' />");
	});
	
}

function resetForm(){
	window.location.href = window.location.href
}

function getLiteral(key){return "";}