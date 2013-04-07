<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Dodaj przejazd</h2>

	<div class="row">
		<div class="span5">
		<form:form method="post" action="" commandName="routeAddInput">
			<div>
				<form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." />
				<form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" />
				<form:errors path="locationSource" cssClass="error" />
		    </div>
		    
		    <div>
				<form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." />
				<form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" />
				<form:errors path="locationDestination" cssClass="error" />
			</div>
			
		    <div>
			<c:forEach items="${routeAddInput.waypoints}" var="waypoint">
				<input name="waypoints[]" type="text" value="${waypoint}"  placeholder="Przez..." />
			</c:forEach>
			
			<c:forEach items="${routeAddInput.waypointsCoords}" var="waypointCoord">
				<input name="waypointsCoords[]" type="text" value="${waypointCoord}"/>
			</c:forEach>
			</div>
			
		    <div>
		    	<label for="fieldSeats">Data wyjazdu:</label>
				<form:input path="dateDeparture" id="fieldDateDeparture" />
				<form:errors path="dateDeparture" cssClass="error" />
			</div>
			
		    <div>
		    	<label for="fieldSeats">Liczba dostępnych miejsc:</label>
				<form:input path="seats" id="fieldSeats" />
				<form:errors path="seats" cssClass="error" />
			</div>
			
		    <div>
		    	<form:input path="routeLine" type="hidden" id="fieldRouteLine" />
				<input type="submit" value="Dalej" class="btn btn-success" >
			</div>
			
			<div class="routeSummary">
				<p class="distance">Trasa <span></span></p>
			</div>
			
		</form:form>
		</div>
		<div class="span7">
			<div id="map_canvas" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
	
<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('#fieldLocationSource').bind('keypress', function(e) {
			if(e.keyCode == 13) {
				e.preventDefault();
				//$('#fieldLocationDestination').focus();
			}
		});
		
		$('#fieldLocationDestination').bind('keypress', function(e) {
			if(e.keyCode == 13) {
				e.preventDefault();
			}
		});
		
    	$("#fieldDateArrival").datepicker({
    		dateFormat: 'dd.mm.yy'/*,
    		minDate: new Date()*/
    	});
	});
	
	function initialize() {
		directionsDisplay = new google.maps.DirectionsRenderer();
		var centerPoint = new google.maps.LatLng(52.160455, 19.050293);
		var mapOptions = {
			zoom: 5,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			center: centerPoint,
			disableDefaultUI: true,
			streetViewControl: false
		}
		map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
		directionsDisplay.setMap(map);
	  	
		var fieldLocationSource = document.getElementById('fieldLocationSource');
		var autocompleteFieldLocationSource = new google.maps.places.Autocomplete(fieldLocationSource, {
			types: ['(cities)'],
		});
		
		google.maps.event.addListener(autocompleteFieldLocationSource, 'place_changed', function() {
			var place = autocompleteFieldLocationSource.getPlace();
			
			$('#fieldLocationSourceCoords').val(place.geometry.location.jb + " " + place.geometry.location.kb);
			//console.log(place.geometry.location.jb);
			//console.log(place.geometry.location.kb);
			calcRoute();
		});
		
		var fieldLocationDestination = document.getElementById('fieldLocationDestination');
		var autocompleteFieldLocationDestination = new google.maps.places.Autocomplete(fieldLocationDestination, {
			types: ['(cities)'],
		});
		
		google.maps.event.addListener(autocompleteFieldLocationDestination, 'place_changed', function() {
			var place = autocompleteFieldLocationDestination.getPlace();
			
			$('#fieldLocationDestinationCoords').val(place.geometry.location.jb + " " + place.geometry.location.kb);
			calcRoute();
		});
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	var directionDisplay;
	var directionsService = new google.maps.DirectionsService();
	var map;
	
	function calcRoute() {
		var request = {
		    origin: $('#fieldLocationSourceCoords').val(),
		    destination: $('#fieldLocationDestinationCoords').val(),
		    travelMode: google.maps.DirectionsTravelMode.DRIVING
		};
		
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
				
				var route = response.routes[0];
				
				$('.routeSummary .distance span').html(route.legs[0].distance.text);
				
				var lastPoint = route.overview_path[0];
				var points = [];
				
				for(var i = 0; i < route.overview_path.length; i++) {
					var distance = Math.pow(lastPoint.jb - route.overview_path[i].jb, 2) + Math.pow(lastPoint.kb - route.overview_path[i].kb, 2);
				  
					if(i > 0 && distance < 0.0001)
						continue;
					
					lastPoint = route.overview_path[i];
					points.push(route.overview_path[i].jb + " " + route.overview_path[i].kb);
				}
				
				$('#fieldRouteLine').val(points.join(","));
			}
		});
	}
</script> 
	
</t:wrapper>