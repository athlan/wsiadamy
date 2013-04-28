package pl.wsiadamy.common.model.wrapper;

import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;

public class RouteParticipanseWrapper {
	private Route route;
	
	private Participanse participanse;
	
	public RouteParticipanseWrapper(Route route, Participanse participanse) {
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
}
