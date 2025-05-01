<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />

<div id="footer">
	<strong> <fmt:message key="jsp.footer.copyrights" />&nbsp;<a
		href="http://www.sds-eg.com" target="_blank">Smart Digital
			Solutions</a></strong><br> <strong><fmt:message
			key="jsp.footer.supporttel" />: </strong> <span
		dir='${sessionScope.direction}'>00966535117694 - 00201004375876</span><strong>
		<fmt:message key="jsp.footer.supportemail" />:
	</strong> <span dir='${sessionScope.direction}'><a
		href="mailto:support@sds-eg.com">support@sds-eg.com</a></span>
</div>
