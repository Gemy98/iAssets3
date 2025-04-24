<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.entity.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title> <fmt:message key="jsp.ContractProfile.pagetitle" /></title>
<%
    String langCode = WebUtil.getSessionLanguage(request);

	CommonSite site = (CommonSite)request.getAttribute("site");
	CommonOperatingCompany operatingCompany = site.getBioOperatingCompany();
	
     
    String companyName  = Common.getDisplayString(operatingCompany.getLocalizedName(langCode), "", true);
    String siteManagerName = Common.getDisplayString(site.getBioSiteManagerName(langCode), "", true);
    String contractStartDate = DateUtil.getDateString(site.getBioContractStartDate());
    String contractEndDate = DateUtil.getDateString(site.getBioContractEndDate());
    String sparePartsValue = Common.formatMoneyValue(site.getBioSparePartsValue());
    String contractTotalValue = Common.formatMoneyValue(site.getBioContractTotalValue());
    String ta3midNo = Common.getDisplayString(site.getBioTa3midNo(), "", true);
    String ta3midDate = DateUtil.getDateString(site.getBioTa3midDate());
    // String takeoverReport = site.getBioTakeoverReportScan();
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td colspan="2"><h1 class="page_title"><fmt:message key="jsp.ContractProfile.pagetitle" /></h1> </td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.companyName" />: </td>
    <td><%=companyName%></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.siteManagerName" />: </td>
    <td><%=siteManagerName%></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.contractStartDate" />: </td>
    <td><%=contractStartDate%></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.contractEndDate" />: </td>
    <td><%=contractEndDate%></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.sparePartsValue" />: </td>
    <td><%=sparePartsValue%>&nbsp;&nbsp;<strong><fmt:message key="jsp.ContractProfile.currency" /></strong></td>
  </tr>
  <tr>
    <td  class="side_label_middle"><fmt:message key="jsp.ContractProfile.contractTotalValue" />: </td>
    <td><%=contractTotalValue%>&nbsp;&nbsp;<strong><fmt:message key="jsp.ContractProfile.currency" /></strong></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.ta3midNo" />: </td>
    <td><%=ta3midNo%></td>
  </tr>
  <tr>
    <td class="side_label_middle"><fmt:message key="jsp.ContractProfile.ta3midDate" />: </td>
    <td><%=ta3midDate%></td>
  </tr>
  <tr>
    <td class="side_label_top"><fmt:message key="jsp.ContractProfile.bioTakeoverReport" />: </td>
    <td><%= CommonHtmlUtil.showUploadedFilesInViewMode(site.getBioTakeoverReportScan(), false, langCode)%></td>
  </tr>
</table>
</body>
</html>
