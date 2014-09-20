<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>事件管理</title>
	
</head>

<body>
	<form:form id="inputForm" modelAttribute="event" action="${ctx}/system/event/save/${event.eventId}" method="post" class="form-horizontal">
		<input type="hidden" name="eventId" value="${event.eventId}"/>
		<fieldset>
			<legend><small>管理事件</small></legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
	
		   <div class="control-group">
				<label for="eventName" class="control-label">eventName:</label>
				<div class="controls">
					<input type="text" id="eventName" name="eventName" size="50" value="${event.eventName}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="eventDes" class="control-label">eventDes:</label>
				<div class="controls">
					<input type="text" id="eventDes" name="eventDes" size="50" value="${event.eventDes}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="comments" class="control-label">comments:</label>
				<div class="controls">
				<textarea rows="10" cols="10" id="comments" name="comments"  value="${event.comments}" class="required"></textarea>
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
