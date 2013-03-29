package pl.wsiadamy.common.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

public class RouteSimpleInput {
	
	@NotNull
	@Length(min=1)
	//@Size(min=1)
	private String locationSource;
	
	@NotNull
	@Size(min=1)
	private String locationDestination;
	
    public RouteSimpleInput() {
    	locationSource = "";
    	locationDestination = "";
    }
    
	public String getLocationSource() {
		return locationSource;
	}
	
	public void setLocationSource(String locationSource) {
		this.locationSource = locationSource;
	}

	public String getLocationDestination() {
		return locationDestination;
	}

	public void setLocationDestination(String locationDestination) {
		this.locationDestination = locationDestination;
	}
}
