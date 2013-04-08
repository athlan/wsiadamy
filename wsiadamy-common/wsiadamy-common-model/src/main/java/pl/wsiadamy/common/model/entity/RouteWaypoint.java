package pl.wsiadamy.common.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import pl.wsiadamy.common.model.common.AbstractEntity;

import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "route_waypoint")
public class RouteWaypoint extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	Route route;
	
	@Column(nullable = true)
	@Type(type="org.hibernatespatial.GeometryUserType")
	Point point;
	
	@Column
	private
	String name;
	
	public RouteWaypoint() {
	}

	public RouteWaypoint(Route route, Point point) {
		this.route = route;
		setPoint(point);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
