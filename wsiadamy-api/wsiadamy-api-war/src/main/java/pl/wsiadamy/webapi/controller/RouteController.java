package pl.wsiadamy.webapi.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.entity.Route;

@Controller
@RequestMapping("/route")
public class RouteController {
	
	@Autowired
	RouteBO routeBO;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody Route getItem(@PathVariable String id) {
		return routeBO.getById(Integer.valueOf(id));
	}
}
