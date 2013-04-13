<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Załóż konto</h2>
	
	<form:form method="post" action="" commandName="accountRegisterInput">
		<div>
			<label for="fieldEmail">Email address:</label>
			<form:input path="email" id="fieldEmail" placeholder="you@example.com" autocomplete="off" />
			<form:errors path="email" cssClass="error" />
	    </div>
	    <div>
			<label for="fieldPassword">Password:</label>
			<form:input path="password" id="fieldPassword" type="password" autocomplete="off" />
			<form:errors path="password" cssClass="error" />
	    </div>
	    <div>
			<label for="fieldPasswordConfirmation">Re-type password:</label>
			<form:input path="passwordConfirmation" id="fieldPasswordConfirmation" type="password" autocomplete="off" />
			<form:errors path="passwordConfirmation" cssClass="error" />
	    </div>
	    
		<input type="submit" value="Zarejestruj" class="btn btn-success" >
	</form:form>
	
</t:wrapper>