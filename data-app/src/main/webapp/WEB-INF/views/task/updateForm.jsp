<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
</head>

<body>
		<form:form id="inputForm" modelAttribute="task" action="${ctx}/task/updateTask" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}"/>
		<fieldset>
			<legend><small>管理任务</small></legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
	
			<div class="control-group">
				<label for="eventType" class="control-label">事件类型:</label>
				<div class="controls">
					<input type="text" id="eventType" name="eventType" size="50" value="${task.eventType}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="userId" class="control-label">用户编号:</label>
				<div class="controls">
					<input type="text" id="userId" name="userId" size="50" value="${task.userId}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="triggerTime" class="control-label">触发时间:</label>
				<div class="controls">
					<input type="text" id="triggerTime" name="triggerTime" size="50" value="${task.triggerTime}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="taskPriority" class="control-label">任务优先级:</label>
				<div class="controls">
					<input type="text" id="taskPriority" name="taskPriority" size="50" value="${task.taskPriority}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="inputParas" class="control-label">事件参数:</label>
				<div class="controls">
					<input type="text" id="inputParas" name="inputParas" size="50" value="${task.inputParas}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="taskStatus" class="control-label">任务状态:</label>
				<div class="controls">
					<input type="text" id="taskStatus" name="taskStatus" size="50" value="${task.taskStatus}" class="email"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>

	</form:form>
</body>
</html>
