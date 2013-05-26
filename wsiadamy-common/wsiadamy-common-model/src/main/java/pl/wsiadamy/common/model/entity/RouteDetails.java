package pl.wsiadamy.common.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;

@Entity
@Table(name = "route_details")
public class RouteDetails extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	Route route;
	
	@Column
	private float routeLength;

	@Column
	private float fuelPrice;
	
	@Column
	private float carCombustion;
	
	@Column
	private Integer feedbackCountParticipants = 0;
	
	@Column
	private Integer feedbackCountDriver = 0;
	
	
	public RouteDetails() {
	}

	public RouteDetails(Route route) {
		setRoute(route);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}

	public float getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(float routeLength) {
		this.routeLength = routeLength;
	}

	public float getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(float fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public float getCarCombustion() {
		return carCombustion;
	}

	public void setCarCombustion(float carCombustion) {
		this.carCombustion = carCombustion;
	}

	public Integer getFeedbackCountParticipants() {
		return feedbackCountParticipants;
	}

	public void setFeedbackCountParticipants(Integer feedbackCountParticipants) {
		this.feedbackCountParticipants = feedbackCountParticipants;
	}

	public Integer getFeedbackCountDriver() {
		return feedbackCountDriver;
	}

	public void setFeedbackCountDriver(Integer feedbackCountDriver) {
		this.feedbackCountDriver = feedbackCountDriver;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
