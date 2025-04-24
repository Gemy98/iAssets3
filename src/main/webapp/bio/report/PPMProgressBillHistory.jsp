<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
	<body>
		<%
		  List<BioPPMProgressBill> progressBills = (List<BioPPMProgressBill>)request.getAttribute("ppmProgressBillList");
		%>
		<table id="ppm_list" class="tablesorter">
			<thead>
				<tr>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list1" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list2" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list3" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list4" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list5" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list6" /></th>
				    <th><fmt:message key="jsp.PPMProgressBillHistory.ppm_list7" /></th>
				</tr>
			</thead>
			<tbody>
				<%
				if (progressBills !=null && !progressBills.isEmpty()){
					int i = 1;
					for (BioPPMProgressBill bill : progressBills){
				%>
				<tr>
				    <td align="center"><%=i++%></td>
					<td><%=DateUtil.getDateString(bill.getFrom())%></td>
					<td><%=DateUtil.getDateString(bill.getTo())%></td>
					<td><%=Common.getDisplayString(bill.getProgressBill().getPaymentNo(),"-",true)%></td>
					<td><%=Common.formatMoneyValue(bill.getProgressBill().getPbValue())%></td>
					<td><%=Common.getDisplayString(bill.getProgressBill().getIssueNo(),"-",true)%></td>
					<td><%=DateUtil.getDateString(bill.getProgressBill().getIssueDate())%></td>
				</tr>
				<%
					}}
				%>
			</tbody>
		</table>
		<script>
			$(function() {
				$("#ppm_list").tablesorter({
					widthFixed : false,
					widgets : [ 'zebra' ],
					headers: { 0: { sorter: false},9: { sorter: false}}
				});
			});
		</script>
	</body>
</html>