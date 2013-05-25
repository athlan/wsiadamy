<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:account>
	<h2>Statystyka</h2>
	
	<ul>
		<li>Przejechałeś łącznie: ${ stats.totalDistance } w ${ stats.count } przejazdach</li>
		<li>Całkowity koszt przejazdów: ${ stats.totalPrice } (średnio ${ stats.averagePrice })</li>
		<li>Zapłaciłeś: ${ stats.totalPriceSaved }</li>
		<li>Czyli zaoszczędziłeś łącznie: ${ stats.totalPrice - stats.totalPriceSaved }</li>
	</ul>
</t:account>