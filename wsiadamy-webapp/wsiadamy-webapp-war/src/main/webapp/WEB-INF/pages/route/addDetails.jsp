<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>=
	<h2>Dodaj przejazd</h2>
	
	<div class="row">
		<div class="span5">
		<form:form method="post" action="" commandName="routeInput">
		    <div>
		    	<label for="fieldRouteLength">Długość trasy:</label>
				<form:input path="routeLength" id="fieldRouteLength" />
				<form:errors path="routeLength" cssClass="error" />
			</div>
			<div>
				<label for="fieldCarCombustion">Spalanie Twojego samochodu na 100km:</label>
				<form:input path="carCombustion" id="fieldCarCombustion" />
				<form:errors path="carCombustion" cssClass="error" />
			</div>
			<div>
				<label for="fieldFuelPrice">Cena benzyny za litr:</label>
				<form:input path="fuelPrice" id="fieldFuelPrice" />
				<form:errors path="fuelPrice" cssClass="error" />
			</div>
			<div>
				<label for="fieldTotalPrice">Całkowity koszt trasy:</label>
				<form:input path="totalPrice" id="fieldTotalPrice" />
				<form:errors path="totalPrice" cssClass="error" />
			</div>
			<div>
				<label for="fieldParticipansModeration">Ustawienia dołączania:</label>
				<label><form:radiobutton path="participansModeration" value="0" id="fieldParticipansModeration" /> Pozwól dołączać każdemu</label>
				<label><form:radiobutton path="participansModeration" value="1" id="fieldParticipansModeration" /> Chcę zdecydować, kogo wybiorę do podróży spośród chętnych uczestników</label>
				<form:errors path="participansModeration" cssClass="error" />
			</div>
			
			<div>
				<input type="submit" value="Dodaj" class="btn btn-primary" >
			</div>
		</form:form>
		</div>
		<div class="span7">
			<div id="map_canvas" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
	
	<form:form method="post" action="" commandName="routeInput">
		<form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" />
		<form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" />
<c:forEach items="${routeAddInput.waypointsCoords}" var="waypointCoord">
    <input name="waypointsCoords['${waypointCoord.key}']" type="hidden" value="${waypointCoord.value}" class="waypoint" />
</c:forEach>
	</form:form>
	
<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('#fieldRouteLength, #fieldCarCombustion, #fieldFuelPrice').bind('change keyup', function() {
			var length = parseFloat($('#fieldRouteLength').val());
			var conbustion = parseFloat($('#fieldCarCombustion').val());
			var fuelPrice = parseFloat($('#fieldFuelPrice').val());
			
			var totalPrice = length * conbustion * fuelPrice / 100;
			
			$('#fieldTotalPrice').val(Math.ceil(totalPrice));
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
		
		calcRoute();
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
	var directionDisplay;
	var directionsService = new google.maps.DirectionsService();
	var map;
	
	function calcRoute() {
    var requestWaypoints = [];
    
    $('.waypoint').each(function() {
      var point = $(this).val();
      var coords = point.split(' ');
      
      if(coords.length != 2)
        return;
      
      requestWaypoints.push({
        location: new google.maps.LatLng(coords[0], coords[1]),
        stopover: false
      });
    });
    
		var request = {
		    origin: $('#fieldLocationSourceCoords').val(),
		    destination: $('#fieldLocationDestinationCoords').val(),
		    waypoints: requestWaypoints,
		    travelMode: google.maps.DirectionsTravelMode.DRIVING
		};
		
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
				
				var route = response.routes[0];
				
				var routeDistance = route.legs[0].distance.value / 1000;
				$('#fieldRouteLength').val(routeDistance.toFixed(2));
			}
		});
	}
</script> 
	
</t:wrapper>