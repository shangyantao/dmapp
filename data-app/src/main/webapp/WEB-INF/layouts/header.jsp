<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header" class="span12">
	<div id="title">
	    	<h2><small>SAP Data Migration Showcase</small></h2>
	    	<shiro:user>
			<span class="pull-right">Hello, <shiro:principal property="name"/>!! from company <%=session.getAttribute("companyId")%></span>
		</shiro:user>
	</div>
		
	<div id="menu">
		<ul class="nav nav-tabs">
			<shiro:user>
			<shiro:hasPermission name="user:view">
				<li id="user-tab"><a href="${ctx}/account/user/" >Account List</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="group:view">
				<li id="group-tab"><a href="${ctx}/account/group/">Group List</a></li>
			</shiro:hasPermission>
			 <li id="company-tab"><a href="${ctx}/system/company/">Company Management</a></li>
			<shiro:hasPermission name="system:view">
			    <li id="group-system-tab" class="dropdown">
				<li class="dropdown">
				    <a class="dropdown-toggle"
				       data-toggle="dropdown"
				       href="#">
				    	    System Management
				        <b class="caret"></b>
				      </a>
				    <ul class="dropdown-menu">
				      <li id="agent-tab"><a href="${ctx}/system/agent/">Agent Management</a></li>
					  <li id="system-tab"><a href="${ctx}/system/system/">System Management</a></li>
					  <li id="accountSysUser-tab"><a href="${ctx}/system/accountSysUser/">SystemUser Management</a></li>
			          <li id="company-tab"><a href="${ctx}/system/company/">Company Management</a></li>
			          <li id="event-tab"><a href="${ctx}/system/event/">Event Management</a></li>
				    </ul>
				  </li>
				  </shiro:hasPermission>
				 <shiro:hasPermission name="task:view">
				<li id="task-tab"><a href="${ctx}/task/list/">Task List</a></li>
				</shiro:hasPermission>
			<li><a href="${ctx}/logout">Logout</a></li>
			</shiro:user>
			<shiro:guest>
				<li class="active"><a href="${ctx}/login">Login</a></li>
			</shiro:guest>
		</ul>
		
	</div>
</div>