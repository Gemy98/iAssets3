<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*,com.iassets.common.entity.CommonUser"%>
<% 
   boolean currentUserHasDepHeadRole = request.isUserInRole(Enums.SYS_ROLES.BIO_SITE_MANAGER.getRoleName()); 
   boolean currentUserCanViewReportsOnly = WebUtil.currentUserHasViewPrivilegesOnly(request);
   boolean currentUserPreventedFromSomeReportsAccess = currentUserCanViewReportsOnly;
   // boolean userHasRightToChangeHospital = WebUtil.userHasRightToChangeSessionHospital(request);
   CommonUser currentSessionUser = WebUtil.getSessionUser(request);
   String menuLnkTarget = (currentSessionUser.getCurrentSite().getContainsSeveralLocations()) ? "" : "target='_blank'";
   boolean currentUserNotInventoryKeeper = ! request.isUserInRole(Enums.SYS_ROLES.BIO_INVENTORY_KEEPER.getRoleName());
   boolean currentUserNotEndUser = true;//! request.isUserInRole(Enums.SYS_ROLES.BIO_END_USER.getRoleName());
%>

<div id="dhtmlgoodies_slidedown_menu">
<%if(currentUserNotEndUser){%>
  <ul>
<%--  <%if(currentUserNotInventoryKeeper){%> --%>
   	<li><a href="javascript:void(0)">بيانات الموقع</a>
      <ul>
        <li><a href="bio/HospitalProfileDisplay">عرض بيانات الموقع</a></li>
        <li><a href="bio/UserProfileDisplay" id="home_lnk">عرض بيانات المستخدم</a></li>
        <li><a href="bio/HospitalContractProfileDisplay">عرض بيانات الشركة المشغلة والعقد</a></li>
        <!--  <li><a href="bio/ChangePasswordDisplay">تغيير كلمة المرور</a></li> -->
      </ul>
    </li>
<%--  <%}%> --%>
    <%if(! currentUserCanViewReportsOnly){
        if(currentUserNotInventoryKeeper){%>
		   <li><a href="javascript:void(0)">إدارة الأجهزة</a>
		      <ul>
		        <li><a href="bio/DeviceInfoDisplay">إضافة جهاز جديد</a></li>
		        <li><a href="bio/UpdateDeviceInfoSearch">تعديل بيانات جهاز</a></li>
		        <li class="hidden"><a href="bio/UpdateDeviceInfo">dummy</a></li>
		        <% if(currentUserHasDepHeadRole){ %>
			        <li><a href="bio/TransferDeviceSearch">نقل أو سحب جهاز</a></li>
			        <li class="hidden"><a href="bio/TransferDevice">dummy</a></li>
			        <li><a href="bio/ScrappDeviceSearch">تكهين جهاز</a></li>
			        <li class="hidden"><a href="bio/ScrappDevice">dummy</a></li>
		        <%}%>
		        <li><a href="bio/PpmFollowupSearch">متابعة الزيارات الوقائية</a></li>
		        <li class="hidden"><a href="bio/PpmFollowup">dummy</a></li>
		      </ul>
		    </li>
		   <li><a href="javascript:void(0)">إدارة أوامر العمل</a>
		      <ul>
		        <li><a href="bio/OpenJobOrderSearch">فتح أمر عمل جديد</a></li>
		        <li class="hidden"><a href="bio/OpenJobOrder">dummy</a></li>
		        <% if(currentUserHasDepHeadRole){ %>
			        <li><a href="bio/CancelJobOrderSearch">حذف أمر عمل مفتوح</a></li>
			        <li class="hidden"><a href="bio/CancelJobOrder">dummy</a></li>
		        <%}%>
		        <li><a href="bio/FollowupJobOrderSearch">متابعة أمر عمل مفتوح</a></li>
		        <li class="hidden"><a href="bio/FollowupJobOrder">dummy</a></li>
		      </ul>
		    </li>
		<%}%>
	    <li><a href="javascript:void(0)">إدارة مستودع قطع الغيار</a>
	      <ul>
	        <li><a href="bio/AddNewSPCategoryDisplay">إضافة صنف جديد</a></li>
	        <li><a href="bio/UpdateSPCategorySearch">تعديل بيانات صنف</a></li>
	        <li class="hidden"><a href="bio/UpdateSPCategory">dummy</a></li>
	      </ul>
	    </li>
    <%} %>
    
    <%if(WebUtil.currentUserHasPrivilegeForSiteAdministration(request)){ %>
	    <li><a href="javascript:void(0)">إدارة الموظفين والمستخدمين</a>
	      <ul>
	        <li><a href="bio/ResetUserPasswordDisplay">تغيير كلمة مرور مستخدم</a></li>
	        <li><a href="bio/UpdateEmployeeInfoDisplay">تحديث بيانات موظف</a></li>
	      </ul>
	    </li>
    <%} %>
    
    <%if(currentUserNotInventoryKeeper){%>
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير الأجهزة</a>
	      <ul>
	        <li><a href="bio/ViewDevicesByCatnDepSearch">عرض أجهزة الموقع</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesUnderMaintenanceAlias">عرض  قائمة بالأجهزة  تحت الصيانة</a></li>
	        <li><a href="bio/DevicesByWarrantyExpireFilterDisplay">عرض الأجهزة التي ينتهي ضمانها خلال فترة</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesNotInContractAlias">عرض قائمة بالأجهزة خارج العقد</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesFromOtherSitesAlias">عرض الأجهزة المنقولة من موقع آخر</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesTransferredAlias">عرض الأجهزة المنقولة إلى موقع آخر</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewDevicesScrappedAlias">عرض قائمة بالأجهزة المكهنة</a></li>
	        <li><a href="bio/ViewDevicesStatusSearch">عرض حالة أجهزة الموقع</a></li>
	        <li><a href="bio/ViewDeviceCardSearch">عرض البطاقة التعريفية لجهاز</a></li>
	        <li><a href="bio/DeviceHistoryFilterDisplay">عرض سجل جهاز (إجمالي تكاليف صيانته)</a></li>
	      </ul>
	    </li>
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير الزيارات الوقائية</a>
	      <ul>
	        <li><a href="bio/PPMVisitsTableFilterDisplay">عرض جدول الزيارات الوقائية</a></li>
	        <li><a href="bio/DevicesNeedPPMVisitFilterDisplay">عرض الأجهزة التي تحتاج زيارة وقائية</a></li>
	        <li><a href="bio/PPMVisitsDetailsFilterDisplay">عرض تفاصيل الزيارة الوقائية</a></li>
	        <li><a <%=menuLnkTarget%> href="bio/ViewPPMNotHappenedVisitsAlias">عرض الزيارات الوقائية التي لم تتم</a></li>
	      </ul>
	    </li>
	    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير أوامر العمل</a>
	      <ul>
	        <%if(currentSessionUser.getUserType() == Enums.USER_TYPE.BIOMEDICAL_SITE_MANGER.getId()){%>
	            <li><a target="_blank" href="bio/ViewEmptyStandardJobOrderReport">عرض نموذج أمر عمل فارغ</a></li>
	        <%}%>
	        <li><a href="bio/ViewJobOrderReportSearch">عرض تقرير أمر عمل</a></li>
	        <li><a <%=menuLnkTarget%>  href="bio/ViewOpenedJobOrdersAlias">عرض أوامر العمل المفتوحة</a></li>
	        <li><a href="bio/ViewClosedJobOrdersSearch">عرض أوامر العمل المغلقة</a></li>
	        <%if(! currentUserPreventedFromSomeReportsAccess){ %>
	            <li><a <%=menuLnkTarget%>  href="bio/ViewLateJobOrdersAlias">عرض أوامر العمل المتأخرة</a></li>
	            <li><a href="bio/ViewCancelledJobOrdersSearch">عرض أوامر العمل المحذوفة</a></li>
	        <%}%>
	      </ul>
       </li>  
    <%}%>
    <li class="reports_menu_items"><a href="javascript:void(0)">تقارير مستودع قطع الغيار</a>
      <ul>
        <li><a target="_blank"  href="bio/ViewAllSPInventoryCategories">عرض الحالة الحالية للمستودع</a></li>
        <% if(! currentUserPreventedFromSomeReportsAccess){ %>
        <li><a target="_blank"  href="bio/ViewUnderThresholdSPInventoryCategories">عرض قطع الغيار تحت الحد الأدنى</a></li>
        <%}%>
        <%-- <li><a href="bio/JobOrderFilterDisplay?<%=Default.SEARCH_DEST_PARAM_NAME%>=<%=Enums.JOB_ORDER_STATUS.CANCELLED.getStatus()%>">عرض الحالة الحالية لصنف معين</a></li> --%>
      </ul>
    </li>
   
  <%--    <%if(userHasRightToChangeHospital){ 
     String lnkText = "متابعة موقع آخر";
   %>
    <li><a href="ChooseSessionHospitalDisplay?purpose=<%=lnkText%>"><%=lnkText%></a></li>
   <%}%> --%>
   <!--  <li><a href="Logout">خروج</a></li> -->
   <%if(currentUserNotInventoryKeeper){%>
  	   <li class="no_append"><a href="javascript:void(0)">معلومات تهمك</a>
      <ul>
        <li><a href="javascript:viewFile('\\sys\\directory.xls')">دليل شركات الصيانة الطبية</a></li>
      </ul>
   </li> 
   <%}%>
<!--    <li class="no_append"><a href="javascript:void(0)">ثقف نفسك</a>
      <ul>
        <li><a href="javascript:viewFile('\\sys\\ref1.pdf')">دور الصيانة في أداء المستشفيات</a></li>
        <li><a href="javascript:viewFile('\\sys\\ref2.pdf')">دليل إجراءات أعمال الصيانة</a></li>
      </ul>
   </li>  -->
  </ul>
<%}else{%>
 <ul>
   	<li><a href="javascript:void(0)">بيانات الموقع</a>
      <ul>
        <li><a href="bio/HospitalProfileDisplay">عرض بيانات الموقع</a></li>
        <li><a href="bio/UserProfileDisplay" id="home_lnk">عرض بيانات المستخدم</a></li>
        <li><a href="bio/HospitalContractProfileDisplay">عرض بيانات الشركة المشغلة والعقد</a></li>
        <!--  <li><a href="bio/ChangePasswordDisplay">تغيير كلمة المرور</a></li> -->
      </ul>
    </li>

    <li><a href="javascript:void(0)">إدارة أوامر العمل</a>
      <ul>
        <li><a href="bio/OpenJobOrderSearch">فتح أمر عمل جديد</a></li>
        <li class="hidden"><a href="bio/OpenJobOrder">dummy</a></li>
      </ul>
    </li>
  </ul>
<%}%>
</div>
