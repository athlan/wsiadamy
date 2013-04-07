<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/jquery-ui/css/ui-lightness/jquery-ui-1.10.2.custom.min.css" rel="stylesheet">
    
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/jquery-ui/js/jquery-ui-1.10.2.custom.min.js"></script>
	
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/static/bootstrap/js/html5shiv.js"></script>
    <![endif]-->
  </head>
  <body>
  	<div class="container">
  	  <h1>Bootstrap starter template</h1>
      <p>Current Locale : ${pageContext.response.locale}</p>
      
<sec:authorize access="isAuthenticated()">
      <p>Current user: <sec:authentication property="principal.username" /></p>
</sec:authorize>
      <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
      
      Text: <spring:message code="welcome.springmvc" text="default text" />
      
      <p>Language : <a href="?language=en">English</a>|<a href="?language=zh_CN">Chinese</a></p>
<jsp:doBody/>

	</div>
	
  </body>
</html>