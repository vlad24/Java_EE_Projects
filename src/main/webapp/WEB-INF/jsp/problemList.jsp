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
<title>EMC Task. Pavlov Vlad</title>
</head>


<body>
	<h1>Problems</h1>
	<div class="problem_area">
		<table>
			<tr>
				<th > Crux </th>
				<c:set var="sampleProblem" value="${problems[0]}"/>
				<c:if test="${not empty sampleProblem.place}">
						<th>Place</th>
				</c:if>
				<c:if test="${not empty sampleProblem.time}">
						<th>Time</th>
				</c:if>
				<c:if test="${not empty sampleProblem.reason}">
						<th>Reason</th>
				</c:if>
				<c:if test="${not empty sampleProblem.meta}">
						<th>Help</th>
				</c:if>
			</tr>
			<c:forEach var="problem" items="${problems}">
				<tr>
					<td class="problem">${problem.crux}</td>
					<c:if test="${not empty problem.place}">
						<td>${problem.place}</td>
					</c:if>
					<c:if test="${not empty problem.time}">
						<td>${problem.time}</td>
					</c:if>
					<c:if test="${not empty problem.reason}">
						<td class="bounded">${problem.reason}</td>
					</c:if>
					<c:if test="${not empty problem.meta}">
						<td>
							<form method="get" action="#">
								<input name="id" value="${problem.meta}" type="hidden" />
								<button class="small_button" type="submit" value="Submit">Analyze</button>
							</form>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>

	<form method="get" action="/emcdirectors/main.html">
		<button class="basic_button" type="submit" value="Submit">Back
			to main page</button>
	</form>

</body>
</html>

