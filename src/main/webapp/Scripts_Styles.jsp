<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.WebUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
// Boolean containsForm = (Boolean) request.getAttribute("containsForm");  
Boolean report = (Boolean) request.getAttribute("report");
String appDirVar =  WebUtil.getCurrentlyActiveAppDirectory(request);
%>   
<script>
var appDirVar = "<%=appDirVar%>";
</script>


<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="css/default.css">
<link rel="stylesheet" type="text/css" href="css/component.css">
<script src="js/modernizr.custom.js"></script>
		
		
<link type="text/css" rel="stylesheet" href="css/jquery-ui-themes-1.10.4/themes/flick/jquery-ui.min.css"/>
<link type="text/css" rel="stylesheet" href="css/jquery-ui-themes-1.10.4/themes/flick/jquery.ui.theme.css"/>
<link rel="stylesheet" href="js/appendGrid/jquery.appendGrid-1.3.5.min.css" />

<%if(report == null || !report){%>
	<link rel="stylesheet" href="css/pure-min.css"/>
	<link rel="stylesheet" href="css/pure-skin-mine.css"/>
<%}%>

<!--<link rel="stylesheet" href="js/uploadify/uploadify.css"/>-->
<!--  <link type="text/css" rel="stylesheet" href="css/slidedown-menu.css"/>  -->
<link type="text/css" rel="stylesheet" href="js/ajaxloader/ajaxloader.css"/>
<!--<link type="text/css" rel="stylesheet" href="css/style.css"/>-->


<link type="text/css" rel="stylesheet" href="css/style_${sessionScope.language == 'ar' ? 'rtl' : 'ltr'}.css"/>
<link type="text/css" rel="stylesheet" href="css/slidedown-menu_${sessionScope.language == 'ar' ? 'rtl' : 'ltr'}.css"/>



<link type="text/css" rel="stylesheet" href="css/file-upload.css"/>
<!--<link type="text/css" rel="stylesheet" href="jOrgChart/example/css/jquery.jOrgChart.css" />-->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/js_constants.js"></script>
<script type="text/javascript" src="js/slidedown-menu.js"></script>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.4.min.js"></script>

<!-- <link type="text/css" rel="stylesheet" href="css/jquery-ui-tabs.css"> -->
<!-- <script type="text/javascript" src="js/jquery-ui-tabs.js"></script> -->

<c:if test="${sessionScope.language =='ar'}">
	<script type="text/javascript" src="js/datepicker-ar.js"></script>
</c:if>

<script type="text/javascript" src="js/jquery.cookie.js"></script>
<!--<script type="text/javascript" src="js/fileinput/jquery.fileinput.min.js"></script>-->
<script type="text/javascript" src="js/appendGrid/jquery.appendGrid-1.3.5.min.js"></script>
<script type="text/javascript" src="js/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/validation/additional-methods.js"></script>
<script type="text/javascript" src="js/validation/jquery.ui.datepicker.validation.min.js"></script>
<script type="text/javascript" src="js/js_localized_literals.js"></script>

<script type="text/javascript" src="js/validation/messages_${sessionScope.language}.js"></script>


<%-- <script type="text/javascript" src="${sessionScope.language == 'en' ? 'js/datepicker-en.js':'js/datepicker-ar.js'}"></script> --%>
<!--<script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>-->
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<!--<script type="text/javascript" src="jOrgChart/example/jquery.jOrgChart.js"></script>-->
<script type="text/javascript" src="js/ajaxloader/ajaxloader.js"></script>

<script src="js/charts/highcharts.js"></script>
<script src="js/charts/highcharts-more.js"></script>
<script src="js/charts/solid-gauge.js"></script>

<%if(report != null && report){%>
	
    <link type="text/css" rel="stylesheet" href="js/jquery.tablesorter/themes/blue/style.css">
	<script type="text/javascript" src="js/jquery.tablesorter/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="js/jquery.tablesorter/jquery.tablesorter.widgets.js"></script> 

<!-- 
	<link type="text/css" rel="stylesheet" href="js/tablesorter-master/css/theme.blue.css">
	<script type="text/javascript" src="js/tablesorter-master/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="js/tablesorter-master/js/jquery.tablesorter.widgets.js"></script>
-->
<%}else{%>
	<script>
	$(function(){initPage()});
	</script>
<%}%>