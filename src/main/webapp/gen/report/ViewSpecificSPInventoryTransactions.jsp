<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

<%@ include file="../SPInventoryContentOverview.jsp" %>

<%
	String langCode = WebUtil.getSessionLanguage(request);
	List<GenSpInventoryTransaction> transList = (List<GenSpInventoryTransaction>)request.getAttribute("transList");
%>
<table id="trans_list" class="tablesorter">
		<thead>
			<tr>
				<th>م</th>
				<th>العملية</th>
				<th>تاريخ العملية</th>
				<th>الكمية (بالقطعة)</th>
				<th>سعر الشراء (بالريال السعودي)</th>
				<th>ملاحظات</th>
				<th>الموظف المسؤول</th>
			</tr>
		</thead>
		<tbody>
			<%
			GenSpInventoryTransaction trans = null; 
		    for (int i = 0; i< transList.size(); i++){
		         trans = transList.get(i);
		         String style = (trans.getInput()) ? "color:#0C0" : "color:#F00";
			%>
			<tr>
				<td style='<%=style%>' align="center"><%=(i+1)%></td>
				<td style='<%=style%>'><%=Common.getDisplayString(trans.getOperationDescription(),"-",true)%></td>  
				<td style='<%=style%>'><%=DateUtil.getDateString(trans.getPerformedIn())%></td>                
				<td style='<%=style%>'><%="" + trans.getQuantity()%></td>
	            <td style='<%=style%>'><%=Common.formatMoneyValue(trans.getUnitPrice())%></td>
	            <td style='<%=style%>'><%=Common.getDisplayString(trans.getNotes(),"-",true)%></td>
	            <td style='<%=style%>'><%=(trans.getPerformedBy() == null)? "عملية إدخال البيانات" : trans.getPerformedBy().getName(langCode)%></td> 
			</tr>
			<%}%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#trans_list thead th:eq(1)").data("sorter", false);
			$("#trans_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers: { 0: { sorter: false} }
			});
		});
		
/* 		function updateSPInventoryContent(spCatCode){
			if(confirm("هل تريد تحديث بيانات هذا الصنف من قطع الغيار؟"))
			{
				window.opener.location.href="bio/UpdateSPCategory?purpose=تعديل بيانات صنف&code=" + spCatCode;
				window.close();
			}
		} */
	</script>

</body>
</html>