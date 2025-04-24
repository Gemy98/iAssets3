<%@ page language="java" pageEncoding="UTF-8" import="java.util.*,com.iassets.general.entity.*,com.iassets.common.util.*,com.iassets.general.util.*"%>
<%
Date startDate = (Date) request.getAttribute("startDate");
String startDateStr = DateUtil.getDateString(startDate);

Date endDate = (Date) request.getAttribute("endDate");
String endDateStr = DateUtil.getDateString(endDate);

// Boolean updateMode = (Boolean) request.getAttribute("updateMode");
Boolean firstVisit = (Boolean) request.getAttribute("firstVisit");

List<GenLookupEvaluationGroup> evaluationGroupList = (List<GenLookupEvaluationGroup>) request.getAttribute("evaluationGroupList");

Integer month = WebUtil.getParamValueAsInteger(request, "month", null);
Integer year = WebUtil.getParamValueAsInteger(request, "year", null);

String langCode = WebUtil.getSessionLanguage(request);

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>تقييم الأداء</title>
</head>
<body>
<form id="tabs_form" method="post" action="gen/MasterEvaluationDisplay">
  <table class="layout_grid" style="width:45%;margin:auto">
    <tr>
       <td colspan="4"><h1 class="page_title"> شاشة تقييم الأداء</h1></td>
    </tr>
    <tr>
      <td colspan="1" class="side_label_middle">الشهر : </td>
      <td colspan="1"><%=HtmlUtil.getMonthesAsHtmlSelect("month","", langCode)%></td>
 <!--
    </tr>
    <tr> 
-->
      <td colspan="1" class="side_label_middle">السنة : </td>
      <td colspan="1"><select name="year" id="year"></select></td>  
    </tr>
    <%if(!firstVisit){%>
    <tr id="interval_container">
      <td class="side_label_middle">الفترة من :</td>
      <td><input size="10" type="text" class="caldr" id="datepickerfrom" name="datepickerfrom" readonly="readonly" value="<%=startDateStr%>"></td>
      <td class="side_label_middle">إلي :</td>
      <td><input size="10" type="text" id="datepickerto" name="datepickerto" readonly="readonly" value="<%=endDateStr%>"></td>
    </tr>
    <%}%>
  </table>
</form>
<br />
<%if(!firstVisit){%>
	<div id="tabs">
	  <ul>
	    <%for (GenLookupEvaluationGroup group : evaluationGroupList) {
		  int groupId = group.getId();
		  String groupName = group.getName();
		%>
	    <li><a href="gen/MasterEvaluationDisplay#tabs-<%=groupId%>"><%=groupName%></a></li>
	    <%}%>
	    <li><a href="gen/MasterEvaluationDisplay#tabs-due">المبالغ المستحقة</a></li>
	  </ul>
	  <%for (GenLookupEvaluationGroup group : evaluationGroupList) {
		  int groupId = group.getId();
	   %>
	  <div id="tabs-<%=groupId%>"></div>
	  <%}%>
	  <div id="tabs-due">
	   <table style="width:50%;margin: auto" id="table_sheet_due" class="data_table" >
		  <thead>
			<tr>
				<th colspan="2">المبالغ المستحقة للمقاول عن تقييم الأداء للفترة المذكورة</th>
			</tr>
		  </thead>
	<%
		if(evaluationGroupList!=null){
			 for (GenLookupEvaluationGroup group : evaluationGroupList) {
				  int groupId = group.getId();
				  String groupName=group.getName();
	%>
		   <tr>
			  <td style="font-weight:bold;width:50%;text-align: right;padding-right: 20px" id="sheet_name_<%=groupId %>">قسم <%=groupName%></td>
			  <td style="text-align: center" id="sheet_due_<%=groupId %>" class="sheet_due_<%=groupId %>"></td>
	       </tr>
		  <%}}%>
		  
		  <tfoot>
			  <tr>
				  <th>المجموع</th>
				  <th id="due_total"></th>
			  </tr>
		  </tfoot>
	 </table>
 
	  </div>
	</div>
	<%
	String src = null;
	for (GenLookupEvaluationGroup group : evaluationGroupList) {
		  int groupId = group.getId();
		  src = "gen/EvaluationDisplay?groupId="+groupId+"&month="+month+"&year="+year;
	%>
	<iframe src="<%=src%>" style="display: none" onload="loadIframeContentToDiv('<%=groupId%>')" id="iframe-<%=groupId%>" name="iframe-<%=groupId%>" frameborder="0"></iframe>
	<%}%>
<!--     <div align="center"><input class="close_btn" type='button' value='إغلاق النافذة'  onclick="closeWindow()" ></div>
 -->
 <%}%>
 

 
