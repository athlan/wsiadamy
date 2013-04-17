package pl.wsiadamy.common.model.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.ScriptAssert.List;

import pl.wsiadamy.common.model.common.AbstractEntity;

@Entity
@Table(name = "route")
public class Route extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	private User owner;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	RouteLine routeLine;
	
	@OneToOne(cascade = CascadeType.ALL)
	RouteDetails routeDetails;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	RouteWaypoint waypointSource;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	RouteWaypoint waypointDestination;
	
	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<RouteWaypoint> waypoints;
	
	@Column
	private Date dateDeparture;

	@Column
	private float totalPrice;

	@Column
	private int seats;
	
	@Column
	private int seatsAvailable;
	
	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Participanse> participances;
	
	public Route() {
		routeLine = new RouteLine(this);
		
		waypoints = new LinkedHashSet<RouteWaypoint>();
		
		routeDetails = new RouteDetails(this);
		
		participances = new LinkedHashSet<Participanse>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public RouteLine getRouteLine() {
		return routeLine;
	}

	public RouteDetails getRouteDetails() {
		return routeDetails;
	}

	public RouteWaypoint getWaypointSource() {
		return waypointSource;
	}

	public void setWaypointSource(RouteWaypoint waypointStart) {
		this.waypointSource = waypointStart;
	}

	public RouteWaypoint getWaypointDestination() {
		return waypointDestination;
	}

	public void setWaypointDestination(RouteWaypoint waypointStop) {
		this.waypointDestination = waypointStop;
	}

	public Set<RouteWaypoint> getWaypoints() {
		return waypoints;
	}

	public boolean addWaypoint(RouteWaypoint waypoint) {
		return this.waypoints.add(waypoint);
	}

	public boolean removeWaypoint(RouteWaypoint waypoint) {
		return this.waypoints.remove(waypoint);
	}
	
	public void clearWaypoints() {
		this.waypoints.clear();
	}

	public Set<Participanse> getParticipanses() {
		return participances;
	}

	public boolean addParticipanse(Participanse participance) {
		return this.participances.add(participance);
	}

	public boolean removeParticipanse(Participanse participance) {
		return this.participances.remove(participance);
	}
	
	public void clearParticipanses() {
		this.participances.clear();
	}
	
	public Date getDateDeparture() {
		return dateDeparture;
	}

	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
		this.setSeatsAvailableRecalculate();
	}
	
	private void setSeatsAvailableRecalculate() {
		setSeatsAvailable(getSeatsAvailable());
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	
	private void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
