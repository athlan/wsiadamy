package pl.wsiadamy.webapp.controller;
 
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
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Controller
@RequestMapping("/")
public class RouteParticipationController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;

	@Autowired
	ParticipanseBO participanseBO;
	
	@RequestMapping(value = "/route/participate/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteParticipate')")
    public String participateRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
		User user = AthenticationUtil.getUser();
		participanseBO.participateRoute(user, route);
		
		model.addAttribute("route", route);

        return "redirect:/route/get/" + id;
    }

	@RequestMapping(value = "/route/participateCancel/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteParticipateCancel')")
    public String participateRouteCancel(@PathVariable("id") Integer id, ModelMap model) {
		
		Participanse participanse = participanseBO.getById(id);
		
		if(null == participanse)
			return "forward:/errors/404";
		
		User user = AthenticationUtil.getUser();
		participanseBO.participateRouteCancel(participanse);
		
        return "redirect:/route/get/" + participanse.getRoute().getId();
    }
	
	@RequestMapping(value = "/route/participateAccept/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteParticipateAccept')")
    public String participateRouteAccept(@PathVariable("id") Integer id, ModelMap model) {
		
		Participanse participanse = participanseBO.getById(id);
		
		if(null == participanse)
			return "forward:/errors/404";
		
        return "redirect:/route/get/" + participanse.getRoute().getId();
    }
	
	@RequestMapping(value = "/route/participateReject/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER') AND hasPermission(#id, 'RouteParticipateReject')")
    public String participateRouteReject(@PathVariable("id") Integer id, ModelMap model) {
		
		Participanse participanse = participanseBO.getById(id);
		
		if(null == participanse)
			return "forward:/errors/404";
		
        return "redirect:/route/get/" + participanse.getRoute().getId();
    }

}