<script type="text/javascript">
$(function() {

	// init years dropdown list
	var currentYear = new Date().getFullYear();
	for (var i=currentYear-1; i <=currentYear+1 ; i++)
		$("#year").append(new Option(i, i));
	
	$("#month , #year").change(function(){_onChangeMonthOrYear()});

    <%if(!firstVisit){%>
    
        $("#tabs").tabs();
        
	    setComboSelectedValue("month","<%=month%>");
	    setComboSelectedValue("year","<%=year%>");
	    
	    var startDate = new Date($("#year").val(), $("#month").val() -1, 1);
	    var endDate = new Date($("#year").val(), $("#month").val(), -1);
	    
	    $("#datepickerfrom").datepicker('option', 'changeMonth', false);
	    $("#datepickerfrom").datepicker('option', 'changeYear', false);
	    $("#datepickerfrom").datepicker('option', 'minDate', startDate);
	    $("#datepickerfrom").datepicker('option', 'maxDate', endDate);
	      
<%-- 	    
        <%for (GenLookupEvaluationGroup group : evaluationGroupList) {
	    	int groupId = group.getId();%>
	  		$("#iframe-"+<%=groupId%>).attr('src', 'gen/EvaluationDisplay?groupId='+<%=groupId%>+'&startDate='+$("#datepickerfrom").val()+'&endDate='+$("#datepickerto").val()+'&month='+$("#month").val()+'&year='+$("#year").val());		
		<%}%> 
--%>
	<%}%>
   
});

function _onChangeMonthOrYear(){
	$("#interval_container").remove();
	$("#tabs").remove();
	
	var monthSelectedIndex = $("select[name='month'] option:selected").index();
	var yearSelectedIndex =  $("select[name='year'] option:selected").index();
	
	if (monthSelectedIndex !=0  && yearSelectedIndex != 0)
	   $("#tabs_form").submit();
}
	
function loadIframeContentToDiv(groupId){
	 var content =  $("#iframe-" + groupId).contents().find("html").html();
	 $("div#tabs-" + groupId).html(content);
	 $("html, body").animate({ scrollTop: 100 }, "slow");
	 // init form validator
	 $("#form-"+groupId).attr('novalidate', 'novalidate');
	 $("#form-"+groupId).validate({
		 errorElement:"div",
		 errorPlacement : function(error, element) { error.insertAfter(element)}  
	 });
	 
	 updateSheetDue(groupId);
	 updateDueTotal();
	 // init table sorter
/* 	 $("#items_table_" + groupId).tablesorter({
		widthFixed : false,
		widgets : [ 'zebra' ],
		headers: { 0: { sorter: false} }
	 });  */
}

function updateSheetDue(groupId){
	var itemDedicatedValue = parseFloat( $("#itemDedicatedValue_" + groupId).html()) || 0;
	var itemPenaltyValue = parseFloat( $("#itemPenaltyValue_" + groupId).html()) || 0;
	var sheetDue = itemDedicatedValue-itemPenaltyValue;
	$("[class='sheet_due_" + groupId+"']").html(sheetDue.toFixed(2));
}

function updateDueTotal(){
	var itemObjects=$('#table_sheet_due').find('td[id^="sheet_due_"]');
	var total=0;
	for (var a= 0; a < itemObjects.length; a++){
		total = total +  (parseFloat($(itemObjects[a]).html()) || 0); 
	}
	$("#due_total").html(total.toFixed(2));
}

