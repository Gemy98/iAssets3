<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" import="com.iassets.common.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String langCode = WebUtil.getSessionLanguage(request); %>
<fmt:setBundle basename="i18n.jsp_literals" /><html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<form method="post" action="${processServlet }">
		<table class="layout_grid">
			<tr>
				<td colspan="2"><h1 class="page_title"><fmt:message key="jsp.ChangeUserName.pagetitle" /></h1></td>
			</tr>
			<tr>
				<td colspan="2" style="font-weight: bold">
					** <fmt:message key="jsp.ChangeUserName.msg1"> 
				     	  <fmt:param value="<%=Default.MAX_ALLOWED_USERNAME_CHANGE_COUNT %>" />
				       </fmt:message>  
					<br />
					** <fmt:message key="jsp.ChangeUserName.msg2"> 
				     	  <fmt:param value="${ previousChangeCount }" />
				       </fmt:message> 
				</td>
			</tr>
			<tr>
				<td class="side_label_middle"> <fmt:message key="jsp.ChangeUserName.currentUsername" />  :</td>
				<td>${ currentUsername }</td>
			</tr> 
		 
		 <c:choose>
				<c:when test="${nameSpace != null}">
					<tr>
						<td class="side_label_middle"><fmt:message key="jsp.ChangeUserName.newUsername" /> :</td>
						<td align="right" dir="ltr"><input type="text"
							id="newUsername" name="newUsername" data-rule-alphanumeric="true"
							data-msg-alphanumeric='<fmt:message key="jsp.ChangeUserName.newUsername.msg1" />'
							data-msg-remote='<fmt:message key="jsp.ChangeUserName.newUsername.msg2" />' required /> <span><%=Default.USERNAME_SEPARATOR%>${ nameSpace }
						</span></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" name="button"
							id="button" value='<fmt:message key="jsp.ChangeUserName.submitBtn" />'> <%=CommonHtmlUtil.getResetButtonHTML(langCode)%>
						</td>
					</tr>
				</c:when>

				<c:otherwise>
					<tr>
						<td colspan="2" align="center"><%=CommonHtmlUtil.getResetButtonHTML(langCode)%></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		
	</form>
	<script>
	<c:if test="${nameSpace != null}">
	
		$(function() {
			$("#newUsername").rules("add", {
				remote : {
					url : "CheckUserNameDuplication",
					type : "post",
					data : {
						     newUsername :function() {return $("#newUsername").val()}
						   }
					}
				});
		});

	
   </c:if>
	</script>
</body>
</html>
