<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Server&nbsp;Manage</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>Agent</th><th>Company</th><th>Server</th><th>Status</th><th>操作</th></tr></thead>
		<tbody>
		<tr>
			<td>${agentId}</td>
			<td>${companyId}</td>
			<td>${serverUrl}</td>
			<td>${agentStartStatus}</td>
			<td>
				<c:choose>
					<c:when test="${'connecting' == agentStartStatus}">
						Starting
					</c:when>
					<c:when test="${'started' == agentStartStatus}">
						<a class="btn" href="stop">Stop</a>
					</c:when>
					<c:when test="${'stopped' == agentStartStatus}">
						<a class="btn" href="start">Start</a>
					</c:when>
				</c:choose>
			</td>
		</tbody>
	</table>
</body>
</html>
