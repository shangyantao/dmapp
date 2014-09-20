<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>登录页</title>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</head>

<body>
	<form:form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
	<%
	String error = (String) request.getAttribute("USER_LOGIN_ERROR");
	if(error != null){
	%>
		<div class="control-group">
		<div class="controls">
		<div class="alert alert-error">
		<button class="close" data-dismiss="alert">×</button>
		登录失败，请重试.
		</div>
		</div>
		</div>
	<%
	}
	%>
		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="username" name="username" size="50" value="${username}" class="required span2"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<input type="password" id="password" name="password" size="50"  class="required span2"/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<input id="submit" class="btn" type="submit" value="登录"/>
			</div>
		</div>
	</form:form>
</body>
</html>
