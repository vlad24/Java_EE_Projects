<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<title>EMC Task. Pavlov Vlad</title>
<body>
	<h1>Error occured</h1>
	<div class="error_area">
		<p>${exceptionReason}</p>
	</div>
	
	<form method="get" action="/emcdirectors/main.html">
     	<button class="basic_button" type="submit" value="Submit">Back to main page</button>
	</form>
	
</body>
</html>

