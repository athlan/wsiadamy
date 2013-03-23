package pl.wsiadamy.webapp.controller;
 
import java.util.Map;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.UserInput;

@Controller
@RequestMapping("/")
public class BaseController {
	
	@Autowired
	UserBO userBO;
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		User uu;
		
		uu = userBO.createUser("test2", "aa");
		model.addAttribute("message", "user: " + uu.getId());
		
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "index";
 
	}
	
	@ModelAttribute("myUser")
    public UserInput getLoginForm() {
        return new UserInput();
    }
	
	@RequestMapping(value = "/myForm", method = RequestMethod.GET)
    public String showForm(ModelMap model) {
        return "index";
    }
	
	@RequestMapping(value = "/myForm", method = RequestMethod.POST)
    public String validateForm(
        @ModelAttribute("myUser")
        @Valid UserInput myUser,
        BindingResult result, ModelMap model) {
		
        if (result.hasErrors()) {
            return "index";
        }

		model.addAttribute("message", "good!");
        //model.put("index", myUser);
 
        return "index";
 
    }
	
	@RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {
 
		model.addAttribute("message", "AAA Maven Web Project + Spring 3 MVC - " + name);
		return "index";
 
	}
 
}