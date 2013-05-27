package pl.wsiadamy.webapp.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.UserBO;

@Controller
@RequestMapping("/")
public class UserProfileController {
	
	@Autowired
	UserBO userBO;

	@RequestMapping(value = "/userProfile/get/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteView')")
    public String showRoute(@PathVariable("id") Integer id, ModelMap model) {
		
		
		
        return "route/display";
    }
}