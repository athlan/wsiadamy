package pl.wsiadamy.webapp.controller;
 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.input.RouteAddDetailsInput;
import pl.wsiadamy.common.model.input.RouteAddInput;

@Controller
@RequestMapping("/route")
@SessionAttributes({"routeAddDetailsInput"})
public class RouteEditController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;
	
	@ModelAttribute("routeAddDetailsInput")
    public RouteAddDetailsInput getFormAddDetailsRoute() {
        return new RouteAddDetailsInput();
    }
	
	@RequestMapping(value = "/editDetails/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteEdit')")
    public String step2ShowForm(
		@PathVariable
    	Integer id,
    	
    	@ModelAttribute("routeAddDetailsInput")
        RouteAddDetailsInput formDetails,
        
        ModelMap model) {
		
		Route route = routeBO.getById(id);

		formDetails.setCarCombustion(route.getRouteDetails().getCarCombustion());
		formDetails.setFuelPrice(route.getRouteDetails().getFuelPrice());
		formDetails.setRouteLength(route.getRouteDetails().getRouteLength());
		formDetails.setTotalPrice(route.getTotalPrice());
		formDetails.setParticipansModeration(route.isParticipanseModeration());
		
        return "route/editDetails";
    }

	@RequestMapping(value = "/editDetails/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#id, 'RouteEdit')")
    public String step2ValidateForm(
		@PathVariable
    	Integer id,
    	
        @ModelAttribute("routeAddDetailsInput")
        @Valid RouteAddDetailsInput formDetails,
        
        BindingResult result,
        
        ModelMap model) {
		
        if (!result.hasErrors()) {
        	Route route = routeBO.editRoute(id, null, formDetails);
        	return "redirect:/route/get/" + route.getId();
        }
        
        return "route/editDetails";
    }
}