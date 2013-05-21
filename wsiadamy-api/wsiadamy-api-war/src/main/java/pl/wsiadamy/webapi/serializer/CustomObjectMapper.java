package pl.wsiadamy.webapi.serializer;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerFactory;

public class CustomObjectMapper extends ObjectMapper {
	 public void setCustomSerializerFactory(SerializerFactory factory) {
		 setSerializerFactory(factory);
	 }
}