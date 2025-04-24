<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="i18n.jsp_literals" /><%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.entity.CommonUser"%>
<% 
   boolean currentUserHasDepHeadRole = request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName());
   boolean currentUserIsEndUser = request.isUserInRole(Enums.SYS_ROLES.BIO_AND_GEN_END_USER.getRoleName());
   boolean currentUserIsInventoryKeeper = request.isUserInRole(Enums.SYS_ROLES.BIO_INVENTORY_KEEPER.getRoleName());  
   boolean currentUserCanViewReportsOnly = WebUtil.currentUserHasViewPrivilegesOnly(request);
   boolean currentUserPreventedFromSomeReportsAccess = currentUserCanViewReportsOnly;
   // boolean userHasRightToChangeHospital = WebUtil.userHasRightToChangeSessionHospital(request);
   CommonUser currentSessionUser = WebUtil.getSessionUser(request);
   String menuLnkTarget = (currentSessionUser.getCurrentSite().getContainsSeveralLocations()) ? "" : "target='_blank' rel='opener'";
%>

<div id="dhtmlgoodies_slidedown_menu">
  <ul>
  
   	<li><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SiteInfo" /></a>
      <ul>
        <li><a href="bio/HospitalProfileDisplay"> <fmt:message key="jsp.Menu.HospitalProfileDisplay" /></a></li>
        <li><a href="bio/UserProfileDisplay" id="home_lnk"> <fmt:message key="jsp.Menu.UserProfileDisplay" /> </a></li>
        <li><a href="bio/HospitalContractProfileDisplay"> <fmt:message key="jsp.Menu.HospitalContractProfileDisplay" /></a></li>
        <!--  <li><a href="bio/ChangePasswordDisplay">تغيير كلمة المرور</a></li> -->
      </ul>
    </li>
    
   <%
       	if(request.isUserInRole(Enums.SYS_ROLES.BIO_ONSITE_ADMINISTRATION.getRoleName()) ||
              		   request.isUserInRole(Enums.SYS_ROLES.BIO_CONTRACTOR_EVALUATION.getRoleName()) ){
       %>
    <li><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SiteAdmin" /> </a>
       <ul>
         <%
         	if(request.isUserInRole(Enums.SYS_ROLES.BIO_CONTRACTOR_EVALUATION.getRoleName())){
         %>
	     <li><a href="bio/DailyContractorEvaluationDisplay">  <fmt:message key="jsp.Menu.DailyContractEvluation" /></a></li>
	     <li><a href="bio/MonthlyContractorEvaluationDisplay">  <fmt:message key="jsp.Menu.MonthlyContractorEvaluation" /></a></li>
	     <%}%>
	     <%if(request.isUserInRole(Enums.SYS_ROLES.BIO_ONSITE_ADMINISTRATION.getRoleName())){%>
	     <li><a href="bio/ManageContractInfoDisplay">  <fmt:message key="jsp.Menu.ManageContractInfoDisplay" /></a></li>
	     <li><a href="bio/OperatingCompanyLaborerInfoDisplay">  <fmt:message key="jsp.Menu.OperatingCompanyLaborerInfoDisplay" /></a></li>
	     <li><a href="bio/ProgressBillInfoDisplay">  <fmt:message key="jsp.Menu.ProgressBillInfoDisplay" /> </a></li>
	     <li><a href="bio/UpdateLastPPMProgressBill">  <fmt:message key="jsp.Menu.UpdateLastPPMProgressBill" /></a></li>
	     <li><a href="bio/UpdateLastSparePartsProgressBill">  <fmt:message key="jsp.Menu.UpdateLastSparePartsProgressBill" /> </a></li>
	   <%}%>
       </ul>
    </li>
   <%}%>
   
   <%if(currentUserIsEndUser){%>
       <li><a href="javascript:void(0)"><fmt:message key="jsp.Menu.Maintenance" /> </a>
	      <ul>
		    <li><a href="bio/OpenMaintenanceRequestSearch"> <fmt:message key="jsp.Menu.OpenMaintenanceRequestSearch" /> </a></li>
		    <li class="hidden"><a href="bio/OpenMaintenanceRequest"><fmt:message key="jsp.Menu.OpenMaintenanceRequest" /></a></li>
		    <li><a target="_blank" rel='opener' href="bio/ViewMaintenanceRequests"><fmt:message key="jsp.Menu.ViewMaintenanceRequests" /> </a></li>
		    <li><a target="_blank" rel='opener' href="bio/ViewDevicesByCatnDep"> <fmt:message key="jsp.Menu.ViewDevicesByCatnDep" /> </a></li>
	      </ul>
	    </li>
   <%} else if (currentUserIsInventoryKeeper){%>
   	    <li><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SP" /></a>
	      <ul>
	        <li><a href="bio/AddNewSPCategoryDisplay">  <fmt:message key="jsp.Menu.AddNewSPCategoryDisplay" /> </a></li>
	        <li><a href="bio/UpdateSPCategorySearch"> <fmt:message key="jsp.Menu.UpdateSPCategorySearch" /> </a></li>
	        <li class="hidden"><a href="bio/UpdateSPCategory">  <fmt:message key="jsp.Menu.UpdateSPCategory" /> </a></li>
	        <li><a target="_blank" rel='opener' href="bio/ViewAllSPInventoryCategories">  <fmt:message key="jsp.Menu.ViewAllSPInventoryCategories" /> </a></li>
			<li><a target="_blank" rel='opener' href="bio/ViewUnderThresholdSPInventoryCategories">  <fmt:message key="jsp.Menu.ViewAllSPInventoryCategories" /> </a></li>
			<li><a href="bio/ViewSpecificSPInventoryTransactionsReportSearch">  <fmt:message key="jsp.Menu.ViewSpecificSPInventoryTransactionsReportSearch" /> </a></li>
	      </ul>
	    </li>
   <%} else if(request.isUserInRole(Enums.SYS_ROLES.BIO_REGIONAL_DIRECTOR.getRoleName())){%>    
	    <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SiteAdminReports" /></a>
	      <ul>
	        <li><a target="_blank" rel='opener' href="bio/ViewOperatingCompanyOnsiteLaborers"> <fmt:message key="jsp.Menu.SiteAdminReports.ViewOperatingCompanyOnsiteLaborers" /></a></li>
	        <li><a target="_blank" rel='opener' href="bio/ViewPPMProgressBillHistory"> <fmt:message key="jsp.Menu.SiteAdminReports.ViewPPMProgressBillHistory" />  </a></li>
	        <li><a target="_blank" rel='opener' href="bio/ViewSparePartsProgressBillHistory"> <fmt:message key="jsp.Menu.SiteAdminReports.ViewSparePartsProgressBillHistory" /> </a></li>
	      </ul>
        </li>
        
        <li class="reports_menu_items"><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.JobOrderReports" />  </a>
	      <ul>
	        <li><a href="bio/ViewJobOrderReportSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewJobOrderReportSearch" />  </a></li>
	        <li><a href="bio/ViewOpenedJobOrdersSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewOpenedJobOrdersSearch" />  </a></li>
	        <li><a href="bio/ViewClosedJobOrdersSearch"> <fmt:message key="jsp.Menu.JobOrderReports.ViewClosedJobOrdersSearch" /> </a></li>
            <li><a href="bio/ViewLateJobOrdersSearch"> <fmt:message key="jsp.Menu.JobOrderReports.ViewLateJobOrdersSearch" /> </a></li>
<%--             <li><a href="bio/ViewCancelledJobOrdersSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewCancelledJobOrdersSearch" />  </a></li>
 --%>	      </ul>
        </li> 
        
        <li class="reports_menu_items"><a href="javascript:void(0)"><fmt:message key="jsp.Menu.PPMReports" />  </a>
	      <ul>
	        <li><a href="bio/PPMVisitsTableFilterDisplay"><fmt:message key="jsp.Menu.PPMReports.PPMVisitsTableFilterDisplay" /> </a></li>
	        <li><a href="bio/DevicesNeedPPMVisitFilterDisplay"><fmt:message key="jsp.Menu.PPMReports.DevicesNeedPPMVisitFilterDisplay" /> </a></li>
	        <li><a href="bio/PPMVisitsDetailsFilterDisplay"><fmt:message key="jsp.Menu.PPMReports.PPMVisitsDetailsFilterDisplay" /> </a></li>
	        <li><a <%=menuLnkTarget%> href="bio/ViewPPMNotHappenedVisitsAlias"> <fmt:message key="jsp.Menu.PPMReports.ViewPPMNotHappenedVisitsAlias" />  </a></li>
	      </ul>
	    </li>
   <%} else {
    if(!currentUserCanViewReportsOnly){%>
		  <li><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.DeviceAdmin" /></a>
		    <ul>
		      <li><a href="bio/DeviceInfoDisplay">  <fmt:message key="jsp.Menu.DeviceInfoDisplay" /></a></li>
		      <li><a href="bio/UpdateDeviceInfoSearch">  <fmt:message key="jsp.Menu.UpdateDeviceInfoSearch" /></a></li>
		      <li class="hidden"><a href="bio/UpdateDeviceInfo">  <fmt:message key="jsp.Menu.UpdateDeviceInfo" /> </a></li>
	          <% if(currentUserHasDepHeadRole){ %>
		        <li><a href="bio/TransferDeviceSearch">  <fmt:message key="jsp.Menu.TransferDeviceSearch" /> </a></li>
		        <li class="hidden"><a href="bio/TransferDevice">  <fmt:message key="jsp.Menu.TransferDevice" /> </a></li>
		        <li><a href="bio/ScrappDeviceSearch">  <fmt:message key="jsp.Menu.ScrappDeviceSearch" /> </a></li>
		        <li class="hidden"><a href="bio/ScrappDevice">  <fmt:message key="jsp.Menu.ScrappDevice" /> </a></li>
	          <%}%>
		      <li><a href="bio/PpmFollowupSearch">  <fmt:message key="jsp.Menu.PpmFollowupSearch" /></a></li>
		      <li class="hidden"><a href="bio/PpmFollowup">  <fmt:message key="jsp.Menu.PpmFollowup" /> </a></li>
		    </ul>
		    </li>
		  <li><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.JobOrderAdmin" />  </a>
		    <ul>
		      <li><a href="bio/OpenJobOrderSearch"> <fmt:message key="jsp.Menu.OpenJobOrderSearch" />  </a></li>
		      <li class="hidden"><a href="bio/OpenJobOrder"> <fmt:message key="jsp.Menu.OpenJobOrder" />  </a></li>
<%-- 		      <% if(currentUserHasDepHeadRole){ %>
			     <li><a href="bio/CancelJobOrderSearch"> <fmt:message key="jsp.Menu.CancelJobOrderSearch" />  </a></li>
			     <li class="hidden"><a href="bio/CancelJobOrder"> <fmt:message key="jsp.Menu.CancelJobOrder" />  </a></li>
		      <%}%> --%>
		      <li><a href="bio/FollowupJobOrderSearch"> <fmt:message key="jsp.Menu.FollowupJobOrderSearch" />  </a></li>
		      <li class="hidden"><a href="bio/FollowupJobOrder"> <fmt:message key="jsp.Menu.FollowupJobOrder" />  </a></li>
		    </ul>
		  </li>
	      <li><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.SPAdmin" /> </a>
		    <ul>
		      <li><a href="bio/AddNewSPCategoryDisplay"> <fmt:message key="jsp.Menu.AddNewSPCategoryDisplay" /> </a></li>
		      <li><a href="bio/UpdateSPCategorySearch"> <fmt:message key="jsp.Menu.UpdateSPCategorySearch" /></a></li>
		      <li class="hidden"><a href="bio/UpdateSPCategory"> <fmt:message key="jsp.Menu.UpdateSPCategory" /> </a></li>
		    </ul>
	      </li>
    <%}%>
    
    <%if(WebUtil.currentUserHasPrivilegeForSiteAdministration(request)){ %>
	    <li><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.EmployeeAdmin" />  </a>
	      <ul>
<%-- 	        <li><a href="bio/ResetUserPasswordDisplay">  <fmt:message key="jsp.Menu.ResetUserPasswordDisplay" /> </a></li>
 --%>	        <li><a href="bio/UpdateEmployeeInfoDisplay"> <fmt:message key="jsp.Menu.UpdateEmployeeInfoDisplay" /> </a></li>
	      </ul>
	    </li>
    <%}%>
    

        <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SiteAdminReports" />  </a>
	      <ul>
	        <li><a target="_blank" rel='opener' href="bio/ViewOperatingCompanyOnsiteLaborers"> <fmt:message key="jsp.Menu.SiteAdminReports.ViewOperatingCompanyOnsiteLaborers" /> </a></li>
	        <li><a target="_blank" rel='opener' href="bio/ViewPPMProgressBillHistory">  <fmt:message key="jsp.Menu.SiteAdminReports.ViewPPMProgressBillHistory" /> </a></li>
	        <li><a target="_blank" rel='opener' href="bio/ViewSparePartsProgressBillHistory">  <fmt:message key="jsp.Menu.SiteAdminReports.ViewSparePartsProgressBillHistory" /> </a></li>
	      </ul>
        </li>
        
	    <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.DeviceAdminReports" />  </a>
	      <ul>
	        <li><a href="bio/ViewDevicesByCatnDepSearch">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesByCatnDepSearch" />  </a></li>
	        <li><a href="bio/DevicesUnderMaintenanceSearch"> <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesUnderMaintenanceAlias" /> </a></li>
	        <li><a href="bio/DevicesByWarrantyExpireFilterDisplay">  <fmt:message key="jsp.Menu.DeviceAdminReports.DevicesByWarrantyExpireFilterDisplay" />  </a></li>
	        <li><a href="bio/ViewDevicesNotInContractSearch">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesNotInContractAlias" />  </a></li>
	        <li><a href="bio/ViewDevicesFromOtherSitesSearch">  <fmt:message key="jsp.Menu.DeviceAdminReportsViewDevicesFromOtherSitesAlias" />  </a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesTransferredAlias">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesTransferredAlias" />  </a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesScrappedAlias">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesScrappedAlias" />  </a></li>
	        <li><a href="bio/ViewDevicesStatusSearch">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDevicesStatusSearch" />  </a></li>
	        <li><a href="bio/ViewDeviceCardSearch">  <fmt:message key="jsp.Menu.DeviceAdminReports.ViewDeviceCardSearch" />  </a></li>
	        <li><a href="bio/DeviceHistoryFilterDisplay">  <fmt:message key="jsp.Menu.DeviceAdminReports.DeviceHistoryFilterDisplay" /> </a></li>
	      </ul>
	    </li>
	    
	    <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.PPMReports" />  </a>
	      <ul>
	        <li><a href="bio/PPMVisitsTableFilterDisplay"> <fmt:message key="jsp.Menu.PPMReports.PPMVisitsTableFilterDisplay" />  </a></li>
	        <li><a href="bio/DevicesNeedPPMVisitFilterDisplay"> <fmt:message key="jsp.Menu.PPMReports.DevicesNeedPPMVisitFilterDisplay" /> </a></li>
	        <li><a href="bio/PPMVisitsDetailsFilterDisplay"> <fmt:message key="jsp.Menu.PPMReports.PPMVisitsDetailsFilterDisplay" />  </a></li>
	        <li><a <%=menuLnkTarget%> href="bio/ViewPPMNotHappenedVisitsAlias">  <fmt:message key="jsp.Menu.PPMReports.ViewPPMNotHappenedVisitsAlias" />  </a></li>
	        <li><a href="bio/PPMNotRecordedVisitsFilterDisplay">  <fmt:message key="jsp.Menu.PPMReports.ViewNotRecordedVisitsFilterDisplay" />  </a></li>
	      </ul>
	    </li>
	    
	    <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.JobOrderReports" />  </a>
	      <ul>
	        <%if(currentSessionUser.getUserType() == Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()){%>
	            <li><a target="_blank" rel='opener' href="bio/ViewEmptyStandardJobOrderReport">  <fmt:message key="jsp.Menu.JobOrderReports.ViewEmptyStandardJobOrderReport" />  </a></li>
	        <%}%>
	        <li><a href="bio/ViewJobOrderReportSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewJobOrderReportSearch" />  </a></li>
	        <li><a href="bio/ViewMaintenanceRequestsSearch"> <fmt:message key="jsp.Menu.JobOrderReports.ViewMaintenanceRequestsSearch" />  </a></li>
	        <li><a href="bio/ViewOpenedJobOrdersSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewOpenedJobOrdersSearch" />  </a></li>
	        <li><a href="bio/ViewClosedJobOrdersSearch"> <fmt:message key="jsp.Menu.JobOrderReports.ViewClosedJobOrdersSearch" /> </a></li>
	        <%if(! currentUserPreventedFromSomeReportsAccess){ %>
	            <li><a href="bio/ViewLateJobOrdersSearch"> <fmt:message key="jsp.Menu.JobOrderReports.ViewLateJobOrdersSearch" /> </a></li>
<%-- 	            <li><a href="bio/ViewCancelledJobOrdersSearch">  <fmt:message key="jsp.Menu.JobOrderReports.ViewCancelledJobOrdersSearch" />  </a></li>
 --%>	        <%}%>
	      </ul>
        </li> 
        
	    <li class="reports_menu_items"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.SPReports" />  </a>
	      <ul>
	        <li><a target="_blank" rel='opener' href="bio/ViewAllSPInventoryCategories">  <fmt:message key="jsp.Menu.SPReports.ViewAllSPInventoryCategories" />  </a></li>
	        <% if(! currentUserPreventedFromSomeReportsAccess){ %>
	        <li><a target="_blank" rel='opener' href="bio/ViewUnderThresholdSPInventoryCategories">  <fmt:message key="jsp.Menu.SPReports.ViewUnderThresholdSPInventoryCategories" /> </a></li>
	        <%}%>
	        <li><a href="bio/ViewSpecificSPInventoryTransactionsReportSearch"> <fmt:message key="jsp.Menu.SPReports.ViewSpecificSPInventoryTransactionsReportSearch" /> </a></li>
	      </ul>
	    </li>
	    
	    <%-- 
	    <li class="reports_menu_items"><a href="javascript:void(0)"> <fmt:message key="jsp.Menu.AdminTools" />  </a>
			<ul>
				<li><a href="bio/ManageDirectorateMaintenanceHeadDisplay"> <fmt:message key="jsp.Menu.ManageDirectorateMaintenanceHeadDisplay" />  </a></li>
				<li><a href="bio/ManageDirectorateBioMaintenanceHeadDisplay">  <fmt:message key="jsp.Menu.ManageDirectorateBioMaintenanceHeadDisplay" />  </a></li>
				<li><a href="bio/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay"> <fmt:message key="jsp.Menu.ManageDirectorateMaintenanceDepartmentSupervisorsDisplay" /> </a></li>
				 
				<li><a href="bio/ManageHospitalDiractorDisplay">  <fmt:message key="jsp.Menu.ManageHospitalDiractorDisplay" />  </a></li>
				<li><a href="bio/ManageHospitalDepartmentHeadDisplay">  <fmt:message key="jsp.Menu.ManageHospitalDepartmentHeadDisplay" />  </a></li> <!--  check this please -->
				
				<li><a href="bio/ManageDepHeadDisplay"> <fmt:message key="jsp.Menu.ManageDepHeadDisplay" />  </a></li>
				<li><a href="bio/ManageMaintenanceDepartmentSupervisorsDisplay">  <fmt:message key="jsp.Menu.ManageMaintenanceDepartmentSupervisorsDisplay" />  </a></li>
				<li><a href="bio/ManageSiteManagerDisplay"> <fmt:message key="jsp.Menu.ManageSiteManagerDisplay" /> </a></li> 
				<li><a href="bio/ManageCompanyLabourDisplay">  <fmt:message key="jsp.Menu.ManageCompanyLabourDisplay" />  </a></li>
				
				<li><a href="bio/ManageHospitalDepartmentDisplay">  <fmt:message key="jsp.Menu.ManageHospitalDepartmentDisplay" /> </a></li> <!--  check this please -->
				<li><a href="bio/ManageHospitalBuildingDisplay">  <fmt:message key="jsp.Menu.ManageHospitalBuildingDisplay" />  </a></li> <!--  check this please -->
				<li><a href="bio/ChangeUserNameDisplay">  <fmt:message key="jsp.Menu.ChangeUserNameDisplay" />  </a></li>
			</ul>
		</li> 
		--%>

<%--   <li class="no_append"><a href="javascript:void(0)">  <fmt:message key="jsp.Menu.Info" />  </a>
	      <ul>
	        <li><a href="javascript:viewFile('sys/directory.xls')"> <fmt:message key="jsp.Menu.Directory" />  </a></li>
	      </ul>
	   </li>  --%>
<%}%>


<!--
   <li class="no_append"><a href="javascript:void(0)">ثقف نفسك</a>
      <ul>
        <li><a href="javascript:viewFile('\\sys\\ref1.pdf')">دور الصيانة في أداء المستشفيات</a></li>
        <li><a href="javascript:viewFile('\\sys\\ref2.pdf')">دليل إجراءات أعمال الصيانة</a></li>
      </ul>
   </li>  
-->
  </ul>
</div>
