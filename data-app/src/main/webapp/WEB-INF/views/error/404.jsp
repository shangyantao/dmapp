<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>404 - Not found page </title>
</head>

<body>
<div>
	<div><h1> Not found page .</h1></div>
	<div><a href="<c:url value="/"/>">return index page</a></div>
</div>
</body>
</html>