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
	<form:form id="inputForm" modelAttribute="event" action="${ctx}/basic/save/${event.EVENT_ID}" method="post" class="form-horizontal">
		<input type="hidden" name="eventId" value="${event.EVENT_ID}"/>
		<fieldset>
			<legend><small>event management</small></legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
			
		     <div class="control-group">
				<label for="EVENT_NUMBER" class="control-label">EVENT_NUMBER:</label>
				<div class="controls">
					<input type="text" id="EVENT_NUMBER" name="EVENT_NUMBER" size="50" value="${event.EVENT_NUMBER}" class="required"/>
				</div>
			</div>
		   <div class="control-group">
				<label for="EVENT_NAME" class="control-label">EVENT_NAME:</label>
				<div class="controls">
					<input type="text" id="EVENT_NAME" name="EVENT_NAME" size="50" value="${event.EVENT_NAME}" class="required"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="EVENT_DES" class="control-label">EVENT_DES:</label>
				<div class="controls">
					<input type="text" id="EVENT_DES" name="EVENT_DES" size="50" value="${event.EVENT_DES}" class="required"/>
				</div>
			</div>
			
			<div class="control-group">
				<label for="FUNCNAME" class="control-label">FUNCNAME:</label>
				<div class="controls">
					<input type="text" id="FUNCNAME" name="FUNCNAME" size="50" value="${event.FUNCNAME}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="COMMENTS" class="control-label">COMMENTS:</label>
				<div class="controls">
				<textarea rows="10" cols="10" id="COMMENTS" name="COMMENTS"  value="${event.COMMENTS}" class="required"></textarea>
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
