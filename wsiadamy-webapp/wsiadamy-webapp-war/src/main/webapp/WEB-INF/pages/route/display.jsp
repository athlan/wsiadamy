<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:wrapper>
	<h2>Trasa</h2>
	
	<div class="row">
		<div class="span4">
			User:
      <ul>
        <li><c:out value="${route.owner.userData.firstname}" /> <c:out value="${route.owner.userData.lastname}" /></li>
        <li><c:out value="${route.owner.userData.contactPhone}" /></li>
<c:if test="${not empty route.owner.userData.facebookId}">
        <li><a href="javascript:sendFacebookPrivateMessage();"><c:out value="${route.owner.userData.facebookId}" /></a></li>
</c:if>
      </ul>
      
<sec:authorize access="@permissionHelper.hasPermission(#route, 'FeedbackAddRoute')">
 	<a href="<c:url value="/route/feedback/${route.id}" />" class="btn btn-mini btn-primary">Wystaw ocenę</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#route, 'RouteParticipateAdd')">
	<a href="<c:url value="/route/participate/${route.id}" />" class="btn btn-primary">Wsiadaj!</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateResignation')">
	<a href="<c:url value="/route/participateResignation/${routeParticipanse.id}" />" class="btn">Rezygnuj z przejazdu</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateReviewJoin')">
    <div class="btn-group">
      <a class="btn dropdown-toggle btn-info" data-toggle="dropdown" href="#">
        <i class="icon-envelope icon-white"></i> Zaproszenie
        <span class="caret"></span>
      </a>
      <ul class="dropdown-menu">
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateAccept')">
        <li><a href="<c:url value='/route/participateAccept/${routeParticipanse.id}' />">Zaakceptuj</a></li>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateReject')">
        <li><a href="<c:url value='/route/participateReject/${routeParticipanse.id}' />">Odrzuć</a></li>
</sec:authorize>
      </ul>
    </div>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateCancel')">
	<a href="<c:url value="/route/participateCancel/${routeParticipanse.id}" />" class="btn">Anuluj zaproszenie</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#route, 'RouteRemove')">
  <a href="<c:url value="/route/remove/${route.id}" />" class="btn btn-danger btn-mini">Usuń trasę</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#route, 'RouteEdit')">
  <a href="<c:url value="/route/editDetails/${route.id}" />" class="btn btn-primary">Edytuj trasę</a>
</sec:authorize>
		</div>
		<div class="span8">
			Test route
      <ul>
        <li>From: <c:out value="${route.waypointSource.name}" /></li>
<c:forEach var="item" items="${products}">
        <li>via: <c:out value="${item.name}" /></li>
</c:forEach>
        <li>To: <c:out value="${route.waypointDestination.name}" /></li>
      </ul>
		</div>
		<div class="span7">
			<div id="map_canvas" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
	
<c:if test="${not empty route.owner.userData.facebookId}">
<script>
  window.fbAsyncInit = function() {
    // init the FB JS SDK
    FB.init({
      appId      : '178030972286479',                        // App ID from the app dashboard
      channelUrl : '//WWW.YOUR_DOMAIN.COM/channel.html', // Channel file for x-domain comms
      status     : true,                                 // Check Facebook Login status
      xfbml      : true                                  // Look for social plugins on the page
    });
  };
  
  function sendFacebookPrivateMessage() {
  // Additional initialization code such as adding Event Listeners goes here
    FB.ui({
        method: 'send',
        to: [<c:out value="${route.owner.userData.facebookId}" />],
        //display: 'page',
        //name: 'People Argue Just to Win',
        //link: 'http://www.nytimes.com/2011/06/15/arts/people-argue-just-to-win-scholars-assert.html',
    });
    }
  
  // Load the SDK asynchronously
  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/all.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
</c:if>

</t:wrapper>