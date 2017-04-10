<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<title>EMC Task. Pavlov Vlad</title>
<body>
	<h1>EMC Internship Entry Task</h1>
	<div class="impotrant_area">
	<h2> General info </h2>
			Web Application for operating statistics on <emp>FEDirectors</emp> and <emp>StorageGroups</emp> built with:
				<ul>
					<li>Hibernate 5 + Postgres 9.5</li>
					<li>Spring 4.3</li>
					<li>Canvas.js</li>
				</ul>
			The application was tested on Tomcat v8.0 Server.
	<h2> Implementation notes </h2>
			<h4>Basic definitions:</h4>
				It is assumed that typically entities function properly and bad states are rather exceptional for system. <br>
				Current implementation considers a director to be in bad state if is true for director D: <br>
				  <samp> 
				  		(B<sub>7</sub>(D) > AVG<sub>7</sub> * D_COEFFICIENT<sub>7</sub> ) && <br>
				   	    (B<sub>8</sub>(D) > AVG<sub>8</sub> * D_COEFFICIENT<sub>8</sub>) && <br>
				   		(B<sub>9</sub>(D) > AVG<sub>9</sub> * D_COEFFICIENT<sub>9</sub>)    <br>
				  </samp>
				  where B<sub>i</sub>(D) returns number of requests standing in i-th bucket of queue.
		 		  AVG<sub>i</sub> is an average of B<sub>i</sub> for all directors. <br>  
				  Here sensitivity to state "badness" is set by D_COEFFICIENT<sub>i</sub> values.
				  Similar definition is introduced for storage group, however there the key statistic of storage group's bucket is sum of its read and write response times.
				 <samp>
				  ( BW<sub>5</sub>(S) + BR<sub>5</sub>(S) > (AVGW<sub>5</sub> + AVGR<sub>5</sub>) * SG_COFFICIENT<sub>5</sub> ) && <br>
				  ( BW<sub>6</sub>(S) + BR<sub>6</sub>(S) > (AVGW<sub>6</sub> + AVGR<sub>6</sub>) * SG_COFFICIENT<sub>6</sub> ) && <br>
				  ( BW<sub>7</sub>(S) + BR<sub>7</sub>(S) > (AVGW<sub>7</sub> + AVGR<sub>7</sub>) * SG_COFFICIENT<sub>7</sub> )    <br>
				  </samp> 
		  		  <br>
				  where BW<sub>i</sub>(S) and BR<sub>i</sub>(S) return response times of a write and read I/O requests standing in i-th bucket respectively.
				  AVGW<sub>i</sub> (AVGR<sub>i</sub>) is an average of BW<sub>i</sub> and BR<sub>i</sub> for all SGs respectively.<br>
				  Here storage group's sensitivity is set by values SG_COEFFICIENT<sub>7</sub>, SG_COEFFICIENT<sub>6</sub>, SG_COEFFICIENT<sub>5</sub>.
				  These values are injected into system during its initial configuration. <br> 
				  Custom values could be supplied via <emp>appConfig.properties</emp> file.<br>
				  When bad states are defined a potential casual relationship between a director's bad state and storage group's state
				   is established simply if those bad states are observed at the same time point.
			  <h4> Current configuration </h4>
				  	Values provided in supplied <emp>appConfig.properties</emp> are set to those with demonstration purposes.
				  	C values (director coefficients) are set so that not all directors are considered problematic).<br>
				  	However they are decreased (e.g. down to 1) more directors will show up in problematic list.
				  	On the contrary, coefficients for Storage Groups provide rather weak filtering. <br>
				  	Those values are correct as we can see from table below that maximum deviations are not filtered out.
				  	<div >
				  	<table>
				  		<tr>
				  			<th>Metric</th> <th>AVG (roughly ceiled)</th> <th>MAX(roughly ceiled)</th>
				  		</tr>
				  		<tr>
				  			<tr><td> avg(dir. buck.7 load) </td> <td>49</td> <td>9616</td></tr>
				  			<tr><td> avg(dir. buck.8 load) </td> <td>5</td>  <td>3567</td></tr>
				  			<tr><td> avg(dir. buck.9 load) </td> <td>1</td>  <td>35  </td></tr>
				  			<tr><td> avg(sg buck.5 total rt) </td> <td>45</td> <td>5178</td></tr>
				  			<tr><td> avg(sg buck.6 total rt) </td> <td>30</td> <td>6480</td></tr>
				  			<tr><td> avg(sg buck.7 total rt) </td> <td>7</td> <td>4488</td></tr>
				  		
				  	</table>
				  	</div>
			  <br>
			  <strong>JavaDocs contain more formal specification of algorithm used for detection of problematic directors and storage groups.</strong>
			
	<h2> Starting app notes</h2>
		<ul>
			<li>If algorithm should be configured, please refer to <emp>appConfig.properties</emp> file, described above. <br></li>
			<li>Database credentials should be updated in <emp>application-context.xml</emp> file.</li>
		</ul>
	
	<h2> Author </h2>
			Pavlov Vladislav (vlad.pavlov24@gmail.com)
			
	</div>
	
	<form method="get" action="/emcdirectors/main.html">
		<button class="basic_button" type="submit" value="Submit">Back to main page</button>
	</form>

</body>
</html>

