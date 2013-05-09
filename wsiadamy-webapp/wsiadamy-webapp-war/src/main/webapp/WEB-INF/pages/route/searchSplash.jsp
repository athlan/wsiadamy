<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:wrapper>
  
  <div class="map" id="map_canvas"></div>
  
  <form:form method="get" action="" commandName="routeSearchSimpleInput" class="form-search-route">
    <a href="<c:url value="/route/add" />" class="btn btn-success routeAdd"><i class="icon-plus icon-white"></i> Dodaj przejazd</a>
    <h2>Szukaj przejazdu</h2>
    
    <div class="row">
      <div class="span3">
        <form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." class="input-block-level locationAutocomplete locationSoure" />
        <form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" class="locationAutocompleteCoords" />
        <form:errors path="locationSource" />
      </div>
      
      <div class="span3">
        <form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." class="input-block-level locationAutocomplete locationDestination" />
        <form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" class="locationAutocompleteCoords" />
        <form:errors path="locationDestination" />
      </div>
    </div>
    
    <div class="row">
      <div class="span3">
        <div class="btn-group perspective">
          <button class="btn active" data-field-check="">Pasażer</button>
          <button class="btn" data-field-check="">Kierowca</button>
        </div>
      </div>
      <div class="span3">
        <button class="btn btn-large btn-primary routeSearch"><i class="icon-road icon-white"></i> Szukaj przejazdu</button>
      </div>
    </div>
  </form:form>
  
<style>
.locationSoure {
	background: url(${pageContext.request.contextPath}/static/img/pin-red-mini.png) no-repeat center left;
}
.locationDestination {
	background: url(${pageContext.request.contextPath}/static/img/pin-red-mini.png) no-repeat center left;
}
.map {
  background: #eaeaea;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}
.routeAdd {
  float: right;
  margin-top: 16px;
}
.routeSearch {
  float: right;
}
.form-search-route {
  max-width: 600px;
  padding: 19px 29px 29px;
  /*margin: 0 auto 20px;
  float: none;*/
  background-color: #fff;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 5px;
     -moz-border-radius: 5px;
          border-radius: 5px;
  -webkit-box-shadow: 0 0 40px rgba(0,0,0,.3);
     -moz-box-shadow: 0 0 40px rgba(0,0,0,.3);
          box-shadow: 0 0 40px rgba(0,0,0,.3);
}

.form-search-route {
  height: 200px;
  position: absolute;
  left: 50%;
  top: 50%; 
  margin-left: -300px;
  margin-top: -100px;
}

.form-search-route .form-signin-heading,
.form-search-route .checkbox {
  margin-bottom: 10px;
}
.form-search-route input[type="text"] {
  font-size: 16px;
  height: auto;
  padding: 7px 9px;
}
</style>
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
	    $('.locationAutocomplete').each(function() {
	      initLocationService($(this));
	    });
	});
  
  var map;
  
	function initialize() {
		directionsDisplay = new google.maps.DirectionsRenderer();
		var centerPoint = new google.maps.LatLng(52.160455, 19.050293);
		var mapOptions = {
			zoom: 9,
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
		var request = {
		    origin: $('#fieldLocationSourceCoords').val(),
		    destination: $('#fieldLocationDestinationCoords').val(),
		    travelMode: google.maps.DirectionsTravelMode.DRIVING
		};
		
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
			}
		});
	}
</script>
<script>
$(function() {
  $('.perspective .btn').click(function() {
    var o = $(this);
    $('.btn', o.parents('.btn-group')).removeClass('active');
    o.addClass('active');
    
    return false;
  });
});
</script>
</t:wrapper>