<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.general.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>
<!--     <h3 style="text-align: right; color: green; text-decoration: underline">معلومات بند قطع الغيار</h3> -->
	<table class="layout_table" style="float: right"> 
	<tr>
      <td class="side_label_middle">الكود : </td>
      <td id="code"></td>
      <td style="width:80px">&nbsp;</td>
      <td class="side_label_middle">متوسط سعر القطعة  : </td>
      <td><span id="avg_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle">الاسم : </td>
      <td id="name"></td>
      <td>&nbsp;</td>
      <td class="side_label_middle">أقل سعر شراء : </td>
      <td><span id="min_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle">حد الأمان : </td>
      <td><span id="threshold" ></span><strong> &nbsp;قطعة </strong></td>
      <td>&nbsp;</td>
      <td class="side_label_middle">أعلى سعر شراء : </td>
      <td><span id="max_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>   
    <tr>
      <td class="side_label_middle">الكمية المتاحة : </td>
      <td colspan="4"><span id="curr_quantity" ></span><strong> &nbsp;قطعة </strong></td>
    </tr>
	</table>
	
	<div class="clearer"></div>
	
	<script>	
	$(function(){		
	<%
        GenSpInventoryContent spInventoryContent = (GenSpInventoryContent) request.getAttribute(Default.SP_CATEGORY_INFO_ATTR_NAME);
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
