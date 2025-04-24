<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.util.Common,com.iassets.common.util.DateUtil,com.iassets.general.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%
    String sLangCode = WebUtil.getSessionLanguage(request);
     
    GenJobOrder jobOrderInfo = (GenJobOrder)request.getAttribute(Default.JOB_ORDER_INFO_ATTR_NAME);
	if(jobOrderInfo == null)
          throw new Exception("لم يتم تعيين أمر عمل لإتمام العملية عليه");
	   
	String jobOrderNo  = Common.getDisplayString(jobOrderInfo.getJobOrderNo(),"");
	String jobOrderDate  = DateUtil.getDateString(jobOrderInfo.getJobOrderDate());
	String damageDescription  = Common.getDisplayString(jobOrderInfo.getDamageDescription(),"");
	String damageDate  = DateUtil.getDateString(jobOrderInfo.getDamageDate());
	GenLookupJobOrderType jobOrderType = jobOrderInfo.getJobOrderType();
	GenLookupJobOrderPriority jobOrderPriority = jobOrderInfo.getJobOrderPriority();
	String responsibleDepName = jobOrderInfo.getResponsibleDepartmentName(sLangCode);
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td  class="side_label_middle">رقم امر العمل : </td>
    <td><label id="jobOrderNo"></label></td>
  </tr>
  <%if(responsibleDepName != null){ %>
  <tr>
    <td  class="side_label_middle">القسم المختص : </td>
    <td><label id="responsibleDepName"><%=Common.getDisplayString(responsibleDepName, "", true)%></label></td>  
  </tr>
  <%}%>
  <%if(jobOrderType != null){ %>
  <tr>
    <td  class="side_label_middle">نوع امر العمل : </td>
    <td><label id="jobOrderType"><%=Common.getDisplayString(jobOrderType.getLocalizedName(sLangCode), "", true)%></label></td>  
  </tr>
  <%}%>
  <%if(jobOrderPriority != null){ %>
  <tr>
    <td  class="side_label_middle">أولوية امر العمل : </td>
    <td><label id="jobOrderPriority"><%=Common.getDisplayString(jobOrderPriority.getLocalizedName(sLangCode), "", true)%></label></td>  
  </tr>
  <%}%>
  <tr>
    <td  class="side_label_middle">تاريخ امر العمل : </td>
    <td><label id="jobOrderDate"></label>
        <input type="text" id="joborder_date" name="joborder_date" class="caldr" value="<%=jobOrderDate%>" style="display:none">
    </td>  
  </tr>
  <tr>
    <td  class="side_label_top">وصف العطل : </td>
    <td><label id="damageDescription"></label></td>
  </tr>
  <tr>
    <td  class="side_label_middle">تاريخ العطل : </td>
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
