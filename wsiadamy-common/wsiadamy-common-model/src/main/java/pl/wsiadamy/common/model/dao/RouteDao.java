package pl.wsiadamy.common.model.dao;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Route;

import com.vividsolutions.jts.geom.Point;

public interface RouteDao extends AbstractDao<Route, Integer> {
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, Map<String, Object> params);
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, Map<String, Object> params);
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange);
}
