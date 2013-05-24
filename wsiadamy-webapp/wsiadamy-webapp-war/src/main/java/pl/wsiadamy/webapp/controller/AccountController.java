package pl.wsiadamy.webapp.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AccountController {

	@RequestMapping(value="/account", method = { RequestMethod.GET, RequestMethod.POST })
	public String welcome(ModelMap model) {
		return "forward:/account/routes";
	}
}
