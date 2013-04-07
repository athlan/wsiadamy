package pl.wsiadamy.api.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.vividsolutions.jts.geom.Point;

public class RouteSerializer extends JsonSerializer<Point> {

	@Override
	public void serialize(Point item, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		gen.writeNumber(item.getCoordinate().x);
	}

}
