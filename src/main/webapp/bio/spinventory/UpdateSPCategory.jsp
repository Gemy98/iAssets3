<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.common.util.*,com.iassets.biomedical.util.HtmlUtil,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String langCode = WebUtil.getSessionLanguage(request);	 %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post" action="bio/UpdateSPCategoryProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.UpdateSPCategory.pagetitle" /></h1></td>
    </tr>
    <tr>
      <td class="side_label_middle"> <fmt:message key="jsp.UpdateSPCategory.code" /> : </td>
      <td><input name="code" type="text" id="code" size="10" required data-msg-remote=" <fmt:message key="jsp.UpdateSPCategory.code.msg" />">
     </td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.name" /> : </td>
      <td><input name="name" type="text" id="name" size="40" required></td>
    </tr>
    <tr>
      <td  class="side_label_middle"> <fmt:message key="jsp.UpdateSPCategory.threshold" />  : </td>
      <td><input name="threshold" type="text" id="threshold" size="10" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.input" /> : </td>
      <td><input type="radio" name="input" id="radio" value="1" checked>
       <fmt:message key="jsp.UpdateSPCategory.input1" />
        <%if(WebUtil.userHasRightToWithdrawFromSPInventoryWithoutJobOrder(request)){ %>
        <input type="radio" name="input" id="radio2" value="0">
        <fmt:message key="jsp.UpdateSPCategory.input2" />
        <%} %>
        </td>
    </tr>
    <tr>
      <td  class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.quantity" />  : </td>
      <td><input name="quantity" type="text" id="quantity" size="10" value="0" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle"> <fmt:message key="jsp.UpdateSPCategory.price" /> : </td>
      <td><input name="price" type="text" id="price" size="10" value="0" required data-rule-number="true"><strong> <fmt:message key="jsp.UpdateSPCategory.currency" /> </strong> </td>
    </tr>
    <tr>
      <td  class="side_label_top"> <fmt:message key="jsp.UpdateSPCategory.notes" /> : </td>
      <td><textarea name="notes" id="notes"></textarea></td>
    </tr>
    <tr>
      <td class="side_label_middle"> <fmt:message key="jsp.UpdateSPCategory.curr_quantity" /> : </td>
      <td><span id="curr_quantity" ></span><strong> &nbsp;<fmt:message key="jsp.UpdateSPCategory.quantityUnit" />  </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.avg_price" />: </td>
      <td><span id="avg_price" ></span><strong> &nbsp;<fmt:message key="jsp.UpdateSPCategory.currency" /> </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.min_price" />: </td>
      <td><span id="min_price" ></span><strong> &nbsp;<fmt:message key="jsp.UpdateSPCategory.currency" /> </strong></td>
    </tr>
    <tr>
      <td class="side_label_middle"><fmt:message key="jsp.UpdateSPCategory.max_price" /> : </td>
      <td><span id="max_price" ></span><strong> &nbsp;<fmt:message key="jsp.UpdateSPCategory.currency" /> </strong></td>
    </tr>
    
    <tr>
      <td colspan="2"><input type="submit" value='<fmt:message key="jsp.UpdateSPCategory.submitBtn" />'><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
</form>
<script>
$(function(){
	
addValidationRuleForSpCatDuplication();

<%
BioSpInventoryContent spInventoryContent = (BioSpInventoryContent)request.getAttribute(Default.SP_CATEGORY_INFO_ATTR_NAME);

if (spInventoryContent != null){
	String spCatId  = Common.getDisplayString(spInventoryContent.getId(),"");
	String code  = Common.getDisplayString(spInventoryContent.getCode(),"");
	String name  = Common.getDisplayString(spInventoryContent.getName(),"");
	String threshold  = Common.getDisplayString(spInventoryContent.getThreshold(),"");
	String quantity  = Common.getDisplayString(spInventoryContent.getAvailableQuantity(),"");
	String price  = Common.formatMoneyValue(spInventoryContent.getAvgUnitPrice());
	String minPrice = Common.formatMoneyValue(spInventoryContent.getMinPrice());
	String maxPrice = Common.formatMoneyValue(spInventoryContent.getMaxPrice());%>

	appendIdElement("spCatId","<%=spCatId%>");
	setTextFieldValue("code","<%=code%>");
	setTextFieldValue("name","<%=name%>");
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
