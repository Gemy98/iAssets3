<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.biomedical.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
    String languageCode = WebUtil.getSessionLanguage(request);
    
    BioJobOrder jobOrderInfo = (BioJobOrder)request.getAttribute(Default.JOB_ORDER_INFO_ATTR_NAME);
	if(jobOrderInfo == null)
          throw new Exception( LocalizationManager.getLiteral("jsp.JobOrderOverview.exception", languageCode));
	   
	String jobOrderNo  = Common.getDisplayString(jobOrderInfo.getJobOrderNo(),"");
	String jobOrderDate  = DateUtil.getDateString(jobOrderInfo.getJobOrderDate());
	String damageDescription  = Common.getDisplayString(jobOrderInfo.getDamageDescription(),"");
	String damageDate  = DateUtil.getDateString(jobOrderInfo.getDamageDate());
	BioLookupJobOrderType jobOrderType = jobOrderInfo.getJobOrderType();
	BioLookupJobOrderPriority jobOrderPriority = jobOrderInfo.getJobOrderPriority();
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td  class="side_label_middle"> <fmt:message key="jsp.JobOrderOverview.jobOrderNo" />: </td>
    <td><label id="jobOrderNo"></label></td>
  </tr>
  <%if(jobOrderType != null){ %>
  <tr>
    <td  class="side_label_middle"> <fmt:message key="jsp.JobOrderOverview.jobOrderType" /> : </td>
    <td><label id="jobOrderType"><%=Common.getDisplayString(jobOrderType.getLocalizedName(languageCode), "", true)%></label></td>  
  </tr>
  <%}%>
  <%if(jobOrderPriority != null){ %>
  <tr>
    <td  class="side_label_middle"><fmt:message key="jsp.JobOrderOverview.jobOrderPriority" />: </td>
    <td><label id="jobOrderPriority"><%=Common.getDisplayString(jobOrderPriority.getLocalizedName(languageCode), "", true)%></label></td>  
  </tr>
  <%}%>
  <tr>
    <td  class="side_label_middle"><fmt:message key="jsp.JobOrderOverview.jobOrderDate" />: </td>
    <td><label id="jobOrderDate"></label>
        <input type="text" id="joborder_date" name="joborder_date" class="caldr" value="<%=jobOrderDate%>" style="display:none">
    </td>  
  </tr>
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.JobOrderOverview.damageDescription" /> : </td>
    <td><label id="damageDescription"></label></td>
  </tr>
  <tr>
    <td  class="side_label_middle"> <fmt:message key="jsp.JobOrderOverview.damageDate" /> : </td>
    <td><label id="damageDate"></label></td>
  </tr>
</table>
<script>
$(function(){
appendIdElement("jobOrderId","<%=jobOrderInfo.getId()%>");
appendIdElement("jobOrderNo","<%=jobOrderNo%>");
setInnerHTML("jobOrderNo","<%=jobOrderNo%>");
<%-- 
setInnerHTML("jobOrderType",'<%=Common.getDisplayString(jobOrderType.getName(), "")%>');
setInnerHTML("jobOrderPriority",'<%=Common.getDisplayString(jobOrderPriority.getName(), "")%>'); 
--%>
setInnerHTML("jobOrderDate","<%=jobOrderDate%>");
setInnerHTML("damageDescription","<%=damageDescription%>");
setInnerHTML("damageDate","<%=damageDate%>");
});
</script>
</body>


</html>
