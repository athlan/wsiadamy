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
				<form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." class="locationAutocomplete" />
				<form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" class="locationAutocompleteCoords" />
				<form:errors path="locationSource" cssClass="error" />
		    </div>
		    
		    <div>
				<form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." class="locationAutocomplete" />
				<form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" class="locationAutocompleteCoords" />
				<form:errors path="locationDestination" cssClass="error" />
			</div>
			
      <div class="waypoints">
<c:forEach items="${routeAddInput.waypoints}" var="waypoint">
				<div class="waypoint">
          <input name="waypoints['${waypoint.key}']" type="text" value="${waypoint.value}"  placeholder="Przez..." class="locationAutocomplete" />
          <input name="waypointsCoords['${waypoint.key}']" type="hidden" value="${routeAddInput.waypointsCoords[waypoint.key]}" class="locationAutocompleteCoords" />
          <a href="" class="delete close">&times;</a>
        </div>
</c:forEach>
        <div class="pattern">
					<input name="pattern_waypoints['__index__']" type="text" value=""  placeholder="Przez..." class="locationAutocomplete" />
					<input name="pattern_waypointsCoords['__index__']" type="hidden" value="" class="locationAutocompleteCoords" />
          <a href="" class="delete close">&times;</a>
				</div>
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
  function initLocationService(inputObject) {
    inputObject.bind('keypress', function(e) {
      if(e.keyCode == 13) {
        e.preventDefault();
      }
    });
    
    var autocompleteFieldLocation = new google.maps.places.Autocomplete(inputObject.get(0), {
      types: ['(cities)'],
    });
    
    google.maps.event.addListener(autocompleteFieldLocation, 'place_changed', function() {
      var place = autocompleteFieldLocation.getPlace();
      inputObject.next('.locationAutocompleteCoords').val(place.geometry.location.lb + " " + place.geometry.location.kb);
      calcRoute();
    });
  }
  
	$(function() {
    $(document).on('click', '.waypoints .waypoint .delete', function() {
      $(this).parents('div.waypoint:first').remove();
      return false;
    });
    
    $('.locationAutocomplete').each(function() {
      initLocationService($(this));
    });
		
    $("#fieldDateDeparture").datepicker({
      dateFormat: 'dd.mm.yy'/*,
      minDate: new Date()*/
    });
    
    $(".waypoints .pattern input").bind('focus', function() {
      var pattern = $(this).parents('.pattern').clone();
      pattern.unbind('focus').bind('keypress', function(e) {
        if(e.keyCode == 13) {
          e.preventDefault();
        }
      });
      pattern.removeClass('pattern').addClass('waypoint');
      
      var lastIndex = 0;
      var lastIndexObject = $(".waypoints .waypoint:last input:eq(0)").attr('name');
      if(lastIndexObject)
        lastIndex = lastIndexObject.match(/\['([0-9]+)'\]/)[1];
      
      ++lastIndex;
      
      $(this).parents('.pattern').before(pattern);
      var inputLocation = pattern.find('input:eq(0)');
      inputLocation.focus();
      
      pattern.find('input').each(function() {
        $(this).attr('name', $(this).attr('name').replace(/__index__/, lastIndex).replace(/pattern_/, ''));
      });
      
      initLocationService(inputLocation);
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
      var point = $(this).find('input:eq(1)').val();
      var coords = point.split(' ');
      
      if(coords.length != 2)
        return;
      
      requestWaypoints.push({
        location: new google.maps.LatLng(coords[0], coords[1]),
        stopover: false
      });
    });
    
    var coordsOrigin = $('#fieldLocationSourceCoords').val().split(' ');
    var coordsDestination = $('#fieldLocationDestinationCoords').val().split(' ');
    
		var request = {
		    origin: new google.maps.LatLng(coordsOrigin[0], coordsOrigin[1]),
		    destination: new google.maps.LatLng(coordsDestination[0], coordsDestination[1]),
		    waypoints: requestWaypoints,
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
<style>
  .pattern {
    opacity: 0.7;
  }
  
  .pattern .delete {
    display: none;
  }
  
  .pattern input {
    border: 1px dashed #ccc;
  }
  
  .waypoint {
    position: relative;
  }
  
  .waypoint .delete {
    position: absolute;
    top: 5px; left: 230px;
  }
</style>
	
</t:wrapper>