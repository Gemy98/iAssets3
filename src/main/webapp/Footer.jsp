<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />

<div id="footer"> 
  <strong> 
    <fmt:message key="jsp.footer.copyrights" />&nbsp;
    <a href="http://www.sds-eg.com" target="_blank">Smart Digital Solutions</a>
  </strong><br>
  
  <strong>
    <fmt:message key="jsp.footer.supporttel" />: 
  </strong> 
  <span dir='${sessionScope.direction}'>00201044735305</span>
  <a href="mailto:info@sds-eg.com">info@sds-eg.com</a>
  <br>
  <strong>
    <fmt:message key="jsp.footer.supportsales" />: 
  </strong>
  <span dir='${sessionScope.direction}'>00201004417575</span>
  <a href="mailto:a.gamal@sds-eg.com">a.gamal@sds-eg.com</a>
  <br>
  <strong> 
    <fmt:message key="jsp.footer.supportemail" />: 
  </strong> 
  <span dir='${sessionScope.direction}'>
    <a href="mailto:m.zaki@sds-eg.com">m.zaki@sds-eg.com</a>
    <a href="mailto:abdullahgamal.sa@gmail.com">abdullahgamal.sa@gmail.com</a>
  </span>
</div>
