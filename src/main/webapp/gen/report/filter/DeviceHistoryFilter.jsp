<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.iassets.common.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="gen/ViewDeviceHistory" target="_blank" onsubmit="return _onSubmitDeviceSearchForm()">
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
			<tr>
				<td class="side_label_middle">اجمالي تكلفة الصيانة (قطع الغيار)السابقة :</td>
				<td><input type="text" name="prevCost" id="prevCost" data-rule-number="true" data-msg-number="القيمة المدخلة غير مقبولة" ></td>
			</tr>
			<tr>
				<td class="side_label_middle">كود الجهاز :</td>
				<td><input type="text" name="code" id="deviceCode"></td>
			</tr>
			<tr>
				<td class="side_label_middle">الرقم التسلسلي :</td>
				<td><input type="text" name="sn" id="deviceSerial"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="عرض"></td>
			</tr>
		</table>
	</form>
</body>
</html>