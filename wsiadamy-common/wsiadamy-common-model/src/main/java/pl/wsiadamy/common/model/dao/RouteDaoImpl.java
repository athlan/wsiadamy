package pl.wsiadamy.common.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteLine;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

@Component
public class RouteDaoImpl extends AbstractDaoImpl<Route, Integer> implements RouteDao {

	public RouteDaoImpl() {
		super(Route.class);
	}
	
	@Override
	public List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointRange) {
		return findRoutes(pointSource, pointDestinaton, pointRange, pointRange);
	}
	
	@Override
	public List<Route> findRoutes(Point pointSource, Point pointDestinaton, float pointSourceDistanceRange, float pointDestinationDistanceRange) {
		String sql =
			"SELECT " +
			"route.id, " + 
			"ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]') AS distance_point_source, " +
			"ST_Distance_Spheroid(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")'), 'SPHEROID[\"WGS 84\",6378137,298.257223563]') AS distance_point_destination " +
			"FROM route_line AS route_line " +
			"INNER JOIN route AS route ON(route_line.route_id = route.id) " + 
			"WHERE " +
			"distance_point_source <= " + pointSourceDistanceRange + " " + 
			"distance_point_destination <= " + pointDestinationDistanceRange + " " +
			"AND St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointSource.getX() + " " + pointSource.getY() + ")')) < " +
			"St_Line_Locate_Point(route_line.lineString, ST_GeomFromText('POINT(" + pointDestinaton.getX() + " " + pointDestinaton.getY() + ")'))";
		
		Query qq = getEntityManager().createNativeQuery(sql);
//		qq.setParameter("pointSourceLongitude", pointSource.getX());
//		qq.setParameter("pointSourceLatitude", pointSource.getY());
//		qq.setParameter("pointDestinationLongitude", pointDestinaton.getX());
//		qq.setParameter("pointDestinationLatitude", pointDestinaton.getY());
		
		List aa = qq.getResultList();
		
		return null;
//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		
//		CriteriaQuery<Route> q = cb.createQuery(Route.class);
//		
//		Root<Route> root = q.from(Route.class);
//		Join<Route, RouteLine> joinLine = root.join("routeLine");
//
//		List<Predicate> predicates = new ArrayList<Predicate>();
//		
////		Expression<Float> functionDistance = cb.function("St_Distance", Float.class, joinLine.get("lineString"), cb.literal(pointSource.toText()));
//		
//		Expression<Geometry> pointGeom = cb.function("ST_GeomFromText", Geometry.class, cb.literal(pointSource.toText()));
//		Expression<Float> functionDistance = cb.function("ST_Distance_Spheroid", Float.class, joinLine.get("lineString"), pointGeom, cb.concat("SPHEROID[\"GRS 1980\",6378137,298.257222101]", cb.literal("")));
//		
//		predicates.add(cb.lt(functionDistance, 5000));
//		
////		predicates.add(SpatialRestrictions.distanceWithin("lineString", pointSource, 5000));
//
//		if(predicates.size() > 0)
//			q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
//		
//		TypedQuery<Route> tq = getEntityManager().createQuery(q);
//		
//		tq.setMaxResults(10);
//		tq.setFirstResult(0);
//		
//		return tq.getResultList();
	}
}
