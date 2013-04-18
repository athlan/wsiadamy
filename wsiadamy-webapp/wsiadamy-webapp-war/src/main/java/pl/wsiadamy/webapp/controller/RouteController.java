package pl.wsiadamy.webapp.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.webapp.controller.util.AthenticationUtil;
import pl.wsiadamy.webapp.controller.util.Paginator;

@Controller
@RequestMapping("/")
public class RouteController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;
	
	@RequestMapping(value = "/route/get/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteTest')")
    public String showRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
		model.addAttribute("route", route);
		
        return "route/display";
    }

	@RequestMapping(value = "/route/participate/{id}", method = RequestMethod.GET)
    public String participateRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
		User user = AthenticationUtil.getUser();
		routeBO.participateRoute(user, route);
		
		model.addAttribute("route", route);
		
        return "route/display";
    }

	@RequestMapping(value = "/account/routesCreated", method = RequestMethod.GET)
    public String showCreatedRoutes(ModelMap model, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		User user = AthenticationUtil.getUser();
		
		params.put("ownerId", user.getId());
//		params.put("ownerId", user);
		
		Long itemsCount = routeBO.listRoutesCount(params);
		int itemsPage = 1;
		
		if(null != request.getParameter("p"))
			itemsPage = Integer.valueOf(request.getParameter("p"));
		
		Paginator paginator = new Paginator(5, itemsCount.intValue(), itemsPage);
		
		List<Route> result = routeBO.listRoutes(params, paginator.getLimit(), paginator.getOffset());
		model.addAttribute("routes", result);
		
        return "route/listing";
    }
	
	@RequestMapping(value = "/account/routes", method = RequestMethod.GET)
    public String showParticipatedRoutes(ModelMap model) {
		
		
        return "route/listing";
    }
}