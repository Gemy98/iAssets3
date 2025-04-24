<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib prefix="Charts" tagdir="/WEB-INF/tags/Charts"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String langCode = WebUtil.getSessionLanguage(request);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<style>
.item1 {
	grid-area: sec1;
}
.item2 {
	grid-area: sec2;
}
.item3 {
	grid-area: sec3;
}
.item4 {
	grid-area: menu;
}
.item5 {
	grid-area: main;
}
.item6 {
	grid-area: sec4;
}
.grid-container {
	width: 90%;
	display: grid;
	grid-template-areas:
 'sec1 sec2 sec2 sec2 sec3 sec3 sec4 sec4 sec4 sec4 sec4 sec4 sec4'  
 'menu menu menu menu menu menu sec4 sec4 sec4 sec4 sec4 sec4 sec4';
	grid-gap: 6px;
	background-color: #e5e5e5;
	padding: 10px;

	margin-bottom: 20px;
}
.grid-container>div {
	background-color: rgba(255, 255, 255, 0.8);
	margin: 2px;
	border: 1px solid rgba(0, 0, 0, .07);
	border-radius: 5px;
	box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 7.5px 1.5px;
}
.style1 {
	font-size: 30px;
	font-weight: bold;
}
.style2 {
	font-size: 12px;
	font-weight: bold;
}
.table_of_totals {
	color: #969292;
	font-size: 18px;
	width: 100%;
	height: 100%;
}
.table_of_totals td {
	border-bottom: 1px solid #e6e6e6;
}
</style>
</head>
<body>
  
<div class="grid-container">
  <div class="item1"
			style="color: #fff; background-color: #1baf99; padding: 5px;">
    <table>
      <tr>
        <td rowspan="2"><img src="image/6.png" width="100"
						height="100" /></td>
        <td><div align="left">إجمالي أوامر العمل</div></td>
      </tr>
      <tr>
        <td><div align="center" class="style1">1273</div></td>
      </tr>
      <tr>
        <td colspan="2"><div align="right"> Opened Today <span class="style2">140</span> </div></td>
      </tr>
    </table>
  </div>
  <div class="item2"
			style="color: #fff; background-color: #d8534e; padding: 5px;">
    <table>
      <tr>
        <td rowspan="2"><img src="image/1.png" width="100"
						height="100" /></td>
        <td><div align="left">أوامر العمل المفتوحة</div></td>
      </tr>
      <tr>
        <td><div align="center" class="style1">406</div></td>
      </tr>
      <tr>
        <td colspan="2"><div align="right"> Today <span class="style2">20</span> </div></td>
      </tr>
    </table>
  </div>
  <div class="item3"
			style="color: #fff; background-color: #418bca; padding: 5px;">
    <table>
      <tr>
        <td rowspan="2"><img src="image/4.png" width="100"
						height="100" /></td>
        <td>أوامر العمل المنجزة</td>
      </tr>
      <tr>
        <td><div align="center" class="style1">876</div></td>
      </tr>
      <tr>
        <td colspan="2"><div align="right"> Today <span class="style2">120</span> </div></td>
      </tr>
    </table>
  </div>
  <div class="item4">
    <Charts:ColumnBar idKey="stacked"  
				 bar="${stackedBarChart }"  langCode="<%=langCode %>"/>
  </div>
  <div class="item6">
    <table border="0" cellspacing="5" cellpadding="2"
				class="table_of_totals">
      <tr>
        <td><div style="float: right;">المواقع</div>
          
            <div align="left">12</div>
          </td>
      </tr>
      <tr>
        <td><div style="float: right;">الاقسام</div>
          <div align="left">130</div></td>
      </tr>
      <tr>
        <td><div style="float: right;">KPI</div>
          <div align="left">98</div></td>
      </tr>
      <tr>
        <td><div style="float: right;">إجمالي المستشفيات</div>
          <div align="left">11</div></td>
      </tr>
      <tr>
        <td><div style="float: right;">إجمالي المراكز الصحية</div>
          <div align="left">60</div></td>
      </tr>
    </table>
  </div>
</div>
<%-- 					<Bar:ColumnBar idKey="basic" titleText="1st" yaxisText="yaxis text" --%>
<%-- 										categories="${categories }" nameDataMap="${nameDataMap }" /> --%>
<%-- 					<Bar:ColumnBar idKey="basic2" titleText="2nd" --%>
<%-- 										yaxisText="yaxis text" categories="${categories }" --%>
<%-- 										nameDataMap="${nameDataMap2 }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd1" name="${name }" --%>
<%-- 										min="${min }" data="69" valueSuffix="${valueSuffix }" --%>
<%-- 										enabelFloat="false" /> --%>
<%-- 					<KPI:Legend max="${max }" idKey="atdA" name="${name }" --%>
<%-- 										min="${min }" data="${data }" valueSuffix="${valueSuffix }" --%>
<%-- 										entries="${entries }" enabelFloat="false" /> --%>
</body>
</html>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd2" name="${name }" --%>
<%--   										min="${min }" data="70" valueSuffix="${valueSuffix }" />   --%>
<%--  					<Gauge:SolidGauge max="${max }" idKey="atd3" name="${name }"  --%>
<%--   										min="${min }" data="71" valueSuffix="${valueSuffix }" />   --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd4" name="${name }" --%>
<%-- 										min="${min }" data="79" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd5" name="${name }" --%>
<%-- 										min="${min }" data="80" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd6" name="${name }" --%>
<%-- 										min="${min }" data="81" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd7" name="${name }" --%>
<%-- 										min="${min }" data="89" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd8" name="${name }" --%>
<%-- 										min="${min }" data="90" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd9" name="${name }" --%>
<%-- 										min="${min }" data="91" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd10" name="${name }" --%>
<%-- 										min="${min }" data="100" valueSuffix="${valueSuffix }" /> --%>
<%-- 					<Gauge:SolidGauge max="${max }" idKey="atd11" name="${name }" --%>
<%--  										min="${min }" data="${data }" valueSuffix="${valueSuffix }" enabelFloat="false"/> --%>
<%-- 										<Gauge:SolidGauge max="${max }" idKey="atd12" name="${name }" --%>
<%-- 															min="${min }" data="${data }" valueSuffix="${valueSuffix }" /> --%>
