<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="header" class="span12">
	<div id="title">
		<h2>
			<small>SAP Data Migration Agent Showcase</small>
		</h2>
		<span class="pull-right">Hello,username!!</span>

	</div>

	<div id="menu">
		<ul class="nav nav-tabs">
			<li id="agent-tab"><a href="${ctx}/agent/manage" >Server&nbsp;Manage</a></li>
		</ul>
	</div>
</div>