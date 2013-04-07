package pl.wsiadamy.common.model.util;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

public class GeometryPointFactory {
	
	private static GeometryFactory geometryFactory;
	
	public static GeometryFactory getGeometryFactory() {
		if(null == geometryFactory) {
			geometryFactory = new GeometryFactory();
		}
		
		return geometryFactory;
	}
	
	public static Point createPointFromString(String input) throws IllegalArgumentException {
        return getGeometryFactory().createPoint(createCoordinateFromString(input));
	}
	
	public static Coordinate createCoordinateFromString(String input) throws IllegalArgumentException {
        String[] coordinates = input.split(" ");
        
        double x, y;
        
        try {
	        x = Double.valueOf(coordinates[1]).doubleValue();
	        y = Double.valueOf(coordinates[0]).doubleValue();
        }
        catch(NumberFormatException e) {
        	throw new IllegalArgumentException(e);
        }
        
        return new Coordinate(x, y);
	}


	public static LineString createLineStringFromString(String routeLine) {
		String[] points = routeLine.split(",");
		
		List<Coordinate> pointsList = new ArrayList<Coordinate>();
		
		for(String coordinate : points) {
			pointsList.add(createCoordinateFromString(coordinate));
		}
		
		return getGeometryFactory().createLineString(pointsList.toArray(new Coordinate[pointsList.size()]));
	}
	
	
}
