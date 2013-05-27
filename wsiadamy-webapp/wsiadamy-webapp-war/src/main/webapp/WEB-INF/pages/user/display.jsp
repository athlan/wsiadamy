<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:wrapper>
	<h2>Profil użytkownika</h2>
	
	${user.userData.firstname}
	
<c:forEach items="${feedbacks}" var="feedback">
	${feedback}
</c:forEach>

${ paginatorViewHelper.display(paginator) }

</t:wrapper>