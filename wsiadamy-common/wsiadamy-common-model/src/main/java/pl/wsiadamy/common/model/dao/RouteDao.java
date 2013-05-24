package pl.wsiadamy.common.model.dao;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;

import com.vividsolutions.jts.geom.Point;

public interface RouteDao extends AbstractDao<Route, Integer> {

	List<RouteParticipanseWrapper> listRoutes(Map<String, Object> params, int limit, int offset);
	
	Long listRoutesCount(Map<String, Object> params);
	
	List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, int limit, Map<String, Object> params);
	
	List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, int limit, Map<String, Object> params);
	
	List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, int limit);
	
	void synchronizeWaypointsRoutePositions(Route route);
}
