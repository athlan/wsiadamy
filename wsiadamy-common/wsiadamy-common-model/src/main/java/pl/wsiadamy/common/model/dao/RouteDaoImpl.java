package pl.wsiadamy.common.model.dao;

import java.util.ArrayList;
import java.util.Date;
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
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;
import pl.wsiadamy.common.model.wrapper.RouteUserStatsWrapper;

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
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, int limit) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, limit, null);
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, int limit, Map<String, Object> params) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, limit, params);
	}
	
	@Override
	public List<RouteSearchResultWrapper> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, int limit, Map<String, Object> params) {
		
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

		if(null != params && params.containsKey("dateDepartureAfter")) {
			sqlWhere += "route.datedeparture >= :dateDepartureAfter AND ";
		}
		
		if(null != params && params.containsKey("dateTokenAfter")) {
			sqlWhere += "route.datelastmodified > :dateTokenAfter AND ";
		}
		
		sqlWhere +=
			sqlDistancePointSource + " <= " + pointSourceDistanceRange + " AND " + 
			sqlDistancePointDestination + " <= " + pointDestinationDistanceRange + " " +
			"AND St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")')) < " +
			"St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")')) ";
		
		sql += sqlWhere;
		
		sql += "ORDER BY datedeparture ASC, datelastmodified ASC ";
		sql += "LIMIT " + limit;
		
		Query qq = getEntityManager().createNativeQuery(sql);
		
		if(null != params && params.containsKey("dateDepartureAfter")) {
			Date dateDepartureAfter = (Date) params.get("dateDepartureAfter");
			qq.setParameter("dateDepartureAfter", dateDepartureAfter);
		}
		
		if(null != params && params.containsKey("dateTokenAfter")) {
			Date dateTokenAfter = (Date) params.get("dateTokenAfter");
			qq.setParameter("dateTokenAfter", dateTokenAfter);
		}
		
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
		
//		Integer limit = 10;
//		
//		if(null != params && params.containsKey("limit"))
//			limit = (Integer) params.get("limit");
		
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

	@Override
	public RouteUserStatsWrapper getUserStats(Integer userId) {
		String dql = "SELECT " +
				"COUNT(item.id) AS stats_routes_count, " +
				"SUM(item.totalPrice) AS stats_routes_totalprice, " +
				"SUM(itemDetails.routeLength) AS stats_routes_totaldistance, " +
				"AVG(item.totalPrice) AS stats_routes_averageprice, " +
				"SUM(CASE WHEN ((item.seats+1-item.seatsAvailable) > 0) THEN (item.totalPrice/(item.seats+1 - item.seatsAvailable)) ELSE 0 END) AS stats_routes_totalprice_saved " +
				"FROM Participanse itemParticipanse " +
				"JOIN itemParticipanse.route item " +
				"JOIN item.routeDetails itemDetails " +
				"JOIN itemParticipanse.user itemParticipanseUser " +
				"WHERE itemParticipanseUser.id = :userId " +
				"GROUP BY item.id";
//		String dql = "SELECT " +
//				"COUNT(r.id) AS stats_routes_count " +
//				"FROM Route r ";
		
		Query query = getEntityManager().createQuery(dql);
		
		query.setParameter("userId", userId);
		
		RouteUserStatsWrapper result = new RouteUserStatsWrapper();;
		Object[] resultQuery;
		
		try {
			resultQuery = (Object[]) query.getSingleResult();
			
			result.setCount((Long) resultQuery[0]);
			result.setTotalPrice((Double) resultQuery[1]);
			result.setTotalDistance((Double) resultQuery[2]);
			result.setAveragePrice((Double) resultQuery[3]);
			result.setTotalPriceSaved((Double) resultQuery[4]);
			
		}
		catch(Exception e) {
			return null;
		}
		
		return result;
	}
}
