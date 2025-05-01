<%@ tag body-content="empty" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />

<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="literalKey" required="true" type="java.lang.String"%>

<%@ attribute name="minDate" required="false" type="java.util.Date"%>
<%@ attribute name="maxDate" required="false" type="java.util.Date"%>

<tr>
	<td class="side_label_middle"><fmt:message key="${literalKey}" />
		:</td>
	<td>
		<p>
			<img alt="reset date" id="${id}_reset" src="image/xbuttonimage.png">
			<input name="${name}" id="${id}" size="10" />
		</p>
	</td>
</tr>


<style>
.ui-datepicker-calendar {
	display: none;
}

button.ui-datepicker-current {
	display: none;
}
</style>


<script>
/* 	  	$("#${id}_reset").hide(); */
 	  
		$("#${id}_reset").click(function() {
			 $('#${ id }').val(null);
/* 			 $("#${id}_reset").hide(); */
 		});
		
	    $(function() {
		    $('#${ id }').datepicker( {
		        changeMonth: true,
		        changeYear: true,
		        showButtonPanel: true, 
		        dateFormat : 'mm/yy', 
				yearRange : "-5:+5",
				buttonImage: "image/calendaricon.png",
				buttonImageOnly: true,
		        showOn: "button",
				<c:if test="${minDate != null }">		
					minDate: new Date(${minDate.getYear() + 1900}, ${minDate.getMonth()}, ${minDate.getDate()}),
				</c:if>
		        
				<c:if test="${maxDate != null }">		
			        maxDate: new Date(${maxDate.getYear() + 1900}, ${maxDate.getMonth()}, ${maxDate.getDate()}),
				</c:if>
		        onClose: function(dateText, inst) { 
		            $(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
		            $("#${id}_reset").show();
		        }
// 				,
// 		        beforeShow: function () {  
// 		        	$(".ui-datepicker-calendar").css("display","none");
// 		        	$("button.ui-datepicker-current").css("display","none");
// 		        	$(".ui-datepicker-calendar").css({display:'none'});
// 		        	$("button.ui-datepicker-current").css({display:'none'});
// 		        }
	    	});
		    
		    $("#${id}").keydown(function(event) {
				event.preventDefault();
			});
	});
	</script>