<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:wrapper>
  <jsp:attribute name="cssFragment">
    <link href="${pageContext.request.contextPath}/static/css/pages/searchSplash.css" rel="stylesheet">
  </jsp:attribute>
  <jsp:body>
  <div class="map" id="map_canvas"></div>
  
  <form:form method="get" action="" commandName="routeSearchSimpleInput" class="form-search-route">
    <a href="<c:url value="/route/add" />" class="btn btn-success routeAdd"><i class="icon-plus icon-white"></i> Dodaj przejazd</a>
    <h2>Szukaj przejazdu</h2>
    
    <div class="row">
      <div class="span3">
        <form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." class="input-block-level location locationSoure locationAutocomplete" />
        <form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" class="locationAutocompleteCoords" />
        <form:errors path="locationSource" />
      </div>
      
      <div class="span3">
        <form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." class="input-block-level location locationDestination locationAutocomplete" />
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
      inputObject.next('.locationAutocompleteCoords').val(place.geometry.location.jb + " " + place.geometry.location.kb);
      
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
    initStartLocation();
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
  
  function initStartLocation() {
    if (navigator.geolocation) {
      $('#fieldLocationSource').addClass('loading');
      
      navigator.geolocation.getCurrentPosition(function(position) {
        console.log(position);
        
        $('#fieldLocationSourceCoords').val(position.coords.latitude + '' + position.coords.longitude);
        //$('#fieldLocationSource').val(position.coords.latitude + '' + position.coords.longitude);
        
        var placeSearch = new google.maps.Geocoder(map);
        var placeSearchRequest = {
          location: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
          //types: ['(cities)'],
        };
        placeSearch.geocode(placeSearchRequest, function(geocoderResult, geocoderStatus) {
          $('#fieldLocationSource').removeClass('loading');
          
          if(geocoderStatus == google.maps.GeocoderStatus.OK) {
            for(var i = 0; i < geocoderResult.length; ++i) {
              var item = geocoderResult[i];
              
              if(item.address_components[0].types[0] == 'administrative_area_level_3') {
                $('#fieldLocationSource').val(item.formatted_address);
                console.log(item.formatted_address);
              }
            }
          }
        });
      }, function(errorMessage) {
        $('#fieldLocationSource').removeClass('loading');
        console.log(errorMessage);
      });
    }
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
  </jsp:body>
</t:wrapper>