<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*"%>
<% String langCode = WebUtil.getSessionLanguage(request); %>
<html dir="rtl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post" action="gen/UpdateSPCategoryProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title">تعديل بيانات صنف بالمستودع</h1></td>
    </tr>
    <tr>
      <td class="side_label_middle">الكود : </td>
      <td><input name="code" type="text" id="code" size="10" required data-msg-remote="يوجد في المستودع  صنف بنفس الكود">
     </td>
    </tr>
    <tr>
      <td  class="side_label_middle">الاسم : </td>
      <td><input name="name" type="text" id="name" size="40" required></td>
    </tr>
    <tr>
	  <td class="side_label_top">ملاحظات / مواصفات :</td>
	  <td><textarea name="specs" id="specs"></textarea></td>
	</tr>
    <tr>
      <td  class="side_label_middle">الحد الأدنى : </td>
      <td><input name="threshold" type="text" id="threshold" size="10" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle">نوع العملية : </td>
      <td><input type="radio" name="input" id="radio" value="1" checked>
        إضافة / تسوية بالزيادة
        <%if(WebUtil.userHasRightToWithdrawFromSPInventoryWithoutJobOrder(request)){ %>
        <input type="radio" name="input" id="radio2" value="0">
        سحب / تسوية بالنقص
        <%} %>
        </td>
    </tr>
    <tr>
      <td  class="side_label_middle">الكمية : </td>
      <td><input name="quantity" type="text" id="quantity" size="10" value="0" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle">سعر القطعة : </td>
      <td><input name="price" type="text" id="price" size="10" value="0" required data-rule-number="true"><strong> ريال سعودي </strong> </td>
    </tr>
    <tr>
      <td  class="side_label_top">ملاحظات : </td>
      <td><textarea name="notes" id="notes"></textarea></td>
    </tr>
    
    <tr>
      <td class="side_label_middle">الكمية المتاحة : </td>
      <td><span id="curr_quantity" ></span><strong> &nbsp;قطعة </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle">متوسط سعر القطعة  : </td>
      <td><span id="avg_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle">أقل سعر شراء : </td>
      <td><span id="min_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle">أعلى سعر شراء : </td>
      <td><span id="max_price" ></span><strong> &nbsp;ريال سعودي </strong></td>
    </tr>
    
    <tr>
      <td colspan="2"><input type="submit" value="عدل بيانات الصنف"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
<script>
$(function(){

addValidationRuleForSpCatDuplication();

<%GenSpInventoryContent spInventoryContent = (GenSpInventoryContent)request.getAttribute(Default.SP_CATEGORY_INFO_ATTR_NAME);

if (spInventoryContent != null){
	String spCatId  = Common.getDisplayString(spInventoryContent.getId(),"");
	String code  = Common.getDisplayString(spInventoryContent.getCode(),"");
	String name  = Common.getDisplayString(spInventoryContent.getName(),"");
	String specs  = Common.getDisplayString(spInventoryContent.getSpecs(),"");
	String threshold  = Common.getDisplayString(spInventoryContent.getThreshold(),"");
	String quantity  = Common.getDisplayString(spInventoryContent.getAvailableQuantity(),"");
	String price  = Common.formatMoneyValue(spInventoryContent.getAvgUnitPrice());
	String minPrice = Common.formatMoneyValue(spInventoryContent.getMinPrice());
	String maxPrice = Common.formatMoneyValue(spInventoryContent.getMaxPrice());%>

	appendIdElement("spCatId","<%=spCatId%>");
	setTextFieldValue("code","<%=code%>");
	setTextFieldValue("name","<%=name%>");
	setInnerHTML("specs","<%=specs%>");
	setTextFieldValue("threshold","<%=threshold%>");
	$("#curr_quantity").html("<%=quantity%>");
	$("#avg_price").html("<%=price%>");
	$("#min_price").html("<%=minPrice%>");
	$("#max_price").html("<%=maxPrice%>");
<%}%>
});
</script>

</body>
</html>
