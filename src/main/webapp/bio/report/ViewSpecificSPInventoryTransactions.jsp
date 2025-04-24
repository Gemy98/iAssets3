<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

<%@ include file="../SPInventoryContentOverview.jsp" %>

<%
	String langCode = WebUtil.getSessionLanguage(request);
    List<BioSpInventoryTransaction> transList = (List<BioSpInventoryTransaction>)request.getAttribute("transList");
%>
<table id="trans_list" class="tablesorter">
		<thead>
			<tr>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list1" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list2" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list3" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list4" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list5" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list6" /></th>
				<th><fmt:message key="jsp.ViewSpecificSPInventoryTransactions.trans_list7" /></th>
			</tr>
		</thead>
		<tbody>
			<%
			BioSpInventoryTransaction trans = null; 
		    for (int i = 0; i< transList.size(); i++){
		         trans = transList.get(i);
		         String style = (trans.getInput()) ? "color:#0C0" : "color:#F00";
			%>
			<tr>
				<td style='<%=style%>' align="center"><%=(i+1)%></td>
				<td style='<%=style%>'><%=Common.getDisplayString(trans.getOperationDescription(langCode),"-",true)%></td>  
				<td style='<%=style%>'><%=DateUtil.getDateString(trans.getPerformedIn())%></td>                
				<td style='<%=style%>'><%="" + trans.getQuantity()%></td>
	            <td style='<%=style%>'><%=Common.formatMoneyValue(trans.getUnitPrice())%></td>
	            <td style='<%=style%>'><%=Common.getDisplayString(trans.getNotes(),"-",true)%></td>
	            <td style='<%=style%>'><%=(trans.getPerformedBy() == null)? LocalizationManager.getLiteral("jsp.ViewSpecificSPInventoryTransactions.dataentry", langCode) : trans.getPerformedBy().getName(langCode)%></td> 
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