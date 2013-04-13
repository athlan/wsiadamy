<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<h2>Załóż konto</h2>
	
	<form:form method="post" action="" commandName="accountRegisterInput" class="form-horizontal">
		<div class="control-group">
			<label class="control-label" for="fieldEmail">Email address:</label>
			<div class="controls">
				<div class="input-prepend">
	     	 		<span class="add-on"><i class="icon-envelope"></i></span>
					<form:input path="email" id="fieldEmail" placeholder="you@example.com" autocomplete="off" />
				</div>
				<form:errors path="email" cssClass="error" />
			</div>
	    </div>
	    <div class="control-group">
			<label class="control-label" for="fieldPassword">Password:</label>
			<div class="controls">
				<div class="input-prepend">
	     	 		<span class="add-on"><i class="icon-lock"></i></span>
					<form:input path="password" id="fieldPassword" type="password" autocomplete="off" />
				</div>
				<form:errors path="password" cssClass="error" />
			</div>
	    </div>
	    <div class="control-group">
			<label class="control-label" for="fieldPasswordConfirmation">Re-type password:</label>
			<div class="controls">
				<div class="input-prepend">
	     	 		<span class="add-on"><i class="icon-refresh"></i></span>
					<form:input path="passwordConfirmation" id="fieldPasswordConfirmation" type="password" autocomplete="off" />
				</div>
				<form:errors path="passwordConfirmation" cssClass="error" />
			</div>
	    </div>
	    <div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
	    		<button class="btn btn-success"><i class="icon-user icon-white"></i> Zarejestruj się</button>
    		</div>
   		</div>
	</form:form>
	
<script>
$(function() {
	$('button.btn-success').bind('click', function() {
		$(this).addClass('disabled').attr('disabled', true);
		$(this).parents('form').submit();
	});
});
</script>
	
</t:wrapper>