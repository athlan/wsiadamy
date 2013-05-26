<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:wrapper>

  <h1>Oceń pasażerów:</h1>

<c:forEach items="${participansesToFeedback}" var="participanse">

<sec:authorize access="@permissionHelper.hasPermission(#participanse, 'FeedbackAddParticipanse')">
	<div>
  		<a href="<c:url value="/route/feedbackParticipanse/${participanse.id}" />" class="btn btn-mini btn-primary">Wystaw ocenę</a>
		${ participanse.user.userData.firstname }
		${ participanse.user.userData.lastname }
	</div>
</sec:authorize>
	
</c:forEach>
	
</t:wrapper>