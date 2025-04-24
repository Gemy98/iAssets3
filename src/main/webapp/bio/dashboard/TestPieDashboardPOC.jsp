<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib prefix="Charts" tagdir="/WEB-INF/tags/Charts"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String langCode = WebUtil.getSessionLanguage(request);

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
	<Charts:PieChart idKey="key" pieChart="${pieChart }" langCode="<%= langCode %>"/>
</body>
</html>
