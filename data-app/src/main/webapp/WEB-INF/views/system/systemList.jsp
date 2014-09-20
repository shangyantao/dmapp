<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>系统管理</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>系统列表</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>SystemGUID</th><th>cmyName</th><th>Agent</th><th>Description</th><th>Comments</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${systems}" var="system">
			<tr>
				<td>${system.systemGUID}</td>
				<td>${system.cmyName}</td>
				<td>${system.agentGUID}</td>
				<td>${system.systemDes}</td>
				<td>${system.comments}</td>
				<td>
	    			 <a href="delete/${system.systemGUID}">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<a class="btn" href="create">创建系统</a>
</body>
</html>
