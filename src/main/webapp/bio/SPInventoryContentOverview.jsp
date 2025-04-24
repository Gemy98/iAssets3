<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>
<!--     <h3 style="text-align: right; color: green; text-decoration: underline">معلومات بند قطع الغيار</h3> -->
	<table class="layout_table" style="float: right"> 
	<tr>
      <td class="side_label_middle"> <fmt:message key="jsp.SPInventoryContentOverview.code" />  : </td>
      <td id="code"></td>
      <td style="width:80px">&nbsp;</td>
      <td class="side_label_middle">  <fmt:message key="jsp.SPInventoryContentOverview.avg_price" />  : </td>
      <td><span id="avg_price" ></span><strong>  <fmt:message key="jsp.SPInventoryContentOverview.currency" /> </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle"> <fmt:message key="jsp.SPInventoryContentOverview.name" />   : </td>
      <td id="name"></td>
      <td>&nbsp;</td>
      <td class="side_label_middle"> <fmt:message key="jsp.SPInventoryContentOverview.min_price" /> : </td>
      <td><span id="min_price" ></span><strong>  <fmt:message key="jsp.SPInventoryContentOverview.currency" /> </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle"> <fmt:message key="jsp.SPInventoryContentOverview.threshold" /> : </td>
      <td><span id="threshold" ></span><strong> <fmt:message key="jsp.SPInventoryContentOverview.piece" /> </strong></td>
      <td>&nbsp;</td>
      <td class="side_label_middle">  <fmt:message key="jsp.SPInventoryContentOverview.max_price" />: </td>
      <td><span id="max_price" ></span><strong>  <fmt:message key="jsp.SPInventoryContentOverview.currency" /> </strong></td>
    </tr>   
    <tr>
      <td class="side_label_middle">  <fmt:message key="jsp.SPInventoryContentOverview.curr_quantity" />: </td>
      <td colspan="4"><span id="curr_quantity" ></span><strong>  <fmt:message key="jsp.SPInventoryContentOverview.piece" /> </strong></td>
    </tr>
	</table>
	
	<div class="clearer"></div>
	
	<script>	
	$(function(){		
	<%
        BioSpInventoryContent spInventoryContent = (BioSpInventoryContent) request.getAttribute(Default.SP_CATEGORY_INFO_ATTR_NAME);
		if (spInventoryContent != null){
			// String spCatId  = Common.getDisplayString(spInventoryContent.getId(),"");
			String code  = Common.getDisplayString(spInventoryContent.getCode(),"");
			String name  = Common.getDisplayString(spInventoryContent.getName(),"");
			String threshold  = Common.getDisplayString(spInventoryContent.getThreshold(),"");
			String quantity  = Common.getDisplayString(spInventoryContent.getAvailableQuantity(),"");
			String avgPrice  = Common.formatMoneyValue(spInventoryContent.getAvgUnitPrice());
			String minPrice = Common.formatMoneyValue(spInventoryContent.getMinPrice());
			String maxPrice = Common.formatMoneyValue(spInventoryContent.getMaxPrice());
	%>

			$("#code").html("<%=code%>");
			$("#name").html("<%=name%>");
			$("#threshold").html("<%=threshold%>");
			$("#curr_quantity").html("<%=quantity%>");
			$("#avg_price").html("<%=avgPrice%>");
			$("#min_price").html("<%=minPrice%>");
			$("#max_price").html("<%=maxPrice%>");
		<%}%>
	});
	</script>
</body>

</html>
