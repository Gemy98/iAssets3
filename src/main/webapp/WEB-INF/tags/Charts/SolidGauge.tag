<%@ tag body-content="empty" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="idKey" required="true" type="java.lang.String"%>
<%@ attribute name="gauge" required="true"
	type="com.iassets.common.bo.charts.Gauge"%>
<%@ attribute name="langCode" required="true" type="java.lang.String"%>

<%@ attribute name="width" required="false" type="java.lang.String"%>
<%@ attribute name="heigh" required="false" type="java.lang.String"%>
<%@ attribute name="enabelFloat" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="showLegend" required="false"
	type="java.lang.Boolean"%>
<%@ attribute name="valueSuffix" required="false"
	type="java.lang.String"%>

<c:set var="min" value="${ 0 }" />
<c:set var="data" value="${ gauge.getMeasureValue().getValue()}" />
<c:set var="max"
	value="${ gauge.getMeasureValue().getValue() + gauge.getComplementingMeasureValue().getValue()}" />

<c:set var="title"
	value="${ LocalizationManager.getLiteral(gauge.getTitleLiteralKey(), langCode)}" />

<c:set var="actualWidth" value="${width != null ? width : '230px'}" />
<c:set var="actualHeigh" value="${heigh != null ? heigh : '130px'}" />
<c:set var="actualShowLegend"
	value="${ showLegend == false ? false : true }" />
<c:set var="actualFloat"
	value="${enabelFloat != null && enabelFloat == true? '; float: left' : ''}" />
<fmt:formatNumber var="valueSuffixPercentage"
	value="${ (data / max) * 100}" maxFractionDigits="2" />
<c:set var="actualValueSuffix"
	value='${valueSuffix != null? valueSuffix : valueSuffixPercentage } ${valueSuffix != null? "" : " %"}' />

<h4>${title}</h4>
<div id="${idKey}"
	style="display:inline-block; width: ${actualWidth}; height: ${actualHeigh }${actualFloat}"></div>

<c:if test="${ actualShowLegend }">

	<c:set var="totalTitle"
		value="${ LocalizationManager.getLiteral(gauge.getTotalLiteralKey(), langCode)}" />
	<c:set var="measureValueTitle"
		value="${ LocalizationManager.getLiteral(gauge.getMeasureValue().getTitleLiteralKey(), langCode)}" />
	<c:set var="complementingMeasureValueTitle"
		value="${ LocalizationManager.getLiteral(gauge.getComplementingMeasureValue().getTitleLiteralKey(), langCode)}" />
	<fmt:formatNumber var="valueNotAchieved" value="${(max-data)}" />

	<table cellpadding="4" cellspacing="0" width="96%" border="1"
		bordercolor="rgba(0, 0, 0, .07)" class="summary">
		<tr>
			<th>${totalTitle}</th>
			<th>${measureValueTitle}</th>
			<th>${complementingMeasureValueTitle}</th>
		</tr>
		<tr>
			<td style="background-color: #322e2e;">${max}</td>
			<td style="background-color: #32CD32;">${data}</td>
			<td style="background-color: #FF0000;">${valueNotAchieved}</td>
		</tr>
	</table>
</c:if>

<script type="text/javascript">

	var gaugeOptions = {
	
	    chart: {
	        type: 'solidgauge'
	    },
	
	    title: null,
	
	    pane: {
	        center: ['50%', '85%'],
	        size: '140%',
	        startAngle: -90,
	        endAngle: 90,
	        background: {
	            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
	            innerRadius: '60%',
	            outerRadius: '100%',
	            shape: 'arc'
	        }
	    },
	
	    tooltip: {
	        enabled: false
	    },
	
	    // the value axis
	    yAxis: {
	        stops: [
	            [0.70, '#FF0000'], // red 
	            [0.80, '#FFFF00'], // yellow
	            [0.90, '#32CD32']  // green
	        ],
	        lineWidth: 0,
	        minorTickInterval: null,
	        tickAmount: 2,
	        title: {
	            y: -70
	        },
	        labels: {
	            y: 16
	        }
	    },
	
	    plotOptions: {
	        solidgauge: {
	            dataLabels: {
	                y: 5,
	                borderWidth: 0,
	                useHTML: true
	            }
	        }
	    }
	};
	
	// The speed gauge
	var chartSpeed = Highcharts.chart('${idKey}', Highcharts.merge(gaugeOptions, {
	    yAxis: {
	        min: ${min},
	        max: ${max},
	        tickInterval: 0,
	        title: {
	            text: '${name}'
	        }
	    },
	
	    credits: {
	        enabled: false
	    },
	
	    series: [{
	        name: '${name}',
	        data: [${data}],
	        dataLabels: {
	            format: '<div style="text-align:center"><span style="font-size:13px;color:' +
	                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">${actualValueSuffix}</span><br/></div>'
	        },
	        tooltip: {
	            valueSuffix: '${actualValueSuffix}'
	        }
	    }]
	
	}));
</script>