<%@ page language="java" pageEncoding="UTF-8" import="com.iassets.general.util.*,com.iassets.common.util.WebUtil"%>
<% String langCode = WebUtil.getSessionLanguage(request); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>تقييم الأداء</title>
</head>
<body>
<form method="post" target="_blank" action="gen/ViewPerformanceEvaluation">
  <table style="width:70%" class="layout_grid">
    <tr>
       <td colspan="4"><h1 class="page_title"> شاشة تقرير تقييم الأداء</h1></td>
    </tr>
    <tr>
      <td colspan="1" class="side_label_middle">الشهر : </td>
      <td colspan="1"><%=HtmlUtil.getMonthesAsHtmlSelect("month","required", langCode)%></td>

      <td colspan="1" class="side_label_middle">السنة : </td>
      <td colspan="1"><select name="year" id="year" required></select></td>  
    </tr>
    <tr>
       <td colspan="4" align="center"><input type="submit" name="submit" class="" value="عرض التقرير"></td>
    </tr>
  </table>
</form>
<script type="text/javascript">
$(function() {
	// init years dropdown list
	var currentYear = new Date().getFullYear();
	for (var i=currentYear-1; i <=currentYear+1 ; i++)
		$("#year").append(new Option(i, i));
})

</script>
</body>
</html>
