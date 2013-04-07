package pl.wsiadamy.webapp.controller;
 
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.model.input.RouteSearchSimpleInput;

@Controller
@RequestMapping("/route")
public class RouteSearchController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;
	
	@ModelAttribute("routeSearchSimpleInput")
    public RouteSearchSimpleInput getSearchSimpleForm() {
        return new RouteSearchSimpleInput();
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchForm(ModelMap model) {
        return "route/search";
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchFormProcess(
        @ModelAttribute("routeSearchSimpleInput")
        @Valid RouteSearchSimpleInput form,
        BindingResult result, ModelMap model) {
		
        if (result.hasErrors()) {
            return "route/search";
        }
        
        List<Route> results = routeBO.findRoutes(form);
        
        return "route/search";
 
    }
}