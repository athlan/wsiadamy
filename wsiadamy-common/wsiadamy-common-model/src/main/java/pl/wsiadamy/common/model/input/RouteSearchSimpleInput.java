package pl.wsiadamy.common.model.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	private Calendar dateDeparture;
	private SimpleDateFormat dateDepartureFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	
	private Calendar dateDepartureOffset;
	private SimpleDateFormat dateDepartureOffsetFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private Calendar dateToken;
	private SimpleDateFormat dateTokenFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
    public RouteSearchSimpleInput() {
    	setLocationSource("");
    	setLocationSourceCoords("");
    	
    	setLocationDestination("");
    	setLocationDestinationCoords("");

    	Calendar dateTommorow = Calendar.getInstance();
    	dateTommorow.add(Calendar.MINUTE, 10);
    	setDateDeparture(dateTommorow);
    	
    	this.dateDepartureOffset = null;
    	this.dateToken = null;
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
	
	public String getDateDeparture() {
		if(null == this.dateDeparture)
			return null;
		
		return getDateDepartureFormat().format(this.dateDeparture.getTime());
	}
	
	public Date getDateDepartureObject() {
		return dateDeparture.getTime();
	}

	public void setDateDeparture(String dateDeparture) throws ParseException {
		if(null != dateDeparture) {
			if(null == this.dateDeparture)
				this.dateDeparture = Calendar.getInstance();
			
			this.dateDeparture.setTime(getDateDepartureFormat().parse(dateDeparture));
		}
		else {
			this.dateDeparture = null;
		}
	}
	
	public void setDateDeparture(Calendar dateDeparture) {
		this.dateDeparture = dateDeparture;
	}
	
	public SimpleDateFormat getDateDepartureFormat() {
		return dateDepartureFormat;
	}

	public String getDateDepartureOffset() {
		if(null == this.dateDepartureOffset)
			return null;
		
		return getDateDepartureOffsetFormat().format(this.dateDepartureOffset.getTime());
	}
	
	public Date getDateDepartureOffsetObject() {
		return dateDepartureOffset.getTime();
	}

	public void setDateDepartureOffset(String dateDepartureOffset) throws ParseException {
		if(null != dateDepartureOffset) {
			if(null == this.dateDepartureOffset)
				this.dateDepartureOffset = Calendar.getInstance();
			
			this.dateDepartureOffset.setTime(getDateDepartureOffsetFormat().parse(dateDepartureOffset));
		}
		else {
			this.dateDepartureOffset = null;
		}
	}
	
	public void setDateDepartureOffset(Calendar dateDepartureOffset) {
		this.dateDepartureOffset = dateDepartureOffset;
	}
	
	public SimpleDateFormat getDateDepartureOffsetFormat() {
		return dateDepartureOffsetFormat;
	}

	public String getDateToken() {
		if(null == this.dateToken)
			return null;
		
		return getDateTokenFormat().format(this.dateToken.getTime());
	}
	
	public Date getDateTokenObject() {
		return dateToken.getTime();
	}

	public void setDateToken(String dateToken) throws ParseException {
		if(null != dateToken) {
			if(null == this.dateToken)
				this.dateToken = Calendar.getInstance();
			
			this.dateToken.setTime(getDateTokenFormat().parse(dateToken));
		}
		else {
			this.dateToken = null;
		}
	}
	
	public void setDateToken(Calendar dateToken) {
		this.dateToken = dateToken;
	}

	public SimpleDateFormat getDateTokenFormat() {
		return dateTokenFormat;
	}
}
