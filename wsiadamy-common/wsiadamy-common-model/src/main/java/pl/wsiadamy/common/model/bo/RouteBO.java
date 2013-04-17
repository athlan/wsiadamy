package pl.wsiadamy.common.model.bo;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteAddDetailsInput;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;

public interface RouteBO {
	void save(Route route);
	void update(Route route);
	void delete(Route route);
	
	Route getById(Integer id);

//	Route createRoute();

	Route createRoute(User owner, RouteAddInput input, RouteAddDetailsInput inputDetails);
	
	boolean participateRoute(User participant, Route route);
	
	List<Route> findRoutes(RouteSearchSimpleInput input);
	
	List<Route> listRoutes(Map<String, Object> params, int limit, int offset);
	
	Long listRoutesCount(Map<String, Object> params);
}
