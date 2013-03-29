<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Znajdź przejazd</h2>

	<div class="">
	<form:form method="post" action="myForm" commandName="routeSimpleInput">
		<form:input path="locationSource" id="fieldLocationSource" />
		<form:errors path="locationSource" />
	    
		<form:input path="locationDestination" id="fieldLocationDestination" />
		<form:errors path="locationDestination" />
		
		<div class="btn-group">
		  <button class="btn active">Pasażer</button>
		  <button class="btn">Kierowca</button>
		</div>
		
		<input type="submit" value="Search" class="btn btn-primary" >
	</form:form>
	</div>
	
<script src="http://maps.google.com/maps/api/js?sensor=false&libraries=places" type="text/javascript"></script>
<script> 
   function initialize() {
     var input = document.getElementById('fieldLocationSource');
     var autocomplete = new google.maps.places.Autocomplete(input);
	
     google.maps.event.addListener(autocomplete, 'place_changed', function() {
       infowindow.close();
       var place = autocomplete.getPlace();
/*       if (place.geometry.viewport) {
         map.fitBounds(place.geometry.viewport);
       } else {
         map.setCenter(place.geometry.location);
         map.setZoom(17);  // Why 17? Because it looks good.
       }
alert(autocomplete.getBounds());
       var image = new google.maps.MarkerImage(
           place.icon, new google.maps.Size(71, 71),
           new google.maps.Point(0, 0), new google.maps.Point(17, 34),
           new google.maps.Size(35, 35));
       marker.setIcon(image);
       marker.setPosition(place.geometry.location);

       var address = '';
       if (place.address_components) {
         address = [
           (place.address_components[0] &&
            place.address_components[0].short_name || ''),
           (place.address_components[1] &&
            place.address_components[1].short_name || ''),
           (place.address_components[2] &&
            place.address_components[2].short_name || '')].join(' ');
       }

       infowindow.setContent('<div><b>' + place.name + '</b><br>' + address);
       infowindow.open(map, marker);*/
     });

   }
   google.maps.event.addDomListener(window, 'load', initialize);
 </script> 
	
</t:wrapper>