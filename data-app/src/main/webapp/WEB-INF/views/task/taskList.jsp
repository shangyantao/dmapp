<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>PO管理</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>TASK LIST  <a class="btn" href="../create">创建任务</a></h4>	
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<shiro:hasPermission name="task:edit">

	</shiro:hasPermission>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>taskId</th><th>eventType</th><th>userId</th><th>triggerTime</th><th>taskPriority</th><th>startTime</th><th>endTime</th><th>executionTime</th><th>inputParas</th><th>Status</th><th>actions</th></tr></thead>
		<tbody>
		<c:forEach items="${tasks}" var="task">
			<tr>
			  <td>${task.id}</td>
				<td><a href="../searchTask/${task.eventType}">${task.eventType}</a></td>
				<td>${task.userId}</td>
				<td>${task.triggerTime}</td>
				<td>${task.taskPriority}</td>
				<td><nobr>${task.startTime}</nobr></td>
				<td><nobr>${task.endTime}</nobr></td>
				<td>${task.executionTime}</td>
				<td>${task.inputParas}</td>
				<td>${task.taskStatus}</td>
				<td>
				<shiro:hasPermission name="task:edit">
	    				<a href="../execute/${task.id}"><i class="icon-play" title="execute"></i></a><a href="../logs/${task.id}" id="editLink-${task.id}"><i class="icon-list" title="logs"></i></a><a href="../update/${task.id}" id="editLink-${task.id}"><i class="icon-edit" title="update"></i></a> <a href="../delete/${task.id}"><i class="icon-remove" title="delete"></i></a><a href="../copy/${task.id}"><i class="icon-file" title="copy"></i></a><a href="../searchData/${task.userId}/${task.id}/${task.inputParas}"><i class="icon-eye-open" title="see datas"></i> </a>
	    				<!-- <a href="../searchData/${task.userId}/${task.id}/${task.inputParas}"><i class="icon-eye-open" title="see datas"></i> </a>-->
	    		</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
