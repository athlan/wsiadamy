package pl.wsiadamy.common.model.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteWaypoint;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteAddDetailsInput;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;
import pl.wsiadamy.common.model.util.GeometryPointFactory;
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

@Service("routeBO")
public class RouteBOImpl implements RouteBO {
	@Autowired
	RouteDao routeDao;

	@Override
	public List<RouteParticipanseWrapper> listRoutes(Map<String, Object> params, int limit, int offset) {
		return routeDao.listRoutes(params, limit, offset);
	}
	
	@Override
	public Long listRoutesCount(Map<String, Object> params) {
		return routeDao.listRoutesCount(params);
	}
	
	public Route createRoute(User owner, RouteAddInput input, RouteAddDetailsInput inputDetails) {
		Route route = new Route();
		
		route.setOwner(owner);
		
		// set source and destination
		Point waypointStartPoint = GeometryPointFactory.createPointFromString(input.getLocationSourceCoords());
		RouteWaypoint waypointStart = new RouteWaypoint(route, waypointStartPoint);
		waypointStart.setName(input.getLocationSource());
		
		route.setWaypointSource(waypointStart);
		
		Point waypointStopPoint = GeometryPointFactory.createPointFromString(input.getLocationDestinationCoords());
		RouteWaypoint waypointStop = new RouteWaypoint(route, waypointStopPoint);
		waypointStop.setName(input.getLocationDestination());
		
		route.setWaypointDestination(waypointStop);
		
		// set waypoints
		for(Map.Entry<String, String> entry : input.getWaypoints().entrySet()) {
			if(!entry.getValue().equals("")
				&& input.getWaypointsCoords().containsKey(entry.getKey())
				&& !input.getWaypointsCoords().get(entry.getKey()).equals("")) {
				Point waypointPoint = GeometryPointFactory.createPointFromString(input.getWaypointsCoords().get(entry.getKey()));
				
				RouteWaypoint waypoint = new RouteWaypoint(route, waypointPoint);
				waypoint.setName(entry.getValue());
				route.addWaypoint(waypoint);
			}
		}
		
		// set linestring
		LineString lineString = GeometryPointFactory.createLineStringFromString(input.getRouteLine());
		route.getRouteLine().setLineString(lineString);
		
		// set date and details
		route.setDateDeparture(input.getDateDepartureObject());
		route.setSeats(input.getSeats() + 1);
		route.addParticipanse(new Participanse(owner, route));
		
		route.setTotalPrice(inputDetails.getTotalPrice());
		
		route.getRouteDetails().setRouteLength(inputDetails.getRouteLength());
		route.getRouteDetails().setFuelPrice(inputDetails.getFuelPrice());
		route.getRouteDetails().setCarCombustion(inputDetails.getCarCombustion());
		
		routeDao.create(route);
		
		return route;
	}
	
	@Override
	public List<Route> findRoutes(RouteSearchSimpleInput input) {

		Point waypointStartPoint = GeometryPointFactory.createPointFromString(input.getLocationSourceCoords());
		Point waypointStopPoint = GeometryPointFactory.createPointFromString(input.getLocationDestinationCoords());
		
		return routeDao.findRoutes(waypointStartPoint, waypointStopPoint, 5000);
	}
	
	public void setUserDao(RouteDao routeDao) {
		this.routeDao = routeDao;
	}
	
	@Override
	public void save(Route route) {
		routeDao.create(route);
	}
	
	@Override
	public void update(Route route) {
		routeDao.update(route);
	}
	
	@Override
	public void delete(Route route) {
		routeDao.remove(route);
	}

	@Override
	public Route getById(Integer id) {
		return routeDao.get(id);
	}
}
