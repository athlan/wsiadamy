<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>=
	<h2>Edytuj przejazd</h2>
	
	<div class="row">
		<div class="span5">
		<form:form method="post" action="" commandName="routeAddDetailsInput">
		    <div>
		    	<label for="fieldRouteLength">Długość trasy:</label>
				<form:input path="routeLength" id="fieldRouteLength" />
				<form:errors path="routeLength" cssClass="error" />
			</div>
			<div>
				<label for="fieldCarCombustion">Spalanie Twojego samochodu na 100km:</label>
				<form:input path="carCombustion" id="fieldCarCombustion" />
				<form:errors path="carCombustion" cssClass="error" />
			</div>
			<div>
				<label for="fieldFuelPrice">Cena benzyny za litr:</label>
				<form:input path="fuelPrice" id="fieldFuelPrice" />
				<form:errors path="fuelPrice" cssClass="error" />
			</div>
			<div>
				<label for="fieldTotalPrice">Całkowity koszt trasy:</label>
				<form:input path="totalPrice" id="fieldTotalPrice" />
				<form:errors path="totalPrice" cssClass="error" />
			</div>
			
			<div>
				<input type="submit" value="Edytuj" class="btn btn-primary" >
			</div>
		</form:form>
		</div>
		<div class="span7">
			<div id="map_canvas" style="width: 100%; height: 400px;"></div>
		</div>
	</div>
	
<script type="text/javascript">
	$(function() {
		$('#fieldRouteLength, #fieldCarCombustion, #fieldFuelPrice').bind('change keyup', function() {
			var length = parseFloat($('#fieldRouteLength').val());
			var conbustion = parseFloat($('#fieldCarCombustion').val());
			var fuelPrice = parseFloat($('#fieldFuelPrice').val());
			
			var totalPrice = length * conbustion * fuelPrice / 100;
			
			$('#fieldTotalPrice').val(Math.ceil(totalPrice));
		});
	});
</script> 
	
</t:wrapper>