<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Agent管理</title>
	<script>
		
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#relGroup").focus();
		});
	</script>
</head>

<body>
	<h4>Agent列表</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>AgentGUID</th><th>cmyName</th><th>serverUrl</th><th>agentHost</th><th>agentIP</th><th>Description</th><th>LastConnectionDate</th><th>UnConnectionTime</th><th>Status</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${agents}" var="agent">
			<tr>
				<td>${agent.agentGUID}</td>
				<td>${agent.cmyName}</td>
				<td>${agent.serverUrl}</td>
				<td>${agent.agentHost}</td>
				<td>${agent.agentIP}</td>
				<td>${agent.agentDes}</td>
				<td>${agent.lastConnDate}</td>
				<td>${agent.unConnTime}</td>
				<td>
				<c:if test="${agent.agentStatus=='1'}">
				<i class="icon-ok" title="connected"></i>
				</c:if>
				<c:if test="${agent.agentStatus=='0'}">
				<i class="icon-off" title="unconnected"></i>
				</c:if>
				</td>
				<td>
	    			 <a href="delete/${agent.agentGUID}"><i class=" icon-remove"></i></a>
	    			 <a href="${ctx}/static/xmlfiles/${agent.agentGUID}.xml"><i class="icon-download-alt"></i></a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<a class="btn" href="create">创建Agent</a>
</body>
</html>