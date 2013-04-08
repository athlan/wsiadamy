package pl.wsiadamy.common.model.bo;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteWaypoint;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteAddDetailsInput;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;
import pl.wsiadamy.common.model.util.GeometryPointFactory;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

@Service("routeBO")
public class RouteBOImpl implements RouteBO {
	@Autowired
	RouteDao routeDao;
	
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
		route.setSeats(input.getSeats());
		
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
	
//	public Route createRoute() {
//		Route route = new Route();
//		
//		String wktPoint = "POINT(10 5)";
//		
//		WKTReader fromText = new WKTReader();
//        Geometry geom = null;
//        try {
//            geom = fromText.read(wktPoint);
//        } catch (ParseException e) {
//            throw new RuntimeException("Not a WKT string:" + wktPoint);
//        }
//        if (!geom.getGeometryType().equals("Point")) {
//            throw new RuntimeException("Geometry must be a point. Got a " + geom.getGeometryType());
//        }
//        
//        GeometryFactory factory = new GeometryFactory();
//        
//        route.setPoint1(factory.createPoint(new Coordinate(17.006836, 51.117317)));
//        route.setPoint2(factory.createPoint(new Coordinate(18.672638, 50.30206)));
//        
////        route.setWaypointStart(new RouteWaypoint((Point) geom));
////        
////        List<Coordinate> coords = new ArrayList<Coordinate>();
////        
////        coords.add(new Coordinate());
////        
////        
////        LineString line = factory.createLineString(coords.toArray(new Coordinate[coords.size()]));
////        route.getRouteLine().setLineString(line);
//        
//		routeDao.create(route);
//		
//		return route;
//	}
	
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
