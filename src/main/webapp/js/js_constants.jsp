<%@ page
	import="com.iassets.biomedical.entity.*,com.iassets.common.util.*,org.springframework.beans.factory.annotation.*,org.springframework.web.context.support.SpringBeanAutowiringSupport"
	language="java" pageEncoding="UTF-8" contentType="text/javascript"%>

<%!public void jspInit() {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}

	@Autowired
	@Qualifier("commonDBQueryManager")

	protected com.iassets.common.DB.CommonDBQueryManager commonDBQueryManager;

	public static String bioLookupDeviceNameAutocompleteJsonArray = null;
	public static String bioLookupSubcontractorAutocompleteJsonArray = null;
%>
<%
	if (bioLookupDeviceNameAutocompleteJsonArray == null) {
		bioLookupDeviceNameAutocompleteJsonArray = AppUtil
				.getNamesJsonArrayFromJavaList(commonDBQueryManager.findAll(BioLookupDeviceName.class));
	}

	if (bioLookupSubcontractorAutocompleteJsonArray == null) {
		bioLookupSubcontractorAutocompleteJsonArray = AppUtil
				.getNamesJsonArrayFromJavaList(commonDBQueryManager.findAll(BioLookupSubcontractor.class));
	}
%>

var appServerIP = "<%=WebUtil.getServerIpWithPortNo(request)%>"; 
var appContext = "<%=WebUtil.getAppContextPath(request)%>"; var
bioLookupDeviceNameAutocompleteJsonArray = <%=bioLookupDeviceNameAutocompleteJsonArray%>;
bioLookupSubcontractorAutocompleteJsonArray = <%=bioLookupSubcontractorAutocompleteJsonArray%>;
