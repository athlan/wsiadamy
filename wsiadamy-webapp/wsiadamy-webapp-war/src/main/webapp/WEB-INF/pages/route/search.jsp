<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
  <h2>Szukaj przejazdu</h2>

  <div class="">
  <form:form method="post" action="" commandName="routeSearchSimpleInput">
    <div>
      <form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." class="locationAutocomplete" />
      <form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" class="locationAutocompleteCoords" />
      <form:errors path="locationSource" />
    </div>
    
    <div>
      <form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." class="locationAutocomplete" />
      <form:input path="locationDestinationCoords" type="hidden" id="fieldLocationDestinationCoords" class="locationAutocompleteCoords" />
      <form:errors path="locationDestination" />
    </div>
    
    <div class="btn-group perspective">
      <button class="btn active" data-field-check="">Pasażer</button>
      <button class="btn" data-field-check="">Kierowca</button>
    </div>
    
    <input type="submit" value="Search" class="btn btn-primary" >
  </form:form>
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
      inputObject.next('.locationAutocompleteCoords').val(place.geometry.location.jb + " " + place.geometry.location.kb);
    });
  }
  
	$(function() {
	    $('.locationAutocomplete').each(function() {
	      initLocationService($(this));
	    });
	});
	
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