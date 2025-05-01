<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="i18n.jsp_literals" />
<html dir="${sessionScope.direction}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<div id="apps_btns_con">
		<span id='bio_app_btn'><fmt:message
				key="jsp.ChooseApplication.bio" /><i class="fa fa-hand-o-down"></i>
			<a href="bio/WelcomePageDisplay"> <fmt:message
					key="jsp.ChooseApplication.biotitle" /></a> </span> <span id='gen_app_btn'><fmt:message
				key="jsp.ChooseApplication.gen" /><i class="fa fa-hand-o-down"></i>
			<a href="gen/WelcomePageDisplay"><fmt:message
					key="jsp.ChooseApplication.gentitle" /> </a> </span>
		<div class="clearfix"></div>
	</div>
</body>
</html>
