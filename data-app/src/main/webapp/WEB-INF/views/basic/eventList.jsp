<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Event Management</title>
	<script>
		
	// 全选/取消全选    
	$(document).ready(function() {  
		
	    $("#checkall").click(function(){
	    	
	    	//alert($(this).attr("checked")=="checked");
	    	 if($(this).attr("checked") == "checked"){// 全选    
	    		
	                $("input[type=checkbox][name=checkbox]").each(function(){   
	                	
	                        $(this).attr("checked", true);    
	                    });    
	            } else {// 取消全选    
	            	
	                $("input[type=checkbox][name=checkbox]").each(function(){    
	                    $(this).attr("checked", false);    
	                });    
	            }    
	        });    
	});  
	
	</script>
</head>

<body>
	<h4>Event List</h4>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form action="">
	<a class="btn" href="basic/create">Create Event</a><a class="btn" href="basic/sycnToAgent">SYCN to Agent</a>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><input type="checkbox" name="checkbox" id="checkall" /></th><th>EVENT_ID</th><th>EVENT_NUMBER</th><th>EVENT_NAME</th><th>FUNCNAME</th><th>EVENT_DES</th><th>CHANGED_BY</th><th>LAST_CHANGE_TIME</th><th>Action</th></tr></thead>
		<tbody>
		<c:forEach items="${events}" var="event">
			<tr>
			   <td><input type="checkbox" name="checkbox" id="${event.EVENT_ID}" /></td>
				<td>${event.EVENT_ID}</td>
				<td>${event.EVENT_NUMBER}</td>
				<td>${event.EVENT_NAME}</td>
				<td><a href="basic/view/${event.FUNCNAME}">${event.FUNCNAME}</a></td>
				<td>${event.EVENT_DES}</td>
				<td>${event.CHANGED_BY}</td>
				<td>${event.LAST_CHANGE_TIME}</td>
				<td>
	    		 <a href="delete/${event.EVENT_ID}"><i class="icon-remove" title="delete"></i></a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
</body>
</html>
