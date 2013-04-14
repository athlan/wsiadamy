package pl.wsiadamy.common.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.Route;

import com.vividsolutions.jts.geom.Point;

@Component
public class RouteDaoImpl extends AbstractDaoImpl<Route, Integer> implements RouteDao {

	public RouteDaoImpl() {
		super(Route.class);
	}
	
	@Override
	public List<Route> listRoutes(Map<String, Object> params, int limit, int offset) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Route> q = cb.createQuery(Route.class);
		Root<Route> root = q.from(Route.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		
//		predicates.add(root.get("id").in(routeIds));
		
		if(predicates.size() > 0)
			q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<Route> tq = getEntityManager().createQuery(q);
		
		tq.setMaxResults(limit);
		tq.setFirstResult(offset);
		
		return tq.getResultList();
	}
	
	@Override
	public Long listRoutesCount(Map<String, Object> params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Route> q = cb.createQuery(Route.class);
		Root<Route> root = q.from(Route.class);
		
		// predicates
		List<Predicate> predicates = new ArrayList<Predicate>();
		getEntityManager().createQuery(q);
		if(null != params) {
			if(params.containsKey("ownerId")) {
				predicates.add(cb.equal(root.get("owner"), params.get("ownerId")));
			}
		}
		
		CriteriaQuery<Long> qCount = cb.createQuery(Long.class);
		qCount.select(cb.count(qCount.from(Route.class)));
//		getEntityManager().createQuery(qCount);
		if(predicates.size() > 0)
			qCount.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return getEntityManager().createQuery(qCount).getSingleResult();
	}
	
	@Override
	public List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, null);
	}
	
	@Override
	public List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange, Map<String, Object> params) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange, params);
	}
	
	@Override
	public List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange, Map<String, Object> params) {
		
		String sqlDistancePointSource = "ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]')";
		String sqlDistancePointDestination = "ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]')";
		
		String sql =
			"SELECT " +
			"route.id, " + 
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
			return new ArrayList<Route>();
		
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
		
		return tq.getResultList();
	}
}
