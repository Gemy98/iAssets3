<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.entity.CommonUser"%>
<%
	boolean currentUserHasDepHeadRole = request.isUserInRole(Enums.SYS_ROLES.GEN_SITE_MANAGER.getRoleName());
	boolean currentUserIsEndUser = request.isUserInRole(Enums.SYS_ROLES.BIO_AND_GEN_END_USER.getRoleName());
	boolean currentUserIsInventoryKeeper = request.isUserInRole(Enums.SYS_ROLES.GEN_INVENTORY_KEEPER.getRoleName());
	boolean currentUserHasRightToEvaluatePerformance = request.isUserInRole(Enums.SYS_ROLES.GEN_PERFORMANCE_EVALUATOR.getRoleName());
	boolean currentUserCanViewReportsOnly = WebUtil.currentUserHasViewPrivilegesOnly(request);
	boolean currentUserPreventedFromSomeReportsAccess = currentUserCanViewReportsOnly;
	// boolean userHasRightToChangeHospital = WebUtil.userHasRightToChangeSessionHospital(request);
	CommonUser currentSessionUser = WebUtil.getSessionUser(request);
	String menuLnkTarget = (currentSessionUser.getCurrentSite().getContainsSeveralLocations()) ? "" : "target='_blank'";
%>

<div id="dhtmlgoodies_slidedown_menu">
  <ul>
    <li><a href="javascript:void(0)">بيانات الموقع</a>
      <ul>
        <li><a href="gen/HospitalProfileDisplay">عرض بيانات الموقع</a></li>
        <li><a href="gen/UserProfileDisplay" id="home_lnk">عرض بيانات المستخدم</a></li>
        <li><a href="gen/HospitalContractProfileDisplay">عرض بيانات الشركة المشغلة والعقد</a></li>
        <!--  <li><a href="gen/ChangePasswordDisplay">تغيير كلمة المرور</a></li> -->
      </ul>
    </li>
    
   <%if(currentUserIsEndUser){%>
       <li><a href="javascript:void(0)">إدارة طلبات الصيانة</a>
	      <ul>
		    <li><a href="gen/OpenMaintenanceRequestSearch">اضافة طلب صيانة</a></li>
		    <li class="hidden"><a href="gen/OpenMaintenanceRequest">dummy</a></li>
		    <li><a target="_blank" href="gen/ViewMaintenanceRequests">بلاغات لم يتم التعامل معها</a></li>
		    <li><a target="_blank" href="gen/ViewDevicesByCatnDep">بيان حصر الأجهزة</a></li>
	      </ul>
	    </li>
   <%} else if (currentUserIsInventoryKeeper){%>
   	    <li><a href="javascript:void(0)">إدارة مستودع قطع الغيار</a>
	      <ul>
	        <li><a href="gen/AddNewSPCategoryDisplay">إضافة صنف جديد</a></li>
	        <li><a href="gen/UpdateSPCategorySearch">تعديل بيانات صنف</a></li>
	        <li class="hidden"><a href="gen/UpdateSPCategory">dummy</a></li>
	        <li><a target="_blank"  href="gen/ViewAllSPInventoryCategories">عرض الحالة الحالية للمستودع</a></li>
			<li><a target="_blank"  href="gen/ViewUnderThresholdSPInventoryCategories">عرض قطع الغيار تحت الحد الأدنى</a></li>
			<li><a href="gen/ViewSpecificSPInventoryTransactionsReportSearch">عرض الحركات المخزنية لصنف بالمستودع</a></li>
	      </ul>
	    </li>
   <%}else{%>
    
    
    <%if(!currentUserCanViewReportsOnly){%>
		    <li><a href="javascript:void(0)">إدارة الأنظمة والأجهزة</a>
		      <ul>
		        <li><a href="gen/DeviceInfoDisplay">إضافة جهاز جديد</a></li>
		        <li><a href="gen/UpdateDeviceInfoSearch">تعديل بيانات جهاز</a></li>
		        <li class="hidden"><a href="gen/UpdateDeviceInfo">dummy</a></li>
		        <% if(currentUserHasDepHeadRole){ %>
			        <li><a href="gen/TransferDeviceSearch">نقل أو سحب جهاز</a></li>
			        <li class="hidden"><a href="gen/TransferDevice">dummy</a></li>
			        <li><a href="gen/ScrappDeviceSearch">تكهين جهاز</a></li>
			        <li class="hidden"><a href="gen/ScrappDevice">dummy</a></li>
		        <%}%>
		        <li><a href="gen/PpmFollowupSearch">متابعة زيارات الوكيل</a></li>
		        <li class="hidden"><a href="gen/PpmFollowup">dummy</a></li>
		        <!-- ppm -->
		        <li><a href="gen/InternalPpmFollowupSearch">متابعة الصيانة الوقائية</a></li>
		        <li class="hidden"><a href="gen/InternalPpmFollowup">dummy</a></li>
		        <!-- ppm -->
		      </ul>
		    </li>
		    <li><a href="javascript:void(0)">إدارة أوامر العمل</a>
		      <ul>
		        <li><a href="gen/OpenJobOrderSearch">فتح أمر عمل جديد</a></li>
		        <li class="hidden"><a href="gen/OpenJobOrder">dummy</a></li>
		        <% if(currentUserHasDepHeadRole){ %>
			        <li><a href="gen/CancelJobOrderSearch">حذف أمر عمل مفتوح</a></li>
			        <li class="hidden"><a href="gen/CancelJobOrder">dummy</a></li>
		        <%}%>
		        <li><a href="gen/FollowupJobOrderSearch">متابعة أمر عمل مفتوح</a></li>
		        <li class="hidden"><a href="gen/FollowupJobOrder">dummy</a></li>
		      </ul>
		    </li>
	
			<li><a href="javascript:void(0)">إدارة مستودع قطع الغيار</a>
		      <ul>
		        <li><a href="gen/AddNewSPCategoryDisplay">إضافة صنف جديد</a></li>
		        <li><a href="gen/UpdateSPCategorySearch">تعديل بيانات صنف</a></li>
		        <li class="hidden"><a href="gen/UpdateSPCategory">dummy</a></li>
		      </ul>
		    </li>
    <%}%>
    
    <%if(currentUserHasDepHeadRole || currentUserHasRightToEvaluatePerformance || currentUserCanViewReportsOnly){ %>
	    <li><a href="javascript:void(0)">إدارة تقييم الأداء</a>
	      <ul>
	      <%if(currentUserHasRightToEvaluatePerformance){ %>
	        <li><a href="gen/MasterEvaluationDisplay">إعداد تقييم الأداء</a></li>
	      <%}%>
	        <li><a href="gen/ViewPerformanceEvaluation">طباعة تقييم الأداء</a></li>
	      </ul>
	    </li>
    <%}%>
    
    <%if(WebUtil.currentUserHasPrivilegeForSiteAdministration(request)){ %>
   		<li><a href="javascript:void(0)">إدارة الموظفين والمستخدمين</a>
      <ul>
        <li><a href="gen/ResetUserPasswordDisplay">تغيير كلمة مرور مستخدم</a></li>
        <li><a href="gen/UpdateEmployeeInfoDisplay">تحديث بيانات موظف</a></li>
      </ul>
    </li>
    <%} %>
    
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير الأنظمة والأجهزة</a>
	      <ul>
	        <li><a href="gen/ViewDevicesByCatnDepSearch">عرض أنظمة وأجهزة الموقع</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewDevicesUnderMaintenanceAlias">عرض  قائمة بالأجهزة  تحت الصيانة</a></li>
	        <li><a href="gen/DevicesByWarrantyExpireFilterDisplay">عرض الأجهزة التي ينتهي ضمانها خلال فترة</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewDevicesNotInContractAlias">عرض قائمة بالأجهزة خارج العقد</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewDevicesFromOtherSitesAlias">عرض الأجهزة المنقولة من موقع آخر</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewDevicesTransferredAlias">عرض الأجهزة المنقولة إلى موقع آخر</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewDevicesScrappedAlias">عرض قائمة بالأجهزة المكهنة</a></li>
	        <li><a href="gen/ViewDevicesStatusSearch">عرض حالة أنظمة وأجهزة الموقع</a></li>
	        <li><a href="gen/ViewDeviceCardSearch">عرض البطاقة التعريفية لجهاز</a></li>
	        <li><a href="gen/DeviceHistoryFilterDisplay">عرض سجل جهاز (إجمالي تكاليف صيانته)</a></li>
	      </ul>
	    </li>
	    
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير زيارات الوكيل</a>
	      <ul>
	        <li><a <%=menuLnkTarget%> href="gen/ViewPPMTable">عرض جدول زيارة الوكيل</a></li> <!-- PPMVisitsTableFilterDisplay -->
	        <li><a href="gen/DevicesNeedPPMVisitFilterDisplay">عرض الأجهزة التي تحتاج زيارة الوكيل</a></li>
	        <li><a href="gen/PPMVisitsDetailsFilterDisplay">عرض تفاصيل زيارة الوكيل</a></li>
	        <li><a <%=menuLnkTarget%> href="gen/ViewPPMNotHappenedVisitsAlias">عرض زيارات الوكيل التي لم تتم</a></li>
	      </ul>
	    </li>
	    
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير الصيانة الوقائية</a>
	      <ul>
	        <li><a href="gen/ViewCurrentlyScheduledInternalPPMFilterDisplay">عرض الصيانة الوقائية المجدولة</a></li>
	        <li><a href="gen/ViewScheduledInternalPPMByMonthNYearFilterDisplay">عرض الصيانة الوقائية المجدولة خلال شهر وسنة</a></li>
	      </ul>
	    </li>
	    
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير أوامر العمل</a>
	      <ul>
	        <li><a href="gen/ViewJobOrderReportSearch">عرض تقرير أمر عمل</a></li>
	        <li><a href="gen/ViewMaintenanceRequestsSearch">عرض تقرير ببلاغات الصيانة</a></li>
	        <li><a <%=menuLnkTarget%>  href="gen/ViewOpenedJobOrdersAlias">عرض أوامر العمل المفتوحة</a></li>
	        <li><a href="gen/ViewClosedJobOrdersSearch">عرض أوامر العمل المغلقة</a></li>
	        <% if(! currentUserPreventedFromSomeReportsAccess){ %>
	         <li><a <%=menuLnkTarget%>  href="gen/ViewLateJobOrdersAlias">عرض أوامر العمل المتأخرة</a></li>
	         <li><a href="gen/ViewCancelledJobOrdersSearch">عرض أوامر العمل المحذوفة</a></li>
	        <%}%>
	      </ul>
	    </li> 
	     
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير مستودع قطع الغيار</a>
	      <ul>
	        <li><a target="_blank"  href="gen/ViewAllSPInventoryCategories">عرض الحالة الحالية للمستودع</a></li>
	        <% if(! currentUserPreventedFromSomeReportsAccess){ %>
	        <li><a target="_blank"  href="gen/ViewUnderThresholdSPInventoryCategories">عرض قطع الغيار تحت الحد الأدنى</a></li>
	        <%}%>
			<li><a href="gen/ViewSpecificSPInventoryTransactionsReportSearch">عرض الحركات المخزنية لصنف بالمستودع</a></li>
	      </ul>
	    </li>

		<!--  
	   <li class="no_append"><a href="javascript:void(0)">معلومات تهمك</a>
	      <ul>
	        <li><a href="javascript:viewFile('sys/directory.xls')">دليل شركات الصيانة الطبية</a></li>
	      </ul>
	   </li>
	-->
   <%}%> 
   <li class="reports_menu_items"><a href="javascript:void(0)">Admin Tools</a>
			<ul> 
				<li><a href="gen/ManageDirectorateMaintenanceHeadDisplay">إدارة بيانات مدير الصيانة بالمديرية</a></li>
				<li><a href="gen/ManageDirectorateGenMaintenanceHeadDisplay">إدارة بيانات مدير الصيانة غير الطبية بالمديرية</a></li>
				<li><a href="gen/ManageDirectorateMaintenanceDepartmentSupervisorsDisplay">إدارة بيانات مشرفي الصيانة غير الطبية بالمديرية</a></li>
				
				<li><a href="gen/ManageHospitalDiractorDisplay">إدارة بيانات مدير المستشفى</a></li>
				<li><a href="gen/ManageHospitalDepartmentHeadDisplay">إدارة بيانات مشرفي الأقسام</a></li> <!--  check this please -->
				
				<li><a href="gen/ManageDepHeadDisplay">إدارة بيانات رئيس قسم الصيانة غير الطبية</a></li>
				<li><a href="gen/ManageMaintenanceDepartmentSupervisorsDisplay">إدارة بيانات مشرفى  قسم الصيانة غير الطبية</a></li>
				<li><a href="gen/ManageSiteManagerDisplay">إدارة بيانات مدير موقع الصيانة غير الطبية</a></li>		
				<li><a href="gen/ManageSpecialistSiteManagerDisplay">إدارة بيانات مشرفي الأقسام الفرعية</a></li>
				<li><a href="gen/ManageCompanyLabourDisplay">إدارة بيانات عمالة الصيانة غير الطبية</a></li>
				
				<li><a href="gen/ManageHospitalDepartmentDisplay">إدارة بيانات الأقسام الداخلية بالمستشفى</a></li> <!--  check this please -->
				<li><a href="gen/ManageHospitalBuildingDisplay">إدارة بيانات مواقع ومباني المستشفى</a></li> <!--  check this please -->
				<li><a href="gen/ChangeUsernameDisplay" >تغيير اسم المستخدم</a></li>
			</ul>
		</li>
<!--    <li class="no_append"><a href="javascript:void(0)">ثقف نفسك</a>
      <ul>
        <li><a href="javascript:viewFile('\\sys\\ref1.pdf')">دور الصيانة في أداء المستشفيات</a></li>
        <li><a href="javascript:viewFile('\\sys\\ref2.pdf')">دليل إجراءات أعمال الصيانة</a></li>
      </ul>
   </li>  -->
  </ul>
</div>
