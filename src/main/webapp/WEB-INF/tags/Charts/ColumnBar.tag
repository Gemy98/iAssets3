<%@tag body-content="empty" pageEncoding="UTF-8"
					trimDirectiveWhitespaces="true" import="com.iassets.common.bo.charts.StackedBarChart"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="idKey" required="true" type="java.lang.String"%>
<%@ attribute name="bar" required="true" type="com.iassets.common.bo.charts.BarChart"%>
<%@ attribute name="langCode" required="true" type="java.lang.String"%>

<c:set var="title" value="${ LocalizationManager.getLiteral(bar.getTitleLiteralKey(), langCode)}" />
<c:set var="barSegmentTitle" value="${ LocalizationManager.getLiteral(bar.getBarSegmentTitleLiteralKey(), langCode)}" />

<%@ attribute name="dir" required="false" type="java.lang.String"%>
<%@ attribute name="width" required="false" type="java.lang.String"%>
<%@ attribute name="heigh" required="false" type="java.lang.String"%>


<c:set var="actualDir" value="${dir != null ? dir : 'rtl'}" />
<c:set var="actualWidth" value="${width != null ? width : '310px'}" />
<c:set var="actualHeigh" value="${heigh != null ? heigh : '400px'}" />

<h4>${title}</h4>
<div id="${idKey}" style="min-width: ${actualWidth}; height: ${actualHeigh }; margin: 0 auto"></div>


<script src="js/charts/highcharts.js"></script>
<script src="js/charts/exporting.js"></script>
<script src="js/charts/export-data.js"></script>

<script type="text/javascript">

Highcharts.chart('${idKey}', {
    chart: {
        type: 'column'
    },
    title: {
        text: '${titleText}'
    },
    xAxis: {
	   categories: [
	   	<c:forEach var="segment" items="${bar.getSegmentList()}"  varStatus="loop">
		   	<c:if test="${loop.index != 0}">
		   		,
		   	</c:if> 
	   		'${ LocalizationManager.getLiteral(segment.getBarTitleLiteralKey(), langCode) }'
	   	</c:forEach>
	   ],
	   crosshair: true
    },
    yAxis: {
	   min: 0,
	   title: {
	       text: '${barSegmentTitle}'
	   }, 
	   	stackLabels: {
    	enabled: true,
	   	style: {
		   fontWeight: 'bold',
    	   color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
			}
		}
    }, 
    legend: {	
	   align: 'right',		
	   x: -30,		
	   verticalAlign: 'top',		
	   y: 25,		
	   floating: true,		
	   backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',		
	   borderColor: '#CCC',		
	   borderWidth: 1,		
	   shadow: false		
    }, 
    tooltip: {
		<c:if test="${actualDir == 'ltr'}">
	   		headerFormat: '<span dir="${actualDir}" style="font-size:10px;">{point.key}</span><table>',
	   		pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' + '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
		</c:if>
		<c:if test="${actualDir == 'rtl'}">
	   		headerFormat: '<span dir="${actualDir}" style="font-size:10px;"><div align="right"> {point.key} </div></span><table>',
	   		pointFormat: '<tr><td style="color:{series.color};padding:0"><b> <div align="right"> {point.y:.1f} </div></b></td>' +
	   		'<td style="padding:0"><div align="right"> : {series.name} </div></td></tr>',
		</c:if>
	   footerFormat: '</table>',
	   shared: true,
	   useHTML: true
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
		<% if(bar instanceof StackedBarChart) {  %>
		           ,
			   stacking: 'normal',
		           dataLabels: {
		               enabled: true,
		               color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
		           }
		<% } %>
        }
    },
    series: [
    	
    	<c:forEach var="segmentLiteralKey" items="${bar.getBarSegmentLiteralKeyArray()}" varStatus="outerLoop">
				<c:if test="${outerLoop.index != 0}">
	        		,
	        	</c:if> 
			{ 	name: '${LocalizationManager.getLiteral(segmentLiteralKey, langCode) }',
				data: [
					<c:forEach var="barSegment" items="${bar.getSegmentList()}"  varStatus="loop">
			        	<c:if test="${loop.index != 0}">
			        		,
			        	</c:if> 
			        	${barSegment.getValues().get(outerLoop.index)}
					</c:forEach>
				]	
			}
		</c:forEach>
    
    ]
});
</script>
