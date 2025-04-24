<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.biomedical.util.HtmlUtil,com.iassets.common.util.*,com.iassets.biomedical.entity.*,java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><html  dir="${sessionScope.direction}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    </head>
    <body> 
    <form action="bio/DeviceScrappingProcess" method="post" enctype="multipart/form-data">
      <table  class="layout_grid">
        <tr>
          <td colspan="2"><h1 class="page_title"> <fmt:message key="jsp.DeviceScrapping.pagetitle" /></h1></td>
        </tr>
        <tr>
          <td colspan="2"><%@ include file="../DeviceOverview.jsp" %> </td>
        </tr>

        <tr>
          <td  class="side_label_top"> <fmt:message key="jsp.DeviceScrapping.deviceCondition" /> : </td>
          <td><textarea name="deviceCondition" id="deviceCondition" required></textarea></td>
        </tr>
        <tr>
          <td  class="side_label_middle"> <fmt:message key="jsp.DeviceScrapping.scrappingReason" />  : </td>
          <td>    
          <select id="scrappingReason" name="scrappingReason" required>
        <%
        	List<BioLookupScrappingReason> reasonsList = (List<BioLookupScrappingReason>)request.getAttribute("reasons");
                if (reasonsList != null)
                    for (BioLookupScrappingReason obj : reasonsList){
        %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
        <%}%>
        </select>
        </td>
        </tr>
        <tr>
          <td  class="side_label_top"><fmt:message key="jsp.DeviceScrapping.scrappingReasonReportUrl" />   : </td>
          <td><input type="file" name="scrappingReasonReportUrl" id="scrappingReasonReportUrl" multiple <%=(deviceInfo.getCategory().getId() != Enums.DEVICE_CATEGORY.C.getDBId())?"class='requiredFile'":"" %>></td>
        </tr>
        <tr>
          <td  class="side_label_top"><fmt:message key="jsp.DeviceScrapping.scrappingFinalReportUrl" />  : </td>
          <td><input type="file" name="scrappingFinalReportUrl" id="scrappingFinalReportUrl" multiple class="requiredFile"></td>
        </tr>
        <tr>
          <td  class="side_label_middle"> <fmt:message key="jsp.DeviceScrapping.scrappingDate" />  : </td>
          <td><input type="text" name="scrappingDate" id="scrappingDate"  class="caldr" required></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" name="button" id="button" value='<fmt:message key="jsp.DeviceScrapping.submitbutton" /> '><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
        </tr>
      </table>
    </form>
    
    <%
        	BioHospitalDeviceScrappingInfo scrapInfo = (BioHospitalDeviceScrappingInfo) request.getAttribute("deviceScrappingInfo");
            
            if (scrapInfo != null){
                String  deviceScrappingId = "" + scrapInfo.getId();
                String  deviceCondition  = Common.getDisplayString(scrapInfo.getDeviceCondition() , ""); 
                
                String  scappingReason  = "";
                if(scrapInfo.getScrappingReason() != null)
                   scappingReason = "" + scrapInfo.getScrappingReason().getId();
                   
                String scrappingDate  = DateUtil.getDateString(scrapInfo.getScrappingDate());   
                
                String scrappingReasonFiles = scrapInfo.getScrappingReasonReportUrl();
                String scrappingFinalFiles = scrapInfo.getScrappingFinalReportUrl();
        %>
   <script>
   $(function(){
	   appendIdElement("deviceScrappingId","<%=deviceScrappingId%>");
	   setInnerHTML("deviceCondition","<%=deviceCondition%>");
	   setComboSelectedValue("scrappingReason","<%=scappingReason%>");
	   setDateValue("scrappingDate","<%=scrappingDate%>");	   
	   showUploadedFilesList("scrappingReasonReportUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(scrappingReasonFiles)%>);
	   showUploadedFilesList("scrappingFinalReportUrl", <%=HtmlUtil.arrayFromJavaToJavaScript(scrappingFinalFiles)%>);
   });
   </script>
   
   <%}%>
</body>
</html>