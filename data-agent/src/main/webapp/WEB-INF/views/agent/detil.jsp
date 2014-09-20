<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Server&nbsp;Manage</title>
</head>

<body>
	<form:form id="serverFrom" action="save" method="post" class="form-horizontal">
		<div class="control-group">
			<label for="agentId" class="control-label">Agent ID:</label>
			<div class="controls">
				<input type="text" id="agentId" name="agentId" value="${agentId}" class="required span2"/>
			</div>
		</div>
		<div class="control-group">
			<label for="companyId" class="control-label">Company ID:</label>
			<div class="controls">
				<input type="text" id="companyId" name="companyId" value="${companyId}" class="required span2"/>
			</div>
		</div>
		<div class="control-group">
			<label for="serverUrl" class="control-label">Server URL:</label>
			<div class="controls">
				<input type="text" id="serverUrl" name="serverUrl" value="${serverUrl}"  class="required span2"/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
			<input id="submit" class="btn" type="submit" value="Save"/>
			</div>
		</div>
	</form:form>
</body>
</html>
