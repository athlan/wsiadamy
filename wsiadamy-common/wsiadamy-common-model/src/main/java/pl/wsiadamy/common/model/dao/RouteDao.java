package pl.wsiadamy.common.model.dao;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;

import com.vividsolutions.jts.geom.Point;

public interface RouteDao extends AbstractDao<Route, Integer> {

	List<Route> listRoutes(Map<String, Object> params, int limit, int offset);
	
	Long listRoutesCount(Map<String, Object> params);
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, Map<String, Object> params);
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, Map<String, Object> params);
	
	List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange);
	
	Participanse getParticipation(User participant, Route route);
}
