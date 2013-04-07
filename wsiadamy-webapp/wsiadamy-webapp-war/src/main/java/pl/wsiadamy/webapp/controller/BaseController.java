package pl.wsiadamy.webapp.controller;
 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.input.RouteSimpleInput;

@Controller
@RequestMapping("/")
public class BaseController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;

	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		return "index";
	}
	
	@RequestMapping(value="/welcomeSecured", method = RequestMethod.GET)
	public String welcomeSecured(ModelMap model) {
		return "index2";
	}
	
	@RequestMapping(value="/welcomeSecuredAdmin", method = RequestMethod.GET)
	public String welcomeSecured2(ModelMap model) {
		return "index3";
	}
	
	@ModelAttribute("routeSimpleInput")
    public RouteSimpleInput getLoginForm() {
        return new RouteSimpleInput();
    }
	
	@RequestMapping(value = "/myForm", method = RequestMethod.GET)
    public String showForm(ModelMap model) {
        return "index";
    }
	
	@RequestMapping(value = "/myForm", method = RequestMethod.POST)
    public String validateForm(
        @ModelAttribute("routeSimpleInput")
        @Valid RouteSimpleInput myUser,
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