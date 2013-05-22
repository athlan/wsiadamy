package pl.wsiadamy.common.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NotificationSecond extends Notification {
 
    private String routeId;
    
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
}
