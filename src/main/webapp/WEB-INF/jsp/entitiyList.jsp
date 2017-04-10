<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="spt" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="spf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
<meta name="robots" content="noindex,nofollow" />
<title> EMC Task. Pavlov Vlad </title>
</head>


<body>
	<h1>Entities. ${type}</h1>
	<div class="important_area">
			<table>
			<tr>
			<th> ID </th> <th> Name </th>
			</tr>
				<c:forEach var="entity" items="${entities}">
					<tr>
							<td>
								${entity.id}
							</td>
							<td>
								${entity.name}							
							</td> 
					</tr>
				</c:forEach>
		</table>
	</div>
	
	<form method="get" action="/emcdirectors/main.html">
     	<button class="basic_button" type="submit" value="Submit">Back to main page</button>
	</form>

</body>
</html>

