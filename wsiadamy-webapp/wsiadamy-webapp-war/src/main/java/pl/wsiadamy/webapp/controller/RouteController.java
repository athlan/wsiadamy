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
import pl.wsiadamy.common.model.bo.ParticipanseBO;
import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.wrapper.RouteParticipanseWrapper;
import pl.wsiadamy.common.security.util.AthenticationUtil;
import pl.wsiadamy.webapp.controller.util.Paginator;

@Controller
@RequestMapping("/")
public class RouteController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;

	@Autowired
	RouteDao routeDao;
	
	@Autowired
	ParticipanseBO participanseBO;
	
	@RequestMapping(value = "/route/get/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteView')")
    public String showRoute(@PathVariable("id") Integer id, ModelMap model) {
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
		model.addAttribute("route", route);
		
		// get participanse
		User user = AthenticationUtil.getUser();
		if(null != user) {
			Participanse routeParticipanse = participanseBO.getByUserRoute(user, route);
			model.addAttribute("routeParticipanse", routeParticipanse);
		}
		
		routeDao.synchronizeWaypointsRoutePositions(route);
		
        return "route/display";
    }
	
	@RequestMapping(value = "/route/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteRemove')")
    public String removeRouteConfirmation(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";

		model.addAttribute("route", route);
		
		return "route/removeConfirmation";
    }

	@RequestMapping(value = "/route/remove/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteRemove')")
    public String removeRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
//		return "route/display";
		return removeRoute(route, model);
    }

//	@PreAuthorize("hasPermission(#route, 'RouteRemove')")
    public String removeRoute(Route route, ModelMap model) {
		routeBO.delete(route);
		
        return "redirect:/account/routesCreated";
    }

	@RequestMapping(value = "/account/routesCreated", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
    public String showCreatedRoutes(ModelMap model, HttpServletRequest request) {
		User user = AthenticationUtil.getUser();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ownerId", user.getId());
		params.put("loggedUserId", user.getId());
		
		Long itemsCount = routeBO.listRoutesCount(params);
		int itemsPage = 1;
		
		if(null != request.getParameter("p"))
			itemsPage = Integer.valueOf(request.getParameter("p"));
		
		Paginator paginator = new Paginator(5, itemsCount.intValue(), itemsPage);
		
		List<RouteParticipanseWrapper> result = routeBO.listRoutes(params, paginator.getLimit(), paginator.getOffset());
		model.addAttribute("routes", result);
		
        return "route/listing";
    }
	
	@RequestMapping(value = "/account/routes", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
    public String showParticipatedRoutes(ModelMap model, HttpServletRequest request) {
		User user = AthenticationUtil.getUser();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("participantId", user.getId());
		params.put("loggedUserId", user.getId());
		
		Long itemsCount = routeBO.listRoutesCount(params);
		int itemsPage = 1;
		
		if(null != request.getParameter("p"))
			itemsPage = Integer.valueOf(request.getParameter("p"));
		
		Paginator paginator = new Paginator(5, itemsCount.intValue(), itemsPage);
		
		List<RouteParticipanseWrapper> result = routeBO.listRoutes(params, paginator.getLimit(), paginator.getOffset());
		model.addAttribute("routes", result);
		
        return "route/listing";
    }
}