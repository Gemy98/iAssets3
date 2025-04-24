<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.bo.charts.Gauge, com.iassets.common.util.* , java.util.*"%>
<%@ taglib prefix="Charts" tagdir="/WEB-INF/tags/Charts"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String langCode = WebUtil.getSessionLanguage(request);

	List<Gauge> list = (Vector<Gauge>) request.getAttribute("listOfGauges");

	List<Gauge> list2 = (Vector<Gauge>) request.getAttribute("listOfGauges2");

%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.grid-container {
	display: grid;
	grid-template-columns: auto auto auto auto auto;
	padding: 5px;
}

.grid-item {
	font-size: 16px;
	text-align: center;
	margin: 10px;
	border: 1px solid rgba(0, 0, 0, .07);
	border-radius: 2px;
	box-shadow: rgba(0, 0, 0, 0.05) 0px 0px 7.5px 1.5px;
}

.summary {
	margin: 20px auto;
	border: 1px solid rgba(0, 0, 0, .07);
	border-radius: 4px;
	color: #fff;
	font-size:12px;
}

.summary th {
	background-color: #322e2e;
}

.sectionContainer {
}
</style>
</head>
<body>
	<%
		int counter = 1;
	%>
	<section class="sectionContainer">
		<h1><%=LocalizationManager.getLiteral("servlet.charts.group1", langCode)%></h1>
		<div class="grid-container">
			<%
				for (Gauge g : list) {
			%>
			<div class="grid-item">
				<Charts:SolidGauge
					idKey='<%="atd"+(counter++)%>'
					enabelFloat="false" gauge="<%=g %>" langCode="<%=langCode %>"
					/>
			</div>
			<%
				}
			%>

		</div>
	</section>
	<hr/>
	<section class="sectionContainer">

		<h1><%=LocalizationManager.getLiteral("servlet.charts.group2", langCode)%></h1>

		<div class="grid-container">
			<%
				for (Gauge g : list2) {
			%>
			<div class="grid-item">
				<Charts:SolidGauge
					idKey='<%="atd"+(counter++)%>'
 					gauge="<%=g %>" langCode="<%=langCode %>"
					enabelFloat="false" />
			</div>
			<%
				}
			%>

		</div>
	</section>
</body>
</html>
