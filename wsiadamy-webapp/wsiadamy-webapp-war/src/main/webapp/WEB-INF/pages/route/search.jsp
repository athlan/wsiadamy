<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Szukaj przejazdu</h2>

	<div class="row">
		<div class="span12">
		<form:form method="post" action="" commandName="routeAddInput">
			<div>
				<form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." />
				<form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" />
				<form:errors path="locationSource" />
		    </div>
		    
		    <div>
				<form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." />
				<form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" />
				<form:errors path="locationDestination" />
			</div>
			
		    <div>
				<input type="submit" value="Search" class="btn btn-large btn-primary" >
				<a href="<c:url value='/route/add' />" value="Add" class="btn btn-large btn-primary" >				
			</div>
			
		</form:form>
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
	});
	
	function initialize() {
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
</script> 
	
</t:wrapper>