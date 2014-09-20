<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>公司管理</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>Company Management</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>companyGUID</th><th>cmyName</th><th>cmyDes</th><th>cmyStatus</th><th>expirationDate</th><th>schemaServer</th><th>schemaName</th><th>schemaUser</th><th>schemaPwd</th><th>AccessURL</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${companys}" var="company">
			<tr>
				<td>${company.companyGUID}</td>
				<td>${company.cmyName}</td>
				<td>${company.cmyDes}</td>
				<td>${company.cmyStatus}</td>
				<td>${company.expirationDate}</td>
				<td>${company.schemaServer}</td>
				<td>${company.schemaName}</td>
				<td>${company.schemaUser}</td>
				<td>${company.schemaPwd}</td>
				<td>http://localhost:8080/data-app?companyGUID=${company.companyGUID}</td>
				<td>
	    			 <a href="delete/${company.companyGUID}">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<a class="btn" href="create">Create Company</a>
</body>
</html>
