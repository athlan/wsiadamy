<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:account>

<c:if test="${ feedbackToGiveCount gt 0 }">
	<div>
		Masz ${ feedbackToGiveCount } tras do ocenienia.
		<a href="<c:url value='/account/routesToFeedback' />">Pokaż</a>
	</div>
</c:if>
  
  <h1>Przejazdy:</h1>
<c:choose>
	<c:when test="${not empty routes}">
  
	<c:forEach items="${routes}" var="routeWrapper">
		<div class="">
<c:set var="route" value="${routeWrapper.route}" />
<c:set var="routeParticipanse" value="${routeWrapper.participanse}" />

<sec:authorize access="@permissionHelper.hasPermission(#route, 'FeedbackAddRoute')">
	  	<a href="<c:url value="/route/feedback/${route.id}" />" class="btn btn-mini btn-primary">Wystaw ocenę</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateCancel')">
	  	<a href="<c:url value="/route/participateCancel/${routeParticipanse.id}" />" class="btn btn-mini btn-danger"><i class="icon-envelope icon-white"></i> Anuluj zaproszenie</a>
</sec:authorize>
<sec:authorize access="@permissionHelper.hasPermission(#routeParticipanse, 'RouteParticipateReviewJoin')">
    <div class="btn-group">
      <a class="btn dropdown-toggle btn-mini btn-info" data-toggle="dropdown" href="#">
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

  <c:if test="${route.owner.id == pageContext.request.userPrincipal.principal.id}">
      <span class="label label-info">Kierowca</span>
  </c:if>
  <c:if test="${route.owner.id != pageContext.request.userPrincipal.principal.id}">
      <span class="label">Pasażer</span>
  </c:if>
     <a href="<c:url value='/route/get/${route.id}' />">
       Przejazd #<c:out value="${route.id}" />
       z <c:out value="${route.waypointSource.name}" />
       do <c:out value="${route.waypointDestination.name}" />
     </a>
     w dniu <fmt:formatDate value="${route.dateDeparture}" pattern="dd.MM.yyyy" />
     </div>
	</c:forEach>
		
${ paginatorViewHelper.display(paginator) }

	</c:when>
	<c:otherwise>
		No routes to display
	</c:otherwise>
</c:choose>
  
</t:account>