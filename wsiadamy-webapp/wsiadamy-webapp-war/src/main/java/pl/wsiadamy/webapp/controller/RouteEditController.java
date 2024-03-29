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
import pl.wsiadamy.common.model.input.RouteInput;
import pl.wsiadamy.webapp.controller.util.ValidatorHelper;

@Controller
@RequestMapping("/route")
@SessionAttributes({"routeAddDetailsInput"})
public class RouteEditController {

	@Autowired
	UserBO userBO;

	@Autowired
	RouteBO routeBO;

	@Autowired
	ValidatorHelper validatorHelper;

	@ModelAttribute("routeInput")
    public RouteInput getFormRoute() {
        return new RouteInput();
    }
	
	@RequestMapping(value = "/editDetails/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#id, 'RouteEdit')")
    public String step2ShowForm(
		@PathVariable
    	Integer id,
    	
    	@ModelAttribute("routeInput")
        RouteInput form,
        
        ModelMap model) {
		
		Route route = routeBO.getById(id);
		
		form.setSeats(route.getSeats());
		form.setCarCombustion(route.getRouteDetails().getCarCombustion());
		form.setFuelPrice(route.getRouteDetails().getFuelPrice());
		form.setRouteLength(route.getRouteDetails().getRouteLength());
		form.setTotalPrice(route.getTotalPrice());
		form.setParticipansModeration(route.isParticipanseModeration());
		
        return "route/editDetails";
    }

	@RequestMapping(value = "/editDetails/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#id, 'RouteEdit')")
    public String step2ValidateForm(
		@PathVariable
    	Integer id,
    	
        @ModelAttribute("routeInput")
        RouteInput form,
        
        BindingResult result,
        
        ModelMap model) {
		
		if (validatorHelper.isValid(result, form, RouteInput.EditValidationGroup.class )) {
			Route route = routeBO.editRoute(id, form);
        	return "redirect:/route/get/" + route.getId();
        }
        
//        if (!result.hasErrors()) {
//        	
//        }
        
        return "route/editDetails";
    }
}