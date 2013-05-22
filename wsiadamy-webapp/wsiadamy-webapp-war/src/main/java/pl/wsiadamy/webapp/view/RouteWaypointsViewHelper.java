package pl.wsiadamy.webapp.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;

import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.entity.RouteWaypoint;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;
import pl.wsiadamy.common.model.wrapper.RouteSearchResultWrapper;

@Service(value="routeWaypointsViewHelper")
public class RouteWaypointsViewHelper {
	private Comparator<WaypointEntry> waypointsComparator = new Comparator<WaypointEntry>() {

		@Override
		public int compare(WaypointEntry o1, WaypointEntry o2) {
			return o1.getRoutePosition().compareTo(o2.getRoutePosition());
		}
	};
	
	public String displayResultLine(RouteSearchResultWrapper routeResult, RouteSearchSimpleInput input) {
		StringBuilder out = new StringBuilder();
		
		List<WaypointEntry> waypoints = new ArrayList<WaypointEntry>();
		
		WaypointEntry entry;

		Set<String> waypointsNames = new HashSet<String>();

		entry = new WaypointEntry();
		entry.setName(input.getLocationSource());
		entry.setRoutePosition(routeResult.getPositionSource());
		entry.setFromInput(true);
		waypoints.add(entry);
		waypointsNames.add(entry.getName());
		
		entry = new WaypointEntry();
		entry.setName(input.getLocationDestination());
		entry.setRoutePosition(routeResult.getPositionDestination());
		entry.setFromInput(true);
		waypoints.add(entry);
		waypointsNames.add(entry.getName());
		
		for (RouteWaypoint waypoint : routeResult.getRoute().getWaypoints()) {
			if(waypointsNames.contains(waypoint.getName()))
				continue;
			
			entry = new WaypointEntry();
			entry.setName(waypoint.getName());
			entry.setRoutePosition(waypoint.getRoutePosition());
			entry.setFromInput(false);
			waypoints.add(entry);
			waypointsNames.add(entry.getName());
		}
		
		Collections.sort(waypoints, waypointsComparator);
		
		int i = 0;
		int waypointsSize = waypoints.size();
		for (WaypointEntry waypoint : waypoints) {
			List<String> css = new ArrayList<String>();
			
			css.add("waypoint");

			if(waypoint.isFromInput())
				css.add("waypointInput");

			if(i == 1)
				css.add("waypointSource");
			
			if(i == waypointsSize)
				css.add("waypointDestination");
			
			out.append("<span class=\"" + Joiner.on(" ").join(css) + "\">" + waypoint.getName() + "</span>");
		}
		
		return out.toString();
	}
	
	class WaypointEntry {
		
		private String name;
		
		private Double routePosition;
		
		private boolean fromInput;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getRoutePosition() {
			return routePosition;
		}

		public void setRoutePosition(Double routePosition) {
			this.routePosition = routePosition;
		}

		public boolean isFromInput() {
			return fromInput;
		}

		public void setFromInput(boolean fromInput) {
			this.fromInput = fromInput;
		}
	}
}
