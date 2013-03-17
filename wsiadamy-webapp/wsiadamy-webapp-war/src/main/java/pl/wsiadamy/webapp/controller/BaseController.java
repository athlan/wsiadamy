package pl.wsiadamy.webapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;

@Controller
@RequestMapping("/")
public class BaseController {
	
	@Autowired
	UserBO userBO;
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		User uu;
		
		uu = userBO.createUser("test", "aa");
		model.addAttribute("message", "user: " + uu.getId());
		
		
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "index";
 
	}

	@RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {
 
		model.addAttribute("message", "AAA Maven Web Project + Spring 3 MVC - " + name);
		return "index";
 
	}
 
}