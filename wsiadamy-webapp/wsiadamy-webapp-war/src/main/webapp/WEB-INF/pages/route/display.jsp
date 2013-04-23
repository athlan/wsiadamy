<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:wrapper>
	<h2>Trasa</h2>

	<div class="row">
		<div class="span5">
			Test route
			<a href="<c:url value="/route/participate/${route.id}" />" class="btn">Wsiadaj!</a>
<sec:authorize access="hasPermission(#route, 'RouteRemove')">
			<a href="<c:url value="/route/remove/${route.id}" />" class="btn btn-danger btn-mini">Usuń1</a>
</sec:authorize>
<sec:authorize url="/route/remove/${route.id}">
			<a href="<c:url value="/route/remove/${route.id}" />" class="btn btn-danger btn-mini">Usuń2</a>
</sec:authorize>
		</div>
		<div class="span7">
			<div id="map_canvas" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
</t:wrapper>