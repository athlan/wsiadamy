package pl.wsiadamy.webapp.controller;
 
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AuthenticationController {
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginToRedirect(ModelMap model, HttpServletRequest request) {
//		String ref = request.getHeader("referer");
//		ref = request.getContextPath();
//		
//		if(null != ref) {
//			return "redirect:/login?r=" + request.getPathInfo();
//		}
//		
		return "auth/login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET, params={"r"})
	public String login(ModelMap model) {
		return "auth/login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "auth/logout";
	}
}