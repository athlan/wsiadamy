<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:wrapper>
	<ul class="nav nav-tabs">
	  <li><a href="<c:url value='/account/routes' />">Przejazdy</a></li>
	  <li><a href="<c:url value='/account/routesCreated' />">Moje dodane</a></li>
	  <li><a href="<c:url value='/account/routesHistory' />">Historia</a></li>
	  <li><a href="<c:url value='/account/data' />">Edytuj profil <span class="label label-important">Important</span></a></li>
	  <li><a href="<c:url value='/account/stats' />">Statystyka</a></li>
	</ul>
	<jsp:doBody />
</t:wrapper>