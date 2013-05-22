package pl.wsiadamy.common.model.input;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class RouteSearchSimpleInput {
	
	@Length(min=1, message="{javax.validation.constraints.required}")
	private String locationSource;

	@NotNull
	@Length(min=1, message="{javax.validation.constraints.required}")
	private String locationSourceCoords;
	
	@Length(min=1, message="{javax.validation.constraints.required}")
	private String locationDestination;

	@NotNull
	@Length(min=1, message="{javax.validation.constraints.required}")
	private String locationDestinationCoords;
	
    public RouteSearchSimpleInput() {
    	setLocationSource("");
    	setLocationSourceCoords("");
    	
    	setLocationDestination("");
    	setLocationDestinationCoords("");
    }
    
	public String getLocationSource() {
		return locationSource;
	}
	
	public void setLocationSource(String locationSource) {
		this.locationSource = locationSource;
	}

	public String getLocationSourceCoords() {
		return locationSourceCoords;
	}

	public void setLocationSourceCoords(String locationSourceCoords) {
		this.locationSourceCoords = locationSourceCoords;
	}

	public String getLocationDestination() {
		return locationDestination;
	}

	public void setLocationDestination(String locationDestination) {
		this.locationDestination = locationDestination;
	}

	public String getLocationDestinationCoords() {
		return locationDestinationCoords;
	}

	public void setLocationDestinationCoords(String locationDestinationCoords) {
		this.locationDestinationCoords = locationDestinationCoords;
	}
}
