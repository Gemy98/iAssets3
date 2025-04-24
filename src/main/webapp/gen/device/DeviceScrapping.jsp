<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"  import="com.iassets.general.util.HtmlUtil,com.iassets.common.util.*,com.iassets.general.entity.*,java.util.*"%>
<html dir="rtl">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    </head>
    <body> 
    <form action="gen/DeviceScrappingProcess" method="post" enctype="multipart/form-data">
      <table  class="layout_grid">
        <tr>
          <td colspan="2"><h1 class="page_title">شاشة تكهين جهاز</h1></td>
        </tr>
        <tr>
          <td colspan="2"><%@ include file="../DeviceOverview.jsp" %> </td>
        </tr>

        <tr>
          <td  class="side_label_top">حالة الجهاز : </td>
          <td><textarea name="deviceCondition" id="deviceCondition" required></textarea></td>
        </tr>
        <tr>
          <td  class="side_label_middle">اسباب التكهين : </td>
          <td>    
          <select id="scrappingReason" name="scrappingReason" required>
        <%
        	List<GenLookupScrappingReason> reasonsList = (List<GenLookupScrappingReason>)request.getAttribute("reasons");
                if (reasonsList != null)
                    for (GenLookupScrappingReason obj : reasonsList){
        %>
            <option value="<%=obj.getId()%>"><%=obj.getLocalizedName()%></option>
        <%}%>
        </select>
        </td>
        </tr>
        <tr>
          <td  class="side_label_top"> تقرير سبب التكهين  : </td>
          <td><input type="file" name="scrappingReasonReportUrl" id="scrappingReasonReportUrl" multiple class='requiredFile'></td>
        </tr>
        <tr>
          <td  class="side_label_top"> التقرير النهائي   : </td>
          <td><input type="file" name="scrappingFinalReportUrl" id="scrappingFinalReportUrl" multiple class="requiredFile"></td>
        </tr>
        <tr>
          <td  class="side_label_middle">تاريخ التكهين : </td>
          <td><input type="text" name="scrappingDate" id="scrappingDate"  class="caldr" required></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" name="button" id="button" value="اتمام العملية"><%=HtmlUtil.getResetButtonHTML(langCode)%></td>
        </tr>
      </table>
    </form>
    
    <%
    	GenHospitalDeviceScrappingInfo scrapInfo = (GenHospitalDeviceScrappingInfo) request.getAttribute("deviceScrappingInfo");
    
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