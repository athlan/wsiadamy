package pl.wsiadamy.common.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.wsiadamy.common.model.common.AbstractDaoJpaImpl;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteWaypoint;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;

import com.vividsolutions.jts.geom.Point;

@Component
public class RouteDaoImpl extends AbstractDaoJpaImpl<Route, Integer> implements RouteDao {

	public RouteDaoImpl() {
		super(Route.class);
	}
	
	@Override
	public List<RouteParticipanseWrapper> listRoutes(Map<String, Object> params, int limit, int offset) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<RouteParticipanseWrapper> q = cb.createQuery(RouteParticipanseWrapper.class);
		Root<Route> from = q.from(Route.class);
		
		Join<Route, Participanse> rootLoggedUserParticipation = from.joinList("participances", JoinType.LEFT);
		
		q.multiselect(from, rootLoggedUserParticipation);
		
		// predicates
		List<Predicate> predicates = new ArrayList<Predicate>();
//		getEntityManager().createQuery(q);
		
		predicates.add(cb.equal(rootLoggedUserParticipation.get("user"), params.get("loggedUserId")));
		
		if(null != params) {
			if(params.containsKey("ownerId")) {
				predicates.add(cb.equal(from.get("owner"), params.get("ownerId")));
			}
			
			if(params.containsKey("participantId")) {
				Join<Route, Participanse> rootParticipances = from.joinList("participances", JoinType.INNER);
				predicates.add(cb.equal(rootParticipances.get("user"), params.get("participantId")));
				predicates.add(cb.notEqual(rootParticipances.get("rspvStatus"), ParticipanseRSPV.REJECTED));
			}
		}
		
		if(predicates.size() > 0)
			q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<RouteParticipanseWrapper> tq = getEntityManager().createQuery(q);
		
		tq.setMaxResults(limit);
		tq.setFirstResult(offset);
		List<RouteParticipanseWrapper> aaa = tq.getResultList();
		return tq.getResultList();
	}
	
	@Override
	public Long listRoutesCount(Map<String, Object> params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<Route> from = q.from(Route.class);
		q.select(cb.count(from));
		
		// predicates
		Predicate where = cb.conjunction();
		if(null != params) {
			if(params.containsKey("ownerId")) {
				where = cb.and(where, cb.equal(from.get("owner"), params.get("ownerId")));
			}
			
			if(params.containsKey("participantId")) {
				Join<Route, Participanse> rootParticipances = from.join("participances", JoinType.INNER);
				where = cb.and(where, cb.equal(rootParticipances.get("user"), params.get("participantId")));
			}
		}
		
		q.where(where);
		
		return getEntityManager().createQuery(q).getSingleResult();
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, null);
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, Map<String, Object> params) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, params);
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, Map<String, Object> params) {
		
		String sqlDistancePointSource = "ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]')";
		String sqlDistancePointDestination = "ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]')";
		
		String sql =
			"SELECT " +
			"route.id, " + 
			"ST_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")')) AS position_point_source, " +
			"ST_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")')) AS position_point_destination, " +
			sqlDistancePointSource + " AS distance_point_source, " +
			sqlDistancePointDestination + " AS distance_point_destination " +
			"FROM route_line AS route_line " +
			"INNER JOIN route AS route ON(route_line.route_id = route.id) " + 
			"WHERE ";
		
		String sqlWhere = "";
		
		if(null != params && params.containsKey("offsetId")) {
			sqlWhere += "route.id < " + params.containsKey("offsetId") + " AND ";
		}
		
		sqlWhere +=
			sqlDistancePointSource + " <= " + pointSourceDistanceRange + " AND " + 
			sqlDistancePointDestination + " <= " + pointDestinationDistanceRange + " " +
			"AND St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")')) < " +
			"St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")'))";
		
		sql += sqlWhere;
		
		Query qq = getEntityManager().createNativeQuery(sql);
		
		List<Object[]> aa = qq.getResultList();
		Integer[] routeIds = new Integer[aa.size()];
		
		int i = 0;
		for(Object[] result : aa) {
			routeIds[i] = (Integer) result[0];
			++i;
		}
		
		// no routes found
		if(routeIds.length == 0)
			return new ArrayList<RouteSearchResultWrapper>();
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Route> q = cb.createQuery(Route.class);
		Root<Route> root = q.from(Route.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(root.get("id").in(routeIds));
		
		if(predicates.size() > 0)
			q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<Route> tq = getEntityManager().createQuery(q);
		
		Integer limit = 10;
		
		if(null != params && params.containsKey("limit"))
			limit = (Integer) params.get("limit");
		
		tq.setMaxResults(limit);
		tq.setFirstResult(0);
		
		List<Route> resultRoutes = tq.getResultList();
		List<RouteSearchResultWrapper> result = new ArrayList<RouteSearchResultWrapper>();
		
		i = 0;
		for (Route route : resultRoutes) {
			RouteSearchResultWrapper row = new RouteSearchResultWrapper(route, null);
			
			Object[] statsRow = aa.get(i);
			row.setDistanceSource((Double) statsRow[3]);
			row.setDistanceDestination((Double) statsRow[4]);
			
			row.setPositionSource((Double) statsRow[1]);
			row.setPositionDestination((Double) statsRow[2]);
			
			result.add(row);
			
			++i;
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void synchronizeWaypointsRoutePositions(Route route) {
		String sql =
			"SELECT " +
			"waypoint.id, " + 
			"ST_Line_Locate_Point(route_line.lineString, waypoint.point) AS point_position " +
			"FROM route_waypoint AS waypoint " +
			"INNER JOIN route AS route ON(waypoint.route_id = route.id) " +
			"INNER JOIN route_line AS route_line ON(route.routeline_id = route_line.id) " +
			"WHERE waypoint.route_id = " + route.getId();
		
		Query query = getEntityManager().createNativeQuery(sql);
		List<Object[]> result = query.getResultList();
		
		for (Object[] row : result) {
			Integer waypointId = (Integer) row[0];
			
			for (RouteWaypoint routeWaypoint : route.getWaypoints()) {
				if(!routeWaypoint.getId().equals(waypointId))
					continue;
				
				Double waypointPosition = (Double) row[1];
				routeWaypoint.setRoutePosition(waypointPosition);
				break;
			}
		}
		
		update(route);
		
		return;
	}
}
