<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String langCode = WebUtil.getSessionLanguage(request); %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
	<body>
	
        <%@ include file="../LastSpProgressBillOverview.jsp" %>
        
		<%
		  List<BioSparePartsProgressBill> progressBills = (List<BioSparePartsProgressBill>)request.getAttribute("sparePartsProgressBillList");
		%>
		<table id="sp_list" class="tablesorter">
			<thead>
				<tr>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list1" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list2" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list3" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list4" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list5" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list6" /></th>
					<!-- <th>القيمة الاجمالية للبند</th> -->
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list7" /></th>
				    <th><fmt:message key="jsp.SparePartsProgressBillHistory.sp_list8" /></th>
				</tr>
			</thead>
			<tbody>
				<%
				if (progressBills !=null && !progressBills.isEmpty()){

					int i = 1;
					for (BioSparePartsProgressBill bill : progressBills){
				%>
				<tr>
				    <td align="center"><%=i++%></td>
					<td align="center"><%=Common.getDisplayString(bill.getProgressBill().getPaymentNo(),"-",true)%></td>
					<td align="center"><%=Common.formatMoneyValue(bill.getProgressBill().getPbValue())%></td>
					<td align="center"><%=Common.getDisplayString(bill.getProgressBill().getIssueNo(),"-",true)%></td>
					<td align="center"><%=DateUtil.getDateString(bill.getProgressBill().getIssueDate())%></td>
					<td align="center"><%=CommonHtmlUtil.showUploadedFilesInViewMode(bill.getSerialScan(), false, langCode)%></td>
					<!-- <td align="center">-</td> -->
					<td align="center"><%=Common.formatMoneyValue(bill.getRemainedBudgetAfterPB())%></td>
					<td align="center"><%=Common.formatMoneyValue(bill.getPayedBudgetAfterPB())%></td>
				</tr>
				<%
					}}
				%>
			</tbody>
		</table>
		<script>
			$(function() {
				$("#sp_list").tablesorter({
					widthFixed : false,
					widgets : [ 'zebra' ],
					headers: { 0: { sorter: false},9: { sorter: false}}
				});
			});
		</script>
	</body>
</html>