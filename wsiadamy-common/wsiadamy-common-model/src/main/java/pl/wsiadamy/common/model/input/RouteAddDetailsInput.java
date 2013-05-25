package pl.wsiadamy.common.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class RouteAddDetailsInput {
	
	@NotNull
	@Min((long) 0.001)
	private float routeLength;
	
	@NotNull
	@Range(min = 1, message="{javax.validation.constraints.required}")
	private float carCombustion;
	
	@NotNull
	@Range(min = 1, message="{javax.validation.constraints.required}")
	private float fuelPrice;
	
	@NotNull
	@Range(min = 1, message="{javax.validation.constraints.required}")
	private float totalPrice;
	
	@NotNull
	private boolean participansModeration;
	
    public RouteAddDetailsInput() {
    	
    }

	public float getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(float routeLength) {
		this.routeLength = routeLength;
	}

	public float getCarCombustion() {
		return carCombustion;
	}

	public void setCarCombustion(float carCombustion) {
		this.carCombustion = carCombustion;
	}

	public float getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(float fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isParticipansModeration() {
		return participansModeration;
	}

	public void setParticipansModeration(boolean participansModeration) {
		this.participansModeration = participansModeration;
	}
}
