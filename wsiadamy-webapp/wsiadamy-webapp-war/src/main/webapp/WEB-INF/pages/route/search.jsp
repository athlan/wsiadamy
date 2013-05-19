<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:wrapper>
  
  <form:form method="get" action="" commandName="routeSearchSimpleInput" class="form-search-route">
    <h2>Szukaj przejazdu</h2>
    
    <div class="row">
      <div class="span3">
        <form:input path="locationSource" id="fieldLocationSource" placeholder="Wyruszam z..." class="input-block-level locationAutocomplete" />
        <form:input path="locationSourceCoords" type="hidden" id="fieldLocationSourceCoords" class="locationAutocompleteCoords" />
        <form:errors path="locationSource" />
      </div>
      
      <div class="span3">
        <form:input path="locationDestination" id="fieldLocationDestination" placeholder="Jadę do..." class="input-block-level locationAutocomplete" />
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
    
    <a href="<c:url value="/route/add" />" class="btn btn-success routeAdd"><i class="icon-plus icon-white"></i> Dodaj przejazd</a>
  </form:form>
  
<style>
.routeSearch {
  float: right;
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
	
	<c:choose>
		<c:when test="${not empty routes}">
			<div class="">
			<h1>Przejazdy:</h1>
		<c:forEach items="${routes}" var="routeWrapper">
<c:set var="route" value="${routeWrapper.route}" />
      <div class="route">
      <a href="<c:url value='/route/get/${route.id}' />">
        Przejazd #<c:out value="${route.id}" />
        z <c:out value="${route.waypointSource.name}" />
        do <c:out value="${route.waypointDestination.name}" />
      </a>
      w dniu <fmt:formatDate value="${route.dateDeparture}" pattern="dd.MM.yyyy" />
      <br />
      Distance source <c:out value="${routeWrapper.distanceSource}" /><br />
      Position source <c:out value="${routeWrapper.positionSource}" /><br />
      Distance destination <c:out value="${routeWrapper.distanceDestination}" /><br />
      Position destination <c:out value="${routeWrapper.positionDestination}" /><br />
      </div>
		</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			ssdcsdc
		</c:otherwise>
	</c:choose>
  
</t:wrapper>