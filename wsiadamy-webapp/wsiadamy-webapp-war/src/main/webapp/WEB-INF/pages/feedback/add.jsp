<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Wystaw ocenę</h2>
	
	<form:form method="post" action="" commandName="feedbackAddInput">
	    <div>
	    	<label for="fieldValue">Ocena:</label>
			<form:input path="value" id="fieldValue" />
			<form:errors path="value" cssClass="error" />
		</div>
		<div>
	    	<label for="fieldComment">Komentarz (nieobowiązkowo):</label>
			<form:textarea path="comment" id="fieldComment" />
			<form:errors path="comment" cssClass="error" />
		</div>
		<div>
			<input type="submit" value="Wystaw" class="btn btn-primary" >
		</div>
	</form:form>
	
</t:wrapper>