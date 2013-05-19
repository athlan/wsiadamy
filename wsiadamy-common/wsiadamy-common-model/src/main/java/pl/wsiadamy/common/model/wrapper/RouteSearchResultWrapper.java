package pl.wsiadamy.common.model.wrapper;

import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;

public class RouteSearchResultWrapper {
	private Route route;

	private Participanse participanse;

	private double distanceSource;

	private double distanceDestination;

	private double positionSource;

	private double positionDestination;
	
	public RouteSearchResultWrapper(Route route, Participanse participanse) {
		setRoute(route);
		setParticipanse(participanse);
	}
	
	public Route getRoute() {
		return route;
	}
	
	private void setRoute(Route route) {
		this.route = route;
	}
	
	public Participanse getParticipanse() {
		return participanse;
	}
	
	private void setParticipanse(Participanse participanse) {
		this.participanse = participanse;
	}

	public double getDistanceSource() {
		return distanceSource;
	}

	public void setDistanceSource(double distanceSource) {
		this.distanceSource = distanceSource;
	}

	public double getDistanceDestination() {
		return distanceDestination;
	}

	public void setDistanceDestination(double distanceDestination) {
		this.distanceDestination = distanceDestination;
	}

	public double getPositionSource() {
		return positionSource;
	}

	public void setPositionSource(double positionSource) {
		this.positionSource = positionSource;
	}

	public double getPositionDestination() {
		return positionDestination;
	}

	public void setPositionDestination(double positionDestination) {
		this.positionDestination = positionDestination;
	}

}
