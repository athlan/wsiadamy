<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:account>
	<h2>Statystyka</h2>
	
	<ul>
		<li>Przejechałeś łącznie: <fmt:formatNumber value="${ stats.totalDistance }" maxFractionDigits="0"/>km w ${ stats.count } przejazdach</li>
		<li>Całkowity koszt przejazdów: <fmt:formatNumber value="${ stats.totalPrice }" maxFractionDigits="2"/> PLN (średnio <fmt:formatNumber value="${ stats.averagePrice }" maxFractionDigits="2"/> PLN)</li>
		<li>Zapłaciłeś: <fmt:formatNumber value="${ stats.totalPriceSaved }" maxFractionDigits="2"/> PLN</li>
		<li>Czyli zaoszczędziłeś łącznie: <fmt:formatNumber value="${ stats.totalPrice - stats.totalPriceSaved }" maxFractionDigits="2"/> PLN</li>
	</ul>
</t:account>