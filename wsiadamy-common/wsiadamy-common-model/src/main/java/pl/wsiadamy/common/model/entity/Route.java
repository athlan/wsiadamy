package pl.wsiadamy.common.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;

@Entity
@Table(name = "route")
public class Route extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToOne
	private User owner;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RouteLine routeLine;
	
	@OneToOne(cascade = CascadeType.ALL)
	private RouteDetails routeDetails;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RouteWaypoint waypointSource;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RouteWaypoint waypointDestination;
	
	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RouteWaypoint> waypoints;

	@Column
	private Date dateDeparture;

	@Column
	private float totalPrice;

	@Column
	private int seats;

	@Column
	private int seatsAvailable;
	
	@Column
	private boolean participanseModeration;

	@Column
	private Date dateLastModified;
	
	@OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Participanse> participances;
	
	public Route() {
		routeLine = new RouteLine(this);
		
		waypoints = new ArrayList<RouteWaypoint>();
		
		routeDetails = new RouteDetails(this);
		
		participances = new ArrayList<Participanse>();
		
		dateLastModified = new Date();
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

	public List<RouteWaypoint> getWaypoints() {
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

	public List<Participanse> getParticipanses() {
		return participances;
	}

	public boolean addParticipanse(Participanse participance) {
		for(Participanse item : this.participances) {
			if(item.equals(participance))
				return false;
		}
		
		boolean result = this.participances.add(participance);
		
		if(true == result)
			prePersistRecalculateSeatsAvailable();
		
		return result;
	}

	public boolean removeParticipanse(Participanse participance) {
		boolean result = this.participances.remove(participance);
		
		if(true == result)
			prePersistRecalculateSeatsAvailable();
		
		return result;
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
	
	public boolean isParticipanseModeration() {
		return participanseModeration;
	}

	public void setParticipanseModeration(boolean participanseModeration) {
		this.participanseModeration = participanseModeration;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	@PrePersist
	@PreUpdate
	public void prePersistRecalculateSeatsAvailable() {
		int seatsBooked = 0;
		
		// count all approved participances
		// except onwer.
		for (Participanse participanse : participances) {
			if (participanse.getRspvStatus() == ParticipanseRSPV.APPROVED
				&& !participanse.getUser().equals(this.getOwner())) {
				++seatsBooked;
			}
		}
		
		this.seatsAvailable = this.seats - seatsBooked;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof Route))
			return false;
		
		Route objCast = (Route) obj;
		
		return objCast.getId().equals(this.getId());
	}
	
	@Override
	public String toString() {
		return "Route [id=" + id + "]";
	}
}
