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
	<h1>Main</h1>
	
	<form method="get" action="entities.html">
     	<button class="task_button" type="submit" value="Submit">List entities</button>
	</form>
	<br>
	<form method="get" action="problems.html">
     	<button class="task_button" type="submit" value="Submit">Detect problems</button>
	</form>
	<br>
	<form method="get" action="plots.html">
     	<button class="task_button" type="submit" value="Submit">Show plots</button>
	</form>
		<br>
		<br>
	<form method="get" action="about.html">
     	<button class="task_button" type="submit" value="Submit">About</button>
	</form>

</body>
</html>

