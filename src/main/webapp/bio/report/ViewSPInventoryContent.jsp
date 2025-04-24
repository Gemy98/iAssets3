<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<%
	List<BioSpInventoryContent> spInventoryContentList = (List<BioSpInventoryContent>)request.getAttribute("spInventoryContentList");
%>
<table id="sp_list" class="tablesorter">
		<thead>
			<tr>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list1" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list2" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list3" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list4" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list5" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list6" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list7" /></th>
				<th><fmt:message key="jsp.ViewSPInventoryContent.sp_list8" /></th>
			</tr>
		</thead>
		<tbody>
			<%
				BioSpInventoryContent sp = null; 
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
				<a href="javascript:updateSPInventoryContent('<%=spCatCode%>')" > <%=spCatCode%></a>
				<%}%>
				</td>                
				<td><%=Common.getDisplayString(sp.getName(),"-",true)%></td>
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
		
		function updateSPInventoryContent(spCatCode){
			if(confirm('<fmt:message key="jsp.js.ViewSPInventoryContent.confirm" />'))
			{
				window.opener.location.href="bio/UpdateSPCategory?purpose= " + '<fmt:message key="jsp.js.ViewSPInventoryContent.location" />'+"&code=" + spCatCode;
				window.close();
			}
		}
	</script>
</body>
</html>
