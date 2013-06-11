package pl.wsiadamy.common.model.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.dao.UserDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteWaypoint;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteInput;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;
import pl.wsiadamy.common.model.util.GeometryPointFactory;
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;
import pl.wsiadamy.common.model.wrapper.RouteUserStatsWrapper;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

@Service("routeBO")
public class RouteBOImpl implements RouteBO {
	@Autowired
	RouteDao routeDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public List<RouteParticipanseWrapper> listRoutes(Map<String, Object> params, int limit, int offset) {
		return routeDao.listRoutes(params, limit, offset);
	}
	
	@Override
	public Long listRoutesCount(Map<String, Object> params) {
		return routeDao.listRoutesCount(params);
	}

	@Override
	public Route createRoute(User owner, RouteInput input) {
		Route route = new Route();
		
		route.setOwner(owner);
		route.setDateLastModified(new Date());
		
		fillRoute(route, input);
		
		Participanse ownerParticipanse = new Participanse(owner, route);
		ownerParticipanse.setRspvStatus(ParticipanseRSPV.APPROVED);
		route.addParticipanse(ownerParticipanse);
		
		routeDao.create(route);
		
		routeDao.synchronizeWaypointsRoutePositions(route);
		
		User routeOwner = route.getOwner();
		routeOwner.getUserData().setCarCombustion(input.getCarCombustion());
		userDao.update(routeOwner);
		
		return route;
	}
	
	@Override
	public Route editRoute(Integer id, RouteInput input) {
		Route route = routeDao.get(id);
		
		if(null == route)
			return null;
		
		route.setDateLastModified(new Date());
		
		fillRoute(route, input);
		
		routeDao.update(route);

		User routeOwner = route.getOwner();
		routeOwner.getUserData().setCarCombustion(input.getCarCombustion());
		userDao.update(routeOwner);
		
		return route;
	}
	
	private void fillRoute(Route route, RouteInput input) {
		if(null != input)
		{
			// set source and destination
			if(null != input.getLocationSourceCoords())
			{
				Point waypointStartPoint = GeometryPointFactory.createPointFromString(input.getLocationSourceCoords());
				RouteWaypoint waypointStart = new RouteWaypoint(route, waypointStartPoint);
				waypointStart.setName(input.getLocationSource());
				
				route.setWaypointSource(waypointStart);
			}

			if(null != input.getLocationDestinationCoords())
			{
				Point waypointStopPoint = GeometryPointFactory.createPointFromString(input.getLocationDestinationCoords());
				RouteWaypoint waypointStop = new RouteWaypoint(route, waypointStopPoint);
				waypointStop.setName(input.getLocationDestination());
				
				route.setWaypointDestination(waypointStop);
			}
			
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
			
			if(null != input.getRouteLine())
			{
				// set linestring
				LineString lineString = GeometryPointFactory.createLineStringFromString(input.getRouteLine());
				route.getRouteLine().setLineString(lineString);
			}
			
			// set date and details
			route.setDateDeparture(input.getDateDepartureObject());
			route.setSeats(input.getSeats());
		}
		
		if(null != input)
		{
			route.setTotalPrice(input.getTotalPrice());
			
			route.getRouteDetails().setRouteLength(input.getRouteLength());
			route.getRouteDetails().setFuelPrice(input.getFuelPrice());
			route.getRouteDetails().setCarCombustion(input.getCarCombustion());
			
			route.setParticipanseModeration(input.isParticipansModeration());
		}
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Map<String, Object> params, RouteSearchSimpleInput input, int limit) {
		Point waypointStartPoint = GeometryPointFactory.createPointFromString(input.getLocationSourceCoords());
		Point waypointStopPoint = GeometryPointFactory.createPointFromString(input.getLocationDestinationCoords());
		
		if(null == params)
			params = new HashMap<String, Object>();
		
		if(null != input.getDateDepartureOffset())
			params.put("dateDepartureAfter", input.getDateDepartureOffsetObject());
		else if(null != input.getDateDeparture())
			params.put("dateDepartureAfter", input.getDateDepartureObject());
		
		if(null != input.getDateToken())
			params.put("dateTokenAfter", input.getDateTokenObject());
		
		return routeDao.findRoutes(waypointStartPoint, waypointStopPoint, input.getRangee() * 1000, limit, params);
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(RouteSearchSimpleInput input, int limit) {
		return findRoutes(null, input, limit);
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

	@Override
	public RouteUserStatsWrapper getUserStats(User user) {
		return routeDao.getUserStats(user.getId());
	}
}
