<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.common.entity.*,java.util.*"%>
<% String langCode = WebUtil.getSessionLanguage(request); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form method="post" action='gen/<%=WebUtil.getSearchProcessURL(request)%>' target="_blank">
		<table class="layout_grid">
			<tr>
                <td colspan="2"><h1 class="page_title"><%=LocalizationManager.getLiteral(Default.REPORT_FILTER_SCREEN_TITLE_LITERAL_KEY ,WebUtil.getSessionLanguage(request) )%></h1></td>
            </tr>
            <tr>
               <td colspan="2"><%@include file="/ChooseReportTypePreference.jsp" %></td>
            </tr>
			<tr>
				<td class="side_label_middle">الشهر :</td>
				<td><%=HtmlUtil.getMonthesAsHtmlSelect("month",	"auto_off" , langCode)%></td>
			</tr>
			<tr>
				<td class="side_label_middle">السنة :</td>
				<td>
					<!--class="auto_add_all auto_off"-->
					<select name="year" id="year" class="auto_off">
					</select> 
					<script>
						var currentYear = new Date().getFullYear();
						for (var i = 2014; i <= currentYear + 1; i++)
							$("#year").append(new Option(i, i));
					</script>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="عرض"></td>
			</tr>
		</table>
	</form>
</body>
</html>