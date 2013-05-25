<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:account>
	<h2>Uzupełnij dane profilu</h2>
	
	<form:form method="post" action="" commandName="accountDataInput" class="form-horizontal">
		<div class="control-group">
			<label class="control-label" for="fieldFirstname">Imię:</label>
			<form:input path="firstname" id="fieldFirstname" placeholder="" autocomplete="off" />
			<form:errors path="firstname" cssClass="error" />
	    </div>
	    <div class="control-group">
			<label class="control-label" for="fieldLastname">Nazwisko:</label>
			<form:input path="lastname" id="fieldLastname" placeholder="" autocomplete="off" />
			<form:errors path="lastname" cssClass="error" />
	    </div>
	    <div class="control-group">
			<label class="control-label" for="fieldBirthday">Data urodzenia:</label>
			<form:input path="birthday" id="fieldBirthday" placeholder="dd.mm.rrrr" autocomplete="off" />
			<form:errors path="birthday" cssClass="error" />
	    </div>
	    <div class="control-group">
			<label class="control-label" for="fieldContactPhone">Numer teleonu:</label>
			<form:input path="contactPhone" id="fieldContactPhone" placeholder="" autocomplete="off" />
			<form:errors path="contactPhone" cssClass="error" />
	    </div>
	    <div class="control-group">
			<label class="control-label" for="">Facebook:</label>
<c:if test="${empty userData.facebookId}">
			<a href="<c:url value="/account/data/joinFacebook" />" class="btn btn-facebook btnLocker" type="submit"><i class="icon-facebook-sign icon-white"></i> Połącz konto z Facebook</a>
</c:if>
<c:if test="${not empty userData.facebookId}">
			<a href="<c:url value="/account/data/joinFacebookRemove" />" class="btn btn-danger btn-mini btnLocker" type="submit">Usuń połączenie konta z facebook</a>
</c:if>
	    </div>
	    
	    <div class="form-actions">
	   		<button class="btn btn-primary btnLocker">Zapisz dane</button>
	    </div>
	</form:form>
<script>
$(function() {
    $("#fieldBirthday").datepicker({
      dateFormat: 'dd.mm.yy'
    });
});
</script>
</t:account>