function _submitPerformanceEvalForm(groupId){
	// alert($("#form-"+groupId).valid());
	if($("#form-"+groupId).valid()){
		$("#form-"+groupId).append($("#tabs_form").find("input,select").clone().hide());
		// $("#datepickerfrom").attr('readonly','readonly');
		return true;
    }
	return false;
}


function updateCalculatedValues(obj, suffix, group){
	var elem = $(obj);
	var num = parseFloat(elem.val()) || 0;
	var maxDegree = parseFloat($("input[name='itemMaxDegree_"+suffix+"']").val());
	if (!elem.valid()){
		num=0;
		// $(obj).val(0);
     }
	
	var evaluationPercent = (num/maxDegree)*100;
	$("input[name='itemEvalPercentage_"+suffix+"']").val(evaluationPercent.toFixed(4));
	
	var itemPenaltyPercent = calculatePenalty(evaluationPercent);
	
	$("input[name='itemPenaltyPercentage_"+suffix+"']").val(itemPenaltyPercent.toFixed(4));
	
	var itemPenaltyValue = ((itemPenaltyPercent* $("input[name='itemDedicatedValue_"+suffix+"']").val())/100);
	$("input[name='itemPenaltyValue_"+suffix+"']").val(itemPenaltyValue.toFixed(2));
	
	var count=$("#totalItemsCount_"+group).val();
	calculateTotal("itemPenaltyValue", group, count);
	calculateTotal("itemEvalDegree", group, count);

	updateSheetDue(group);
	updateDueTotal();
}

function calculateTotal(name, group, count){
	var total = 0;
	var elem;
	for(var i=1; i<=count; i++){
		elem = $("input[name='"+name+"_"+group+"_"+i+"']");
		if(elem.valid())
		   total = total + parseFloat(elem.val());
	}
	total = total.toFixed(2);
	$("#"+name+"_"+group+"").html(total);
	
}

function calculatePenalty(val){
	
/* 	if(val==0)
		return 0; */
	
	var res=20;
	
	if(val>=86 )
		res=0;
	
	else if(val==85 )
		res=1;
	
	else if(val==84 )
		res=2;
	
	else if(val==83 )
		res=2.5;
	
	else if(val==82 )
		res=3;
	
	else if(val==81 )
		res=3.5;
	
	else if(val==80 )
		res=4;

	else if(val==79 )
		res=4.75;
	
	else if(val==78 )
		res=5.5;
	
	else if(val==77 )
		res=5.75;
	
	else if(val==76 )
		res=6;
	
	else if(val==75 )
		res=6.25;
	
	else if(val==74 )
		res=6,5;
	
	else if(val==73 )
		res=6.75;
	
	else if(val==72 )
		res=7;
	
	else if(val==71 )
		res=7.25;
	
	else if(val==70 )
		res=7.5;
	
	else if(val==69 )
		res=7.75; 
	             
	else if(val==68 )
		res=8; 
	             
	else if(val==67 )
		res=8.25; 
	             
	else if(val==66 )
		res=8.5; 
	             
	else if(val==65 )
		res=8.75; 
	             
	else if(val==64 )
		res=9; 
	             
	else if(val==63 )
		res=9.25; 
	             
	else if(val==62 )
		res=9.5; 
	             
	else if(val==61 )
		res=9.75; 
	             
	else if(val==60 )
		res=10; 

	else if(val==59 )
		res=11;  
	             
	else if(val==58 )
		res=12;  
	             
	else if(val==57 )
		res=13;  
	             
	else if(val==56 )
		res=14;  
	             
	else if(val==55 )
		res=15;  
	             
	else if(val==54 )
		res=16;  
	             
	else if(val==53 )
		res=17;  
	             
	else if(val==52 )
		res=18;  
	             
	else if(val==51 )
		res=19;  
	
	return res;
}

</script>
</body>
</html>
