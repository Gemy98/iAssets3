<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.general.entity.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%
	List<GenSpInventoryContent> spInventoryContentList = (List<GenSpInventoryContent>)request.getAttribute("spInventoryContentList");
%>
<table id="sp_list" class="tablesorter">
		<thead>
			<tr>
				<th>م</th>
				<th>الكود</th>
				<th>اسم الصنف</th>
				<th>ملاحظات/مواصفات</th>
				<th>الكمية المتاحة بالمستودع</th>
				<th>الحد الأدنى المحدد</th>
				<th>متوسط سعر القطعة<br>(بالريال السعودي)</th>
				<th>أقل سعر شراء للقطعة<br>(بالريال السعودي)</th>
				<th>أعلى سعر شراء للقطعة<br>(بالريال السعودي)</th>
			</tr>
		</thead>
		<tbody>
			<%
			GenSpInventoryContent sp = null; 
					    for (int i = 0; i< spInventoryContentList.size(); i++){
					         sp = spInventoryContentList.get(i);
			%>
			<tr>
				<td align="center"><%=(i+1)%></td>
				<td>
				<% 
				  String spCatCode = Common.getDisplayString(sp.getCode(),"-",true); 
				  if(WebUtil.currentUserHasViewPrivilegesOnly(request)){
					  out.print(spCatCode);
				  }else{
				%>
				<a href="gen/UpdateSPCategory?purpose=تعديل بيانات صنف&code=<%=sp.getCode()%>"	target="_blank"> <%=spCatCode%></a>
				<%}%>
				</td>                
				<td><%=Common.getDisplayString(sp.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(sp.getSpecs(),"-",true)%></td>
	            <td><%=Common.getDisplayString(sp.getAvailableQuantity(),"-")%></td>
	            <td><%=Common.getDisplayString(sp.getThreshold(),"-")%></td>
	            <td><%=Common.formatMoneyValue(sp.getAvgUnitPrice())%></td> 
	            <td><%=Common.formatMoneyValue(sp.getMinPrice())%></td>  
	            <td><%=Common.formatMoneyValue(sp.getMaxPrice())%></td>      
			</tr>
			<%}%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#sp_list thead th:eq(1)").data("sorter", false);
			$("#sp_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers: { 0: { sorter: false} }
			});
		});
	</script>
</body>
</html>
