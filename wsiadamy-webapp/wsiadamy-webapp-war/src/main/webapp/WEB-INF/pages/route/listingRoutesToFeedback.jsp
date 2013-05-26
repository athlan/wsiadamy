<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:account>

  <h1>Przejazdy do ocenienia:</h1>
<c:choose>
	<c:when test="${not empty routes}">
  
	<c:forEach items="${routes}" var="route">
		<div class="">

<sec:authorize access="@permissionHelper.hasPermission(#route, 'FeedbackAddRoute')">
	  	<a href="<c:url value="/route/feedback/${route.id}" />" class="btn btn-mini btn-primary">Wystaw ocenę</a>
</sec:authorize>

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