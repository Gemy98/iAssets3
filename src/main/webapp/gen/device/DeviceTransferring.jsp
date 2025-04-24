<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.general.util.HtmlUtil,com.iassets.common.util.*,com.iassets.general.entity.*"%>
<html dir="rtl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form action="gen/DeviceTransferringProcess" method="post" enctype="multipart/form-data">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title">شاشة نقل أو سحب جهاز</h1></td>
			</tr>
			<tr>
				<td colspan="2"><%@ include file="../DeviceOverview.jsp"%>
				</td>
			</tr>

			<tr>
				<td class="side_label_middle">تاريخ النقل / السحب :</td>
				<td><input type="text" name="transferDate" id="transferDate"
					class="caldr" required></td>
			</tr>

			<tr>
				<td class="side_label_top">سبب النقل :</td>
				<td><textarea name="transferReason" id="transferReason"
						required></textarea></td>
			</tr>
			<tr>
				<td class="side_label_top">تقرير النقل / السحب :</td>
				<td><input type="file" name="transferReportUrl"
					id="transferReportUrl" multiple class="requiredFile"></td>
			</tr>
			<tr>
				<td class="side_label_middle">الجهة المنقول إليها :</td>
				<td><input type="text" name="transferDestination"
					id="transferDestination" size="40" required></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="button" id="button" value="اتمام العملية"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
			</tr>
		</table>
	</form>
  <script>
	$(function(){ 
		 $("#button").click(function(){
			 if($("form").valid())
			    return confirm("لن يسمح بإجراء أي عملية على هذا الجهاز بعد إتمام هذه العملية .. هل تريد الاستمرار؟");    
		 });
	});
  </script>
</body>
</html>