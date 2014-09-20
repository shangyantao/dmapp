<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务日志管理</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>Task log list</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>TaskId</th><th>RunId</th><th>logType</th><th>logTime</th><th>logId</th><th>logDesc</th></tr></thead>
		<tbody>
		<c:forEach items="${taskLogs}" var="tasklog">
			<tr>
			  <td>${tasklog.task.id}</td>
			  <td>${tasklog.runId}</td>
				<td>${tasklog.logType}</td>
				<td>${tasklog.logTime}</td>
			  	<td>${tasklog.id}</td>
				<td>${tasklog.logDesc}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
