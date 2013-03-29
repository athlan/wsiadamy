package pl.wsiadamy.api.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;

@Controller
@RequestMapping("/")
public class BaseController {
	
	@Autowired
	UserBO userBO;
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public @ResponseBody User welcome(ModelMap model) {
		User user = new User();
		user.setId(123);
		user.setUsername("username");
		return user;
	}
}