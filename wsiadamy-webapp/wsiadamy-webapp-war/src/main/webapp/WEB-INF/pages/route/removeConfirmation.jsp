<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
	<form method="post" action="" class="form-horizontal">
		<div class="alert alert-info">
			Czy na pewno chcesz usunąć tę trasę?
		</div>
    <button class="btn btn-danger btnLocker">Usuń trasę</button>
    <button type="button" class="btn" onclick="history.back(-1)">Anuluj</button>
	</form>
</t:wrapper>