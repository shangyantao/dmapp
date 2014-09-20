<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>view Data</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>EVENT DISPLAY-e0000_fupararef</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>POSITION</th><th>PARAMETER</th><th>FUNCNAME</th><th>PARAMTYPE</th><th>STRUCTURE</th><th>DEFAULTVAL</th><th>REFERENCE</th><th>OPTIONAL</th><th>TYPE</th><th>CLASS</th><th>LINE_OF</th><th>TABLE_OF</th><th>Action</th></tr></thead>
		<tbody>
		<c:forEach items="${fupararefs}" var="fupararef">
			<tr>
				<td>${fupararef.PPOSITION}</td>
				<td>${fupararef.id.PARAMETER}</td>
				<td>${fupararef.id.FUNCNAME}</td>
				<td>${fupararef.id.PARAMTYPE}</td>
				<td><a href="../viewdd3l/${fupararef.STRUCTURE}">${fupararef.STRUCTURE}</a></td>
				<td>${fupararef.DEFAULTVAL}</td>
				<td>${fupararef.REFERENCE}</td>
				<td>${fupararef.OPTIONAL}</td>
				<td>${fupararef.TYPE}</td>
				<td>${fupararef.CLASS}</td>
				<td>${fupararef.LINE_OF}</td>
				<td>${fupararef.TABLE_OF}</td>
				<td>
	    		 <i class="icon-remove" title="delete"></i>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
