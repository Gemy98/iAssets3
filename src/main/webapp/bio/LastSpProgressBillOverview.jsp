<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%
	BioSparePartsProgressBill lastSPPB = (BioSparePartsProgressBill) request.getAttribute(Default.LAST_SP_PROGRESS_BILL_INFO_ATTR_NAME);
	if (lastSPPB != null){
		BioProgressBill lastPB = lastSPPB.getProgressBill();
		String paymentNo = Common.getDisplayString(lastPB.getPaymentNo(), "");
		String pbValue = Common.formatMoneyValue(lastPB.getPbValue());
		String issueNo = Common.getDisplayString(lastPB.getIssueNo(),"");
		String issueDate = DateUtil.getDateString(lastPB.getIssueDate());
		String serialScan = lastSPPB.getSerialScan();
		String totalSPValue = Common.formatMoneyValue(WebUtil.getSessionSite(request).getBioSparePartsValue());
		String remainedValueAfterBP =  Common.formatMoneyValue(lastSPPB.getRemainedBudgetAfterPB());
		String payedValueAfterBP =  Common.formatMoneyValue(lastSPPB.getPayedBudgetAfterPB());
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>
    <h3 style="text-align: right; color: green; text-decoration: underline">  <fmt:message key="jsp.LastSpProgressBillOverview.title" /></h3>
	<table class="layout_table" style="float: right">    
<%-- 		
       <tr>
			<th>رقم الدفعة</th>
			<td><label id="paymentNo"></label></td>
			<th>قيمة المستخلص</th>
			<td><label id="pbValue"></label></td>
			<th>رقم الصادر</th>
			<td><label id="issueNo"></label></td>	
			<th>تاريخ الصادر</th>
			<td><label id="issueDate"></label></td>
			<th>صورة الرقم التسلسلي</th>
			<td><label id="serialScan"><%= CommonHtmlUtil.showUploadedFilesInViewMode(serialScan, false)%></label></td>	
		</tr>
		<tr>
		    <th>القيمة الكلية للبند</th>
		    <td><label id="totalSPValue"></label></td>
			<th>القيمة المتبقية من البند</th>
			<td><label id="remainedValueAfterBP"></label></td>	
			<th>القيمة المستنفذة من البند</th>
			<td colspan="5"><label id="payedValueAfterBP"></label></td>
		</tr> 
--%>
        <tr>
			<td class="side_label_middle"> <fmt:message key="jsp.LastSpProgressBillOverview.totalSPValue" />  :</td>
			<td><label id="totalSPValue"></label>  <fmt:message key="jsp.LastSpProgressBillOverview.currency" /> </td>
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message key="jsp.LastSpProgressBillOverview.remainedValueAfterBP" />  :</td>
			<td><label id="remainedValueAfterBP"></label>  <fmt:message key="jsp.LastSpProgressBillOverview.currency" />  </td>	
		</tr>
		<tr>
			<td class="side_label_middle"><fmt:message key="jsp.LastSpProgressBillOverview.payedValueAfterBP" /> :</td>
			<td><label id="payedValueAfterBP"></label>  <fmt:message key="jsp.LastSpProgressBillOverview.currency" /> </td>
		</tr>

	</table>
	<div class="clearer"></div>
	<script>	
	$(function(){
<%-- 		
        setInnerHTML("paymentNo","<%=paymentNo%>");
		setInnerHTML("pbValue","<%=pbValue%>");
		setInnerHTML("issueNo","<%=issueNo%>");
		setInnerHTML("issueDate","<%=issueDate%>"); 
--%>
		setInnerHTML("totalSPValue","<%=totalSPValue%>");
		setInnerHTML("payedValueAfterBP","<%=payedValueAfterBP%>");
		setInnerHTML("remainedValueAfterBP","<%=remainedValueAfterBP%>");
	});
	</script>
</body>

</html>
<%} %>
