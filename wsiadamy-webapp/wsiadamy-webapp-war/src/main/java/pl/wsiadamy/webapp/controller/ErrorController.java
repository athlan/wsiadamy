package pl.wsiadamy.webapp.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ErrorController {

	@RequestMapping(value = "/error404", method = RequestMethod.GET)
    public String error404() {
        return "error/404";
    }

	@RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String error403() {
        return "error/403";
    }
}