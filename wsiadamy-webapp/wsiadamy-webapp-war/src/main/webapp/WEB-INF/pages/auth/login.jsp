<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
  <jsp:attribute name="cssFragment">
    <link href="${pageContext.request.contextPath}/static/css/pages/login.css" rel="stylesheet">
  </jsp:attribute>
  <jsp:body>
<form class="form-signin" action="<c:url value='j_spring_security_check' />" method="POST">
  <h2 class="form-signin-heading">Please sign in</h2>

<c:if test="${not empty param.authfailed && not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
  <div class="alert alert-error">
    ${SPRING_SECURITY_LAST_EXCEPTION.message}
  </div>
</c:if>
  
  <input type="hidden" name="spring-security-redirect" value="<c:out value="${param.r}" />">
  <input type="text" name="j_username" class="input-block-level" id="fieldUsername" value="${SPRING_SECURITY_LAST_USERNAME}" placeholder="Email address">
  <input type="password" name="j_password" class="input-block-level" id="fieldPassword" value="" placeholder="Password">
  <!--
  <label class="checkbox">
    <input type="checkbox" name="_spring_security_remember_me" value="remember-me"> Remember me
  </label>
  //-->
  <div>
    <button class="btn btn-large btn-primary btnLocker" type="submit"><i class="icon-road icon-white"></i> Sign in</button>
  </div>
  <div class="alternative"><span>or</span></div>
  <div>
	<a href="<c:url value="/loginFacebook" />" class="btn btn-facebook btn-block btnLocker" type="submit"><i class="icon-facebook-sign icon-white"></i> Log in with Facebook</a>
  </div>
</form>
<script>
$(function() {
  if($('#fieldUsername').val() == '')
    $('#fieldUsername').focus();
  else
    $('#fieldPassword').focus();
});
</script>
  </jsp:body>
</t:wrapper>