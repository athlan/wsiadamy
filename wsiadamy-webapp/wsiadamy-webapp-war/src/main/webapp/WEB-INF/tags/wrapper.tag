<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="scriptsFragment" fragment="true" %>
<%@attribute name="cssFragment" fragment="true" %>
<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" scope="request" />

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
    <link href="${pageContext.request.contextPath}/static/css/common.css" rel="stylesheet">
    <jsp:invoke fragment="cssFragment"/>
    
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery-ui/js/jquery-ui-1.10.2.custom.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery-ui-timepicker-addon.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <jsp:invoke fragment="scriptsFragment"/>
	
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="${pageContext.request.contextPath}/static/bootstrap/js/html5shiv.js"></script>
    <![endif]-->
  </head>
  <body>
  	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="<c:url value="/" />">wsiadamy.pl</a>
          <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
<sec:authorize access="isAuthenticated()">
              Logged in as <a href="<c:url value="/account/routes" />" class="navbar-link"><sec:authentication property="principal.username" /></a> | <a href="<c:url value="/logout" />">Sign out</a>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
              <a href="<c:url value="/login" />" class="navbar-link">Log in</a> or <a href="<c:url value="/register" />" class="navbar-link">register now</a>.
</sec:authorize>
            </p>
            <ul class="nav">
<sec:authorize access="!isAuthenticated()">
              <li><a href="<c:url value="/" />">Znajdź przejazd</a></li>
              <li><a href="<c:url value="/route/add" />">Dodaj przejazd</a>
              <li><a href="<c:url value="/account/routes" />">Przejazdy</a></li>
              <li><a href="<c:url value="/account/data" />">Profil</a></li>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
              <li><a href="<c:url value="/" />">Znajdź przejazd</a></li>
              <li><a href="<c:url value="/route/add" />">Dodaj przejazd</a>
              <li><a href="<c:url value="/account/routes" />">Przejazdy</a></li>
              <li><a href="<c:url value="/account/data" />">Profil</a></li>
</sec:authorize>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
  	<div class="container">
      <!--<p>Current Locale : ${pageContext.response.locale}</p>
      Text: <spring:message code="welcome.springmvc" text="default text" />
      <p>Language : <a href="?language=en">English</a>|<a href="?language=zh_CN">Chinese</a></p>-->
<jsp:doBody/>

	</div>
	
  </body>
</html>