package pl.wsiadamy.webapp.controller;
 
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.RouteAddDetailsInput;
import pl.wsiadamy.common.model.input.RouteAddInput;
import pl.wsiadamy.common.model.input.RouteInput;
import pl.wsiadamy.common.security.util.AthenticationUtil;
import pl.wsiadamy.webapp.controller.util.ValidatorHelper;

@Controller
@RequestMapping("/route")
@SessionAttributes({"routeInput", "routeAddInput", "routeAddDetailsInput"})
public class RouteAddController {

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
	
	@ModelAttribute("routeAddInput")
    public RouteAddInput getFormAddRoute() {
        return new RouteAddInput();
    }
	
	@ModelAttribute("routeAddDetailsInput")
    public RouteAddDetailsInput getFormAddDetailsRoute() {
        return new RouteAddDetailsInput();
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String step1ShowForm(ModelMap model) {
        return "route/add";
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String step1ValidateForm(
        @ModelAttribute("routeInput")
        @Valid RouteInput form,
        BindingResult result, ModelMap model) {
		
		HashMap<String, String> waypoints = form.getWaypoints();
		HashMap<String, String> waypointsCoords = form.getWaypointsCoords();
		
		for(Map.Entry<String, String> entry : waypoints.entrySet()) {
			if(entry.getValue().equals("")) {
//				waypoints.remove(entry.getKey());
//				waypointsCoords.remove(entry.getKey());
			}
		}
		
		form.setWaypoints(waypoints);
		form.setWaypointsCoords(waypointsCoords);
		
        if (!validatorHelper.isValid(result, form, RouteInput.BasicDataValidationGroup.class )) {
//        	if (result.hasErrors()) {
            return "route/add";
        }
        
        //routeBO.createRoute(form);
        
//        return "route/add";
        return "redirect:addDetails";
    }

	@RequestMapping(value = "/addDetails", method = RequestMethod.GET)
    public String step2ShowForm(
        @ModelAttribute("routeInput")
        @Valid RouteInput form,
        
        BindingResult result,
        
        ModelMap model) {
		
		if (!validatorHelper.isValid(result, form, RouteInput.BasicDataValidationGroup.class )) {
            return "redirect:add";
        }
        
        return "route/addDetails";
    }

	@RequestMapping(value = "/addDetails", method = RequestMethod.POST)
    public String step2ValidateForm(
        @ModelAttribute("routeInput")
        @Valid RouteInput form,
        
        BindingResult result,
        
        ModelMap model) {
		
        if (result.hasErrors()) {
            return "route/addDetails";
        }
        
        return "redirect:addCommit";
    }
	
	@RequestMapping(value = "/addCommit", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
    public String commitAction(
		@ModelAttribute("routeInput")
        RouteInput form,
        
        BindingResult result,
        
        ModelMap model,
        
		Principal principal) {

    	if (result.hasErrors()) {
            return "redirect:addDetails";
        }
    	
    	User user = userBO.getById(AthenticationUtil.getUser().getId());
    	Route route = routeBO.createRoute(user, form);
    	
        return "redirect:/route/get/" + route.getId();
    }
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String step1ValidateForm(
//        @ModelAttribute("routeAddInput")
//        @Valid RouteAddInput form,
//        BindingResult result, ModelMap model) {
//		
//		HashMap<String, String> waypoints = form.getWaypoints();
//		HashMap<String, String> waypointsCoords = form.getWaypointsCoords();
//		
//		for(Map.Entry<String, String> entry : waypoints.entrySet()) {
//			if(entry.getValue().equals("")) {
////				waypoints.remove(entry.getKey());
////				waypointsCoords.remove(entry.getKey());
//			}
//		}
//		
//		form.setWaypoints(waypoints);
//		form.setWaypointsCoords(waypointsCoords);
//		
//        if (result.hasErrors()) {
//            return "route/add";
//        }
//        
//        //routeBO.createRoute(form);
//        
////        return "route/add";
//        return "redirect:addDetails";
//    }
//
//	@RequestMapping(value = "/addDetails", method = RequestMethod.GET)
//    public String step2ShowForm(
//        @ModelAttribute("routeAddInput")
//        @Valid RouteAddInput form,
//        
//        BindingResult result,
//        
//        ModelMap model) {
//		
//        if (result.hasErrors()) {
//            return "redirect:add";
//        }
//        
//        return "route/addDetails";
//    }
//
//	@RequestMapping(value = "/addDetails", method = RequestMethod.POST)
//    public String step2ValidateForm(
//        @ModelAttribute("routeAddInput")
//        @Valid RouteAddInput form,
//        
//        @ModelAttribute("routeAddDetailsInput")
//        @Valid RouteAddDetailsInput formDetails,
//        
//        BindingResult result,
//        
//        ModelMap model) {
//		
//        if (result.hasErrors()) {
//            return "route/addDetails";
//        }
//        
//        return "redirect:addCommit";
//    }
//	
//	@RequestMapping(value = "/addCommit", method = RequestMethod.GET)
//	@PreAuthorize("hasRole('ROLE_USER')")
//    public String commitAction(
//		@ModelAttribute("routeAddInput")
//        RouteAddInput form,
//
//        BindingResult result1,
//        
//        @ModelAttribute("routeAddDetailsInput")
//        @Valid RouteAddDetailsInput formDetails,
//        
//        BindingResult result,
//        
//        ModelMap model,
//        
//		Principal principal) {
//
//    	if (result1.hasErrors() || result.hasErrors()) {
//            return "redirect:addDetails";
//        }
//    	
//    	User user = userBO.getById(AthenticationUtil.getUser().getId());
//    	Route route = routeBO.createRoute(user, form, formDetails);
//    	
//        return "redirect:/route/get/" + route.getId();
//    }
}