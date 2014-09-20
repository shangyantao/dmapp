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
	<h4>EVENT DISPLAY-e0000_dd03l</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>DM_USED_FLAG</th><th>DM_EXCLUDE_FLAG</th><th>DM_SEQ_NO</th><th>DM_CHANGED_BY</th><th>DM_LAST_CHANGE_TIME</th><th>POSITION</th><th>TABNAME</th><th>FIELDNAME</th><th>AS4LOCAL</th><th>AS4VERS</th><th>KEYFLAG</th><th>MANDATORY</th><th>ROLLNAME</th><th>CHECKTABLE</th><th>ADMINFIELD</th><th>INTTYPE</th>
		<th>REFTABLE</th><th>PRECFIELD</th><th>CONROUT</th><th>NOTNULL</th><th>DATATYPE</th><th>LENG</th><th>DECIMALS</th><th>DOMNAME</th><th>SHLPORIGIN</th><th>TABLETYPE</th><th>DEPTH</th><th>COMPTYPE</th>
		<th>REFTYPE</th><th>LANGUFLAG</th><th>DBPOSITION</th><th>ANONYMOUS</th><th>OUTPUTSTYLE</th><th>Action</th></tr></thead>
		<tbody>
		<c:forEach items="${dd03ls}" var="dd03l">
			<tr>
			    <td>${dd03l.dd03ldm.DM_USED_FLAG}</td>
			    <td>${dd03l.dd03ldm.DM_EXCLUDE_FLAG}</td>
			    <td>${dd03l.dd03ldm.DM_SEQ_NO}</td>
			    <td>${dd03l.dd03ldm.DM_CHANGED_BY}</td>
			    <td>${dd03l.dd03ldm.DM_LAST_CHANGE_TIME}</td>
			    
				<td>${dd03l.id.POSITION}</td>
				<td>${dd03l.id.TABNAME}</td>
				<td>${dd03l.id.FIELDNAME}</td>
				<td>${dd03l.id.AS4LOCAL}</td>
				<td>${dd03l.id.AS4VERS}</td>
				<td>${dd03l.KEYFLAG}</td>
				<td>${dd03l.MANDATORY}</td>
				<td>${dd03l.ROLLNAME}</td>
				<td>${dd03l.CHECKTABLE}</td>
				<td>${dd03l.ADMINFIELD}</td>
				<td>${dd03l.INTTYPE}</td>
				<td>${dd03l.REFTABLE}</td>
				<td>${dd03l.PRECFIELD}</td>
				<td>${dd03l.CONROUT}</td>
				<td>${dd03l.NOTNULL}</td>
				<td>${dd03l.DATATYPE}</td>
				<td>${dd03l.LENG}</td>
				<td>${dd03l.DECIMALS}</td>
				<td>${dd03l.DOMNAME}</td>
				<td>${dd03l.SHLPORIGIN}</td>
				<td>${dd03l.TABLETYPE}</td>
				<td>${dd03l.DEPTH}</td>
				<td>${dd03l.COMPTYPE}</td>
				<td>${dd03l.REFTYPE}</td>
				<td>${dd03l.LANGUFLAG}</td>
				<td>${dd03l.DBPOSITION}</td>
				<td>${dd03l.ANONYMOUS}</td>
				<td>${dd03l.OUTPUTSTYLE}</td>
				
				<td>
	    		 <i class="icon-remove" title="delete"></i>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
