<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>ECC User管理</title>

</head>
<body>
	<form:form id="inputForm" modelAttribute="accountSysUser" action="${ctx}/system/accountSysUser/save" method="post" class="form-horizontal">
		<fieldset>
			<legend>
				<small>添加ECC User</small>
			</legend>
			<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
			<div class="control-group">
				<label for="allCompanys" class="control-label">选择对应公司:</label>
				<div class="controls">
					<form:select path="company.companyGUID" items="${allCompanys}" itemLabel="cmyName" itemValue="companyGUID" />
				</div>
			</div>
			<div class="control-group">
				<label for="allSystems" class="control-label">选择对应系统:</label>
				<div class="controls">
					<form:select path="system.systemGUID" items="${allSystems}" itemLabel="systemDes" itemValue="systemGUID" />
				</div>
			</div>
			<div class="control-group">
				<label for="allUsers" class="control-label">选择对应Account:</label>
				<div class="controls">
					<form:select path="account.name" items="${allUsers}" itemLabel="name" itemValue="id" />
				</div>
			</div>
			<div class="control-group">
				<label for="sapUserId" class="control-label">ECC User ID:</label>
				<div class="controls">
					<input type="text" id="sapUserId" name="sapUserId" size="50"
						value="${accountSysUser.sapUserId}" class="required" />
				</div>
			</div>
			
			<div class="control-group">
				<label for="language" class="control-label">Language:</label>
				<div class="controls">
					<input type="text" id="language" name="language" size="50"
						value="${accountSysUser.language}" class="required" />
				</div>
			</div>

			<div class="control-group">
				<label for="comments" class="control-label">comments:</label>
				<div class="controls">
					<textarea rows="5" cols="10" id="comments" name="comments" 
						value="${accountSysUser.comments}" class="required"></textarea>
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
