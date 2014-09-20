<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>系统管理</title>

</head>
<body>
	<form:form id="inputForm" modelAttribute="agent" action="${ctx}/system/agent/save" method="post" class="form-horizontal">
		<fieldset>
			<legend>
				<small>添加Agent</small>
			</legend>
			<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
			<div class="control-group">
				<label for="allCompanys" class="control-label">选择系统对应公司:</label>
				<div class="controls">
					<form:select path="company.companyGUID" items="${allCompanys}" itemLabel="cmyName" itemValue="companyGUID" />
				</div>
			</div>
			<div class="control-group">
				<label for="systemDes" class="control-label">ServerURL:</label>
				<div class="controls">
					<input type="text" id="serverUrl" name="serverUrl" size="50"
						value="${agent.serverUrl}" class="required" />
				</div>
			</div>
			<div class="control-group">
				<label for="systemDes" class="control-label">Description:</label>
				<div class="controls">
					<input type="text" id="agentDes" name="agentDes" size="50"
						value="${agent.agentDes}" class="required" />
				</div>
			</div>
			<div class="control-group">
				<label for="systemId" class="control-label">Last Connection Date:</label>
				<div class="controls">
					<input type="text" id="lastConnDate" name="lastConnDate" size="50"
						value="${agent.lastConnDate}" class="required"/>
				</div>
			</div>

			<div class="control-group">
				<label for="comments" class="control-label">comments:</label>
				<div class="controls">
					<textarea rows="5" cols="10" id="comments" name="comments" 
						value="${agent.comments}" class="required"></textarea>
				</div>
			</div>

			<div class="form-actions">
				<input id="submit" class="btn btn-primary" type="submit" value="提交" />&nbsp;
				<input id="cancel" class="btn" type="button" value="返回"
					onclick="history.back()" />
			</div>
		</fieldset>

	</form:form>
</body>
</html>
