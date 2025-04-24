<%@ page import="com.iassets.general.util.HtmlUtil, com.iassets.common.util.WebUtil" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String langCode = WebUtil.getSessionLanguage(request); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="post"  action="gen/AddNewSPCategoryProcess">
  <table  class="layout_grid">
    <tr>
      <td colspan="2"><h1 class="page_title">اضافة صنف جديد بالمستودع</h1></td>
    </tr>
    <tr>
      <td class="side_label_middle">الكود : </td>
      <td><input type="text" name="code" id="code" size="10" required data-msg-remote="يوجد في المستودع  صنف بنفس الكود"></td>
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
      <td  class="side_label_middle">الكمية : </td>
      <td><input name="quantity" type="text" id="quantity" size="10" required data-rule-digits="true"></td>
    </tr>
    <tr>
      <td  class="side_label_middle">سعر القطعة : </td>
      <td><input name="price" type="text" id="price" size="10" required data-rule-number="true"><strong> ريال سعودي </strong></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" name="button" id="button" value="اضف الصنف"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
    </tr>
  </table>
  <script>
  $(function(){
	  addValidationRuleForSpCatDuplication();
  });
  </script>
</form>
</body>
</html>