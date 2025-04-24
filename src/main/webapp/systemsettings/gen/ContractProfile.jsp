<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"   import="com.iassets.common.util.*,com.iassets.common.entity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>بيانات الشركة المشغلة والعقد</title>
<%
	String langCode = WebUtil.getSessionLanguage(request);

	CommonSite site = (CommonSite)request.getAttribute("site");
	CommonOperatingCompany operatingCompany = site.getGenOperatingCompany();
   
    String companyName  = Common.getDisplayString(operatingCompany.getLocalizedName(langCode), "", true);
    String siteManagerName = Common.getDisplayString(site.getGenSiteManagerName(langCode), "", true);
    String contractStartDate = DateUtil.getDateString(site.getGenContractStartDate());
    String contractEndDate = DateUtil.getDateString(site.getGenContractEndDate());
    String sparePartsValue = Common.formatMoneyValue(site.getGenSparePartsValue());
    
    String subcontractorExpenses = Common.formatMoneyValue(site.getGenSubcontractorExpenses());
    String electricalMechanicalConsumables = Common.formatMoneyValue(site.getGenElectricalMechanicalConsumables());
    String monthlyConsumables = Common.formatMoneyValue(site.getGenMonthlyConsumables());
    String laborExpenses = Common.formatMoneyValue(site.getGenLaborExpenses());
    String waterSupplyExpenses = Common.formatMoneyValue(site.getGenWaterSupplyExpenses());
    String washingSupplyExpenses = Common.formatMoneyValue(site.getGenWashingSupplyExpenses());
    String sweepingSewageExpenses = Common.formatMoneyValue(site.getGenSweepingSewageExpenses());
    String developementExpenses = Common.formatMoneyValue(site.getGenDevelopementExpenses());
    
    String contractTotalValue = Common.formatMoneyValue(site.getGenContractTotalValue());
    String ta3midNo = Common.getDisplayString(site.getGenTa3midNo(), "", true);
    String ta3midDate = DateUtil.getDateString(site.getGenTa3midDate());
%>
</head>
<body>
<table class="layout_grid">
  <tr>
    <td colspan="2"><h1 class="page_title">بيانات الشركة المشغلة وعقد التشغيل</h1> </td>
  </tr>
  <tr>
    <td class="side_label_middle">اسم الشركة : </td>
    <td><%=companyName%></td>
  </tr>
  <tr>
    <td class="side_label_middle">اسم مدير الموقع : </td>
    <td><%=siteManagerName%></td>
  </tr>
  <tr>
    <td class="side_label_middle">تاريخ بداية العقد : </td>
    <td><%=contractStartDate%></td>
  </tr>
  <tr>
    <td class="side_label_middle">تاريخ نهاية العقد : </td>
    <td><%=contractEndDate%></td>
  </tr>
  <tr>
    <td class="side_label_middle">قيمة قطع الغيار : </td>
    <td><%=sparePartsValue%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  
  <tr>
    <td class="side_label_middle">تكاليف مقاولي الباطن : </td>
    <td><%=subcontractorExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف المستهلكات الكهربائية والميكانيكية : </td>
    <td><%=electricalMechanicalConsumables%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف المستهلكات الشهرية : </td>
    <td><%=monthlyConsumables%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف العمالة : </td>
    <td><%=laborExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف توريد المياه : </td>
    <td><%=waterSupplyExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف توريد غسيل : </td>
    <td><%=washingSupplyExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف كسح مياه الصرف الصحي : </td>
    <td><%=sweepingSewageExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">تكاليف التطوير والتحديث : </td>
    <td><%=developementExpenses%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  
  <tr>
    <td  class="side_label_middle">القيمة الاجمالية للعقد : </td>
    <td><%=contractTotalValue%>&nbsp;&nbsp;<strong>ريال سعودي</strong></td>
  </tr>
  <tr>
    <td class="side_label_middle">رقم التعميد : </td>
    <td><%=ta3midNo%></td>
  </tr>
  <tr>
    <td class="side_label_middle">تاريخ التعميد : </td>
    <td><%=ta3midDate%></td>
  </tr>
</table>
</body>
</html>
