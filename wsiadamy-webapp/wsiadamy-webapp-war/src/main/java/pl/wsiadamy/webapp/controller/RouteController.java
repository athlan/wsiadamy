package pl.wsiadamy.webapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Route;

@Controller
@RequestMapping("/route")
public class RouteController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String showRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		if(null == route)
			return "forward:/errors/404";
		
		model.addAttribute("route", route);
		
        return "route/display";
    }
}