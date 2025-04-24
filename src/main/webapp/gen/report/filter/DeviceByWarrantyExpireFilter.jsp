<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action="gen/ViewDevicesByWarrantyExpire" target="_blank">
		<table class="layout_grid">
		    <tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
			<tr>
				<td class="side_label_middle">الفترة من :</td>
				<td><input type="text" name="pFrom" id="pFrom" required	class="caldr"></td>
			</tr>
			<tr>
				<td class="side_label_middle">الي :</td>
				<td><input type="text" name="pTo" id="pTo" class="caldr"></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="عرض"></td>
			</tr>
		</table>
	</form>
</body>
</html>