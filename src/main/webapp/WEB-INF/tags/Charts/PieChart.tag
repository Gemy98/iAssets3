<%@ tag body-content="empty" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="idKey" required="true" type="java.lang.String"%>
<%@ attribute name="pieChart" required="true"
	type="com.iassets.common.bo.charts.PieChart"%>
<%@ attribute name="langCode" required="true" type="java.lang.String"%>

<c:set var="title"
	value="${ LocalizationManager.getLiteral(pieChart.getTitleLiteralKey(), langCode)}" />
<c:set var="seriesText"
	value="${ LocalizationManager.getLiteral(pieChart.getSeriesLiteralKey(), langCode)}" />

<%@ attribute name="dir" required="false" type="java.lang.String"%>
<%@ attribute name="width" required="false" type="java.lang.String"%>
<%@ attribute name="heigh" required="false" type="java.lang.String"%>


<c:set var="actualDir" value="${dir != null ? dir : 'rtl'}" />
<c:set var="actualWidth" value="${width != null ? width : '310px'}" />
<c:set var="actualHeigh" value="${heigh != null ? heigh : '400px'}" />

<div id="${idKey}"
	style="min-width: ${actualWidth}; height: ${actualHeigh }; margin: 0 auto"></div>


<script src="js/charts/highcharts.js"></script>
<script src="js/charts/exporting.js"></script>
<script src="js/charts/export-data.js"></script>

<script type="text/javascript">

Highcharts.chart('${idKey}', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: '${title}'
    },
    tooltip: {
   		<c:if test="${actualDir == 'ltr'}">
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		</c:if>
		<c:if test="${actualDir == 'rtl'}">
	        pointFormat: '<span dir="rtl"> {series.name} <b> : {point.percentage:.1f}  %</b> </span>'
		</c:if>
		
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false
            },
            showInLegend: true
        }
    },
    series: [{
        name: '${seriesText}',
        colorByPoint: true,
        data: [
        <c:forEach var="segment" items="${pieChart.getSegmentList()}"  varStatus="loop">
        	<c:if test="${loop.index != 0}">
        		,
        	</c:if> 
        		{
        			<c:if test="${loop.index == 0}">
        			 	sliced: true,
        	            selected: true,
            		</c:if> 
        				name: ' <div align="center"> <b>${LocalizationManager.getLiteral(segment.getSegmentTitleLiteralKey(), langCode) }</b></div>',
                		y: ${segment.getValue()}
                }
		</c:forEach>
        ]
    }]
});
</script>
