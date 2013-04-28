<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:account>
  
<c:choose>
	<c:when test="${not empty routes}">
		<div class="">
		<h1>Przejazdy:</h1>
	<c:forEach items="${routes}" var="routeWrapper">
<c:set var="route" value="${routeWrapper.route}" />
<c:set var="routeParticipanse" value="${routeWrapper.participanse}" />
  <c:if test="${routeParticipanse.rspvStatus == 'PENDING'}">
      <div class="btn-group">
        <a class="btn dropdown-toggle btn-mini btn-info" data-toggle="dropdown" href="#">
          <i class="icon-envelope icon-white"></i> Zaproszenie
          <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
          <li><a href="<c:url value='/route/participateAccept/${routeParticipanse.id}' />">Zaakceptuj</a></li>
          <li><a href="<c:url value='/route/participateReject/${routeParticipanse.id}' />">Odrzuć</a></li>
        </ul>
      </div>
  </c:if>
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
		</div>
	</c:when>
	<c:otherwise>
		No routes to display
	</c:otherwise>
</c:choose>
  
</t:account>