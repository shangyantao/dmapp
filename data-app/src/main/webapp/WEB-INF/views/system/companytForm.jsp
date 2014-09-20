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
	<form:form id="inputForm" modelAttribute="company" action="${ctx}/system/company/save/${company.companyGUID}" method="post" class="form-horizontal">
		<input type="hidden" name="companyGUID" value="${company.companyGUID}"/>
		<fieldset>
			<legend><small>Create Company</small></legend>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
	
			<div class="control-group">
				<label for="cmyName" class="control-label">cmyName:</label>
				<div class="controls">
					<input type="text" id="cmyName" name="cmyName" size="50" value="${company.cmyName}" class="required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="cmyDes" class="control-label">cmyDes:</label>
				<div class="controls">
				<textarea rows="5" cols="10" id="cmyDes" name="cmyDes"  value="${company.cmyDes}" class="required"></textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label for="cmyStatus" class="control-label">cmyStatus:</label>
				<div class="controls">
					<input type="text" id="cmyStatus" name="cmyStatus" size="50" value="${company.cmyStatus}" class="required"/>
				</div>
			</div>
			
				<div class="control-group">
				<label for="cmyStatus" class="control-label">expirationDate:</label>
				<div class="controls">
					<input type="text" id="expirationDate" name="expirationDate" size="50" value="${company.expirationDate}" class="required"/>
				</div>
			  </div>
			  <div class="control-group">
				<label for="schemaServer" class="control-label">schemaServer:</label>
				<div class="controls">
					<input type="text" id="schemaServer" name="schemaServer" size="50" value="${company.schemaServer}" class="required"/>
				</div>
			  </div>
			  <div class="control-group">
				<label for="schemaName" class="control-label">schemaName:</label>
				<div class="controls">
					<input type="text" id="schemaName" name="schemaName" size="50" value="${company.schemaName}" class="required"/>
				</div>
			  </div>
			      <div class="control-group">
				<label for="schemaUser" class="control-label">schemaUser:</label>
				<div class="controls">
					<input type="text" id="schemaUser" name="schemaUser" size="50" value="${company.schemaUser}" class="required"/>
				</div>
			  </div>
			    <div class="control-group">
				<label for="schemaPwd" class="control-label">schemaPwd:</label>
				<div class="controls">
					<input type="text" id="schemaPwd" name="schemaPwd" size="50" value="${company.schemaPwd}" class="required"/>
				</div>
			  </div>
			  
				<div class="control-group">
				<label for="comments" class="control-label">comments:</label>
				<div class="controls">
				<textarea rows="5" cols="10" id="comments" name="comments"  value="${company.comments}" class="required"></textarea>
				</div>
			    </div>
			<%-- 	<div class="control-group">
					<label for="allEvents" class="control-label">选择公司对应事件:</label>
					<div class="controls">
						<form:checkboxes path="eventList" items="${allEvents}" itemLabel="eventName" itemValue="eventId" />
					</div>
				</div>	 --%>
			<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>

	</form:form>
</body>
</html>
