<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
<meta name="robots" content="noindex,nofollow" />
<title>EMC Task. Pavlov Vlad</title>
<script src="http://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script type="text/javascript">

window.onload = function () {
	var dateFormat    = "DD/MM HH:mm"
	var noticeColor   = "#5dff57"
	var warningColor  = "#ffbf48"
	var dangerColor   = "#BB0000"
	var lt            = 2
	alert			  = document.getElementById("plotsParams")
		
	var directorStatPointsFromServer = document.getElementById("dps").value;
	var sgStatPointsFromServer       = document.getElementById("gps").value;
	var dPoints  =  JSON.parse(directorStatPointsFromServer);
	var sgPoints =  JSON.parse(sgStatPointsFromServer);
	var dp7 = []; var dp8 = [];	var dp9 = [];
	var gp5 = []; var gp6 = [];	var gp7 = [];
	for (var i = 0; i < dPoints.length; i++){
		dp7.push( { x : new Date(dPoints[i].timePoint.timestamp), y: dPoints[i].bucket7Load	} );
		dp8.push( { x : new Date(dPoints[i].timePoint.timestamp), y: dPoints[i].bucket8Load } );
		dp9.push( { x : new Date(dPoints[i].timePoint.timestamp), y: dPoints[i].bucket9Load	} );
	}
	for (var i = 0; i < sgPoints.length; i++){
		gp5.push( { x : new Date(sgPoints[i].timePoint.timestamp), y: sgPoints[i].bucket5ReadRt + sgPoints[i].bucket5WriteRt} );
		gp6.push( { x : new Date(sgPoints[i].timePoint.timestamp), y: sgPoints[i].bucket6ReadRt + sgPoints[i].bucket6WriteRt} );
		gp7.push( { x : new Date(sgPoints[i].timePoint.timestamp), y: sgPoints[i].bucket7ReadRt + sgPoints[i].bucket7WriteRt} );
	}

	
	var upperChart = new CanvasJS.Chart("upperChart", {
			theme: "theme2",
	        animationEnabled: true,
			title:	{ text: "Director Statistic", fontSize: 24 },
			toolTip:{ shared:true  },
			axisX:	{ gridColor: "silver", tickColor: "black", valueFormatString: dateFormat},                        
			axisY: 	{ gridColor: "silver", tickColor: "black"},
			legend:	{verticalAlign: "center", horizontalAlign: "right"},
			data: [	
			     	{dataPoints: dp7, name: "Bucket 7 load", type: "line", showInLegend: true, lineThickness: lt,     color: noticeColor },
					{dataPoints: dp8, name: "Bucket 8 load", type: "line", showInLegend: true, lineThickness: lt,     color: warningColor},
					{dataPoints: dp9, name: "Bucket 9 load", type: "line", showInLegend: true, lineThickness: lt + 2, color: dangerColor }
				]
			
		}
	);
	
	var lowerChart = new CanvasJS.Chart("lowerChart", {
		theme: "theme2",
        animationEnabled: true,
		title:	{ text: "Storage Group Statistic", fontSize: 24 },
		toolTip:{ shared:true  },
		axisX:	{ gridColor: "silver", tickColor: "black", valueFormatString: dateFormat},                        
		axisY: 	{ gridColor: "silver", tickColor: "black"},
		legend:	{verticalAlign: "center", horizontalAlign: "right"},
		data: [	
		     	{dataPoints: gp5, name: "Bucket5 R+W RT", type: "line", showInLegend: true, lineThickness: lt,     color: noticeColor },
				{dataPoints: gp6, name: "Bucket6 R+W RT", type: "line", showInLegend: true, lineThickness: lt,     color: warningColor},
				{dataPoints: gp7, name: "Bucket7 R+W RT", type: "line", showInLegend: true, lineThickness: lt + 2, color: dangerColor }
		]
		
	}
);

upperChart.render();
lowerChart.render();
}
</script>
</head>



<body>
	<h1>Plots</h1>

	<div class="input_area">
		<form:form id="plotsParams" modelAttribute="plotRequest" method="get" action="showPlot.html">
		<p>
			<label>Director:</label>
			 <form:select id="directorId" path="directorId" name="directorId">
				<c:forEach items="${directors}" var="director">
					<option value="${director.id}">${director.name}</option>
				</c:forEach>
			</form:select>
			<label>from:</label>   <input name="tFromDir" path="tFromDir" type="datetime-local"     value="${minDate}"> 
			<label>to:  </label>   <input name="tToDir"   path="tToDir"   type="datetime-local"     value="${maxDate}">
		</p>
			 <br>
		<p> 
			 <label>Storage Group:</label> 
			 <select id="sgId" path="sgId" name="sgId">
				<c:forEach items="${storageGroups}" var="storageGroup">
					<option value="${storageGroup.id}">${storageGroup.name}</option>
				</c:forEach>
			</select> 
			<label>from:</label>   <input name="tFromSG" path="tFromSG" type="datetime-local"     value="${minDate}"> 
			<label>to:  </label>   <input name="tToSG"   path="tToSG"   type="datetime-local"     value="${maxDate}">
		</p>
		
		</form:form>
	</div>


	
	<div id="upperChart" style="height: 300px; width: 100%;"></div>
	<div id="lowerChart" style="height: 300px; width: 100%;"></div>

	
	<button class="basic_button" form="plotsParams" type="submit" value="Submit">Show</button>
	<form method="get" action="/emcdirectors/main.html">
     	<button class="basic_button" type="submit">Back to main page</button>
	</form>


	<input id="dps" type="hidden" value='${directorStatPoints}' />
	<input id="gps" type="hidden" value='${sgStatPoints}'       />	
</body>
</html>

