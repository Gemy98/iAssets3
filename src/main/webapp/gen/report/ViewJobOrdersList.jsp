<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"
	import="com.iassets.common.util.*,com.iassets.general.util.HtmlUtil,com.iassets.general.entity.*,java.util.*"%>
<%
	String langCode = WebUtil.getSessionLanguage(request);
    Boolean flag = (Boolean) request.getAttribute("showClosedJobOrders");
    Boolean flag2 = (Boolean) request.getAttribute("viewLate");
	boolean showClosedJobOrders = (flag != null && flag == true);
	boolean showOpenedJobOrders = (flag2 != null && flag2 == false);
	boolean showLateJobOrders = (flag2 != null && flag2 == true);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<%
		List<GenJobOrder> jobOrders = (List<GenJobOrder>)request.getAttribute("jobOrders");
	%>
	<table id="jo_list" class="tablesorter">
		<thead>
			<tr>
				<th>م</th>
				<th>رقم الأمر</th>
				<th>القسم المختص</th>
				<th>تاريخ الفتح</th>
				<%if(showLateJobOrders){%>
				<th>تاريخ العطل</th>
				<%}%>
				<%if(!showClosedJobOrders){%>
				<th>وصف العطل</th>
				<%}%>
				<th>اسم الجهاز</th>
				<th>كود الجهاز</th>
				<!-- <th>الفئة</th> -->
				<th>المكان</th>
				<th>نموذج طلب الصيانة</th>
				<%if(showOpenedJobOrders || showClosedJobOrders){%>
					<th>تقرير الوكيل</th>
					<th>عرض السعر</th>
				<%}%>
				<%if(showClosedJobOrders){%>
					<th>تقرير الشركة المسؤولة</th>
					<th>التقرير النهائي</th>
				<%}%>
				<%if(!showClosedJobOrders){%>
				<th>الحالة الحالية</th>
				<th>الموظف المسؤول</th>
				<%}%>
			</tr>
		</thead>
		<tbody>
			<%
			GenJobOrder jo = null;
			GenHospitalDevice hd = null;
			// String qStr = null;
		    for (int i = 0; i< jobOrders.size(); i++){
		         jo = jobOrders.get(i);
		         hd = jo.getHospitalDevice();
			%>
			<tr>
				<td align="center"><%=(i+1)%></td>
				<td>
					<%
						String jobOrderNo = Common.getDisplayString(jo.getJobOrderNo(),"-",true); 
													  if(WebUtil.currentUserHasViewPrivilegesOnly(request) || showClosedJobOrders){
														  out.print(jobOrderNo);
													  }else{
					%> <a
					href='<%=request.getContextPath() + "/gen/FollowupJobOrder?purpose=متابعة أمر عمل مفتوح&jobOrderNo=" + jo.getJobOrderNo()%>'
					target="_blank"> <%=jobOrderNo%></a> <%
 	}
 %>
				</td>
				<td><%=Common.getDisplayString(jo.getResponsibleDepartmentName(langCode),"-",true)%></td>
				<td><%=DateUtil.getDateString(jo.getJobOrderDate())%></td>
				<%if(showLateJobOrders){%>
				<td><%=DateUtil.getDateString(jo.getDamageDate())%></td>
				<%}%>
				<%if(!showClosedJobOrders){%>
				<td><%=Common.getDisplayString(jo.getDamageDescription(),"-",true)%></td>
				<%}%>
				<td><%=Common.getDisplayString(hd.getName(),"-",true)%></td>
				<td><%=Common.getDisplayString(hd.getCode(),"-",true)%></td>
				<%-- <td><%=Common.getDisplayString(hd.getCategory().getName(),"-",true)%></td> --%>
				<td><%=Common.getDisplayString(hd.getLocation(langCode),"-",true)%></td>

				<td>
				    <%= HtmlUtil.showUploadedFilesInViewMode(jo.getRequestFormUrl(), false, langCode)%>
				</td>
				
				<%if(showOpenedJobOrders || showClosedJobOrders){%>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getAgentReportUrl(), false, langCode)%>
				</td>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getQuotationScanUrl(), false, langCode)%>
				</td>
				<%}%>
				
				<%if(showClosedJobOrders){%>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getFinalAgentReportUrl(), false, langCode)%>
				</td>
				<td>
					<%= HtmlUtil.showUploadedFilesInViewMode(jo.getFinalReportUrl(), false, langCode)%>
				</td>
                <%}%>
                <%if(!showClosedJobOrders){%>
                <td><%=Common.getDisplayString(jo.getCurrentPhaseName(),"-",true)%></td>
				<td>
				<%-- <%=Common.getDisplayString((showClosedJobOrders)?jo.getClosedBy().getName():jo.getOpenedBy().getName(),"-",true)%> --%>
				<%
				if(jo.getResponsibleEngineer() != null)
				   out.print(Common.getDisplayString(jo.getResponsibleEngineer().getName(langCode),"-",true));
				else
					out.print("-");
				%>
				</td>
				<%}%>
			</tr>
			<%}%>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#jo_list thead th:eq(1)").data("sorter", false);
			$("#jo_list").tablesorter({
				widthFixed : false,
				widgets : [ 'zebra' ],
				headers : {
					0 : {
						sorter : false
					}
				}
			});
		});
	</script>
</body>
</html>
