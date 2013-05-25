package pl.wsiadamy.webapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.security.util.AthenticationUtil;

import pl.wsiadamy.common.model.wrapper.RouteUserStatsWrapper;

@Controller
@RequestMapping("/")
public class AccountStatsController {
	
	@Autowired
	RouteBO routeBO;

	@RequestMapping(value="/account/stats", method = { RequestMethod.GET })
	public String stats(ModelMap model) {
		RouteUserStatsWrapper stats = routeBO.getUserStats(AthenticationUtil.getUser());
		
		model.addAttribute("stats", stats);
		return "account/stats";
	}
}
