package pl.wsiadamy.common.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import pl.wsiadamy.common.model.common.AbstractEntity;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "route_line")
public class RouteLine extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL)
	Route route;
	
	@Column(nullable = true)
	@Type(type="org.hibernatespatial.GeometryUserType")
	LineString lineString;

	public RouteLine() {
	}

	public RouteLine(Route route) {
		setRoute(route);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}

	public LineString getLineString() {
		return lineString;
	}

	public void setLineString(LineString lineString) {
		this.lineString = lineString;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
