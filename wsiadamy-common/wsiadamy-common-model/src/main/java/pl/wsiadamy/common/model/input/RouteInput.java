package pl.wsiadamy.common.model.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class RouteInput {

	public interface BasicDataValidationGroup {};
	public interface EditValidationGroup {};
	
	@NotNull(groups = { BasicDataValidationGroup.class } )
	@Length(min=1, message="{javax.validation.constraints.required}", groups = { BasicDataValidationGroup.class } )
	private String locationSource;
	
	@NotNull(groups = { BasicDataValidationGroup.class } )
	@Length(min=1, message="{javax.validation.constraints.required}", groups = { BasicDataValidationGroup.class } )
	private String locationSourceCoords;
	
	@NotNull(groups = { BasicDataValidationGroup.class } )
	@Length(min=1, message="{javax.validation.constraints.required}", groups = { BasicDataValidationGroup.class } )
	private String locationDestination;
	
	@NotNull(groups = { BasicDataValidationGroup.class } )
	@Length(min=1, message="{javax.validation.constraints.required}", groups = { BasicDataValidationGroup.class } )
	private String locationDestinationCoords;
	
//	@NotEmpty
	private HashMap<String, String> waypoints;
	
//	@NotEmpty
	private HashMap<String, String> waypointsCoords;
	
	private Calendar dateDeparture;
	private SimpleDateFormat dateArrivalFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	
	@NotNull
	@Length(min=1)
	private String routeLine;

	@NotNull(groups = { BasicDataValidationGroup.class, EditValidationGroup.class })
	@Min(1)
	private int seats;
	
	@NotNull(groups = { EditValidationGroup.class })
	@Min((long) 0.001)
	private float routeLength;
	
	@NotNull(groups = { EditValidationGroup.class })
	@Range(min = 1, message="{javax.validation.constraints.required}", groups = { EditValidationGroup.class })
	private float carCombustion;
	
	@NotNull(groups = { EditValidationGroup.class })
	@Range(min = 1, message="{javax.validation.constraints.required}", groups = { EditValidationGroup.class })
	private float fuelPrice;
	
	@NotNull(groups = { EditValidationGroup.class })
	@Range(min = 1, message="{javax.validation.constraints.required}", groups = { EditValidationGroup.class })
	private float totalPrice;
	
	@NotNull(groups = { EditValidationGroup.class })
	private boolean participansModeration;
	
    public RouteInput() {
//    	setLocationSource("");
//    	setLocationDestination("");
//    	
//    	setLocationSourceCoords("");
//    	setLocationDestinationCoords("");
    	
    	Calendar dateTommorow = Calendar.getInstance();
    	dateTommorow.add(Calendar.DATE, 1);
    	setDateDeparture(dateTommorow);
    	
    	waypoints = new LinkedHashMap<String, String>();
    	waypointsCoords = new LinkedHashMap<String, String>();
    	
    	setSeats(2);
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

	public HashMap<String, String> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(HashMap<String, String> waypoints) {
		this.waypoints = waypoints;
	}

	public HashMap<String, String> getWaypointsCoords() {
		return waypointsCoords;
	}
	
	public void setWaypointsCoords(HashMap<String, String> waypointsCoords) {
		this.waypointsCoords = waypointsCoords;
	}

	public String getRouteLine() {
		return routeLine;
	}

	public void setRouteLine(String routeLine) {
		this.routeLine = routeLine;
	}
	
	public String getDateDeparture() {
		return dateArrivalFormat.format(this.dateDeparture.getTime());
	}
	
	public Date getDateDepartureObject() {
		return dateDeparture.getTime();
	}

	public void setDateDeparture(String dateDeparture) throws ParseException {
		this.dateDeparture.setTime(dateArrivalFormat.parse(dateDeparture));
	}
	
	public void setDateDeparture(Calendar dateDeparture) {
		this.dateDeparture = dateDeparture;
	}
	
	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
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
